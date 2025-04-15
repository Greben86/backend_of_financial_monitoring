package workshop.financial.monitoring.backend.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workshop.financial.monitoring.backend.model.UserAccount;
import workshop.financial.monitoring.backend.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("users")
public class UserRestController {

    private final UserService userService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserAccount addUser(@RequestBody UserAccount user) {
        return user;
    }

    @PutMapping(value = "/{id}/edit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserAccount editUser(@PathVariable("id") Long id, @RequestBody UserAccount user) {
        return user;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserAccount loginUser(@RequestBody UserAccount user) {
        return user;
    }

    @GetMapping("/logout")
    public String logoutUser(@PathVariable("id") Long id) {
        return String.valueOf(id);
    }
}
