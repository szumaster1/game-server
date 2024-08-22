package content.global.random.event.drilldemon

import core.api.*
import cfg.consts.Animations
import cfg.consts.Components
import cfg.consts.Items
import cfg.consts.NPCs
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.TeleportManager
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders
import core.game.world.update.flag.context.Animation

/**
 * Drill demon utils.
 */
object DrillDemonUtils {
    // Constants for saving data
    val DD_KEY_TASK = "/save:drilldemon:task"
    val DD_KEY_RETURN_LOC = "/save:original-loc"
    val DD_SIGN_VARP = 531
    val DD_SIGN_JOG = 0
    val DD_SIGN_SITUP = 1
    val DD_SIGN_PUSHUP = 2
    val DD_SIGN_JUMP = 3
    val DD_CORRECT_OFFSET = "/save:drilldemon:offset"
    val DD_CORRECT_COUNTER = "/save:drilldemon:numcorrect"
    val DD_AREA = ZoneBorders(3158, 4817, 3168, 4823)
    val DD_NPC = NPCs.SERGEANT_DAMIEN_2790

    /**
     * Teleport the player to the Drill Demon event location.
     *
     * @param player The player to teleport.
     */
    fun teleport(player: Player) {
        // Save the player's current location
        setAttribute(player, DD_KEY_RETURN_LOC, player.location)
        // Teleport the player to the Drill Demon event location
        teleport(player, Location.create(3163, 4819, 0), TeleportManager.TeleportType.NORMAL)
        // Close default interface tabs
        player.interfaceManager.closeDefaultTabs()
        // Set the visibility of specific components
        setComponentVisibility(player, Components.TOPLEVEL_548, 69, true)
        setComponentVisibility(player, Components.TOPLEVEL_FULLSCREEN_746, 12, true)
    }

    /**
     * Change the signs and assign a task to the player.
     *
     * @param player The player to assign the task to.
     */
    fun changeSignsAndAssignTask(player: Player) {
        // Reset the sign varp to 0
        setVarp(player, DD_SIGN_VARP, 0)
        // Create shuffled lists of sign tasks and offsets
        val tempList = arrayListOf(DD_SIGN_JOG, DD_SIGN_JUMP, DD_SIGN_PUSHUP, DD_SIGN_SITUP).shuffled().toMutableList()
        val tempOffsetList = arrayListOf(1335, 1336, 1337, 1338).shuffled().toMutableList()
        // Randomly select a task and offset from the lists
        val task = tempList.random()
        val taskOffset = tempOffsetList.random()

        // Save the task and offset as player attributes
        setAttribute(player, DD_KEY_TASK, task)
        setAttribute(player, DD_CORRECT_OFFSET, taskOffset)

        // Remove the selected task and offset from the lists
        tempList.remove(task)
        tempOffsetList.remove(taskOffset)

        // Set the varbits for the remaining tasks and offsets
        setVarbit(player, taskOffset, task)
        for (i in 0 until tempList.size) {
            setVarbit(player, tempOffsetList[i], tempList[i], true)
        }
    }

    /**
     * Get the varbit value for a specific id.
     *
     * @param id The id to get the varbit for.
     * @return The varbit value.
     */
    fun getVarbitForId(id: Int): Int {
        return when (id) {
            10076 -> 1335
            10077 -> 1336
            10078 -> 1337
            10079 -> 1338
            else -> 0
        }
    }

    /**
     * Get the task for a specific mat id.
     *
     * @param id     The mat id.
     * @param player The player.
     * @return The task.
     */
    fun getMatTask(id: Int, player: Player): Int {
        return getVarbit(player, getVarbitForId(id))
    }

    /**
     * Cleanup after the Drill Demon event.
     *
     * @param player The player.
     */
    fun cleanup(player: Player) {
        // Unlock teleportation for the player
        player.locks.unlockTeleport()
        // Unlock the player
        unlock(player)
        // Teleport the player back to their original location
        teleport(player, getAttribute(player, DD_KEY_RETURN_LOC, Location.create(3222, 3218, 0)), TeleportManager.TeleportType.NORMAL)
        // Remove saved attributes
        removeAttribute(player, DD_KEY_RETURN_LOC)
        removeAttribute(player, DD_KEY_TASK)
        removeAttribute(player, DD_CORRECT_OFFSET)
        removeAttribute(player, DD_CORRECT_COUNTER)
        // Open default interface tabs
        player.interfaceManager.openDefaultTabs()
        // Set the visibility of specific components
        setComponentVisibility(player, Components.TOPLEVEL_548, 69, false)
        setComponentVisibility(player, Components.TOPLEVEL_FULLSCREEN_746, 12, false)
    }

    /**
     * Get the animation for a specific task.
     *
     * @param task The task.
     * @return The animation.
     */
    fun animationForTask(task: Int): Animation {
        return when (task) {
            DD_SIGN_SITUP -> Animation(Animations.SIT_UPS_FROM_DRILL_DEMON_EVENT_2763)
            DD_SIGN_PUSHUP -> Animation(Animations.PUSH_UPS_FROM_DRILL_DEMON_EVENT_2762)
            DD_SIGN_JUMP -> Animation(Animations.JUMPS_FROM_DRILL_DEMON_EVENT_2761)
            DD_SIGN_JOG -> Animation(Animations.RUNNING_IN_PLACE_FROM_DRILL_DEMON_EVENT_2764)
            else -> Animation(-1)
        }
    }

    /**
     * Reward the player after completing the Drill Demon event.
     *
     * @param player The player.
     */
    fun reward(player: Player) {
        queueScript(player, 2, QueueStrength.SOFT) {
            // Check if the player has the required items
            val hasHat = hasAnItem(player, Items.CAMO_HELMET_6656).container != null
            val hasShirt = hasAnItem(player, Items.CAMO_TOP_6654).container != null
            val hasPants = hasAnItem(player, Items.CAMO_BOTTOMS_6655).container != null
            // Give the player the appropriate reward
            when {
                !hasHat -> addItemOrDrop(player, Items.CAMO_HELMET_6656)
                !hasShirt -> addItemOrDrop(player, Items.CAMO_TOP_6654)
                !hasPants -> addItemOrDrop(player, Items.CAMO_BOTTOMS_6655)
                else -> addItemOrDrop(player, Items.COINS_995, 500)
            }
            return@queueScript stopExecuting(player)
        }
    }
}
