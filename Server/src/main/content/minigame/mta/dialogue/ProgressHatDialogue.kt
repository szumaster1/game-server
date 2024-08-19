package content.minigame.mta.dialogue

import core.api.consts.NPCs
import core.api.sendDialogueLines
import core.api.sendDialogueOptions
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Progress Hat dialogue.
 */
@Initializable
class ProgressHatDialogue(player: Player? = null) : Dialogue(player) {

    private var progressHat: Item? = null

    override fun open(vararg args: Any): Boolean {
        if (args.size > 1) {
            progressHat = args[0] as Item
            npc(FacialExpression.OLD_NORMAL, "How dare you destroy me? You'll lose your Pizazz", "Points!").also { stage = 50 }
            return true
        }
        player("Mr Progress Hat... sir?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.OLD_NORMAL, "Can't you see I'm busy?").also { stage++ }
            1 -> player("But you're just a hat? Can you tell me my Pizazz", "Point totals?").also { stage++ }
            2 -> npc(FacialExpression.OLD_NORMAL, "Ok, I suppose it's my job. You have:", getPoints(0).toString() + " Telekinetic, " + getPoints(1) + " Alchemist,", getPoints(2).toString() + " Enchantment, and " + getPoints(3) + " Graveyard Pizazz Points.").also { stage++ }
            3 -> player("Thank you!").also { stage = END_DIALOGUE }
            50 -> sendDialogueOptions(player, "Destroy Hat?", "Yes", "No").also { stage++ }
            51 -> when (buttonId) {
                1 -> {
                    if (progressHat == null) {
                        end()
                        return true
                    }
                    if (!player.inventory.containsItem(progressHat)) {
                        end()
                        return true
                    }
                    player.inventory.remove(progressHat)
                    sendDialogueLines(player, "The hat whispers as you destroy it. You can get another from the", "Entrance Guardian.").also { stage = END_DIALOGUE }
                }
                2 -> npc(FacialExpression.OLD_NORMAL, "I think so too!").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    /**
     * Get points
     *
     * @param index
     * @return
     */
    fun getPoints(index: Int): Int {
        return player.getSavedData().activityData.getPizazzPoints(index)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.PIZZAZ_HAT_3096)
    }
}
