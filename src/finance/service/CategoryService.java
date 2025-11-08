package finance.service;

import finance.model.Category;
import finance.model.CategoryType;
import finance.repositroy.CategoryRepository;

import java.util.List;
import java.util.Optional;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getOrCreateCategory(String name, CategoryType type) {
        Optional<Category> existingCategory = categoryRepository.getCategory(name);

        if (existingCategory.isPresent()) {
            Category category = existingCategory.get();
            if (category.getType() != type) {
                throw new IllegalArgumentException(
                        String.format("Category '%s' already exists as %s, but you specified %s. " +
                                        "Please use a different category name or the correct type.",
                                name, category.getType().getDisplayName(), type.getDisplayName())
                );
            }
            return category;
        } else {
            Category newCategory = new Category(name, type);
            categoryRepository.addCategory(newCategory);
            return newCategory;
        }
    }

    public Optional<Category> getCategory(String name) {
        return categoryRepository.getCategory(name);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    public List<Category> getIncomeCategories() {
        return categoryRepository.getCategoriesByType(CategoryType.INCOME);
    }

    public List<Category> getExpenseCategories() {
        return categoryRepository.getCategoriesByType(CategoryType.EXPENSE);
    }

    public boolean categoryExists(String name) {
        return categoryRepository.categoryExists(name);
    }
}
