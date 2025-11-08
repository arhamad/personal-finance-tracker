package finance.service;

import finance.model.CategoryType;
import finance.model.Transaction;
import finance.repositroy.TransactionRepository;

import java.util.List;
import java.util.Optional;

public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(double amount, finance.model.Category category, String description) {
        validateTransactionAmount(amount);
        return transactionRepository.addTransaction(amount, category, description);
    }

    public boolean updateTransaction(int id, Double amount, finance.model.Category category, String description) {
        if (!transactionRepository.transactionExists(id)) {
            return false;
        }

        if (amount != null) {
            validateTransactionAmount(amount);
        }

        return transactionRepository.updateTransaction(id, amount, category, description);
    }

    public boolean deleteTransaction(int id) {
        return transactionRepository.deleteTransaction(id);
    }

    public Optional<Transaction> getTransaction(int id) {
        return transactionRepository.getTransaction(id);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }

    public List<Transaction> getTransactionsByCategory(String categoryName) {
        return transactionRepository.getTransactionsByCategory(categoryName);
    }

    public List<Transaction> getIncomeTransactions() {
        return transactionRepository.getTransactionsByType(CategoryType.INCOME);
    }

    public List<Transaction> getExpenseTransactions() {
        return transactionRepository.getTransactionsByType(CategoryType.EXPENSE);
    }

    public boolean transactionExists(int id) {
        return transactionRepository.transactionExists(id);
    }

    private void validateTransactionAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive");
        }
    }
}
