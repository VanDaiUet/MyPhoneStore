package com.nwdy.phonevip.service;

import com.nwdy.phonevip.config.VNPayConfig;
import com.nwdy.phonevip.constant.VnpIpnResponseConst;
import com.nwdy.phonevip.dto.request.AddressRequest;
import com.nwdy.phonevip.dto.response.IpnResponse;
import com.nwdy.phonevip.dto.response.OrderDTO;
import com.nwdy.phonevip.dto.response.PaymentResponse;
import com.nwdy.phonevip.model.enums.PaymentStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final OrderService orderService;

    public PaymentResponse handlePayment(String baseUrl, AddressRequest request) {
        // TODO: Removing this function: orderService.purchase()
//        orderService.purchase();
        OrderDTO orderDTO = orderService.createOrder(request);

        long amount = orderDTO.getTotalPrice().longValue();
        String orderId = orderDTO.getOrderCode();
        String orderInfo = String.format("Thanh toan don hang %s", orderDTO.getOrderCode());

        String vnp_Url = createReturnUrl(amount, orderId, orderInfo, baseUrl);
        log.info("[VNPay] return url: {}", vnp_Url);

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setReturnUrl(vnp_Url);

        return paymentResponse;
    }

    private String createReturnUrl(long amount, String orderCode, String orderInfo, String baseUrl) {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other-type";

//        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1"; //TODO: Get value from input of func
        String vnp_ReturnUrl = baseUrl + VNPayConfig.vnp_ReturnUrl;
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100L));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", orderCode);
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = "vn"; //TODO: Get value from input of func
        vnp_Params.put("vnp_Locale", locate);
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);


        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);

            if (fieldValue != null && !fieldValue.isEmpty()) {
                if (!hashData.isEmpty()) {
                    hashData.append("&");
                }
                // Build hash data
                hashData
                        .append(fieldName)
                        .append("=")
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                if (!query.isEmpty()) {
                    query.append("&");
                }
                // Build query
                query
                        .append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII))
                        .append("=")
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        return VNPayConfig.vnp_PayUrl + "?" + queryUrl;

    }

    public IpnResponse process(HttpServletRequest request) {
        try
        {
            /*  IPN URL: Record payment results from VNPAY
            Implementation steps:
            Check checksum
            Find transactions (vnp_TxnRef) in the database (checkOrderId)
            Check the payment status of transactions before updating (checkOrderStatus)
            Check the amount (vnp_Amount) of transactions before updating (checkAmount)
            Update results to Database
            Return recorded results to VNPAY
            */

            // ex:  	PaymentStatus = 0; pending
            //          PaymentStatus = 1; success
            //          PaymentStatus = 2; Fail

            // Check checksum
            Map<String, String> fields = extractFieldsFromRequest(request);
            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (!validateChecksum(vnp_SecureHash, fields)) {
                return VnpIpnResponseConst.INVALID_CHECKSUM;
            }

            // Check orderId (vnp_TxnRef) in the database
            String orderCode = request.getParameter("vnp_TxnRef");
            if (!validateOrderExists(orderCode)) {
                return VnpIpnResponseConst.ORDER_NOT_FOUND;
            }

            // Check vnp_Amount VNPAY returns compared to the amount of the code (vnp_TxnRef) in the database
            String amount = request.getParameter("vnp_Amount");
            if (!validateOrderAmount(amount)) {
                return VnpIpnResponseConst.INVALID_AMOUNT;
            }

            // Check payment status of order (expected status = PENDING)
            if (!validatePaymentStatus(orderCode)) {
                return VnpIpnResponseConst.ORDER_ALREADY_CONFIRMED;
            }

            // Process after payment
            String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
            String vnp_TransactionNo = request.getParameter("vnp_TransactionNo");
            orderService.processAfterPayment(orderCode, vnp_ResponseCode, vnp_TransactionNo);

            return VnpIpnResponseConst.SUCCESS;
        }
        catch (Exception e)
        {
            return VnpIpnResponseConst.UNKNOWN_ERROR;
        }
    }

    private Map<String, String> extractFieldsFromRequest(HttpServletRequest request) {
        Map<String, String> fields = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = URLEncoder.encode(paramNames.nextElement(), StandardCharsets.US_ASCII);
            String paramValue = URLEncoder.encode(request.getParameter(paramName), StandardCharsets.US_ASCII);

            if (paramValue != null && !paramValue.isEmpty()) {
                fields.put(paramName, paramValue);
            }
        }
        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");

        return fields;
    }

    private boolean validateChecksum(String vnp_SecureHash, Map<String, String> fields) {
        String signValue = VNPayConfig.hashAllFields(fields);
        return vnp_SecureHash.equals(signValue);
    }

    private boolean validateOrderExists(String orderCode) {
        return orderService.orderExists(orderCode);
    }

    private boolean validateOrderAmount(String amount) {
        String totalPrice = orderService.getAmount();
        System.out.println(totalPrice + ", " + amount);
        return totalPrice.equals(amount);
    }

    private boolean validatePaymentStatus(String orderCode) {
        return orderService.getPaymentStatus(orderCode) == PaymentStatus.PENDING;
    }
}
