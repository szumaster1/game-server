package content.region.misc.dialogue.zanaris

import core.api.consts.NPCs
import core.api.isQuestComplete
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Fairy fixit dialogue.
 */
@Initializable
class FairyFixitDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (isQuestComplete(player, "A Fairy Tale II - Cure a Queen")) {
            npc(FacialExpression.OLD_CALM_TALK1, "Pssst! Human! I've got something for you.").also { stage = 20 }
        } else {
            npc(FacialExpression.OLD_DISTRESSED, "What is it, human? Busy busy busy!")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Why are you carrying that toolbox?", "I'm okay, thanks.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.ASKING, "Why are you carrying that toolbox?").also { stage = 10 }
                2 -> player(FacialExpression.FRIENDLY, "I'm okay, thanks.").also { stage = END_DIALOGUE }
            }
            10 -> npc(FacialExpression.OLD_DEFAULT, "It's the fizgog! It's picking up cable again!").also { stage++ }
            11 -> playerl(FacialExpression.ASKING, "Uh, right. So is it safe to use the fairy rings then?").also { stage++ }
            12 -> npcl(FacialExpression.OLD_CALM_TALK1, "Sure, as long as you have been given permission to use them. You should just be aware that using the fairy rings sometimes has strange results - the locations that you have been to may").also { stage++ }
            13 -> npcl(FacialExpression.OLD_CALM_TALK2, "affect the locations you are trying to reach. I could fix it by replacing the fizgog and the whosprangit; I've put in a request for some new parts, but they're").also { stage++ }
            14 -> npc(FacialExpression.OLD_CALM_TALK1, "pretty hard to get hold of it seems.").also { stage = END_DIALOGUE }
            20 -> options("What have you got for me?", "Why are you carrying that toolbox?", "Not interested, thanks.").also { stage++ }
            21 -> when (buttonId) {
                1 -> player(FacialExpression.ASKING, "What have you got for me?").also { stage = 30 }
                2 -> player(FacialExpression.ASKING, "Why are you carrying that toolbox?").also { stage = 10 }
                3 -> player(FacialExpression.NEUTRAL, "Not interested, thanks.").also { stage = END_DIALOGUE }
            }
            30 -> npcl(FacialExpression.OLD_CALM_TALK1, "They said you'd helped cure our Queen. I haven't got a lot of rewards to offer, but my enchantment scrolls might help if you're working with fairy rings in your home.").also { stage++ }
            31 -> {
                end()
                openNpcShop(player, NPCs.FAIRY_FIXIT_4455)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FAIRY_FIXIT_4455)
    }

}
