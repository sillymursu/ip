package legoat.exceptions;

import legoat.ui.StringFormat;

/**
* Represents an error condition when the input command of the addTodo method
* is in the wrong format.
*
* @author Russell Lin
*/
public class WrongFormatTodoException extends Exception {

    /**
    * Constructs a {@code WrongFormatTodoException} with a detail message.
    */
    public WrongFormatTodoException() {
        super(StringFormat.WRONG_FORMAT_TODO_EXCEPTION_STRING);
    }
}
