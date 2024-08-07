package content.region.desert.dialogue.pollnivneach

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Ali the snake charmer dialogue.
 */
@Initializable
class AliTheSnakeCharmerDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        if (args.size > 0) {
            player("Wow a snake charmer. Can I have a go? Please?").also { stage = 5 }
            return true
        }
        player("Wow a snake charmer. Can I have a go? Please?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendDialogue("The snake charmer fails to acknowledge you,", "he seems too deep into the music to notice you.").also {stage = END_DIALOGUE }
            5 -> sendDialogue("The snake charmer snaps out of his trance", "and directs his full attention to you.").also { stage++ }
            6 -> player(FacialExpression.JOLLY, "Wow a snake charmer. Can I have a go? Please?").also { stage++ }
            7 -> {
                end()
                if (!player.inventory.containsItems(Item(4605), Item(4606)) && !player.bank.containItems(4605, 4606)) {
                    if (player.inventory.freeSlots() >= 2) {
                        player.inventory.add(Item(4605))
                        player.inventory.add(Item(4606))
                    } else {
                        GroundItemManager.create(Item(4605), player.location)
                        GroundItemManager.create(Item(4606), player.location)
                    }
                    npc(FacialExpression.ANNOYED, "If it means that you'll leave me alone, I would give you", "my snake charming super starter kit complete", "with flute and basket.")
                } else {
                    npc(FacialExpression.ANGRY, "I already gave you one!")
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ALI_THE_SNAKE_CHARMER_1872)
    }

}
