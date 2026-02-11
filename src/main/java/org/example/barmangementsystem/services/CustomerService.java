package org.example.barmangementsystem.services;




import org.example.barmangementsystem.entity.Customer;
import org.example.barmangementsystem.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer update(Long id, Customer updated) {
        return customerRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setPhone(updated.getPhone());
                    return customerRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
}
