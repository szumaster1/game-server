package content.region.kandarin.gnomestronghold.dialogue

import content.region.kandarin.quest.grandtree.dialogue.FemiDialogueFile
import org.rs.consts.NPCs
import core.api.face
import core.api.findLocalNPC
import core.api.openDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC

/**
 * Represents the Gnome gate guard dialogue.
 */
class GnomeGateGuardDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.GNOME_GUARD_163)
        when (stage) {
            0 -> npcl(FacialExpression.OLD_DEFAULT, "I'm afraid that we have orders not to let you in.").also { stage++ }
            1 -> player(FacialExpression.HALF_GUILTY, "Orders from who?").also { stage++ }
            2 -> npcl(FacialExpression.OLD_DEFAULT, "The head tree guardian, he say's you're a spy!").also { stage++ }
            3 -> player(FacialExpression.HALF_GUILTY, "Glough!").also { stage++ }
            4 -> npc(FacialExpression.OLD_DEFAULT, "I'm sorry but you'll have to leave.").also { stage++ }
            5 -> {
                end()
                findLocalNPC(player!!, NPCs.FEMI_676)?.let {
                    face(player!!, it, 2)
                    openDialogue(player!!, FemiDialogueFile())
                }
            }
        }
    }
}