package content.region.kandarin.npc

import core.api.*
import core.api.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.node.entity.player.Player
import core.tools.RandomFunction

/**
 * Invrigar the Necromancer NPC.
 */
class InvrigarTheNecromancerNPC : NPCBehavior(NPCs.INVRIGAR_THE_NECROMANCER_173) {

    override fun afterDamageReceived(self: NPC, attacker: Entity, state: BattleState) {
        if (attacker is Player) {
            val random = RandomFunction.random(1, 5)
            if (random == 3) runTask(self, 1) {
                SummonedZombieNPC.summonZombie(attacker)
                setAttribute(attacker, "necro:summoned_zombie", true)
            }
        }
    }

    override fun onDeathStarted(self: NPC, killer: Entity) {
        if (killer is Player)
            if (getAttribute(killer.asPlayer(), "necro:summoned_zombie", false))
                poofClear(findLocalNPC(killer, NPCs.SUMMONED_ZOMBIE_77)!!)
                    .also { removeAttribute(killer, "necro:summoned_zombie") }
    }
}
