package finance.model;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction {
    private final int id;
    private final LocalDate date;
    private final double amount;
    private final Category category;
    private final String description;

    public Transaction(int id, double amount, Category category, String description, LocalDate date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.id = id;
        this.amount = amount;
        this.category = Objects.requireNonNull(category, "Category cannot be null");
        this.description = description != null ? description.trim() : "";
        this.date = date != null ? date : LocalDate.now();
    }

    // Copy constructor for editing
    public Transaction(Transaction original, Double newAmount, Category newCategory, String newDescription) {
        this(original.id,
                newAmount != null ? newAmount : original.amount,
                newCategory != null ? newCategory : original.category,
                newDescription != null ? newDescription : original.description,
                original.date);
    }

    public int getId() { return id; }
    public LocalDate getDate() { return date; }
    public double getAmount() { return amount; }
    public Category getCategory() { return category; }
    public String getDescription() { return description; }

    public boolean isIncome() {
        return category.getType() == CategoryType.INCOME;
    }

    public boolean isExpense() {
        return category.getType() == CategoryType.EXPENSE;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s | %s | %s%.2f | %s",
                id, date, category.getName(),
                isIncome() ? "+" : "-", amount, description);
    }
}
