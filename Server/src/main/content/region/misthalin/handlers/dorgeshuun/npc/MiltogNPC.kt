package content.region.misthalin.handlers.dorgeshuun.npc

import core.api.*
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.tools.RandomFunction
import cfg.consts.NPCs

/**
 * Represents the Miltog NPC.
 */
class MiltogNPC : NPCBehavior(NPCs.MILTOG_5781) {

    private val forceChat = arrayOf(
        "Lamps!",
        "Lanterns!",
        "Tinderboxes!",
        "Torches!",
        "Lamp oil!"
    )

    override fun tick(self: NPC): Boolean {
        if (RandomFunction.random(35) == 10)
            sendChat(self, forceChat.random())
        return super.tick(self)
    }
}
