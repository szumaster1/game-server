package content.dialogue

import cfg.consts.NPCs
import core.api.teleport
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.TeleportManager
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Represents the Zandar horfyre dialogue.
 */
@Initializable
class ZandarHorfyreDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Zandar Horfyre is a dark wizard and teacher
     * located on the top floor of the Dark Wizards' Tower.
     * Location: 2905,3333,2
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_THINKING, "Who are you?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.NEUTRAL, "My name is Zandar Horfyre, and you ${player.name} are trespassing in my tower, not to mention attacking my students! I thank you to leave immediately!").also { stage++ }
            1 -> options("Ok, I was going anyway.", "No, I think I'll stay for a bit.").also { stage++ }
            2 -> when (buttonId) {
                1 -> player("Ok, I was going anyway.").also { stage = 3 }
                2 -> player("No, I think I'll stay for a bit.").also { stage = 4 }
            }
            3 -> npcl(FacialExpression.NEUTRAL, "Good! And don't forget to close the door behind you!").also { stage = 7 }
            4 -> npcl(FacialExpression.ANNOYED, "Actually, that wasn't an invitation. I've tried being polite, now we'll do it the hard way!").also { teleport(player, Location.create(3217, 3177, 0), TeleportManager.TeleportType.INSTANT) }.also { stage++ }
            5 -> player(FacialExpression.ANGRY, "Zamorak curse that mage!").also { stage++ }
            6 -> player(FacialExpression.LAUGH, "Actually, I guess he already has!").also { stage++ }
            7 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ZANDAR_HORFYRE_3308)
    }

}