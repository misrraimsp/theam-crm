package misrraimsp.theam.crm.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CredentialDTO {
    private final String type = "password";
    private String value;
    private Boolean temporary;
}
