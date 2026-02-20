package legoat.exceptions;

import legoat.ui.StringFormat;

/**
* Represents an error condition when the start date is later than the end date
* of an Event instance.
*
* @author Russell Lin
*/
public class EventTimeException extends Exception {

    /**
    * Constructs a {@code EventTimeException} with a detail message.
    */
    public EventTimeException() {
        super(StringFormat.EVENT_TIME_EXCEPTION_STRING);
    }
}
