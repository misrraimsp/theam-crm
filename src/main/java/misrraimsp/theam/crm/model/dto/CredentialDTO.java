package misrraimsp.theam.crm.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CredentialDTO extends BasicDTO {

    private final String type = "password";

    @Pattern(regexp = PASSWORD)
    private String value;

    private Boolean temporary;
}
