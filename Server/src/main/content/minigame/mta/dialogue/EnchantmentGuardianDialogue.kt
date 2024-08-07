package content.minigame.mta.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Enchantment guardian dialogue.
 */
@Initializable
class EnchantmentGuardianDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc(FacialExpression.OLD_NORMAL, "Greetings young one. How can I enlighten you?").also { stage = 0 }.also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("What do I have to do in this room?", "What are the rewards?", "Got any tips that may help me?", "Thanks, bye!").also { stage++ }
            1 -> when (buttonId) {
                1 -> player("What do I have to do in this room?").also { stage = 10 }
                2 -> player("What are the rewards?").also { stage = 20 }
                3 -> player("Got any tips that may help me?").also { stage = 30 }
                4 -> player("Thanks, bye!").also { stage = END_DIALOGUE }
            }
            10 -> npc(FacialExpression.OLD_NORMAL, "In this chamber you will see various piles of shapes. You", "can pick up these shapes and enchant them using the", "enchant jewelry spells. By enchanting these shapes,", "you'll be converting them into orbs which you can be").also { stage++ }
            11 -> npc(FacialExpression.OLD_NORMAL, "placed in the hole in the centre. You will get", "Enchantment Pizazz Points for every ten shapes that", "you convert and the points you get depends on the", "level of enchantment spell you cast. You will get a").also { stage++ }
            12 -> npc(FacialExpression.OLD_NORMAL, "bonus Pizazz Point for enchanting a certain shape at a", "certain time, which I will periodically shout out. You will", "also be rewarded with an item for every so many orbs", "you deposit into the hole.").also { stage = 0 }
            20 -> npc(FacialExpression.OLD_NORMAL, "As well as the magic experience from casting your", "enchantment spells, you will get Enchantment Pizazz", "Points for converting so many shapes, plus a bonus", "point for converting shape of the correct type. You").also { stage++ }
            21 -> npc(FacialExpression.OLD_NORMAL, "should also note that you will occasionally be rewarded", "with items when you put one of the enchanted orbs in", "the floor.").also { stage = 0 }
            30 -> npc(FacialExpression.OLD_NORMAL, "Try to guess or keep track of the time between the", "change of the best shape to enchant. This means you", "can be ready to run to a different shape when you", "know it is about to change. Look out for the dragon").also { stage++ }
            31 -> npc(FacialExpression.OLD_NORMAL, "gems, as these will get you more Pizazz Points!").also { stage++ }
            32 -> player("I see.").also { stage++ }
            33 -> npc(FacialExpression.OLD_NORMAL, "Oh, and a word of warning: should you decide to leave", "this room by a method other than the exit portals, you", "will be teleported to the entrance and have any items", "that you picked up in the room removed.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ENCHANTMENT_GUARDIAN_3100)
    }

}
