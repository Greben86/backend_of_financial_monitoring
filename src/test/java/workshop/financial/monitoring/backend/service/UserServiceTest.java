package workshop.financial.monitoring.backend.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import workshop.financial.monitoring.backend.domain.model.User;
import workshop.financial.monitoring.backend.exception.LogicException;
import workshop.financial.monitoring.backend.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void test_addUser_userNameAlreadyExists_throwsLogicException() {
        User user = new User();
        user.setUsername("Petr");
        when(userRepository.existsByUsername("Petr")).thenReturn(true);

        var x = assertThrows(LogicException.class, () -> userService.addUser(user));

        assertEquals("Пользователь с таким именем уже существует", x.getMessage());
        verify(userRepository, never()).save(any());

    }

    @Test
    public void test_addUser_ok_savesAndReturnUser() {
        User user = new User();
        user.setUsername("Petr");
        when(userRepository.existsByUsername("Petr")).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        var savedUser = userService.addUser(user);

        assertSame(user, savedUser);
        verify(userRepository).save(any());

    }

}