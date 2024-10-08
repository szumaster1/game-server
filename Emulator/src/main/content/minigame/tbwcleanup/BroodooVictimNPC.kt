package content.minigame.tbwcleanup

import org.rs.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.api.poofClear
import core.game.node.entity.player.Player
import core.tools.RandomFunction.random
import kotlin.math.min

private val BROODOO_VICTIM_NPC = intArrayOf(NPCs.BROODOO_VICTIM_2499, NPCs.BROODOO_VICTIM_2501, NPCs.BROODOO_VICTIM_2503)

/**
 * Represents the Broodoo victim NPC.
 */
class BroodooVictimNPC : NPCBehavior(*BROODOO_VICTIM_NPC) {

    private var ticksSinceSpawn = 0
    private var ticksSinceStatDrainAttack = 25

    override fun onCreation(self: NPC) {
        ticksSinceSpawn = 0
        ticksSinceStatDrainAttack = 25
    }

    override fun beforeAttackFinalized(self: NPC, victim: Entity, state: BattleState) {
        // Drain a victims attack skills. This behavior is poorly documented on the wikis so If anyone has some sources
        // with details on how this is supposed to work please let me know. ( What skills can be affected (summoning,
        // slayer, prayer, all?). How frequently does this happen? What is the range of the drain?)
        if (ticksSinceStatDrainAttack >= 25) {
            if (victim is Player) {
                val victim = victim.asPlayer()
                val randomDraw = random(0, 25)
                if (randomDraw in arrayOf(0, 1, 2, 4, 6)) {
                    victim.skills.drainLevel(randomDraw, random(0, 10).toDouble() * 0.01, 0.7)
                    ticksSinceStatDrainAttack = 0
                }
            }
        }
    }

    override fun beforeDamageReceived(self: NPC, attacker: Entity, state: BattleState) {
        state.estimatedHit = min(3, state.estimatedHit)
    }

    override fun onDeathFinished(self: NPC, killer: Entity) {
        if (self.getAttribute("TBWC", false)) {
            if (killer is Player) {
                if (killer.asPlayer().getAttribute("/save:startedTBWCleanup", false)) {
                    awardTBWCleanupPoints(killer.asPlayer(), self.getAttribute("TBWC:Points", 0))
                }
                self.clear()
            }
        }
    }

    override fun tick(self: NPC): Boolean {
        if (self.getAttribute("TBWC", false)) {
            ticksSinceSpawn += 1
            ticksSinceStatDrainAttack += 1
            if (ticksSinceSpawn > 5000) {
                poofClear(self)
            }
        }
        return true
    }
}