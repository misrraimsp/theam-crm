package misrraimsp.theam.crm.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImplementation implements AuditorAware<String> {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public Optional<String> getCurrentAuditor() {

        LOGGER.info("AuditorAware is working...");
        return Optional.of("someone");
    }
}
