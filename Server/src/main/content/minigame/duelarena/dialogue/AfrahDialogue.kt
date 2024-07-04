package content.minigame.duelarena.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class AfrahDialogue(player: Player? = null) : Dialogue(player) {
    private val conversations = arrayOf(0, 4, 10, 11, 15, 17, 20, 22, 23, 24, 29, 32)

    override fun open(vararg args: Any): Boolean {
        player(FacialExpression.ASKING, "Hi!")
        stage = conversations.random()
        npc = args[0] as NPC
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.ASKING, "Ooh. This is exciting!").also { stage++ }
            1 -> player(FacialExpression.ASKING, "Yup!").also { stage = END_DIALOGUE }
            2 -> npc(FacialExpression.ASKING, "I wouldn't want to be the poor guy that has to", "clean up after the duels.").also { stage++ }
            3 -> player(FacialExpression.ASKING, "Me neither.").also { stage = END_DIALOGUE }
            4 -> npc(FacialExpression.ASKING, "My son just won his first duel!").also { stage++ }
            5 -> player(FacialExpression.ASKING, "Congratulations!").also { stage++ }
            6 -> npc(FacialExpression.ASKING, "He ripped his opponent in half!").also { stage++ }
            7 -> player(FacialExpression.ASKING, "That's gotta hurt!").also { stage++ }
            8 -> npc(FacialExpression.ASKING, "He's only 10 as well!").also { stage++ }
            9 -> player(FacialExpression.ASKING, "You gotta start 'em young!").also { stage = END_DIALOGUE }
            10 -> npc(FacialExpression.ASKING, "Hmph.").also { stage = END_DIALOGUE }
            11 -> npc(FacialExpression.ASKING, "My favourite fighter is Mubariz!").also { stage++ }
            12 -> player(FacialExpression.ASKING, "The guy at the information kiosk?").also { stage++ }
            13 -> npc(FacialExpression.ASKING, "Yeah! He rocks!").also { stage++ }
            14 -> player(FacialExpression.ASKING, "Takes all sorts, I guess.").also { stage = END_DIALOGUE }
            15 -> npc(FacialExpression.ASKING, "Hi! I'm here to watch the duels!").also { stage++ }
            16 -> player(FacialExpression.ASKING, "Me too!").also { stage = END_DIALOGUE }
            17 -> npc(FacialExpression.ASKING, "Did you know they think this place dates", "back to the second age?!")
            18 -> player(FacialExpression.ASKING, "Really?").also { stage++ }
            19 -> npc(FacialExpression.ASKING, "Yeah. The guy at the information kiosk was telling me.").also { stage = END_DIALOGUE }
            20 -> npc(FacialExpression.ANGRY, "Can't you see I'm watching the duels?").also { stage++ }
            21 -> player(FacialExpression.SAD, "I'm sorry!").also { stage = END_DIALOGUE }
            22 -> npc(FacialExpression.ASKING, "Well. This beats doing the shopping!").also { stage = END_DIALOGUE }
            23 -> npc(FacialExpression.ASKING, "Hi!").also { stage = END_DIALOGUE }
            24 -> npc(FacialExpression.ASKING, "Knock knock!").also { stage++ }
            25 -> player(FacialExpression.ASKING, "Who's there?").also { stage++ }
            26 -> npc(FacialExpression.ASKING, "Boo!").also { stage++ }
            27 -> player(FacialExpression.ASKING, "Boo who?").also { stage++ }
            28 -> npc(FacialExpression.LAUGH, "Don't cry, it's just me!").also { stage = END_DIALOGUE }
            29 -> npc(FacialExpression.ASKING, "Why did the skeleton burp?").also { stage++ }
            30 -> player(FacialExpression.ASKING, "I don't know?").also { stage++ }
            31 -> npc(FacialExpression.ASKING, "'Cause it didn't have the guts to fart!").also { stage = END_DIALOGUE }
            32 -> npc(FacialExpression.ASKING, "Waaaaassssssuuuuupp?!.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.AFRAH_968)
    }
}
