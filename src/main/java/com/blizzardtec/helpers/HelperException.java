/**
 * 
 */
package com.blizzardtec.helpers;

/**
 * Wraps exceptions thrown by helper classes.
 *
 * @author Barnaby Golden
 *
 */
public final class HelperException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 3357343587955807854L;

    /**
     * Constructor expecting to wrap another exception.
     * @param except exception to wrap
     */
    public HelperException(final Exception except) {
        super(except);
    }

    /**
     * Constructor taking a text message.
     * @param message text error message
     */
    public HelperException(final String message) {
        super(message);
    }
}
