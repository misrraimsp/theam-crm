package misrraimsp.theam.crm.util;

import misrraimsp.theam.crm.data.CustomerRepository;
import misrraimsp.theam.crm.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataLoader_H2 {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Bean
    CommandLineRunner initDatabase(CustomerRepository customerRepository) {

        return args -> {

            Customer c1 = new Customer();
            c1.setName("Misrra");
            c1.setSurname("Suárez");
            customerRepository.save(c1);

            Customer c2 = new Customer();
            c2.setName("Andrea");
            c2.setSurname("Grau");
            customerRepository.save(c2);

            Customer c3 = new Customer();
            c3.setName("Jabel");
            c3.setSurname("Suárez");
            customerRepository.save(c3);

            customerRepository.findAll().forEach(customer -> LOGGER.info("Loaded " + customer.getName()));



        };

    }
}
