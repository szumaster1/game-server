package content.global.skill.production.cooking.plugins

import content.global.skill.production.cooking.fermenting.WineFermentingPulse
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.GameWorld.Pulser
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class WineFermentPlugin : UseWithHandler(1937) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(1987, ITEM_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        if (player.getSkills().getLevel(Skills.COOKING) < 35) {
            player.packetDispatch.sendMessage("You need a cooking level of 35 to do this.")
            return true
        }
        if (player.inventory.remove(GRAPES, JUG_OF_WATER)) {
            player.inventory.add(UNFERMENTED_WINE)
            Pulser.submit(WineFermentingPulse(1, player))
        }
        return true
    }

    companion object {
        private val GRAPES = Item(1987, 1)
        private val JUG_OF_WATER = Item(1937, 1)
        private val UNFERMENTED_WINE = Item(1995, 1)
    }
}
