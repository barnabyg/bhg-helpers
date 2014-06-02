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

import org.junit.Test;

/**
 * @author Barnaby Golden
 *
 */
public final class DirectoryHelperTest extends AbstractTest {

    /**
     * Test directory.
     */
    private static final String TESTDIR = "test-directory";
    /**
     * First test file.
     */
    private static final String TEST1TXT = "test1.txt";
    /**
     * Second test file.
     */
    private static final String TEST2TXT = "test2.txt";
    /**
     * File that forms the contents of directory to be copied.
     */
    private static final String COPY_FILE = "copydircontents.txt";
    /**
     * Test file name.
     */
    private static final String TEST_FILE = "copy-test-file.txt";
    /**
     * Test file name.
     */
    private static final String TEST_FILE2 = "copy-test-file2.txt";
    /**
     * Path to the first test directory.
     */
    private final transient String dir1Path;
    /**
     * Path to the copy-directory source directory.
     */
    private final transient String copy1Path;
    /**
     * Path to the copy-directory target directory.
     */
    private final transient String copy2Path;

    /**
     * Constructor - builds some useful paths.
     */
    public DirectoryHelperTest() {
        super();
        dir1Path = getBaseDir() + File.separator + "dir1";
        copy1Path = getBaseDir() + File.separator + "copysrcdir";
        copy2Path = getBaseDir() + File.separator + "copydstdir";
    }

    /**
     * Test the directory contents listing.
     */
    @Test
    public void directoryContentsTest() {
        final Collection<File> files =
            DirectoryHelper.dirContents(dir1Path);

        assertEquals("", 1, files.size());

        boolean flag = false;

        final Iterator<File> iterator = files.iterator();

        while (iterator.hasNext()) {
            if (TEST_FILE.equalsIgnoreCase(iterator.next().getName())) {
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
            DirectoryHelper.recursiveDirContents(dir1Path);

        boolean flag = false;

        final Iterator<File> iterator = files.iterator();

        while (iterator.hasNext()) {
            if (TEST_FILE2.equalsIgnoreCase(iterator.next().getName())) {
                flag = true;
            }            
        }

        assertTrue("Test file not found", flag);
    }

    /**
     * Test the copying of a directory.
     * @throws HelperException thrown
     */
    @Test
    public void copyDirectoryTest() throws HelperException {
        DirectoryHelper.copyDirectory(copy1Path, copy2Path);

        // check the existence of the copied directory
        final File sourceDir = new File(copy1Path);
        final File copiedDir =
            new File(copy2Path + File.separator + sourceDir.getName());

        assertTrue("Copied directory does not exist", copiedDir.exists());
        final File copiedFile =
            new File(copiedDir.getPath() + File.separator + COPY_FILE);
        assertTrue("Copied file does not exist", copiedFile.exists());

        // cleanup
        DirectoryHelper.deleteDir(copiedDir);
    }

    /**
     * Test the creation of a directory.
     */
    @Test
    public void createDirectoryTest() {
        DirectoryHelper.createDirectory(getBaseDir(), TESTDIR);

        final File newDir =
            new File(getBaseDir() + File.separator + TESTDIR);

        assertTrue("Directory not created", newDir.exists());

        // cleanup
        newDir.delete();
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
}
