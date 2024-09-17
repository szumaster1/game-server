package content.global.skill.production.crafting.plugin

import content.global.skill.production.crafting.data.Leather
import content.global.skill.production.crafting.data.Leather.SoftLeather
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Leather craft interface plugin.
 */
@Initializable
class LeatherCraftInterfacePlugin : UseWithHandler(Leather.LEATHER, Leather.HARD_LEATHER, Leather.GREEN_LEATHER, Leather.BLUE_LEATHER, Leather.RED_LEATHER, Leather.BLACK_LEATHER) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(Leather.NEEDLE, ITEM_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        if (event.baseItem.id == Leather.LEATHER || event.usedItem.id == Leather.LEATHER) {
            SoftLeather.open(event.player)
        } else if (event.baseItem.id == Leather.HARD_LEATHER || event.usedItem.id == Leather.HARD_LEATHER) {
            event.player.dialogueInterpreter.open(48923, "hard")
        } else {
            event.player.dialogueInterpreter.open(48923, "dragon", event.usedItem.id)
        }
        return true
    }
}
