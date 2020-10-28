package misrraimsp.theam.crm.service;

import org.springframework.security.oauth2.jwt.Jwt;

public interface OAuthClient {
    void setAuthorizationToken(Jwt jwt);
}
