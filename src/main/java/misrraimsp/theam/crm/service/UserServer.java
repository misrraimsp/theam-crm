package misrraimsp.theam.crm.service;

import lombok.RequiredArgsConstructor;
import misrraimsp.theam.crm.util.EntityNotFoundByIdException;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserServer implements Server<UserRepresentation>, OAuthClient {

    private final RestTemplate restTemplate;

    private Jwt jwt;
    String serverUrl = "http://localhost:8080/auth/admin/realms/first-realm/users"; // #remember to externalize for prod
    String defaultPassword = "pass123"; // #remember to externalize for prod

    @Override
    public void setAuthorizationToken(Jwt jwt) {
        this.jwt = jwt;
    }

    @Override
    public UserRepresentation[] findAll() {


        return restTemplate.exchange(
                serverUrl,
                HttpMethod.GET,
                new HttpEntity<>(this.buildHeaders()),
                UserRepresentation[].class
        ).getBody();
    }

    @Override
    public UserRepresentation findById(String id) throws EntityNotFoundByIdException {
        return null;
    }

    @Override
    public UserRepresentation create(UserRepresentation newUser) {
        this.setDefaults(newUser);

        return restTemplate.exchange(
                serverUrl,
                HttpMethod.POST,
                new HttpEntity<>(newUser,this.buildHeaders()),
                UserRepresentation.class
        ).getBody();
    }

    @Override
    public UserRepresentation edit(UserRepresentation object) throws EntityNotFoundByIdException {
        return null;
    }

    @Override
    public void delete(String id) throws EntityNotFoundByIdException {

    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwt.getTokenValue());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    private void setDefaults(UserRepresentation u) {
        if (u.getCredentials() == null || u.getCredentials().isEmpty()) {
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setTemporary(true);
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(defaultPassword);
            u.setCredentials(Collections.singletonList(credential));
        }
        u.setEnabled(true);
    }

}
