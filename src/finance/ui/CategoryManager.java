package finance.ui;

import finance.model.Category;
import finance.service.FinanceService;

import java.util.List;

public class CategoryManager {
    private final FinanceService service;
    private final InputHandler inputHandler;

    public CategoryManager(FinanceService service, InputHandler inputHandler) {
        this.service = service;
        this.inputHandler = inputHandler;
    }

    public String getCategoryInput() {
        displayAvailableCategories();

        while (true) {
            String category = inputHandler.readNonEmpty("\nEnter category (or type 'new' to create one): ");

            if (category.equalsIgnoreCase("new")) {
                return inputHandler.readNonEmpty("Enter new category name: ");
            }

            boolean categoryExists = service.getAllCategories().stream()
                    .anyMatch(cat -> cat.getName().equalsIgnoreCase(category));

            if (categoryExists) {
                return category;
            } else {
                System.out.println("❌ Category '" + category + "' not found.");
                System.out.println("Please choose from available categories or type 'new' to create a new one.");
            }
        }
    }

    private void displayAvailableCategories() {
        System.out.println("\n--- AVAILABLE CATEGORIES ---");

        List<Category> incomeCategories = service.getIncomeCategories();
        if (!incomeCategories.isEmpty()) {
            System.out.println("Income Categories:");
            incomeCategories.forEach(cat -> System.out.println("  - " + cat.getName()));
        }

        List<Category> expenseCategories = service.getExpenseCategories();
        if (!expenseCategories.isEmpty()) {
            System.out.println("Expense Categories:");
            expenseCategories.forEach(cat -> System.out.println("  - " + cat.getName()));
        }
    }
}
