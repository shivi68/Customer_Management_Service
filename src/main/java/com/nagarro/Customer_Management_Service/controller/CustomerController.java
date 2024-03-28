package com.nagarro.Customer_Management_Service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.Customer_Management_Service.models.Customer;
import com.nagarro.Customer_Management_Service.services.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
		@Autowired
	    private CustomerService customerService;

		 // Endpoint to retrieve all customers
	    @GetMapping
	    public List<Customer> getAllCustomers() {
	        return customerService.getAllCustomers();
	    }

	    // Endpoint to retrieve a customer by ID
	    @GetMapping("/{id}")
	    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
	        Optional<Customer> customer = customerService.getCustomerById(id);
	        
	        if (customer.isPresent()) {
	            return ResponseEntity.ok(customer.get());
	        } else {
	            String errorMessage = "Customer not found with id: " + id;
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	        }
	    }
 
	    // Endpoint to add a new customer
	    @PostMapping
	    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) {
	        Customer addedCustomer = customerService.addCustomer(customer);
	        return ResponseEntity.status(HttpStatus.CREATED).body(addedCustomer);
	    }

	    // Endpoint to update an existing customer
	    @PutMapping("/{id}")
	    public ResponseEntity<?> updateCustomer(@Valid @PathVariable Long id, @RequestBody Customer customer) {
	        try {
	            Customer updatedCustomer = customerService.updateCustomer(id, customer);
	            return ResponseEntity.ok(updatedCustomer);
	        }  catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	        }
	    }

	    // Endpoint to delete a customer by ID and account ID
	    @DeleteMapping("/{id}/{accountId}")
	    public ResponseEntity<String> deleteCustomer(@PathVariable Long id, @PathVariable Long accountId) {
	        try {
	            customerService.deleteCustomer(id, accountId);
	            String successMessage = "Customer details with ID " + id + " and Account ID " + accountId + " deleted successfully";
	            return ResponseEntity.ok(successMessage);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        }
	    }
	    
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public Map<String, String> handleValidationExceptions(
	      MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getAllErrors().forEach((error) -> {
	            String fieldName = ((FieldError) error).getField();
	            String errorMessage = error.getDefaultMessage();
	            errors.put(fieldName, errorMessage);
	        });
	        return errors;
	    }
	}