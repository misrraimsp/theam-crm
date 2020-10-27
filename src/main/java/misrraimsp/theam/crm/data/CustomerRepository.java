package misrraimsp.theam.crm.data;

import misrraimsp.theam.crm.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
