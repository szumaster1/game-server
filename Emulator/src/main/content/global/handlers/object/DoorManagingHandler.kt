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
import core.api.*
import org.rs.consts.Animations
import org.rs.consts.Sounds

/**
 * Plugin used for handling the opening/closing of (double) doors/gates/fences/...
 */
@Initializable
class DoorManagingHandler : OptionHandler() {

    private val doorOptions = listOf("open", "close", "shut", "go-through")

    override fun newInstance(arg: Any?): Plugin<Any> {
        doorOptions.forEach { SceneryDefinition.setOptionHandler(it, this) }
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val obj = node as? Scenery ?: return false
        val name = obj.name.lowercase()

        if (shouldIgnoreObject(player, obj)) return true

        return when {
            isFurniture(name) -> handleFurnitureAction(player, obj, name, option)
            isTrapdoor(name) -> handleTrapdoorAction(player, obj)
            isDoorGateFenceWall(name) -> handleDoorAction(player, obj)
            else -> false
        }
    }

    private fun shouldIgnoreObject(player: Player, obj: Scenery): Boolean {
        return obj.type != 9 && !player.location.isNextTo(obj) && obj.name.contains("cupboard")
    }

    private fun isFurniture(name: String): Boolean {
        return name.contains("drawers") || name.contains("wardrobe") || name.contains("cupboard")
    }

    private fun handleFurnitureAction(player: Player, obj: Scenery, name: String, option: String): Boolean {
        when (option) {
            "open" -> openFurniture(player, obj, name)
            "go-through" -> if (obj.isActive) SceneryBuilder.replace(obj, obj.transform(obj.id + 1), 80)
            "close", "shut" -> closeFurniture(player, obj, name)
        }
        return true
    }

    private fun openFurniture(player: Player, obj: Scenery, name: String) {
        if (!obj.isActive) {
            when {
                name.contains("drawers") -> playAudio(player, Sounds.DRAWER_OPEN_64)
                name.contains("wardrobe") -> {
                    animate(player, Animations.OPEN_WARDROBE_545, false)
                    playAudio(player, Sounds.WARDROBE_OPEN_96)
                }

                name.contains("cupboard") -> playAudio(player, Sounds.CUPBOARD_OPEN_58)
            }
        }
    }

    private fun closeFurniture(player: Player, obj: Scenery, name: String) {
        if (obj.isActive) {
            when {
                name.contains("drawers") -> playAudio(player, Sounds.DRAWER_CLOSE_63)
                name.contains("wardrobe") -> {
                    animate(player, Animations.CLOSE_WARDROBE_544, false)
                    playAudio(player, Sounds.WARDROBE_CLOSE_95)
                }

                name.contains("cupboard") -> playAudio(player, Sounds.CUPBOARD_CLOSE_57)
            }
            SceneryBuilder.replace(obj, obj.transform(obj.id - 1))
        }
    }

    private fun isTrapdoor(name: String): Boolean {
        return name.contains("trapdoor") || name.contains("trap door")
    }

    private fun handleTrapdoorAction(player: Player, obj: Scenery): Boolean {
        val destination = obj.location.transform(0, 6400, 0)
        if (!RegionManager.isTeleportPermitted(destination)) {
            player.packetDispatch.sendMessage("This doesn't seem to go anywhere.")
            return true
        }
        player.properties.teleportLocation = destination
        return true
    }

    private fun isDoorGateFenceWall(name: String): Boolean {
        return listOf("door", "gate", "fence", "wall", "exit", "entrance").any { name.contains(it) }
    }

    private fun handleDoorAction(player: Player, obj: Scenery): Boolean {
        DoorActionHandler.handleDoor(player, obj)
        return true
    }

    override fun getDestination(n: Node, node: Node): Location? {
        val obj = node as? Scenery ?: return null
        return if (obj.type < 4 || obj.type == 9) {
            DoorActionHandler.getDestination(n as Player, obj)
        } else null
    }
}
