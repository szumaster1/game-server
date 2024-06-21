package content.global.skill.gathering.hunter;

import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;

/**
 * The Trap node.
 */
public class TrapNode {

    private final int[] npcIds;

    private final int level;

    private final double experience;

    private final int[] objectIds;

    private final Item[] rewards;

    /**
     * Instantiates a new Trap node.
     *
     * @param npcIds     the npc ids
     * @param level      the level
     * @param experience the experience
     * @param objectIds  the object ids
     * @param rewards    the rewards
     */
    public TrapNode(int[] npcIds, int level, double experience, int[] objectIds, Item[] rewards) {
        this.npcIds = npcIds;
        this.level = level;
        this.experience = experience;
        this.objectIds = objectIds;
        this.rewards = rewards;
    }

    /**
     * Can catch boolean.
     *
     * @param wrapper the wrapper
     * @param npc     the npc
     * @return the boolean
     */
    public boolean canCatch(TrapWrapper wrapper, final NPC npc) {
        final Player player = wrapper.getPlayer();
        if (wrapper.isCaught() || wrapper.isBusy() || wrapper.isFailed()) {
            return false;
        }
        return player.skills.getStaticLevel(Skills.HUNTER) >= level && !npc.isInvisible();
    }

    /**
     * Gets transform id.
     *
     * @return the transform id
     */
    public int getTransformId() {
        return objectIds[0];
    }

    /**
     * Gets final id.
     *
     * @return the final id
     */
    public int getFinalId() {
        return objectIds[1];
    }

    /**
     * Get npc ids int [ ].
     *
     * @return the int [ ]
     */
    public int[] getNpcIds() {
        return npcIds;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets experience.
     *
     * @return the experience
     */
    public double getExperience() {
        return experience;
    }

    /**
     * Get rewards item [ ].
     *
     * @return the item [ ]
     */
    public Item[] getRewards() {
        return rewards;
    }

    /**
     * Get object ids int [ ].
     *
     * @return the int [ ]
     */
    public int[] getObjectIds() {
        return objectIds;
    }

}
