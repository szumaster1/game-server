package content.minigame.tbwcleanup

import core.api.applyPoison
import cfg.consts.NPCs
import core.api.getAttribute
import core.api.poofClear
import core.api.rewardXP
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.tools.RandomFunction.random
import kotlin.math.min

private val TBWC_NPCs = intArrayOf(NPCs.JUNGLE_SPIDER_62, NPCs.BUSH_SNAKE_2489, NPCs.JUNGLE_SPIDER_2491, NPCs.LARGE_MOSQUITO_2493, NPCs.MOSQUITO_SWARM_2494, NPCs.MOSQUITO_SWARM_2495, NPCs.TRIBESMAN_2497)

class ThreatBehavior : NPCBehavior(*TBWC_NPCs) {

    private var ticksSinceLastCombatAction = 0

    override fun beforeAttackFinalized(self: NPC, victim: Entity, state: BattleState) {
        if (state.estimatedHit == 0) return
        when (self.id) {
            NPCs.BUSH_SNAKE_2489 -> {
                state.estimatedHit = min(3, state.estimatedHit)
                applyPoison(victim, self, 52)
            }

            NPCs.JUNGLE_SPIDER_2491 -> {
                state.estimatedHit = min(4, state.estimatedHit)
            }

            NPCs.LARGE_MOSQUITO_2493, NPCs.MOSQUITO_SWARM_2494, NPCs.MOSQUITO_SWARM_2495 -> {
                state.estimatedHit = min(1, state.estimatedHit)
            }

            NPCs.TRIBESMAN_2497 -> {
                state.estimatedHit = min(4, state.estimatedHit)
                applyPoison(victim, self, 52)
            }
        }
        return
    }

    override fun afterDamageReceived(self: NPC, attacker: Entity, state: BattleState) {
        if (self.getAttribute("TBWC", false)) {
            ticksSinceLastCombatAction = 0
        }
    }

    override fun onDeathFinished(self: NPC, killer: Entity) {
        if (killer is Player) {
            val player = killer.asPlayer()
            when (self.id) {
                NPCs.LARGE_MOSQUITO_2493 -> rewardXP(player, Skills.AGILITY, 17.5)
                NPCs.MOSQUITO_SWARM_2494 -> rewardXP(player, Skills.AGILITY, 35.0)
                NPCs.MOSQUITO_SWARM_2495 -> rewardXP(player, Skills.AGILITY, 75.0)
            }
            if (getAttribute(player, "/save:startedTBWCleanup", false)) {
                if (self.id == NPCs.JUNGLE_SPIDER_62) self.setAttribute("TBWC:Points", random(10, 20))
                awardTBWCleanupPoints(killer.asPlayer(), self.getAttribute("TBWC:Points", 0))
            }
        }
        if (self.getAttribute("TBWC", false)) self.clear()
    }

    override fun tick(self: NPC): Boolean {
        if (self.getAttribute("TBWC", false)) {
            ticksSinceLastCombatAction += 1
            if (ticksSinceLastCombatAction > 200) {
                poofClear(self)
            }
        }
        return true
    }
}