/**
 *
 */
package com.blizzardtec.helpers;

import static org.junit.Assert.assertTrue;

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
public final class ZipHelperTest extends AbstractTest {

    /**
     * Test war file.
     */
    private static final String WAR_FILE = "test.war";
    /**
     * Working directory for tests.
     */
    private static File scratch;
    /**
     * Create the directory needed for the test.
     */
    @BeforeClass
    public static void setUp() {

        // make the scratch working directory
        scratch = new File(
                    getBaseDir() + File.separator + "scratch");

        scratch.mkdir();
    }

    /**
     * Test the extraction of a .war file.
     *
     * @throws HelperException thrown
     */
    @Test
    public void extractWarTest() throws HelperException {

        final String warPath = getBaseDir() + File.separator
                + "src" + File.separator
                + "test" + File.separator
                + "resources" + File.separator + WAR_FILE;

        final File warFile = new File(warPath);

        ZipHelper.expandWarFile(warFile, scratch.getPath());

        final File metaInf = new File(
                scratch.getPath() + File.separator + "META-INF");

        assertTrue("META-INF file not found", metaInf.exists());
    }

    /**
     * Tear down directories and files created in the testing.
     * @throws IOException thrown
     */
    @AfterClass
    public static void tearDown() throws IOException {

        FileUtils.deleteDirectory(
                new File(getBaseDir() + File.separator + "scratch"));
    }
}
