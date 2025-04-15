package workshop.financial.monitoring.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workshop.financial.monitoring.backend.repository.UserJPARepository;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserJPARepository jpaRepository;

}
