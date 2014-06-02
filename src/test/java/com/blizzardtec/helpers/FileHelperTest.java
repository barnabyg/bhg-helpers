/**
 * 
 */
package com.blizzardtec.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Barnaby Golden
 *
 */
public final class FileHelperTest extends AbstractTest {

    /**
     * Test file name.
     */
    private static final String TEST_FILE = "copy-test-file.txt";
    /**
     * File extension to use in tests.
     */
    private static final String TEST_EXTENSION = "doc";
    /**
     * Path to the first test directory.
     */
    private final transient String dir1Path;
    /**
     * Path to the second test directory.
     */
    private final transient String dir2Path;
    /**
     * Location of directory containing files to be copied in
     * copy files test.
     */
    private final transient String copyFiles1;
    /**
     * Target directory for files in copy files test.
     */
    private final transient String copyFiles2;
    /**
     * File to be used in copy files test.
     */
    private static final String COPY_FILE1 = "file1.txt";
    /**
     * File to be used in copy files test.
     */
    private static final String COPY_FILE2 = "file2.doc";

    /**
     * Constructor - assigns the current working directory.
     */
    public FileHelperTest() {
        super();

        dir1Path = getBaseDir() + File.separator + "dir1";
        dir2Path = getBaseDir() + File.separator + "dir2";
        copyFiles1 = getBaseDir() + File.separator + "copyfilessrcdir";
        copyFiles2 = getBaseDir() + File.separator + "copyfilesdstdir";
    }

    /**
     * Copy a file test. Also deletes a file.
     * @throws HelperException thrown
     */
    @Test
    public void copyFileTest() throws HelperException {
        FileHelper.copyFile(
                dir1Path + File.separator + TEST_FILE, dir2Path);

        final File testFile = new File(dir2Path + File.separator + TEST_FILE);
        assertTrue("The copy file did not appear in the target directory",
                                                        testFile.exists());

        FileHelper.deleteFile(dir2Path + File.separator + TEST_FILE);

        assertFalse("The copy file was not deleted", testFile.exists());
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
     * Test the copying of a set of files to a given location.
     * @throws HelperException thrown
     */
    @Test
    public void copyFilesTest() throws HelperException {

        final Collection<File> files = new ArrayList<File>();

        File file1 = new File(copyFiles1 + File.separator + COPY_FILE1);
        File file2 = new File(copyFiles1 + File.separator + COPY_FILE2);

        files.add(file1);
        files.add(file2);        

        FileHelper.copyFiles(files, copyFiles2);

        final File targetDir = new File(copyFiles2);
        final String[] fileList = targetDir.list();
        boolean flag = false;
        for (int i = 0; i < fileList.length; i++) {
            if ((COPY_FILE1.equalsIgnoreCase(fileList[i]))
                    || (COPY_FILE2.equalsIgnoreCase(fileList[i]))) {
                flag = true;
            }
        }
        assertTrue("Copied files not found", flag);

        // cleanup
        file1 = new File(copyFiles2 + File.separator + COPY_FILE1);
        file2 = new File(copyFiles2 + File.separator + COPY_FILE2);
        file1.delete();
        file2.delete();
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

        final String srcPath = getBaseDir() + File.separator + "test.xml";
        final String targetPath = getBaseDir() + File.separator + "frog.dog";

        FileHelper.copyAndRenameFile(srcPath, targetPath);

        final File targetFile = new File(targetPath);

        assertTrue("renamed file does not exist", targetFile.exists());

        // cleanup
        targetFile.delete();
    }
}
