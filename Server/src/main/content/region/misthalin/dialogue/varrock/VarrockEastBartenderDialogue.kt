package content.region.misthalin.dialogue.varrock

import core.api.consts.NPCs
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.GameWorld.settings
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class VarrockEastBartenderDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.FRIENDLY, "What can I do yer for?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("A glass of your finest ale please.", "Can you recommend where an adventurer might make his fortune?", "Do you know where I can get some good equipment?").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HAPPY, "A glass of your finest ale please.").also { stage = 10 }
                2 -> player(FacialExpression.ASKING, "Can you recommend where an adventurer might make", "his fortune?").also { stage = 20 }
                3 -> player(FacialExpression.ASKING, "Do you know where I can get some good equipment?").also { stage = 30 }

            }
            10 -> npc(FacialExpression.FRIENDLY, "No problemo. That'll be 2 coins.").also { stage++ }
            11 -> if (player.inventory.contains(995, 2)) {
                player.inventory.remove(Item(995, 2))
                player.inventory.add(Item(1917, 1))
                end()
                sendMessage(player, "You buy a pint of beer.")
            } else {
                end()
                sendMessage(player, "You need 2 coins to buy ale.")
            }
            20 -> npc(FacialExpression.NEUTRAL, "Ooh I don't know if I should be giving away information,", "makes the computer game too easy.").also { stage++ }
            21 -> options("Oh ah well...", "Computer game? What are you talking about?", "Just a small clue?").also { stage++ }
            22 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Oh ah well...").also { stage = END_DIALOGUE }
                2 -> player(FacialExpression.ASKING, "Computer game? What are you talking about?").also { stage = 160 }
                3 -> player(FacialExpression.THINKING, "Just a small clue?").also { stage = 170 }
            }
            160 -> npc(FacialExpression.THINKING, "This world around us... is a computer game.... called", settings!!.name + ".").also { stage++ }
            161 -> player(FacialExpression.HALF_THINKING, "Nope, still don't understand what you are talking about.", "What's a computer?").also { stage++ }
            162 -> npc(FacialExpression.THINKING, "It's a sort of magic box thing, which can do all sorts of", "stuff.").also { stage++ }
            163 -> player(FacialExpression.WORRIED, "I give up. You're obviously completely mad.").also { stage = END_DIALOGUE }
            30 -> npc(FacialExpression.FRIENDLY, "Well, there's the sword shop across the road, or there's", "also all sorts of shops up around the market.").also { stage = END_DIALOGUE }
            170 -> npc(FacialExpression.NEUTRAL, "Go and talk to the bartender at the Holly Boar Inn, he", "doesn't seem to mind giving away clues.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BARTENDER_733)
    }

}