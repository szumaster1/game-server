package content.region.misthalin.dialogue.varrock

import core.api.consts.NPCs
import core.api.isQuestComplete
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.dialogue.IfTopic
import core.game.dialogue.Topic
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Scavvo dialogue.
 */
@Initializable
class ScavvoDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: Merchant that runs Scavvo's Rune Store
     * on the 1st floor in the Champions' Guild.
     * Location: 3192,3355,1
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "'Ello matey! D'ya wanna buy some exiting new toys?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> showTopics(
                Topic("No - toys are for kids.", END_DIALOGUE),
                Topic("Let's have a look, then.", 6),
                Topic(FacialExpression.HAPPY, "Ooh, goody-goody - toys!", 6),
                IfTopic(FacialExpression.HALF_ASKING, "Why do you sell most rune armour but not platebodies?", 5, !isQuestComplete(player, "Dragon Slayer"))
            )
            5 -> npcl(FacialExpression.FRIENDLY, "Oh, you have to complete a special quest in order to wear rune platebodies. You should talk to the guild master downstairs about that").also { stage = END_DIALOGUE }
            6 -> {
                end()
                openNpcShop(player, NPCs.SCAVVO_537)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SCAVVO_537)
    }

}
