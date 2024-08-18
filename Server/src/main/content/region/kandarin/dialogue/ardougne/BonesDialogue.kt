package content.region.kandarin.dialogue.ardougne

import core.api.consts.Items
import core.api.consts.NPCs
import core.api.inEquipment
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Bones dialogue.
 */
@Initializable
class BonesDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player("Who's a cute little kitty?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                end()
                if(!inEquipment(player, Items.CATSPEAK_AMULET_4677)){
                    npcl(FacialExpression.CHILD_NEUTRAL,"Miaow!")
                } else {
                    npcl(FacialExpression.CHILD_NEUTRAL, "Jimmy doesn't like me talking to strangers.")
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BONES_2945)
    }

}
