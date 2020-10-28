package misrraimsp.theam.crm.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    private String id;
    private Long createdTimestamp;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<CredentialDTO> credentials;
    private boolean enabled;
}
