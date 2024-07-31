package content.global.handlers.item.withnpc

import core.api.consts.Items
import core.api.consts.NPCs
import core.api.openDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.item.Item
import core.tools.END_DIALOGUE

class ChaliceOnKingArthurListener : InteractionListener {

    private val poisonChalice = Items.POISON_CHALICE_197
    private val kingArthur = NPCs.KING_ARTHUR_251

    override fun defineListeners() {
        onUseWith(IntType.NPC, poisonChalice, kingArthur) { player, _, _ ->
            openDialogue(player, object : DialogueFile() {
                override fun handle(componentID: Int, buttonID: Int) {
                    npc = NPC(NPCs.KING_ARTHUR_251)
                    when (stage) {
                        0 -> npcl(FacialExpression.SAD, "You have chosen poorly.").also {
                            if (!player.achievementDiaryManager.getDiary(DiaryType.SEERS_VILLAGE)!!.isComplete(0, 3)) {
                                player.achievementDiaryManager.getDiary(DiaryType.SEERS_VILLAGE)!!.updateTask(player, 0, 3, true)
                            }
                            stage++
                        }
                        1 -> playerl(FacialExpression.ANNOYED, "Excuse me?").also { stage++ }
                        2 -> npcl(FacialExpression.FRIENDLY, "Sorry, I meant to say 'thank you'. Most refreshing.").also { stage++ }
                        3 -> playerl(FacialExpression.DISGUSTED_HEAD_SHAKE, "Are you sure that stuff is safe to drink?").also { stage++ }
                        4 -> npcl(FacialExpression.HAPPY, "Oh yes, Stankers' creations may be dangerous for those with weak constitutions, but, personally. I find them rather invigorating.").also {
                            player.inventory.remove(Item(poisonChalice, 1))
                            stage = END_DIALOGUE
                        }
                    }
                }
            })
            return@onUseWith true
        }
    }
}
