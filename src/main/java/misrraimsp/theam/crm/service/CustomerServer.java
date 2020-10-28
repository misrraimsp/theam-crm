package misrraimsp.theam.crm.service;

import lombok.RequiredArgsConstructor;
import misrraimsp.theam.crm.data.CustomerRepository;
import misrraimsp.theam.crm.model.Customer;
import misrraimsp.theam.crm.util.EntityNotFoundByIdException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerServer implements Server<Customer> {

    private final CustomerRepository customerRepository;

    @Override
    public Customer[] findAll() {
        return customerRepository.findAll().toArray(new Customer[0]);
    }

    @Override
    public Customer findById(String id) throws EntityNotFoundByIdException {
        return customerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundByIdException(id, Customer.class.getSimpleName())
        );

    }

    @Override
    public Customer create(Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }

    @Override
    public Customer edit(Customer newCustomerInfo) throws EntityNotFoundByIdException {
        Customer customer = this.findById(newCustomerInfo.getId());
        if (newCustomerInfo.getName() != null) customer.setName(newCustomerInfo.getName());
        if (newCustomerInfo.getSurname() != null) customer.setSurname(newCustomerInfo.getSurname());
        return customerRepository.save(customer);
    }

    @Override
    public void delete(String id) throws EntityNotFoundByIdException {
        customerRepository.delete(this.findById(id));
    }
}
