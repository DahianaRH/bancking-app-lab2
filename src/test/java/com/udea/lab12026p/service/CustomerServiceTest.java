package com.udea.lab12026p.service;

import com.udea.lab12026p.dto.CustomerDTO;
import com.udea.lab12026p.entity.Customer;
import com.udea.lab12026p.mapper.CustomerMapper;
import com.udea.lab12026p.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock CustomerRepository repository;
    private CustomerService service;
    private final CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);

    @BeforeEach void setUp() { service = new CustomerService(repository, mapper); }

    @Test void getAllCustomersMapsRepositoryResults() {
        when(repository.findAll()).thenReturn(List.of(customer(1L, "100")));
        List<CustomerDTO> result = service.getAllCustomers();
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("100", result.get(0).getAccountNumber());
        assertEquals("Ada", result.get(0).getFirstName());
    }

    @Test void getAllCustomersReturnsEmptyList() {
        when(repository.findAll()).thenReturn(List.of());
        assertTrue(service.getAllCustomers().isEmpty());
    }

    @Test void getCustomerByIdReturnsMappedCustomer() {
        when(repository.findById(1L)).thenReturn(Optional.of(customer(1L, "100")));
        CustomerDTO result = service.getCustomerById(1L);
        assertEquals(1L, result.getId());
        assertEquals("100", result.getAccountNumber());
        assertEquals(80.0, result.getBalance());
    }

    @Test void getCustomerByIdThrowsWhenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertEquals("Cliente no encontrado",
                assertThrows(RuntimeException.class, () -> service.getCustomerById(99L)).getMessage());
    }

    @Test void createCustomerMapsSavesAndReturnsSavedCustomer() {
        CustomerDTO input = new CustomerDTO();
        input.setAccountNumber("300");
        input.setFirstName("Grace");
        input.setLastName("Hopper");
        input.setBalance(25.0);
        when(repository.save(org.mockito.ArgumentMatchers.any(Customer.class)))
                .thenAnswer(inv -> {
                    Customer c = inv.getArgument(0);
                    c.setId(5L);
                    return c;
                });

        CustomerDTO result = service.createCustomer(input);
        assertEquals(5L, result.getId());
        assertEquals("300", result.getAccountNumber());
        assertEquals("Grace", result.getFirstName());
        assertEquals("Hopper", result.getLastName());
        assertEquals(25.0, result.getBalance());
        verify(repository).save(org.mockito.ArgumentMatchers.any(Customer.class));
    }

    private Customer customer(Long id, String account) {
        return new Customer(id, account, "Ada", "Lovelace", 80.0);
    }
}
