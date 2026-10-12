package com.udea.lab12026p.service;

import com.udea.lab12026p.dto.TransactionDTO;
import com.udea.lab12026p.entity.Customer;
import com.udea.lab12026p.entity.Transaction;
import com.udea.lab12026p.repository.CustomerRepository;
import com.udea.lab12026p.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @Mock TransactionRepository transactionRepository;
    @Mock CustomerRepository customerRepository;
    private TransactionService service;

    @BeforeEach void setUp() {
        service = new TransactionService(transactionRepository, customerRepository);
    }

    @Test void transferRejectsNullSenderOrReceiverAccount() {
        TransactionDTO dto = new TransactionDTO();
        dto.setSenderAccountNumber(null);
        dto.setReceiverAccountNumber("200");
        assertEquals("Los números de cuenta del remitente y receptor son obligatorios.",
                assertThrows(IllegalArgumentException.class, () -> service.transferMoney(dto)).getMessage());
        verifyNoInteractions(customerRepository, transactionRepository);
    }

    @Test void transferRejectsMissingSender() {
        TransactionDTO dto = transfer("100", "200", 10.0);
        when(customerRepository.findByAccountNumber("100")).thenReturn(Optional.empty());
        assertEquals("La cuenta del remitente no existe.",
                assertThrows(IllegalArgumentException.class, () -> service.transferMoney(dto)).getMessage());
        verify(customerRepository, never()).save(any());
        verifyNoInteractions(transactionRepository);
    }

    @Test void transferRejectsMissingReceiver() {
        TransactionDTO dto = transfer("100", "200", 10.0);
        when(customerRepository.findByAccountNumber("100")).thenReturn(Optional.of(customer("100", 50.0)));
        when(customerRepository.findByAccountNumber("200")).thenReturn(Optional.empty());
        assertEquals("La cuenta del receptor no existe.",
                assertThrows(IllegalArgumentException.class, () -> service.transferMoney(dto)).getMessage());
        verify(customerRepository, never()).save(any());
        verifyNoInteractions(transactionRepository);
    }

    @Test void transferRejectsInsufficientBalance() {
        TransactionDTO dto = transfer("100", "200", 60.0);
        when(customerRepository.findByAccountNumber("100")).thenReturn(Optional.of(customer("100", 50.0)));
        when(customerRepository.findByAccountNumber("200")).thenReturn(Optional.of(customer("200", 20.0)));
        assertEquals("Saldo insuficiente en la cuenta del remitente.",
                assertThrows(IllegalArgumentException.class, () -> service.transferMoney(dto)).getMessage());
        verify(customerRepository, never()).save(any());
        verifyNoInteractions(transactionRepository);
    }

    @Test void transferUpdatesBalancesSavesTransactionAndReturnsDto() {
        LocalDateTime time = LocalDateTime.of(2026, 10, 11, 12, 0);
        TransactionDTO dto = transfer("100", "200", 15.0);
        dto.setTimestamp(time);
        Customer sender = customer("100", 50.0);
        Customer receiver = customer("200", 20.0);
        when(customerRepository.findByAccountNumber("100")).thenReturn(Optional.of(sender));
        when(customerRepository.findByAccountNumber("200")).thenReturn(Optional.of(receiver));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(inv -> {
            Transaction t = inv.getArgument(0);
            t.setId(7L);
            return t;
        });

        TransactionDTO result = service.transferMoney(dto);

        assertEquals(35.0, sender.getBalance());
        assertEquals(35.0, receiver.getBalance());
        verify(customerRepository).save(sender);
        verify(customerRepository).save(receiver);
        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).save(captor.capture());
        Transaction saved = captor.getValue();
        assertEquals("100", saved.getSenderAccountNumber());
        assertEquals("200", saved.getReceiverAccountNumber());
        assertEquals(15.0, saved.getAmount());
        assertEquals(time, saved.getTimestamp());
        assertEquals(7L, result.getId());
        assertEquals("100", result.getSenderAccountNumber());
        assertEquals("200", result.getReceiverAccountNumber());
        assertEquals(15.0, result.getAmount());
        assertEquals(time, result.getTimestamp());
    }

    @Test void getTransactionsForAccountMapsAllFields() {
        LocalDateTime time = LocalDateTime.of(2026, 1, 1, 10, 30);
        Transaction t = new Transaction(3L, "100", "200", 12.5, time);
        when(transactionRepository.findBySenderAccountNumberOrReceiverAccountNumber("100", "100"))
                .thenReturn(List.of(t));

        List<TransactionDTO> result = service.getTransactionsForAccount("100");

        assertEquals(1, result.size());
        TransactionDTO dto = result.get(0);
        assertEquals(3L, dto.getId());
        assertEquals("100", dto.getSenderAccountNumber());
        assertEquals("200", dto.getReceiverAccountNumber());
        assertEquals(12.5, dto.getAmount());
        assertEquals(time, dto.getTimestamp());
    }

    @Test void getTransactionsForAccountReturnsEmptyListWhenNoTransactions() {
        when(transactionRepository.findBySenderAccountNumberOrReceiverAccountNumber("404", "404"))
                .thenReturn(List.of());
        assertTrue(service.getTransactionsForAccount("404").isEmpty());
    }

    private static TransactionDTO transfer(String sender, String receiver, Double amount) {
        TransactionDTO dto = new TransactionDTO();
        dto.setSenderAccountNumber(sender);
        dto.setReceiverAccountNumber(receiver);
        dto.setAmount(amount);
        return dto;
    }

    private static Customer customer(String account, Double balance) {
        return new Customer(null, account, "Test", "User", balance);
    }
}
