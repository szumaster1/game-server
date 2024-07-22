package content.global.plugins.item

import core.api.consts.Components
import core.api.consts.Items
import core.api.setInterfaceText
import core.cache.def.impl.ItemDefinition
import core.game.component.Component
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.RandomFunction

@Initializable
class GnomecopterTicketPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        ItemDefinition.forId(Items.GNOMECOPTER_TICKET_12843).handlers["option:read"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        player.interfaceManager.open(Component(Components.CARPET_TICKET_729))
        var info = "Gnomecopter ticket:"
        info += "<br>" + "Castle Wars" // Destination
        info += "<br>" + "Ref. #000"
        for (i in 3 until 8) {
            info += RandomFunction.randomize(10)
        }
        setInterfaceText(player, info, Components.CARPET_TICKET_729, 2)
        return true
    }
}
