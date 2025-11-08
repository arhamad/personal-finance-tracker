package finance.model;

public enum CategoryType {
    INCOME("Income"),
    EXPENSE("Expense");

    private final String displayName;

    CategoryType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static CategoryType fromString(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }

        String normalized = text.trim().toLowerCase();

        for (CategoryType type : CategoryType.values()) {
            if (type.displayName.toLowerCase().equals(normalized) ||
                    type.name().toLowerCase().equals(normalized) ||
                    (type == INCOME && normalized.equals("1")) ||
                    (type == EXPENSE && normalized.equals("2"))) {
                return type;
            }
        }

        // Provide helpful error message
        throw new IllegalArgumentException("Invalid type: '" + text + "'. Must be 'Income' or 'Expense' (or 1/2)");
    }

    public static boolean isValidType(String text) {
        try {
            fromString(text);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
