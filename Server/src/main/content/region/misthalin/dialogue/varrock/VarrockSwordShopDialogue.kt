package content.region.misthalin.dialogue.varrock

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class VarrockSwordShopDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "Hello, adventurer. Can I interest you in some swords?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                options( "Yes, please.", "No, I'm okay for swords right now.")
                stage = 1
            }

            1 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HAPPY, "Yes, please.")
                    stage = 2
                }

                2 -> {
                    player(FacialExpression.FRIENDLY, "No, I'm okay for swords right now.")
                    stage = 10
                }
            }

            2 -> {
                end()
                npc.openShop(player)
            }

            10 -> {
                npc(FacialExpression.FRIENDLY, "Come back if you need any.")
                stage = 11
            }

            11 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SHOPKEEPER_551, NPCs.SHOP_ASSISTANT_552)
    }
}