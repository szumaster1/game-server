package content.location.yanille

import core.api.Container
import core.api.addItem
import cfg.consts.Items
import cfg.consts.NPCs
import core.api.openNpcShop
import core.api.removeItem
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Leon dialogue.
 */
@Initializable
class LeonDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        options("What is this place?", "Can I have a go with your crossbow?", "What are you holding there?").also { stage = 1 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            1 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.ASKING, "What is this place?")
                    stage = 10
                }

                2 -> {
                    player(FacialExpression.FRIENDLY, "Can I have a go with your crossbow?")
                    stage = 20
                }

                3 -> {
                    player(FacialExpression.ASKING, "What are you holding there?")
                    stage = 30
                }
            }

            10 -> {
                npc(FacialExpression.HAPPY, "This is Aleck's Hunter Emporium. Basically, it's just a", "shop with fancy name; you can buy various weapons", "and traps here.")
                stage = END_DIALOGUE
            }
            20 -> {
                npc(FacialExpression.HALF_GUILTY, "I'm afraid with it being a prototype, I've only got a few", "for my own testing purposes.")
                stage = 21
            }

            21 -> {
                npc(FacialExpression.HALF_GUILTY, "Of course, for the right price, it might be worth my while", "to make another prototype.")
                stage = 22
            }

            22 -> {
                player(FacialExpression.HALF_GUILTY, "How much?")
                stage = 23
            }

            23 -> {
                npc(FacialExpression.HALF_GUILTY, "To you, 1300 gold pieces.")
                stage = 24
            }

            24 -> {
                options("Ok, here you go.", "I'll give it a miss, thanks.", "Does it use normal crossbow bolts?")
                stage = 25
            }

            25 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Ok, here you go.")
                    stage = 40
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "I'll give it a miss, thanks.")
                    stage = 41
                }

                3 -> {
                    player(FacialExpression.HALF_GUILTY, "Does it use normal crossbow bolts?")
                    stage = 42
                }
            }

            30 -> {
                npc(FacialExpression.HAPPY, "This? This is a prototype for a new type of crossbow", "I've been designing.")
                stage = END_DIALOGUE
            }

            40 -> if (!removeItem<Item>(player, Item(Items.COINS_995, 1300), Container.INVENTORY)) {
                player(FacialExpression.HALF_GUILTY, "Oh, actually I don't have enough money with me.")
                stage = END_DIALOGUE
            } else if (player.inventory.freeSlots() == 0) {
                npc(FacialExpression.HALF_GUILTY, "Well, you look a little overburdened there at the moment.", "Maybe you should free up some space to carry it in first.")
                stage = END_DIALOGUE
            } else {
                npc(FacialExpression.HALF_GUILTY, "Here, you might as well take this one.", "Aleck over there doesn't seem interested and it'll save you", "a wait.")
                addItem(player, Items.HUNTERS_CROSSBOW_10156, 1, Container.INVENTORY)
                stage = END_DIALOGUE
            }
            41 -> {
                npc(FacialExpression.HALF_GUILTY, "Your loss; they'll", "probably become highly valuable and sought after once", "I convince someone to market the things.")
                stage = END_DIALOGUE
            }

            42 -> {
                npc(FacialExpression.HALF_GUILTY, "Ah, I admit as a result of its er...unique construction, it", "won't take just any old bolts")
                stage = 43
            }

            43 -> {
                npc(FacialExpression.HALF_GUILTY, "If you can supply the materials and a token fee, I'd", "be happy to make some for you.")
                stage = 44
            }

            44 -> {
                player(FacialExpression.HALF_ASKING, "What materials do you need?")
                stage = 45
            }

            45 -> {
                npc(FacialExpression.HALF_GUILTY, "Kebbit spikes. Lots of 'em. Not all kebbits have spikes, mind", "you. You'll be needing to look for prickly kebbits or, even better, ", "razor-backed kebbits to get material hard enough.")
                stage = 46
            }

            46 -> {
                options("Let's see what you can make then.", "I'll give it a miss, thanks.", "Can't I just make my own?")
                stage = 47
            }

            47 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Let's see what you can make then.")
                    stage = 48
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "I'll give it a miss, thanks.")
                    stage = 49
                }

                3 -> {
                    player(FacialExpression.HALF_GUILTY, "Can't I just make my own?")
                    stage = 52
                }
            }
            48 -> {
                end()
                openNpcShop(player, NPCs.LEON_5111)
            }
            49 -> {
                npc(FacialExpression.HALF_GUILTY, "Well, if you ever find yourself in need of that innovative", "edge, you know who to contact.")
                stage = 50
            }

            50 -> {
                player(FacialExpression.HALF_GUILTY, "Er, yes, of course.")
                stage = END_DIALOGUE
            }

            51 -> {
                npc(FacialExpression.HALF_GUILTY, "Yes, I suppose you could, although you'll need a steady hand with", "a knife and chisel.")
                stage = 52
            }

            52 -> {
                npc(FacialExpression.HALF_GUILTY, "The bolts have an unusual diameter, but basically you'll", "just need to be able to carve kebbit spikes into", "straight shafts.")
                stage = 53
            }

            53 -> {
                player(FacialExpression.HALF_GUILTY, "Great; thanks.")
                stage = END_DIALOGUE
            }
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return LeonDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LEON_5111)
    }

}
