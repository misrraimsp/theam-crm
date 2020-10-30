package misrraimsp.theam.crm.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO extends BasicDTO {

    @Pattern(regexp = TEXT_BASIC)
    private String id;

    private Long createdTimestamp;

    @Pattern(regexp = TEXT_BASIC)
    private String username;

    @Pattern(regexp = TEXT_BASIC)
    private String firstName;

    @Pattern(regexp = TEXT_BASIC)
    private String lastName;

    @Pattern(regexp = EMAIL)
    private String email;

    @Valid
    private List<CredentialDTO> credentials;

    private boolean enabled = true;
}
