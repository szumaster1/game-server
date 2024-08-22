package content.region.misthalin.handlers.stronghold.playersafety

import core.api.*
import cfg.consts.Components
import cfg.consts.Items
import cfg.consts.Music
import core.game.activity.Cutscene
import core.game.component.Component
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.InterfaceListener
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.emote.Emotes
import core.game.node.scenery.Scenery
import core.game.world.map.Location

/**
 * Represents the player safety area interaction listener.
 */
class PlayerSafetyListener : InteractionListener {

    companion object {
        /*
         * DTI_MAP maps specific Door IDs to their corresponding interface IDs.
         */
        private val DTI_MAP = mapOf(
            29595 to 701,
            29596 to 703,
            29597 to 711,
            29598 to 695,
            29599 to 312,
            29600 to 706,
            29601 to 698
        )
        private const val ATTRIBUTE_CLIMB_CREVICE = "player_strong:crevice_climbed"
    }

    override fun defineListeners() {

        /*
         * Listener for taking the exam using the test paper item.
         */

        on(Items.TEST_PAPER_12626, IntType.ITEM, "take exam") { player, _ ->
            if (player.savedData.globalData.getTestStage() == 2) {
                sendDialogue(player, "You have already completed the test. Hand it in to Professor Henry for marking.")
            } else {
                // Open the exam interface for the player.
                openInterface(player, Components.PLAYER_SAFETY_EXAM_697)
            }
            return@on true
        }

        /*
         * Listener for talking to students (NPCs).
         */

        on((7151..7157).toIntArray(), IntType.NPC, "Talk-to") { player, _ ->
            sendDialogue(player, "This student is trying to focus on their work.")
            return@on true
        }

        /*
         * Using the jail teleport scenery.
         */

        on(29603, IntType.SCENERY, "use") { player, _ ->
            teleport(player, Location.create(3082, 4229, 0))
            if (!player.musicPlayer.hasUnlocked(Music.INCARCERATION_494)) {
                player.musicPlayer.unlock(Music.INCARCERATION_494)
            }
            return@on true
        }

        /*
         * Leaving the jail scenery.
         */

        on(29602, IntType.SCENERY, "leave") { player, _ ->
            teleport(player, Location.create(3074, 3456, 0))
            return@on true
        }

        /*
         * Climbing up the jail scenery.
         */

        on(29589, IntType.SCENERY, "climb-up") { player, _ ->
            if (player.globalData.hasReadPlaques()) {
                if (!player.musicPlayer.hasUnlocked(Music.EXAM_CONDITIONS_492)) {
                    player.musicPlayer.unlock(Music.EXAM_CONDITIONS_492)
                }
                teleport(player, Location.create(3083, 3452, 0))
            } else {
                sendMessage(player, "You need to read the jail plaques before the guard will allow you upstairs.")
            }
            return@on true
        }

        /*
         * Opening the exam room door.
         */

        on(29732, IntType.SCENERY, "open") { player, node ->
            if (player.globalData.getTestStage() > 0) {
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            } else {
                sendMessage(player, "The door is locked.")
            }
            return@on true
        }

        /*
         * Climbing down the jail scenery.
         */

        on(29592, IntType.SCENERY, "climb-down") { player, _ ->
            teleport(player, Location.create(3086, 4247, 0))
            return@on true
        }

        /*
         * Entering the crevice scenery.
         */

        on(29728, IntType.SCENERY, "enter") { player, _ ->
            if (getAttribute(player, ATTRIBUTE_CLIMB_CREVICE, false)) {
                teleport(player, Location.create(3159, 4279, 3))
            } else {
                sendMessage(player, "There's no way down.")
            }
            return@on true
        }

        /*
         * Climbing the crevice scenery.
         */

        on(29729, IntType.SCENERY, "climb") { player, _ ->
            if (!getAttribute(player, ATTRIBUTE_CLIMB_CREVICE, false)) {
                // Set attribute indicating the player has climbed the crevice.
                setAttribute(player, ATTRIBUTE_CLIMB_CREVICE, true)
            }
            // Teleport player to the specified location when climbing.
            teleport(player, Location.create(3077, 3462, 0))
            return@on true
        }

        /*
         * Reading plaques interaction.
         */

        on((29595..29601).toIntArray(), IntType.SCENERY, "Read-plaque on") { player, node ->
            player.lock() // Prevent client crash during reading.
            read(player, node) // Call the read function to handle plaque reading.
            return@on true
        }

        /*
         * Listener for using a specific scenery.
         */

        on(29623, IntType.SCENERY, "use") { player, _ ->
            // Teleport player to the specified location.
            teleport(player, Location.create(3077, 4235, 0))
            return@on true
        }

        /*
         * Listener for pulling a specific scenery.
         */

        on(29730, IntType.SCENERY, "pull") { player, _ ->
            sendMessage(player, "You hear the cogs and gears moving and a distant unlocking sound.")
            setVarp(player, 1203, (1 shl 29) or (1 shl 26), true)
            return@on true
        }

        /*
         * Listener for pulling another scenery.
         */

        on(29731, IntType.SCENERY, "pull") { player, _ ->
            sendMessage(player, "You hear cogs and gears moving and the sound of heavy locks falling into place.")
            setVarp(player, 1203, 1 shl 29, true)
            return@on true
        }

        /*
         * Listener for opening jail doors in different locations.
         */

        on(29624, IntType.SCENERY, "open") { player, _ ->
            if (getVarp(player, 1203) and (1 shl 26) == 0) {
                sendMessage(player, "The door seems to be locked by some kind of mechanism.")
                return@on true
            }
            // Handle teleportation based on player's current location.
            if (player.location.z == 2) {
                // Teleport from floor 2 to hidden tunnel.
                teleport(player, Location.create(3177, 4266, 0))
            } else if (player.location.z == 1) {
                // Teleport from floor 1 to hidden tunnel.
                teleport(player, Location.create(3143, 4270, 0))
            } else {
                // Handle exiting the hidden tunnel based on player's x-coordinate.
                if (player.location.x < 3150) {
                    // Teleport to floor 1 from the west exit.
                    teleport(player, Location.create(3142, 4272, 1))
                } else {
                    // Teleport to floor 2 from the east exit.
                    teleport(player, Location.create(3177, 4269, 2))
                }
            }
            return@on true
        }

        /*
         * Climbing down the stairs between the 1st and 2nd floors.
         */

        on(29667, IntType.SCENERY, "climb-down") { player, _ ->
            teleport(player, Location.create(3160, 4249, 1))
            return@on true
        }

        /*
         * Climbing up the stairs between the 1st and 2nd floors.
         */

        on(29668, IntType.SCENERY, "climb-up") { player, _ ->
            teleport(player, Location.create(3158, 4250, 2))
            return@on true
        }

        /*
         * Climbing down another set of stairs.
         */

        on(29663, IntType.SCENERY, "climb-down") { player, _ ->
            teleport(player, Location.create(3160, 4246, 1))
            return@on true
        }

        /*
         * Climbing up another set of stairs.
         */

        on(29664, IntType.SCENERY, "climb-up") { player, _ ->
            teleport(player, Location.create(3158, 4245, 2))
            return@on true
        }

        /*
         * Climbing down yet another set of stairs.
         */

        on(29655, IntType.SCENERY, "climb-down") { player, _ ->
            teleport(player, Location.create(3146, 4246, 1))
            return@on true
        }

        /*
         * Climbing up yet another set of stairs.
         */

        on(29656, IntType.SCENERY, "climb-up") { player, _ ->
            teleport(player, Location.create(3149, 4244, 2))
            return@on true
        }

        /*
         * Climbing down another set of stairs.
         */

        on(29659, IntType.SCENERY, "climb-down") { player, _ ->
            teleport(player, Location.create(3146, 4249, 1))
            return@on true
        }

        /*
         * Climbing up another set of stairs.
         */

        on(29660, IntType.SCENERY, "climb-up") { player, _ ->
            teleport(player, Location.create(3148, 4250, 2))
            return@on true
        }

        /*
         * Opening a specific scenery.
         */

        on(29577, IntType.SCENERY, "open") { player, _ ->
            setVarbit(player, 4499, 1, true)
            return@on true
        }

        /*
         * Searching a specific scenery.
         */

        on(29578, IntType.SCENERY, "search") { player, _ ->
            if (player.globalData.getTestStage() == 3) {
                if ((freeSlots(player) == 0) or ((freeSlots(player) == 1) and !inInventory(player, Items.COINS_995))) {
                    sendDialogue(player, "You do not have enough inventory space!")
                } else {
                    player.emoteManager.unlock(Emotes.SAFETY_FIRST)
                    addItem(player, Items.COINS_995, 10000)
                    addItem(player, Items.SAFETY_GLOVES_12629)
                    sendItemDialogue(player, Items.SAFETY_GLOVES_12629, "You open the chest to find a large pile of gold, along with a pair of safety gloves. ")
                    player.globalData.setTestStage(4) // Update the test stage for the player.
                }
            } else {
                if (hasAnItem(player, Items.SAFETY_GLOVES_12629).exists()) {
                    sendDialogue(player, "The chest is empty")
                } else {
                    if (freeSlots(player) == 0) {
                        sendDialogue(player, "You do not have enough inventory space!")
                    } else {
                        sendItemDialogue(player, Items.SAFETY_GLOVES_12629, "You open the chest to find a pair of safety gloves. ")
                        addItem(player, Items.SAFETY_GLOVES_12629)
                    }
                }
            }
            return@on true
        }
    }

    /**
     * Reads the plaque for the player and opens the corresponding interface.
     *
     * @param player The player interacting with the plaque.
     * @param plaque The plaque node being read.
     */
    fun read(player: Player, plaque: Node) {
        if (plaque !is Scenery) return
        player.interfaceManager.openChatbox(DTI_MAP[plaque.id]!!)
    }

    /**
     * This class implements the InterfaceListener for handling plaque interactions.
     */
    class PlaqueInterfaceListener : InterfaceListener {

        var scene: PlaqueCutscene? = null

        override fun defineInterfaceListeners() {
            for ((index, iface) in DTI_MAP.values.withIndex()) {
                onClose(iface) { player, _ ->
                    scene?.endWithoutFade()
                    player.globalData.readPlaques[index] = true
                    return@onClose true
                }

                onOpen(iface) { player, component ->
                    scene = PlaqueCutscene(player, component)
                    scene?.start(false)
                    return@onOpen true
                }


                on(iface) { player, _, _, buttonID, _, _ ->
                    if (buttonID == 2) {
                        player.unlock()
                        scene?.incrementStage()
                        player.interfaceManager.closeChatbox()
                    }
                    return@on true
                }
            }
        }

        /**
         * This class represents a cutscene related to a plaque interaction.
         *
         * @param component The component associated with the cutscene.
         * @param player The player interacting with the plaque.
         * @constructor Initializes the cutscene with the player and component.
         */
        class PlaqueCutscene(player: Player, val component: Component) : Cutscene(player) {

            /*
             * Maps component IDs to their corresponding door location offsets.
             * This is necessary because the component does not know the door's location.
             */
            private val rotationMapping = mapOf(
                Components.JAIL_PLAQUE_701 to listOf(-1, 0),
                Components.JAIL_PLAQUE_703 to listOf(-1, 0),
                Components.JAIL_PLAQUE_711 to listOf(-1, 0),
                Components.JAIL_PLAQUE_695 to listOf(0, 1),
                Components.SAFETY_JAIL_312 to listOf(1, 0),
                Components.JAIL_PLAQUE_706 to listOf(1, 0),
                Components.JAIL_PLAQUE_698 to listOf(1, 0),
            )

            override fun setup() {
                setExit(player.location)
            }

            override fun runStage(stage: Int) {
                when (stage) {
                    0 -> {
                        moveCamera(
                            regionX = player.location.localX,
                            regionY = player.location.localY,
                            height = 200,
                            speed = 15
                        )
                        rotateCamera(
                            regionX = player.location.localX + rotationMapping[component.id]!![0],
                            regionY = player.location.localY + rotationMapping[component.id]!![1],
                            height = 200,
                            speed = 30
                        )
                    }
                    1 -> resetCamera()
                }
            }
        }
    }
}