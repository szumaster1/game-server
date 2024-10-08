package content.minigame.pestcontrol.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.GameWorld.settings
import core.plugin.Initializable

/**
 * Represents the Outpost Squire dialogue.
 */
@Initializable
class OutpostSquireDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc("Hi, how can I help you?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                options("Who are you?", "What is this place?", "I'm fine thanks.")
                stage = 1
            }

            1 -> when (buttonId) {
                1 -> {
                    player("Who are you?")
                    stage = 10
                }

                2 -> {
                    player("What is this place?")
                    stage = 20
                }

                3 -> {
                    player("I'm fine thanks.")
                    stage = 30
                }
            }

            10 -> {
                npc("I'm a a Squire for the Void Knights.")
                stage = 5
            }

            5 -> {
                player("The who?")
                stage = 6
            }

            6 -> {
                npc("The Void Knights, they are great warriors of balance", "who do Guthix's work here in " + settings!!.name + ".")
                stage = 11
            }

            11 -> {
                options("Wow, can I join?", "What kind of work?", "What's " + settings!!.name + "?", "Uh huh, sure.")
                stage = 12
            }

            12 -> when (buttonId) {
                1 -> {
                    player("Wow, can I join?")
                    stage = 180
                }

                2 -> {
                    player("What kind of work?")
                    stage = 17
                }

                3 -> {
                    player("What's " + settings!!.name + "?")
                    stage = 15
                }

                4 -> {
                    player("Uh huh, sure.")
                    stage = 14
                }
            }

            14 -> end()
            15 -> {
                npc("It is the name that Guthix gave to this world, so we", "honour him with its use.")
                stage = 16
            }

            16 -> end()
            17 -> {
                npc("Ah well you see we try to keep " + settings!!.name + " as Guthix", "intended, it's very challenging. Actually we've been", "having some problems recently, maybe you could help", "us?")
                stage = 18
            }

            18 -> {
                options("Yeah ok, what's the problem?", "What's " + settings!!.name + "?", "I'd rather not, sorry.")
                stage = 19
            }

            19 -> when (buttonId) {
                1 -> {
                    player("Yeah ok, what's the problem?")
                    stage = 191
                }

                2 -> {
                    player("What's " + settings!!.name + "?")
                    stage = 15
                }

                3 -> {
                    player("I'd rather not sorry.")
                    stage = 190
                }
            }

            180 -> {
                npc("Entry is strictly invite only, however we do need help", "continuing Guthix's work.")
                stage = 181
            }

            181 -> {
                player("What kind of work?")
                stage = 17
            }

            190 -> end()
            191 -> {
                npc("Well the order has become quite diminished over the", "years, it's a very long process to learn the skills of a", "Void Knight. Recently there have been breaches into", "our realm from somewhere else, and strange creatures")
                stage = 192
            }

            192 -> {
                npc("have been pouring through. We can't let that happen,", "and we'd be very grateful if you'd help us.")
                stage = 193
            }

            193 -> {
                options("How can I help?", "Sorry, but I can't.")
                stage = 194
            }

            194 -> when (buttonId) {
                1 -> {
                    player("How can I help?")
                    stage = 195
                }

                2 -> {
                    player("Sorry, but I can't.")
                    stage = 190
                }
            }

            195 -> {
                npc("We send launchers from our outpost to the nearby", "islands. If you go and wait in the lander there that'd", "really help.")
                stage = 196
            }

            196 -> end()
            20 -> {
                npc("This is our outpost. From here we send launchers out to", "the nearby islands to beat back the invaders.")
                stage = 21
            }

            21 -> {
                options("What invaders?", "How can I help?", "Good luck with that.")
                stage = 22
            }

            22 -> when (buttonId) {
                1 -> {
                    player("What invaders?")
                    stage = 24
                }

                2 -> {
                    player("How can I help?")
                    stage = 195
                }

                3 -> {
                    player("Good luck with that.")
                    stage = 23
                }
            }

            23 -> end()
            24 -> {
                npc("Recently there have been breaches into our realm from", "somewhere else, and strange creatures have been", "pouring through. We can't let that happen, and we'd be", "very grateful if you'd help us.")
                stage = 193
            }

            30 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SQUIRE_3791, NPCs.SQUIRE_3792, NPCs.SQUIRE_3793, NPCs.SQUIRE_3801)
    }
}
