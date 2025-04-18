package workshop.financial.monitoring.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workshop.financial.monitoring.backend.domain.dto.UserRequest;
import workshop.financial.monitoring.backend.service.UserService;

@RequiredArgsConstructor(onConstructor_=@Autowired)
@RestController
@RequestMapping("user")
@Tag(name = "REST API: Пользователь")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Редактирование имени пользователя")
    @PutMapping(value = "/edit",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> editUser(@RequestBody UserRequest userRequest) {
        userService.updateUser(userRequest);
        return ResponseEntity.ok().build();
    }
}
