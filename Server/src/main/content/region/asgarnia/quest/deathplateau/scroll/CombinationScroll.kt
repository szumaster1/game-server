package content.region.asgarnia.quest.deathplateau.scroll

import core.api.consts.Components
import core.api.consts.Items
import core.api.openInterface
import core.api.setInterfaceText
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player

/**
 * Combination scroll.
 */
class CombinationScroll : InteractionListener {

    companion object {
        fun CombinationScroll(player: Player) {
            val combinationScroll =
                arrayOf(
                    "",
                    "Red is North of Blue. Yellow is South of Purple.",
                    "Green is North of Purple. Blue is West of",
                    "Yellow. Purple is East of Red.",
                    "",
                )
            setInterfaceText(player, combinationScroll.joinToString("<br>"), Components.BLANK_SCROLL_222, 4)
        }

    }

    override fun defineListeners() {
        on(Items.COMBINATION_3102, IntType.ITEM, "read") { player, _ ->
            openInterface(player, Components.BLANK_SCROLL_222).also { CombinationScroll(player) }
            return@on true
        }

    }

}