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

    private final Server<UserDTO> userDTOServer;

    @GetMapping("/users")
    public UserDTO[] getAllUsers(@AuthenticationPrincipal Jwt jwt) {
        ((OAuthClient) userDTOServer).setAuthorizationToken(jwt);
        return userDTOServer.findAll();
    }

    @GetMapping("/users/{id}")
    public UserDTO getUser(@AuthenticationPrincipal Jwt jwt,
                           @PathVariable String id) {
        ((OAuthClient) userDTOServer).setAuthorizationToken(jwt);
        return userDTOServer.findById(id);
    }

    @PostMapping("/users")
    public UserDTO newUser(@AuthenticationPrincipal Jwt jwt,
                           @RequestBody UserDTO userDTO) {

        ((OAuthClient) userDTOServer).setAuthorizationToken(jwt);
        return userDTOServer.create(userDTO);
    }

    @PutMapping("/users")
    public UserDTO editUser(@AuthenticationPrincipal Jwt jwt,
                            @RequestBody UserDTO userDTO) {

        ((OAuthClient) userDTOServer).setAuthorizationToken(jwt);
        return userDTOServer.edit(userDTO);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@AuthenticationPrincipal Jwt jwt,
                           @PathVariable String id) {

        ((OAuthClient) userDTOServer).setAuthorizationToken(jwt);
        userDTOServer.delete(id);
    }
}
