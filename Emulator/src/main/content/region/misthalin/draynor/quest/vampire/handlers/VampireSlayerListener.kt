package content.region.misthalin.draynor.quest.vampire.handlers

import core.api.*
import content.region.misthalin.draynor.handlers.DraynorUtils
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.world.map.Direction
import core.game.world.map.Location
import org.rs.consts.*

/**
 * Represents the Vampire slayer quest related interactions.
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
            if(!isQuestComplete(player, QuestName.VAMPIRE_SLAYER) && getQuestStage(player, QuestName.VAMPIRE_SLAYER) == 30) {
                replaceScenery(node.asScenery(), DraynorUtils.openedCoffin, 6)
                runTask(player, 3) {
                    val o = player.getAttribute<NPC>("count", null)
                    if (o == null || !o.isActive) {
                        val vampire = core.game.node.entity.npc.NPC.create(NPCs.COUNT_DRAYNOR_757, Location.create(3078, 9775))
                        vampire.isRespawn = false
                        vampire.setAttribute("player", player)
                        vampire.init()
                        runTask(player, 5) {
                            teleport(vampire, Location(3078, 9774, 0))
                            vampire.animator.reset()
                            vampire.properties.combatPulse.attack(player)
                        }
                        setAttribute(player, "count", vampire)
                        if(inInventory(player, Items.STAKE_1549)) {
                            sendMessage(player, "The vampire seems to weaken.")
                        }
                    }
                }
            } else {
                sendPlayerDialogue(player, "I should tell Morgan that I've killed the vampire!")
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