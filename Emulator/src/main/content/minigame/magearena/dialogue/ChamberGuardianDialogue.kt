package content.minigame.magearena.dialogue

import content.data.GodType
import org.rs.consts.NPCs
import core.api.hasSpaceFor
import core.api.openNpcShop
import core.api.sendItemDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Chamber Guardian dialogue.
 */
@Initializable
class ChamberGuardianDialogue(player: Player? = null) : Dialogue(player) {

    private var godType: GodType? = null

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (player.getSavedData().activityData.hasRecievedKolodionReward()) {
            player(FacialExpression.FRIENDLY,"Hello again.")
            return true
        } else if (player.getSavedData().activityData.hasKilledKolodion()) {
            npc(FacialExpression.FRIENDLY, "Hello adventurer, have you made your choice?")
            return true
        }
        npc(FacialExpression.ANNOYED,"YOU SHOULD NOT BE IN HERE!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (player.getSavedData().activityData.hasRecievedKolodionReward()) {
            when (stage) {
                0 -> npc(FacialExpression.FRIENDLY, "Hello adventurer, are you looking for another staff?").also { stage++ }
                1 -> options("What do you have to offer?", "No thanks.").also { stage++ }
                2 -> when (buttonId) {
                    1 -> end().also { openNpcShop(player, NPCs.CHAMBER_GUARDIAN_904) }
                    2 -> player(FacialExpression.NEUTRAL,"No thanks.").also { stage++ }
                }
                3 -> npc(FacialExpression.FRIENDLY, "Well let me know if you need one.").also { stage = END_DIALOGUE }
            }
            return true
        } else if (player.getSavedData().activityData.hasKilledKolodion()) {
            when (stage) {
                0 -> {
                    godType = GodType.getCape(player)
                    if (godType == null) {
                        player(FacialExpression.HALF_GUILTY,"Sorry, I'm still looking.").also { stage = END_DIALOGUE }
                    } else {
                        player(FacialExpression.NEUTRAL,"I have.").also { stage += 2 }
                    }
                }
                2 -> npc(FacialExpression.FRIENDLY, "Good, good, I hope you have chosen well. I will now", "present you with a magic staff. This, along with the", "cape awarded to you by your chosen god, are all the", "weapons and armour you will need here.").also { stage++ }
                3 -> {
                    end()
                    if (!hasSpaceFor(player, godType!!.staff)) {
                        player(FacialExpression.HALF_GUILTY, "Sorry, I don't have enough inventory space.").also { stage = 1 }
                        return true
                    }
                    if (player.inventory.containsItem(godType!!.cape) || player.equipment.containsItem(godType!!.cape)) {
                        player.inventory.add(godType!!.staff)
                        player.getSavedData().activityData.kolodionStage = 3
                        sendItemDialogue(player, godType!!.staff, "The guardian hands you an ornate magic staff.")
                    }
                }
            }
            return true
        } else {
            end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CHAMBER_GUARDIAN_904)
    }
}
