package misrraimsp.theam.crm.controller;

import lombok.RequiredArgsConstructor;
import misrraimsp.theam.crm.model.Customer;
import misrraimsp.theam.crm.model.Image;
import misrraimsp.theam.crm.service.CustomerServer;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class CustomerController {

    private final CustomerServer customerServer;

    @GetMapping("/customers")
    public Customer[] getAllCustomers() {
        return customerServer.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable String id) {
        return customerServer.findById(id);
    }

    @PostMapping("/customers")
    public Customer newCustomer(@RequestBody Customer customer) {
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


    @GetMapping("/customers/{id}/image")
    public void getCustomerImage(@PathVariable String id,
                                 HttpServletResponse response) throws IOException {

        Image image = customerServer.getCustomerImage(id);
        response.setContentType(image.getMimeType());
        try (InputStream inputStream = new ByteArrayInputStream(image.getData())) {
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    }

    @PostMapping("/customers/{id}/image")
    public ResponseEntity<?> updateCustomerImage(@PathVariable String id,
                                                 @RequestParam Image image) {

        Customer customer = customerServer.updateCustomerImage(id, image);
        return ResponseEntity.created(URI.create(customer.getImageUrl())).body("success");
    }
}
