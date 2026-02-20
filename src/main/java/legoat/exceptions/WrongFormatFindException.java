package legoat.exceptions;

import legoat.ui.StringFormat;

/**
* Represents an error condition when the input command of the find method
* is in the wrong format.
*
* @author Russell Lin
*/
public class WrongFormatFindException extends Exception {

    /**
    * Constructs a {@code WrongFormatFindException} with a detail message.
    */
    public WrongFormatFindException() {
        super(StringFormat.WRONG_FORMAT_FIND_EXCEPTION_STRING);
    }
}
