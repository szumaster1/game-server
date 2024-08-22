package content.minigame.puropuro.dialogue

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Wandering Impling dialogue.
 */
@Initializable
class WanderingImplingDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        player("Hello. So, what is this place?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.OLD_DEFAULT, "in your pockets? Something tasty?").also { stage++ }
            1 -> player("Stay out of my pockets! I don't have anything that you", "want.").also { stage++ }
            2 -> npc(FacialExpression.OLD_DEFAULT, "Ah, but do you have anything that *you* want?").also { stage++ }
            3 -> player("Of course I do!").also { stage++ }
            4 -> npc(FacialExpression.OLD_DEFAULT, "Then you have something that implings want.").also { stage++ }
            5 -> player("Eh?").also { stage++ }
            6 -> npc(FacialExpression.OLD_DEFAULT, "We want things you people want. They are tasty to us!", "The more you want them, the tastier they are!").also { stage++ }
            7 -> player("So, you collect things that humans want? Interesting...", "So, what would happen if I caught an impling in a", "butterfly net?").also { stage++ }
            8 -> npc(FacialExpression.OLD_DEFAULT, "Don't do that! That would be cruel. But chase us, yes!", "That is good. Implings that are not easy to catch. Especially", "ones with tasty food.").also { stage = END_DIALOGUE}
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.WANDERING_IMPLING_6073)
    }

}
