package content.region.asgarnia.dialogue.burthope

import content.region.asgarnia.quest.deathplateau.dialogue.HaroldDialogueFile
import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.world.update.flag.context.Animation
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

class HaroldDialogue : DialogueFile(), InteractionListener {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.HAROLD_1078)
        if (isQuestInProgress(player!!, "Death Plateau", 10, 29)) {
            openDialogue(player!!, HaroldDialogueFile(), npc!!)
        }

        when (stage) {
            START_DIALOGUE -> player(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
            1 -> npc(FacialExpression.FRIENDLY, "Hi.").also { stage++ }
            2 -> player(FacialExpression.FRIENDLY, "Can I buy you a drink?").also { stage++ }
            3 -> npc(FacialExpression.HAPPY, "Now you're talking! An Asgarnian Ale, please!").also { stage++ }
            4 -> {
                if (!removeItem(player!!, Items.ASGARNIAN_ALE_1905)) {
                    player(FacialExpression.FRIENDLY, "I'll go and get you one.").also { stage = END_DIALOGUE }
                } else {
                    sendMessage(player!!, "You give Harold an Asgarnian Ale.")
                    sendItemDialogue(player!!, Items.ASGARNIAN_ALE_1905, "You give Harold an Asgarnian Ale.").also { stage++ }
                }
            }
            5 -> {
                end()
                animate(npc!!, Animation(Animations.HUMAN_EATTING_829), true)
                runTask(npc!!, 3) {
                    npc(FacialExpression.FRIENDLY, "*burp*").also { stage = END_DIALOGUE }
                }
            }
        }
    }

    override fun defineListeners() {
        on(NPCs.HAROLD_1078, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, HaroldDialogue())
            return@on true
        }
    }
}
