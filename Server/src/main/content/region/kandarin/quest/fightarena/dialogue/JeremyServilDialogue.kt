package content.region.kandarin.quest.fightarena.dialogue

import core.api.consts.NPCs
import core.api.getQuestStage
import core.api.setQuestStage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Jeremy servil dialogue.
 */
@Initializable
class JeremyServilDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        when (getQuestStage(player!!, "Fight Arena")) {

            20 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello.").also { stage++ }
                1 -> npcl(FacialExpression.CHILD_SAD, "Please " + (if (player!!.isMale) "Sir" else "Madam") + ", don't hurt me.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Sshh. This uniform is a disguise. I'm here to help. Where do they keep the keys?").also { stage++ }
                3 -> npcl(FacialExpression.CHILD_SAD, "The guard always keeps hold of them.").also { stage++ }
                4 -> playerl(FacialExpression.FRIENDLY, "Don't lose heart, I'll be back.").also { stage++ }
                5 -> {
                    end()
                    setQuestStage(player!!, "Fight Arena", 40)
                }
            }

            in 90..100 -> when (stage) {
                0 -> npcl(FacialExpression.CHILD_NORMAL, "You need to kill the creatures in the arena").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.JEREMY_SERVIL_265)
    }

}
