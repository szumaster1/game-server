package content.region.misc.keldagrim.dialogue.ferryman

import core.api.inInventory
import core.api.removeItem
import core.api.teleport
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.Location
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE
import org.rs.consts.Items
import org.rs.consts.NPCs

/**
 * Represents the Dwarven ferryman forth dialogue.
 */
@Initializable
class DwarvenFerrymanForthDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.OLD_DEFAULT, "Hello there, want a ride?").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "A ride, where to?").also { stage++ }
            2 -> npcl(FacialExpression.OLD_DEFAULT, "Across the river, across the river! It's just a short ride!").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "How much will that cost me?").also { stage++ }
            4 -> npcl(FacialExpression.OLD_DEFAULT, "Haha, you understand us dwarves well, human!").also { stage++ }
            5 -> npcl(FacialExpression.OLD_DEFAULT, "It's just 2 gold pieces! Want to go?").also { stage++ }
            6 -> showTopics(Topic(FacialExpression.FRIENDLY, "I'd like to go further downstream.", 7), Topic(FacialExpression.FRIENDLY, "Yes please.", 12), Topic(FacialExpression.FRIENDLY, "No thanks.", END_DIALOGUE))
            7 -> npcl(FacialExpression.OLD_DEFAULT, "You mean to the city of Keldagrim?").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "Ehm, yes, I think so.").also { stage++ }
            9 -> npcl(FacialExpression.OLD_DEFAULT, "Sorry, I'm just a simple ferryman, I only go across.").also { stage++ }
            10 -> npcl(FacialExpression.OLD_DEFAULT, "But talk to my friend here, he sometimes goes to the city in his ship.").also { stage++ }
            11 -> playerl(FacialExpression.FRIENDLY, "Thanks.").also { stage = END_DIALOGUE }
            12 -> {
                if (!inInventory(player, Items.COINS_995, 2)) {
                    npcl(FacialExpression.OLD_DEFAULT, "You don't even have 2 gold coins, humans!").also { stage++ }
                } else {
                    if (removeItem(player, Item(Items.COINS_995, 2))) {
                        end()
                        teleport(player, Location.create(2836, 10143, 0))
                    }
                }
            }
            13 -> npcl(FacialExpression.OLD_DEFAULT, "Come back later.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DWARVEN_FERRYMAN_1843)
    }
}
