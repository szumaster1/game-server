package content.global.random.event.pinball

import core.api.*
import core.api.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.BLUE

/**
 * Represents the Old Man dialogue.
 */
class OldManDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        lock(player!!, 100)
        lockInteractions(player!!, 100)
        npc = NPC(NPCs.MYSTERIOUS_OLD_MAN_410)
        when (stage) {
            0 -> npc("The rules of the game are quite simple. You have to", "score 10 points by tagging the flashing pillars.").also { stage++ }
            1 -> npc("Don't tag the ones that do not have rings around the", "base, as that will reset your points, and don't try and", "get past those trolls until you are done!").also { stage++ }
            2 -> npc("See you later!").also { stage++ }
            3 -> playerl(FacialExpression.SCARED, "Wait, I didn't ask to play this game!").also { stage++ }
            4 -> {
                end()
                PinballUtils.generateTag(player!!)
                sendUnclosableDialogue(player!!, true, "", "Tag the post with the " + BLUE + "flashing rings" + ".")
                runTask(player!!, 2) {
                    unlock(player!!)
                    poofClear(findNPC(NPCs.MYSTERIOUS_OLD_MAN_410)!!)
                }
            }
        }
    }
}
