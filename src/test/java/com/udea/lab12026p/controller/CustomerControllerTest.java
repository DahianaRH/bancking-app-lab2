package com.udea.lab12026p.controller;

import com.udea.lab12026p.dto.CustomerDTO;
import com.udea.lab12026p.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {
    @Test void getAllCustomersReturnsServiceResult() {
        CustomerService service = mock(CustomerService.class);
        List<CustomerDTO> customers = List.of(new CustomerDTO());
        when(service.getAllCustomers()).thenReturn(customers);
        ResponseEntity<List<CustomerDTO>> response = new CustomerController(service).getAllCustomers();
        assertEquals(200, response.getStatusCode().value());
        assertSame(customers, response.getBody());
    }

    @Test void getCustomerByIdReturnsServiceResult() {
        CustomerService service = mock(CustomerService.class);
        CustomerDTO dto = new CustomerDTO();
        when(service.getCustomerById(2L)).thenReturn(dto);
        ResponseEntity<CustomerDTO> response = new CustomerController(service).getCustomerById(2L);
        assertEquals(200, response.getStatusCode().value());
        assertSame(dto, response.getBody());
    }

    @Test void createCustomerReturnsCreatedDtoWhenBalancePresent() {
        CustomerService service = mock(CustomerService.class);
        CustomerDTO input = new CustomerDTO();
        input.setBalance(10.0);
        when(service.createCustomer(input)).thenReturn(input);
        ResponseEntity<CustomerDTO> response = new CustomerController(service).createCustomer(input);
        assertEquals(200, response.getStatusCode().value());
        assertSame(input, response.getBody());
    }

    @Test void createCustomerRejectsNullBalance() {
        CustomerService service = mock(CustomerService.class);
        CustomerDTO input = new CustomerDTO();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new CustomerController(service).createCustomer(input));
        assertEquals("Balance cannot be null", ex.getMessage());
        verifyNoInteractions(service);
    }
}
