package content.region.misc.zanaris.handlers

import cfg.consts.Items
import content.global.skill.support.slayer.data.Tasks
import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Represents the Zygomite NPC.
 */
class ZygomiteNPC : NPCBehavior(*Tasks.ZYGOMITES.npcs), InteractionListener {

    private val fungicideSpray = intArrayOf(
        Items.FUNGICIDE_SPRAY_9_7422,
        Items.FUNGICIDE_SPRAY_8_7423,
        Items.FUNGICIDE_SPRAY_7_7424,
        Items.FUNGICIDE_SPRAY_6_7425,
        Items.FUNGICIDE_SPRAY_5_7426,
        Items.FUNGICIDE_SPRAY_4_7427,
        Items.FUNGICIDE_SPRAY_3_7428,
        Items.FUNGICIDE_SPRAY_2_7429,
        Items.FUNGICIDE_SPRAY_1_7430,
        Items.FUNGICIDE_SPRAY_0_7431
    )

    override fun defineListeners() {
        onUseWith(IntType.NPC, fungicideSpray, *ids, handler = ::handleFungicideSpray)
    }

    override fun onDeathFinished(self: NPC, killer: Entity) {
        super.onDeathFinished(self, killer)
        self.reTransform()
    }

    override fun beforeDamageReceived(self: NPC, attacker: Entity, state: BattleState) {
        val lifepoints = self.skills.lifepoints
        if (state.estimatedHit + Integer.max(state.secondaryHit, 0) > lifepoints - 1) {
            state.estimatedHit = lifepoints - 1
            state.secondaryHit = -1
            setAttribute(self, "shouldRun", true)
        }
    }

    override fun tick(self: NPC): Boolean {
        if (getAttribute(self, "shouldRun", false)) {
            self.properties.combatPulse.stop()
            forceWalk(self, self.properties.spawnLocation, "smart")
            removeAttribute(self, "shouldRun")
        }
        return true
    }

    private fun handleFungicideSpray(player: Player, used: Node, with: Node): Boolean {
        if (with !is NPC) return false
        if (!removeItem(player, used.id) || used.id == Items.FUNGICIDE_SPRAY_0_7431) return false
        for (i in fungicideSpray) {
            when (i) {
                Items.FUNGICIDE_SPRAY_0_7431 -> {
                    sendMessage(player, "Your fungicide spray is currently empty.")
                    return true
                }

                else -> {
                    replaceSlot(player, used.asItem().slot, Item(used.id + 1))
                    continue
                }
            }
        }
        if (with.getSkills().lifepoints > 7) {
          //sendMessage(player, "The Zygomite is on its last legs! Finish it quickly!")
            sendMessage(player, "The zygomite isn't weak enough to be affected by the fungicide.")
        } else {
            sendMessage(player, "The Zygomite is covered in fungicide. It bubbles away to nothing!")
            with.impactHandler.manualHit(player, with.getSkills().lifepoints, ImpactHandler.HitsplatType.NORMAL)
        }
        return true
    }

}
