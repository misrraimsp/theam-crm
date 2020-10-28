package misrraimsp.theam.crm.service;

import lombok.RequiredArgsConstructor;
import misrraimsp.theam.crm.model.dto.CredentialDTO;
import misrraimsp.theam.crm.model.dto.UserDTO;
import org.springframework.http.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserServer implements Server<UserDTO>, OAuthClient {

    private final RestTemplate restTemplate;

    private Jwt jwt;
    String serverUrl = "http://localhost:8080/auth/admin/realms/first-realm/users"; // #remember to externalize for prod
    String defaultPassword = "pass123"; // #remember to externalize for prod

    @Override
    public void setAuthorizationToken(Jwt jwt) {
        this.jwt = jwt;
    }

    @Override
    public UserDTO[] findAll() {
        return restTemplate.exchange(
                serverUrl,
                HttpMethod.GET,
                new HttpEntity<>(this.buildHeaders()),
                UserDTO[].class
        ).getBody();
    }

    @Override
    public UserDTO findById(String id) {
        return null;
    }

    @Override
    public UserDTO create(UserDTO newUser) {
        this.setDefaults(newUser);

        return restTemplate.exchange(
                serverUrl,
                HttpMethod.POST,
                new HttpEntity<>(newUser,this.buildHeaders()),
                UserDTO.class
        ).getBody();
    }

    @Override
    public UserDTO edit(UserDTO object) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwt.getTokenValue());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    private void setDefaults(UserDTO u) {
        if (u.getCredentials() == null || u.getCredentials().isEmpty()) {
            CredentialDTO credential = new CredentialDTO();
            credential.setTemporary(true);
            credential.setValue(defaultPassword);
            u.setCredentials(Collections.singletonList(credential));
        }
        u.setEnabled(true);
    }

}
