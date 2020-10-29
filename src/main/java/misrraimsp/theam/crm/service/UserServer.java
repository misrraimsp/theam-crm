package misrraimsp.theam.crm.service;

import lombok.RequiredArgsConstructor;
import misrraimsp.theam.crm.model.dto.CredentialDTO;
import misrraimsp.theam.crm.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${crm.oauth2.users.server-uri}")
    private String serverUrl;

    @Value("${crm.oauth2.users.default-pw}")
    private String defaultPassword;

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
        return restTemplate.exchange(
                serverUrl + "/{id}",
                HttpMethod.GET,
                new HttpEntity<>(this.buildHeaders()),
                UserDTO.class,
                id
        ).getBody();
    }

    @Override
    public UserDTO create(UserDTO newUser) {
        this.setDefaultsCredentials(newUser);

        return restTemplate.exchange(
                serverUrl,
                HttpMethod.POST,
                new HttpEntity<>(newUser,this.buildHeaders()),
                UserDTO.class
        ).getBody();
    }

    @Override
    public UserDTO edit(UserDTO userDTOInfo) {
        return restTemplate.exchange(
                serverUrl + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(userDTOInfo,this.buildHeaders()),
                UserDTO.class,
                userDTOInfo.getId()
        ).getBody();
    }

    @Override
    public void delete(String id) {
        restTemplate.exchange(
                serverUrl + "/{id}",
                HttpMethod.DELETE,
                new HttpEntity<>(this.buildHeaders()),
                UserDTO.class,
                id
        );
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwt.getTokenValue());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    private void setDefaultsCredentials(UserDTO u) {
        if (u.getCredentials() == null || u.getCredentials().isEmpty()) {
            CredentialDTO credential = new CredentialDTO();
            credential.setTemporary(true);
            credential.setValue(defaultPassword);
            u.setCredentials(Collections.singletonList(credential));
        }
    }

}
