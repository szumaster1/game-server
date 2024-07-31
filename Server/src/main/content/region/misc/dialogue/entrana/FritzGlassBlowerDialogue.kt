package content.region.misc.dialogue.entrana

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.GroundItem
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.plugin.Initializable

@Initializable
class FritzGlassBlowerDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (!player.getSavedData().globalData.isFritzGlass()) {
            npc(FacialExpression.HALF_GUILTY, "Hello adventurer, welcome to the Entrana furnace.")
            stage = 0
        } else {
            npc(FacialExpression.HALF_GUILTY, "Ah " + player.username + ", have you come to sell me some molten", "glass?")
            stage = 100
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "Would you like me to explain my craft to you?").also { stage++ }
            1 -> options("Yes please. I'd be fascinated to hear what you do.", "No thanks, I doubt I'll ever turn my hand to glassblowing.").also { stage++ }
            2 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Yes please. I'd be fascinated to hear what you do.")
                    stage = 10
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "No thanks, I doubt I'll ever turn my hand to glassblowing.")
                    stage = 22
                }
            }

            10 -> {
                npc(FacialExpression.HALF_GUILTY, "I'm extremely pleased to hear that! I've always wanted", "an apprentice. Let me talk to you through the secrets of", "glassblowing.")
                stage = 11
            }

            11 -> {
                npc(FacialExpression.HALF_GUILTY, "Glass is made from soda ash and silica. We get out", "soda ash by collecting seaweed from the rocks - the", "prevailing currents make the north-west corner of the", "island the best place to find it, it can also be found in")
                stage = 12
            }

            12 -> {
                npc(FacialExpression.HALF_GUILTY, "your nets sometimes when you're fishing, on Karamja", "island or at the Piscatoris Fishing Colonly in the nets", "there. To turn seaweed into soda ash, all you need to", "do is burn it on a fire. Feel free to use the range in")
                stage = 13
            }

            13 -> {
                npc(FacialExpression.HALF_GUILTY, "my house for that; it's the one directly west of here.", "Next we collect sand from the sandpit that you'll also", "find just west of here, there are other located in", "Yanille and Shilo Village.")
                stage = 14
            }

            14 -> {
                npc(FacialExpression.HALF_GUILTY, "You'll need a bucket to cary it in. Tell you what, you", "can have this old one of mine.")
                stage = 15
            }

            15 -> {
                if (!player.inventory.add(Item(1925))) {
                    GroundItemManager.create(GroundItem(Item(1925), player.location, player))
                }
                player.getSavedData().globalData.setFritzGlass(true);
                npc(FacialExpression.HALF_GUILTY, "Bring the sand and the soda ash back here and melt", "them together in the furnace, and there you have it -", "molten glass!")
                stage = 16
            }

            16 -> {
                npc(FacialExpression.HALF_GUILTY, "There are many things you can use the molten glass", "for once you have made it. Depending on how talented", "you are, you could try turning it into something, like a", "fishbowl, for example. If you'd like to try your hand at")
                stage = 17
            }

            17 -> {
                npc(FacialExpression.HALF_GUILTY, "the fine art of glassblowing you can use my spare", "glassblowing pipe. I think I left it on the chest of", "drawers in my house this morning.")
                stage = 18
            }

            18 -> {
                npc(FacialExpression.HALF_GUILTY, "Alternatively I am always happy to buy the molten glass", "from you, saves me running about making it for", "myself.")
                stage = 19
            }

            19 -> {
                player(FacialExpression.HALF_GUILTY, "That sounds good. How much will you pay me?")
                stage = 20
            }

            20 -> {
                npc(FacialExpression.HALF_GUILTY, "Tell you what, because you've been interested in my", "art, I'll pay you the premium price of 20 gold pieces", "for each piece of molten glass you bring me.")
                stage = 21
            }

            21 -> end()
            22 -> end()
            100 -> {
                options( "Yes.", "No.")
                stage = 101
            }

            101 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Yes.")
                    stage = 110
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "No.")
                    stage = 120
                }
            }

            110 -> if (!player.inventory.containsItem(MOLTEN_GLASS)) {
                npc(FacialExpression.HALF_GUILTY, "Umm, not much point me trying to pay you for glass", "you don't have, is there?")
                stage = 111
            } else {
                val amt = player.inventory.getAmount(MOLTEN_GLASS)
                val remove = Item(MOLTEN_GLASS.id, amt)
                if (!player.inventory.containsItem(remove)) {
                    end()
                    return true
                }
                if (player.inventory.remove(remove)) {
                    player.inventory.add(Item(995, amt * 20))
                    npc(FacialExpression.HALF_GUILTY, "Pleasure doing business with you " + player.username + ".")
                    stage = 118
                }
            }

            111 -> {
                player(FacialExpression.HALF_GUILTY, "Well, actually, if you don't mind...")
                stage = 112
            }

            112 -> {
                npc(FacialExpression.HALF_GUILTY, "I guess you've never heard of a rhetorical question", "then. I'll make it simple for you. You bring glass, me", "pay shiny gold coins.")
                stage = 113
            }

            113 -> end()
            118 -> end()
            120 -> {
                npc(FacialExpression.HALF_GUILTY, "Oh.")
                stage = 121
            }

            121 -> {
                npc(FacialExpression.HALF_GUILTY, "...errr, well should you get any I'm quite happy to pay", "for it. Remember, I'll pay you 20 gold pieces for each", "piece of molten glass you get for me.")
                stage = 122
            }

            122 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FRITZ_THE_GLASSBLOWER_4909)
    }

    companion object {
        private val MOLTEN_GLASS = Item(1775)
    }
}
