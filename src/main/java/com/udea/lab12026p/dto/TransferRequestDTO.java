package com.udea.lab12026p.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransferRequestDTO {
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private Double amount;
}
