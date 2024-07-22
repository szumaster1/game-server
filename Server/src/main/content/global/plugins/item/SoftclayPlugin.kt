package content.global.plugins.item

import core.api.consts.Items
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class SoftclayPlugin : UseWithHandler(Items.CLAY_434) {

    private val CLAY = Item(Items.CLAY_434)
    private val SOFT_CLAY = Item(Items.SOFT_CLAY_1761)
    private val BOWL_OF_WATER = Item(Items.BOWL_OF_WATER_1921)
    private val BOWL = Item(Items.BOWL_1923)
    private val BUCKET = Item(Items.BUCKET_1925)
    private val BUCKET_OF_WATER = Item(Items.BUCKET_OF_WATER_1929)
    private val JUG = Item(Items.JUG_1935)
    private val JUG_OF_WATER = Item(Items.JUG_OF_WATER_1937)

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(Items.BOWL_OF_WATER_1921, ITEM_TYPE, this)
        addHandler(Items.BUCKET_OF_WATER_1929, ITEM_TYPE, this)
        addHandler(Items.JUG_OF_WATER_1937, ITEM_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        val handler = object : SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, SOFT_CLAY) {
            override fun create(amount: Int, index: Int) {
                player.pulseManager.run(object : Pulse(2, player) {
                    var count = 0
                    override fun pulse(): Boolean {
                        if (!this@SoftclayPlugin.create(player, event)) {
                            return true
                        }
                        return ++count >= amount
                    }
                })
            }

            override fun getAll(index: Int): Int {
                return player.inventory.getAmount(CLAY)
            }
        }
        if (player.inventory.getAmount(CLAY) == 1) {
            create(player, event)
        } else {
            handler.open()
        }
        return true
    }

    private fun create(player: Player, event: NodeUsageEvent): Boolean {
        var removeItem: Item? = null
        var returnItem: Item? = null
        if (event.usedItem.id == Items.BUCKET_OF_WATER_1929 || event.baseItem.id == Items.BUCKET_OF_WATER_1929) {
            removeItem = BUCKET_OF_WATER
            returnItem = BUCKET
        }
        if (event.usedItem.id == Items.BOWL_OF_WATER_1921 || event.baseItem.id == Items.BOWL_OF_WATER_1921) {
            removeItem = BOWL_OF_WATER
            returnItem = BOWL
        }
        if (event.usedItem.id == Items.JUG_OF_WATER_1937 || event.baseItem.id == Items.JUG_OF_WATER_1937) {
            removeItem = JUG_OF_WATER
            returnItem = JUG
        }

        if (player.inventory.containsItem(CLAY) && player.inventory.containsItem(removeItem)) {
            player.inventory.remove(removeItem)
            player.inventory.remove(CLAY)
            player.packetDispatch.sendMessage("You mix the clay and water. You now have some soft, workable clay.")
            player.inventory.add(SOFT_CLAY)
            player.inventory.add(returnItem)
            return true
        } else {
            return false
        }
    }
}
