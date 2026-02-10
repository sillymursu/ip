package legoat.tasktypes;

/**
* TaskStatus enum class.
*
* @author Russell Lin
*/
public enum TaskStatus {
    COMPLETE("X"),
    INCOMPLETE("  ");

    private final String symbol;

    TaskStatus(String symbol) {
        this.symbol = symbol;
    }

    /**
    * Getter method that returns the String representation of TaskStatus enum.
    * @return String representation of TaskStatus enum value
    */
    public String getSymbol() {
        return symbol;
    }

    /**
    * Convert a string symbol to the corresponding TaskStatus enum.
    * @param symbol the string representation of Task type (e.g., "X", "  ")
    * @return the TaskStatus enum value
    * @throws IllegalArgumentException if symbol is invalid
    */
    public static TaskStatus fromSymbol(String symbol) {
        for (TaskStatus status : TaskStatus.values()) {
            if (status.symbol.equals(symbol)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown task type: " + symbol);
    }
}
