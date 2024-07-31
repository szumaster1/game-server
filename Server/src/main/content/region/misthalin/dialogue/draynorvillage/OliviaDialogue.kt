package content.region.misthalin.dialogue.draynorvillage

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class OliviaDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "Would you like to trade in seeds?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                options( "Yes", "No", "Where do I get rarer seeds from?")
                stage = 1
            }

            1 -> when (buttonId) {
                1 -> {
                    end()
                    npc.openShop(player)
                }

                2 -> {
                    player(FacialExpression.NEUTRAL, "No, thanks.")
                    stage = 20
                }

                3 -> {
                    player(FacialExpression.ASKING, "Where do I get rarer seeds from?")
                    stage = 40
                }
            }

            20 -> end()
            40 -> {
                npc(FacialExpression.FRIENDLY, "The Master Farmers usually carry a few rare seeds", "around with them, although I don't know if they'd want", "to part with them for any price to be honest.")
                stage = 41
            }

            41 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.OLIVIA_2233, NPCs.OLIVIA_2572)
    }

}