package workshop.financial.monitoring.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workshop.financial.monitoring.backend.domain.dto.CategoryRequest;
import workshop.financial.monitoring.backend.domain.dto.CategoryResponse;
import workshop.financial.monitoring.backend.service.CategoryService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("category")
@Tag(name = "REST API: Категории")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Добавление категории")
    @PostMapping(value = "/add",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public CategoryResponse addCategory(@RequestBody @Valid CategoryRequest category) {
        return categoryService.addCategory(category);
    }

    @Operation(summary = "Редактирование категории")
    @PutMapping(value = "/{id}/edit",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public CategoryResponse editCategory(@PathVariable("id") Long id, @RequestBody @Valid CategoryRequest category) {
        return categoryService.editCategory(id, category);
    }

    @Operation(summary = "Все категории")
    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryResponse> listCategory() {
        return categoryService.allCategory();
    }
}
