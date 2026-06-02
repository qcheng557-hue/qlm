package com.qlm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private String location;
    private Double area;
    private BigDecimal budget;
    private BigDecimal spent;
    private Integer progress;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
}
