/**
 *
 */
package com.blizzardtec.helpers;

import java.io.File;

/**
 * @author Barnaby Golden
 *
 */
public abstract class AbstractTest {

    /**
     * Base directory.
     */
    private final transient String baseDir;

    /**
     * Constructor - assigns the base directory.
     */
    public AbstractTest() {
        baseDir = System.getProperty("user.dir") + File.separator + "src"
                    + File.separator + "test" + File.separator + "resources";
    }

    /**
     * @return the baseDir
     */
    public final String getBaseDir() {
        return baseDir;
    }
}
