package legoat.commands;

/**
 * Command is the common contract for all command classes.
 */
public interface Command {
    String execute(String[] input, Parser commandHandler) throws Exception;
}
