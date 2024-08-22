package content.global.skill.production.crafting.handlers

import content.global.skill.production.crafting.data.SnakeskinData
import content.global.skill.production.crafting.item.SnakeskinCraftPulse
import core.api.amountInInventory
import cfg.consts.Items
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item

/**
 * Snakeskin listener.
 */
class SnakeskinListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.NEEDLE_1733, Items.SNAKESKIN_6289) { player, used, _ ->
            object : SkillDialogueHandler(player, SkillDialogue.FIVE_OPTION, skins) {
                override fun create(amount: Int, index: Int) {
                    player.pulseManager.run(
                        SnakeskinCraftPulse(
                            player = player,
                            node = used.asItem(),
                            amount = amount,
                            skin = SnakeskinData.values()[index]
                        )
                    )
                }

                override fun getAll(index: Int): Int {
                    return amountInInventory(player, Items.SNAKESKIN_6289)
                }

                public override fun getName(item: Item): String {
                    return when (item.id) {
                        Items.SNAKESKIN_BOOTS_6328 -> "Boots"
                        Items.SNAKESKIN_VBRACE_6330 -> "Vambs"
                        Items.SNAKESKIN_BANDANA_6326 -> "Bandana"
                        Items.SNAKESKIN_CHAPS_6324 -> "Chaps"
                        else -> "Body"
                    }
                }
            }.open()
            return@onUseWith true
        }
    }

    companion object {
        val skins: Array<Item?>
            get() {
                val items = arrayOfNulls<Item>(SnakeskinData.values().size)
                for (i in items.indices) {
                    items[i] = SnakeskinData.values()[i].product
                }
                return items
            }
    }
}
