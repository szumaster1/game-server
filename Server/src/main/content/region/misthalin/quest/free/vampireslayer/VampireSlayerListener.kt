package content.region.misthalin.quest.free.vampireslayer

import cfg.consts.Sounds
import core.api.*
import cfg.consts.Animations
import cfg.consts.NPCs
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.world.map.Location

/**
 * Represents the Vampire slayer listener.
 */
class VampireSlayerListener : InteractionListener {

    private val cupBoard = 33502
    private val openedCupBoard = 33503
    private val coffin = 2614
    private val openedCoffin = 2615
    private val stairsUp = 32835
    private val stairsBasement = 32836
    private val basement = Location.create(3077, 9770, 0)
    private val groundFloor = Location.create(3115, 3356, 0)

    val garlic = 1550
    val stake = 1549
    val hammer = 2347

    override fun defineListeners() {

        /*
         * Listener for opening the cupboard.
         */

        on(cupBoard, IntType.SCENERY, "open") { player, node ->
            animate(player, Animations.OPEN_WARDROBE_542)
            playAudio(player, Sounds.CUPBOARD_OPEN_58)
            sendMessage(player, "You open the cupboard.")
            replaceScenery(node.asScenery(), openedCupBoard, -1)
            return@on true
        }

        /*
         * Listener for closing the cupboard.
         */

        on(openedCupBoard, IntType.SCENERY, "close") { player, node ->
            animate(player, Animations.CLOSE_CUPBOARD_543)
            playAudio(player, Sounds.CUPBOARD_CLOSE_57)
            sendMessage(player, "You close the cupboard.")
            replaceScenery(node.asScenery(), cupBoard, -1)
            return@on true
        }

        /*
         * Listener for searching the opened cupboard.
         */

        on(openedCupBoard, IntType.SCENERY, "search") { player, _ ->
            if (!inInventory(player, garlic)) {
                sendMessage(player, "The cupboard contains garlic. You take a clove.")
                addItem(player, garlic, 1)
            } else if (freeSlots(player) == 0) {
                sendMessage(player, "Not enough inventory space.")
            } else {
                sendMessage(player, "You search the cupboard but find nothing.")
            }
            return@on true
        }

        /*
         * Listener for opening the coffin.
         */

        on(coffin, IntType.SCENERY, "open") { player, node ->
            animate(player, Animations.MULTI_USE_TAKE_832)
            playAudio(player, Sounds.COFFIN_OPEN_54)
            replaceScenery(node.asScenery(), openedCoffin, -1)
            return@on true
        }

        /*
         * Listener for closing the opened coffin.
         */

        on(openedCoffin, IntType.SCENERY, "close") { player, node ->
            animate(player, Animations.MULTI_USE_TAKE_832)
            playAudio(player, Sounds.COFFIN_CLOSE_53)
            replaceScenery(node.asScenery(), coffin, -1)
            return@on true
        }

        /*
         * Listener for searching the opened coffin.
         */

        on(openedCoffin, IntType.SCENERY, "search") { player, _ ->
            if (isQuestComplete(player, "Vampire Slayer")) { // Check if the quest is complete
                sendDialogue(player, "There's only a pillow in here..") // Notify player about the pillow
            } else if (getQuestStage(player, "Vampire Slayer") == 30) { // Check if the player is at a specific quest stage
                animate(player, Animations.MULTI_USE_TAKE_832) // Animate the player searching the coffin
                if (!inInventory(player, stake) && !inInventory(player, hammer)) { // Check if player lacks necessary items
                    sendMessage(player, "You must have a stake and hammer with you to kill the vampire.") // Notify player
                    return@on true // Indicate successful interaction
                }
                val o = player.getAttribute<NPC>("count", null) // Retrieve the NPC attribute
                if (o == null || !o.isActive) { // Check if the NPC is not active
                    runTask(player, 1) { // Schedule a task to spawn the vampire
                        val vampire = core.game.node.entity.npc.NPC.create(NPCs.COUNT_DRAYNOR_757, player.location) // Create vampire NPC
                        vampire.isRespawn = false // Set vampire to not respawn
                        vampire.setAttribute("player", player) // Set player attribute for the vampire
                        vampire.init() // Initialize the vampire
                        vampire.properties.combatPulse.attack(player) // Attack the player
                        setAttribute(player, "count", vampire) // Set the vampire as an attribute of the player
                    }
                }
            }
            return@on true
        }

        /*
         * Listener for walking down the stairs.
         */

        on(stairsUp, IntType.SCENERY, "walk-down") { player, _ ->
            player.properties.teleportLocation = basement
            sendMessage(player, "You walk down the stairs...")
            return@on true
        }

        /*
         * Listener for walking up the stairs.
         */

        on(stairsBasement, IntType.SCENERY, "walk-up") { player, _ ->
            player.properties.teleportLocation = groundFloor
            return@on true
        }
    }
}