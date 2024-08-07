package content.global.random.event.pillory

import core.api.consts.Components
import core.api.getAttribute
import core.api.runTask
import core.api.setAttribute
import core.api.setComponentVisibility
import core.game.interaction.InterfaceListener

/**
 * Pillory interface.
 */
class PilloryInterface : InterfaceListener {

    private val RE_INTER = Components.MACRO_PILLORY_GUARD_188

    override fun defineInterfaceListeners() {
        onOpen(RE_INTER) { player, _ ->

            // Attributes.
            setAttribute(player, PilloryUtils.WRONG_ANSWER, 0)
            setAttribute(player, PilloryUtils.CORRECT_ANSWER, 0)
            setAttribute(player, PilloryUtils.COMPLETE_RANDOM, 0)

            // Hide padlocks.
            setComponentVisibility(player, RE_INTER, 14, true)
            setComponentVisibility(player, RE_INTER, 15, true)
            setComponentVisibility(player, RE_INTER, 16, true)
            return@onOpen true
        }

        on(Components.MACRO_PILLORY_GUARD_188) { player, _, _, buttonID, _, _ ->
            var goodPoints = getAttribute(player, PilloryUtils.CORRECT_ANSWER, 0)
            var completeRandom = getAttribute(player, PilloryCutscene.COMPLETE_RANDOM, 0)

            if (goodPoints == 1) {
                setComponentVisibility(player, RE_INTER, 17, false)
                setComponentVisibility(player, RE_INTER, 11, true)
            }

            if (goodPoints == 2) {
                setComponentVisibility(player, RE_INTER, 18, false)
                setComponentVisibility(player, RE_INTER, 12, true)
            }

            if (goodPoints == 3) {
                setComponentVisibility(player, RE_INTER, 19, false)
                setComponentVisibility(player, RE_INTER, 13, true)
                setAttribute(player, PilloryCutscene.COMPLETE_RANDOM, 1)
            }

            // End
            if (completeRandom == 1) {
                runTask(player, 1) {
                    PilloryCutscene(player).updateStage(2)
                }
            }

            when (buttonID) {
                5, 6, 7 -> player.incrementAttribute(PilloryUtils.CORRECT_ANSWER)
            }
            return@on true
        }
    }
}

//Child: 11, type: 6, width: 28, height: 48
//Child: 12, type: 6, width: 28, height: 48
//Child: 13, type: 6, width: 28, height: 48
//Child: 14, type: 6, width: 28, height: 48
//Child: 15, type: 6, width: 28, height: 48
//Child: 16, type: 6, width: 28, height: 48

// 8, 9, 10