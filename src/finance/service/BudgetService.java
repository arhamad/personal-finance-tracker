package finance.service;

import finance.model.Budget;

public class BudgetService {
    private final Budget budget;

    public BudgetService(double initialBudget) {
        this.budget = new Budget(initialBudget);
    }

    public void addExpense(double amount) {
        budget.addExpense(amount);
    }

    public void removeExpense(double amount) {
        budget.removeExpense(amount);
    }

    public void setMonthlyBudget(double amount) {
        budget.setMonthlyBudget(amount);
    }

    public double getRemainingBudget() {
        return budget.getRemaining();
    }

    public double getSpentAmount() {
        return budget.getSpentAmount();
    }

    public double getSpentPercentage() {
        return budget.getSpentPercentage();
    }

    public Budget getBudget() {
        return budget;
    }
}
