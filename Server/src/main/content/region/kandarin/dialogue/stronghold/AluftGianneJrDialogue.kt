package content.region.kandarin.dialogue.stronghold

import content.minigame.gnomecook.GC_BASE_ATTRIBUTE
import content.minigame.gnomecook.GC_TUT_PROG
import core.api.consts.NPCs
import core.api.setAttribute
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Aluft gianne jr dialogue.
 */
@Initializable
class AluftGianneJrDialogue(player: Player? = null) : Dialogue(player) {

    var tutorialStage = -1

    override fun open(vararg args: Any?): Boolean {
        tutorialStage = player.getAttribute("$GC_BASE_ATTRIBUTE:$GC_TUT_PROG", -1)
        if (tutorialStage == -1) {
            player("Hey can I get a job here?")
        } else {
            npc(FacialExpression.OLD_NORMAL,"Having fun?").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.OLD_NORMAL,"Sure, go talk to my dad. I'll put", "a good word in!").also { stage++ }
            1 -> player(FacialExpression.THINKING, "Th-thanks...?").also { stage++ }
            2 -> {
                end()
                setAttribute(player, "/save:$GC_BASE_ATTRIBUTE:$GC_TUT_PROG", 0)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GIANNE_JNR_4572)
    }

}
