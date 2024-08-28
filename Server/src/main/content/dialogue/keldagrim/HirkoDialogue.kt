package content.dialogue.keldagrim

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Hirko dialogue.
 */
@Initializable
class HirkoDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_NORMAL, "Hello there!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.HALF_ASKING, "Hello, what's in those boxes?").also { stage++ }
            1 -> npc(FacialExpression.OLD_NORMAL, "Aha, interested in my crossbows are you?").also { stage++ }
            2 -> player(FacialExpression.HALF_ASKING, "Are they any good?").also { stage++ }
            3 -> npcl(FacialExpression.OLD_NORMAL, "They're dwarven engineering at its best. If you've got enough skill in crafting, smithing and fletching, you can even make them yourself. You can buy some of the parts here and make the rest yourself").also { stage++ }
            4 -> options("How do I make one for myself?", "What about ammo?", "Thanks for telling me. Bye!").also { stage++ }
            5 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_ASKING, "How do I make one for myself?").also { stage++ }
                2 -> player(FacialExpression.HALF_ASKING, "What about ammo?").also { stage = 18 }
                3 -> player(FacialExpression.FRIENDLY, "Thanks for telling me. Bye!").also { stage = 100 }
            }
            6 -> npcl(FacialExpression.OLD_NORMAL, "Well, firstly you'll need to chop yourself some wood, then use a knife on the wood to whittle out a nice crossbow stock like these here.").also { stage++ }
            7 -> player(FacialExpression.NEUTRAL, "Wood fletched into stock... check.").also { stage++ }
            8 -> npcl(FacialExpression.OLD_NORMAL, "Then get yourself some metal and a hammer and smith yourself some limbs for the bow, mind that you use the right metals and woods though as some wood is too light to use with some metal and vice versa.").also { stage++ }
            9 -> player(FacialExpression.HALF_ASKING, "Which goes with which?").also { stage++ }
            10 -> npc(FacialExpression.OLD_NORMAL, "Wood and Bronze as they're basic materials,", "Oak and Blurite, Willow and Iron, Steel and Teak,", "Mithril and Maple, Adamantite and Mahogany and finally", "Runite and Yew.").also { stage++ }
            11 -> player("Ok, so I have my stock and a pair of ", "limbs... what now?").also { stage++ }
            12 -> npcl(FacialExpression.OLD_NORMAL, "Simply take a hammer and smack the limbs firmly onto the stock. You'll then need a string, only they're not the same as normal bows.").also { stage++ }
            13 -> npcl(FacialExpression.OLD_NORMAL, "You'll need to dry some large animal's meat to get sinew, then spin that on a spinning wheel, it's the only thing we've found to be strong enough for a crossbow.").also { stage++ }
            14 -> options("What about magic logs?", "Thanks for telling me. Bye!").also { stage++ }
            15 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_ASKING, "What about magic logs?").also { stage++ }
                2 -> player(FacialExpression.FRIENDLY, "Thanks for telling me. Bye!").also { stage = 100 }
            }
            16 -> npcl(FacialExpression.OLD_NORMAL, "Well.. I don't rightly know... us dwarves don't work with magic, we prefer gold and rock. Much more stable. I guess you could ask the humans at the rangers guild to see if they can do something but I don't want").also { stage++ }
            17 -> npcl(FacialExpression.OLD_NORMAL, "anything to do with it!").also { stage = END_DIALOGUE }
            18 -> npcl(FacialExpression.OLD_NORMAL, "You can smith yourself lots of different bolts, don't forget to flight them with feathers like you do arrows though. You can poison any untipped bolt but there's also the option of tipping them with gems then").also { stage++ }
            19 -> npcl(FacialExpression.OLD_NORMAL, "enchanting them with runes. This can have some pretty powerful effects.").also { stage = END_DIALOGUE }
            100 -> npcl(FacialExpression.OLD_NORMAL, "Take care, straight shooting.").also { stage++ }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HIRKO_4558)
    }
}
