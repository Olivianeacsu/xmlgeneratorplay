package org.example.xmlgeneratorplay.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private BigDecimal amount;
    private String accountNumber;
    private TransactionType type;
    private LocalDate date;
    private Currency currency;
}
