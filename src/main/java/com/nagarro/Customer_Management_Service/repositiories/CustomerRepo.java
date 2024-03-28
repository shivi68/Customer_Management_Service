package com.nagarro.Customer_Management_Service.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.Customer_Management_Service.models.Customer;


@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
	// Method to check if a customer exists by email
	boolean existsByEmail(String email);
	
}