package content.region.fremennik.dialogue.miscellania

import content.region.fremennik.handlers.waterbirth.TravelDestination
import content.region.fremennik.handlers.waterbirth.WaterbirthTravel.sail
import core.api.consts.NPCs
import core.api.inBorders
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class SailorDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        playerl(FacialExpression.ASKING, "Hello. Can I get a ride on your ship?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(
                FacialExpression.HAPPY,
                "Hello brother ${player!!.username}. If you are ready to jump aboard, we're all ready to set sail with the tide!"
            ).also { stage++ }

            1 -> options("Let's go!", "Actually no.").also { stage++ }
            2 -> when (buttonId) {
                1 -> {
                    end()
                    if (inBorders(player, 2580, 3844, 2584, 3847)) sail(
                        player!!,
                        TravelDestination.MISCELLANIA_TO_RELLEKKA
                    ) else sail(player!!, TravelDestination.RELLEKA_TO_MISCELLANIA)
                }

                2 -> {
                    npc("Okay. Suit yourself.")
                    end()
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SAILOR_1304)
    }
}