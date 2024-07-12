package content.region.karamja.dialogue.brimhaven

import content.global.travel.ship.Ships
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.setTitle
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.RandomFunction

@Initializable
class CaptainShanksDialogue(player: Player? = null) : Dialogue(player) {

    private var coins: Item? = null

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc("Hello there shipmate! I sail to Khazard Port and", "to Port Sarim. Where are you bound?")
        stage = if (!player.inventory.containsAtLeastOneItem(TICKET)) {
            -1
        } else {
            0
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            999 -> end()
            -1 -> {
                coins = Item(995, RandomFunction.random(20, 50))
                npc("I see you don't have a ticket for the ship, my", "colleague normally only sells them in Shilo village.", "But I could sell you one for a small additional", "charge. Shall we say " + coins!!.amount + " gold pieces?")
                stage = 3
            }

            0 -> {
                options("Khazard Port please.", "Port Sarim please.", "Nowhere just at the moment thanks.")
                stage++
            }

            1 -> when (buttonId) {
                1 -> {
                    player("Khazard Port please.")
                    stage = if (!player.inventory.containsItem(TICKET)) {
                        -1
                    } else {
                        10
                    }
                }

                2 -> {
                    player("Port Sarim please.")
                    stage = if (!player.inventory.containsItem(TICKET)) {
                        -1
                    } else {
                        20
                    }
                }

                3 -> {
                    player("Nowhere just at the moment thanks.")
                    stage++
                }
            }

            2 -> {
                npc("Very well then me old shipmate, Just let me know", "if you change your mind.")
                stage = 999
            }

            3 -> {
                setTitle(player, 2)
                interpreter.sendOptions("Buy a ticket for " + coins!!.amount + " gold pieces.", "Yes, I'll buy a ticket for the ship.", "No thanks, not just at the moment.")
                stage++
            }

            4 -> when (buttonId) {
                1 -> {
                    player("Yes, I'll buy a ticket for the ship.")
                    stage = 6
                }

                2 -> {
                    player("No thanks, not just at the moment.")
                    stage++
                }
            }

            5 -> {
                npc("Very well me old shipmate, come back if you change", "your mind now.")
                stage = 999
            }

            6 -> if (!player.inventory.containsItem(coins)) {
                npc("Sorry me old ship mate, but you seem to be", "financially challenged at the moment. Come back", "when your coffers are full!")
                stage = 999
            } else if (!player.inventory.hasSpaceFor(Item(Items.SHIP_TICKET_621))) {
                npc("Sorry me old ship mate, it looks like you haven't", "got enough space for a ticket. Come back when", "you've got rid of some of that junk.")
                stage = 999
            } else {
                npc("It's a good deal and no mistake. Here you go me old", "shipmate, here's your ticket.")
                player.inventory.remove(coins)
                player.inventory.add(Item(Items.SHIP_TICKET_621))
                stage++
            }

            7 -> {
                npc(FacialExpression.HALF_GUILTY, "Ok, now you have your ticket, do you want to sail", "anywhere?")
                stage++
            }

            8 -> {
                setTitle(player, 3)
                interpreter.sendOptions("Captain Shanks asks, 'Do you want to sail anywhere?'", "Khazard Port please.", "Port Sarim please.", "Nowhere just at the moment thanks.")
                stage = 1
            }

            10 -> {
                npcl(FacialExpression.HALF_GUILTY, "Very well then me old shipmate, I'll just take your ticket and then we'll set sail.")
                stage = 11
            }

            11 -> {
                end()
                if (player.inventory.remove(TICKET)) {
                    Ships.sail(player, Ships.CAIRN_ISLAND_TO_PORT_KHAZARD)
                }
            }

            20 -> {
                npcl(FacialExpression.HALF_GUILTY, "Very well then me old shipmate, I'll just take your ticket and then we'll set sail.")
                stage = 21
            }

            21 -> {
                end()
                if (player.inventory.remove(TICKET)) {
                    Ships.sail(player, Ships.CAIRN_ISLAND_TO_PORT_SARIM)
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CAPTAIN_SHANKS_518)
    }

    companion object {
        private val TICKET = Item(Items.SHIP_TICKET_621)
    }
}
