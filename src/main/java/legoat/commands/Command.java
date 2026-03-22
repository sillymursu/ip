package legoat.commands;

/**
 * Command is the common contract for all command classes.
 */
public interface Command {
    /**
     * Executes a command with tokenized user input.
     *
     * @param input User command tokens
     * @param commandHandler Parser that owns task state and storage
     * @return User-facing response string
     * @throws Exception If command execution fails
     */
    String execute(String[] input, Parser commandHandler) throws Exception;
}
