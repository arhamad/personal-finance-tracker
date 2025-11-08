package finance.ui;

public class MenuHandler {
    private final InputHandler inputHandler;

    public MenuHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public void displayWelcome() {
        System.out.println("=========================================");
        System.out.println("    PERSONAL FINANCE TRACKER");
        System.out.println("=========================================");
    }

    public void displayMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Add Transaction");
        System.out.println("2. View Transactions");
        System.out.println("3. Edit Transaction");
        System.out.println("4. Delete Transaction");
        System.out.println("5. Budget Management");
        System.out.println("6. Financial Summary");
        System.out.println("7. Exit");
        System.out.println("-------------------");
    }

    public void displayTransactionViewMenu() {
        System.out.println("\n--- VIEW TRANSACTIONS ---");
        System.out.println("1. All Transactions");
        System.out.println("2. Income Only");
        System.out.println("3. Expenses Only");
        System.out.println("4. By Category");
    }

    public void displayBudgetMenu() {
        System.out.println("\n--- BUDGET MANAGEMENT ---");
        System.out.println("1. View Budget");
        System.out.println("2. Set Monthly Budget");
    }

    public String readTransactionType() {
        while (true) {
            System.out.println("Select type:");
            System.out.println("1. Income");
            System.out.println("2. Expense");

            String input = inputHandler.readString("Enter choice (1 or 2): ");

            if (input.equalsIgnoreCase("1") || input.equalsIgnoreCase("income")) {
                return "Income";
            } else if (input.equalsIgnoreCase("2") || input.equalsIgnoreCase("expense")) {
                return "Expense";
            } else {
                System.out.println("❌ Invalid type! Please enter '1' for Income or '2' for Expense.");
            }
        }
    }
}
