package content.region.misc.zanaris.handlers

import cfg.consts.Animations
import cfg.consts.NPCs
import core.api.animate
import core.api.lock
import core.api.submitWorldPulse
import core.api.transformNpc
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable

/**
 * Represents the Fungi NPC.
 */
class FungiNPC : NPCBehavior(*intArrayOf(/*
                                          * Level-74            Level-86
                                          */NPCs.FUNGI_3344, NPCs.FUNGI_3345)
) {
    /**
     * Represents the picking up the mushroom animation.
     */
    private val firstAnimation: Animation = Animation(3335)

    /**
     * Represents the zygomite pop-up animation.
     */
    private val secondAnimation: Animation = Animation(3322)

    override fun beforeAttackFinalized(self: NPC, victim: Entity, state: BattleState) {
        if (victim is Player) {
            val player = victim.asPlayer()

            lock(player, 1)
            animate(player, firstAnimation)
            submitWorldPulse(object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        0 -> {
                            animate(player, secondAnimation)
                            self.impactHandler.disabledTicks = 1
                            transformNpc(self, self.id + 2, -1)
                            self.attack(victim)
                            return true
                        }
                    }
                    return false
                }
            })
        }
    }
}