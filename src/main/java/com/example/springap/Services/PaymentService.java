package com.example.springap.Services;

import com.example.springap.dto.PaymentRequestDtoForMultiply;
import com.stripe.exception.StripeException;

import java.util.List;

public interface PaymentService {
    String makePayment(String orderId, Long amount) throws StripeException;

    String makePaymentForMultiple(String orderId, Long amount, List<PaymentRequestDtoForMultiply.Item> items) throws StripeException;
}
