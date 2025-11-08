package finance.service;

import finance.model.Category;
import finance.model.CategoryType;
import finance.model.Transaction;
import finance.repositroy.CategoryRepository;
import finance.repositroy.TransactionRepository;

import java.util.List;
import java.util.Optional;

public class FinanceService {
    private final TransactionService transactionService;
    private final CategoryService categoryService;
    private final BudgetService budgetService;
    private final FinancialCalculatorService calculatorService;

    public FinanceService() {
        CategoryRepository categoryRepository = new CategoryRepository();
        TransactionRepository transactionRepository = new TransactionRepository();

        this.transactionService = new TransactionService(transactionRepository);
        this.categoryService = new CategoryService(categoryRepository);
        this.budgetService = new BudgetService(1000.0); // Default monthly budget
        this.calculatorService = new FinancialCalculatorService();
    }

    public Transaction addTransaction(double amount, String categoryName, String type, String description) {
        validateInputs(amount, categoryName, type);

        CategoryType categoryType = CategoryType.fromString(type);
        String cleanCategoryName = categoryName.trim();
        String cleanDescription = description != null ? description.trim() : "";

        Category category = categoryService.getOrCreateCategory(cleanCategoryName, categoryType);
        Transaction transaction = transactionService.createTransaction(amount, category, cleanDescription);

        // Update budget if it's an expense
        if (categoryType == CategoryType.EXPENSE) {
            budgetService.addExpense(amount);
        }

        return transaction;
    }

    public boolean updateTransaction(int id, Double amount, String categoryName, String description) {
        if (!transactionService.transactionExists(id)) {
            return false;
        }

        Transaction oldTransaction = transactionService.getTransaction(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found with ID: " + id));

        Category category = null;
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            category = categoryService.getCategory(categoryName.trim())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryName));
        }

        // Adjust budget if amount changed and it's an expense
        if (amount != null && oldTransaction.isExpense()) {
            budgetService.removeExpense(oldTransaction.getAmount());
            budgetService.addExpense(amount);
        }

        return transactionService.updateTransaction(id, amount, category, description);
    }

    public boolean deleteTransaction(int id) {
        Optional<Transaction> transaction = transactionService.getTransaction(id);
        if (transaction.isPresent()) {
            // Remove from budget if it's an expense
            if (transaction.get().isExpense()) {
                budgetService.removeExpense(transaction.get().getAmount());
            }
            return transactionService.deleteTransaction(id);
        }
        return false;
    }

    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    public List<Transaction> getTransactionsByCategory(String categoryName) {
        return transactionService.getTransactionsByCategory(categoryName);
    }

    public List<Transaction> getIncomeTransactions() {
        return transactionService.getIncomeTransactions();
    }

    public List<Transaction> getExpenseTransactions() {
        return transactionService.getExpenseTransactions();
    }

    public BudgetService getBudgetService() {
        return budgetService;
    }

    public void setMonthlyBudget(double amount) {
        budgetService.setMonthlyBudget(amount);
    }

    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    public List<Category> getIncomeCategories() {
        return categoryService.getIncomeCategories();
    }

    public List<Category> getExpenseCategories() {
        return categoryService.getExpenseCategories();
    }

    public double getTotalIncome() {
        return calculatorService.calculateTotalIncome(getIncomeTransactions());
    }

    public double getTotalExpenses() {
        return calculatorService.calculateTotalExpenses(getExpenseTransactions());
    }

    public double getNetBalance() {
        return calculatorService.calculateNetBalance(getTotalIncome(), getTotalExpenses());
    }

    // Backward compatibility method
    public finance.model.Budget getBudget() {
        return budgetService.getBudget();
    }

    private void validateInputs(double amount, String categoryName, String type) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }

        // This will validate the type
        CategoryType.fromString(type);
    }
}
