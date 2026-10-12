package com.udea.lab12026p.dto;

import com.udea.lab12026p.entity.Customer;
import com.udea.lab12026p.entity.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DtoAndEntityTest {
    @Test void customerDtoGettersAndSetters() {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(1L); dto.setFirstName("Ada"); dto.setLastName("Lovelace");
        dto.setAccountNumber("123"); dto.setBalance(10.0);
        assertEquals(1L, dto.getId());
        assertEquals("Ada", dto.getFirstName());
        assertEquals("Lovelace", dto.getLastName());
        assertEquals("123", dto.getAccountNumber());
        assertEquals(10.0, dto.getBalance());
        assertNotNull(new CustomerDTO());
        assertNotNull(new CustomerDTO(1L, "Ada", "Lovelace", "123", 10.0));
    }

    @Test void transactionDtoConstructorsAndAccessors() {
        LocalDateTime time = LocalDateTime.of(2026, 1, 2, 3, 4);
        TransactionDTO dto = new TransactionDTO(2L, "111", "222", 3.5, time);
        assertEquals(2L, dto.getId());
        assertEquals("111", dto.getSenderAccountNumber());
        assertEquals("222", dto.getReceiverAccountNumber());
        assertEquals(3.5, dto.getAmount());
        assertEquals(time, dto.getTimestamp());
        TransactionDTO empty = new TransactionDTO();
        assertNotNull(empty.getTimestamp());
        empty.setId(3L); empty.setSenderAccountNumber("a");
        empty.setReceiverAccountNumber("b"); empty.setAmount(1.0); empty.setTimestamp(time);
        assertEquals(3L, empty.getId());
        assertEquals("a", empty.getSenderAccountNumber());
        assertEquals("b", empty.getReceiverAccountNumber());
        assertEquals(1.0, empty.getAmount());
        assertEquals(time, empty.getTimestamp());
    }

    @Test void transferRequestDtoAccessors() {
        TransferRequestDTO dto = new TransferRequestDTO();
        dto.setSenderAccountNumber("111");
        dto.setReceiverAccountNumber("222");
        dto.setAmount(9.0);
        assertEquals("111", dto.getSenderAccountNumber());
        assertEquals("222", dto.getReceiverAccountNumber());
        assertEquals(9.0, dto.getAmount());
    }

    @Test void customerEntityConstructorsAndAccessors() {
        Customer empty = new Customer();
        assertNull(empty.getId());
        Customer c = new Customer(1L, "123", "Ada", "Lovelace", 12.0);
        assertEquals(1L, c.getId()); assertEquals("123", c.getAccountNumber());
        assertEquals("Ada", c.getFirstName()); assertEquals("Lovelace", c.getLastName());
        assertEquals(12.0, c.getBalance());
        c.setId(2L); c.setAccountNumber("456"); c.setFirstName("Grace");
        c.setLastName("Hopper"); c.setBalance(15.0);
        assertEquals(2L, c.getId()); assertEquals("456", c.getAccountNumber());
        assertEquals("Grace", c.getFirstName()); assertEquals("Hopper", c.getLastName());
        assertEquals(15.0, c.getBalance());
    }

    @Test void transactionEntityConstructorsAndAccessors() {
        LocalDateTime time = LocalDateTime.of(2026, 5, 1, 9, 0);
        Transaction empty = new Transaction();
        assertNotNull(empty.getTimestamp());
        Transaction t = new Transaction(1L, "111", "222", 4.0, time);
        assertEquals(1L, t.getId()); assertEquals("111", t.getSenderAccountNumber());
        assertEquals("222", t.getReceiverAccountNumber()); assertEquals(4.0, t.getAmount());
        assertEquals(time, t.getTimestamp());
        t.setId(2L); t.setSenderAccountNumber("a"); t.setReceiverAccountNumber("b");
        t.setAmount(5.0); t.setTimestamp(time);
        assertEquals(2L, t.getId()); assertEquals("a", t.getSenderAccountNumber());
        assertEquals("b", t.getReceiverAccountNumber()); assertEquals(5.0, t.getAmount());
        assertEquals(time, t.getTimestamp());
    }
}
