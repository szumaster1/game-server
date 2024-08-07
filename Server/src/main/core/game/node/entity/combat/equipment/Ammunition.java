package core.game.node.entity.combat.equipment;

import core.game.node.entity.Entity;
import core.game.node.entity.impl.Projectile;
import core.game.world.update.flag.context.Graphic;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Ammunition.
 */
public final class Ammunition {

    private static final Map<Integer, Ammunition> AMMUNITION = new HashMap<Integer, Ammunition>();

    private final int itemId;

    private final Graphic startGraphic;

    private final Graphic darkBowGraphic;

    private final Projectile projectile;

    private final int poisonDamage;

    private BoltEffect effect;

    /**
     * Instantiates a new Ammunition.
     *
     * @param itemId         the item id
     * @param startGraphic   the start graphic
     * @param darkBowGraphic the dark bow graphic
     * @param projectile     the projectile
     * @param poisonDamage   the poison damage
     */
    public Ammunition(int itemId, Graphic startGraphic, Graphic darkBowGraphic, Projectile projectile, int poisonDamage) {
        this.itemId = itemId;
        this.startGraphic = startGraphic;
        this.darkBowGraphic = darkBowGraphic;
        this.poisonDamage = poisonDamage;
        this.projectile = projectile;
    }

    /**
     * Initialize boolean.
     *
     * @return the boolean
     */
    public static final boolean initialize() {
        Document doc;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new File("This file never existed. I have no idea how this code works, but it probably doesn't get called anywhere."));
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (short i = 1; i < nodeList.getLength(); i += 2) {
            Node n = nodeList.item(i);
            if (n != null) {
                if (n.getNodeName().equalsIgnoreCase("Ammunition")) {
                    NodeList list = n.getChildNodes();
                    int itemId = 0;
                    int graphicsId = 0;
                    Graphic startGraphic = null;
                    Graphic darkBowGraphic = null;
                    Projectile projectile = null;
                    for (int a = 1; a < list.getLength(); a += 2) {
                        Node node = list.item(a);
                        if (node.getNodeName().equalsIgnoreCase("itemId")) {
                            itemId = Integer.parseInt(node.getTextContent());
                        } else if (node.getNodeName().equalsIgnoreCase("startGraphicsId")) {
                            graphicsId = Integer.parseInt(node.getTextContent());
                        } else if (node.getNodeName().equalsIgnoreCase("startGraphicsHeight")) {
                            startGraphic = new Graphic(graphicsId, Integer.parseInt(node.getTextContent()), 0);
                        } else if (node.getNodeName().equalsIgnoreCase("darkBowGraphicsId")) {
                            graphicsId = Integer.parseInt(node.getTextContent());
                        } else if (node.getNodeName().equalsIgnoreCase("darkBowGraphicsHeight")) {
                            darkBowGraphic = new Graphic(graphicsId, Integer.parseInt(node.getTextContent()), 0);
                        } else if (node.getNodeName().equalsIgnoreCase("projectileId")) {
                            int startHeight = Integer.parseInt(node.getAttributes().getNamedItem("start_height").getTextContent());
                            int type = Integer.parseInt(node.getAttributes().getNamedItem("type").getTextContent());
                            int angle = Integer.parseInt(node.getAttributes().getNamedItem("angle").getTextContent());
                            int baseSpeed = Integer.parseInt(node.getAttributes().getNamedItem("base_speed").getTextContent());
                            int projectileId = Integer.parseInt(node.getTextContent());
                            projectile = Projectile.create((Entity) null, null, projectileId, startHeight, 36, type, baseSpeed, angle, 0);
                        } else if (node.getNodeName().equalsIgnoreCase("poisonDamage")) {
                            AMMUNITION.put(itemId, new Ammunition(itemId, startGraphic, darkBowGraphic, projectile, Integer.parseInt(node.getTextContent())));
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String... args) {
        initialize();
    }

    /**
     * Gets ammunition.
     *
     * @return the ammunition
     */
    public static Map<Integer, Ammunition> getAmmunition() {
        return AMMUNITION;
    }

    /**
     * Get ammunition.
     *
     * @param id the id
     * @return the ammunition
     */
    public static final Ammunition get(int id) {
        return AMMUNITION.get(id);
    }

    /**
     * Gets item id.
     *
     * @return the item id
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Gets start graphics.
     *
     * @return the start graphics
     */
    public Graphic getStartGraphics() {
        return startGraphic;
    }

    /**
     * Gets dark bow graphics.
     *
     * @return the dark bow graphics
     */
    public Graphic getDarkBowGraphics() {
        return darkBowGraphic;
    }

    /**
     * Gets projectile.
     *
     * @return the projectile
     */
    public Projectile getProjectile() {
        return projectile;
    }

    /**
     * Gets poison damage.
     *
     * @return the poison damage
     */
    public int getPoisonDamage() {
        return poisonDamage;
    }

    /**
     * Gets effect.
     *
     * @return the effect
     */
    public BoltEffect getEffect() {
        return effect;
    }

    /**
     * Sets effect.
     *
     * @param effect the effect
     */
    public void setEffect(BoltEffect effect) {
        this.effect = effect;
    }

    @Override
    public String toString() {
        return "Ammunition [itemId=" + itemId + ", startGraphic=" + startGraphic + ", darkBowGraphic=" + darkBowGraphic + ", projectile=" + projectile + ", poisonDamage=" + poisonDamage + ", effect=" + effect + "]";
    }
}
