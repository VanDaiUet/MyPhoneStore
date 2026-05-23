package com.nwdy.phonevip.controller;

import com.nwdy.phonevip.dto.request.AddressRequest;
import com.nwdy.phonevip.dto.response.*;
import com.nwdy.phonevip.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/submitOrder")
    public ResponseEntity<ApiResponse<PaymentResponse>> submitOrder(
            @RequestBody AddressRequest addressRequest
    ) {
        String baseUrl = "https://grower-scorch-apron.ngrok-free.dev";
        return ResponseEntity.ok(ApiResponse.success(
                "Processing payment",
                paymentService.handlePayment(baseUrl, addressRequest)
        ));
    }

    @GetMapping("/ipn")
    public ResponseEntity<IpnResponse> getIPN(HttpServletRequest request) {
        IpnResponse ipnResponse = paymentService.process(request);
        log.info("[VNPay IPN] response: {}", ipnResponse);
        return ResponseEntity.ok(ipnResponse);
    }

}
