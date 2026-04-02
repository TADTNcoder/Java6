package com.lab7.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductRequest(
        String id,
        String name,
        BigDecimal price,
        LocalDate date,
        String categoryId
) {
}
