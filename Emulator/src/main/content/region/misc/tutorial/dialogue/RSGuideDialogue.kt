package content.region.misc.tutorial.dialogue

import content.region.misc.tutorial.handlers.TutorialStage
import cfg.consts.NPCs
import core.api.sendUnclosableDialogue
import core.api.setAttribute
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Guide dialogue.
 */
@Initializable
class RSGuideDialogue(private val player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        val tutStage = player?.getAttribute("tutorial:stage", 0) ?: 0
        val greetingMessage = when {
            tutStage < 2 -> "Greetings! Please follow the onscreen instructions!"
            tutStage == 2 -> "Greetings! I see you are a new arrival to this land. My job is to welcome all new visitors. So welcome!"
            else -> "Please follow the onscreen instructions!"
        }
        sendUnclosableDialogue(player, false, *greetingMessage.split(" ").toTypedArray()).also { stage = END_DIALOGUE }
        return tutStage >= 2
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val messages = listOf(
            "You have already learned the first thing needed to succeed in this world: talking to other people!",
            "You will find many inhabitants of this world have useful things to say to you. By clicking on them with your mouse you can talk to them.",
            "I would also suggest reading through some of the supporting information on the website. There you can find the starter guides, which contain all the additional information you're ever likely to need. They also contain helpful tips to help you on your journey.",
            "To continue the tutorial go through that door over there and speak to your first instructor!"
        )
        if (stage < messages.size) {
            sendUnclosableDialogue(player, false, *messages[stage].split(" ").toTypedArray()).also { stage++ }
        } else {
            end()
            setAttribute(player, "tutorial:stage", 3)
            TutorialStage.load(player, 3)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.RUNESCAPE_GUIDE_945)
    }
}