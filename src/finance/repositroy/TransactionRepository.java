package finance.repositroy;

import finance.model.Budget;
import finance.model.Category;
import finance.model.CategoryType;
import finance.model.Transaction;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class FinanceRepository {
    private final Map<Integer, Transaction> transactions = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    private final Set<Category> categories = new HashSet<>();

    public TransactionRepository() {
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

    public Transaction addTransaction(double amount, Category category, String description) {
        int id = idCounter.getAndIncrement();
        Transaction transaction = new Transaction(id, amount, category, description, null);
        transactions.put(id, transaction);
        return transaction;
    }

    public boolean updateTransaction(int id, Double amount, Category category, String description) {
        Transaction existing = transactions.get(id);
        if (existing != null) {
            Transaction updated = new Transaction(existing, amount, category, description);
            transactions.put(id, updated);
            return true;
        }
        return false;
    }

    public boolean deleteTransaction(int id) {
        return transactions.remove(id) != null;
    }

    public Optional<Transaction> getTransaction(int id) {
        return Optional.ofNullable(transactions.get(id));
    }

    public List<Transaction> getAllTransactions() {
        return transactions.values().stream()
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactionsByCategory(String categoryName) {
        return transactions.values().stream()
                .filter(t -> t.getCategory().getName().equalsIgnoreCase(categoryName))
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactionsByType(CategoryType type) {
        return transactions.values().stream()
                .filter(t -> t.getCategory().getType() == type)
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .collect(Collectors.toList());
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
                .collect(Collectors.toList());
    }
}
