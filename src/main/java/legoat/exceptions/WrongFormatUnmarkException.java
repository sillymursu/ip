package legoat.exceptions;

import legoat.ui.StringFormat;

/**
* Represents an error condition when the input command of the Unmark method
* is in the wrong format.
*
* @author Russell Lin
*/
public class WrongFormatUnmarkException extends Exception {

    /**
    * Constructs a {@code WrongFormatUnmarkException} with a detail message.
    */
    public WrongFormatUnmarkException() {
        super(StringFormat.WRONG_FORMAT_UNMARK_EXCEPTION_STRING);
    }
}
