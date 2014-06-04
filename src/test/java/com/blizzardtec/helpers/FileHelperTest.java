/**
 *
 */
package com.blizzardtec.helpers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Barnaby Golden
 *
 */
public final class FileHelperTest extends AbstractTest {

    /**
     * Test file to be copied.
     */
    private static final String TEST_FILE = "copy-test-file.txt";
    /**
     * Test file to be renamed and copied.
     */
    private static final String RENAME_FILE = "rename.txt";
    /**
     * File extension to use in tests.
     */
    private static final String TEST_EXTENSION = "doc";
    /**
     * File to be used in copy files test.
     */
    private static final String COPY_FILE1 = "file1.txt";
    /**
     * File to be used in copy files test.
     */
    private static final String COPY_FILE2 = "file2.doc";

    /**
     * Copy file.
     */
    private static File copyTestFile;
    /**
     * Destination dir for single file copy.
     */
    private static File copyFileDir2;
    /**
     * First file of multiple copy.
     */
    private static File copyTestFiles1;
    /**
     * Second file of multiple copy.
     */
    private static File copyTestFiles2;
    /**
     * Destination dir for multiple file copy.
     */
    private static File copyFilesDir2;
    /**
     * File to be used in rename and copy test.
     */
    private static File renameTestFile;

    /**
     * Create the files and directories needed for the tests.
     * @throws IOException thrown
     */
    @BeforeClass
    public static void setUp() throws IOException {

        // make the scratch working directory
        final File scratch = new File(
                    getBaseDir() + File.separator + "scratch");

        scratch.mkdir();

        // make the four directories needed for the tests
        final File copyFileDir1 = new File(
                scratch.getPath() + File.separator + "copyfiledir1");

        copyFileDir1.mkdir();

        copyFileDir2 = new File(
                scratch.getPath() + File.separator + "copyfiledir2");

        copyFileDir2.mkdir();

        final File copyFilesDir1 = new File(
                scratch.getPath() + File.separator + "copyfilesdir1");

        copyFilesDir1.mkdir();

        copyFilesDir2 = new File(
                scratch.getPath() + File.separator + "copyfilesdir2");

        copyFilesDir2.mkdir();

        // make the file to be copied on its own
        copyTestFile = new File(
                copyFileDir1.getPath() + File.separator + TEST_FILE);

        copyTestFile.createNewFile();

        renameTestFile = new File(
                copyFileDir1.getPath() + File.separator + RENAME_FILE);

        renameTestFile.createNewFile();

        // and make two files for the multiple file copy test
        copyTestFiles1 = new File(
                copyFilesDir1.getPath() + File.separator + COPY_FILE1);

        copyTestFiles1.createNewFile();

        copyTestFiles2 = new File(
                copyFilesDir1.getPath() + File.separator + COPY_FILE2);

        copyTestFiles2.createNewFile();
    }

    /**
     * Test the extraction of a file extension from a filename.
     */
    @Test
    public void fileExtensionTest() {

        final String extension = FileHelper.fileExtension(TEST_FILE);

        assertTrue("Incorrect file extension", "txt".equals(extension));
    }

    /**
     * Test that files with a given extension are selected.
     */
    @Test
    public void selectFilesByExtensionTest() {
        final Collection<File> inputList = new ArrayList<File>();

        inputList.add(new File("firstfile.txt"));
        inputList.add(new File("secondfile.doc"));
        inputList.add(new File("thirdfile.doc"));

        final Collection<File> resultList =
            FileHelper.selectFilesByExtension(inputList, TEST_EXTENSION);

        assertEquals("Incorrect number of files selected",
                                        2, resultList.size());
    }

    /**
     * Copy a file test.
     *
     * @throws HelperException thrown
     * @throws IOException thrown
     */
    @Test
    public void copyFileTest() throws HelperException, IOException {

        FileHelper.copyFile(copyTestFile.getPath(), copyFileDir2.getPath());

        final File testFile = new File(copyFileDir2.getPath());

        assertTrue(
              "The copy file did not appear in the target directory",
                                                       testFile.exists());
    }

    /**
     * Test the copying of a set of files to a given location.
     * @throws HelperException thrown
     */
    @Test
    public void copyFilesTest() throws HelperException {

        final Collection<File> files = new ArrayList<File>();

        files.add(copyTestFiles1);
        files.add(copyTestFiles2);

        FileHelper.copyFiles(files, copyFilesDir2.getPath());

        final File targetDir = new File(copyFilesDir2.getPath());

        final String[] fileList = targetDir.list();
        boolean flag = false;

        for (int i = 0; i < fileList.length; i++) {
            if ((COPY_FILE1.equalsIgnoreCase(fileList[i]))
                    || (COPY_FILE2.equalsIgnoreCase(fileList[i]))) {
                flag = true;
            }
        }

        assertTrue("Copied files not found", flag);
    }

    /**
     * Test the saving of an XML document.
     */
    public void saveXMLDocumentTest() {
        //:TODO
    }

    /**
     * Copy and rename a file test.
     *
     * @throws HelperException thrown
     */
    @Test
    public void copyAndRenameTest()
        throws HelperException {

        final String renameTarget =
                copyFileDir2.getPath() + File.separator + "frog.dog";

        FileHelper.copyAndRenameFile(renameTestFile.getPath(), renameTarget);

        final File targetFile = new File(renameTarget);

        assertTrue("renamed file does not exist", targetFile.exists());
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
