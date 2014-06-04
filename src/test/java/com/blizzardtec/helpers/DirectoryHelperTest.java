/**
 *
 */
package com.blizzardtec.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Barnaby Golden
 *
 */
public final class DirectoryHelperTest extends AbstractTest {

    /**
     * First test file.
     */
    private static final String TEST1TXT = "test1.txt";
    /**
     * Second test file.
     */
    private static final String TEST2TXT = "test2.txt";
    /**
     * Dir to be copied.
     */
    private static File fullDir;
    /**
     * Sub directory to be copied.
     */
    private static File subDir;
    /**
     * Destination for recursive dir copy.
     */
    private static File destDir;
    /**
     * Working directory for tests.
     */
    private static File scratch;

    /**
     * Setup tests.
     * @throws IOException thrown
     */
    @BeforeClass
    public static void setUp() throws IOException {

        // make the scratch working directory
        scratch = new File(
                    getBaseDir() + File.separator + "scratch");

        scratch.mkdir();

        // make a directory for the dir copy test
        // and a sub-directory to check recursive copy
        fullDir = new File(
                scratch.getPath() + File.separator + "fulldir");

        fullDir.mkdir();

        subDir = new File(
                fullDir.getPath() + File.separator + "subdir");

        subDir.mkdir();

        // create a test file in the dir to be copied
        // and one in the sub-directory
        final File copyFile1 = new File(
                fullDir.getPath() + File.separator + TEST1TXT);

        copyFile1.createNewFile();

        final File copyFile2 = new File(
                subDir.getPath() + File.separator + TEST2TXT);

        copyFile2.createNewFile();

        // create a destination directory for recursive copy
        destDir = new File(
                scratch.getPath() + File.separator + "destdir");

        destDir.mkdir();
    }

    /**
     * Test the directory contents listing.
     */
    @Test
    public void directoryContentsTest() {

        final Collection<File> files =
            DirectoryHelper.dirContents(fullDir.getPath());

        assertEquals("Did not find the one file expected", 1, files.size());

        boolean flag = false;

        final Iterator<File> iterator = files.iterator();

        while (iterator.hasNext()) {
            if (TEST1TXT.equalsIgnoreCase(iterator.next().getName())) {
                flag = true;
            }
        }

        assertTrue("The Test file was not found", flag);
    }

    /**
     * Test the directory contents listing.
     */
    @Test
    public void recursiveDirContentsTest() {

        final Collection<File> files =
            DirectoryHelper.recursiveDirContents(fullDir.getPath());

        assertEquals("Did not find the two expected files", 2, files.size());

        boolean flag = false;

        final Iterator<File> iterator = files.iterator();

        while (iterator.hasNext()) {

            final String fileName = iterator.next().getName();

            if (TEST1TXT.equalsIgnoreCase(fileName)) {
                flag = true;
            }

            if (TEST2TXT.equalsIgnoreCase(fileName)) {
                flag = true;
            }
        }

        assertTrue("Test file(s) not found", flag);
    }

    /**
     * Test the copying of a directory.
     * @throws HelperException thrown
     */
    @Test
    public void copyDirectoryTest() throws HelperException {

        DirectoryHelper.copyDirectory(fullDir.getPath(), destDir.getPath());

        // check the existence of the copied directory
        final File sourceDir = new File(fullDir.getPath());

        final File copiedDir =
            new File(destDir.getPath() + File.separator + sourceDir.getName());

        assertTrue("Copied directory does not exist", copiedDir.exists());

        final File copiedFile =
            new File(copiedDir.getPath() + File.separator + TEST1TXT);

        assertTrue("In recursive directory copy,"
                    + " the copied file does not exist", copiedFile.exists());
    }

    /**
     * Test the creation of a directory.
     */
    @Test
    public void createDirectoryTest() {

        DirectoryHelper.createDirectory(scratch.getPath(), "tmpdir");

        final File newDir =
            new File(scratch.getPath() + File.separator + "tmpdir");

        assertTrue("Directory not created", newDir.exists());
    }

    /**
     * Test the copying of directory contents to a new location.
     *
     * @throws HelperException thrown
     * @throws IOException thrown
     */
    @Test
    public void copyDirectoryContentsTest()
                throws HelperException, IOException {

        final File srcDir = new File(getBaseDir() + File.separator + "ccsrc");
        srcDir.mkdir();
        final File dstDir = new File(getBaseDir() + File.separator + "ccdst");
        dstDir.mkdir();

        final File srcFile1 =
            new File(srcDir.getPath() + File.separator + TEST1TXT);
        srcFile1.createNewFile();
        final File srcFile2 =
            new File(srcDir.getPath() + File.separator + TEST2TXT);
        srcFile2.createNewFile();

        DirectoryHelper.copyDirectoryContents(srcDir, dstDir);

        final File dstFile1 =
            new File(dstDir.getPath() + File.separator + TEST1TXT);

        assertTrue("First file to be copied did not exist in target directory",
                dstFile1.exists());

        final File dstFile2 =
            new File(dstDir.getPath() + File.separator + TEST2TXT);

        assertTrue("Second file to be copied did not exist in target directory",
                dstFile2.exists());

        // cleanup
        DirectoryHelper.deleteDir(srcDir);
        DirectoryHelper.deleteDir(dstDir);
    }

    /**
     * Tear down test fixtures.
     * @throws IOException thrown
     */
    @AfterClass
    public static void tearDown() throws IOException {

        FileUtils.deleteDirectory(
                new File(getBaseDir() + File.separator + "scratch"));
    }
}
