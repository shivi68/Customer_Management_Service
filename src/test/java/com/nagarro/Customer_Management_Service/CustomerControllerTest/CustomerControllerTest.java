package com.nagarro.Customer_Management_Service.CustomerControllerTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.nagarro.Customer_Management_Service.controller.CustomerController;
import com.nagarro.Customer_Management_Service.models.Customer;
import com.nagarro.Customer_Management_Service.services.CustomerService;

public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetMessage() {
        String message = customerController.getMessage();
        assertThat(message).isEqualTo("Welcome to Customer Managament Service!!");
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1L, "John", "Doe", "Male", "john.doe@example.com", "1234567890", "Address"));

        when(customerService.getAllCustomers()).thenReturn(customers);

        List<Customer> response = customerController.getAllCustomers();

        assertThat(response).isEqualTo(customers);
    }

    @Test
    void testGetCustomerByIdFound() {
        Long id = 1L;
        Customer customer = new Customer(id, "John", "Doe", "Male", "john.doe@example.com", "1234567890", "Address");

        when(customerService.getCustomerById(id)).thenReturn(Optional.of(customer));

        ResponseEntity<?> response = customerController.getCustomerById(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(customer);
    }

    @Test
    void testGetCustomerByIdNotFound() {
        Long id = 1L;

        when(customerService.getCustomerById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = customerController.getCustomerById(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Customer not found with id: " + id);
    }

    @Test
    void testAddCustomer() {
        Customer customerToAdd = new Customer(null, "Jane", "Doe", "Female", "jane.doe@example.com", "0987654321", "New Address");
        Customer addedCustomer = new Customer(1L, "Jane", "Doe", "Female", "jane.doe@example.com", "0987654321", "New Address");

        when(customerService.addCustomer(customerToAdd)).thenReturn(addedCustomer);

        ResponseEntity<Customer> response = customerController.addCustomer(customerToAdd);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(addedCustomer);
    }

    @Test
    void testUpdateCustomer() {
        Long id = 1L;
        Customer updatedCustomer = new Customer(id, "Jane", "Doe", "Female", "jane.doe@example.com", "0987654321", "New Address");

        when(customerService.updateCustomer(id, updatedCustomer)).thenReturn(updatedCustomer);

        ResponseEntity<?> response = customerController.updateCustomer(id, updatedCustomer);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedCustomer);
    }

    @Test
    void testDeleteCustomer_AccountNotFound() {
        // Arrange
        Long id = 1L;
        Long accountId = 1L;

        // Mock the service method to throw an exception when account is not found
        doThrow(new RuntimeException("Account with ID " + accountId + " not found")).when(customerService).deleteCustomer(id, accountId);

        // Act
        ResponseEntity<String> response = customerController.deleteCustomer(id, accountId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("Account with ID " + accountId + " not found");
    }

    
    
}
