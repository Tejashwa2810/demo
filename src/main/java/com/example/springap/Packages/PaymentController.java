package com.example.springap.Packages;

import com.example.springap.Models.Product;
import com.example.springap.Services.PaymentService;
import com.example.springap.Services.SelfProductService;
import com.example.springap.dto.PaymentRequestDto;
import com.example.springap.dto.PaymentRequestDtoForMultiply;
import com.example.springap.exceptions.ProductNotFoundException;
import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private  PaymentService paymentService;
    private SelfProductService selfProductService;

    public PaymentController(PaymentService paymentService, SelfProductService selfProductService) {
        this.paymentService = paymentService;
        this.selfProductService = selfProductService;
    }

    @PostMapping("/payments")
    public ResponseEntity<String> createPaymentLink(@RequestBody PaymentRequestDto paymentRequestDto) throws StripeException {
        String paymentLink = paymentService.makePayment(paymentRequestDto.getOrderId(), paymentRequestDto.getAmount());
        return new ResponseEntity<>(paymentLink, HttpStatus.OK);
    }

    @PostMapping("/payments/multiple")
    public ResponseEntity<String> createPaymentLinkForMultiple(
            @RequestBody PaymentRequestDtoForMultiply paymentRequestDtoForMultiply) throws StripeException {

        String paymentLink = paymentService.makePaymentForMultiple(
                paymentRequestDtoForMultiply.getCompleteOrderId(),
                null,
                paymentRequestDtoForMultiply.getItems());

        return new ResponseEntity<>(paymentLink, HttpStatus.OK);
    }





    @PostMapping("/payments/{id}")
    public ResponseEntity<String> createPayment(@PathVariable("id") Long id, @RequestBody PaymentRequestDto paymentRequestDto) throws StripeException, ProductNotFoundException {
        Product product = selfProductService.getSingleProduct(id);
        Long amount = Math.round(product.getPrice())*100;
        String paymentLink = paymentService.makePayment(paymentRequestDto.getOrderId(), amount);
        return new ResponseEntity<>(paymentLink, HttpStatus.OK);
    }
}
