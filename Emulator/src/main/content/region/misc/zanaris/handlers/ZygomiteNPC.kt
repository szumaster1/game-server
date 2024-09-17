package content.region.misc.zanaris.handlers

import org.rs.consts.Items
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
        Items.FUNGICIDE_SPRAY_10_7421,
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
        val lifepoints = self.getSkills().lifepoints
        if (state.estimatedHit > -1) {
            if (lifepoints - state.estimatedHit < 1) {
                state.estimatedHit = 0
                if (lifepoints > 1) {
                    state.estimatedHit = lifepoints - 1
                }
            }
        }
        if (state.secondaryHit > -1) {
            if (lifepoints - state.secondaryHit < 1) {
                state.secondaryHit = 0
                if (lifepoints > 1) {
                    state.secondaryHit = lifepoints - 1
                }
            }
        }
        val totalHit = state.estimatedHit + state.secondaryHit
        if (lifepoints - totalHit < 1) {
            state.estimatedHit = 0
            state.secondaryHit = 0
        }
    }

    private fun handleFungicideSpray(player: Player, used: Node, with: Node): Boolean {
        if (with !is NPC) return false
        if (used.id != Items.FUNGICIDE_SPRAY_0_7431) {
            if (used.id in fungicideSpray) {
                replaceSlot(player, used.asItem().slot, Item(used.id + 1))
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
        } else {
            sendMessage(player, "Your fungicide spray is currently empty.")
        }
        if (with.getSkills().lifepoints > 7) {
            sendMessage(player, "The zygomite isn't weak enough to be affected by the fungicide.")
        } else {
            sendMessage(player, "The Zygomite is covered in fungicide. It bubbles away to nothing!")
            with.impactHandler.manualHit(player, with.getSkills().lifepoints, ImpactHandler.HitsplatType.NORMAL)
        }
        return true
    }

}
