package content.global.handlers.`object`

import core.cache.def.impl.SceneryDefinition
import core.game.global.action.DoorActionHandler
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.world.map.Location
import core.game.world.map.RegionManager
import core.plugin.Initializable
import core.plugin.Plugin
import org.rs.consts.Sounds
import core.api.playAudio
import core.api.animate

/**
 * Plugin used for handling the opening/closing of (double) doors/gates/fences/...
 * @author Emperor
 */
@Initializable
class DoorManaging : OptionHandler() {

    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any> {
        SceneryDefinition.setOptionHandler("open", this)
        SceneryDefinition.setOptionHandler("close", this)
        SceneryDefinition.setOptionHandler("shut", this)
        SceneryDefinition.setOptionHandler("go-through", this)
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val obj = node as Scenery
        if (obj.type != 9 && !player.location.equals(node.location) && !player.location.isNextTo(obj) && obj.name.contains("cupboard")) {
            return true
        }
        val name = obj.name.lowercase()
        when {
            name.contains("drawers") || name.contains("wardrobe") || name.contains("cupboard") -> {
                when (option) {
                    "open" -> {
                        when {
                            name.contains("drawers") -> playAudio(player, Sounds.DRAWER_OPEN_64)
                            name.contains("wardrobe") -> {
                                animate(player, 545, false)
                                playAudio(player, Sounds.WARDROBE_OPEN_96)
                            }
                            name.contains("cupboard") -> playAudio(player, Sounds.CUPBOARD_OPEN_58)
                        }
                    }
                    "go-through" -> {
                        if (obj.isActive) {
                            SceneryBuilder.replace(obj, obj.transform(obj.id + 1), 80)
                        }
                        return true
                    }
                    "close", "shut" -> {
                        when {
                            name.contains("drawers") -> playAudio(player, Sounds.DRAWER_CLOSE_63)
                            name.contains("wardrobe") -> {
                                animate(player, 544, false)
                                playAudio(player, Sounds.WARDROBE_CLOSE_95)
                            }
                            name.contains("cupboard") -> playAudio(player, Sounds.CUPBOARD_CLOSE_57)
                        }
                        SceneryBuilder.replace(obj, obj.transform(obj.id - 1))
                        return true
                    }
                }
                return true
            }
            name.contains("trapdoor") || name.contains("trap door") -> {
                val destination = obj.location.transform(0, 6400, 0)
                if (!RegionManager.isTeleportPermitted(destination)) {
                    player.packetDispatch.sendMessage("This doesn't seem to go anywhere.")
                    return true
                }
                player.properties.teleportLocation = destination
                return true
            }
            !name.contains("door") && !name.contains("gate") && !name.contains("fence") && !name.contains("wall") && !name.contains("exit") && !name.contains("entrance") -> {
                return false
            }
            else -> {
                DoorActionHandler.handleDoor(player, obj)
                return true
            }
        }
    }

    override fun getDestination(n: Node, node: Node): Location? {
        val obj = node as Scenery
        return if (obj.type < 4 || obj.type == 9) {
            DoorActionHandler.getDestination(n as Player, obj)
        } else null
    }
}