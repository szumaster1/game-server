package core.game.node.entity.impl;

import core.ServerConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Game attributes.
 */
public final class GameAttributes {

    private final Map<String, Object> attributes = new HashMap<>();

    private final List<String> savedAttributes = new ArrayList<>(250);

    /**
     * The Key expirations.
     */
    public final HashMap<String, Long> keyExpirations = new HashMap<>(250);

    /**
     * Instantiates a new Game attributes.
     */
    public GameAttributes() {
        /*
         * Empty.
         */
    }

    /**
     * Dump.
     *
     * @param file the file
     */
    @Deprecated
    public void dump(String file) {

    }

    /**
     * Parse.
     *
     * @param file the file
     */
    @Deprecated
    public void parse(String file) {
        File saveFile = new File(ServerConstants.PLAYER_ATTRIBUTE_PATH + file);
        if (!saveFile.exists()) {
            return;
        }
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(saveFile);

            NodeList attributesList = doc.getElementsByTagName("GameAttribute");
            for (int i = 0; i < attributesList.getLength(); i++) {
                Node attrNode = attributesList.item(i);
                if (attrNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element attr = (Element) attrNode;
                    String key = attr.getAttribute("key");
                    switch (attr.getAttribute("type")) {
                        case "bool": {
                            boolean value = Boolean.parseBoolean(attr.getAttribute("value"));
                            attributes.put(key, value);
                            if (!savedAttributes.contains(key)) {
                                savedAttributes.add(key);
                            }
                            break;
                        }
                        case "long": {
                            long value = Long.parseLong(attr.getAttribute("value"));
                            attributes.put(key, value);
                            if (!savedAttributes.contains(key)) {
                                savedAttributes.add(key);
                            }
                            break;
                        }
                        case "short": {
                            short value = Short.parseShort(attr.getAttribute("value"));
                            attributes.put(key, value);
                            if (!savedAttributes.contains(key)) {
                                savedAttributes.add(key);
                            }
                            break;
                        }
                        case "int": {
                            int value = Integer.parseInt(attr.getAttribute("value"));
                            attributes.put(key, value);
                            if (!savedAttributes.contains(key)) {
                                savedAttributes.add(key);
                            }
                            break;
                        }
                        case "byte": {
                            byte value = Byte.parseByte(attr.getAttribute("value"));
                            attributes.put(key, value);
                            if (!savedAttributes.contains(key)) {
                                savedAttributes.add(key);
                            }
                            break;
                        }
                        case "string": {
                            String value = attr.getAttribute("value");
                            attributes.put(key, value);
                            if (!savedAttributes.contains(key)) {
                                savedAttributes.add(key);
                            }
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets attribute.
     *
     * @param key   the key
     * @param value the value
     */
    public void setAttribute(String key, Object value) {
        if (key.startsWith("/save:")) {
            key = key.substring(6);
            if (!savedAttributes.contains(key)) {
                savedAttributes.add(key);
            }
        }
        attributes.put(key, value);
    }

    /**
     * Gets attribute.
     *
     * @param <T> the type parameter
     * @param key the key
     * @return the attribute
     */
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key) {
        key = key.replace("/save:", "");
        return (T) attributes.get(key);
    }

    /**
     * Gets attribute.
     *
     * @param <T>    the type parameter
     * @param string the string
     * @param fail   the fail
     * @return the attribute
     */
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String string, T fail) {
        string = string.replace("/save:", "");
        Object object = attributes.get(string);
        if (object != null) {
            return (T) object;
        }
        return fail;
    }

    /**
     * Remove attribute.
     *
     * @param string the string
     */
    public void removeAttribute(String string) {
        savedAttributes.remove(string);
        attributes.remove(string);
    }

    /**
     * Gets attributes.
     *
     * @return the attributes
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * Gets saved attributes.
     *
     * @return the saved attributes
     */
    public List<String> getSavedAttributes() {
        return savedAttributes;
    }
}