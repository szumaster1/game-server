package content.region.misthalin.dialogue.edgeville

import core.api.getStatLevel
import core.api.sendMessage
import core.api.sendNPCDialogue
import core.api.visualize
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import cfg.consts.NPCs

/**
 * Represents the Edgeville monk dialogue.
 */
@Initializable
class EdgevilleMonkDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        if (args.size >= 1) {
            if (args[0] is NPC) {
                npc = args[0] as NPC
            } else {
                sendNPCDialogue(player, NPCs.MONK_7727, "Only members of our order can go up there. You'll need to talk to Abbot Langley if you want to explore the monastery further.").also { stage = END_DIALOGUE }
                return true
            }
        }
        npc(FacialExpression.HALF_GUILTY, "Greetings traveller.").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Can you heal me? I'm injured.", "Isn't this place built a bit out of the way?", "How do I get further into the monastery?").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Can you heal me? I'm injured.").also { stage = 10 }
                2 -> player(FacialExpression.HALF_GUILTY, "Isn't this place built a bit out of the way?").also { stage = 20 }
                3 -> player(FacialExpression.HALF_GUILTY, "How do i get farther into the monastery?").also { stage = 30 }
            }
            10 -> npc(FacialExpression.HALF_GUILTY, "Ok.").also { stage++ }
            11 -> {
                end()
                visualize(npc!!, ANIMATION,GRAPHIC)
                sendMessage(player,"You feel a little better.")
                player.getSkills().heal(((getStatLevel(player, Skills.HITPOINTS) * 0.20).toInt()))
            }
            20 -> npc(FacialExpression.HALF_GUILTY, "We like it that way actually! We get disturbed less. We still", "get rather a large amount of travellers looking for", "sanctuary and healing here as it is!").also { stage = END_DIALOGUE }
            30 -> npc(FacialExpression.HALF_GUILTY, "You'll need to talk to Abbot Langley about that. He's", "usually to be found walking the halls of the monastery.").also { stage = END_DIALOGUE }

        }
        return true
    }


    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MONK_7727)
    }

    companion object {
        private val ANIMATION = Animation(710)
        private val GRAPHIC = Graphic(84)
    }

}
