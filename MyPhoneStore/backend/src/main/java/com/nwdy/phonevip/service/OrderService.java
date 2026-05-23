package com.nwdy.phonevip.service;

import com.nwdy.phonevip.dto.SelectedOrderItemDTO;
import com.nwdy.phonevip.dto.request.AddressRequest;
import com.nwdy.phonevip.dto.response.*;
import com.nwdy.phonevip.exception.AppException;
import com.nwdy.phonevip.exception.ErrorCode;
import com.nwdy.phonevip.mapper.OrderMapper;
import com.nwdy.phonevip.model.*;
import com.nwdy.phonevip.model.enums.PaymentStatus;
import com.nwdy.phonevip.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final CartItemRepository cartItemRepository;

    private final OrderItemRepository orderItemRepository;

    private final ProductRepository productRepository;

    private BigDecimal totalPrice;

    public OrderInfoResponse purchase() {
        List<OrderItemDTO> orderItemDTOList = cartItemRepository.findOrderItemDTOsByUsername(getCurrentUsername());
        User user = userRepository.findByUsername(getCurrentUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        OrderInfoResponse orderInfoResponse = new OrderInfoResponse(orderItemDTOList, user.getPhoneNumber(), user.getAddress());
        totalPrice = orderInfoResponse.getTotalPrice();
        return orderInfoResponse;
    }

    public OrderDTO createOrder(AddressRequest request) {
        Order order = new Order();
        // Update user address
        User user = userRepository.findByUsername(getCurrentUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        userRepository.save(user);

        // Save the order
        order.setUser(user);
        order.setTotalPrice(totalPrice);
        order.setTransactionNo("000"); // Initial transaction number
        order.setPaymentStatus(PaymentStatus.PENDING);
        orderRepository.save(order);
        log.info("Order created {}, id {}, order code {}, total price {}", order, order.getId(), order.getOrderCode(), order.getTotalPrice());
        return OrderMapper.INSTANCE.toOrderDTO(order);
    }

    public void processAfterPayment(String orderCode, String vnp_ResponseCode, String vnp_TransactionNo) {
        System.out.println("Processing after payment: orderCode = " + orderCode);
        Order order = orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        // Update order
        // Update payment status
        if ("00".equals(vnp_ResponseCode)) {
            order.setPaymentStatus(PaymentStatus.COMPLETED);
        } else {
            order.setPaymentStatus(PaymentStatus.CANCELLED);
        }

        // Update VNPay transaction number
        order.setTransactionNo(vnp_TransactionNo);
        orderRepository.save(order);


        System.out.println("Getting cart items for saving order");
        List<SelectedOrderItemDTO> selectedItems = cartItemRepository
                .findSelectedOrderItemDTOsByUsername(order.getUser().getUsername());

        if (selectedItems.isEmpty()) {
            log.warn("Order {} has no cart items", orderCode);
        }

        for (SelectedOrderItemDTO item : selectedItems) {
            System.out.println("Processing item: " + item);
            // Save order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItemRepository.save(orderItem);
            log.info("Saved order item {}", orderItem);

            // Update product stock
            Product product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
            log.info("Updated product stock quantity: id {}, stock quantity {}", product.getId(), product.getStock());
        }

        // Remove paid/selected cart items
        System.out.println("Deleting cart items from cart");
        List<Long> cartItemIds = selectedItems.stream().map(SelectedOrderItemDTO::getCartItemId).toList();
        if (!cartItemIds.isEmpty()) {
            cartItemRepository.deleteByIdIn(cartItemIds);
            System.out.println("Deleted " + cartItemIds.size() + " cart items for order " + orderCode);
        } else {
            System.out.println("No cart items found");
        }

    }

    public List<OrderHistoryDTO> getOrderHistory() {
        List<OrderHistoryDTO> orders = orderRepository.findHistoricalOrderDTOsByUsername(getCurrentUsername());
        for (OrderHistoryDTO order : orders) {
            List<OrderItemDTO> items = orderItemRepository.findOrderItemsByOrderId(order.getOrderId());
            order.setOrderItemDTOList(items);
        }
        return orders;
    }

    public List<OrderItemReviewDTO> getOrderItemReviews(Long orderId) {
        User user = userRepository.findByUsername(getCurrentUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        if (!order.getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }

        return orderItemRepository.findOrderItemReviewDTOByOrderId(orderId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> getOrders() {
        return orderRepository.findAllOrders();
    }

    public boolean orderExists(String orderCode) {
        return orderRepository.existsByOrderCode(orderCode);
    }

    public String getAmount() {
        return String.valueOf(BigDecimal.valueOf(100).multiply(totalPrice).longValue());
    }

    public PaymentStatus getPaymentStatus(String orderCode) {
        Order order = orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return order.getPaymentStatus();
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
