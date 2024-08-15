package content.region.misthalin.quest.free.vampireslayer

import core.api.consts.Sounds
import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.world.map.Location

/**
 * Vampire slayer listener.
 */
class VampireSlayerListener : InteractionListener {

    // Define constants for various interactive objects
    private val cupBoard = 33502
    private val openedCupBoard = 33503
    private val coffin = 2614
    private val openedCoffin = 2615
    private val stairsUp = 32835
    private val stairsBasement = 32836
    // Define locations for basement and ground floor
    private val basement = Location.create(3077, 9770, 0)
    private val groundFloor = Location.create(3115, 3356, 0)

    // Define item IDs for garlic, stake, and hammer
    val garlic = 1550
    val stake = 1549
    val hammer = 2347

    // Method to define interaction listeners
    override fun defineListeners() {
        // Listener for opening the cupboard
        on(cupBoard, IntType.SCENERY, "open") { player, node ->
            animate(player, 542) // Animate the player opening the cupboard
            playAudio(player, Sounds.CUPBOARD_OPEN_58) // Play sound for cupboard opening
            sendMessage(player, "You open the cupboard.") // Notify player
            replaceScenery(node.asScenery(), openedCupBoard, -1) // Change scenery to opened cupboard
            return@on true // Indicate successful interaction
        }

        // Listener for closing the cupboard
        on(openedCupBoard, IntType.SCENERY, "close") { player, node ->
            animate(player, 543) // Animate the player closing the cupboard
            playAudio(player, Sounds.CUPBOARD_CLOSE_57) // Play sound for cupboard closing
            sendMessage(player, "You close the cupboard.") // Notify player
            replaceScenery(node.asScenery(), cupBoard, -1) // Change scenery back to closed cupboard
            return@on true // Indicate successful interaction
        }

        // Listener for searching the opened cupboard
        on(openedCupBoard, IntType.SCENERY, "search") { player, _ ->
            if (!inInventory(player, garlic)) { // Check if player does not have garlic
                sendMessage(player, "The cupboard contains garlic. You take a clove.") // Notify player about garlic
                addItem(player, garlic, 1) // Add garlic to player's inventory
            } else if (freeSlots(player) == 0) { // Check if inventory is full
                sendMessage(player, "Not enough inventory space.") // Notify player about lack of space
            } else {
                sendMessage(player, "You search the cupboard but find nothing.") // Notify player of no findings
            }
            return@on true // Indicate successful interaction
        }

        // Listener for opening the coffin
        on(coffin, IntType.SCENERY, "open") { player, node ->
            animate(player, 832) // Animate the player opening the coffin
            playAudio(player, Sounds.COFFIN_OPEN_54) // Play sound for coffin opening
            replaceScenery(node.asScenery(), openedCoffin, -1) // Change scenery to opened coffin
            return@on true // Indicate successful interaction
        }

        // Listener for closing the opened coffin
        on(openedCoffin, IntType.SCENERY, "close") { player, node ->
            animate(player, 832) // Animate the player closing the coffin
            playAudio(player, Sounds.COFFIN_CLOSE_53) // Play sound for coffin closing
            replaceScenery(node.asScenery(), coffin, -1) // Change scenery back to closed coffin
            return@on true // Indicate successful interaction
        }

        // Listener for searching the opened coffin
        on(openedCoffin, IntType.SCENERY, "search") { player, _ ->
            if (isQuestComplete(player, "Vampire Slayer")) { // Check if the quest is complete
                sendDialogue(player, "There's only a pillow in here..") // Notify player about the pillow
            } else if (getQuestStage(player, "Vampire Slayer") == 30) { // Check if the player is at a specific quest stage
                animate(player, 832) // Animate the player searching the coffin
                if (!inInventory(player, stake) && !inInventory(player, hammer)) { // Check if player lacks necessary items
                    sendMessage(player, "You must have a stake and hammer with you to kill the vampire.") // Notify player
                    return@on true // Indicate successful interaction
                }
                val o = player.getAttribute<NPC>("count", null) // Retrieve the NPC attribute
                if (o == null || !o.isActive) { // Check if the NPC is not active
                    runTask(player, 1) { // Schedule a task to spawn the vampire
                        val vampire = core.game.node.entity.npc.NPC.create(757, player.location) // Create vampire NPC
                        vampire.isRespawn = false // Set vampire to not respawn
                        vampire.setAttribute("player", player) // Set player attribute for the vampire
                        vampire.init() // Initialize the vampire
                        vampire.properties.combatPulse.attack(player) // Attack the player
                        setAttribute(player, "count", vampire) // Set the vampire as an attribute of the player
                    }
                }
            }
            return@on true // Indicate successful interaction
        }

        // Listener for walking down the stairs
        on(stairsUp, IntType.SCENERY, "walk-down") { player, _ ->
            player.properties.teleportLocation = basement // Set player's teleport location to basement
            sendMessage(player, "You walk down the stairs...") // Notify player about walking down
            return@on true // Indicate successful interaction
        }

        // Listener for walking up the stairs
        on(stairsBasement, IntType.SCENERY, "walk-up") { player, _ ->
            player.properties.teleportLocation = groundFloor // Set player's teleport location to ground floor
            return@on true // Indicate successful interaction
        }
    }
}