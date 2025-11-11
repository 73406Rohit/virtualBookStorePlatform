package com.VirtualBookStore.dto;

import lombok.Data;

@Data
public class CartRequest {
    private Long userId;
    private Long bookId;
    private int quantity;
}
