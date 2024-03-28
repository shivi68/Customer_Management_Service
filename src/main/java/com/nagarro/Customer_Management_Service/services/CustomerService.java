package com.nagarro.Customer_Management_Service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.Customer_Management_Service.models.Customer;
import com.nagarro.Customer_Management_Service.repositiories.CustomerRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;
    
    @Autowired
    private WebClient webClient;

    // Method to get all customers
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    // Method to get a customer by ID
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepo.findById(id);
    }

    // Method to add a new customer
    public Customer addCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    // Method to update an existing customer
    public Customer updateCustomer(Long id, Customer customer) {
        Optional<Customer> existingCustomer = customerRepo.findById(id);

        if (existingCustomer.isPresent()) {
        	existingCustomer.get().setEmail(customer.getEmail());
        	existingCustomer.get().setFirst_name(customer.getFirst_name());
        	existingCustomer.get().setLast_name(customer.getLast_name());
        	existingCustomer.get().setAddress(customer.getAddress());
        	existingCustomer.get().setPhone_number(customer.getPhone_number());

            return customerRepo.save(existingCustomer.get());
        } else {
            // If customer with given ID is not present, throw an exception or return an error message
            throw new RuntimeException("Customer with ID " + id + " not found");
        }
    }

    // Method to delete a customer by ID and account ID
    public void deleteCustomer(Long id, Long accountId) {
    	webClient.delete()
        .uri("http://localhost:8082/account/deleteAccount/" + accountId)
        .retrieve()
        .bodyToMono(Void.class) // Adjust this based on your actual response type
        .block(); // Block to get the result

        customerRepo.deleteById(id);
    }

}
