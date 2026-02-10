package legoat.tasktypes;

/**
* TaskType enum class.
*
* @author Russell Lin
*/
public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");

    private final String code;

    TaskType(String code) {
        this.code = code;
    }

    /**
    * Getter method that returns the String representation of TaskType enum.
    * @return String representation of TaskType enum value
    */
    public String getCode() {
        return code;
    }

    /**
     * Convert a string code to the corresponding TaskType enum.
     * @param code the string representation of Task type (e.g., "T", "D", "E")
     * @return the TaskType enum value
     * @throws IllegalArgumentException if code is invalid
     */
    public static TaskType fromCode(String code) {
        for (TaskType type : TaskType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown task type: " + code);
    }
}
