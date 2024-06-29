package content.global.skill.production.crafting.handlers

import content.global.skill.production.crafting.data.PotteryData
import content.global.skill.production.crafting.handlers.PotteryListener.Companion.getPottery
import content.global.skill.production.crafting.item.FirePotteryPulse
import core.api.amountInInventory
import core.api.consts.Items
import core.api.consts.Scenery
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class UnfiredPotteryListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, UNFIRED_POTTERY, *POTTERY_OVEN) { player, used, _ ->
            object : SkillDialogueHandler(player, SkillDialogue.FIVE_OPTION, getPottery(true)) {
                override fun create(amount: Int, index: Int) {
                    player.pulseManager.run(
                        FirePotteryPulse(
                            player,
                            PotteryData.values()[index].unfinished,
                            PotteryData.values()[index],
                            amount
                        )
                    )
                }

                override fun getAll(index: Int): Int {
                    return amountInInventory(player, used.id)
                }
            }.open()
            return@onUseWith true
        }
    }

    companion object {
        val UNFIRED_POTTERY = intArrayOf(
            Items.UNFIRED_POT_1787,
            Items.UNFIRED_PIE_DISH_1789,
            Items.UNFIRED_BOWL_1791,
            Items.UNFIRED_PLANT_POT_5352,
            Items.UNFIRED_POT_LID_4438
        )
        val POTTERY_OVEN = intArrayOf(
            Scenery.POTTERY_OVEN_2643,
            Scenery.POTTERY_OVEN_4308,
            Scenery.POTTERY_OVEN_11601,
            Scenery.POTTERY_OVEN_34802
        )
    }
}
