package core.game.node.entity.combat.equipment;

import core.game.node.entity.impl.Animator.Priority;
import core.game.world.update.flag.context.Animation;
import org.w3c.dom.Document;
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
 * Range weapon.
 */
public final class RangeWeapon {

    private static final Map<Integer, RangeWeapon> RANGE_WEAPONS = new HashMap<Integer, RangeWeapon>();

    private final List<Integer> ammunition;

    private final int itemId;

    private final Animation animation;

    private final int attackSpeed;

    private final int ammunitionSlot;

    private final int type;

    private final boolean dropAmmo;

    /**
     * Instantiates a new Range weapon.
     *
     * @param itemId         the item id
     * @param animation      the animation
     * @param attackSpeed    the attack speed
     * @param ammunitionSlot the ammunition slot
     * @param type           the type
     * @param dropAmmo       the drop ammo
     * @param ammunition     the ammunition
     */
    public RangeWeapon(int itemId, Animation animation, int attackSpeed, int ammunitionSlot, int type, boolean dropAmmo, List<Integer> ammunition) {
        this.itemId = itemId;
        this.animation = animation;
        this.attackSpeed = attackSpeed;
        this.ammunitionSlot = ammunitionSlot;
        this.type = type;
        this.dropAmmo = dropAmmo;
        this.ammunition = ammunition;
    }

    /**
     * Initialize boolean.
     *
     * @return the boolean
     */
    public static boolean initialize() {
        Document doc;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new File("This file never existed. I have no idea how this code works, but it probably doesn't get called anywhere"));
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
        NodeList nodeList = doc.getDocumentElement().getChildNodes();

        for (short i = 1; i < nodeList.getLength(); i += 2) {
            Node n = nodeList.item(i);
            if (n != null) {
                if (n.getNodeName().equalsIgnoreCase("Weapon")) {
                    NodeList list = n.getChildNodes();
                    int itemId = 0;
                    int animationId = 426;
                    int attackSpeed = 4;
                    int slot = 13;
                    int type = 0;
                    boolean dropAmmo = true;
                    List<Integer> ammo = new ArrayList<Integer>();
                    for (int a = 1; a < list.getLength(); a += 2) {
                        Node node = list.item(a);
                        if (node.getNodeName().equalsIgnoreCase("itemId")) {
                            itemId = Integer.parseInt(node.getTextContent());
                        } else if (node.getNodeName().equalsIgnoreCase("animationId")) {
                            animationId = Integer.parseInt(node.getTextContent());
                        } else if (node.getNodeName().equalsIgnoreCase("attackSpeed")) {
                            attackSpeed = Integer.parseInt(node.getTextContent());
                        } else if (node.getNodeName().equalsIgnoreCase("ammunitionSlot")) {
                            slot = Integer.parseInt(node.getTextContent());
                        } else if (node.getNodeName().equalsIgnoreCase("type")) {
                            type = Integer.parseInt(node.getTextContent());
                        } else if (node.getNodeName().equalsIgnoreCase("drop_ammo")) {
                            dropAmmo = Boolean.parseBoolean(node.getTextContent());
                        } else if (node.getNodeName().equalsIgnoreCase("ammunition")) {
                            int ammunitionId = Integer.parseInt(node.getTextContent());
                            ammo.add(ammunitionId);
                        }
                    }
                    RANGE_WEAPONS.put(itemId, new RangeWeapon(itemId, new Animation(animationId, 0, Priority.HIGH), attackSpeed, slot, type, dropAmmo, ammo));
                }
            }
        }

        return true;
    }

    /**
     * Gets range weapons.
     *
     * @return the range weapons
     */
    public static Map<Integer, RangeWeapon> getRangeWeapons() {
        return RANGE_WEAPONS;
    }

    /**
     * Get range weapon.
     *
     * @param id the id
     * @return the range weapon
     */
    public static RangeWeapon get(int id) {
        return RANGE_WEAPONS.get(id);
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
     * Gets animation.
     *
     * @return the animation
     */
    public Animation getAnimation() {
        return animation;
    }

    /**
     * Gets ammunition slot.
     *
     * @return the ammunition slot
     */
    public int getAmmunitionSlot() {
        return ammunitionSlot;
    }

    /**
     * Gets ammunition.
     *
     * @return the ammunition
     */
    public List<Integer> getAmmunition() {
        return ammunition;
    }

    /**
     * Gets attack speed.
     *
     * @return the attack speed
     */
    public int getAttackSpeed() {
        return attackSpeed;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * Gets weapon type.
     *
     * @return the weapon type
     */
    public Weapon.WeaponType getWeaponType() {
        return Weapon.WeaponType.values()[type];
    }

    /**
     * Is drop ammo boolean.
     *
     * @return the boolean
     */
    public boolean isDropAmmo() {
        return dropAmmo;
    }
}