/**
 * 
 */
package com.blizzardtec.helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Utility class for working on XML documents and files.
 *
 * @author Barnaby Golden
 *
 */
public final class XMLHelper {

    /**
     * Private constructor denotes utility class.
     */
    private XMLHelper() {
        
    }

    /**
     * Create a new XML document.
     *
     * @return XML document
     * @throws HelperException thrown
     */
    public static Document getDocument() throws HelperException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();

        DocumentBuilder builder = null;

        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            throw new HelperException(pce);
        }

        final DOMImplementation impl = builder.getDOMImplementation();

        return impl.createDocument(null, null, null);
    }

    /**
     * Save the XML document to a file.
     * @param doc the XML document to save to file
     * @param filename file name
     * @throws HelperException thrown
     */
    public static void saveXMLFile(
            final Document doc, final String filename)
                                throws HelperException {


        Writer output = null;
        final File file = new File(filename);

        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write(documentToString(doc));
            output.close();
        } catch (IOException ioe) {
            throw new HelperException(ioe);
        }
    }

    /**
     * Convert an XML Document to a formated string.
     * @param doc XML Document
     * @return formated XML string
     * @throws HelperException thrown
     */
    public static String documentToString(final Document doc)
                throws HelperException {

        Transformer transformer = null;
        try {
            final TransformerFactory tFactory =
                    TransformerFactory.newInstance();
            tFactory.setAttribute("indent-number", 2);
            transformer = tFactory.newTransformer();
        } catch (TransformerConfigurationException tce) {
            throw new HelperException(tce);
        }

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        //initialise StreamResult with File object to save to file
        final StreamResult result =
            new StreamResult(new StringWriter());
        final DOMSource source = new DOMSource(doc);
        try {
            transformer.transform(source, result);
        } catch (TransformerException tfe) {
            throw new HelperException(tfe);
        }

        return result.getWriter().toString();
    }

    /**
     * Add an attribute to an Element.
     * @param doc XML document
     * @param element XML Element
     * @param attribute attribute name
     * @param value attribute value
     */
    public static void addAttribute(final Document doc,
                                    final Element element,
                                    final String attribute,
                                    final String value) {

        final NamedNodeMap atts = element.getAttributes();
        final Attr newAttr = doc.createAttribute(attribute);
        newAttr.setValue(value);
        atts.setNamedItem(newAttr);
    }

    /**
     * Load an XML file in to an XML document.
     * @param xmlfile file to load
     * @return XML document
     * @throws HelperException thrown
     */
    public static Document loadXMLFile(final File xmlfile)
                                    throws HelperException {
        DocumentBuilder docBuilder;
        Document doc = null;
        final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                .newInstance();
        docBuilderFactory.setIgnoringElementContentWhitespace(true);
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            throw new HelperException(pce);
        }

        try {
            doc = docBuilder.parse(xmlfile);
        } catch (SAXException sae) {
            throw new HelperException(sae);
        } catch (IOException ioe) {
            throw new HelperException(ioe);
        }

        return doc;
    }

    /**
     * Extract the text content of a named node from a list of nodes.
     * Returns null if the node is not found.
     *
     * @param nodeList a list of nodes
     * @param nodeName name of the node to extract from the list
     * @return found node text or null if not found
     */
    public static String getNodeTextFromList(
            final NodeList nodeList, final String nodeName) {

        String text = null;

        if (nodeList != null) {
            final Node node = getNodeFromList(nodeList, nodeName);

            if (node != null) {
                text = node.getTextContent();
            }            
        }

        return text;
    }
    
    /**
     * Extract a named node from a list of nodes.
     * Note that if more than one instance of the named node
     * exists, then the returned node will be undefined and could
     * be any of the nodes that match.
     *
     * @param nodeList a list of nodes
     * @param nodeName name of the node to extract from the list
     * @return found node or null if not found
     */
    public static Node getNodeFromList(
            final NodeList nodeList, final String nodeName) {

        Node node = null;

        if (nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeName.equalsIgnoreCase(nodeList.item(i).getNodeName())) {
                    node = nodeList.item(i);
                }
            }            
        }

        return node;
    }

    /**
     * Extract a named attribute from a node map.
     *
     * @param map attribute node map
     * @param attrName name of the attribute to extract from the list
     * @return found node or null if not found
     */
    public static Node getAttributeFromList(
            final NamedNodeMap map, final String attrName) {

        Node node = null;

        if (map != null) {
            for (int i = 0; i < map.getLength(); i++) {
                if (attrName.equalsIgnoreCase(map.item(i).getNodeName())) {
                    node = map.item(i);
                }
            }            
        }

        return node;
    }

    /**
     * Extract the text content of a named node from a list of nodes.
     * Returns null if the node is not found.
     *
     * @param map attribute node map
     * @param attrName name of the attribute to extract from the list
     * @return found node text or null if not found
     */
    public static String getAttributeTextFromList(
            final NamedNodeMap map, final String attrName) {

        String text = null;

        if (map != null) {
            final Node node = getAttributeFromList(map, attrName);

            if (node != null) {
                text = node.getTextContent();
            }            
        }

        return text;
    }

    /**
     * Get a node from a list with a specific attribute value.
     *
     * @param nodeList the list of nodes
     * @param name the name of the attribute to check against
     * @param value the value to match
     * @return the node with matching attribute value
     */
    public static Node getNodeWithGivenValue(
            final NodeList nodeList, final String name, final String value) {

        Node node = null;

        for (int i = 0; i < nodeList.getLength(); i++) {
            final NamedNodeMap map = nodeList.item(i).getAttributes();
            if (map != null) {
                for (int j = 0; j < map.getLength(); j++) {
                    if ((name.equals(map.item(j).getNodeName()))
                            && (value.equals(map.item(j).getTextContent()))) {

                        node = nodeList.item(i);
                    }
                }                
            }
        }

        return node;
    }
}
