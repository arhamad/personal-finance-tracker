package finance.ui;

import finance.model.Transaction;
import finance.service.FinanceService;

import java.util.List;

public class TransactionManager {
    private final FinanceService service;
    private final InputHandler inputHandler;
    private final MenuHandler menuHandler;
    private final CategoryManager categoryManager;

    public TransactionManager(FinanceService service, InputHandler inputHandler, CategoryManager categoryManager, MenuHandler menuHandler) {
        this.service = service;
        this.inputHandler = inputHandler;
        this.categoryManager = categoryManager;
        this.menuHandler = menuHandler;
    }

    public void addTransaction() {
        System.out.println("\n--- ADD TRANSACTION ---");

        try {
            double amount = inputHandler.readPositiveDouble("Enter amount: ");
            String category = categoryManager.getCategoryInput();
            String type = menuHandler.readTransactionType();
            String description = inputHandler.readString("Enter description: ");

            Transaction transaction = service.addTransaction(amount, category, type, description);
            System.out.println("✅ Transaction added successfully!");
            System.out.println("ID: " + transaction.getId() + " | " + transaction);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
            System.out.println("Please try again with correct values.");
        } catch (Exception e) {
            System.out.println("❌ Unexpected error: " + e.getMessage());
        }
    }

    public void viewTransactions() {
        System.out.println("\n--- VIEW TRANSACTIONS ---");
        System.out.println("1. All Transactions");
        System.out.println("2. Income Only");
        System.out.println("3. Expenses Only");
        System.out.println("4. By Category");

        int choice = inputHandler.readInt("Choose view option: ");

        List<Transaction> transactions;
        switch (choice) {
            case 1 -> transactions = service.getAllTransactions();
            case 2 -> transactions = service.getIncomeTransactions();
            case 3 -> transactions = service.getExpenseTransactions();
            case 4 -> {
                String category = categoryManager.getCategoryInput();
                transactions = service.getTransactionsByCategory(category);
            }
            default -> {
                System.out.println("Invalid choice!");
                return;
            }
        }

        displayTransactions(transactions);
    }

    public void editTransaction() {
        System.out.println("\n--- EDIT TRANSACTION ---");
        int id = inputHandler.readInt("Enter transaction ID to edit: ");

        System.out.println("Leave field blank to keep current value:");
        Double amount = readDoubleOrNull("New amount: ");
        String category = inputHandler.readString("New category (leave blank to keep current): ");
        String description = inputHandler.readString("New description (leave blank to keep current): ");

        boolean success = service.updateTransaction(id,
                amount,
                category.isEmpty() ? null : category,
                description.isEmpty() ? null : description);

        if (success) {
            System.out.println("✅ Transaction updated successfully!");
        } else {
            System.out.println("❌ Transaction not found!");
        }
    }

    public void deleteTransaction() {
        System.out.println("\n--- DELETE TRANSACTION ---");
        int id = inputHandler.readInt("Enter transaction ID to delete: ");

        boolean success = service.deleteTransaction(id);
        if (success) {
            System.out.println("✅ Transaction deleted successfully!");
        } else {
            System.out.println("❌ Transaction not found!");
        }
    }

    private Double readDoubleOrNull(String prompt) {
        System.out.print(prompt);
        String input = inputHandler.readString("");
        return input.isEmpty() ? null : Double.parseDouble(input);
    }

    private void displayTransactions(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("\nTransactions:");
        System.out.println("----------------------------------------------------------------");
        transactions.forEach(System.out::println);
        System.out.println("----------------------------------------------------------------");
        System.out.printf("Total: %d transactions%n", transactions.size());
    }
}
