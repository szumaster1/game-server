package content.global.skill.support.slayer.npc

import content.global.skill.support.slayer.SlayerUtils
import content.global.skill.support.slayer.data.Tasks
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.node.entity.player.Player

/**
 * Turoth NPC.
 */
class TurothNPC : NPCBehavior(*Tasks.TUROTHS.npcs) {

    override fun beforeDamageReceived(self: NPC, attacker: Entity, state: BattleState) {
        if (attacker is Player) {
            if (!SlayerUtils.hasBroadWeaponEquipped(attacker, state))
                state.neutralizeHits()
        }
    }
}