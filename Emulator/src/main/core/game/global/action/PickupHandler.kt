package core.game.global.action

import org.rs.consts.Items
import org.rs.consts.NPCs
import org.rs.consts.Sounds
import content.data.GodType
import content.global.skill.runecrafting.pouch.RunePouch
import core.api.*
import core.game.dialogue.FacialExpression
import core.game.event.PickUpEvent
import core.game.node.entity.player.Player
import core.game.node.entity.player.info.LogType
import core.game.node.entity.player.info.PlayerMonitor
import core.game.node.item.GroundItem
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.system.config.GroundSpawnLoader
import core.game.world.GameWorld
import core.game.world.map.RegionManager
import core.game.world.update.flag.context.Animation

/**
 * A class used to handle the picking up of ground items.
 * @author Vexia
 */
object PickupHandler {
    /**
     * Method used to take a ground item.
     * @param player the player.
     * @param item the item.
     * @return `True` if taken.
     */
    @JvmStatic
    fun take(player: Player, item: GroundItem): Boolean {
        // Check if the item's location is null
        if (item.location == null) {
            sendMessage(player, "Invalid ground item!")
            return true
        }
        // Check if the item is no longer available
        if (!GroundItemManager.getItems().contains(item)) {
            sendMessage(player, "Too late - it's gone!")
            return true
        }
        // Check if the item has a time restriction
        if (getAttribute(player,"droppedItem:" + item.id, 0) > GameWorld.ticks) { //Splinter
            return true
        }
        // Check if the item is private and not dropped by the player
        if (item !is GroundSpawnLoader.GroundSpawn && item.isRemainPrivate && !item.droppedBy(player)) {
            sendMessage(player, "You can't take that item!")
            return true
        }
        // Create a new item to add to the player's inventory
        val add = Item(item.id, item.amount, item.charge)
        // Check if the player has enough inventory space
        if (!hasSpaceFor(player, add)) {
            sendMessage(player, "You don't have enough inventory space to hold that item.")
            return true
        }
        // Check if the player can take the item
        if (!canTake(player, item, 0)) {
            return true
        }
        // Add the item to the player's inventory and handle any additional actions
        if (item.isActive && player.inventory.add(add)) {
            // Log the item pickup if it was dropped by another player
            if (item.dropper is Player && item.dropper!!.details.uid != player.details.uid) {
                PlayerMonitor.log(item.dropper!!, LogType.DROP_TRADE, "${getItemName(item.id)} x${item.amount} picked up by ${player.name}.")
            }
            // Play an animation if the item's location does not permit teleportation
            if (!RegionManager.isTeleportPermitted(item.location)) {
                player.animate(Animation.create(535))
            }
            // Destroy the ground item
            GroundItemManager.destroy(item)
            /*
            if (item.dropper?.isArtificial == true) {
                getItems(item.dropper)?.remove(item)
            }
            */
            // Play a pickup sound
            playAudio(player, Sounds.PICK2_2582)
            // Dispatch a pickup event for the item
            player.dispatch(PickUpEvent(item.id))
        }
        return true
    }

    /**
     * Checks if the player can take an item.
     * @param player the player.
     * @param item the item.
     * @param type the type (1= ground, 2=telegrab)
     * @return `True` if so.
     */
    @JvmStatic
    fun canTake(player: Player, item: GroundItem, type: Int): Boolean {
        // Check if the item was not dropped by the player and the player has an ironman restriction
        if (item.dropper != null && !item.droppedBy(player) && player.ironmanManager.checkRestriction()) {
            return false
        }
        // Check if the item is a guild property and cannot be taken
        if (item.id == Items.EIGHTEEN_LB_SHOT_8858 || item.id == Items.TWENTY_TWO_LB_SHOT_8859) {
            sendNPCDialogue(player, NPCs.REF_4300, "Hey! You can't take that, it's guild property. Take one from the pile.", FacialExpression.FURIOUS)
            return false
        }

        // Check if the player already has a sacred cape and cannot possess another one
        if (GodType.forCape(item) != null) {
            if (GodType.hasAny(player)) {
                sendDialogueLines(player, "You may only possess one sacred cape at a time.", "The conflicting powers of the capes drive them apart.")
                return false
            }
        }

        // Check if the player already has a rune pouch and cannot pick up another one
        if (RunePouch.forItem(item) != null) {
            if (player.hasItem(item)) {
                sendDialogueLines(player,"A mystical force prevents you from picking up the pouch.")
                return false
            }
        }

        // Check if the item has a plugin and call its canPickUp method
        return if (item.hasItemPlugin()) {
            item.plugin!!.canPickUp(player, item, type)
        } else true
    }
}
