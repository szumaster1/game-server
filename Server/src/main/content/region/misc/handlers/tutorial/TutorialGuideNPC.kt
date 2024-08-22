package content.region.misc.handlers.tutorial

import cfg.consts.NPCs
import core.api.sendChat
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.tools.RandomFunction

/**
 * Handles the Tutorial guide NPC.
 */
class TutorialGuideNPC : NPCBehavior(NPCs.TUTORIAL_GUIDE_8591) {

    override fun tick(self: NPC): Boolean {
        if (RandomFunction.random(100) < 15) {
            sendChat(self, "You can skip the tutorial by talking to me!")
        }
        return true
    }
}
