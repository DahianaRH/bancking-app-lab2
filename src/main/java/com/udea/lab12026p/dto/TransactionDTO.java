package com.udea.lab12026p.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {

    private Long id;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private Double amount;
    private LocalDateTime timestamp = LocalDateTime.now();

    public TransactionDTO(Long id, String senderAccountNumber, String receiverAccountNumber, Double amount, LocalDateTime timestamp) {
        this.id = id;
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.amount = amount;
        this.timestamp = timestamp;

    }
}
