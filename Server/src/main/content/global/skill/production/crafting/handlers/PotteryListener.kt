package content.global.skill.production.crafting.handlers

import content.global.skill.production.crafting.data.PotteryData
import content.global.skill.production.crafting.item.PotteryCraftPulse
import core.api.amountInInventory
import core.api.consts.Items
import core.api.consts.Scenery
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item

class PotteryListener : InteractionListener {
    override fun defineListeners() {
        onUseWith(IntType.SCENERY, Items.SOFT_CLAY_1761, *POTTERY) { player, used, _ ->
            object : SkillDialogueHandler(player, SkillDialogue.FIVE_OPTION, getPottery(false)) {
                override fun create(amount: Int, index: Int) {
                    player.pulseManager.run(
                        PotteryCraftPulse(
                            player,
                            used.asItem(),
                            amount,
                            PotteryData.values()[index]
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
        val POTTERY = intArrayOf(
            Scenery.POTTER_S_WHEEL_2642,
            Scenery.POTTERY_OVEN_2643,
            Scenery.POTTERY_OVEN_4308,
            Scenery.POTTER_S_WHEEL_4310,
            Scenery.POTTER_S_WHEEL_20375,
            Scenery.POTTER_S_WHEEL_34801,
            Scenery.POTTERY_OVEN_34802
        )

        fun getPottery(finished: Boolean): Array<Item?> {
            val items = arrayOfNulls<Item>(PotteryData.values().size)
            for (i in items.indices) {
                items[i] = if (finished) PotteryData.values()[i].product else PotteryData.values()[i].unfinished
            }
            return items
        }
    }
}
