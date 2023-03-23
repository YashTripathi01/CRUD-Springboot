package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {
    private final CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("{customerId}")
    public Customer getCustomerById(@PathVariable("customerId") Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);

        return customer.get();
    }

    record NewCustomer(String name, String email, Integer age) {}

    @PostMapping
    public void addCustomer(@RequestBody NewCustomer request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());

        customerRepository.save(customer);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer id, @RequestBody NewCustomer request) {
        Customer customer = getCustomerById(id);

        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());

        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id) {
        // customerRepository.deleteById(id);
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
    }
}
