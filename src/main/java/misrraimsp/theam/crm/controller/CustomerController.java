package misrraimsp.theam.crm.controller;

import lombok.RequiredArgsConstructor;
import misrraimsp.theam.crm.model.Customer;
import misrraimsp.theam.crm.service.CustomerServer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class CustomerController {

    private final CustomerServer customerServer;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerServer.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        return customerServer.findById(id);
    }

    @PostMapping("/customers")
    public Customer newCustomer(@RequestParam String name,
                                @RequestParam String surname) {

        return customerServer.create(name, surname);
    }

    @PutMapping("/customers/{id}")
    public Customer editCustomer(@PathVariable Long id,
                                 @RequestParam(defaultValue = "") String name,
                                 @RequestParam(defaultValue = "") String surname) {

        return customerServer.edit(id, name, surname);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerServer.delete(id);
    }
}
