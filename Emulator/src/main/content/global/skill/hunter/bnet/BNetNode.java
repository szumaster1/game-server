package content.global.skill.hunter.bnet;

import core.cache.def.impl.NPCDefinition;
import core.game.container.impl.EquipmentContainer;
import core.game.node.entity.skill.Skills;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.world.update.flag.context.Graphic;

/**
 * A butterfly net node.
 * @author Vexia
 */
public class BNetNode {

    /**
     * The butterfly jar item.
     */
    private static final Item BUTTERFLY_JAR = new Item(10012);

    /**
     * The impling jar item.
     */
    protected static final Item IMPLING_JAR = new Item(11260);

    /**
     * The npcs related to this node.
     */
    private final int[] npcs;

    /**
     * The levels needed.
     */
    private final int[] levels;

    /**
     * The experience gained.
     */
    private final double[] experience;

    /**
     * The graphics related to the node.
     */
    private final Graphic[] graphics;

    /**
     * The reward item.
     */
    private final Item reward;

    /**
     * Constructs a new {@code ButterflyNetNode} {@code Object}.
     *
     * @param npcs       the npcs.
     * @param levels     the levels.
     * @param experience the experience.
     * @param graphics   the graphics.
     */
    public BNetNode(int[] npcs, int[] levels, double[] experience, Graphic[] graphics, Item reward) {
        this.npcs = npcs;
        this.levels = levels;
        this.experience = experience;
        this.graphics = graphics;
        this.reward = reward;
    }

    /**
     * Rewards the player.
     *
     * @param player the player.
     * @param npc    the npc.
     */
    public void reward(Player player, NPC npc) {
        if (!isBareHand(player)) {
            if (player.getInventory().remove(getJar())) {
                final Item item = getReward();
                player.getInventory().add(item);
                player.getSkills().addExperience(Skills.HUNTER, getExperience(player), true);
            }
        } else {
            player.graphics(graphics[0]);
            player.getSkills().addExperience(Skills.HUNTER, getExperiences()[1], true);
            player.getSkills().addExperience(Skills.AGILITY, getExperiences()[2], true);
        }
    }

    /**
     * Handles a message event.
     *
     * @param type    the type.
     * @param success the success.
     */
    public void message(Player player, int type, boolean success) {
        if (!success) {
            return;
        }
        switch (type) {
            case 1:
                player.getPacketDispatch().sendMessage("You manage to catch the butterfly.");
                if (isBareHand(player)) {
                    player.getPacketDispatch().sendMessage("You release the " + NPCDefinition.forId(npcs[0]).getName().toLowerCase() + " butterfly.");
                }
                break;
        }
    }

    /**
     * Checks if the player has a jar.
     *
     * @param player the player.
     * @return {@code True} if so.
     */
    public boolean hasJar(Player player) {
        return player.getInventory().containsItem(getJar());
    }

    /**
     * Checks if the player has a weapon.
     *
     * @param player the player.
     * @return {@code True} if so.
     */
    public boolean hasWeapon(Player player) {
        Item item = player.getEquipment().get(EquipmentContainer.SLOT_WEAPON);
        return item != null && (item.getId() != 10010 && item.getId() != 11259);
    }

    /**
     * Checks if the player has a net.
     *
     * @param player the player.
     * @return {@code True} if so.
     */
    public boolean hasNet(Player player) {
        return player.getEquipment().contains(10010, 1) || player.getEquipment().contains(11259, 1);
    }

    /**
     * Checks if the player is bare handed.
     *
     * @param player the player.
     * @return {@code} True if so.
     */
    public boolean isBareHand(Player player) {
        return !hasNet(player) && player.getSkills().getLevel(Skills.HUNTER) >= getBareHandLevel() && player.getSkills().getLevel(Skills.AGILITY) >= getAgilityLevel();
    }

    /**
     * Gets the experience in hunter.
     *
     * @return the exp.
     */
    public double getExperience(Player player) {
        return experience[0];
    }

    /**
     * Gets the hunter level.
     *
     * @return the level.
     */
    public int getLevel() {
        return levels[0];
    }

    /**
     * Gets the agility level.
     *
     * @return the level.
     */
    public int getAgilityLevel() {
        return levels[2];
    }

    /**
     * Gets the bare hand level.
     *
     * @return the level.
     */
    public int getBareHandLevel() {
        return levels[1];
    }

    /**
     * Gets the npcs.
     *
     * @return The npcs.
     */
    public int[] getNpcs() {
        return npcs;
    }

    /**
     * Gets the levels.
     *
     * @return The levels.
     */
    public int[] getLevels() {
        return levels;
    }

    /**
     * Gets the experience.
     *
     * @return The experience.
     */
    public double[] getExperiences() {
        return experience;
    }

    /**
     * Gets the graphics.
     *
     * @return The graphics.
     */
    public Graphic[] getGraphics() {
        return graphics;
    }

    /**
     * Gets the reward.
     *
     * @return The reward.
     */
    public Item getReward() {
        return reward;
    }

    /**
     * Gets the jar.
     *
     * @return the jar.
     */
    public Item getJar() {
        return BUTTERFLY_JAR;
    }

}
