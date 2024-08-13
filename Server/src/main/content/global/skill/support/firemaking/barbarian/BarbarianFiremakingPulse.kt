package content.global.skill.support.firemaking.barbarian

import content.data.skill.SkillingTool
import content.global.skill.BarbarianTraining
import content.global.skill.support.firemaking.data.Log
import core.api.*
import core.api.consts.Items
import core.game.event.LitFireEvent
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.GroundItem
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.world.GameWorld
import core.tools.RandomFunction
import kotlin.math.ceil

/**
 * Barbarian firemaking pulse.
 * @param node The item node representing the log.
 * @param groundItem The ground item associated with the log, if any.
 */
class BarbarianFiremakingPulse(player: Player, node: Item, groundItem: GroundItem?) : SkillPulse<Item?>(player, node) {

    private val fire = Log.forId(node.id) // Retrieve the log data based on the node ID.
    private var groundItem: GroundItem? = null // Initialize groundItem to hold the ground item.
    private var ticks = 0 // Initialize ticks to track the number of attempts.

    init {
        // Check if groundItem is null and create a new GroundItem if it is.
        if (groundItem == null) {
            this.groundItem = GroundItem(node, player.location, player) // Create a new GroundItem at the player's location.
            setAttribute(player, "remove-log", true) // Set an attribute to indicate the log should be removed.
        } else {
            this.groundItem = groundItem // Assign the provided groundItem.
            removeAttribute(player, "remove-log") // Remove the attribute indicating log removal.
        }
    }

    override fun checkRequirements(): Boolean {
        if (fire == null) { // Check if the fire log is valid.
            return false // Return false if the log is invalid.
        }
        // Check if the player is an Ironman and if the ground item was not dropped by them.
        if (player.ironmanManager.isIronman && !groundItem!!.droppedBy(player)) {
            sendMessage(player, "You can't do that as an Ironman.") // Inform the player they cannot perform the action.
            return false // Return false to indicate requirements are not met.
        }
        // Check if the player is in a location where fire cannot be lit.
        if (getScenery(player.location) != null || player.zoneMonitor.isInZone("bank")) {
            sendMessage(player, "You can't light a fire here.") // Inform the player they cannot light a fire here.
            return false // Return false to indicate requirements are not met.
        }
        // Check if the player has the required firemaking tool in their inventory.
        if (!anyInInventory(player, SkillingTool.getFiremakingTool(player)!!.id)) {
            sendMessage(player, "You do not have the required items to light this.") // Inform the player of missing items.
            return false // Return false to indicate requirements are not met.
        }
        // Check if the player's firemaking level is sufficient to light the log.
        if (getStatLevel(player, Skills.FIREMAKING) < fire.barbarianLevel) {
            sendMessage(player, "You need a Firemaking level of " + fire.barbarianLevel + " to light this log.") // Inform the player of level requirement.
            return false // Return false to indicate requirements are not met.
        }
        // Check if the log should be removed from the player's inventory.
        if (getAttribute(player, "remove-log", false)) {
            removeAttribute(player, "remove-log") // Remove the log removal attribute.
            if (inInventory(player, node!!.id, 1)) { // Check if the player has the log in their inventory.
                replaceSlot(player, node!!.slot, Item(node!!.id, (node!!.amount - 1)), node, Container.INVENTORY) // Replace the log in the inventory.
                GroundItemManager.create(groundItem) // Create the ground item.
            }
        }
        return true // Return true if all requirements are met.
    }

    override fun animate() {
        // Animation logic can be implemented here.
    }

    override fun reward(): Boolean {
        if (lastFire >= GameWorld.ticks) { // Check if the last fire was lit recently.
            createFire() // Create a fire if the last fire was not too recent.
            return true // Return true to indicate a reward was given.
        }
        if (ticks == 0) { // Check if this is the first tick.
            animate(player, SkillingTool.getFiremakingTool(player)!!.animation) // Animate the firemaking action.
        }
        if (++ticks % 3 != 0) { // Increment ticks and check if it's not a multiple of 3.
            return false // Return false to indicate no reward this tick.
        }
        if (ticks % 12 == 0) { // Check if ticks is a multiple of 12.
            animate(player, SkillingTool.getFiremakingTool(player)!!.animation) // Animate again for every 12 ticks.
        }
        if (!success()) { // Check if the firemaking attempt was successful.
            return false // Return false if the attempt failed.
        }
        createFire() // Create the fire if the attempt was successful.
        return true // Return true to indicate a reward was given.
    }

    /**
     * Create fire
     *
     */
    fun createFire() {
        if (!groundItem!!.isActive) { // Check if the ground item is still active.
            return // Exit if the ground item is not active.
        }
        val scenery = Scenery(fire!!.fireId, player.location) // Create a new scenery object for the fire.
        SceneryBuilder.add(scenery, fire.life, getAsh(player, fire, scenery)) // Add the fire to the scenery.
        GroundItemManager.destroy(groundItem) // Destroy the ground item after creating the fire.
        player.moveStep() // Move the player slightly after lighting the fire.
        face(player, scenery.getFaceLocation(player.location)) // Face the direction of the fire.
        rewardXP(player, Skills.FIREMAKING, fire.xp) // Reward experience points for firemaking.

        val playerRegion = player.viewport.region.id // Get the player's current region ID.

        setLastFire() // Set the last fire timestamp.
        player.dispatch(LitFireEvent(fire.logId)) // Dispatch an event for lighting the fire.

        // Check if the player is in the firemaking tutorial.
        if (getAttribute(player, BarbarianTraining.BARBARIAN_FIREMAKING_TUTORIAL, false)) {
            removeAttribute(player, BarbarianTraining.BARBARIAN_FIREMAKING_TUTORIAL) // Remove the tutorial attribute.
            setAttribute(player, "/save:${BarbarianTraining.BARBARIAN_FIREMAKING_COMPLETE}", true) // Mark the tutorial as complete.
            sendDialogueLines(player, "You feel you have learned more of barbarian ways. Otto might wish", "to talk to you more.") // Inform the player of their progress.
        }
    }

    override fun message(type: Int) {
        val name = if (node!!.id == Items.JOGRE_BONES_3125) "bones" else "logs" // Determine the name based on the item type.
        when (type) {
            0 -> sendMessage(player, "You attempt to light the $name.") // Inform the player of their attempt.
            1 -> sendMessage(player, "The fire catches and the $name begin to burn.") // Inform the player of success.
        }
    }

    val lastFire: Int get() = getAttribute(player, "last-firemake", 0) // Get the last fire timestamp.

    /**
     * Set last fire
     *
     */
    fun setLastFire() {
        setAttribute(player, "last-firemake", GameWorld.ticks + 2) // Set the last fire timestamp to the current ticks plus 2.
    }

    private fun success(): Boolean {
        val level = 1 + player.getSkills().getLevel(Skills.FIREMAKING) // Calculate the player's effective firemaking level.
        val req = fire!!.barbarianLevel.toDouble() // Get the required level to light the fire.
        val successChance = ceil((level * 50 - req * 15) / req / 3 * 4) // Calculate the success chance based on levels.
        val roll = RandomFunction.random(99) // Roll a random number to determine success.
        return successChance >= roll // Return true if the success chance is greater than or equal to the roll.
    }

    companion object {
        @JvmStatic
        fun getAsh(player: Player?, fire: Log?, scenery: Scenery): GroundItem {
            val ash = GroundItem(Item(Items.ASHES_592), scenery.location, player) // Create a ground item for ashes.
            ash.decayTime = fire!!.life + 200 // Set the decay time for the ashes.
            return ash // Return the created ash ground item.
        }
    }
}
