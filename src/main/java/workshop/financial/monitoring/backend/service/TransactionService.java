package workshop.financial.monitoring.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workshop.financial.monitoring.backend.repository.TransactionJPARepository;

@RequiredArgsConstructor
@Service
@Transactional
public class TransactionService {

    private final TransactionJPARepository jpaRepository;

}
