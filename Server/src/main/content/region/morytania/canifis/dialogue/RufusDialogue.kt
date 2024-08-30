package content.region.morytania.canifis.dialogue

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Rufus dialogue.
 */
@Initializable
class RufusDialogue(player: Player? = null) : Dialogue(player) {


    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HAPPY, "Hi!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HAPPY, "Grreeting frrriend! Welcome to my worrrld famous", "food emporrium! All my meats are so frrresh you'd", "swear you killed them yourrrself!").also { stage++ }
            1 -> options("Why do you only sell meats?", "Do you sell cooked food?", "Can I buy some food?").also { stage++ }
            2 -> when (buttonId) {
                1 -> player(FacialExpression.ASKING, "Why do you only sell meats?").also { stage++ }
                2 -> player(FacialExpression.ASKING, "Do you sell cooked food?").also { stage = 7 }
                3 -> player(FacialExpression.FRIENDLY, "Can I buy some food?").also { stage = 8 }
            }
            3 -> npc(FacialExpression.DISGUSTED, "What? Why, what else would you want to eat? What", "kind of lycanthrrope are you anyway?").also { stage++ }
            4 -> player(FacialExpression.HALF_GUILTY, "...A vegetarian one?").also { stage++ }
            5 -> npc(FacialExpression.EXTREMELY_SHOCKED, "Vegetarrrian...?").also { stage++ }
            6 -> player(FacialExpression.SUSPICIOUS, "Never mind.").also { stage = END_DIALOGUE }
            7 -> npc(FacialExpression.EVIL_LAUGH, "Cooked food? Who would want that? You lose all the", "flavourrr of the meat when you can't taste the blood!").also { stage = END_DIALOGUE }
            8 -> npc(FacialExpression.HAPPY, "Cerrrtainly!").also { stage++ }
            9 -> {
                end()
                openNpcShop(player, NPCs.RUFUS_1038)
            }
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return RufusDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.RUFUS_1038)
    }

}
