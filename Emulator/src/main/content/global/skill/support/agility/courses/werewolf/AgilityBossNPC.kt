package content.global.skill.support.agility.courses.werewolf

import core.api.animate
import cfg.consts.NPCs
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.tools.RandomFunction

/**
 * Agility boss NPC.
 */
class AgilityBossNPC : NPCBehavior(NPCs.AGILITY_BOSS_1661) {

    override fun tick(self: NPC): Boolean {
        if (RandomFunction.roll(25))
            animate(self, 6549)
        return true
    }

}
