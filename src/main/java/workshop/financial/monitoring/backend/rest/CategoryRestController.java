package workshop.financial.monitoring.backend.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workshop.financial.monitoring.backend.model.Category;
import workshop.financial.monitoring.backend.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("category")
public class CategoryRestController {

    private final CategoryService categoryService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Category addCategory(@RequestBody Category category) {
        return category;
    }

    @PutMapping(value = "/{id}/edit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Category editCategory(@PathVariable("id") Long id, @RequestBody Category category) {
        return category;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> listCategory() {
        return new ArrayList<>();
    }
}
