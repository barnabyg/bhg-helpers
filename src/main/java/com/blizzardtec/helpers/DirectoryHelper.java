/**
 *
 */
package com.blizzardtec.helpers;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

/**
 * General purpose helper for directory operations.
 *
 * @author Barnaby Golden
 *
 */
public final class DirectoryHelper {

    /**
     * Private constructor to denote utility class.
     */
    private DirectoryHelper() {

    }

    /**
     * Copy a directory and its contents to a new location.
     *
     * @param srcPath
     *            source directory path
     * @param destPath
     *            destination directory path
     * @throws HelperException
     *             thrown
     */
    public static void copyDirectory(final String srcPath,
            final String destPath) throws HelperException {

        final File sourceDir = new File(srcPath);
        final File destDir = new File(destPath + File.separator
                + sourceDir.getName());

        copyDirectory(sourceDir, destDir);
    }

    /**
     * Copy a directory and its contents to a new location.
     *
     * @param srcDir
     *            source directory
     * @param dstDir
     *            destination directory
     * @throws HelperException
     *             thrown
     */
    private static void copyDirectory(final File srcDir, final File dstDir)
            throws HelperException {

        try {
            FileUtils.copyDirectory(srcDir, dstDir);
        } catch (IOException ioe) {
            throw new HelperException(ioe);
        }
    }

    /**
     * Copy the contents of a directory to a new directory location.
     *
     * @param srcDir the source directory
     * @param dstDir the target directory
     * @throws HelperException thrown
     */
    public static void copyDirectoryContents(
            final File srcDir, final File dstDir)
                                throws HelperException {

        if (srcDir.isDirectory()) {

            if (!dstDir.exists()) {
                throw new HelperException(
                        "destination directory does not exist: "
                                                + dstDir.getPath());
            }

            final String[] files = srcDir.list();

            for (int i = 0; i < files.length; i++) {
                copyDirectoryContents(
                   new File(srcDir, files[i]), new File(dstDir, files[i]));
            }
        } else {
            if (srcDir.exists()) {
                FileHelper.copyFile(srcDir, dstDir);
            } else {
                throw new HelperException(
                        "Source file or directory does not exist: " + srcDir);
            }
        }
    }

    /**
     * Return a list of files in the given directory and recursively
     * in to any sub-directories.
     *
     * @param srcPath
     *            directory path
     * @return list of files
     */
    @SuppressWarnings("unchecked")
    public static Collection<File> recursiveDirContents(final String srcPath) {
        final File dir = new File(srcPath);

        return (Collection<File>) FileUtils.listFiles(dir, null, true);
    }

    /**
     * Return a list of files in the given directory only, ignoring
     * sub-directories.
     *
     * @param srcPath
     *            directory path
     * @return list of files
     */
    @SuppressWarnings("unchecked")
    public static Collection<File> dirContents(final String srcPath) {
        final File dir = new File(srcPath);

        return (Collection<File>) FileUtils.listFiles(dir, null, false);
    }

    /**
     * Delete a directory and its contents recursively.
     *
     * @param dir target directory
     * @throws HelperException thrown
     */
    public static void deleteDir(final File dir)
                                throws HelperException {
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException ioe) {
            throw new HelperException(ioe);
        }
    }

    /**
     * Create a directory in the specified location.
     *
     * @param path location to create new directory
     * @param dirName new directory name
     */
    public static void createDirectory(
            final String path, final String dirName) {
        final File newDir = new File(path + File.separator + dirName);

        if (!newDir.exists()) {
            newDir.mkdir();
        }
    }
}
