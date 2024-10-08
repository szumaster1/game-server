package content.region.asgarnia.falador.handlers.craftguild

import content.global.skill.crafting.Tanning
import org.rs.consts.NPCs
import core.api.inInventory
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Tanner dialogue.
 */
@Initializable
class TannerDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.NEUTRAL, "Greetings friend. I am a manufacturer of leather.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                var hasHides = false
                for (tanningProduct in Tanning.values()) {
                    if (inInventory(player, tanningProduct.item)) {
                        hasHides = true
                        break
                    }
                }

                if(hasHides) {
                    npcl(FacialExpression.FRIENDLY, "I see you have brought me some hides. Would you like me to tan them for you?").also { stage = 10 }
                } else {
                    options("Can I buy some leather?", "Leather is rather weak stuff.").also { stage = 20 }
                }
            }

            10 -> options("Yes please.", "No thanks.").also { stage++ }
            11 -> when (buttonId) {
                1 -> playerl(FacialExpression.HAPPY, "Yes please.").also { stage = 12 }
                2 -> playerl(FacialExpression.NEUTRAL, "No thanks.").also { stage = 13 }
            }

            12 -> end().also { Tanning.open(player, NPCs.TANNER_804) }
            13 -> npcl(FacialExpression.FRIENDLY, "Very well, @g[sir,madam], as you wish.").also { stage = END_DIALOGUE }

            20 -> when (buttonId) {
                1 -> playerl(FacialExpression.ASKING, "Can I buy some leather?").also { stage = 21 }
                2 -> playerl(FacialExpression.SUSPICIOUS, "Leather is rather weak stuff.").also { stage = 22 }
            }

            21 -> npcl(FacialExpression.FRIENDLY, "I make leather from animal hides. Bring me some cowhides and one gold coin per hide, and I'll tan them into soft leather for you.").also { stage = END_DIALOGUE }

            22 -> npcl(FacialExpression.NOD_YES, "Normal leather may be quite weak, but it's very cheap - I make it from cowhides for only 1 gp per hide - and it's so easy to craft that anyone can work with it.").also { stage++ }
            23 -> npcl(FacialExpression.HALF_THINKING, "Alternatively you could try hard leather. It's not so easy to craft, but I only charge 3 gp per cowhide to prepare it, and it makes much sturdier armour.").also { stage++ }
            24 -> npcl(FacialExpression.FRIENDLY, "I can also tan snake hides and dragonhides, suitable for crafting into the highest quality armour for rangers.").also { stage++ }
            25 -> playerl(FacialExpression.NEUTRAL, "Thanks, I'll bear it in mind.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return TannerDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TANNER_804)
    }

}
