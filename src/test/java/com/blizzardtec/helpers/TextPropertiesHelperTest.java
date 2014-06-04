/**
 *
 */
package com.blizzardtec.helpers;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
     * Setup for test.
     */
    @BeforeClass
    public static void setUp() {

        // make the scratch working directory
        final File scratch = new File(
                    getBaseDir() + File.separator + "scratch");

        scratch.mkdir();
    }

    /**
     * Test the update of a property in a text properties file.
     *
     * @throws HelperException thrown
     */
    @Test
    public void setPropertyTest() throws HelperException {

        // first copy the test properties file to the test directory
        final String testDir = getBaseDir() + File.separator + "testdir";
        final String propPath =
            getBaseDir() + File.separator + PROP_FILE;

        FileHelper.copyFile(propPath, testDir);

        final File propFile = new File(testDir + File.separator + PROP_FILE);

        final TextPropertiesHelper helper = new TextPropertiesHelper(propFile);

        helper.setProperty(MODSEARCH, VALUE);

        final String value =
            helper.getProperty(MODSEARCH);

        assertEquals("Invalid value for property", VALUE, value);

        // cleanup
        propFile.delete();
    }

    /**
     * Cleanup after test.
     * @throws IOException thrown
     */
    @AfterClass
    public static void tearDown() throws IOException {

        FileUtils.deleteDirectory(
                new File(getBaseDir() + File.separator + "scratch"));
    }
}
