package content.region.kandarin.catherby.dialogue

import org.rs.consts.NPCs
import core.api.freeSlots
import core.api.inInventory
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable

/**
 * Represents the Candle seller dialogue.
 */
@Initializable
class CandleSellerDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "Do you want a lit candle for 1000 gold?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                options("Yes please.", "One thousand gold?!", "No thanks, I'd rather curse the darkness.")
                stage = 1
            }

            800 -> {
                if (freeSlots(player) == 0) {
                    end()
                    player.packetDispatch.sendMessage("You don't have enough inventory space to buy a candle.")

                }
                if (!player.inventory.contains(995, 1000)) {
                    player(FacialExpression.HALF_GUILTY, "Sorry, I don't seem to have enough coins.")
                    stage = 30

                }
                if (player.inventory.contains(995, 1000)) {
                    player.inventory.remove(COINS)
                    player.inventory.add(Item(33, 1))
                    npc(FacialExpression.HAPPY, "Here you go then.")
                    stage = 400
                }
            }

            1 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HAPPY, "Yes please.")
                    stage = 800
                }

                2 -> {
                    player(FacialExpression.EXTREMELY_SHOCKED, "One thousand gold?!")
                    stage = 20
                }

                3 -> {
                    player(FacialExpression.EXTREMELY_SHOCKED, "No thanks, I'd rather curse the darkness.")
                    stage = 30
                }
            }

            20 -> {
                npc(FacialExpression.NEUTRAL, "Look, you're not going to be able to survive down that", "hole without a light source.")
                stage = 21
            }

            21 -> {
                npc(FacialExpression.NEUTRAL, "So you could go off to the candle shop to buy one", "more cheaply. You could even make your own lantern,", "which is a lot better.")
                stage = 22
            }

            22 -> {
                npc(FacialExpression.HAPPY, "But I bet you want to find out what's down there right", "now, don't you? And you can pay me 1000 gold for", "the privilege!")
                stage = 23
            }

            23 -> {
                options("All right, you win, I'll buy a candle.", "No way.", "How do you make lanterns?")
                stage = 240
            }

            30 -> end()
            240 -> when (buttonId) {
                1 -> {
                    player(
                        FacialExpression.HALF_GUILTY,
                        "All right, you win, I'll buy a candle."
                    )
                    stage = 350
                }

                2 -> {
                    player(FacialExpression.ANNOYED, "No way.")
                    stage = 30
                }

                3 -> {
                    player(FacialExpression.HALF_ASKING, "How do you make lanterns?")
                    stage = 230
                }
            }

            230 -> {
                npc(FacialExpression.FRIENDLY, "Out of glass. The more advanced lanterns have a", "metal component as well.")
                stage = 231
            }

            231 -> {
                npc(FacialExpression.FRIENDLY, "Firstly you can make a simple candle lantern out of", "glass. It's just like a candle, but the flame isn't exposed,", "so it's safer.")
                stage = 232
            }

            232 -> {
                npc(FacialExpression.FRIENDLY, "Then you can make an oil lamp, which is brighter but", "has an exposed flame. But if you make an iron frame", "for it you can turn it into an oil lantern.")
                stage = 233
            }

            233 -> {
                npc(FacialExpression.FRIENDLY, "Finally there's a Bullseye lantern. You'll need to", "make a frame out of steel and add a glass lens.")
                stage = 234
            }

            234 -> {
                npc(FacialExpression.FRIENDLY, "Oce you've made your lamp or lantern, you'll need to", "make lamp oil for it. The chemist near Reimmington has", "a machine for that.")
                stage = 235
            }

            235 -> {
                npc(FacialExpression.FRIENDLY, "For any light source, you'll need a tinderbox to light it.", "Keep your tinderbox handy in case it goes out!")
                stage = 236
            }

            236 -> {
                npc(FacialExpression.HAPPY, "But if all that's to complicated, you can buy a candle", "right here for 1000 gold!")
                stage = 237
            }

            237 -> {
                options( "All right, you win, I'll buy a candle.", "No thanks, I'd rather curse the darkness.")
                stage = 290
            }

            350 -> {
                if (player.inventory.freeSlots() == 0) {
                    end()
                    sendMessage(player, "You don't have enough inventory space to buy a candle.")

                }
                if (!player.inventory.contains(995, 1000)) {
                    player(FacialExpression.HALF_GUILTY, "Sorry, I don't seem to have enough coins.")
                    stage = 30

                }
                if (player.inventory.remove(COINS)) {
                    player.inventory.add(CANDLE)
                    npc(FacialExpression.HAPPY, "Here you go then.")
                    stage = 400
                }
            }

            400 -> {
                npc(FacialExpression.NEUTRAL, "I should warn you, though, it can be dangerous to take", "a naked flame down there. You'd better off making", "a lantern.")
                stage = 401
            }

            401 -> {
                player(FacialExpression.FRIENDLY, "Okay, thanks.")
                stage = 402
            }

            402 -> end()
            290 -> when (buttonId) {
                1 -> {
                    if (freeSlots(player) == 0) {
                        end()
                        sendMessage(player,"You don't have enough inventory space to buy a candle.")

                    }
                    if (!inInventory(player, 995, 1000)) {
                        player(FacialExpression.HALF_GUILTY,
                            "Sorry, I don't seem to have enough coins."
                        )
                        stage = 30

                    }
                    if (player.inventory.remove(COINS)) {
                        player.inventory.add(CANDLE)
                        npc(FacialExpression.FRIENDLY, "Here you go then.")
                        stage = 400
                    }
                }

                2 -> {
                    player(FacialExpression.EXTREMELY_SHOCKED, "No thanks, I'd rather curse the darkness.")
                    stage = 291
                }
            }

            291 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CANDLE_SELLER_1834)
    }

    companion object {
        private val COINS = Item(995, 1000)
        private val CANDLE = Item(33, 1)
    }
}