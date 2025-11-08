package finance.ui;

import finance.service.FinanceService;

public class SummaryManager {
    private final FinanceService service;

    public SummaryManager(FinanceService service) {
        this.service = service;
    }

    public void displaySummary() {
        System.out.println("\n--- FINANCIAL SUMMARY ---");
        System.out.printf("Total Income: $%.2f%n", service.getTotalIncome());
        System.out.printf("Total Expenses: $%.2f%n", service.getTotalExpenses());
        System.out.printf("Net Balance: $%.2f%n", service.getNetBalance());
        System.out.println("\n" + service.getBudget());

        displayCategories();
    }

    private void displayCategories() {
        System.out.println("\nIncome Categories:");
        service.getIncomeCategories().forEach(cat -> System.out.println("  - " + cat.getName()));

        System.out.println("\nExpense Categories:");
        service.getExpenseCategories().forEach(cat -> System.out.println("  - " + cat.getName()));
    }
}
