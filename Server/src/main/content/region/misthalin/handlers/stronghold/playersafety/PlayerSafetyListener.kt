package content.region.misthalin.handlers.stronghold.playersafety

import core.api.*
import core.api.consts.Items
import core.api.utils.PlayerCamera
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

class PlayerSafetyListener : InteractionListener {

    companion object {
        /*
            Door to interface
         */
        private val DTI_MAP = mapOf(
            29595 to 701,
            29596 to 703,
            29597 to 711,
            29598 to 695,
            29599 to 312,
            29600 to 706,
            29601 to 698,
        )
        private const val ATTRIBUTE_CLIMB_CREVICE = "player_strong:crevice_climbed"
    }

    override fun defineListeners() {

        /*
            Test exam item interaction.
         */

        on(Items.TEST_PAPER_12626, IntType.ITEM, "take exam") { player, _ ->
            if (player.savedData.globalData.getTestStage() == 2) {
                sendDialogue(player, "You have already completed the test. Hand it in to Professor Henry for marking.")
            } else {
                openInterface(player, 697)
            }
            return@on true
        }

        /*
            Interaction with students.
         */

        on((7151..7157).toIntArray(), IntType.NPC, "Talk-to") { player, _ ->
            sendDialogue(player, "This student is trying to focus on their work.")
            return@on true
        }

        /*
            Jail teleports interaction.
         */

        on(29603, IntType.SCENERY, "use") { player, _ ->
            teleport(player, Location.create(3082, 4229, 0))
            return@on true
        }

        on(29602, IntType.SCENERY, "leave") { player, _ ->
            teleport(player, Location.create(3074, 3456, 0))
            return@on true
        }

        on(29589, IntType.SCENERY, "climb-up") { player, _ ->
            if (player.globalData.hasReadPlaques()) {
                teleport(player, Location.create(3083, 3452, 0))
            } else {
                sendMessage(player, "You need to read the jail plaques before the guard will allow you upstairs.")
            }

            return@on true
        }

        /*
            Exam room door interaction.
         */

        on(29732, IntType.SCENERY, "open") { player, node ->
            if (player.globalData.getTestStage() > 0) {
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            } else {
                sendMessage(player, "The door is locked.")
            }
            return@on true
        }
        on(29592, IntType.SCENERY, "climb-down") { player, _ ->
            teleport(player, Location.create(3086, 4247, 0))
            return@on true
        }

        /*
            Crevice interaction (and rope).
         */

        on(29728, IntType.SCENERY, "enter") { player, _ ->
            if (getAttribute(player, ATTRIBUTE_CLIMB_CREVICE, false)) {
                teleport(player, Location.create(3159, 4279, 3))
            } else {
                sendMessage(player, "There's no way down.")
            }
            return@on true
        }

        on(29729, IntType.SCENERY, "climb") { player, _ ->
            if (!getAttribute(player, ATTRIBUTE_CLIMB_CREVICE, false)) {
                setAttribute(player, ATTRIBUTE_CLIMB_CREVICE, true)
            }
            teleport(player, Location.create(3077, 3462, 0))
            return@on true
        }

        /*
            Plaques interaction.
         */

        on((29595..29601).toIntArray(), IntType.SCENERY, "Read-plaque on") { player, node ->
            player.lock() // Prevent client crash.
            read(player, node)
            return@on true
        }

        on(29623, IntType.SCENERY, "use") { player, _ ->
            teleport(player, Location.create(3077, 4235, 0))
            return@on true
        }

        on(29730, IntType.SCENERY, "pull") { player, _ ->
            sendMessage(player, "You hear the cogs and gears moving and a distant unlocking sound.")
            setVarp(player, 1203, (1 shl 29) or (1 shl 26), true)
            return@on true
        }

        on(29731, IntType.SCENERY, "pull") { player, _ ->
            sendMessage(player, "You hear cogs and gears moving and the sound of heavy locks falling into place.")
            setVarp(player, 1203, 1 shl 29, true)
            return@on true
        }

        /*
            Jail doors interaction in 4 different places.
         */

        on(29624, IntType.SCENERY, "open") { player, _ ->
            if (getVarp(player, 1203) and (1 shl 26) == 0) {
                // The door is locked
                sendMessage(player, "The door seems to be locked by some kind of mechanism.")
                return@on true
            }
            if (player.location.z == 2) {
                // Floor 2 to hidden tunnel.
                teleport(player, Location.create(3177, 4266, 0))
            } else if (player.location.z == 1) {
                // Floor 1 to hidden tunnel.
                teleport(player, Location.create(3143, 4270, 0))
            } else {
                // Leaving the hidden tunnel.
                if (player.location.x < 3150) {
                    // Leaving by the west exit (to floor 1).
                    teleport(player, Location.create(3142, 4272, 1))
                } else {
                    // Must be exiting by the east exit (to floor 2).
                    teleport(player, Location.create(3177, 4269, 2))
                }
            }
            return@on true
        }

        /*
            Stairs in the middle of the 1st/2nd floor interaction.
         */

        on(29667, IntType.SCENERY, "climb-down") { player, _ ->
            teleport(player, Location.create(3160, 4249, 1))
            return@on true
        }

        on(29668, IntType.SCENERY, "climb-up") { player, _ ->
            teleport(player, Location.create(3158, 4250, 2))
            return@on true
        }

        on(29663, IntType.SCENERY, "climb-down") { player, _ ->
            teleport(player, Location.create(3160, 4246, 1))
            return@on true
        }

        on(29664, IntType.SCENERY, "climb-up") { player, _ ->
            teleport(player, Location.create(3158, 4245, 2))
            return@on true
        }

        on(29655, IntType.SCENERY, "climb-down") { player, _ ->
            teleport(player, Location.create(3146, 4246, 1))
            return@on true
        }

        on(29656, IntType.SCENERY, "climb-up") { player, _ ->
            teleport(player, Location.create(3149, 4244, 2))
            return@on true
        }

        on(29659, IntType.SCENERY, "climb-down") { player, _ ->
            teleport(player, Location.create(3146, 4249, 1))
            return@on true
        }

        on(29660, IntType.SCENERY, "climb-up") { player, _ ->
            teleport(player, Location.create(3148, 4250, 2))
            return@on true
        }

        /*
            Rewards chest interaction.
         */

        on(29577, IntType.SCENERY, "open") { player, _ ->
            setVarbit(player, 4499, 1, true)
            return@on true
        }

        on(29578, IntType.SCENERY, "search") { player, _ ->
            if (player.globalData.getTestStage() == 3) {
                if ((freeSlots(player) == 0) or ((freeSlots(player) == 1) and !inInventory(player, Items.COINS_995))) {
                    sendDialogue(player, "You do not have enough inventory space!")
                } else {
                    player.emoteManager.unlock(Emotes.SAFETY_FIRST)
                    addItem(player, Items.COINS_995, 10000)
                    addItem(player, Items.SAFETY_GLOVES_12629)
                    sendItemDialogue(player, Items.SAFETY_GLOVES_12629, "You open the chest to find a large pile of gold, along with a pair of safety gloves. ")
                    player.globalData.setTestStage(4)
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

    fun read(player: Player, plaque: Node) {
        if (plaque !is Scenery) return
        player.interfaceManager.openChatbox(DTI_MAP[plaque.id]!!)
    }

    class PlaqueListener : InterfaceListener {

        var scene: PlaqueCutscene? = null

        override fun defineInterfaceListeners() {
            for ((index, iface) in DTI_MAP.values.withIndex()) {
                onClose(iface) { player, _ ->
                    scene?.end()
                    player.globalData.readPlaques[index] = true
                    return@onClose true
                }

                onOpen(iface) { player, component ->
                    scene = PlaqueCutscene(player, component)
                    scene?.start()
                    return@onOpen true
                }

                on(iface) { player, _, _, buttonID, _, _ ->
                    // If thumbs up is clicked.
                    if (buttonID == 2) {
                        player.unlock()
                        scene?.incrementStage()
                        player.interfaceManager.closeChatbox()
                    }
                    return@on true
                }
            }
        }

        class PlaqueCutscene(player: Player, val component: Component) : Cutscene(player) {

            /*
                Since the component does not know the door's location
                there needs to be some translation from player position
                to the door location. This is component -> rotation (dx, dy)
             */

            private val rotationMapping = mapOf(
                701 to listOf(-1, 0),
                703 to listOf(-1, 0),
                711 to listOf(-1, 0),
                695 to listOf(0, 1),
                312 to listOf(1, 0),
                706 to listOf(1, 0),
                698 to listOf(1, 0),
            )

            override fun setup() {
                setExit(player.location)
            }

            override fun runStage(stage: Int) {
                when (stage) {
                    0 -> {
                        // Go to head height.
                        moveCamera(player.location.localX, player.location.localY, 245, speed = 50)
                        // Spin in the right direction.
                        rotateCamera(player.location.localX + rotationMapping[component.id]!![0], player.location.localY + rotationMapping[component.id]!![1], 245, speed = 50)
                    }

                    1 -> PlayerCamera(player).reset()
                }
            }

        }

    }

}
