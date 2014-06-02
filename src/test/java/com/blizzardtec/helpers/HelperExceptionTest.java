/**
 * 
 */
package com.blizzardtec.helpers;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * @author Barnaby Golden
 *
 */
public final class HelperExceptionTest {

    /**
     * Test the constructors of the HelperException.
     */
    @Test
    public void helperExceptionTest() {
        final HelperException hee = new HelperException("some message");
        assertNotNull("HelperException was null", hee);
        final HelperException hee2 = new HelperException(hee);
        assertNotNull("HelperException null", hee2);
    }
}
