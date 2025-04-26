package workshop.financial.monitoring.backend.util;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import workshop.financial.monitoring.backend.domain.dto.FilterDTO;
import workshop.financial.monitoring.backend.domain.model.Status;
import workshop.financial.monitoring.backend.domain.model.Transaction;
import workshop.financial.monitoring.backend.domain.model.TransactionType;
import workshop.financial.monitoring.backend.service.CategoryService;
import workshop.financial.monitoring.backend.service.UserService;

@RequiredArgsConstructor
@Component
public class SpecificationBuilder {

    private final CategoryService categoryService;
    private final UserService userService;

    public Specification<Transaction> build(final FilterDTO dto) {
        final var user = userService.getCurrentUser();
        var specification = Specification.<Transaction>where((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user"), user));

        if (StringUtils.isNotBlank(dto.getSenderBank())) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("senderBank"), dto.getSenderBank()));
        }

        if (StringUtils.isNotBlank(dto.getRecipientBank())) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("recipientBank"), dto.getRecipientBank()));
        }

        if (dto.getDateStart() != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("transactionTime"), dto.getDateStart()));
        }

        if (dto.getDateEnd() != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThan(root.get("transactionTime"), dto.getDateEnd()));
        }

        if (StringUtils.isNotBlank(dto.getStatus())) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), Status.forValue(dto.getStatus())));
        }

        if (StringUtils.isNotBlank(dto.getInn())) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("inn"), dto.getInn()));
        }

        if (dto.getMinSumValue() != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("sumValue"), dto.getMinSumValue()));
        }

        if (dto.getMaxSumValue() != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("sumValue"), dto.getMaxSumValue()));
        }

        if (StringUtils.isNotBlank(dto.getTransactionType())) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("transactionType"), TransactionType.forValue(dto.getTransactionType())));
        }

        if (StringUtils.isNotBlank(dto.getCategoryName())) {
            final var category = categoryService.findCategory(dto.getCategoryName());
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("category"), category));
        }

        return specification;
    }
}
