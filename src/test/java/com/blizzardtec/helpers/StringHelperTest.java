/**
 * 
 */
package com.blizzardtec.helpers;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

/**
 * @author Barnaby Golden
 *
 */
public final class StringHelperTest {

    /**
     * Test the conversion of a dot notation package name
     * to a system file path.
     */
    @Test
    public void convertPackageToPathTest() {

        final String path =
            StringHelper.convertPackageToPath("dog.frog.badger");

        final String result =
            "dog" + File.separator + "frog" + File.separator + "badger";

        assertEquals("Path did not match package", result, path);
    }
}
