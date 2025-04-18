package workshop.financial.monitoring.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workshop.financial.monitoring.backend.domain.dto.CategoryRequest;
import workshop.financial.monitoring.backend.domain.dto.CategoryResponse;
import workshop.financial.monitoring.backend.domain.model.Category;
import workshop.financial.monitoring.backend.repository.CategoryRepository;

import java.util.List;

/**
 * Сервис управления категориями
 */
@RequiredArgsConstructor
@Service
@Transactional
public class CategoryService {

    private final CategoryRepository repository;
    private final UserService userService;

    /**
     * Добавление категории
     *
     * @param request данные категории
     * @return новая категория
     */
    public CategoryResponse addCategory(final CategoryRequest request) {
        var user = userService.getCurrentUser();
        if (repository.existsByNameAndUser(request.name(), user)) {
            // TODO Заменить на свои исключения
            throw new RuntimeException("Категория с таким названием уже существует");
        }

        var category = new Category();
        category.setUser(user);
        category.setName(request.name());
        repository.save(category);
        return convertToResponse(category);
    }

    /**
     * Редактирование категории
     *
     * @param id идентификатор категории
     * @param request данные категории
     * @return обновленная категория
     */
    public CategoryResponse editCategory(final Long id, final CategoryRequest request) {
        var user = userService.getCurrentUser();
        if (repository.existsByNameAndUser(request.name(), user)) {
            // TODO Заменить на свои исключения
            throw new RuntimeException("Категория с таким названием уже существует");
        }

        var category = repository.findByIdAndUser(id, user)
                .orElseThrow(IllegalStateException::new);
        category.setName(request.name());
        repository.save(category);
        return convertToResponse(category);
    }

    /**
     * Выборка всех категорий пользователя
     *
     * @return список категорий
     */
    public List<CategoryResponse> allCategory() {
        var user = userService.getCurrentUser();
        return repository.findByUser(user).stream()
                .map(this::convertToResponse)
                .toList();
    }

    private CategoryResponse convertToResponse(Category input) {
        return new CategoryResponse(input.getId(), input.getName());
    }
}
