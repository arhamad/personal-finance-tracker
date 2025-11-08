package finance.model;

public class Budget {
    private double monthlyBudget;
    private double spentAmount;

    public Budget(double monthlyBudget) {
        setMonthlyBudget(monthlyBudget);
        this.spentAmount = 0.0;
    }

    public void setMonthlyBudget(double monthlyBudget) {
        if (monthlyBudget < 0) {
            throw new IllegalArgumentException("Budget cannot be negative");
        }
        this.monthlyBudget = monthlyBudget;
    }

    public void addExpense(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Expense amount cannot be negative");
        }
        this.spentAmount += amount;
    }

    public void removeExpense(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Expense amount cannot be negative");
        }
        this.spentAmount = Math.max(0, this.spentAmount - amount);
    }

    public double getRemaining() {
        return monthlyBudget - spentAmount;
    }

    public double getSpentAmount() { return spentAmount; }
    public double getMonthlyBudget() { return monthlyBudget; }

    public double getSpentPercentage() {
        return monthlyBudget > 0 ? (spentAmount / monthlyBudget) * 100 : 0;
    }

    @Override
    public String toString() {
        return String.format("Monthly Budget: $%.2f | Spent: $%.2f | Remaining: $%.2f (%.1f%%)",
                monthlyBudget, spentAmount, getRemaining(), getSpentPercentage());
    }
}
