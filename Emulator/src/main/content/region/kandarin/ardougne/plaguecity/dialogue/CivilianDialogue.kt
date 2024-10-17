package content.region.kandarin.ardougne.plaguecity.dialogue

import content.region.kandarin.ardougne.plaguecity.quest.elena.PlagueCityUtils
import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Civilian dialogue.
 */
@Initializable
class CivilianDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        // Talk to any Civilian with full Mourner gear.
        if(PlagueCityUtils.hasFullMournerGear(player)){
            player("Hello.").also { stage = 20 }
            return true
        }
        // Talk with Civilian (Long hair with green pants).
        if(npc.id == NPCs.CIVILIAN_786) {
            player("Hi there.")
        }
        // Anything else.
        else {
            playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage = 15 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.FRIENDLY, "Good day to you, traveller.").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "What are you up to?").also { stage++ }
            2 -> npcl(FacialExpression.FRIENDLY, "Chasing mice as usual! It's all I seem to do nowadays.").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "You must waste a lot of time?").also { stage++ }
            4 -> npcl(FacialExpression.FRIENDLY, "Yes, but what can you do? It's not like there's many cats around here!").also { stage++ }
            // Has a Kitten.
            5 -> if(PlagueCityUtils.hasAnKitten(player)) {
                playerl(FacialExpression.HAPPY, "I have a kitten that I could sell.").also { stage++ }
            }
            // Has a Cat.
            else if(PlagueCityUtils.hasAnCat(player)) {
                playerl(FacialExpression.HAPPY, "I have a cat that I could sell.").also { stage = 9 }
            }
            // Has a Witch's Cat.
            else if(inInventory(player, Items.WITCHS_CAT_1491)) {
                player(FacialExpression.FRIENDLY, "I have a cat...look!").also { stage = 21 }
            }
            // Anything else.
            else {
                playerl(FacialExpression.NEUTRAL, "No, you're right, you don't see many around.").also { stage = END_DIALOGUE }
            }

            6 -> npcl(FacialExpression.HAPPY, "Really, let's have a look.").also { stage++ }
            7 -> sendDialogue(player, "You present the kitten.").also { stage++ }
            8 -> npcl(FacialExpression.LAUGH, "Hah! that little thing won't catch any mice. I need a fully grown cat.").also { stage = END_DIALOGUE }

            9 -> npcl(FacialExpression.FRIENDLY, "You don't say. Is that it?").also { stage++ }
            10 -> player("Say hello to a real mouse-killer!").also { stage++ }
            11 -> npcl(FacialExpression.FRIENDLY, "Hmmm, not bad, not bad at all. Looks like it's a lively one.").also { stage++ }
            12 -> playerl(FacialExpression.THINKING, "Erm...kind of...").also { stage++ }
            13 -> npcl(FacialExpression.FRIENDLY, "I don't have much in the way of money, but I do have these!").also { stage++ }

            // Cat sale.
            14 -> {
                end()
                val item = PlagueCityUtils.grownCatItemIds
                for (i in item) {
                    if (removeItem(player, Item(i.id, 1))) {
                        player.familiarManager.removeDetails(i.idHash)
                        sendItemDialogue(player, Items.DEATH_RUNE_560, "The peasant shows you a sack of death runes.")
                        addItem(player, Items.DEATH_RUNE_560, 100)
                    }
                }
            }

            15 -> npcl(FacialExpression.FRIENDLY, "I'm a bit busy to talk right now, sorry.").also { stage++ }
            16 -> playerl(FacialExpression.FRIENDLY, "Why? What are you doing?").also { stage++ }
            17 -> npcl(FacialExpression.FRIENDLY, "Trying to kill these mice! What I really need is a cat!").also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "No, you're right, you don't see many around.").also { stage = END_DIALOGUE }
            19 -> npcl(FacialExpression.FRIENDLY, "If you Mourners really wanna help, why don't you do something about these mice?!").also { stage = END_DIALOGUE }
            20 -> npcl(FacialExpression.HALF_ASKING, "If you Mourners really wanna help, why don't you do something about these mice?!").also { stage = END_DIALOGUE }
            21 -> npcl(FacialExpression.NEUTRAL, "Hmmm...doesn't look like it's seen daylight in years. That's not going to catch any mice!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CIVILIAN_785, NPCs.CIVILIAN_786, NPCs.CIVILIAN_787)
    }
}
