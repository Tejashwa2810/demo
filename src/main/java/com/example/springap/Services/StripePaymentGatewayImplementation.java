package com.example.springap.Services;

import com.example.springap.dto.PaymentRequestDtoForMultiply;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StripePaymentGatewayImplementation implements PaymentService{
    @Override
    public String makePayment(String orderId, Long amount) throws StripeException {

        Stripe.apiKey = "sk_test_51QfzTvGDrr2Oa02h5VodG4Ak9dEknFCymxo5B0UkSs0lqhfdsQIXQAejW79PvOvbtOhi8OtlY4VBkLXLlEIqCdf800IT74nTc7";

        PriceCreateParams params =
                PriceCreateParams.builder()
                        .setCurrency("usd")
                        .setUnitAmount(amount)
                        .setProductData(
                                PriceCreateParams.ProductData.builder().setName(orderId).build()
                        )
                        .build();

        Price price = Price.create(params);

        //Creating Payment Link
        PaymentLinkCreateParams LinkParams =
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(1L)
                                        .build()
                        ).setAfterCompletion(  // Redirects you to any particular url after the completion of payment (Callbacks)
                                PaymentLinkCreateParams.AfterCompletion.builder()
                                        .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                        .setRedirect(
                                                PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                        .setUrl("https://example.com")
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();

        PaymentLink paymentLink = PaymentLink.create(LinkParams);

        return paymentLink.getUrl();
    }


    @Override
    public String makePaymentForMultiple(String completeOrderId, Long totalAmount, List<PaymentRequestDtoForMultiply.Item> items) throws StripeException {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Items list cannot be null or empty.");
        }

        if (completeOrderId == null || completeOrderId.isEmpty()) {
            throw new IllegalArgumentException("CompleteOrderId cannot be null or empty.");
        }

        Stripe.apiKey = "sk_test_51QfzTvGDrr2Oa02h5VodG4Ak9dEknFCymxo5B0UkSs0lqhfdsQIXQAejW79PvOvbtOhi8OtlY4VBkLXLlEIqCdf800IT74nTc7";

        PaymentLinkCreateParams.Builder linkParamsBuilder = PaymentLinkCreateParams.builder();

        for (PaymentRequestDtoForMultiply.Item item : items) {
            if (item.getAmount() <= 0) {
                throw new IllegalArgumentException("Item amount must be greater than 0.");
            }

            PriceCreateParams priceParams = PriceCreateParams.builder()
                    .setCurrency("usd")
                    .setUnitAmount(item.getAmount()*100)
                    .setProductData(
                            PriceCreateParams.ProductData.builder()
                                    .setName(item.getOrderId())
                                    .build()
                    )
                    .build();

            Price price = Price.create(priceParams);

            linkParamsBuilder.addLineItem(
                    PaymentLinkCreateParams.LineItem.builder()
                            .setPrice(price.getId())
                            .setQuantity(item.getQuantity())
                            .build()
            );
        }

        linkParamsBuilder.setAfterCompletion(
                PaymentLinkCreateParams.AfterCompletion.builder()
                        .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                        .setRedirect(
                                PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                        .setUrl("https://example.com")
                                        .build()
                        )
                        .build()
        );

        PaymentLink paymentLink = PaymentLink.create(linkParamsBuilder.build());

        return paymentLink.getUrl();
    }

}
