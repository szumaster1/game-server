package content.region.kandarin.ardougne.necromancer_tower.handlers

import cfg.consts.NPCs
import core.api.*
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
            handleZombieSummoning(attacker)
        }
    }

    private fun handleZombieSummoning(attacker: Player) {
        if (RandomFunction.random(5, 100) == 15) {
            SummonedZombieNPC.summonZombie(attacker)
            setAttribute(attacker, "necro:summoned_zombie", true)

            // Check the number of zombies alive.
            val zombiesAlive = getAttribute(attacker, "necro:zombie_alive", 0)
            if (zombiesAlive < 3) {
                setAttribute(attacker, "necro:zombie_alive", zombiesAlive + 1)
            }
        }
    }

    override fun onDeathFinished(self: NPC, killer: Entity) {
        if (killer is Player && getAttribute(killer.asPlayer(), "necro:summoned_zombie", false)) {
            runTask(killer, 3) {
                poofClear(findLocalNPC(killer, NPCs.SUMMONED_ZOMBIE_77)!!)
                    .also { removeAttributes(killer, "necro:summoned_zombie") }
            }
        }
    }
}