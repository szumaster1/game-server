package content.dialogue

import cfg.consts.Items
import cfg.consts.NPCs
import core.api.inEquipment
import core.api.inInventory
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Elf tracker dialogue.
 */
@Initializable
class ElfTrackerDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        var tracksFound = false
        if (!tracksFound) {
            player(FacialExpression.FRIENDLY, "Hello.")
        } else {
            playerl(FacialExpression.NEUTRAL, "I've found tracks leading off to the west. But they trail off into the trees. Beyond that I am unable to follow.").also { stage = 30 }
        }

        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.SUSPICIOUS, "Human! You must be one of Tyras's men...").also { stage++ }
            1 -> playerl(FacialExpression.HALF_GUILTY, "No, I'm " + player.name + "! Lord Iorwerth said you might be able to help me.").also { stage++ }
            2 -> npc(FacialExpression.SUSPICIOUS, "And you have something to prove this?").also { stage++ }
            3 -> {
                if (inInventory(player, Items.CRYSTAL_PENDANT_3208, 1) || inEquipment(player, Items.CRYSTAL_PENDANT_3208, 1)) {
                    sendDialogue("You show the tracker the crystal pendant.").also { stage = 20 }
                } else {
                    player(FacialExpression.HALF_GUILTY, "Well... Err... No.").also { stage = 10 }
                }
            }
            10 -> npcl(FacialExpression.ANNOYED, "As I was saying... I have no time for brigands or outlaws.").also { stage = END_DIALOGUE }
            20 -> npcl(FacialExpression.FRIENDLY, "That is Lord Iorwerth's pendant. He must have a lot of faith in you. Now, what is it I can help you with?").also { stage++ }
            21 -> playerl(FacialExpression.ASKING, "I need to find Tyras and kill him. Do you know where his camp is?").also { stage++ }
            22 -> npcl(FacialExpression.FRIENDLY, "Well this was his old camp. After the battle a few days ago they moved. We're yet to find them again.").also { stage++ }
            23 -> player(FacialExpression.ASKING, "Can I help at all?").also { stage++ }
            24 -> npcl(FacialExpression.FRIENDLY, "As it goes I'm not actually tracking them at the moment. I'm currently trying to trace our renegade brethen instead. This here is the best lead we've found so far.").also { stage++ }
            25 -> player(FacialExpression.HALF_ASKING, "What is?").also { stage++ }
            26 -> npc(FacialExpression.THINKING, "Ahh I guess you can't see it with those human eyes.").also { stage++ }
            27 -> npcl(FacialExpression.THINKING, "I tell you what. Now that you're here I may as well give you a hand. I'll search here on the east side. You check out the west end of the camp.").also { stage++ }
            28 -> npc(FacialExpression.THINKING, "Come and tell me if you find anything.").also { stage = END_DIALOGUE }
            30 -> npcl(FacialExpression.THINKING, "These forests aren't always as dense as you'd think. If you look closer, you might see ways that you can get through. With that in mind, why don't you give it another go?").also { stage++ }
            31 -> player(FacialExpression.ANNOYED, "Thanks... I'll see what I can find.").also { stage = END_DIALOGUE }
            40 -> player(FacialExpression.FRIENDLY, "Hello.").also { stage++ }
            41 -> npc(FacialExpression.FRIENDLY, "How goes the hunt for that bandit camp?").also { stage++ }
            42 -> {
                var campFound = false
                if (!campFound) {
                    player(FacialExpression.FRIENDLY, "Getting there.").also { stage = END_DIALOGUE }
                } else {
                    player(FacialExpression.NEUTRAL, "I found it a short distance west of here.").also { stage++ }
                }
            }
            43 -> npcl(FacialExpression.FRIENDLY, "I'm sure Lord Iorwerth will be pleased to hear that. You should let him know.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ELF_TRACKER_1199)
    }

}
