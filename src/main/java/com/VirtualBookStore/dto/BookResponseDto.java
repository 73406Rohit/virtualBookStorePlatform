package com.VirtualBookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private Double price;
    private Integer stock;
    private Double rating;
}
