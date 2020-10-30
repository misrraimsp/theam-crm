package misrraimsp.theam.crm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Image extends BasicEntity {

    @Lob //h2
    @Size(message = "Too large image", max = IMAGE_MAX_BYTES)
    private byte[] data;

    @Pattern(regexp = IMAGE_NAME)
    private String name;

    @Pattern(regexp = IMAGE_MIME_TYPE)
    private String mimeType;

}
