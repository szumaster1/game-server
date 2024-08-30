package content.region.misthalin.draynor.quest.vampire

import cfg.consts.Sounds
import core.api.*
import cfg.consts.Animations
import cfg.consts.NPCs
import content.region.misthalin.draynor.DraynorUtils
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC

/**
 * Represents the [VampireSlayerListener].
 */
class VampireSlayerListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Listener for opening the cupboard.
         */

        on(DraynorUtils.cupBoard, IntType.SCENERY, "open") { player, node ->
            animate(player, Animations.OPEN_WARDROBE_542)
            playAudio(player, Sounds.CUPBOARD_OPEN_58)
            sendMessage(player, "You open the cupboard.")
            replaceScenery(node.asScenery(), DraynorUtils.openedCupBoard, -1)
            return@on true
        }

        /*
         * Listener for closing the cupboard.
         */

        on(DraynorUtils.openedCupBoard, IntType.SCENERY, "close") { player, node ->
            animate(player, Animations.CLOSE_CUPBOARD_543)
            playAudio(player, Sounds.CUPBOARD_CLOSE_57)
            sendMessage(player, "You close the cupboard.")
            replaceScenery(node.asScenery(), DraynorUtils.cupBoard, -1)
            return@on true
        }

        /*
         * Listener for searching the opened cupboard.
         */

        on(DraynorUtils.openedCupBoard, IntType.SCENERY, "search") { player, _ ->
            if (!inInventory(player, DraynorUtils.garlic)) {
                sendMessage(player, "The cupboard contains garlic. You take a clove.")
                addItem(player, DraynorUtils.garlic, 1)
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

        on(DraynorUtils.coffin, IntType.SCENERY, "open") { player, node ->
            animate(player, Animations.MULTI_USE_TAKE_832)
            playAudio(player, Sounds.COFFIN_OPEN_54)
            replaceScenery(node.asScenery(), DraynorUtils.openedCoffin, -1)
            return@on true
        }

        /*
         * Listener for closing the opened coffin.
         */

        on(DraynorUtils.openedCoffin, IntType.SCENERY, "close") { player, node ->
            animate(player, Animations.MULTI_USE_TAKE_832)
            playAudio(player, Sounds.COFFIN_CLOSE_53)
            replaceScenery(node.asScenery(), DraynorUtils.coffin, -1)
            return@on true
        }

        /*
         * Listener for searching the opened coffin.
         */

        on(DraynorUtils.openedCoffin, IntType.SCENERY, "search") { player, _ ->
            if (isQuestComplete(player, "Vampire Slayer")) {
                sendDialogue(player, "There's only a pillow in here..")
            } else if (getQuestStage(player, "Vampire Slayer") == 30) {
                animate(player, Animations.MULTI_USE_TAKE_832)
                if (!inInventory(player, DraynorUtils.stake) && !inInventory(player, DraynorUtils.hammer)) {
                    sendMessage(player, "You must have a stake and hammer with you to kill the vampire.")
                    return@on true
                }
                val o = player.getAttribute<NPC>("count", null)
                if (o == null || !o.isActive) {
                    runTask(player, 1) {
                        val vampire = core.game.node.entity.npc.NPC.create(NPCs.COUNT_DRAYNOR_757, player.location)
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

        /*
         * Listener for walking down the stairs.
         */

        on(DraynorUtils.stairsUp, IntType.SCENERY, "walk-down") { player, _ ->
            player.properties.teleportLocation = DraynorUtils.basement
            sendMessage(player, "You walk down the stairs...")
            return@on true
        }

        /*
         * Listener for walking up the stairs.
         */

        on(DraynorUtils.stairsBasement, IntType.SCENERY, "walk-up") { player, _ ->
            player.properties.teleportLocation = DraynorUtils.groundFloor
            return@on true
        }
    }
}