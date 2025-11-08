package finance.repositroy;

import finance.model.CategoryType;
import finance.model.Transaction;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionRepository {
    private final Map<Integer, Transaction> transactions = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public Transaction addTransaction(double amount, finance.model.Category category, String description) {
        int id = idCounter.getAndIncrement();
        Transaction transaction = new Transaction(id, amount, category, description, null);
        transactions.put(id, transaction);
        return transaction;
    }

    public boolean updateTransaction(int id, Double amount, finance.model.Category category, String description) {
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
                .toList();
    }

    public List<Transaction> getTransactionsByCategory(String categoryName) {
        return transactions.values().stream()
                .filter(t -> t.getCategory().getName().equalsIgnoreCase(categoryName))
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .toList();
    }

    public List<Transaction> getTransactionsByType(CategoryType type) {
        return transactions.values().stream()
                .filter(t -> t.getCategory().getType() == type)
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .toList();
    }

    public boolean transactionExists(int id) {
        return transactions.containsKey(id);
    }

    public int getTransactionCount() {
        return transactions.size();
    }
}
