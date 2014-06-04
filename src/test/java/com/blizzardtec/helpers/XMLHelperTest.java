/**
 *
 */
package com.blizzardtec.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Barnaby Golden
 *
 */
public final class XMLHelperTest extends AbstractTest {

    /**
     * Parent.
     */
    private static final String PARENTTEXT = "parentText";
    /**
     * child.
     */
    private static final String CHILD1TEXT = "child1Text";
    /**
     * child.
     */
    private static final String CHILD2TEXT = "child2Text";
    /**
     * Parent.
     */
    private static final String PARENT = "parent";
    /**
     * child.
     */
    private static final String CHILD1 = "child1";
    /**
     * child.
     */
    private static final String CHILD2 = "child2";
    /**
     * Name.
     */
    private static final String BOB = "bob";
    /**
     * Type.
     */
    private static final String DOG = "dog";
    /**
     * Name.
     */
    private static final String NAME = "name";
    /**
     * Type.
     */
    private static final String TYPE = "type";
    /**
     * Colour.
     */
    private static final String COLOUR = "colour";
    /**
     * Blue.
     */
    private static final String BLUE = "blue";
    /**
     * Test XML file.
     */
    private static final String XML_FILE = "test.xml";
    /**
     * Working folder for tests.
     */
    private static File scratch;

    /**
     * Setup test.
     */
    @BeforeClass
    public static void setUp() {

        // make the scratch working directory
        scratch = new File(
                    getBaseDir() + File.separator + "scratch");

        scratch.mkdir();
    }

    /**
     * Get an XML document test.
     * @throws HelperException thrown
     */
    @Test
    public void getDocumentTest() throws HelperException {

        final Document doc = XMLHelper.getDocument();

        assertNotNull("Document was null", doc);
    }

    /**
     * Test the loading of an XML file.
     * @throws HelperException thrown
     */
    @Test
    public void loadXMLFileTest() throws HelperException {

        final String filePath = getBaseDir() + File.separator
                + "src" + File.separator
                + "test" + File.separator
                + "resources" + File.separator + XML_FILE;

        final File xmlFile = new File(filePath);

        final Document doc = XMLHelper.loadXMLFile(xmlFile);

        assertNotNull("XML document from file was null", doc);
    }

    /**
     * Save an XML file test.
     * @throws HelperException thrown
     */
    @Test
    public void saveXMLFileTest() throws HelperException {

        final String filename = scratch.getPath() + File.separator + "blah.xml";

        XMLHelper.saveXMLFile(XMLHelper.getDocument(), filename);

        final File xmlFile = new File(filename);

        assertTrue("Unable to find created XML file", xmlFile.exists());
    }

    /**
     * Test the extraction of a node from a node list.
     * Also tests the extraction of the text content of the node.
     *
     * @throws HelperException thrown
     */
    @Test
    public void getNodeFromListTest() throws HelperException {

        // create a simple node list
        final Document doc = XMLHelper.getDocument();
        final Element parent = doc.createElement(PARENT);
        parent.setTextContent(PARENTTEXT);
        final Element child1 = doc.createElement(CHILD1);
        child1.setTextContent(CHILD1TEXT);
        final Element child2 = doc.createElement(CHILD2);
        child2.setTextContent(CHILD2TEXT);

        parent.appendChild(child1);
        parent.appendChild(child2);

        final NodeList nodeList = parent.getChildNodes();
        final Node found = XMLHelper.getNodeFromList(nodeList, CHILD2);
        assertNotNull("child node not found", found);

        final String child2Text =
            XMLHelper.getNodeTextFromList(nodeList, CHILD2);

        assertEquals("child node text did not match", CHILD2TEXT, child2Text);
    }

    /**
     * Test the retrieval of an attribute from a map of attributes.
     *
     * @throws HelperException thrown
     */
    @Test
    public void getAttributeFromListTest() throws HelperException {

        // create a simple node with attributes
        final Document doc = XMLHelper.getDocument();

        final Element element = doc.createElement(PARENT);

        final NamedNodeMap atts = element.getAttributes();
        final Attr nameAttr = doc.createAttribute(NAME);
        nameAttr.setValue(BOB);
        final Attr typeAttr = doc.createAttribute(TYPE);
        typeAttr.setValue(DOG);
        atts.setNamedItem(nameAttr);
        atts.setNamedItem(typeAttr);

        final Node node =
            XMLHelper.getAttributeFromList(atts, NAME);

        assertEquals("Name node content does not match",
                        BOB, node.getTextContent());

        final String value =
            XMLHelper.getAttributeTextFromList(atts, TYPE);

        assertEquals("Node text was not retrieved", DOG, value);
    }

    /**
     * Test the retrieval of a node from a node list, where a
     * specified attribute has a given value.
     *
     * @throws HelperException thrown
     */
    @Test
    public void getNodeWithGivenValueTest() throws HelperException {

        // create a simple node list
        final Document doc = XMLHelper.getDocument();
        final Element parent = doc.createElement(PARENT);
        parent.setTextContent(PARENTTEXT);

        final Element child1 = doc.createElement(CHILD1);
        child1.setTextContent(CHILD1TEXT);
        NamedNodeMap map = child1.getAttributes();
        Attr attr = doc.createAttribute(COLOUR);
        attr.setValue(BLUE);
        map.setNamedItem(attr);

        final Element child2 = doc.createElement(CHILD2);
        child2.setTextContent(CHILD2TEXT);
        map = child2.getAttributes();
        attr = doc.createAttribute(COLOUR);
        attr.setValue("green");
        map.setNamedItem(attr);

        parent.appendChild(child1);
        parent.appendChild(child2);

        final Node foundNode =
            XMLHelper.getNodeWithGivenValue(
                    parent.getChildNodes(), COLOUR, BLUE);

        assertNotNull("did not find node with given value", foundNode);

        map = foundNode.getAttributes();
        final String colour = XMLHelper.getAttributeTextFromList(map, COLOUR);
        assertEquals("mismatch", BLUE, colour);
    }

    /**
     * Tear down.
     * @throws IOException thrown
     */
    @AfterClass
    public static void tearDown() throws IOException {

        FileUtils.deleteDirectory(
                new File(getBaseDir() + File.separator + "scratch"));
    }
}
