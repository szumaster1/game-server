package content.region.kandarin.portkhazard.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Murphy dialogue.
 */
@Initializable
class MurphyDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Sailor who lives in Port Khazard. He can be found on
     * the north pier at the east side next to the red star.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        playerl(FacialExpression.FRIENDLY, "Good day to you Sir.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.FRIENDLY, "Well hello my brave adventurer.").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "What are you up to?").also { stage++ }
            2 -> npcl(FacialExpression.FRIENDLY, "Getting ready to go fishing of course. There's no time to waste!").also { stage++ }
            3 -> npcl(FacialExpression.FRIENDLY, "I've got all the supplies I need from the shop at the end of the pier. They sell good rope, although their bailing buckets aren't too effective.").also { stage++ }
            4 -> options("What fish do you catch?", "Your boat doesn't look too safe.", "Could I help?").also { stage++ }
            5 -> when (buttonId) {
                1 -> player("What fish do you catch?").also { stage++ }
                2 -> player("Your boat doesn't look too safe.").also { stage = 7 }
                3 -> player("Could I help?").also { stage = 10 }
            }
            6 -> npcl(FacialExpression.FRIENDLY, "I get all sorts, anything that lies on the sea bed, you never know what you're going to get until you pull up the net!").also { stage = 4 }
            7 -> npcl(FacialExpression.FRIENDLY, "That's because it's not, the darn thing's full of holes.").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "Oh, so I suppose you can't go out for a while?").also { stage++ }
            9 -> npcl(FacialExpression.FRIENDLY, "Oh no, I don't let a few holes stop an experienced sailor like me. I could sail these seas in a barrel, I'll be going out soon enough.").also { stage = 4 }
            10 -> npcl(FacialExpression.FRIENDLY, "Well of course you can! I'll warn you though, the seas are merciless and without fishing experience you won't catch much.").also { stage++ }
            11 -> npcl(FacialExpression.FRIENDLY, "You need a fishing level of 15 or above to catch any fish on the trawler.").also { stage++ }
            12 -> npcl(FacialExpression.FRIENDLY, "On occasions the net rips, so you'll need some rope to repair it.").also { stage++ }
            13 -> npcl(FacialExpression.FRIENDLY, "Repairing the net is difficult in the harsh conditions so you'll find it easier with a higher Crafting level.").also { stage++ }
            14 -> playerl(FacialExpression.WORRIED, "Right...ok.").also { stage++ }
            15 -> npcl(FacialExpression.FRIENDLY, "There's also a slight problem with leaks.").also { stage++ }
            16 -> playerl(FacialExpression.SCARED, "Leaks?!").also { stage++ }
            17 -> npcl(FacialExpression.FRIENDLY, "Nothing some swamp paste won't fix...").also { stage++ }
            18 -> playerl(FacialExpression.HALF_ASKING, "Swamp paste?").also { stage++ }
            19 -> npcl(FacialExpression.HALF_ASKING, "Oh, and one more thing... I hope you're a good swimmer?").also { stage++ }
            20 -> options("Actually, I think I'll leave it.", "I'll be fine, let's go.", "What's swamp paste?").also { stage++ }
            21 -> when (buttonId) {
                1 -> player("Actually, I think I'll leave it.").also { stage++ }
                2 -> player("I'll be fine, let's go.").also { stage = 23 }
                3 -> player("What's swamp paste?").also { stage = 24 }
            }
            22 -> npcl(FacialExpression.FRIENDLY, "Bloomin' land lovers!!!").also { stage = END_DIALOGUE }
            23 -> npcl(FacialExpression.FRIENDLY, "Aye aye! Meet me on board the trawler. I just need to get a few things together.").also { stage = END_DIALOGUE }
            24 -> npcl(FacialExpression.FRIENDLY, "Swamp tar mixed with flour which is then heated over a fire.").also { stage++ }
            25 -> playerl(FacialExpression.HALF_ASKING, "Where can I find swamp tar?").also { stage++ }
            26 -> npcl(FacialExpression.FRIENDLY, "Unfortunately the only supply of swamp tar is in the swamps below Lumbridge.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MURPHY_464)
    }

}
