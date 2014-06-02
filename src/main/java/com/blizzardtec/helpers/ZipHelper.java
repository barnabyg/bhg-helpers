/**
 * 
 */
package com.blizzardtec.helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Utility class for packing and unpacking ZIP format files.
 * This includes WAR and JAR files.
 *
 * @author Barnaby Golden
 *
 */
public final class ZipHelper {

    /**
     * Private constructor denotes utility class.
     */
    private ZipHelper() {
        
    }

    /**
     * Expand a .war file to a specified location.
     *
     * @param warFile war file to expand
     * @param destDir destination directory
     * @throws HelperException thrown
     */
    public static void expandWarFile(final File warFile, final String destDir)
                                    throws HelperException {

        try {
            final JarFile jar = new JarFile(warFile);

            final Enumeration<JarEntry> enumeration = jar.entries();

            while (enumeration.hasMoreElements()) {

                final JarEntry entry = enumeration.nextElement();

                final File file = new File(
                        destDir + File.separator + entry.getName());

             // if its a directory, create it
                if (entry.isDirectory()) {
                    file.mkdir();
                    continue;
                }

                // get the input stream
                final InputStream iStream = jar.getInputStream(entry);

                final FileOutputStream fos = new FileOutputStream(file);

             // write contents of input stream to output stream
                while (iStream.available() > 0) {
                    fos.write(iStream.read());
                }

                fos.close();
                iStream.close();
            }            
        } catch (IOException ioe) {
            throw new HelperException(ioe);
        }
    }
}
