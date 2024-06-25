package content.global.plugins.iface

import content.global.skill.production.crafting.armour.LeatherCrafting
import content.global.skill.production.crafting.armour.LeatherCrafting.SoftLeather
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class LeatherCraftInterfacePlugin : UseWithHandler(
    LeatherCrafting.LEATHER,
    LeatherCrafting.HARD_LEATHER,
    LeatherCrafting.GREEN_LEATHER,
    LeatherCrafting.BLUE_LEATHER,
    LeatherCrafting.RED_LEATHER,
    LeatherCrafting.BLACK_LEATHER
) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(LeatherCrafting.NEEDLE, ITEM_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        if (event.baseItem.id == LeatherCrafting.LEATHER || event.usedItem.id == LeatherCrafting.LEATHER) {
            SoftLeather.open(event.player)
        } else if (event.baseItem.id == LeatherCrafting.HARD_LEATHER || event.usedItem.id == LeatherCrafting.HARD_LEATHER) {
            event.player.dialogueInterpreter.open(48923, "hard")
        } else {
            event.player.dialogueInterpreter.open(48923, "dragon", event.usedItem.id)
        }
        return true
    }
}
