package content.global.skill.production.crafting.plugin

import content.global.skill.production.crafting.data.LeatherData
import content.global.skill.production.crafting.data.LeatherData.SoftLeather
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Leather craft interface plugin.
 */
@Initializable
class LeatherCraftInterfacePlugin : UseWithHandler(
    LeatherData.LEATHER,
    LeatherData.HARD_LEATHER,
    LeatherData.GREEN_LEATHER,
    LeatherData.BLUE_LEATHER,
    LeatherData.RED_LEATHER,
    LeatherData.BLACK_LEATHER
) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(LeatherData.NEEDLE, ITEM_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        if (event.baseItem.id == LeatherData.LEATHER || event.usedItem.id == LeatherData.LEATHER) {
            SoftLeather.open(event.player)
        } else if (event.baseItem.id == LeatherData.HARD_LEATHER || event.usedItem.id == LeatherData.HARD_LEATHER) {
            event.player.dialogueInterpreter.open(48923, "hard")
        } else {
            event.player.dialogueInterpreter.open(48923, "dragon", event.usedItem.id)
        }
        return true
    }
}
