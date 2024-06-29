package content.global.skill.production.crafting.plugins

import content.global.skill.production.crafting.data.SnakeskinData
import content.global.skill.production.crafting.item.SnakeskinCraftPulse
import core.api.consts.Items
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class SnakeSkinPlugin : UseWithHandler(Items.NEEDLE_1733) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(Items.SNAKESKIN_6289, ITEM_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        object : SkillDialogueHandler(player, SkillDialogue.FIVE_OPTION, (skins as Array<Any?>?)!!) {
            override fun create(amount: Int, index: Int) {
                player.pulseManager.run(
                    SnakeskinCraftPulse(
                        player,
                        event.usedItem,
                        amount,
                        SnakeskinData.values()[index]
                    )
                )
            }

            override fun getAll(index: Int): Int {
                return player.inventory.getAmount(Item(Items.SNAKESKIN_6289))
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
        return true
    }

    val skins: Array<Item?>
        get() {
            val items = arrayOfNulls<Item>(SnakeskinData.values().size)
            for (i in items.indices) {
                items[i] = SnakeskinData.values()[i].product
            }
            return items
        }
}
