/**
 *
 */
package com.blizzardtec.helpers;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

/**
 * @author Barnaby Golden
 *
 */
public final class ZipHelperTest extends AbstractTest {

    /**
     * Directory to expand war file in.
     */
    private static final String WAR_DIR = "warDir";
    /**
     * Test war file.
     */
    private static final String WAR_FILE = "test.war";

    /**
     * Test the extraction of a .war file.
     *
     * @throws HelperException thrown
     */
    @Test
    public void extractWarTest() throws HelperException {

        final File warFile = new File(getBaseDir() + File.separator + WAR_FILE);

        final File expandDir =
            new File(getBaseDir() + File.separator + WAR_DIR);
        expandDir.mkdir();

        ZipHelper.expandWarFile(warFile, expandDir.getPath());

        final File metaInf = new File(expandDir + File.separator + "META-INF");

        assertTrue("", metaInf.exists());

        // cleanup
        DirectoryHelper.deleteDir(expandDir);
    }
}
