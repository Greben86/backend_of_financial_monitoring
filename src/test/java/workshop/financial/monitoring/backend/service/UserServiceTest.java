package workshop.financial.monitoring.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import workshop.financial.monitoring.backend.domain.model.User;
import workshop.financial.monitoring.backend.repository.UserRepository;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void init() {
        Mockito.reset(repository);
    }

    @DisplayName("Тест сохранения пользователя")
    @Test
    void addUser() {
        final var user = new User();
        user.setUsername("testUser");
        user.setPassword("password123");

        Mockito.when(repository.existsByUsername(eq(user.getUsername()))).thenReturn(Boolean.FALSE);

        userService.addUser(user);

        Mockito.verify(repository, Mockito.times(1)).save(eq(user));
    }
}