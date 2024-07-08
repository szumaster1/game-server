package core.game.global.action

import core.game.event.PickUpEvent
import content.data.GodType
import core.game.node.entity.player.Player
import content.global.skill.production.runecrafting.item.pouch.RunePouch
import core.api.*
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.info.LogType
import core.game.node.item.GroundItem
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.world.map.RegionManager
import core.game.world.update.flag.context.Animation
import core.game.node.entity.player.info.PlayerMonitor
import core.game.system.config.GroundSpawnLoader
import core.game.world.GameWorld
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Sounds

object PickupHandler {

    @JvmStatic
    fun take(player: Player, item: GroundItem): Boolean {
        if (item.location == null) {
            sendMessage(player, "Invalid ground item!")
            return true
        }
        if (!GroundItemManager.getItems().contains(item)) {
            sendMessage(player, "Too late!")
            return true
        }
        if (getAttribute(player,"droppedItem:" + item.id, 0) > GameWorld.ticks) { //Splinter
            return true
        }
        if (item !is GroundSpawnLoader.GroundSpawn && item.isRemainPrivate && !item.droppedBy(player)) {
            sendMessage(player, "You can't take that item!")
            return true
        }
        val add = Item(item.id, item.amount, item.charge)
        if (!hasSpaceFor(player, add)) {
            sendMessage(player, "You don't have enough inventory space to hold that item.")
            return true
        }
        if (!canTake(player, item, 0)) {
            return true
        }
        if (item.isActive && player.inventory.add(add)) {
            if (item.dropper is Player && item.dropper.details.uid != player.details.uid) {
                PlayerMonitor.log(item.dropper, LogType.DROP_TRADE, "${getItemName(item.id)} x${item.amount} picked up by ${player.name}.")
            }
            if (!RegionManager.isTeleportPermitted(item.location)) {
                player.animate(Animation.create(535))
            }
            GroundItemManager.destroy(item)
            /*
            if (item.dropper?.isArtificial == true) {
                getItems(item.dropper)?.remove(item)
            }
            */
            playAudio(player, Sounds.PICK2_2582)
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
        if (item.dropper != null && !item.droppedBy(player) && player.ironmanManager.checkRestriction()) {
            return false
        }
        if (item.id == Items.EIGHTEEN_LB_SHOT_8858 || item.id == Items.TWENTY_TWO_LB_SHOT_8859) {
            sendNPCDialogue(player, NPCs.REF_4300, "Hey! You can't take that, it's guild property. Take one from the pile.", FacialExpression.FURIOUS)
            return false
        }

        if (GodType.forCape(item) != null) {
            if (GodType.hasAny(player)) {
                sendDialogueLines(player, "You may only possess one sacred cape at a time.", "The conflicting powers of the capes drive them apart.")
                return false
            }
        }

        if (RunePouch.forItem(item) != null) {
            if (player.hasItem(item)) {
                sendDialogueLines(player,"A mystical force prevents you from picking up the pouch.")
                return false
            }
        }

        return if (item.hasItemPlugin()) {
            item.plugin.canPickUp(player, item, type)
        } else true
    }
}
