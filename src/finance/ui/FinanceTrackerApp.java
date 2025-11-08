package finance.ui;

import finance.model.Category;
import finance.model.Transaction;
import finance.service.FinanceService;

import java.util.List;
import java.util.Scanner;

public class FinanceTrackerApp {
    private final InputHandler inputHandler;
    private final MenuHandler menuHandler;
    private final FinanceService service;
    private final TransactionManager transactionManager;
    private final BudgetManager budgetManager;
    private final SummaryManager summaryManager;

    public FinanceTrackerApp() {
        Scanner scanner = new Scanner(System.in);
        this.inputHandler = new InputHandler(scanner);
        this.menuHandler = new MenuHandler(inputHandler);
        this.service = new FinanceService();

        CategoryManager categoryManager = new CategoryManager(service, inputHandler);
        this.transactionManager = new TransactionManager(service, inputHandler, categoryManager, menuHandler);
        this.budgetManager = new BudgetManager(service, inputHandler);
        this.summaryManager = new SummaryManager(service);
    }

    public void run() {
        menuHandler.displayWelcome();

        while (true) {
            menuHandler.displayMainMenu();
            int choice = inputHandler.readInt("Enter your choice: ");

            if (!handleMainMenuChoice(choice)) {
                break;
            }

            inputHandler.pause();
        }

        System.out.println("Thank you for using Personal Finance Tracker!");
    }

    private boolean handleMainMenuChoice(int choice) {
        try {
            switch (choice) {
                case 1 -> transactionManager.addTransaction();
                case 2 -> transactionManager.viewTransactions();
                case 3 -> transactionManager.editTransaction();
                case 4 -> transactionManager.deleteTransaction();
                case 5 -> budgetManager.manageBudget();
                case 6 -> summaryManager.displaySummary();
                case 7 -> { return false; }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return true;
    }
}
