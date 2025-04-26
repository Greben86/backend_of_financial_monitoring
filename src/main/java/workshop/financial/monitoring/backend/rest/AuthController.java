package workshop.financial.monitoring.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import workshop.financial.monitoring.backend.domain.dto.ChangePasswordRequest;
import workshop.financial.monitoring.backend.domain.dto.JwtAuthenticationResponse;
import workshop.financial.monitoring.backend.domain.dto.SignInRequest;
import workshop.financial.monitoring.backend.domain.dto.SignUpRequest;
import workshop.financial.monitoring.backend.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "REST API: Аутентификация")
@Validated
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/sign/up",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "Авторизация пользователя")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/sign/in",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @Operation(summary = "Смена пароля пользователя")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/password/change",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public JwtAuthenticationResponse passwordChange(@RequestBody @Valid ChangePasswordRequest request) {
        return authenticationService.passwordChange(request);
    }
}
