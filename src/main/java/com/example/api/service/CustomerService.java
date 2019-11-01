package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;

@Service
public class CustomerService {

	private CustomerRepository repository;

	@Autowired
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public Page<Customer> findAll(int page) {
		PageRequest pageRequest = PageRequest.of(page, 2);
		return repository.findAllByOrderByNameAsc(pageRequest);
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}

	public Customer save(Customer customer){
		return repository.save(customer);
	}

	public void deleteCustomer(Long id){
		Optional<Customer> customer = repository.findById(id);
		repository.delete(customer.get());
	}

}
