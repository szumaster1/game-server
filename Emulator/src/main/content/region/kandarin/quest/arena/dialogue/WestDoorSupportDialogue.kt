package content.region.kandarin.quest.arena.dialogue

import core.api.*
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.global.action.DoorActionHandler
import core.game.node.entity.npc.NPC
import org.rs.consts.NPCs
import org.rs.consts.QuestName

/**
 * Represents the West door support dialogue.
 */
class WestDoorSupportDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.KHAZARD_GUARD_257)
        when (stage) {
            0 -> {
                face(player!!, location(2585, 3141, 0))
                playerl(FacialExpression.NEUTRAL, "This door appears to be locked.").also { stage++ }
            }

            1 -> {
                face(player!!, location(2603, 3155, 0))
                npcl(FacialExpression.NEUTRAL, "Nice observation guard. You could have just asked to be let in like a normal person.").also { stage++ }
            }

            2 -> {
                end()
                lock(player!!, 2)
                setQuestStage(player!!, QuestName.FIGHT_ARENA, 20)
                DoorActionHandler.handleAutowalkDoor(player!!, getScenery(2584, 3141, 0)!!)
            }
        }
    }
}