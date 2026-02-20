package legoat.exceptions;

import legoat.ui.StringFormat;

/**
* Represents an error condition when the input command of the delete method
* is in the wrong format.
*
* @author Russell Lin
*/
public class WrongFormatDeleteException extends Exception {

    /**
    * Constructs a {@code WrongFormatDeleteException} with a detail message.
    */
    public WrongFormatDeleteException() {
        super(StringFormat.WRONG_FORMAT_DELETE_EXCEPTION_STRING);
    }
}
