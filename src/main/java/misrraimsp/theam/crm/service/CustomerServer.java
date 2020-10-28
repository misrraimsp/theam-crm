package misrraimsp.theam.crm.service;

import lombok.RequiredArgsConstructor;
import misrraimsp.theam.crm.data.CustomerRepository;
import misrraimsp.theam.crm.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;

@RequiredArgsConstructor
@Service
public class CustomerServer implements Server<Customer> {

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
        return customerRepository.save(newCustomer);
    }

    @Override
    public Customer edit(Customer newCustomerInfo) {
        Customer customer = this.findById(newCustomerInfo.getId());
        if (newCustomerInfo.getName() != null) customer.setName(newCustomerInfo.getName());
        if (newCustomerInfo.getSurname() != null) customer.setSurname(newCustomerInfo.getSurname());
        return customerRepository.save(customer);
    }

    @Override
    public void delete(String id) {
        customerRepository.delete(this.findById(id));
    }
}
