package content.global.skill.combat.summoning.familiar.dialogue

import content.global.skill.gathering.fishing.data.Fish
import core.api.anyInInventory
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Granite crab dialogue.
 */
@Initializable
class GraniteCrabDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (anyInInventory(player!!, *fishes)) {
            npcl(FacialExpression.CHILD_NORMAL, "That is not a rock fish...").also { stage = END_DIALOGUE }
            return true
        }
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Can I have some fish?").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Rock fish now, please?").also { stage = 5 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "When can we go fishing? I want rock fish.").also { stage = 6 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "I'm stealthy!").also { stage = 7 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when(stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "No, I have to cook these for later.").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Free fish, please?").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "No...I already told you you can't.").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Can it be fish time soon?").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "Great...I get stuck with the only granite crab in existence that can't take no for an answer...").also { stage = END_DIALOGUE }

            5 -> playerl(FacialExpression.FRIENDLY, "Not right now. I don't have any rock fish.").also { stage = END_DIALOGUE }

            6 -> playerl(FacialExpression.FRIENDLY, "When I need some fish. It's not that hard to work out, right?").also { stage = END_DIALOGUE }

            7 -> playerl(FacialExpression.FRIENDLY, "Errr... of course you are.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GRANITE_CRAB_6796, NPCs.GRANITE_CRAB_6797)
    }

    companion object {
        private val fishes = Fish.fishMap.values.map { it.id }.toIntArray()
    }
}
