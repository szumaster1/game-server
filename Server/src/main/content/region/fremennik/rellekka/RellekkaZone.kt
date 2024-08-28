package content.region.fremennik.rellekka

import content.dialogue.rellekka.JarvaldDialogue
import core.game.interaction.Option
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneBuilder
import core.plugin.ClassScanner.definePlugin
import core.plugin.Initializable
import core.plugin.Plugin
import java.util.*

/**
 * Represents the Rellekka zone.
 */
@Initializable
class RellekkaZone : MapZone("rellekka", true), Plugin<Any> {

    override fun newInstance(arg: Any?): Plugin<Any> {
        ZoneBuilder.configure(this)
        definePlugin(JarvaldDialogue())
        definePlugin(object : OptionHandler() {
            override fun newInstance(arg: Any?): Plugin<Any> {
                return this
            }

            override fun handle(player: Player, node: Node, option: String): Boolean {
                return true
            }
        })
        return this
    }

    override fun interact(e: Entity, target: Node, option: Option): Boolean {
        if (e is Player) {
            val player = e
            when (target.id) {
                4306, 4310, 4309, 4304, 4308 -> {
                    player.sendMessage("Only Fremenniks may use this " + target.name.lowercase(Locale.getDefault()) + ".")
                    return true
                }

                100 -> {
                    player.dialogueInterpreter.sendDialogue("You try to open the trapdoor but it won't budge! It looks like the", "trapdoor can only be opened from the other side.")
                    return true
                }

                2435, 2436, 2437, 2438 -> if (option.name == "Travel") {
                    player.dialogueInterpreter.open(target.id, target, true)
                    return true
                }
            }
        }
        return super.interact(e, target, option)
    }

    override fun fireEvent(identifier: String, vararg args: Any): Any? {
        return null
    }

    override fun configure() {
        register(ZoneBorders(2602, 3639, 2739, 3741))
    }
}
