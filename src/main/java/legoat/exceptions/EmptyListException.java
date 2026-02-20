package legoat.exceptions;

import legoat.ui.StringFormat;

/**
* Represents an error condition when list method is called but there are
* no tasks in the list.
*
* @author Russell Lin
*/
public class EmptyListException extends Exception {

    /**
    * Constructs a {@code EmptyListException} with a detail message.
    */
    public EmptyListException() {
        super(StringFormat.EMPTY_LIST_EXCEPTION_STRING);
    }
}
