package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerRepository repository;

    @GetMapping(path = "/")
    public String index() {
        return "external";
    }

    @GetMapping(path = "/customers")
    public String customers(Model model) {
        addCustomers();
        model.addAttribute("customers", repository.findAll());
        return "customers";
    }

    // add customers for demonstration
    public void addCustomers() {
        Customer customer1 = new Customer();
        customer1.setAddress("1111 foo blvd");
        customer1.setName("Foo Industries");
        customer1.setServiceRendered("Important services");
        repository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setAddress("2222 bar street");
        customer2.setName("Bar LLP");
        customer2.setServiceRendered("Important services");
        repository.save(customer2);

        Customer customer3 = new Customer();
        customer3.setAddress("33 main street");
        customer3.setName("Big LLC");
        customer3.setServiceRendered("Important services");
        repository.save(customer3);
    }
}