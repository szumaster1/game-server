package content.region.kandarin.ardougne.necromancer_tower.handlers

import org.rs.consts.NPCs
import core.api.*
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.node.entity.player.Player
import core.tools.RandomFunction

private val npc_IDs = intArrayOf(NPCs.INVRIGAR_THE_NECROMANCER_173, NPCs.NECROMANCER_194)

/**
 * Represents the Necromancers NPC.
 */
class TheNecromancerNPC : NPCBehavior(*npc_IDs) {

    override fun afterDamageReceived(self: NPC, attacker: Entity, state: BattleState) {
        if (attacker is Player) {
            handleZombieSummoning(attacker)
        }
    }

    private fun handleZombieSummoning(player: Player) {
        val random = RandomFunction.random(5, 10)
        if (random == 5) {
            SummonedZombiesNPC.summonZombie(player)
            setAttribute(player, "necro:summoned_zombie", true)

            // Check the number of zombies alive.
            val zombiesAlive = getAttribute(player, "necro:zombie_alive", 0)
            if (getAttribute(player, "necro:summoned_zombie", false) && zombiesAlive < 3) {
                setAttribute(player, "necro:zombie_alive", zombiesAlive + 1)
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