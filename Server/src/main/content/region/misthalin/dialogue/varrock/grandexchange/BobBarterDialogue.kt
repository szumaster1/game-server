package content.region.misthalin.dialogue.varrock.grandexchange

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.ge.GEGuidePrice
import core.game.ge.GEGuidePrice.GuideType
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Bob barter dialogue.
 */
@Initializable
class BobBarterDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: One of the current "Market Price Guides". Walthy merchant from East Ardougne.
     * located in the north-west corner of the Grand Exchange.
     * Location: 3156,3481
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player("Hi.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc("Hello, chum, fancy buyin' some designer", "jewellery? They've come all the way from", "Ardougne! most pukka!").also { stage++ }
            1 -> player("Erm, no. I'm all set, thanks.").also { stage++ }
            2 -> npc("Okay, chum. So what can I do for you? I can", "tell you the very latest herb prices.").also { stage++ }
            3 -> options(
                "Who are you?",
                "Can you help me out with the prices for herbs?",
                "Sorry, I've got to split."
            ).also { stage++ }

            4 -> when (buttonId) {
                1 -> player("Who are you?").also { stage = 5 }
                2 -> player("Can you help me out with the prices for herbs?").also { stage = 10 }
                3 -> player("Sorry, I've got to split.").also { stage = END_DIALOGUE }
            }
            5 -> npc("Why, I'm Bob! Your friendly seller of goods!").also { stage++ }
            6 -> player("So what do you have to sell?").also { stage++ }
            7 -> npc("Oh, not much at the moment. Cuz, ya know", "Business being so well and cushie.").also { stage++ }
            8 -> player("You don't really look like you're being so", "successfull.").also { stage++ }
            9 -> npc("You plonka! It's all a show, innit! If I let people", "knows I'm in good business they'll want a", "share of the moolah!").also { stage = END_DIALOGUE }
            10 -> {
                end()
                GEGuidePrice.open(player, GuideType.HERBS)
            }
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return BobBarterDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BOB_BARTER_HERBS_6524)
    }

}
