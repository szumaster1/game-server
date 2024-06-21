package content.region.morytania.handlers.npc

import core.api.*
import core.api.consts.Items
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.DeathTask
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.update.flag.context.Animation

class WerewolfNPC : NPCBehavior(*HUMAN_NPC) {

    companion object {
        private val HUMAN_NPC = (6026..6045).toIntArray()
        private val WEREWOLF_NPC = (6006..6025).toIntArray()
        private val WEREWOLF_GFX = (1079..1098).toIntArray()
        private val TRANSFORM_NPC = Animation(6554)
    }

    override fun tick(self: NPC): Boolean {
        val target = getAttribute<Player?>(self, "werewolf:target", null) ?: return true
        if (!target.isActive || DeathTask.isDead(target) || !target.location.withinDistance(self.location, 8)) {
            removeAttribute(target, "werewolf:target")
            self.reTransform()
            return true
        }
        return super.tick(self)
    }

    override fun onDeathFinished(self: NPC, killer: Entity) {
        self.reTransform()
    }

    override fun beforeDamageReceived(self: NPC, attacker: Entity, state: BattleState) {
        if (attacker is Player) {
            if (!inEquipment(attacker, Items.WOLFBANE_2952, 1) && self.id in HUMAN_NPC) {
                lock(self, 3)
                delayAttack(self, 2)
                delayAttack(attacker, 2)
                visualize(self, TRANSFORM_NPC, WEREWOLF_GFX[self.id - 6026])
                GameWorld.Pulser.submit(object : Pulse(3) {
                    override fun pulse(): Boolean {
                        transformNpc(self, WEREWOLF_NPC[self.id - 6026], 200)
                        self.properties.combatPulse.attack(attacker)
                        return true
                    }
                })
            }
        }
    }
}