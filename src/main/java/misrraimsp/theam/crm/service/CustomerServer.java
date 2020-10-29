package misrraimsp.theam.crm.service;

import lombok.RequiredArgsConstructor;
import misrraimsp.theam.crm.data.CustomerRepository;
import misrraimsp.theam.crm.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;

@RequiredArgsConstructor
@Service
public class CustomerServer implements Server<Customer> {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final CustomerRepository customerRepository;

    @Override
    public Customer[] findAll() {
        return customerRepository.findAll().toArray(new Customer[0]);
    }

    @Override
    public Customer findById(String id) {
        return customerRepository.findById(id).orElseThrow(() ->
                new HttpClientErrorException(
                        HttpStatus.NOT_FOUND,
                        "Not Found",
                        String.format(
                                "{\"errorMessage\": \"Entity of class %s not found by id=%s\"}",
                                Customer.class.getSimpleName(),
                                id
                        ).getBytes(),
                        Charset.defaultCharset()
                )
        );

    }

    @Override
    public Customer create(Customer newCustomer) {
        Customer createdCustomer = customerRepository.save(newCustomer);
        LOGGER.info("Customer (id={}) created", createdCustomer.getId());
        return createdCustomer;
    }

    @Override
    public Customer edit(Customer newCustomerInfo) {
        Customer customer = this.findById(newCustomerInfo.getId());
        if (newCustomerInfo.getName() != null) customer.setName(newCustomerInfo.getName());
        if (newCustomerInfo.getSurname() != null) customer.setSurname(newCustomerInfo.getSurname());
        LOGGER.info("Customer (id={}) updated", newCustomerInfo.getId());
        return customerRepository.save(customer);
    }

    @Override
    public void delete(String id) {
        customerRepository.delete(this.findById(id));
    }
}
