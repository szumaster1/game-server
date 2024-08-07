package content.region.kandarin.quest.merlinsquest.dialogue

import content.region.kandarin.quest.merlinsquest.MerlinUtils
import core.api.consts.NPCs
import core.api.getQuest
import core.api.removeAttribute
import core.api.sendDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.combat.DeathTask
import core.game.node.entity.npc.NPC
import core.game.world.repository.Repository
import core.tools.END_DIALOGUE

/**
 * Morgan le faye dialogue file
 *
 * @constructor Morgan le faye dialogue file
 */
class MorganLeFayeDialogueFile : DialogueFile() {

    var STAGE_VANISH = 50
    var STAGE_EXCALIBUR = 15
    var STAGE_MAGIC_WORDS = 32
    var STAGE_KILL = 100

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.MORGAN_LE_FAYE_248)

        when (stage) {
            0 -> {
                val sirMordred = Repository.findNPC(NPCs.SIR_MORDRED_247)
                sirMordred?.lock()

                npcl(FacialExpression.SCARED, "STOP! Please... spare my son.")
                stage++
            }

            1 -> showTopics(
                    Topic(FacialExpression.ANGRY, "Tell me how to untrap Merlin and I might.", 3),
                    Topic(FacialExpression.ANGRY, "No. He deserves to die.", STAGE_KILL),
                    Topic(FacialExpression.NEUTRAL, "Ok then.", STAGE_VANISH))

            2 -> npcl(FacialExpression.NEUTRAL, "You have guessed correctly that I'm responsible for that.").also { stage++ }
            3 -> npcl(FacialExpression.NEUTRAL, "I suppose I can live with that fool Merlin being loose for the sake of my son.").also { stage++ }
            4 -> npcl(FacialExpression.NEUTRAL, "Setting him free won't be easy though.").also { stage++ }
            5 -> npcl(FacialExpression.NEUTRAL, "You will need to find a magic symbol as close to the crystal as you can find.").also { stage++ }
            6 -> npcl(FacialExpression.NEUTRAL, "You will then need to drop some bats' bones on the magic symbol while holding a lit black candle.").also { stage++ }
            7 -> npcl(FacialExpression.NEUTRAL, "This will summon a mighty spirit named Thrantax.").also { stage++ }
            8 -> npcl(FacialExpression.NEUTRAL, "You will need to bind him with magic words.").also { stage++ }
            9 -> npcl(FacialExpression.NEUTRAL, "Then you will need the sword Excalibur with which the spell was bound in order to shatter the crystal.").also { stage++ }
            10 -> showTopics(
                    Topic(FacialExpression.NEUTRAL, "So where can I find Excalibur?", STAGE_EXCALIBUR),
                    Topic(FacialExpression.NEUTRAL, "OK I will do all that.", STAGE_VANISH),
                    Topic(FacialExpression.NEUTRAL, "What are the magic words?", STAGE_MAGIC_WORDS))

            STAGE_EXCALIBUR -> npcl(FacialExpression.NEUTRAL, "The lady of the lake has it. I don't know if she'll give it to you though, she can be rather temperamental.").also {
                var quest = getQuest(player!!, "Merlin's Crystal")

                if (quest.getStage(player) == 30) {
                    player!!.questRepository.setStage(quest, 40)
                }
                stage = 31
            }
            31 -> showTopics(
                    Topic(FacialExpression.NEUTRAL, "OK, I will go do all that.", STAGE_VANISH),
                    Topic(FacialExpression.NEUTRAL, "What are the magic words?", STAGE_MAGIC_WORDS))
            STAGE_MAGIC_WORDS -> npcl(FacialExpression.NEUTRAL, "You will find the magic words at the base of one of the chaos altars.").also { stage++ }
            33 -> npcl(FacialExpression.NEUTRAL, "Which chaos altar I cannot remember.").also { stage++ }
            34 -> showTopics(
                    Topic(FacialExpression.NEUTRAL, "So where can I find Excalibur?", STAGE_EXCALIBUR),
                    Topic(FacialExpression.NEUTRAL, "OK I will go do all that.", STAGE_VANISH))

            STAGE_VANISH -> {
                morganDisapear(true)
                stage = END_DIALOGUE
            }

            STAGE_KILL -> {
                val sirMordred = Repository.findNPC(NPCs.SIR_MORDRED_247)

                if (sirMordred != null && sirMordred.isActive) {
                    player!!.sendMessage("You kill Mordred.")
                    DeathTask.startDeath(sirMordred, player!!)
                }

                end()
                stage = END_DIALOGUE
                morganDisapear(false)
            }

        }
    }

    /**
     * Morgan disapear
     *
     * @param sendMessage
     */
    fun morganDisapear(sendMessage: Boolean) {
        val morgan = player!!.getAttribute<NPC>(MerlinUtils.TEMP_ATTR_MORGAN, null)

        if (morgan != null) {
            if (sendMessage) {
                sendDialogue(player!!, "Morgan Le Faye vanishes.")
            }
            removeAttribute(player!!, MerlinUtils.TEMP_ATTR_MORGAN)
        } else {
            end()
        }

    }

}
