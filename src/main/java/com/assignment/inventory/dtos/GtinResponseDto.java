package com.assignment.inventory.dtos;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GtinResponseDto {
    private String gtin;
    private List<BatchDto> batches;
}
