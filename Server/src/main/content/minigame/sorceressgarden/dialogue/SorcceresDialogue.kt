package content.minigame.sorceressgarden.dialogue

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Sorcceres dialogue.
 */
@Initializable
class SorcceresDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc("What are you doing in my house?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                options("None of your business!", "I'm here to kill you!", "Can I have some sq'irks please?", "I'm just passing by.")
                stage = 1
            }

            1 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.FURIOUS, "None of your business!")
                    stage = 10
                }

                2 -> {
                    player("I'm here to kill you!")
                    stage = 20
                }

                3 -> {
                    player("Can I have some sq'irks please?")
                    stage = 30
                }

                4 -> {
                    player("I'm just passing by.")
                    stage = 40
                }
            }

            10 -> {
                player("I go where I like and do what I like.")
                stage = 11
            }

            11 -> {
                npc("Not in my house. Be gone!")
                stage = 12
            }

            12 -> {
                end()
                tele()
            }

            20 -> {
                npc("I think not!")
                stage = 21
            }

            21 -> {
                end()
                tele()
            }

            30 -> {
                npc("What do you want them for?")
                stage = 31
            }

            31 -> {
                player("Someone asked me to bring them some.")
                stage = 32
            }

            32 -> {
                npc("Who?")
                stage = 33
            }

            33 -> {
                player("<col=0000FF>You find yourself compelled to answer truthfully:</col>", "Osman.")
                stage = 34
            }

            34 -> {
                npc(
                    "In that case I'm sorry, you can't. I have had a falling",
                    "out with him recently and would rather not oblige him."
                )
                stage = 35
            }

            35 -> end()
            40 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(5531)
    }

    /**
     * Tele
     *
     */
    fun tele() {
        npc.sendChat("Be gone intruder!")
        player.lock()
        Pulser.submit(object : Pulse(2, player) {
            override fun pulse(): Boolean {
                player.unlock()
                player.properties.teleportLocation = Location(3321, 3143, 0)
                return true
            }
        })
    }
}