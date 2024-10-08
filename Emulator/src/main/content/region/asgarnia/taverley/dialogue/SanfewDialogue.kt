package content.region.asgarnia.taverley.dialogue

import core.api.setQuestStage
import core.api.updateQuestTab
import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import org.rs.consts.QuestName

/**
 * Represents the Sanfew dialogue.
 */
@Initializable
class SanfewDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Sanfew is the leader of the druids in Taverley,
     * and is found in the building north of Jatix's Herblore Shop.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "What can I do for you young 'un?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                if (player.getQuestRepository().getQuest(QuestName.DRUIDIC_RITUAL).getStage(player) == 20) {
                    npc(FacialExpression.HALF_ASKING, "Did you bring me the required ingredients for the", "potion?")
                    stage = 100
                }
                if (player.getQuestRepository().getQuest(QuestName.DRUIDIC_RITUAL).getStage(player) == 10) {
                    options("I've been sent to help purify the Varrock stone circle.", "Actually, I don't need to speak to you.")
                    stage = 2
                }
                player(FacialExpression.HALF_GUILTY,
                    "Nothing... I'll just be on my way now."
                )
                stage = 1
            }

            1 -> end()
            2 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "I've been sent to assist you with the ritual to purify the", "Varrockian stone circle.")
                    stage = 5
                }

                2 -> {
                    player(FacialExpression.NEUTRAL, "Actually, I don't need to speak to you.")
                    stage = 3
                }
            }

            3 -> {
                npc(FacialExpression.FRIENDLY, "Well, we all make mistakes sometimes.")
                stage = 4
            }

            4 -> end()
            5 -> {
                npc(FacialExpression.HALF_GUILTY, "Well, what I'm struggling with right now is the meats", "needed for the potion to honour Guthix. I need the raw", "meat of four different animals for it, but not just any", "old meats will do.")
                stage = 6
            }

            6 -> {
                npc(FacialExpression.HALF_GUILTY, "Each meat has to be dipped individually into the", "Cauldron of Thunder for it to work correctly.")
                stage = 7
            }

            7 -> {
                player(FacialExpression.ASKING, "Where can I find this cauldron?")
                stage = 8
            }

            8 -> {
                npc(FacialExpression.HALF_GUILTY, "It is located somewhere in the mysterious underground", "halls which are located somewhere in the woods just", "South of here. They are too dangerous for me to go", "myself however.")
                setQuestStage(player, QuestName.DRUIDIC_RITUAL, 20)
                stage = 9
            }

            9 -> end()
            100 -> {
                if (player.inventory.containItems(522, 523, 524, 525)) {
                    player("Yes, I have all four now!")
                    stage = 200
                }
                player(FacialExpression.HALF_GUILTY, "No, not yet...")
                stage = 101
            }

            101 -> {
                npc(FacialExpression.HALF_GUILTY, "Well let me know when you do young 'un.")
                stage = 102
            }

            102 -> {
                player(FacialExpression.HALF_GUILTY, "I'll get on with it.")
                stage = 103
            }

            103 -> {
                npc(FacialExpression.HALF_GUILTY, "Good, good.")
                stage = 104
            }

            104 -> end()
            200 -> {
                npc("Well hand 'em over then " + (if (player.isMale) "lad" else "lass") + "!"
                )
                stage = 201
            }

            201 -> {
                npc("Thank you so much adventurer! These meats will allow", "our potion to honour Guthix to be completed, and bring", "one step closer to reclaiming our stone circle!")
                stage = 202
            }

            202 -> {
                player.inventory.remove(Item(522, 1), Item(523, 1), Item(524, 1), Item(525, 1))
                setQuestStage(player, QuestName.DRUIDIC_RITUAL, 99)
                updateQuestTab(player)
                npc("Now go and talk to Kaqemeex and he will introduce", "you to the wonderful world of herblore and potion", "making!")
                stage = 203
            }

            203 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SANFEW_454)
    }
}