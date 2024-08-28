package content.dialogue.falador

import content.region.asgarnia.quest.rd.RecruitmentDrive
import core.api.*
import cfg.consts.Items
import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Make over mage dialogue.
 */
@Initializable
class MakeOverMageDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (args.size == 2) {
            npc(FacialExpression.HALF_ASKING, "Hmm... you didn't feel any unexpected growths", "anywhere around your head just then did you?").also { stage = 600 }
            return true
        }
        npc(FacialExpression.HAPPY, "Hello there! I am known as the make-over mage! I", "have spent many years researching magics that can", "change your physical appearance!").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val hasAnMakeover = hasAnItem(player, Items.YIN_YANG_AMULET_7803).container != null
        when (stage) {
            0 -> npc(FacialExpression.HAPPY, "I can alter your physical form for a small fee of", "only 3000 gold coins! Would you like me to perform my", "magics upon you?").also { stage++ }
            1 -> if (!hasAnMakeover) {
                options("Tell me more about this 'make-over'.", "Sure. Do it.", "No thanks.", "Cool amulet! Can I have one?").also { stage++ }
            } else {
                options("Tell me more about this 'make-over'.", "Sure. Do it.", "No thanks.").also { stage++ }
            }
            2 -> when (buttonId) {
                1 -> player(FacialExpression.HAPPY, "Tell me more about this 'make-over'.").also { stage = 10 }
                2 -> player(FacialExpression.HAPPY, "Sure. Do it.").also { stage = 20 }
                3 -> player(FacialExpression.HAPPY, "No thanks. I'm happy as Saradomin made me.").also { stage = 19 }
                4 -> player(FacialExpression.HAPPY, "Cool amulet! Can I have one?").also { stage = 40 }
            }
            10 -> npc(FacialExpression.HAPPY, "Why, of course! Basically, and I will try and explain", "this so that you will understand it correctly,").also { stage++ }
            11 -> npc(FacialExpression.FRIENDLY, "I use my secret magical technique to melt your body", "down into a puddle of its elements.").also { stage++ }
            12 -> npc(FacialExpression.FRIENDLY, "When I have broken down all trace of your body, I", "then rebuild it into the form I am thinking of.").also { stage++ }
            13 -> npc(FacialExpression.FRIENDLY, "Or, you know, somewhere vaguely close enough", "anyway.").also { stage++ }
            14 -> player(FacialExpression.HALF_GUILTY, "Uh... that doesn't sound particularly safe to me...").also { stage++ }
            15 -> npc(FacialExpression.LAUGH, "It's as safe as houses! Why, I have only had thirty-six", "major accidents this month!").also { stage++ }
            16 -> npc(FacialExpression.ASKING, "So what do you say? Feel like a change?").also { stage++ }
            17 -> options("Sure. Do it.", "No thanks.").also { stage++ }
            18 -> when (buttonId) {
                1 -> player(FacialExpression.HAPPY, "Sure. Do it.").also { stage = 20 }
                2 -> player(FacialExpression.HAPPY, "No thanks. I'm happy as Saradomin made me.").also { stage++ }
            }
            19 -> npc(FacialExpression.HALF_GUILTY, "Ehhh... suit yourself.").also { stage = END_DIALOGUE }
            20 -> npc(FacialExpression.HAPPY, "You of course agree that if by some accident you", "are turned into a frog you have no rights for", "compensation or refund.").also { stage = 25 }
            21 -> npc(FacialExpression.ANGRY, "Well, go get it then. No freebies here!").also { stage = END_DIALOGUE }
            25 -> {
                end()
                if (!player.equipment.isEmpty) {
                    sendDialogueLines(player, "You need to take off all your equipment before the mage", "can change your appearance.").also { stage = END_DIALOGUE }
                } else {
                    end()
                    if (getQuestStage(player, "Recruitment Drive") >= 1 && player.isMale) {
                        setAttribute(player, RecruitmentDrive.makeoverTicket, true)
                    }
                    openInterface(player, 205)
                }
            }
            40 -> npc(FacialExpression.HAPPY, "No problem, but please remember that the amulet I will", "sell you is only a copy of my own. It contains no", "magical powers; and as such will only cost you 100", "coins.").also { stage++ }
            41 -> options("Sure, here you go.", "No way! that's too expensive.").also { stage++ }
            42 -> when (buttonId) {
                1 -> player(FacialExpression.HAPPY, "Sure, here you go.").also { stage = 100 }
                2 -> player(FacialExpression.AMAZED, "No way! That's too expensive.").also { stage = 400 }
            }
            100 -> {
                end()
                if (freeSlots(player) == 0) {
                    sendMessage(player, "You don't have enough inventory space.")
                }
                if (!removeItem(player, Item(Items.COINS_995, 100))) {
                    sendMessage(player, "You don't have enough coins.")
                } else {
                    sendItemDialogue(player, Items.YIN_YANG_AMULET_7803, "You receive an amulet in exchange for 100 coins.")
                    addItemOrDrop(player, Items.YIN_YANG_AMULET_7803, 1)
                }
            }
            400 -> npc(FacialExpression.HALF_GUILTY, "That's fair enough, my jewellery is not to everyone's", "taste.").also { stage = END_DIALOGUE }
            600 -> player(FacialExpression.EXTREMELY_SHOCKED, "Uh... No...?").also { stage++ }
            601 -> npc(FacialExpression.WORRIED, "Good, good, I was worried for a second there!").also { stage++ }
            602 -> player(FacialExpression.SUSPICIOUS, "Uh... Thanks, I guess.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MAKE_OVER_MAGE_2676, NPCs.MAKE_OVER_MAGE_599)
    }
}
