package finance.model;

import java.util.Objects;

public class Category {
    private final String name;
    private final CategoryType type;

    public Category(String name, CategoryType type) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        this.name = name.trim();
        this.type = Objects.requireNonNull(type, "Category type cannot be null");
    }

    public String getName() { return name; }
    public CategoryType getType() { return type; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name.equalsIgnoreCase(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, type.getDisplayName());
    }
}
