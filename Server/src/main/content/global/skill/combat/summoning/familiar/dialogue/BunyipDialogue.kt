package content.global.skill.combat.summoning.familiar.dialogue

import content.global.skill.gathering.fishing.data.Fish
import core.api.anyInEquipment
import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Bunyip dialogue.
 */
@Initializable
class BunyipDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (anyInEquipment(player!!, *fishes)) {
            npcl(FacialExpression.CHILD_NORMAL, "I see you've got some fish there, mate.").also { stage = 0 }
            return true
        }
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Where are we going and why is it not to the beach?").also { stage = 4 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Hey Bruce, can we go down to the beach t'day?").also { stage = 8 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Pass me another bunch of shrimps, mate!").also { stage = 10 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Sigh...").also { stage = 13 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when(stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Yeah, but I might cook them up before I give them to you!").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Humans...always ruining good fishes.").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "You know, some people prefer them cooked.").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Yeah. We call 'em freaks.").also { stage = END_DIALOGUE }

            4  -> playerl(FacialExpression.FRIENDLY, "Well, we have a fair few places to go, but I suppose we could go to the beach if we get time.").also { stage++ }
            5  -> npcl(FacialExpression.CHILD_NORMAL, "Bonza! I'll get my board ready!").also { stage++ }
            6  -> playerl(FacialExpression.FRIENDLY, "Well, even if we do go to the beach I don't know if we'll have time for that.").also { stage++ }
            7  -> npcl(FacialExpression.CHILD_NORMAL, "Awww, that's a drag...").also { stage = END_DIALOGUE }

            8  -> playerl(FacialExpression.FRIENDLY, "Well, I have a lot of things to do today but maybe later.").also { stage++ }
            9  -> npcl(FacialExpression.CHILD_NORMAL, "Bonza!").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.FRIENDLY, "I don't know if I want any more water runes.").also { stage++ }
            11 -> npcl(FacialExpression.CHILD_NORMAL, "Righty, but I do know that I want some shrimps!").also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "A fair point.").also { stage = END_DIALOGUE }

            13 -> playerl(FacialExpression.HALF_ASKING, "What's the matter?").also { stage++ }
            14 -> npcl(FacialExpression.CHILD_NORMAL, "I'm dryin' out in this sun, mate.").also { stage++ }
            15 -> playerl(FacialExpression.ASKING, "Well, what can I do to help?").also { stage++ }
            16 -> npcl(FacialExpression.CHILD_NORMAL, "Well, fish oil is bonza for the skin, ya know.").also { stage++ }
            17 -> playerl(FacialExpression.FRIENDLY, "Oh, right, I think I see where this is going.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BUNYIP_6813, NPCs.BUNYIP_6814)
    }

    companion object {
        private val fishes = Fish.fishMap.values.map { it.id }.toIntArray()
    }

}
