package content.global.skill.combat.summoning.familiar.dialogue

import core.api.consts.Items
import core.api.consts.NPCs
import core.api.inInventory
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class FireGiantDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (inInventory(player!!, Items.TINDERBOX_590)) {
            npcl(FacialExpression.CHILD_NORMAL, "Relight my fire.").also { stage = 0 }
            return true
        }
        when ((0..4).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Pick flax.").also { stage = 8 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "You're fanning my flame with your wind spells.").also { stage = 12 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "I'm burning up.").also { stage = 14 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "It's raining flame!").also { stage = 17 }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "Let's go fireside.").also { stage = 20 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when(stage) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "A tinderbox is my only desire.").also { stage++ }
            1 -> playerl(FacialExpression.HALF_ASKING, "What are you singing?").also { stage++ }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Just a song I heard a while ago.").also { stage++ }
            3 -> playerl(FacialExpression.HALF_ASKING, "It's not very good.").also { stage++ }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "You're just jealous of my singing voice.").also { stage++ }
            5 -> playerl(FacialExpression.HALF_ASKING, "Where did you hear this again?").also { stage++ }
            6 -> npcl(FacialExpression.CHILD_NORMAL, "Oh, you know, just with some other fire titans. Out for a night on the pyres.").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "Hmm. Come on then. We have stuff to do.").also { stage = END_DIALOGUE }

            8 -> npcl(FacialExpression.CHILD_NORMAL, "Jump to it.").also { stage++ }
            9 -> npcl(FacialExpression.CHILD_NORMAL, "If you want to get to fletching level 99.").also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "That song...is terrible.").also { stage++ }
            11 -> npcl(FacialExpression.CHILD_NORMAL, "Sorry.").also { stage = END_DIALOGUE }

            12 -> npcl(FacialExpression.CHILD_NORMAL, "I'm singeing the curtains with my heat.").also { stage++ }
            13 -> playerl(FacialExpression.FRIENDLY, "Oooh, very mellow.").also { stage = END_DIALOGUE }

            14 -> npcl(FacialExpression.CHILD_NORMAL, "I want the world to know.").also { stage++ }
            15 -> npcl(FacialExpression.CHILD_NORMAL, "I got to let it show.").also { stage++ }
            16 -> playerl(FacialExpression.FRIENDLY, "Catchy.").also { stage = END_DIALOGUE }

            17 -> npcl(FacialExpression.CHILD_NORMAL, "Huzzah!").also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "You have a...powerful voice.").also { stage++ }
            19 -> npcl(FacialExpression.CHILD_NORMAL, "Thanks.").also { stage = END_DIALOGUE }

            20 -> npcl(FacialExpression.CHILD_NORMAL, "I think I've roasted the sofa.").also { stage++ }
            21 -> npcl(FacialExpression.CHILD_NORMAL, "I think I've burnt down the hall.").also { stage++ }
            22 -> playerl(FacialExpression.HALF_ASKING, "Can't you sing quietly?").also { stage++ }
            23 -> npcl(FacialExpression.CHILD_NORMAL, "Sorry.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FIRE_GIANT_7003, NPCs.FIRE_GIANT_7004)
    }

}
