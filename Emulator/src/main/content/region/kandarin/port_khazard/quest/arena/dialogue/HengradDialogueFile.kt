package content.region.kandarin.port_khazard.quest.arena.dialogue

import core.api.*
import cfg.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC

/**
 * Represents the Hengrad dialogue file.
 */
class HengradDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.HENGRAD_263)
        when (getQuestStage(player!!, "Fight Arena")) {

            in 72..88 -> when (stage) {
                0 -> {
                    lock(player!!, 1000)
                    lockInteractions(player!!, 1000)
                    face(findNPC(NPCs.HENGRAD_263)!!, player!!, 1)
                    npcl(FacialExpression.AFRAID, "Are you ok stranger?").also { stage++ }
                }

                1 -> {
                    face(player!!, findNPC(NPCs.HENGRAD_263)!!, 1)
                    playerl(FacialExpression.FRIENDLY, "I'm fine thanks.").also { stage++ }
                }

                2 -> npcl(FacialExpression.ASKING, " So Khazard got his hand on you too?").also { stage++ }
                3 -> playerl(FacialExpression.HALF_WORRIED, " I'm afraid so.").also { stage++ }
                4 -> npcl(FacialExpression.FRIENDLY, " If you're lucky you may last as long as me.").also { stage++ }
                5 -> playerl(FacialExpression.ASKING, " How long have you been here?").also { stage++ }
                6 -> npcl(FacialExpression.FRIENDLY, "I've been in Khazard's prisons ever since I can remember. I was a child when his men kidnapped me. My whole life as been spent killing and fighting, all in the hope that, one day, I might escape.").also { stage++ }

                7 -> playerl(FacialExpression.FRIENDLY, "Don't give up.").also { stage++ }
                8 -> npcl(FacialExpression.FRIENDLY, "Thanks friend.").also { stage++ }
                9 -> npcl(
                    FacialExpression.SILENT,
                    "Wait... Shhh, the guard is coming. Looks like you'll be going into the arena. Good luck, friend."
                ).also { stage++ }

                10 -> {
                    end()
                    setQuestStage(player!!, "Fight Arena", 88)
                    content.region.kandarin.port_khazard.quest.arena.cutscene.ScorpionFightCutscene(player!!).start()
                }
            }
        }
    }
}