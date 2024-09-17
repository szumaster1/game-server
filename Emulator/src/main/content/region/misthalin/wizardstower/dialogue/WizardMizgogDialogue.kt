package content.region.misthalin.wizardstower.dialogue

import org.rs.consts.NPCs
import content.region.misthalin.lumbridge.quest.imp.dialogue.WizardMizgogDialogueFile
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Wizard Mizgog dialogue.
 */
@Initializable
class WizardMizgogDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Imp Catcher")) {
            openDialogue(player, WizardMizgogDialogueFile(), npc)
        } else {
            options("Got any more quests?", "Do you know any interesting spells you could teach me?")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> when (buttonId) {
                1 -> player("Got any more quests?").also { stage++ }
                2 -> player("Do you know any interesting spells you could teach me?").also { stage = 2 }
            }
            1 -> npc("No, everything is good with the world today.").also { stage = END_DIALOGUE }
            2 -> npc("I don't think so, the type of magic I study involves", "years of meditation and research.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return WizardMizgogDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.WIZARD_MIZGOG_706)
    }
}
