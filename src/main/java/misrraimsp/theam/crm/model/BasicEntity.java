package misrraimsp.theam.crm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import misrraimsp.theam.crm.util.ValidationParameters;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@MappedSuperclass
public class BasicEntity implements ValidationParameters {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @EqualsAndHashCode.Include // BasicEntity's equality is defined through their Id's equality
    protected String id;
}
