package content.region.misthalin.edgeville.dialogue

import org.rs.consts.Items
import org.rs.consts.NPCs
import core.api.inEquipment
import core.api.isDiaryComplete
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.world.GameWorld
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Jeffery dialogue.
 */
@Initializable
class JefferyDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.HALF_ASKING, "Keep it quick. What do you want?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Who was that love poem for?", "I want to use the furnace.", "Er, nothing.").also { stage++ }
            1 -> when (buttonId) {
                1 -> npcl(FacialExpression.SUSPICIOUS, "It, er, it didn't work out well. I don't want to talk about it! Leave me alone!").also { stage = END_DIALOGUE }
                2 -> if (!GameWorld.settings!!.isMembers) {
                    npc(FacialExpression.ANNOYED, "You want to use my furnace? No one can use my furnace!", "Only I can use my furnace!").also { stage = END_DIALOGUE }
                } else {
                    if (!isDiaryComplete(player, DiaryType.VARROCK, 0)) {
                        npc("You want to use my furnace?").also { stage++ }
                    } else {
                        npc("You seem exceptional enough. Go ahead.").also { stage = 6 }
                    }
                }
                3 -> player("Er, nothing.").also { stage = END_DIALOGUE }
            }
            2 -> npcl(FacialExpression.CALM_TALK, "I only let exceptional people use my furnace. You don't look exceptional to me.").also { stage++ }
            3 -> playerl(FacialExpression.HALF_ASKING, "How do I become exceptional?").also { stage++ }
            4 -> npcl(FacialExpression.NEUTRAL, "Exceptional people have earned exceptional items; earning Varrock armour would impress me.").also { stage++ }
            5 -> player(FacialExpression.HAPPY, "Alright!").also { stage = END_DIALOGUE }
            6 -> npc(FacialExpression.HALF_ASKING,"What can I make here, exactly?").also { stage++ }
            9 -> npcl(FacialExpression.NEUTRAL, "Well, depending on your skill as a blacksmith, you can use this furnace to smelt ore into metal bars.").also { stage++ }
            10 -> playerl(FacialExpression.NEUTRAL, "Oh, I see. What's so special about this furnace, then?").also { stage++ }
            11 -> npcl(FacialExpression.NEUTRAL, "If you smelt at this furnace while wearing your Varrock armour the enchantment on the armour will give you a small chance of smelting two bars instead of one.").also { stage++ }
            12 -> playerl(FacialExpression.HALF_ASKING, "I see. So, which metal will I be able to obtain more of when smelting with the armour I'm wearing?").also { stage++ }
            13 -> if (!inEquipment(player, Items.VARROCK_ARMOUR_1_11756)) {
                npcl(FacialExpression.NEUTRAL, "Try coming back when you're actually wearing your Varrock armour.").also { stage++ }
            } else {
                npcl(FacialExpression.CALM_TALK, "While wearing the Varrock armour, you will have a chance of smelting an extra bar of any metal up to, and including, adamant.").also { stage = 15 }
            }
            14 -> playerl(FacialExpression.NOD_YES, "Oh, right. Yes, I see. Okay, thanks.").also { stage = END_DIALOGUE }
            15 -> playerl(FacialExpression.FRIENDLY, "Oh, that's useful. That should save me a fair bit of time. Thanks very much.").also { stage++ }
            16 -> npc("Stay exceptional!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.JEFFERY_6298)
    }

}
