package content.global.travel.ship

import core.api.consts.NPCs
import core.api.openNpcShop
import core.api.sendDialogueOptions
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.StringUtils

@Initializable
class TraderCrewmemberDialogue (player: Player? = null) : Dialogue(player) {

    private var destination: ShipCharter.Destination? = null
    private var cost = 0

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (args.size > 1) {
            destination = (args[1] as ShipCharter.Destination)
            cost = args[2] as Int
            core.api.sendDialogue(player, "To sail to " + StringUtils.formatDisplayName(destination!!.name) + " from here will cost you " + cost + " gold. Are you sure you want to pay that?")
            stage = 3000
            return true
        }
        npc("Can I help you?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendDialogueOptions(player, "Choose an option:", "Yes, who are you?", "Yes, I would like to charter a ship.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.ASKING, "Yes, who are you?").also { stage = 100 }
                2 -> player(FacialExpression.FRIENDLY, "Yes, I would like to charter a ship.").also { stage = 2000 }
            }
            100 -> npc(FacialExpression.FRIENDLY, "I'm one of the Trader Stan's crew; we are all part of the", "largest fleet of trading and sailing vessels to ever sail the", "seven seas.").also { stage++ }
            101 -> npc(FacialExpression.FRIENDLY, "If you want to get to a port in a hurry then you can", "charter one of our ships to take you there - if the price", "is right...").also { stage++ }
            102 -> player(FacialExpression.HALF_ASKING, "So, where exactly can I go with your ships?").also { stage++ }
            103 -> npc(FacialExpression.NEUTRAL, "We run ships from Port Phasmatys over to Port Tyras,", "stopping at Port Sarim, Catherby, Karamja,", "the Shipyard and Port Khazard.").also { stage++ }
            104 -> player(FacialExpression.FRIENDLY, "Wow, that's a lot of ports. I take it you have some exotic", "stuff to trade?").also { stage++ }
            105 -> npc(FacialExpression.HAPPY, "We certainly do! We have access to items", "bought and sold from around the world.").also { stage++ }
            106 -> npc(FacialExpression.HALF_ASKING, "Would you like to take a look?").also { stage++ }
            107 -> options("Yes.", "No.").also { stage++ }
            108 -> when (buttonId) {
                1 -> player(FacialExpression.FRIENDLY, "Yes.").also { stage = 1000 }
                2 -> end()
            }

            1000 -> {
                end()
                openNpcShop(player, npc.id)
            }

            2000 -> npc(FacialExpression.HAPPY, "Certainly sir, where would you like to go?").also { stage++ }
            2001 -> {
                end()
                ShipCharter.open(player)
            }

            3000 -> options("Ok", "Choose again", "No").also { stage = 30001 }
            30001 -> when (buttonId) {
                1 -> {
                    end()
                    if (cost == 0) {
                        destination!!.sail(player)
                    }
                    if (!player.inventory.containsItem(Item(995, cost))) {
                        end()
                        return true
                    }
                    if (!player.inventory.remove(Item(995, cost))) {
                        player(FacialExpression.HALF_GUILTY, "I don't have the money for that.")
                        stage = 30002
                        return true
                    }
                    destination!!.sail(player)
                }

                2 -> {
                    end()
                    ShipCharter.open(player)
                }
                3 -> end()
            }

            30002 -> end()
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return TraderCrewmemberDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(
            NPCs.TRADER_STAN_4650,
            NPCs.TRADER_CREWMEMBER_4651,
            NPCs.TRADER_CREWMEMBER_4652,
            NPCs.TRADER_CREWMEMBER_4653,
            NPCs.TRADER_CREWMEMBER_4654,
            NPCs.TRADER_CREWMEMBER_4655,
            NPCs.TRADER_CREWMEMBER_4656
        )
    }

}
