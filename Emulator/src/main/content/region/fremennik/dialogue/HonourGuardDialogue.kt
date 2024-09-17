package content.region.fremennik.dialogue

import org.rs.consts.Items
import org.rs.consts.NPCs
import core.api.*
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.zone.ZoneBorders
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Honour guard dialogue.
 */
@Initializable
class HonourGuardDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (anyInEquipment(player, Items.SILLY_JESTER_HAT_10836, Items.SILLY_JESTER_TOP_10837, Items.SILLY_JESTER_TIGHTS_10838, Items.SILLY_JESTER_BOOTS_10839)) {
            if (player.location.z == 1) {
                npcl(FacialExpression.NEUTRAL, "Hey jester, this area is not a suitable place for civilians to wander about in. Trolls everywhere.").also { stage = 12 }
            } else {
                npc("Good day, jester! Got any new jokes?").also { stage = 6 }
            }
            return true
        }
        if (player.location.z == 1) {
            npc("Hail, ${getAttribute(player, "fremennikname", "name")}!").also { stage = 8 }
            return true
        }
        if (npc.id == 5515 && inBorders(player, ZoneBorders(2328, 3802, 2309, 3824))) {
            npc("Hail, ${getAttribute(player, "fremennikname", "name")}!").also { stage = 12 }
            return true
        }
        if (inBorders(player, ZoneBorders(2342, 3795, 2329, 3811))) {
            npc("Hail, ${getAttribute(player, "fremennikname", "name")}!").also { stage = 1 }
            return true
        }
        if (getQuestStage(player, "Fremennik Isles") in 1..100) {
            npc("Can't talk now, I'm on duty.").also { stage = END_DIALOGUE }
        } else {
            npc("Hail, ${getAttribute(player, "fremennikname", "name")}!").also { stage = 1 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
             1 -> player("Good day! How are you doing?").also { stage++ }
             2 -> npc("Pretty good. It's nice to have a day off from troll-","killing duty.").also { stage++ }
             3 -> player("Have you killed many trolls?").also { stage++ }
             4 -> npc("Of course. It's all part of growing up around here. You", "get used to it.").also { stage++ }
             5 -> player("See you later.").also { stage = END_DIALOGUE }
             6 -> playerl(FacialExpression.NOD_NO, "No. I only use old, recycled jokes. It's better for the environment.").also { stage++ }
             7 -> npc(FacialExpression.LAUGH, "HaHa!").also { stage = END_DIALOGUE }
             8 -> player("Good day!").also { stage++ }
             9 -> npc("Are you here to help with the trolls?").also { stage++ }
            10 -> player("I'm afraid not, I have other things to do.").also { stage++ }
            11 -> npcl(FacialExpression.NEUTRAL, "Well, watch yourself. Those rocks that the trolls throw can take your arm off.").also { stage = END_DIALOGUE }
            12 -> player("Good day!").also { stage++ }
            13 -> npcl(FacialExpression.NEUTRAL, "I hope you're not heading north. There are lots of trolls, very dangerous.").also { stage++ }
            14 -> playerl(FacialExpression.FRIENDLY, " Thanks for the warning. I'll keep my eyes peeled.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HONOUR_GUARD_5514, NPCs.HONOUR_GUARD_5515)
    }
}