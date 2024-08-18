package content.region.asgarnia.dialogue.goblinvillage

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.RandomFunction

/**
 * Represents the Goblin village dialogue.
 */
@Initializable
class GoblinVillageDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        val rand = RandomFunction.random(5)
        when (rand) {
            0 -> {
                end()
                npc(FacialExpression.OLD_DEFAULT, "I kill you human!")
                npc.attack(player)
            }
            1 -> npc(FacialExpression.OLD_DEFAULT, "Go away smelly human!").also { stage = 7 }
            2 -> npc(FacialExpression.OLD_DEFAULT, "Happy goblin new century!").also { stage = 0 }
            3 -> npc(FacialExpression.OLD_DEFAULT, "What you doing here?").also { stage = 3 }
            4 -> npc(FacialExpression.OLD_DEFAULT, "Brown armour best!").also { stage = 9 }
            5 -> npc(FacialExpression.OLD_DEFAULT, "Go away smelly human!").also { stage = 7 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Happy new century!", "What is the goblin new century?").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Happy new century!").also { stage = END_DIALOGUE }
                2 -> player(FacialExpression.HALF_GUILTY, "What is the goblin new century?").also { stage++ }
            }
            2 -> npc(FacialExpression.OLD_DEFAULT, "You tell human secrets!").also { stage = END_DIALOGUE }
            3 -> options("I'm here to kill all you goblins!", "I'm just looking around.").also { stage++ }
            4 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "I'm here to kill all you goblins!").also { stage++ }
                2 -> player(FacialExpression.HALF_GUILTY, "I'm just looking around.").also { stage = 6 }
            }
            5 -> npc(FacialExpression.OLD_DEFAULT, "I kill you!").also {
                end()
                npc.attack(player)
            }
            6 -> npc(FacialExpression.OLD_DEFAULT, "Me not sure that allowed. You have to check with", "generals.").also { stage = END_DIALOGUE }
            7 -> npc(FacialExpression.OLD_DEFAULT, "What you call me?").also { stage++ }
            8 -> npc(FacialExpression.OLD_DEFAULT, "I kill you human!").also {
                end()
                npc.attack(player)
            }
            9 -> options("Err or.", "Why is brown best?").also { stage++ }
            10 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Err ok.").also { stage = END_DIALOGUE }
                2 -> player(FacialExpression.HALF_GUILTY, "Why is brown best?").also { stage++ }
            }
            11 -> npc(FacialExpression.OLD_DEFAULT, "General Wartface and General Bentnoze both say it is.", "And normally they never agree!").also { stage = END_DIALOGUE }
        }
        return true
    }
    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GOBLIN_4483, NPCs.GOBLIN_4488, NPCs.GOBLIN_4489, NPCs.GOBLIN_4484, NPCs.GOBLIN_4491, NPCs.GOBLIN_4485, NPCs.GOBLIN_4486, NPCs.GOBLIN_4492, NPCs.GOBLIN_4487, NPCs.GOBLIN_4481, NPCs.GOBLIN_4479, NPCs.GOBLIN_4482, NPCs.GOBLIN_4480)
    }
}
