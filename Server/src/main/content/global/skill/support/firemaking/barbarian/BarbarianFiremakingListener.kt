package content.global.skill.support.firemaking.barbarian

import content.global.skill.BarbarianTraining
import content.global.skill.support.firemaking.FiremakingListener
import core.api.consts.Items
import core.api.getAttribute
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.GroundItem

/**
 * Barbarian firemaking listener.
 */
class BarbarianFiremakingListener : InteractionListener {

    companion object {
        private val tools = intArrayOf(
            Items.TRAINING_BOW_9705,
            Items.LONGBOW_839,
            Items.SHORTBOW_841,
            Items.OAK_SHORTBOW_843,
            Items.OAK_LONGBOW_845,
            Items.WILLOW_LONGBOW_847,
            Items.WILLOW_SHORTBOW_849,
            Items.MAPLE_LONGBOW_851,
            Items.MAPLE_SHORTBOW_853,
            Items.YEW_LONGBOW_855,
            Items.YEW_SHORTBOW_857,
            Items.MAGIC_LONGBOW_859,
            Items.MAGIC_SHORTBOW_861,
            Items.SEERCULL_6724
        )
    }

    override fun defineListeners() {

        onUseWith(IntType.ITEM, tools, *FiremakingListener.logs) { player, _, with ->
            val barbarianFiremaking = getAttribute(player, BarbarianTraining.BARBARIAN_FIREMAKING_TUTORIAL, false)
            val completeBarbarianFiremaking =
                getAttribute(player, BarbarianTraining.BARBARIAN_FIREMAKING_COMPLETE, false)

            if (completeBarbarianFiremaking || barbarianFiremaking) {
                player.pulseManager.run(BarbarianFiremakingPulse(player, with.asItem(), null))
            } else {
                sendMessage(player, "In order to be able to lighting fires, Otto Godblessed must be talked to.")
            }
            return@onUseWith true
        }

        onUseWith(IntType.GROUNDITEM, tools, *FiremakingListener.logs) { player, _, with ->
            val barbarianFiremaking = getAttribute(player, BarbarianTraining.BARBARIAN_FIREMAKING_TUTORIAL, false)
            val completeBarbarianFiremaking =
                getAttribute(player, BarbarianTraining.BARBARIAN_FIREMAKING_COMPLETE, false)

            if (completeBarbarianFiremaking || barbarianFiremaking) {
                player.pulseManager.run(BarbarianFiremakingPulse(player, with.asItem(), with as GroundItem))
            } else {
                sendMessage(player, "In order to be able to lighting fires, Otto Godblessed must be talked to.")
            }
            return@onUseWith true
        }
    }
}
