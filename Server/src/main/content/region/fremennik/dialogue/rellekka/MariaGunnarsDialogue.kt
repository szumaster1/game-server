package content.region.fremennik.dialogue.rellekka

import content.region.fremennik.handlers.waterbirth.TravelDestination
import content.region.fremennik.handlers.waterbirth.WaterbirthTravel.sail
import core.api.requireQuest
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player

class MariaGunnarsDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc("Welcome, Talvald. Do you have any questions?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                options(
                    "Can you ferry me to " + (if (npc.id == 5508) "Neitiznot?" else "Relleka") + "?",
                    "I just stopped to say 'hello'."
                )
                stage++
            }

            1 -> if (buttonId == 1) {
                player("Can you ferry me to " + (if (npc.id == 5508) "Neitiznot?" else "Relleka") + "?")
                stage++
            } else {
                player("I just stopped to say 'hello'.")
                stage = 4
            }

            2 -> {
                npc("Let's set sail then.")
                stage++
            }

            3 -> {
                end()
                if (!requireQuest(player, "Fremennik Trials", "")) return true
                if (npc.id == 5508) {
                    sail(player, TravelDestination.RELLEKKA_TO_NEITIZNOT)
                } else {
                    sail(player, TravelDestination.NEITIZNOT_TO_RELLEKKA)
                }
            }

            4 -> {
                npc("Thanks!")
                stage++
            }

            5 -> {
                player("I may be back later.")
                stage++
            }

            6 -> {
                end()
                npc.sendChat("Bye")
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(5508, 5507)
    }
}