package misrraimsp.theam.crm.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

public class AuditorAwareImplementation implements AuditorAware<String> {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getPrincipal() instanceof Jwt) {
                String userId = ((Jwt) authentication.getPrincipal()).getClaim("sub");
                LOGGER.debug("user principal({}) of type Jwt", userId);
                return Optional.of(userId);
            } else {
                LOGGER.warn("user principal({}) of unexpected type {}", authentication.getPrincipal(), authentication.getPrincipal().getClass());
                return Optional.of("unknown");
            }
        }
        else {
            if (authentication == null) {
                LOGGER.debug("authentication is null");
            }
            else {
                LOGGER.warn("authentication is not null but authentication.isAuthenticated is {}", authentication.isAuthenticated());
            }
            return Optional.of("unknown");
        }
    }

}
