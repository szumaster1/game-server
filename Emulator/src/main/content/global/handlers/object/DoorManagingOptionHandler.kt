package content.global.handlers.`object`

import content.region.kandarin.quest.grail.HolyGrail
import core.cache.def.impl.SceneryDefinition
import core.game.global.action.DoorActionHandler
import core.game.global.action.DoorActionHandler.handleDoor
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.world.map.Location
import core.game.world.map.RegionManager.isTeleportPermitted
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Scenery managing plugin.
 * @author Emperor
 */
@Initializable
class DoorManagingOptionHandler : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        SceneryDefinition.setOptionHandler("open", this)
        SceneryDefinition.setOptionHandler("close", this)
        SceneryDefinition.setOptionHandler("shut", this)
        SceneryDefinition.setOptionHandler("go-through", this)
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val `object` = node as Scenery
        if (`object`.type != 9 && player.location != node.getLocation() && !player.location.isNextTo(`object`) && `object`.name.contains("cupboard")) {
            return true
        }
        val name = `object`.name.lowercase()
        if (name.contains("drawers") || name.contains("wardrobe") || name.contains("cupboard")) {
            when (option) {
                "open", "go-through" -> {
                    if (`object`.isActive) {
                        SceneryBuilder.replace(`object`, `object`.transform(`object`.id + 1), 80)
                    }
                    return true
                }

                "close", "shut" -> {
                    SceneryBuilder.replace(`object`, `object`.transform(`object`.id - 1))
                    return true
                }
            }
            return true
        }
        if (name.contains("trapdoor") || name.contains("trap door")) {
            val destination = `object`.location.transform(0, 6400, 0)
            if (!isTeleportPermitted(destination)) {
                player.packetDispatch.sendMessage("This doesn't seem to go anywhere.")
                return true
            }
            player.properties.teleportLocation = destination
            return true
        }
        if (!name.contains("door") && !name.contains("gate") && !name.contains("fence") && !name.contains("wall") && !name.contains("exit") && !name.contains("entrance")) {
            return false
        }
        if (HolyGrail.MERLIN_DOOR_ID == `object`.id && !player.getQuestRepository().hasStarted("Holy Grail")) {
            if (`object`.location == HolyGrail.MERLIN_DOOR_LOCATION_OPEN || `object`.location == HolyGrail.MERLIN_DOOR_LOCATION_OPEN) return false
        }
        handleDoor(player, `object`)
        return true
    }

    override fun getDestination(n: Node, node: Node): Location? {
        val o = node as Scenery
        if (o.type < 4 || o.type == 9) {
            return DoorActionHandler.getDestination((n as Player), o)
        }
        return null
    }
}
