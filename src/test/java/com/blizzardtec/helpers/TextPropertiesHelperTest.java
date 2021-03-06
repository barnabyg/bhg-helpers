/**
 *
 */
package com.blizzardtec.helpers;

import static org.junit.Assert.assertEquals;

import java.io.File;
import org.junit.Test;

/**
 * @author Barnaby Golden
 *
 */
public final class TextPropertiesHelperTest extends AbstractTest {

    /**
     * Mod search.
     */
    private static final String MODSEARCH = "moduleSearchPathFolders";
    /**
     * Test properties file.
     */
    private static final String PROP_FILE = "test-properties.txt";
    /**
     * Test property value.
     */
    private static final String VALUE = "dogboy";

    /**
     * Test the update of a property in a text properties file.
     *
     * @throws HelperException thrown
     */
    @Test
    public void setPropertyTest() throws HelperException {

        final String filePath = getBaseDir()
                + File.separator
                + "src" + File.separator
                + "test" + File.separator + PROP_FILE;

        final File propFile = new File(filePath);

        final TextPropertiesHelper helper = new TextPropertiesHelper(propFile);

        helper.setProperty(MODSEARCH, VALUE);

        final String value =
            helper.getProperty(MODSEARCH);

        assertEquals("Invalid value for property", VALUE, value);
    }
}
