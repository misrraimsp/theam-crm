package misrraimsp.theam.crm.service;

import lombok.RequiredArgsConstructor;
import misrraimsp.theam.crm.data.CustomerRepository;
import misrraimsp.theam.crm.model.Customer;
import misrraimsp.theam.crm.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;

@RequiredArgsConstructor
@Service
public class CustomerServer {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final CustomerRepository customerRepository;
    private final ImageServer imageServer;

    public Customer[] findAll() {
        return customerRepository.findAll().toArray(new Customer[0]);
    }

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

    public Customer create(Customer newCustomer) {
        Customer createdCustomer = customerRepository.save(newCustomer);
        LOGGER.info("Customer (id={}) created", createdCustomer.getId());
        return createdCustomer;
    }

    public Customer edit(Customer newCustomerInfo) {
        Customer customer = this.findById(newCustomerInfo.getId());
        if (newCustomerInfo.getName() != null) customer.setName(newCustomerInfo.getName());
        if (newCustomerInfo.getSurname() != null) customer.setSurname(newCustomerInfo.getSurname());
        LOGGER.info("Customer (id={}) updated", newCustomerInfo.getId());
        return customerRepository.save(customer);
    }

    public void delete(String id) {
        Customer customer = this.findById(id);
        String imageId = customer.getImageId();
        customerRepository.delete(customer);
        imageServer.delete(imageId);
        LOGGER.info("Customer (id={}) deleted", id);

    }

    public Image getCustomerImage(String customerId) {
        Customer customer = this.findById(customerId);
        return imageServer.findById(customer.getImageId());
    }

    public Customer updateCustomerImage(String customerId, Image imageInfo) {
        Customer customer = this.findById(customerId);
        String prevImageId = customer.getImageId();
        Image newImage = imageServer.create(imageInfo);
        customer.setImage(newImage);
        Customer editedCustomer = this.edit(customer);
        if (prevImageId != null) {
            imageServer.delete(prevImageId);
        }
        LOGGER.info("Customer (id={}) image (id={}) updated", customerId, newImage.getId());
        return editedCustomer;
    }
}
