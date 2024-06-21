package content.region.misthalin.dialogue.varrock

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.GameWorld.settings
import core.plugin.Initializable

@Initializable
class VarrockEastBartenderDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.FRIENDLY, "What can I do yer for?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                interpreter.sendOptions(
                    "Select an Option",
                    "A glass of your finest ale please.",
                    "Can you recommend where an adventurer might make his fortune?",
                    "Do you know where I can get some good equipment?"
                )
                stage = 1
            }

            1 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HAPPY, "A glass of your finest ale please.")
                    stage = 10
                }

                2 -> {
                    player(FacialExpression.ASKING,
                        "Can you recommend where an adventurer might make",
                        "his fortune?"
                    )
                    stage = 20
                }

                3 -> {
                    player(FacialExpression.ASKING,
                        "Do you know where I can get some good equipment?"
                    )
                    stage = 30
                }
            }

            10 -> {
                npc(FacialExpression.FRIENDLY, "No problemo. That'll be 2 coins.")
                stage = 11
            }

            11 -> if (player.inventory.contains(995, 2)) {
                player.inventory.remove(Item(995, 2))
                player.inventory.add(Item(1917, 1))
                end()
                player.packetDispatch.sendMessage("You buy a pint of beer.")
            } else {
                end()
                player.packetDispatch.sendMessage("You need 2 coins to buy ale.")
            }

            20 -> {
                npc(FacialExpression.NEUTRAL,
                    "Ooh I don't know if I should be giving away information,",
                    "makes the computer game too easy."
                )
                stage = 21
            }

            21 -> {
                interpreter.sendOptions(
                    "Select an Option",
                    "Oh ah well...",
                    "Computer game? What are you talking about?",
                    "Just a small clue?"
                )
                stage = 22
            }

            22 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Oh ah well...")
                    stage = 150
                }

                2 -> {
                    player(FacialExpression.ASKING,
                        "Computer game? What are you talking about?"
                    )
                    stage = 160
                }

                3 -> {
                    player(FacialExpression.THINKING, "Just a small clue?")
                    stage = 170
                }
            }

            160 -> {
                npc(FacialExpression.THINKING,
                    "This world around us... is a computer game.... called",
                    settings!!.name + "."
                )
                stage = 161
            }

            161 -> {
                player(FacialExpression.HALF_THINKING,
                    "Nope, still don't understand what you are talking about.",
                    "What's a computer?"
                )
                stage = 162
            }

            162 -> {
                npc(FacialExpression.THINKING,
                    "It's a sort of magic box thing, which can do all sorts of",
                    "stuff."
                )
                stage = 163
            }

            163 -> {
                player(FacialExpression.WORRIED,
                    "I give up. You're obviously completely mad."
                )
                stage = 164
            }

            164 -> end()
            150 -> end()
            30 -> {
                npc(FacialExpression.FRIENDLY,
                    "Well, there's the sword shop across the road, or there's",
                    "also all sorts of shops up around the market."
                )
                stage = 31
            }

            31 -> end()
            170 -> {
                npc(FacialExpression.NEUTRAL,
                    "Go and talk to the bartender at the Holly Boar Inn, he",
                    "doesn't seem to mind giving away clues."
                )
                stage = 171
            }

            171 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BARTENDER_733)
    }

}