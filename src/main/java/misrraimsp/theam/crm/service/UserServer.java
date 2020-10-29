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
public class UserServer {

    private final RestTemplate restTemplate;

    @Value("${crm.oauth2.users.server-uri}")
    private String serverUrl;

    @Value("${crm.oauth2.users.default-pw}")
    private String defaultPassword;

    public UserDTO[] findAll(Jwt jwt) {
        return restTemplate.exchange(
                serverUrl,
                HttpMethod.GET,
                new HttpEntity<>(this.buildHeaders(jwt)),
                UserDTO[].class
        ).getBody();
    }

    public UserDTO findById(String id, Jwt jwt) {
        return restTemplate.exchange(
                serverUrl + "/{id}",
                HttpMethod.GET,
                new HttpEntity<>(this.buildHeaders(jwt)),
                UserDTO.class,
                id
        ).getBody();
    }

    public UserDTO create(UserDTO newUser, Jwt jwt) {
        this.setDefaultsCredentials(newUser);

        return restTemplate.exchange(
                serverUrl,
                HttpMethod.POST,
                new HttpEntity<>(newUser,this.buildHeaders(jwt)),
                UserDTO.class
        ).getBody();
    }

    public UserDTO edit(UserDTO userDTOInfo, Jwt jwt) {
        return restTemplate.exchange(
                serverUrl + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(userDTOInfo,this.buildHeaders(jwt)),
                UserDTO.class,
                userDTOInfo.getId()
        ).getBody();
    }

    public void delete(String id, Jwt jwt) {
        restTemplate.exchange(
                serverUrl + "/{id}",
                HttpMethod.DELETE,
                new HttpEntity<>(this.buildHeaders(jwt)),
                UserDTO.class,
                id
        );
    }

    private HttpHeaders buildHeaders(Jwt jwt) {
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
