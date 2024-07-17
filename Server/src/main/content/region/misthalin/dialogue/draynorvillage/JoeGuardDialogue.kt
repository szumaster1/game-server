package content.region.misthalin.dialogue.draynorvillage

import core.api.consts.NPCs
import core.api.sendDialogue
import core.api.setAttribute
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.item.Item
import core.plugin.Initializable

@Initializable
class JoeGuardDialogue(player: Player? = null) : Dialogue(player) {

    private var quest: Quest? = null

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        quest = player.getQuestRepository().getQuest("Prince Ali Rescue")
        when (quest!!.getStage(player)) {
            40 -> {
                if (player.getAttribute("guard-drunk", false)) {
                    npc("Halt! Who goes there?")
                    stage = 23
                    return true
                }
                if (player.inventory.contains(1917, 3)) {
                    player("I have some beer here, fancy one?")
                    stage = 10

                }
                npc(FacialExpression.HALF_GUILTY, "Hi, I'm Joe, door guard for Lady Keli.")
                stage = 0
            }

            60, 100 -> {
                npc("Halt! Who goes there? Friend or foe?")
                stage = 0
            }

            else -> {
                npc(FacialExpression.HALF_GUILTY, "Hi, I'm Joe, door guard for Lady Keli.")
                stage = 0
            }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (quest!!.getStage(player) > 50) {
            when (stage) {
                0 -> {
                    player("Hi friend, I am just checking out things here.")
                    stage = 1
                }

                1 -> {
                    npc("The Prince got away, I am in trouble. I better not talk",
                        "to you, they are not sure I was drunk."
                    )
                    stage = 2
                }

                2 -> end()
            }
            return true
        }
        if (stage >= 10 && quest!!.getStage(player) == 40) {
            when (stage) {
                10 -> {
                    npc("Ah, that would be lovely, just one now,", "just to wet my throat.")
                    stage = 11
                }

                11 -> {
                    player("Of course, it must be tough being here without a drink.")
                    stage = 12
                }

                12 -> {
                    sendDialogue(player, "You hand a beer to the guard, he drinks it in seconds.")
                    stage = 13
                }

                13 -> {
                    if (!player.inventory.containsItem(BEER)) {
                        end()
                        return true
                    }
                    if (player.inventory.remove(BEER)) {
                        npc("That was perfect, I can't thank you enough.")
                        stage = 14
                    }
                }

                14 -> {
                    player("How are you? Still ok? Not too drunk?")
                    stage = 15
                }

                15 -> {
                    player("Would you care for another, my friend?")
                    stage = 16
                }

                16 -> {
                    npc("I better not, I don't want to be drunk on duty.")
                    stage = 17
                }

                17 -> {
                    player("Here, just keep these for later,", "I hate to see a thirsty guard.")
                    stage = 18
                }

                18 -> if (player.inventory.remove(BEER) && player.inventory.remove(BEER)) {
                    sendDialogue(player,"You hand two more beers to the guard. He takes a sip of one, and then he drinks the both.")
                    stage = 19
                    setAttribute(player, "/save:guard-drunk", true)
                }

                19 -> {
                    npc("Franksh, that wash just what I need to shtay on guard.",
                        "No more beersh, I don't want to get drunk."
                    )
                    stage = 20
                }

                20 -> {
                    interpreter.sendDialogue("The guard is drunk, and no longer a problem.")
                    stage = 21
                }

                21 -> end()
                23 -> {
                    player("Hello friend, I am just rescuing the prince, is that ok?")
                    stage = 24
                }

                24 -> {
                    npc("Thatsh a funny joke. You are lucky I am shober. Go", "in peace, friend.")
                    stage = 21
                }
            }
            return true
        }
        when (stage) {
            0 -> {
                player(FacialExpression.HALF_GUILTY, "Hi, who are you guarding here?")
                stage = 1
            }

            1 -> {
                npc(FacialExpression.HALF_GUILTY, "Can't say, all very secret. You should get out of here.", "I am not suposed to talk while I guard.")
                stage = 2
            }

            2 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.JOE_916)
    }

    companion object {
        private val BEER = Item(1917)
    }
}