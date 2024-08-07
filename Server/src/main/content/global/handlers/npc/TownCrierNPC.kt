package content.global.handlers.npc

import core.api.animate
import core.api.consts.Animations
import core.api.consts.NPCs
import core.api.sendChat
import core.api.stopWalk
import core.api.toIntArray
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.tools.RandomFunction

private val IDS = (NPCs.TOWN_CRIER_6135..NPCs.TOWN_CRIER_6139).toIntArray()

/**
 * Town crier NPC.
 */
class TownCrierNPC : NPCBehavior(*IDS) {

    override fun tick(self: NPC): Boolean {
        if (RandomFunction.roll(33)) {
            stopWalk(self)
            animate(self, Animations.TOWN_CRIER_RING_BELL_6865)
            sendChat(self, "The Duke of Lumbridge needs a hand.")
        }
        if (RandomFunction.roll(25)) {
            stopWalk(self)
            animate(self, Animations.TOWN_CRIER_SCRATCHES_HEAD_6863)
            sendChat(self, "The squirrels! The squirrels are coming! Noooo, get them out of my head!")
        }
        return true
    }
}
