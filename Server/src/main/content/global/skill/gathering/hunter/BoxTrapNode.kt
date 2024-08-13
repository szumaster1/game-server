package content.global.skill.gathering.hunter

import core.game.node.entity.npc.NPC
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Box trap node
 *
 * @param summoningLevel
 * @param npcIds Array of NPC IDs associated with the box trap.
 * @param level The level required to use the box trap.
 * @param experience The experience gained from using the box trap.
 * @param rewards Array of items that can be obtained from the box trap.
 */
open class BoxTrapNode(
    npcIds: IntArray, // Array of NPC IDs associated with the box trap.
    level: Int, // The level required to use the box trap.
    experience: Double, // The experience gained from using the box trap.
    rewards: Array<Item>, // Array of items that can be obtained from the box trap.
    private val summoningLevel: Int // The summoning level required to use the box trap.
) : TrapNode(npcIds, level, experience, intArrayOf(19188, 19189), rewards) {

    override fun canCatch(wrapper: TrapWrapper, npc: NPC): Boolean {
        if (wrapper.player.getSkills().getStaticLevel(Skills.SUMMONING) < summoningLevel) {
            return false
        }
        return super.canCatch(wrapper, npc)
    }
}
