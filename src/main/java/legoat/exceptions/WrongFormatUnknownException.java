package legoat.exceptions;

import legoat.ui.StringFormat;

/**
* Represents an error condition when the input command is not recognised.
*
* @author Russell Lin
*/
public class WrongFormatUnknownException extends Exception {

    /**
    * Constructs a {@code WrongFormatUnknownException} with a detail message.
    */
    public WrongFormatUnknownException() {
        super(StringFormat.WRONG_FORMAT_UNKNOWN_EXCEPTION_STRING);
    }
}
