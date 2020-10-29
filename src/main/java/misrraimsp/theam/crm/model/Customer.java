package misrraimsp.theam.crm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Customer extends BasicEntity {

    @NotBlank(message = "Customer's Name needs not to be empty")
    private String name;

    @NotBlank(message = "Customer's Surname needs not to be empty")
    private String surname;
}
