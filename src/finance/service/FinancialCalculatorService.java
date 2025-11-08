package finance.service;

import finance.model.Transaction;

import java.util.List;

public class FinancialCalculatorService {

    public double calculateTotalIncome(List<Transaction> incomeTransactions) {
        return incomeTransactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double calculateTotalExpenses(List<Transaction> expenseTransactions) {
        return expenseTransactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double calculateNetBalance(double totalIncome, double totalExpenses) {
        return totalIncome - totalExpenses;
    }

    public double calculateCategoryTotal(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}
