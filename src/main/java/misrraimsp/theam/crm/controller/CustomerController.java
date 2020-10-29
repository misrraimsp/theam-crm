package misrraimsp.theam.crm.controller;

import lombok.RequiredArgsConstructor;
import misrraimsp.theam.crm.model.Customer;
import misrraimsp.theam.crm.service.Server;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class CustomerController {

    private final Server<Customer> customerServer;

    @GetMapping("/customers")
    public Customer[] getAllCustomers() {
        return customerServer.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable String id) {
        return customerServer.findById(id);
    }

    @PostMapping("/customers")
    public Customer newCustomer(@RequestBody @Valid Customer customer) {
        return customerServer.create(customer);
    }

    @PutMapping("/customers")
    public Customer editCustomer(@RequestBody Customer customer) {
        return customerServer.edit(customer);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable String id) {
        customerServer.delete(id);
    }
}
