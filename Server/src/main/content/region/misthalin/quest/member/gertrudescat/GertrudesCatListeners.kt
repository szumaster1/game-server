package content.region.misthalin.quest.member.gertrudescat

import core.api.*
import core.api.consts.Items
import core.api.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.tools.RandomFunction

/**
 * "Gertrude's Cat" quest listeners.
 */
class GertrudesCatListeners : InteractionListener {

    private val CRATE = intArrayOf(Scenery.CRATE_767, Scenery.CRATE_2620)

    override fun defineListeners() {

        on(CRATE, IntType.SCENERY, "search") { player, node ->
            if (getQuestStage(player, "Gertrude's Cat") == 50 && hasAnItem(player, Items.THREE_LITTLE_KITTENS_13236).container != null) {
                setQuestStage(player, "Gertrude's Cat", 40)
            }

            if (node is NPC) {
                sendMessage(player, "You search the crate.")
                sendMessage(player, "You find nothing.")
            }

            if (getQuestStage(player, "Gertrude's Cat") == 40) {
                if (getAttribute(player, "findkitten", false) && freeSlots(player) > 0) {
                    setQuestStage(player, "Gertrude's Cat", 50)
                    sendDialogue(player, "You find a kitten! You carefully place it in your backpack.")
                    addItem(player, Items.THREE_LITTLE_KITTENS_13236)
                }

                sendMessage(player, "You search the crate.")
                sendMessage(player, "You find nothing.")
                if (RandomFunction.random(0, 3) == 1) {
                    sendMessage(player, "You can hear kittens mewing close by...")
                    setAttribute(player, "findkitten", true)
                }
            } else {
                sendMessage(player, "You search the crate.")
                sendMessage(player, "You find nothing.")
            }
            return@on true
        }
    }
}