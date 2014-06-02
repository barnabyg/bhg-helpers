/**
 * 
 */
package com.blizzardtec.helpers;

import java.io.File;

/**
 * General purpose helper class relating to String operations.
 *
 * @author Barnaby Golden
 *
 */
public final class StringHelper {

    /**
     * Private constructor denotes utility class.
     */
    private StringHelper() {
        
    }
    
    /**
     * Convert a package string (e.g. com.blizzardtec.plugin)
     * in dot notation to a system path string (e.g. com/blizzardtec/plugin).
     *
     * @param packageName group id in the form of a package name
     * @return path string
     */
    public static String convertPackageToPath(final String packageName) {

        final StringBuffer buf = new StringBuffer();

        if (packageName != null) {
            final String[] tokens = packageName.split("\\.");
            for (int i = 0; i < tokens.length; i++) {
                buf.append(tokens[i]);
                if (i < tokens.length - 1) {
                    buf.append(File.separator);
                }
            }
        }

        return buf.toString();
    }
}
