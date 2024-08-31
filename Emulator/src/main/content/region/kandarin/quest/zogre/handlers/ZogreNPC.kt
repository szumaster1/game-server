package content.region.kandarin.quest.zogre.handlers

import cfg.consts.NPCs
import core.api.getOrStartTimer
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.node.entity.player.Player
import core.game.system.timer.impl.Disease

/**
 * Zogre NPC.
 */
class ZogreNPC : NPCBehavior(*ZogreAndSkogreNPCs) {

    override fun onCreation(self: NPC) {
        self.isWalks = true
        self.isNeverWalks = false
        self.isAggressive = true
    }

    companion object {
        private val ZogreAndSkogreNPCs = intArrayOf(NPCs.ZOGRE_2044, NPCs.ZOGRE_2045, NPCs.ZOGRE_2046, NPCs.ZOGRE_2047, NPCs.ZOGRE_2048, NPCs.ZOGRE_2049, NPCs.SKOGRE_2050, NPCs.ZOGRE_2051, NPCs.ZOGRE_2052, NPCs.ZOGRE_2053, NPCs.ZOGRE_2054, NPCs.ZOGRE_2055, NPCs.SKOGRE_2056, NPCs.SKOGRE_2057)
    }

    override fun beforeDamageReceived(self: NPC, attacker: Entity, state: BattleState) {
        if (attacker is Player) {
            getOrStartTimer<Disease>(attacker, 10)
        }
    }
}