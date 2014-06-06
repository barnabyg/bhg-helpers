/**
 *
 */
package com.blizzardtec.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

/**
 * General purpose utility class for file operations.
 *
 * @author Barnaby Golden
 *
 */
public final class FileHelper {

    /**
     * Private constructor to denote utility class.
     */
    private FileHelper() {

    }

    /**
     * Copy a file from source to destination directory.
     *
     * @param srcPath
     *            fully qualified file path
     * @param destDirPath
     *            path to destination directory
     * @throws HelperException
     *             thrown
     */
    public static void copyFile(final String srcPath, final String destDirPath)
            throws HelperException {

        final File sourceFile = new File(srcPath);
        final File destDir = new File(destDirPath);

        if ((sourceFile.exists()) && (destDir.exists())) {

            final File destFile = new File(destDir + File.separator
                    + sourceFile.getName());

            copyFile(sourceFile, destFile);

        }

        if (!sourceFile.exists()) {
            throw new HelperException("Copy source file does not exist: "
                                                      + sourceFile.getPath());
        }

        if (!destDir.exists()) {
            throw new HelperException("Copy destination dir does not exist: "
                                                          + destDir.getPath());
        }
    }

    /**
     * Copy a file to a new location and with a new filename.
     *
     * @param srcPath
     *            path to the source file
     * @param targetPath
     *            name and path of the target file
     * @throws HelperException
     *             thrown
     */
    public static void copyAndRenameFile(final String srcPath,
            final String targetPath) throws HelperException {

        final File sourceFile = new File(srcPath);
        final File destFile = new File(targetPath);

        if (sourceFile.exists()) {
            copyFile(sourceFile, destFile);
        } else {
            throw new HelperException("Source file of copy does not exist");
        }
    }

    /**
     * Copy a file from source to destination directory.
     *
     * @param sourceFile
     *            fully qualified file path
     * @param destFile
     *            path to destination directory
     * @throws HelperException
     *             thrown
     */
    public static void copyFile(final File sourceFile, final File destFile)
            throws HelperException {

        if (sourceFile.exists()) {
            try {
                FileUtils.copyFile(sourceFile, destFile);
            } catch (IOException ioe) {
                throw new HelperException(ioe);
            }
        } else {
            throw new HelperException("Copy source file does not exist");
        }
    }

    /**
     * Delete the specified file.
     *
     * @param srcPath
     *            fully qualified file path
     * @throws HelperException
     *             thrown
     */
    public static void deleteFile(final String srcPath) throws HelperException {

        final File targetFile = new File(srcPath);

        // ensure the target file exists
        if (!targetFile.exists()) {
            throw new HelperException("deletion target file does not exist: "
                    + srcPath);
        }
        final boolean result = targetFile.delete();
        if (!result) {
            throw new HelperException("unable to delete file: " + srcPath);
        }
    }

    /**
     * Find the file extension of a given filename.
     *
     * @param filename
     *            the file to use
     * @return file extension of empty string if no extension
     */
    public static String fileExtension(final String filename) {
        String extension = null;
        if (filename.lastIndexOf('.') == -1) {
            extension = "";
        } else {
            extension = filename.substring(filename.lastIndexOf('.') + 1,
                    filename.length());
        }

        return extension;
    }

    /**
     * Select the files of a given extension from a list of files.
     *
     * @param allFiles
     *            the list of files to use
     * @param extension
     *            the extension to check for
     * @return the collection of files of the given extension
     */
    public static Collection<File> selectFilesByExtension(
            final Collection<File> allFiles, final String extension) {

        final Collection<File> collection = new ArrayList<File>();

        final Iterator<File> iterator = allFiles.iterator();

        while (iterator.hasNext()) {
            final File file = iterator.next();

            if (extension.equalsIgnoreCase(FileHelper.fileExtension(file
                    .getName()))) {

                collection.add(file);
            }
        }

        return collection;
    }

    /**
     * Copy a set of files to the given destination.
     *
     * @param files
     *            list of files
     * @param destPath
     *            fully qualified destination directory
     * @throws HelperException
     *             thrown on error from file copy
     */
    public static void copyFiles(final Collection<File> files,
            final String destPath) throws HelperException {

        final Iterator<File> iterator = files.iterator();

        while (iterator.hasNext()) {
            copyFile(iterator.next().getPath(), destPath);
        }
    }

    /**
     * Strip the file name out of a fully qualified file path.
     *
     * @param filePath
     *            path
     * @return file name
     */
    public static String filenameFromPath(final String filePath) {
        final String[] tokens = filePath.split("\\\\");
        return tokens[tokens.length - 1];
    }

    /**
     * Load a file and return a list of strings representing the lines in the
     * file.
     *
     * @param srcPath
     *            file name
     * @return list of strings
     * @throws HelperException
     *             thrown on IO error
     */
    public static List<String> loadFile(final String srcPath)
            throws HelperException {
        final List<String> list = new ArrayList<String>();
        BufferedReader inReader = null;
        try {
            inReader = new BufferedReader(new FileReader(srcPath));
            String line = "";
            while ((line = inReader.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception ex) {
            throw new HelperException(ex);
        } finally {
            try {
                if (inReader != null) {
                    inReader.close();
                }
            } catch (IOException ex) {
                // no action on finally block error
                // but should log this really
                Logger.getLogger("");
            }
        }

        return list;
    }
}
