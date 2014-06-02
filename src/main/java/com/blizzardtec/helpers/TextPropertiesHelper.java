/**
 * 
 */
package com.blizzardtec.helpers;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * General purpose utility class for operating on text based property
 * files.
 *
 * @author Barnaby Golden
 *
 */
public final class TextPropertiesHelper {

    /**
     * File configuration.
     */
    private final transient FileConfiguration fileConfig;

    /**
     * Constructor takes the property file as an argument.
     *
     * @param propertyFile property file
     */
    public TextPropertiesHelper(final File propertyFile) {
        fileConfig = new PropertiesConfiguration();
        fileConfig.setFile(propertyFile);

    }

    /**
     * Load the properties file.
     *
     * @throws HelperException thrown
     */
    public void load() throws HelperException {
        try {
            fileConfig.load();
        } catch (ConfigurationException cfe) {
            throw new HelperException(cfe);
        }        
    }

    /**
     * Update a single value in a property file (or add the name-value pair
     * if it does not exist).
     *
     * @param name name tag
     * @param value value associated with name tag
     * @throws HelperException thrown
     */
    public void setProperty(final String name, final String value) 
                    throws HelperException {

        try {
            fileConfig.setProperty(name, value);
            fileConfig.save();
        } catch (ConfigurationException cfe) {
            throw new HelperException(cfe);
        }
    }

    /**
     * Get the value of a given property.
     *
     * @param name the name of the property to find.
     * @return property value or null if not found
     * @throws HelperException thrown
     */
    public String getProperty(final String name)
                    throws HelperException {

        String value = null;

//        try {
            value = fileConfig.getString(name);
//        } catch (ConfigurationException cfe) {
//            throw new HelperException(cfe);
//        }

        return value;
    }
}
