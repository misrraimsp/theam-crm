package misrraimsp.theam.crm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Image extends BasicEntity {

    @Lob //h2
    private byte[] data;

    private String name;

    private String mimeType;

}
