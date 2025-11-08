package finance.ui;

import finance.service.FinanceService;

public class BudgetManager {
    private final FinanceService service;
    private final InputHandler inputHandler;

    public BudgetManager(FinanceService service, InputHandler inputHandler) {
        this.service = service;
        this.inputHandler = inputHandler;
    }

    public void manageBudget() {
        System.out.println("\n--- BUDGET MANAGEMENT ---");
        System.out.println("1. View Budget");
        System.out.println("2. Set Monthly Budget");

        int choice = inputHandler.readInt("Choose option: ");

        switch (choice) {
            case 1 -> displayBudget();
            case 2 -> setMonthlyBudget();
            default -> System.out.println("Invalid choice!");
        }
    }

    private void displayBudget() {
        System.out.println("\n" + service.getBudget());
    }

    private void setMonthlyBudget() {
        double budget = inputHandler.readPositiveDouble("Enter new monthly budget: ");
        service.setMonthlyBudget(budget);
        System.out.println("✅ Monthly budget updated!");
    }
}
