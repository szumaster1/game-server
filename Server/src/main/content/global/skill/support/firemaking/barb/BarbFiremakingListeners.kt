package content.global.skill.support.firemaking.barb

import content.global.skill.BarbarianTraining
import core.api.consts.Items
import core.api.getAttribute
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.GroundItem

class BarbFiremakingListeners : InteractionListener {

    companion object {
        val bows = intArrayOf(Items.TRAINING_BOW_9705, Items.SHORTBOW_841, Items.LONGBOW_839, Items.OAK_SHORTBOW_843, Items.OAK_LONGBOW_845, Items.WILLOW_LONGBOW_847, Items.WILLOW_SHORTBOW_849, Items.MAPLE_LONGBOW_851, Items.MAPLE_SHORTBOW_853, Items.YEW_LONGBOW_855, Items.YEW_SHORTBOW_857, Items.MAGIC_LONGBOW_859, Items.MAGIC_SHORTBOW_861, Items.SEERCULL_6724)
        val logs = intArrayOf(Items.LOGS_1511, Items.OAK_LOGS_1521, Items.WILLOW_LOGS_1519, Items.MAPLE_LOGS_1517, Items.YEW_LOGS_1515, Items.MAGIC_LOGS_1513, Items.ACHEY_TREE_LOGS_2862, Items.PYRE_LOGS_3438, Items.OAK_PYRE_LOGS_3440, Items.WILLOW_PYRE_LOGS_3442, Items.MAPLE_PYRE_LOGS_3444, Items.YEW_PYRE_LOGS_3446, Items.MAGIC_PYRE_LOGS_3448, Items.TEAK_PYRE_LOGS_6211, Items.MAHOGANY_PYRE_LOG_6213, Items.MAHOGANY_LOGS_6332, Items.TEAK_LOGS_6333, Items.RED_LOGS_7404, Items.GREEN_LOGS_7405, Items.BLUE_LOGS_7406, Items.PURPLE_LOGS_10329, Items.WHITE_LOGS_10328, Items.SCRAPEY_TREE_LOGS_8934, Items.DREAM_LOG_9067, Items.ARCTIC_PYRE_LOGS_10808, Items.ARCTIC_PINE_LOGS_10810, Items.SPLIT_LOG_10812, Items.WINDSWEPT_LOGS_11035, Items.EUCALYPTUS_LOGS_12581, Items.EUCALYPTUS_PYRE_LOGS_12583, Items.JOGRE_BONES_3125)
    }

    override fun defineListeners() {
        onUseWith(IntType.ITEM, bows, *logs) { player, _, with ->
            if (getAttribute(player, BarbarianTraining.ATTR_BARB_TRAIN_FIREMAKING_BEGIN, false) || getAttribute(player, BarbarianTraining.ATTR_BARB_TRAIN_FIREMAKING, false))
                player.pulseManager.run(BarbFiremakingPulse(player, with.asItem(), null))
            return@onUseWith true
        }

        onUseWith(IntType.GROUNDITEM, bows, *logs) { player, _, with ->
            if (getAttribute(player, BarbarianTraining.ATTR_BARB_TRAIN_FIREMAKING_BEGIN, false) || getAttribute(player, BarbarianTraining.ATTR_BARB_TRAIN_FIREMAKING, false))
                player.pulseManager.run(BarbFiremakingPulse(player, with.asItem(), with as GroundItem))
            return@onUseWith true
        }
    }
}