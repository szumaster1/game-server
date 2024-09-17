package content.global.dialogue

import org.rs.consts.Items
import org.rs.consts.NPCs
import core.api.setAttribute
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.RandomFunction

/**
 * Represents the Man dialogue.
 */
@Initializable
class ManDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (npc == null) return false
        if ((args.size > 1 && args[1] is Item) && (args[1] as Item) == Item(Items.CIDER_5763) && player.inventory.remove(Item(Items.CIDER_5763))) {
            // Seers achievement diary
            if (!player.achievementDiaryManager.getDiary(DiaryType.SEERS_VILLAGE)!!.isComplete(0, 6)) {
                if (player.getAttribute("diary:seers:pub-cider", 0) >= 4) {
                    setAttribute(player, "/save:diary:seers:pub-cider", 5)
                    player.achievementDiaryManager.getDiary(DiaryType.SEERS_VILLAGE)!!.updateTask(player, 0, 6, true)
                } else {
                    setAttribute(player, "/save:diary:seers:pub-cider", player.getAttribute("diary:seers:pub-cider", 0) + 1)
                }
            }
            npc("Ah, a glass of cider, that's very generous of you. I", "don't mind if I do. Thanks!").also { stage = END_DIALOGUE }
            return true
        }
        player(FacialExpression.HALF_GUILTY, "Hello, how's it going?")
        stage = RandomFunction.random(0, 5)
        if (stage == 1) {
            stage = 0
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "I'm very well thank you.").also { stage = END_DIALOGUE }
            2 -> npc(FacialExpression.HALF_GUILTY, "Who are you?").also { stage = 6 }
            3 -> npc(FacialExpression.HALF_GUILTY, "I'm fine, how are you?").also { stage = 8 }
            4 -> npc(FacialExpression.HALF_GUILTY, "No, I don't want to buy anything!").also { stage = END_DIALOGUE }
            5 -> npc(FacialExpression.HALF_GUILTY, "I think we need a new king. The one we've got isn't", "very good.").also { stage = END_DIALOGUE }
            6 -> player(FacialExpression.HALF_GUILTY, "I'm a bold adventurer.").also { stage++ }
            7 -> npc(FacialExpression.HALF_GUILTY, "Ah, a very noble profession.").also { stage = END_DIALOGUE }
            8 -> player(FacialExpression.HALF_GUILTY, "Very well thank you.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MAN_1, NPCs.MAN_2, NPCs.MAN_3, NPCs.WOMAN_4, NPCs.WOMAN_5, NPCs.WOMAN_6, NPCs.MAN_16, NPCs.MAN_24, NPCs.WOMAN_25, NPCs.MAN_170, NPCs.MAN_351, NPCs.WOMAN_352, NPCs.WOMAN_353, NPCs.WOMAN_354, NPCs.MAN_359, NPCs.WOMAN_360, NPCs.WOMAN_361, NPCs.WOMAN_362, NPCs.WOMAN_363, NPCs.MAN_726, NPCs.MAN_727, NPCs.MAN_728, NPCs.MAN_729, NPCs.MAN_730, NPCs.MAN_1086, NPCs.MAN_2675, NPCs.WOMAN_2776, NPCs.MAN_3224, NPCs.MAN_3225, NPCs.WOMAN_3227, NPCs.MAN_5923, NPCs.WOMAN_5924)
    }
}
