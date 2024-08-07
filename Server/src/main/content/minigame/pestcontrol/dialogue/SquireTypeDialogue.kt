package content.minigame.pestcontrol.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.GameWorld.settings
import core.plugin.Initializable

/**
 * Squire type dialogue.
 */
@Initializable
class SquireTypeDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc("Hi, how can I help you?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                options("Who are you?", "What's going on here?", "I'm fine thanks.")
                stage = 1
            }

            1 -> when (buttonId) {
                1 -> {
                    player("Who are you?")
                    stage = 10
                }

                2 -> {
                    player("What's going on here?")
                    stage = 20
                }

                3 -> {
                    player("I'm fine thanks.")
                    stage = 30
                }
            }

            10 -> {
                npc("I'm a squire for the Void Knights.")
                stage = 11
            }

            11 -> {
                player("The who?")
                stage = 12
            }

            12 -> {
                npc(
                    "The Void Knights, they are great warriors of balance",
                    "who do Guthix's work here in " + settings!!.name + "."
                )
                stage = 13
            }

            13 -> end()
            20 -> {
                npc("This is where we launch our lander to combat the", "invasion of the nearby islands. You'll see three lander - ", "one for novice, intermediate and veteran adventurers.", "Each has a different difficulty, but they therefore offer")
                stage = 21
            }

            21 -> {
                npc("varying rewards.")
                stage = 22
            }

            22 -> {
                player("And this lander is...?")
                stage = 23
            }

            23 -> {
                npc("The " + (if (npc.id == 3802) "novice" else if (npc.id == 6140) "intermediate" else "veteran") + ".")
                stage = 24
            }

            24 -> {
                player("So how do they work?")
                stage = 25
            }

            25 -> {
                npc("Simple. We send each lander out every five minutes,", "however we need at least five volunteers or it's a suicide", "mission. The lander can only hold a maximum of", "twenty five people though, so we do send them out")
                stage = 26
            }

            26 -> {
                npc("early if they get full. If you'd be willing to help us then", "just wait in the lander and we'll launch it as soon as it's", "ready. However you will need a combat level of " + (if (npc.id == 3802) "40" else if (npc.id == 6140) "70" else "100") + " to", "use this lander.")
                stage = 27
            }

            27 -> end()
            30 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SQUIRE_NOVICE_3802, NPCs.SQUIRE_INTERMEDIATE_6140, NPCs.SQUIRE_VETERAN_6141)
    }
}
