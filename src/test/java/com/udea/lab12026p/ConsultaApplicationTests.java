package com.udea.lab12026p;

import com.udea.lab12026p.controller.CustomerController;
import com.udea.lab12026p.controller.DataController;
import com.udea.lab12026p.controller.TransactionController;
import com.udea.lab12026p.dto.CustomerDTO;
import com.udea.lab12026p.dto.TransactionDTO;
import com.udea.lab12026p.service.CustomerService;
import com.udea.lab12026p.service.TransactionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {
        DataController.class,
        CustomerController.class,
        TransactionController.class
})
class ConsultaApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private TransactionService transactionService;

    // =========================
    // DATA CONTROLLER
    // =========================

    @Test
    void health() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "BANKING API HEALTH CHECK OK!"
                ));
    }

    @Test
    void version() throws Exception {
        mockMvc.perform(get("/version"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.application")
                        .value("Aplicación Bancaria"))
                .andExpect(jsonPath("$.version").value("1.0.0"))
                .andExpect(jsonPath("$.application").exists())
                .andExpect(jsonPath("$.version").exists());
    }

    // =========================
    // CUSTOMER CONTROLLER
    // =========================

    @Test
    void getAllCustomers() throws Exception {
        when(customerService.getAllCustomers())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON
                ))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getCustomerById() throws Exception {
        when(customerService.getCustomerById(1L))
                .thenReturn(new CustomerDTO());

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON
                ))
                .andExpect(jsonPath("$").isMap());
    }

    @Test
    void createCustomer() throws Exception {
        when(customerService.createCustomer(any(CustomerDTO.class)))
                .thenReturn(new CustomerDTO());

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "balance": 100.0
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON
                ));
    }

    // =========================
    // TRANSACTION CONTROLLER
    // =========================

    @Test
    void getTransactionsByAccount() throws Exception {
        when(transactionService.getTransactionsForAccount("123456"))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/transactions/123456"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON
                ))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void transferMoney() throws Exception {
        when(transactionService.transferMoney(any(TransactionDTO.class)))
                .thenReturn(new TransactionDTO());

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON
                ));
    }

    @Test
    void transferMoneyWithBusinessError() throws Exception {
        when(transactionService.transferMoney(any(TransactionDTO.class)))
                .thenThrow(new IllegalArgumentException(
                        "Saldo insuficiente en la cuenta del remitente."
                ));

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        "Saldo insuficiente en la cuenta del remitente."
                ));
    }
}
