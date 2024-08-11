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
        on(cupBoard, IntType.SCENERY, "open") { player, node ->
            animate(player, 542)
            playAudio(player, Sounds.CUPBOARD_OPEN_58)
            sendMessage(player, "You open the cupboard.")
            replaceScenery(node.asScenery(), openedCupBoard, -1)
            return@on true
        }

        on(openedCupBoard, IntType.SCENERY, "close") { player, node ->
            animate(player, 543)
            playAudio(player, Sounds.CUPBOARD_CLOSE_57)
            sendMessage(player, "You close the cupboard.")
            replaceScenery(node.asScenery(), cupBoard, -1)
            return@on true
        }

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

        on(coffin, IntType.SCENERY, "open") { player, node ->
            animate(player, 832)
            playAudio(player, Sounds.COFFIN_OPEN_54)
            replaceScenery(node.asScenery(), openedCoffin, -1)
            return@on true
        }

        on(openedCoffin, IntType.SCENERY, "close") { player, node ->
            animate(player, 832)
            playAudio(player, Sounds.COFFIN_CLOSE_53)
            replaceScenery(node.asScenery(), coffin, -1)
            return@on true
        }

        on(openedCoffin, IntType.SCENERY, "search") { player, _ ->
            if (isQuestComplete(player, "Vampire Slayer")) {
                sendDialogue(player, "There's only a pillow in here..")
            } else if (getQuestStage(player, "Vampire Slayer") == 30) {
                animate(player, 832)
                if (!inInventory(player, stake) && !inInventory(player, hammer)) {
                    sendMessage(player, "You must have a stake and hammer with you to kill the vampire.")
                    return@on true
                }
                val o = player.getAttribute<NPC>("count", null)
                if (o == null || !o.isActive) {

                    runTask(player, 1) {
                        val vampire = core.game.node.entity.npc.NPC.create(757, player.location)
                        vampire.isRespawn = false
                        vampire.setAttribute("player", player)
                        vampire.init()
                        vampire.properties.combatPulse.attack(player)
                        setAttribute(player, "count", vampire)
                    }
                }
            }
            return@on true
        }

        on(stairsUp, IntType.SCENERY, "walk-down") { player, _ ->
            player.properties.teleportLocation = basement
            sendMessage(player, "You walk down the stairs...")
            return@on true
        }

        on(stairsBasement, IntType.SCENERY, "walk-up") { player, _ ->
            player.properties.teleportLocation = groundFloor
            return@on true
        }
    }

}