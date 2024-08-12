package content.global.skill.gathering

import content.global.skill.gathering.fishing.FishingPulse
import content.global.skill.gathering.fishing.FishingSpot
import core.game.interaction.InteractionListener
import core.game.node.Node
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player

/**
 * Gathering listener.
 */
class GatheringListener : InteractionListener {

    override fun defineListeners() {

    }

    /**
     * Fish
     *
     * @param player The player who is attempting to fish.
     * @param node The fishing node where the player is fishing.
     * @param opt Optional parameters that may affect the fishing action.
     * @return Returns true if the fishing action is successful, otherwise false.
     */
    fun fish(player: Player, node: Node, opt: String): Boolean {
        val npc = node as NPC
        val spot = FishingSpot.forId(npc.id) ?: return false
        val op = spot.getOptionByName(opt) ?: return false
        /*
         * sendNPCDialogue(player, NPCs.FISHERMAN_FRODI_1397, "Hey! This is the king's fishing spot. You'll need his approval to make use of it.")
         */
        player.pulseManager.run(FishingPulse(player, npc, op))
        return true
    }
}
