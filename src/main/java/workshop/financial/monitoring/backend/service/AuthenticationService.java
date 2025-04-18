package workshop.financial.monitoring.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import workshop.financial.monitoring.backend.domain.dto.ChangePasswordRequest;
import workshop.financial.monitoring.backend.domain.dto.JwtAuthenticationResponse;
import workshop.financial.monitoring.backend.domain.dto.SignInRequest;
import workshop.financial.monitoring.backend.domain.dto.SignUpRequest;
import workshop.financial.monitoring.backend.domain.model.User;

/**
 * Сервис аутентификации
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        final var user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userService.addUser(user);

        final var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        ));

        final var user = userService
                .userDetailsService()
                .loadUserByUsername(request.username());

        final var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Смена пароля пользователя
     *
     * @param request новый пароль
     * @return токен
     */
    public JwtAuthenticationResponse passwordChange(ChangePasswordRequest request) {
        final var user = userService.getCurrentUser();
        final var newPassword = passwordEncoder.encode(request.password());
        if (newPassword.equals(user.getPassword())) {
            // TODO Заменить на свои исключения
            throw new RuntimeException("Новый пароль совпадает со старым");
        }

        user.setPassword(newPassword);
        userService.updateUser(user);

        final var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
