package com.udea.lab12026p.mapper;

import com.udea.lab12026p.dto.CustomerDTO;
import com.udea.lab12026p.dto.TransactionDTO;
import com.udea.lab12026p.entity.Customer;
import com.udea.lab12026p.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {
    @Test void customerMapperConvertsEntityToDtoAndBack() {
        CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);
        Customer customer = new Customer(1L, "123", "Ada", "Lovelace", 50.0);
        CustomerDTO dto = mapper.toDTO(customer);
        assertEquals(1L, dto.getId());
        assertEquals("123", dto.getAccountNumber());
        assertEquals("Ada", dto.getFirstName());
        assertEquals("Lovelace", dto.getLastName());
        assertEquals(50.0, dto.getBalance());

        Customer mapped = mapper.toEntity(dto);
        assertEquals(dto.getId(), mapped.getId());
        assertEquals(dto.getAccountNumber(), mapped.getAccountNumber());
        assertEquals(dto.getFirstName(), mapped.getFirstName());
        assertEquals(dto.getLastName(), mapped.getLastName());
        assertEquals(dto.getBalance(), mapped.getBalance());
    }

    @Test void customerMapperReturnsNullForNullInput() {
        CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);
        assertNull(mapper.toDTO(null));
        assertNull(mapper.toEntity(null));
    }

    @Test void transactionMapperConvertsEntityToDto() {
        TransactionMapper mapper = Mappers.getMapper(TransactionMapper.class);
        LocalDateTime time = LocalDateTime.of(2026, 2, 3, 4, 5);
        Transaction transaction = new Transaction(4L, "100", "200", 12.0, time);
        TransactionDTO dto = mapper.toDTO(transaction);
        assertEquals(4L, dto.getId());
        assertEquals("100", dto.getSenderAccountNumber());
        assertEquals("200", dto.getReceiverAccountNumber());
        assertEquals(12.0, dto.getAmount());
        assertEquals(time, dto.getTimestamp());
    }

    @Test void transactionMapperReturnsNullForNullInput() {
        assertNull(Mappers.getMapper(TransactionMapper.class).toDTO(null));
    }
}
