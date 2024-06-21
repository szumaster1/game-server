package content.region.kandarin.handlers.guilds.ranging.dialogue

import content.global.skill.production.crafting.TanningProduct
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class LeatherWorkerDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        player("Hello.")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                npc("Can I help you?")
                stage = 1
            }

            1 -> {
                options("What do you do here?", "No thanks.")
                stage = 2
            }

            2 -> when (buttonId) {
                1 -> {
                    player("What do you do here?")
                    stage = 10
                }

                2 -> {
                    player("No thanks.")
                    stage = 20
                }
            }

            10 -> {
                npc("Well, I can cure plain cowhides into pieces of leather", "ready for crafting.")
                stage = 11
            }

            11 -> {
                npc("I work with ordinary, hard or dragonhide leather and", "also snakeskin.")
                stage = 12
            }

            12 -> {
                end()
                TanningProduct.open(player, 680)
            }

            20 -> {
                npc("Suit yourself.")
                stage = 21
            }

            21 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LEATHERWORKER_680)
    }
}