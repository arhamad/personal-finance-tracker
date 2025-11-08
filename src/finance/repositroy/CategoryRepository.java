package finance.repositroy;

import finance.model.Category;
import finance.model.CategoryType;

import java.util.*;

public class CategoryRepository {
    private final Set<Category> categories = new HashSet<>();

    public CategoryRepository() {
        initializeDefaultCategories();
    }

    private void initializeDefaultCategories() {
        addCategory(new Category("Salary", CategoryType.INCOME));
        addCategory(new Category("Investment", CategoryType.INCOME));
        addCategory(new Category("Food", CategoryType.EXPENSE));
        addCategory(new Category("Transport", CategoryType.EXPENSE));
        addCategory(new Category("Entertainment", CategoryType.EXPENSE));
        addCategory(new Category("Utilities", CategoryType.EXPENSE));
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public Optional<Category> getCategory(String name) {
        return categories.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }

    public List<Category> getCategoriesByType(CategoryType type) {
        return categories.stream()
                .filter(c -> c.getType() == type)
                .toList();
    }

    public boolean categoryExists(String name) {
        return categories.stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(name));
    }
}
