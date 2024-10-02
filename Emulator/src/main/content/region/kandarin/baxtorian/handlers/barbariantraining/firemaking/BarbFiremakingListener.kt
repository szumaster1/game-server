package content.region.kandarin.baxtorian.handlers.barbariantraining.firemaking

import content.global.skill.firemaking.FiremakingListener.Companion.logs
import content.region.kandarin.baxtorian.handlers.barbariantraining.BarbarianTraining
import core.api.*
import org.rs.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class BarbFiremakingListener : InteractionListener {

    companion object {
        val crystalEquipment = intArrayOf(Items.NEW_CRYSTAL_BOW_4212, Items.CRYSTAL_BOW_FULL_4214, Items.CRYSTAL_BOW_9_10_4215, Items.CRYSTAL_BOW_8_10_4216, Items.CRYSTAL_BOW_7_10_4217, Items.CRYSTAL_BOW_6_10_4218, Items.CRYSTAL_BOW_5_10_4219, Items.CRYSTAL_BOW_4_10_4220, Items.CRYSTAL_BOW_3_10_4221, Items.CRYSTAL_BOW_2_10_4222, Items.CRYSTAL_BOW_1_10_4223, Items.NEW_CRYSTAL_SHIELD_4224)
        val tools = intArrayOf(Items.NEW_CRYSTAL_BOW_4212, Items.CRYSTAL_BOW_FULL_4214, Items.CRYSTAL_BOW_9_10_4215, Items.CRYSTAL_BOW_8_10_4216, Items.CRYSTAL_BOW_7_10_4217, Items.CRYSTAL_BOW_6_10_4218, Items.CRYSTAL_BOW_5_10_4219, Items.CRYSTAL_BOW_4_10_4220, Items.CRYSTAL_BOW_3_10_4221, Items.CRYSTAL_BOW_2_10_4222, Items.CRYSTAL_BOW_1_10_4223, Items.NEW_CRYSTAL_SHIELD_4224, Items.TRAINING_BOW_9705, Items.LONGBOW_839, Items.SHORTBOW_841, Items.OAK_SHORTBOW_843, Items.OAK_LONGBOW_845, Items.WILLOW_LONGBOW_847, Items.WILLOW_SHORTBOW_849, Items.MAPLE_LONGBOW_851, Items.MAPLE_SHORTBOW_853, Items.YEW_LONGBOW_855, Items.YEW_SHORTBOW_857, Items.MAGIC_LONGBOW_859, Items.MAGIC_SHORTBOW_861, Items.SEERCULL_6724)
    }

    override fun defineListeners() {
        onUseWith(IntType.ITEM, tools, *logs) { player, used, with ->
            if (used.id in crystalEquipment) {
                sendMessage(player, "The bow resists all attempts to light the fire. It seems that the sentient tools of the elves don't approve of you burning down forests.")
                return@onUseWith false
            }
            if (getAttribute(player, BarbarianTraining.FM_BASE, false) || getAttribute(player, BarbarianTraining.FM_FULL, false)) {
                submitIndividualPulse(player, BarbFiremakingPulse(player, with.asItem(), null))
            } else {
                sendMessage(player, "In order to be able to lighting fires, Otto Godblessed must be talked to.")
            }
            return@onUseWith true
        }
    }
}
