package misrraimsp.theam.crm.controller;

import lombok.RequiredArgsConstructor;
import misrraimsp.theam.crm.model.dto.UserDTO;
import misrraimsp.theam.crm.service.UserServer;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class UserController {

    private final UserServer userServer;

    @GetMapping("/users")
    public UserDTO[] getAllUsers(@AuthenticationPrincipal Jwt jwt) {
        return userServer.findAll(jwt);
    }

    @GetMapping("/users/{id}")
    public UserDTO getUser(@AuthenticationPrincipal Jwt jwt,
                           @PathVariable String id) {

        return userServer.findById(id, jwt);
    }

    @PostMapping("/users")
    public UserDTO newUser(@AuthenticationPrincipal Jwt jwt,
                           @RequestBody UserDTO userDTO) {

        return userServer.create(userDTO, jwt);
    }

    @PutMapping("/users")
    public UserDTO editUser(@AuthenticationPrincipal Jwt jwt,
                            @RequestBody UserDTO userDTO) {

        return userServer.edit(userDTO, jwt);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@AuthenticationPrincipal Jwt jwt,
                           @PathVariable String id) {

        userServer.delete(id, jwt);
    }
}
