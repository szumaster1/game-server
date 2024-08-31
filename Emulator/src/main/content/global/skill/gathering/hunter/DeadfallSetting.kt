package content.global.skill.gathering.hunter

import content.global.skill.support.firemaking.data.Log
import core.api.inInventory
import core.api.sendMessage
import core.game.node.Node
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

/**
 * Handles a deadfall trap.
 * @author Vexia
 */
class DeadfallSetting : TrapSetting(intArrayOf(28935, 19205), arrayOf(Item(946)), intArrayOf(28937, 19206), intArrayOf(10138, 6006, 12574, 341, 2132), "set-trap", 23, -1, Animation(5208), Animation(9726), true) {

    override fun hasItems(player: Player): Boolean {
        if (!super.hasItems(player)) {
            sendMessage(player, "You need a knife in order to set a deadfall trap.")
            return false
        }
        for (log in Log.values()) {
            if (inInventory(player, log.logId, 1)) {
                return true
            }
        }
        sendMessage(player, "You need logs in order to set a deadfall trap.")
        return false
    }

    override fun createHook(wrapper: TrapWrapper): TrapHook {
        return TrapHook(wrapper, getLocations(wrapper.`object`).toTypedArray())
    }

    override fun reward(player: Player, node: Node, wrapper: TrapWrapper) {
        player.inventory.remove(Item(getLog(player)!!.logId))
    }

    override fun clear(wrapper: TrapWrapper, type: Int): Boolean {
        if (super.clear(wrapper, type)) {
            SceneryBuilder.add(wrapper.`object`.transform(getNodeForObjectId(if (wrapper.isCaught) wrapper.originalId else wrapper.`object`.id)))
            return true
        }
        return false
    }

    override fun canCatch(wrapper: TrapWrapper, npc: NPC): Boolean {
        val x = wrapper.`object`.location.x
        val y = wrapper.`object`.location.y
        val direction = wrapper.`object`.direction
        var dir = 0
        if (direction == Direction.NORTH) {
            dir = if (npc.location.y < y) 1 else 0
        } else if (direction == Direction.SOUTH) {
            dir = if (npc.location.y < y) 0 else 1
        } else if (direction == Direction.WEST) {
            dir = if (npc.location.x > x) 1 else 0
        } else {
            dir = if (npc.location.x > x) 0 else 1
        }
        npc.faceLocation(wrapper.`object`.location)
        wrapper.`object`.attributes.setAttribute("kebbit-dir", dir)
        return true
    }

    override fun isSuccess(player: Player, node: TrapNode): Boolean {
        return true
    }

    override fun getTransformId(wrapper: TrapWrapper, node: TrapNode): Int {
        val dir = wrapper.`object`.attributes.getAttribute("kebbit-dir", 0)
        val id = if (dir == 0) node.objectIds[0] else node.objectIds[1]
        return id
    }

    override fun getFinalId(wrapper: TrapWrapper, node: TrapNode): Int {
        return node.objectIds[2]
    }

    override fun buildObject(player: Player, node: Node): Scenery {
        return node.asScenery().transform(getObjectForNode(node))
    }

    override fun getLimitMessage(player: Player): String {
        return "You can only have one deadfall trap at a time."
    }

    override fun exceedsLimit(player: Player): Boolean {
        return HunterManager.getInstance(player).trapAmount > 0
    }

    /**
     * Gets the list of locations.
     * @return the locations.
     */
    private fun getLocations(`object`: Scenery): List<Location> {
        val locs = ArrayList<Location>(20)
        if (`object`.direction == Direction.NORTH) {
            locs.add(`object`.location.transform(1, -1, 0))
            locs.add(`object`.location.transform(1, 1, 0))
        } else if (`object`.direction == Direction.SOUTH) {
            locs.add(`object`.location.transform(0, 1, 0))
            locs.add(`object`.location.transform(0, -1, 0))
        } else if (`object`.direction == Direction.WEST) {
            locs.add(`object`.location.transform(1, 1, 0))
            locs.add(`object`.location.transform(-1, 1, 0))
        } else {
            locs.add(`object`.location.transform(-1, 0, 0))
            locs.add(`object`.location.transform(1, 0, 0))
        }
        return locs
    }

    /**
     * Gets the log the player has.
     * @param player the player.
     * @return the log.
     */
    private fun getLog(player: Player): Log? {
        for (log in Log.values()) {
            if (player.inventory.contains(log.logId, 1)) {
                return log
            }
        }
        return null
    }
}
