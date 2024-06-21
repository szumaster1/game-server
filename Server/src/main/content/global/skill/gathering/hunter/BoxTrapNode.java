package content.global.skill.gathering.hunter;

import core.game.node.entity.npc.NPC;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;

/**
 * The Box trap node.
 */
public class BoxTrapNode extends TrapNode {

    private final int summoningLevel;

    /**
     * Instantiates a new Box trap node.
     *
     * @param npcIds         the npc ids
     * @param level          the level
     * @param experience     the experience
     * @param rewards        the rewards
     * @param summoningLevel the summoning level
     */
    public BoxTrapNode(int[] npcIds, int level, double experience, Item[] rewards, final int summoningLevel) {
        super(npcIds, level, experience, new int[]{19188, 19189}, rewards);
        this.summoningLevel = summoningLevel;
    }

    @Override
    public boolean canCatch(TrapWrapper wrapper, final NPC npc) {
        if (wrapper.getPlayer().getSkills().getStaticLevel(Skills.SUMMONING) < summoningLevel) {
            return false;
        }
        return super.canCatch(wrapper, npc);
    }
}
