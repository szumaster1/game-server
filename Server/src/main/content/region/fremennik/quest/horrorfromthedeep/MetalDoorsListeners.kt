package content.region.fremennik.quest.horrorfromthedeep

import content.region.fremennik.quest.horrorfromthedeep.dialogue.MetalDoorsInteractionDialogue
import core.api.*
import cfg.consts.Components
import cfg.consts.Sounds
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.InterfaceListener
import core.game.interaction.QueueStrength

/**
 * Metal doors listeners.
 */
class MetalDoorsListeners : InteractionListener, InterfaceListener {

    override fun defineListeners() {

        /*
         * Opens the metal door interface.
         */

        on(HFTDUtils.METAL_DOORS, IntType.SCENERY, "study") { player, _ ->
            when (player.location.y) {
                4626 -> {
                    if (getQuestStage(player, "Horror from the Deep") >= 50) {
                        openInterface(player, 142)
                    } else {
                        openInterface(player, 142)
                        setAttribute(player, HFTDUtils.STRANGE_WALL_DISCOVER, true)
                    }
                }

                10002 -> openInterface(player, 142)
                4627, 10003 -> sendMessage(player, "You cannot see anything unusual about the wall from this side.")
            }
            return@on true
        }

        /*
         * Handle use with interaction on metal doors.
         */

        onUseWith(IntType.SCENERY, HFTDUtils.STRANGE_W_REQ_ITEMS, *HFTDUtils.METAL_DOORS) { player, used, _ ->
            openDialogue(player, MetalDoorsInteractionDialogue(qitems = used.id))
            return@onUseWith true
        }

        /*
         * Handle opening the door after placed required items.
         */

        on(HFTDUtils.STRANGE_DOORS, IntType.SCENERY, "open") { player, node ->
            if (getQuestStage(player, "Horror from the Deep") >= 50) {
                queueScript(player, 1, QueueStrength.SOFT) { stage: Int ->
                    if (stage == 0) {
                        DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
                        playAudio(player, Sounds.STRANGEDOOR_OPEN_1626)
                        return@queueScript keepRunning(player)
                    }
                    if (stage == 1) {
                        playAudio(player, Sounds.STRANGEDOOR_CLOSE_1625)
                        setQuestStage(player, "Horror from the Deep", 55)
                        removeAttribute(player, HFTDUtils.STRANGE_WALL_DISCOVER)
                        return@queueScript stopExecuting(player)
                    }
                    return@queueScript stopExecuting(player)
                }
            } else {
                when (player.location.y) {
                    4626,
                    10002 -> sendMessage(player, "You cannot see any way to move this part of the wall....")

                    4627,
                    10003 -> sendMessage(player, "You cannot see anything unusual about the wall from this side.")
                }
            }
            return@on true
        }
    }

    override fun defineInterfaceListeners() {

        /*
         * Handle visuals of metal door interface.
         */

        onOpen(Components.HORROR_METALDOOR_142) { player, _ ->
            setComponentVisibility(player, 142, 2, getAttribute(player, HFTDUtils.STRANGE_W_FIRE, 0) != 1)
            setComponentVisibility(player, 142, 3, getAttribute(player, HFTDUtils.STRANGE_W_AIR, 0) != 1)
            setComponentVisibility(player, 142, 4, getAttribute(player, HFTDUtils.STRANGE_W_EARTH, 0) != 1)
            setComponentVisibility(player, 142, 5, getAttribute(player, HFTDUtils.STRANGE_W_WATER, 0) != 1)
            setComponentVisibility(player, 142, 6, getAttribute(player, HFTDUtils.STRANGE_W_ARROW, 0) != 1)
            setComponentVisibility(player, 142, 7, getAttribute(player, HFTDUtils.STRANGE_W_SWORD, 0) != 1)

            if (getAttribute(player, HFTDUtils.PUZZLE_PROGRESS, 0) > 5) {
                closeInterface(player)
                queueScript(player, 1, QueueStrength.SOFT){
                    sendMessage(player, "You hear the sound of something moving within the wall.")
                    playAudio(player, Sounds.STRANGEDOOR_SOUND_1627)
                    setQuestStage(player, "Horror from the Deep", 50)
                    return@queueScript stopExecuting(player)
                }
            }
            return@onOpen true
        }
    }
}
