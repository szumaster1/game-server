package content.region.misc.handlers.tutorial

import core.api.animate
import core.api.consts.Animations
import core.api.consts.NPCs
import core.api.sendChat
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.world.GameWorld
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction

/**
 * Handles the Tutorial guide NPC.
 */
class TutorialGuideNPC : NPCBehavior(NPCs.TUTORIAL_GUIDE_8591) {

    override fun tick(self: NPC): Boolean {
        if (RandomFunction.random(100) < 15) {
            sendChat(self, "Welcome to ${GameWorld.settings!!.name}!")
            animate(self, Animation(Animations.HUMAN_WAVE_863))
            sendChat(self, "You can skip the tutorial by talking to me!", 2)
        }
        return true
    }
}
