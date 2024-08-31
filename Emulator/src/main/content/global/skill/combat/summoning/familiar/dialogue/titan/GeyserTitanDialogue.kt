package content.global.skill.combat.summoning.familiar.dialogue.titan

import core.api.amountInInventory
import cfg.consts.Items
import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.GameWorld
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Geyser titan dialogue.
 */
@Initializable
class GeyserTitanDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (amountInInventory(player!!, Items.SHARK_385) < 5) {
            npcl(FacialExpression.CHILD_NORMAL, "Hey mate, how are you?").also { stage = 0 }
            return true
        }
        when ((0..6).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Over the course of their lifetime a shark may grow and use 20,000 teeth.").also { stage = 3 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Did you know a snail can sleep up to three years?").also { stage = 4 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Unlike most animals, both the shark's upper and lower jaws move when they bite.").also { stage = 5 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Did you know that in one feeding a mosquito can absorb one-and-a-half times its own body weight in blood?").also { stage = 7 }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "Did you know that sharks have the most powerful jaws of any animal on the planet?").also { stage = 8 }
            5 -> npcl(FacialExpression.CHILD_NORMAL, "Did you know that ${GameWorld.settings!!.name} gets 100 tons heavier every day, due to dust falling from space?").also { stage = 10 }
            6 -> npcl(FacialExpression.CHILD_NORMAL, "Did you know that sharks normally eat alone?").also { stage = 11 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Not so bad.").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Did you know that during the average human life-span the heart will beat approximately 2.5 billion times?").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Wow, that is a lot of non-stop work!").also { stage = END_DIALOGUE }

            3 -> playerl(FacialExpression.HALF_ASKING, "Wow! That is a whole load of teeth. I wonder what the Tooth Fairy would give for those?").also { stage = END_DIALOGUE }

            4 -> playerl(FacialExpression.FRIENDLY, "I wish I could do that. Ah...sleep.").also { stage = END_DIALOGUE }

            5 -> playerl(FacialExpression.HALF_ASKING, "Really?").also { stage++ }
            6 -> npcl(FacialExpression.CHILD_NORMAL, "Yup. Chomp chomp.").also { stage = END_DIALOGUE }

            7 -> playerl(FacialExpression.FRIENDLY, "Eugh.").also { stage = END_DIALOGUE }

            8 -> playerl(FacialExpression.FRIENDLY, "No, I didn't.").also { stage++ }
            9 -> npcl(FacialExpression.CHILD_NORMAL, "Full of facts, me.").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.FRIENDLY, "What a fascinating fact.").also { stage = END_DIALOGUE }

            11 -> playerl(FacialExpression.FRIENDLY, "I see.").also { stage++ }
            12 -> npcl(FacialExpression.CHILD_NORMAL, "Sometimes one feeding shark attracts others and they all try and get a piece of the prey.").also { stage++ }
            13 -> npcl(FacialExpression.CHILD_NORMAL, "They take a bite at anything in their way and may even bite each other!").also { stage++ }
            14 -> playerl(FacialExpression.FRIENDLY, "Ouch!").also { stage = END_DIALOGUE }

        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GEYSER_TITAN_7339, NPCs.GEYSER_TITAN_7340)
    }

}
