package content.region.misc.dialogue.keldagrim

import cfg.consts.NPCs
import core.api.findNPC
import core.api.sendChat
import core.api.sendNPCDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Hegir dialogue.
 */
@Initializable
class HegirDialogue(player: Player? = null) : Dialogue(player) {

    private val forceChat = arrayOf(
        "No need to shout!",
        "Calm down dear.",
        "I don't see why.",
        "Yes you did!",
        "I did not!",
        "What did you do that for?"
    )

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_ASKING, "Hello, can I ask you a question?")
        stage = 1
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            1 -> sendNPCDialogue(player, NPCs.HAERA_2189, "Oh yes, young fellow? Yes, what do you want?", FacialExpression.OLD_NORMAL).also { stage++ }
            2 -> sendNPCDialogue(player, NPCs.HAERA_2189, "Who's that you're talking to Hegir?", FacialExpression.OLD_NORMAL).also { stage++ }
            3 -> npc(FacialExpression.OLD_NORMAL, "Let me see, he appears to be a human of some sort.").also { stage++ }
            4 -> npc(FacialExpression.OLD_NORMAL, "What was your name again, human?").also { stage++ }
            5 -> player("${player.username}.").also { stage++ }
            6 -> npc(FacialExpression.OLD_NORMAL, "It's ${player.username}!").also { stage++ }
            7 -> sendNPCDialogue(player, NPCs.HAERA_2189, "Well ask him what he wants then!", FacialExpression.OLD_NORMAL).also { stage++ }
            8 -> npc(FacialExpression.OLD_NORMAL, "What do you want, human?").also { stage++ }
            9 -> player("Errr... I don't know... A quest, maybe?").also { stage++ }
            10 -> npc(FacialExpression.OLD_NORMAL, "He says he's looking for his quest!").also { stage++ }
            11 -> sendNPCDialogue(player, NPCs.HAERA_2189, "What makes him think he lost his quest in here?", FacialExpression.OLD_NORMAL).also { stage++ }
            12 -> npc(FacialExpression.OLD_NORMAL, "What makes you think you lost your quest in here?").also { stage++ }
            13 -> player("No no, I mean, do you have a task of", "some sort for me?").also { stage++ }
            14 -> npc(FacialExpression.OLD_NORMAL, "Now he says he wants a task!").also { stage++ }
            15 -> sendNPCDialogue(player, NPCs.HAERA_2189, "Stop talking to that human and get back in here before I have a task for you!", FacialExpression.OLD_NORMAL).also { stage++ }
            16 -> npc(FacialExpression.OLD_NORMAL, "No need to talk to me like that, Haera.").also { stage++ }
            17 -> sendNPCDialogue(player, NPCs.HAERA_2189, "I'll talk to you in whatever way I like!", FacialExpression.OLD_NORMAL).also { stage++ }
            18 -> player("I think I hear someone calling my name", "in the distance...").also { stage++ }
            19 -> {
                end()
                sendChat(findNPC(NPCs.HEGIR_2188)!!, forceChat.random())
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HEGIR_2188)
    }
}