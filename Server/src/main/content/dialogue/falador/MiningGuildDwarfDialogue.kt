package content.dialogue.falador

import cfg.consts.NPCs
import core.api.getStatLevel
import core.api.sendDialogueOptions
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Mining guild dwarf dialogue.
 */
@Initializable
class MiningGuildDwarfDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_NORMAL, "Welcome to the Mining Guild.", "Can I help you with anything?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendDialogueOptions(player, "What would you like to say?", "What have you got in the Guild?", "What do you dwarves do with the ore you mine?", "No thanks, I'm fine.").also { stage++ }
            1 -> when (buttonId) {
                1 -> playerl(FacialExpression.ASKING, "What have you got in the Guild?").also { stage++ }
                2 -> playerl(FacialExpression.ASKING, "What do you dwarves do with the ore you mine?").also { stage = 5 }
                3 -> playerl(FacialExpression.FRIENDLY, "No thanks, I'm fine.").also { stage = END_DIALOGUE }
            }
            2 -> npc(FacialExpression.OLD_HAPPY, "Ooh, it's WONDEFRFUL! There are lots of coal rocks,", "and even a few mithril rocks in the guild,", "all exclusively for people with at least level 60 mining", "There's no better mining site anywhere near here.").also { stage++ }
            3 -> playerl(FacialExpression.HALF_ASKING, "So you won't let me go in there?").also { stage++ }
            4 -> {
                if (getStatLevel(player, Skills.MINING) >= 60) {
                    npc(FacialExpression.OLD_NORMAL, "Yes, you can enter if you wish.").also { stage = END_DIALOGUE }
                } else {
                    npc(FacialExpression.OLD_NORMAL, "Sorry, but the rules are rules. Do some more training", "first.").also { stage = END_DIALOGUE }
                }
            }
            5 -> npc(FacialExpression.OLD_NORMAL, "What do you think? We smelt it into bars, smith the", "metal to make armour and weapons, then we exchange", "them for goods and services.").also { stage++ }
            6 -> playerl(FacialExpression.NEUTRAL, "I don't see many dwarves here.").also { stage++ }
            7 -> npc(FacialExpression.OLD_NORMAL, "No, this is only a mining outpost. We dwarves don't", "much like to settle in human cities. Most of the ore is", "carted off to Keldagrim, the great dwarven city - They've", "got a special blast furnace up there - it makes").also { stage++ }
            8 -> npc(FacialExpression.OLD_NORMAL, "smelting the ore so much easier. There are plenty of", "dwarven traders working in Keldagrim.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DWARF_382)
    }
}
