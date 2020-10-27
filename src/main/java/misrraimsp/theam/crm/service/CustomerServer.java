package misrraimsp.theam.crm.service;

import lombok.RequiredArgsConstructor;
import misrraimsp.theam.crm.data.CustomerRepository;
import misrraimsp.theam.crm.model.Customer;
import misrraimsp.theam.crm.util.EntityNotFoundByIdException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerServer {

    private final CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) throws EntityNotFoundByIdException {
        return customerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundByIdException(id, Customer.class.getSimpleName())
        );

    }

    public Customer create(String name, String surname) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setSurname(surname);
        return customerRepository.save(customer);
    }

    public Customer edit(Long id, String name, String surname) throws EntityNotFoundByIdException {
        Customer customer = this.findById(id);
        if (!name.isBlank()) customer.setName(name);
        if (!surname.isBlank()) customer.setSurname(surname);
        return customerRepository.save(customer);
    }
}
