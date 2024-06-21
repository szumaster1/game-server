package content.region.fremennik.dialogue.neitiznot

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.game.node.item.Item

class MawnisBurowgarDialogue(player: Player? = null) : Dialogue(player) {


    override fun open(vararg args: Any): Boolean {
        npc("It makes me proud to know that the helm of my", "ancestors will be worn in battle.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                npc("I thank you on behalf of all my kinsmen Talvald.")
                stage++
            }

            1 -> {
                if (player.hasItem(HELM) || player.hasItem(Item(10843))) {
                    end()
                }
                player("Ah yes, about that beautiful helmet.")
                stage++
            }

            2 -> {
                npc("You mean the priceless heirloom that I have to you as", "a sign of my trust and gratitude?")
                stage++
            }

            3 -> {
                player("Err yes, that one. I may have mislaid it.")
                stage++
            }

            4 -> {
                npc(
                    "It's a good job I have alert and loyal men who notice",
                    "when something like this is left lying around and picks it",
                    "up."
                )
                stage++
            }

            5 -> {
                npc("I'm afraid I'm going to have to charge you a", "50,000GP handling cost.")
                stage++
            }

            6 -> {
                if (!player.inventory.contains(995, 50000)) {
                    player("I don't have that much money on me.")
                    stage++
                }
                if (player.inventory.remove(Item(995, 50000))) {
                    player.inventory.add(HELM, player)
                    interpreter.sendItemMessage(HELM, "The Burgher hands you his crown.")
                    stage = 8
                }
            }

            7 -> {
                npc("Well, come back when you do.")
                stage++
            }

            8 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MAWNIS_BUROWGAR_5504)
    }

    companion object {
        private val HELM = Item(10828)
    }
}