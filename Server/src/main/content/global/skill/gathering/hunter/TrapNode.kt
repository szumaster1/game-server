package content.global.skill.gathering.hunter

import core.game.node.entity.npc.NPC
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Represents a node for a trap.
 * @author Vexia
 *
 * @param npcIds Array of NPC IDs associated with the trap.
 * @param level Minimum level required to use the trap.
 * @param experience Experience gained from using the trap.
 * @param objectIds Array of object IDs related to the trap.
 * @param rewards Array of rewards obtained from the trap.
 * @constructor Trap node initializes with specified parameters.
 */
open class TrapNode(
    val npcIds: IntArray, // Holds the IDs of NPCs that can be caught by the trap.
    @JvmField val level: Int, // The level required to set the trap.
    val experience: Double, // The experience points awarded for using the trap.
    val objectIds: IntArray, // Contains the IDs of objects related to the trap.
    @JvmField val rewards: Array<Item> // The items that can be obtained as rewards from the trap.
) {

    /**
     * Can catch
     *
     * @param wrapper The TrapWrapper instance that contains player information.
     * @param npc The NPC that is being evaluated for catching.
     * @return Returns true if the trap can catch the NPC, otherwise false.
     */
    open fun canCatch(wrapper: TrapWrapper, npc: NPC): Boolean {
        val player = wrapper.player // Retrieves the player associated with the wrapper.
        // Checks if the trap is already caught, busy, or has failed.
        if (wrapper.isCaught || wrapper.isBusy || wrapper.isFailed) {
            return false // If any of these conditions are true, catching is not possible.
        }
        // Checks if the player's hunter skill level is sufficient and if the NPC is visible.
        return player.skills.getStaticLevel(Skills.HUNTER) >= level && !npc.isInvisible
    }

    // Property to get the first object ID associated with the trap.
    val transformId: Int
        get() = objectIds[0] // Returns the first object ID from the objectIds array.

    // Property to get the second object ID associated with the trap.
    val finalId: Int
        get() = objectIds[1] // Returns the second object ID from the objectIds array.
}