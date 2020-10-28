package misrraimsp.theam.crm.controller;

import lombok.RequiredArgsConstructor;
import misrraimsp.theam.crm.model.dto.UserDTO;
import misrraimsp.theam.crm.service.OAuthClient;
import misrraimsp.theam.crm.service.Server;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class UserController {

    private final Server<UserDTO> userRepresentationServer;

    @GetMapping("/users")
    public UserDTO[] getAllUsers(@AuthenticationPrincipal Jwt jwt) {
        ((OAuthClient) userRepresentationServer).setAuthorizationToken(jwt);
        return userRepresentationServer.findAll();
    }

    @PostMapping("/users")
    public UserDTO newUser(@AuthenticationPrincipal Jwt jwt,
                           @RequestBody UserDTO userRepresentation) {

        ((OAuthClient) userRepresentationServer).setAuthorizationToken(jwt);
        return userRepresentationServer.create(userRepresentation);
    }
}
