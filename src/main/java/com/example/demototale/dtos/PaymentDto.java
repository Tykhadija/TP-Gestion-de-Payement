package com.example.demototale.dtos;

import com.example.demototale.entities.PaymentType;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class PaymentDto {
    private double amount;
    private PaymentType type;
    private LocalDate date;
    private String code;
}
