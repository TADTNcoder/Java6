package com.lab7.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductResponse(
        String id,
        String name,
        BigDecimal price,
        LocalDate date,
        String categoryId
) {
}
