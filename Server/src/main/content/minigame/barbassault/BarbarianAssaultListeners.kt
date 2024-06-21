package content.minigame.barbassault

import core.api.consts.Components
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.api.openInterface
import core.api.sendDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class BarbarianAssaultListeners : InteractionListener {

    override fun defineListeners() {

        on(NPCs.COMMANDER_CONNAD_5029, IntType.NPC, "get-rewards") { player, _ ->
            openInterface(player, Components.BARBASSAULT_REWARD_SHOP_491)
            return@on true
        }

        on(NPCs.CAPTAIN_CAIN_5030, IntType.NPC, "tutorial") { player, _ ->
            openInterface(player, Components.BARBASSAULT_TUTORIAL_496)
            return@on true
        }

        on(Scenery.PENANCE_FIGHTER_STATUE_20164, IntType.SCENERY, "inspect") { player, _ ->
            sendDialogue(player, "I wouldn't like to meet him in a dark alley.")
            return@on true
        }
    }

}