package content.region.misthalin.dialogue.barbvillage

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class SigurdDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Ha Ha! Hello!").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Who are you?", "Can you teach me about canoeing?").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Who are you?").also { stage++ }
                2 -> npc(FacialExpression.HALF_GUILTY, "It's really quite simple. Just walk down to that", "tree on the water bank and chop it down.").also { stage = 16 }
            }
            2 -> npc(FacialExpression.HALF_GUILTY, "I'm Sigurd the Great and Brainy.").also { stage++ }
            3 -> player(FacialExpression.HALF_GUILTY, "Why do they call you the Great and Brainy?").also { stage++ }
            4 -> npc(FacialExpression.HALF_GUILTY, "Because I Iinvented the Log Canoe!").also { stage++ }
            5 -> player(FacialExpression.HALF_GUILTY, "Log Canoe?").also { stage++ }
            6 -> npc(FacialExpression.HALF_GUILTY, "Yeash! Me and my cousins were having a great", "party by the river when we decided to have a", " game of 'Smack The SeasonDefinitions'").also { stage++ }
            7 -> player(FacialExpression.HALF_GUILTY, "Smack the SeasonDefinitions?").also { stage++ }
            8 -> npc(FacialExpression.HALF_GUILTY, "It's a game were you take it in turnsh shmacking", " a tree. First one to uproot the tree winsh!").also { stage++ }
            9 -> npc(FacialExpression.HALF_GUILTY, "Anyway, I won the game with a flying tackle.", " The tree came loose and down the river bank I went", " still holding the tree.").also { stage++ }
            10 -> npc(FacialExpression.HALF_GUILTY, "I woke up a few hours later and found myself", "several miles down river. and thatsh how I", "invented the log canoe!").also { stage++ }
            11 -> player(FacialExpression.HALF_GUILTY, "So you invented the 'Log Canoe' by falling into a river", "hugging a tree?").also { stage++ }
            12 -> npc(FacialExpression.HALF_GUILTY, "Well I refined the design from the original", "you know!").also { stage++ }
            13 -> npc(FacialExpression.HALF_GUILTY, "I cut all the branches off to make it more", "comfortable. I could tell you how to if you like?").also { stage++ }
            14 -> options("Yes", "No").also { stage++ }
            15 -> when (buttonId) {
                1 -> npc(FacialExpression.HALF_GUILTY, "It's really quite simple. Just walk down to that tree", "on the water bank and chop it down.").also { stage++ }
                2 -> npc(FacialExpression.HALF_GUILTY, "Okay, if you change your mind you know where", "to find me.").also { stage = END_DIALOGUE }
            }
            16 -> npc(FacialExpression.HALF_GUILTY, "Then take your axe to it and shape it how you", "like!").also { stage++ }
            17 -> npc(FacialExpression.HALF_GUILTY, "You look like you know your way around a", "tree, you can you can make many canoes.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return SigurdDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIGURD_3329)
    }

}
