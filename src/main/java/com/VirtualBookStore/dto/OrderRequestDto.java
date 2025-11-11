package com.VirtualBookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {
    private List<Item> cartItems;  // ✅ Main list of cart items
    private String paymentType;    // ✅ Payment type (COD, CARD, etc.)

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Item {
        private Long bookId;       // ✅ Book ID
        private Integer quantity;  // ✅ Quantity
    }
}