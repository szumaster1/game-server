package content.region.asgarnia.quest.rd

import cfg.consts.Animations
import cfg.consts.Items
import cfg.consts.Scenery
import cfg.consts.Sounds
import core.api.*
import core.game.interaction.QueueStrength
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

object RDUtils {

    fun getLocationForScenery(node: Node): Location {
        return when (node.asScenery().id) {
            Scenery.CRATE_7347 -> Location(2476, 4943)
            Scenery.CRATE_7348 -> Location(2476, 4937)
            Scenery.CRATE_7349 -> Location(2475, 4943)
            else -> Location(0, 0)
        }
    }

    fun processItemUsage(player: Player, used: Item, with: Item, newItem: Item) {
        replaceSlot(player, slot = used.index, Item(newItem.id))
        replaceSlot(player, slot = with.index, Item(Items.VIAL_229))
        animate(player, Animation(Animations.HUMAN_USE_PESTLE_AND_MORTAR_364))
        playAudio(player, Sounds.VIALPOUR_2613)
        sendMessage(player, "You empty the vial into the tin.")
    }

    fun handleVialUsage(player: Player, used: Item) {
        lock(player, 5)
        lockInteractions(player, 5)

        if (removeItem(player, used.id)) {
            animate(player, Animation(2259))
            playAudio(player, Sounds.VIALPOUR_2613)
            setAttribute(player, MissCheeversRoomListeners.Companion.DoorVials.doorVialsMap[used.id]!!.attribute, true)
            sendMessage(player, "You pour the vial onto the flat part of the spade.")
            addItem(player, Items.VIAL_229)
        }

        if (MissCheeversRoomListeners.Companion.DoorVials.doorVialsRequiredMap.all { getAttribute(player, it.value.attribute, false) }) {
            animate(player, Animation(2259))
            playAudio(player, Sounds.VIALPOUR_2613)
            sendMessage(player, "Something caused a reaction when mixed!")
            sendMessage(player, "The spade gets hotter, and expands slightly.")
            setVarbit(player, MissCheeversRoomListeners.DOOR_VARBIT, 2)
        }
    }

    fun handleSpadePull(player: Player) {
        lock(player, 3)
        lockInteractions(player, 3)

        if (MissCheeversRoomListeners.Companion.DoorVials.doorVialsRequiredMap.all { getAttribute(player, it.value.attribute, false) }) {
            sendMessage(player, "You pull on the spade...")
            sendMessage(player, "It works as a handle, and you swing the stone door open.")
            setVarbit(player, MissCheeversRoomListeners.DOOR_VARBIT, 3)
        } else {
            sendMessage(player, "You pull on the spade...")
            sendMessage(player, "It comes loose, and slides out of the hole in the stone.")
            addItemOrDrop(player, Items.METAL_SPADE_5587)
            setVarbit(player, MissCheeversRoomListeners.DOOR_VARBIT, 0)
        }
    }

    fun handleDoorWalkThrough(player: Player) {
        when {
            inBorders(player, 2476, 4941, 2477, 4939) ->
                forceMove(player, player.location, Location(2478, 4940, 0), 20, 80)
            inBorders(player, 2477, 4941, 2478, 4939) ->
                forceMove(player, player.location, Location(2476, 4940, 0), 20, 80)
        }
    }

    fun searchingHelper(player: Player, attributeCheck: String, item: Int, searchingDescription: String, objectDescription: String) {
        sendMessage(player, searchingDescription)
        queueScript(player, 1, QueueStrength.WEAK) {
            if (attributeCheck != "" && !getAttribute(player, attributeCheck, false)) {
                setAttribute(player, attributeCheck, true)
                addItem(player, item)
                sendMessage(player, objectDescription)
            } else {
                sendMessage(player, "You don't find anything interesting.")
            }
            return@queueScript stopExecuting(player)
        }
    }
}