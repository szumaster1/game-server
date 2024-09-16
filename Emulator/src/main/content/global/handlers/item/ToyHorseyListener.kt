package content.global.handlers.item

import core.api.animate
import cfg.consts.Animations
import cfg.consts.Items
import core.api.sendChat
import core.api.stopWalk
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Handles the toy horsey option interaction.
 */
class ToyHorseyListener : InteractionListener {

    private val horseyMap = mapOf(
        Items.TOY_HORSEY_2520 to Animations.HUMAN_PLAY_WITH_BROWN_HORSE_918,
        Items.TOY_HORSEY_2522 to Animations.HUMAN_PLAY_WITH_WHITE_HORSE_919,
        Items.TOY_HORSEY_2524 to Animations.HUMAN_PLAY_WITH_BLACK_HORSE_920,
        Items.TOY_HORSEY_2526 to Animations.HUMAN_PLAY_WITH_GRAY_HORSE_921
    )

    private val pharses = arrayOf("Come-on Dobbin, we can win the race!", "Hi-ho Silver, and away", "Neaahhhyyy! Giddy-up horsey!")

    override fun defineListeners() {
        on(horseyMap.keys.toIntArray(), IntType.ITEM, "play-with") { player, node ->
            stopWalk(player)
            animate(player, horseyMap[node.id])
            sendChat(player, pharses.random())
            return@on true
        }
    }

}
