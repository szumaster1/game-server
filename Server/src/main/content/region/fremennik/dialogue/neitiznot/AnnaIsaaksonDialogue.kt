package content.region.fremennik.dialogue.neitiznot

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class AnnaIsaaksonDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc("Hello visitor, how are you?")
        stage = 1
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            1 -> {
                player(
                    "Better than expected. Its a lot...nicer...here than I was",
                    "expecting. Everyone seems pretty happy."
                )
                stage = 2
            }

            2 -> {
                npc("Of course, the Burgher is strong and wise, and looks", "after us well")
                stage = 3
            }

            3 -> {
                player("I think some of those Jatizso citizens have got", "the wrong idea about this place.")
                stage = 10
            }

            10 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ANNE_ISAAKSON_5512)
    }
}