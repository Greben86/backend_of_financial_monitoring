package workshop.financial.monitoring.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workshop.financial.monitoring.backend.domain.dto.UserRequest;
import workshop.financial.monitoring.backend.domain.model.User;
import workshop.financial.monitoring.backend.exception.LogicException;
import workshop.financial.monitoring.backend.repository.UserRepository;

/**
 * Сервис управления пользователями
 */
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository repository;

    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User addUser(final User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new LogicException("Пользователь с таким именем уже существует");
        }

        return repository.save(user);
    }

    /**
     * Обновление пользователя
     *
     * @return пользователь
     */
    public void updateUser(final User user) {
        repository.save(user);
    }

    /**
     * Обновление пользователя
     *
     * @return пользователь
     */
    public void updateUser(final UserRequest request) {
        final var user = getCurrentUser();
        if (repository.existsByUsername(request.username())) {
            throw new LogicException("Пользователь с таким именем уже существует");
        }

        user.setUsername(request.username());
        repository.save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(final String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new LogicException("Пользователь не найден"));

    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        final var username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return getByUsername(username);
    }
}
