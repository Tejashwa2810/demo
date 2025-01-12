package com.example.springap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PaymentRequestDtoForMultiply {
    @JsonProperty("CompleteOrderId") // Map JSON field explicitly
    private String completeOrderId;

    private List<Item> items;

    public String getCompleteOrderId() {
        return completeOrderId;
    }

    public void setCompleteOrderId(String completeOrderId) {
        this.completeOrderId = completeOrderId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item {
        private String orderId;
        private Long amount;
        private Long quantity;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        public Long getQuantity() {
            return quantity;
        }

        public void setQuantity(Long quantity) {
            this.quantity = quantity;
        }
    }
}
