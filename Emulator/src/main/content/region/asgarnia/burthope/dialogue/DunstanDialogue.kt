package content.region.asgarnia.burthope.dialogue

import org.rs.consts.NPCs
import core.api.isQuestComplete
import core.api.isQuestInProgress
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE
import org.rs.consts.QuestName

/**
 * Represents the Dunstan dialogue.
 */
@Initializable
class DunstanDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        /*
         * When Troll Stronghold is complete
         */
        if (isQuestComplete(player!!, QuestName.TROLL_STRONGHOLD)) {
            when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Hi!").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "Hi! What can I do for you?").also { stage++ }
                2 ->
                    showTopics(
                        Topic(FacialExpression.THINKING, "Is it OK if I use your anvil?", 10),
                        Topic(FacialExpression.FRIENDLY, "Nothing, thanks.", END_DIALOGUE),
                        Topic(FacialExpression.FRIENDLY, "How is your son getting on?", 15),
                        /*
                         * Sleds topic when Troll Romance is implemented.
                         */
                    )

                10 -> npcl(FacialExpression.FRIENDLY, "So you're a smithy are you?").also { stage++ }
                11 -> playerl(FacialExpression.FRIENDLY, "I dabble.").also { stage++ }
                12 -> npcl(FacialExpression.FRIENDLY, "A fellow smith is welcome to use my anvil!").also {
                        stage++
                    }

                13 -> playerl(FacialExpression.FRIENDLY, "Thanks!").also { stage++ }
                14 -> npcl(FacialExpression.FRIENDLY, "Anything else before I get on with my work?").also { stage = 2 }

                15 -> npcl(FacialExpression.FRIENDLY, "He is getting on fine! He has just been promoted to Sergeant! I'm really proud of him!").also { stage++ }
                16 -> playerl(FacialExpression.FRIENDLY, "I'm happy for you!").also { stage++ }
                17 -> npcl(FacialExpression.FRIENDLY, "Anything else before I get on with my work?").also { stage = 2 }
            }
            return true
        }

        /*
         * Troll Stronghold in progress.
         */
        if (isQuestInProgress(player!!, QuestName.TROLL_STRONGHOLD, 1, 99)) {
            openDialogue(player!!, DunstanDialogue(), npc)
            return true
        }

        /*
         * When Death Plateau is complete.
         */
        if (isQuestComplete(player!!, "Death Plateau")) {
            when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Hi!").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "Hi! What can I do for you?").also { stage++ }
                2 -> showTopics(
                        Topic(FacialExpression.THINKING, "Is it OK if I use your anvil?", 10),
                        Topic(FacialExpression.FRIENDLY, "Nothing, thanks.", END_DIALOGUE),
                        Topic(FacialExpression.FRIENDLY, "How is your son getting on?", 15)
                )
                10 -> npcl(FacialExpression.FRIENDLY, "So you're a smithy are you?").also { stage++ }
                11 -> playerl(FacialExpression.FRIENDLY, "I dabble.").also { stage++ }
                12 -> npcl(FacialExpression.FRIENDLY, "A fellow smith is welcome to use my anvil!").also { stage++ }
                13 -> playerl(FacialExpression.FRIENDLY, "Thanks!").also { stage++ }
                14 -> npcl(FacialExpression.FRIENDLY, "Anything else before I get on with my work?").also { stage = 2 }
                15 -> npcl(FacialExpression.SAD, "He was captured by those cursed trolls! I don't know what to do. Even the imperial guard are too afraid to go rescue him.").also { stage++ }
                16 -> playerl(FacialExpression.ASKING, "What happened?").also { stage++ }
                17 -> npcl(FacialExpression.SAD, "Talk to Denulth, he can tell you all about it. Anything else before I get on with my work?").also { stage = 2 }
            }
            return true
        }

        /*
         * Death Plateau in progress.
         */
        if (isQuestInProgress(player!!, "Death Plateau", 21, 24)) {
            openDialogue(player!!, DunstanDialogue(), npc)
            return true
        }

        /*
         * Default.
         */
        when (stage) {
            START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Hi!").also { stage++ }
            1 -> npcl(FacialExpression.FRIENDLY, "Hi! Did you want something?").also { stage++ }
            2 -> showTopics(
                    Topic(FacialExpression.THINKING, "Is it OK if I use your anvil?", 10),
                    Topic(FacialExpression.FRIENDLY, "Nothing, thanks.", END_DIALOGUE)
            )
            10 -> npcl(FacialExpression.FRIENDLY, "So you're a smithy are you?").also { stage++ }
            11 -> playerl(FacialExpression.FRIENDLY, "I dabble.").also { stage++ }
            12 -> npcl(FacialExpression.FRIENDLY, "A fellow smith is welcome to use my anvil!").also { stage++ }
            13 -> playerl(FacialExpression.FRIENDLY, "Thanks!").also { stage++ }
            14 -> npcl(FacialExpression.FRIENDLY, "Anything else before I get on with my work?").also { stage = 2 }
        }
        return true
    }


    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DUNSTAN_1082)
    }
}
