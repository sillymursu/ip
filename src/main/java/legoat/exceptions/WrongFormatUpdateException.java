package legoat.exceptions;

import legoat.ui.StringFormat;

/**
* Represents an error condition when the input command of the update method
* is in the wrong format.
*
* @author Russell Lin
*/
public class WrongFormatUpdateException extends Exception {

    /**
    * Constructs a {@code WrongFormatUpdateException} with a detail message.
    */
    public WrongFormatUpdateException() {
        super(StringFormat.WRONG_FORMAT_UPDATE_EXCEPTION_STRING);
    }
}
