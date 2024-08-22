package content.region.kandarin.quest.fightarena.dialogue

import core.api.*
import cfg.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.global.action.DoorActionHandler
import core.game.node.entity.npc.NPC

/**
 * Represents the East door support dialogue.
 */
class EastDoorSupportDialogue : DialogueFile() {
    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.KHAZARD_GUARD_257)
        when (stage) {
            0 -> {
                face(player!!, location(2617, 3170, 0))
                playerl(FacialExpression.NEUTRAL, "This door appears to be locked.").also { stage++ }
            }

            1 -> {
                face(player!!, location(2603, 3155, 0))
                npcl(FacialExpression.NEUTRAL, "Nice observation guard. You could have just asked to be let in like a normal person.").also { stage++ }
            }

            2 -> {
                end()
                lock(player!!, 2)
                setQuestStage(player!!, "Fight Arena", 20)
                DoorActionHandler.handleAutowalkDoor(player!!, getScenery(2617, 3172, 0)!!)
            }
        }
    }
}