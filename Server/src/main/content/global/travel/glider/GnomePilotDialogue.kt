package content.global.travel.glider

import core.api.consts.Components
import core.api.consts.NPCs
import core.api.isQuestComplete
import core.api.openInterface
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Gnome pilot dialogue.
 */
@Initializable
class GnomePilotDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_DEFAULT, "What do you want human?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.HALF_GUILTY, "May you fly me somewhere on your glider?").also { stage++ }
            1 -> {
                if (!isQuestComplete(player, "The Grand Tree")) {
                    npc(FacialExpression.OLD_ANGRY3, "I only fly friends of the gnomes!")
                    stage = END_DIALOGUE
                } else {
                    npc(FacialExpression.OLD_DEFAULT, "If you wish.")
                    stage++
                }
            }

            2 -> {
                end()
                player.lock()
                openInterface(player, Components.GLIDERMAP_138)
                GnomeGlider.sendConfig(npc, player)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(
            NPCs.GNORMADIUM_AVLAFRIM_1800,
            NPCs.CAPTAIN_DALBUR_3809,
            NPCs.CAPTAIN_BLEEMADGE_3810,
            NPCs.CAPTAIN_KLEMFOODLE_3812
        )
    }
}
