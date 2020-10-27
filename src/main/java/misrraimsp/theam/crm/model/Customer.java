package misrraimsp.theam.crm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Customer extends BasicEntity {

    private String name;
    private String surname;
}
