package content.region.asgarnia.dialogue.rimmington

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.RegionManager.getLocalNpcs
import core.plugin.Initializable

@Initializable
class HengelDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player("Hello.")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                npc("What are you doing here?")
                stage = 1
            }

            1 -> {
                interpreter.sendOptions(
                    "Select an Option",
                    "I'm just wandering around.",
                    "I was hoping you'd give me some free stuff.",
                    "I've come to kill you."
                )
                stage = 2
            }

            2 -> if (buttonId == 1) {
                player("I'm just wondering around.")
                stage = 3
            } else if (buttonId == 2) {
                player("I was hoping you'd give me some free stuff.")
                stage = 7
            } else if (buttonId == 3) {
                player("I've come to kill you.")
                stage = 9
            }

            3 -> {
                npc("You do realise you're wandering around in my house?")
                stage++
            }

            4 -> {
                player("Yep.")
                stage++
            }

            5 -> {
                npc("Well please get out!")
                stage++
            }

            6 -> {
                player("Sheesh, keep your wig on!")
                stage = 605
            }

            7 -> {
                npc("No, I jolly well wouldn't!", "Get out of my house")
                stage++
            }

            8 -> {
                player("Meanie!")
                stage = 605
            }

            9 -> {
                npc.sendChat("Aaaaarrgh!")
                for (npc1 in getLocalNpcs(player)) {
                    if (npc1.name.equals("anja", ignoreCase = true)) {
                        npc1.sendChat("Eeeek!")
                        break
                    }
                }
                end()
            }

            605 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HENGEL_2683)
    }
}
