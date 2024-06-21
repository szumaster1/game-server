package content.global.skill.production.runecrafting.abyss

import content.global.skill.production.runecrafting.PouchManager.RCPouch
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable

@Initializable
class DarkMageDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (args.size >= 2) {
            if (repair()) {
                npc("There, I have repaired your pouches.", "Now leave me alone. I'm concentrating.")
                stage = 30
                return true
            } else {
                npc("You don't seem to have any pouches in need of repair.", "Leave me alone.")
                stage = 30
                return true
            }
        }
        player("Hello there.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                npc("Quiet!", "You must not break my concentration!")
                stage++
            }

            1 -> {
                options("Why not?", "What are you doing here?", "Can you repair my pouches?", "Ok, Sorry")
                stage++
            }

            2 -> when (buttonId) {
                1 -> {
                    player("Why not?")
                    stage = 10
                }

                2 -> {
                    player("What are you doing here?")
                    stage = 20
                }

                3 -> {
                    player("Can you repair my pouches, please?")
                    stage = 50
                }

                4 -> {
                    player("Ok, sorry.")
                    stage = 30
                }
            }

            10 -> {
                npc(
                    "Well, if my concentration is broken while keeping this",
                    "gate open, then if we are lucky, everyone within a one",
                    "mile radius will either have their heads explode, or will be",
                    "consumed internally by the creatures of the Abyss."
                )
                stage++
            }

            11 -> {
                player("Erm...", "And if we are unlucky?")
                stage++
            }

            12 -> {
                npc(
                    "If we are unlucky, then the entire universe will begin",
                    "to fold in upon itself, and all reality as we know it will",
                    "be annihilated in a single stroke."
                )
                stage++
            }

            13 -> {
                npc("So leave me alone!")
                stage = 30
            }

            20 -> {
                npc(
                    "Do you mean what am I doing here in Abyssal space,",
                    "Or are you asking me what I consider my ultimate role",
                    "to be in this voyage that we call life?"
                )
                stage++
            }

            21 -> {
                player("Um... the first one.")
                stage++
            }

            22 -> {
                npc(
                    "By remaining here and holding this portal open, I am",
                    "providing a permanent link between normal space and",
                    "this strange dimension that we call Abyssal space."
                )
                stage++
            }

            23 -> {
                npc(
                    "As long as this spell remains in effect, we have the",
                    "capability to teleport into abyssal space at will."
                )
                stage++
            }

            24 -> {
                npc("Now leave me be!", "I can afford no distraction in my task!")
                stage = 30
            }

            30 -> end()
            50 -> {
                npc("Fine, fine! Give them here.")
                stage++
            }

            51 -> {
                repair()
                npc("There, I've repaired them all.", "Now get out of my sight!")
                stage = 30
            }
        }
        return true
    }

    /**
     * Repairs rune pouches.
     */
    private fun repair(): Boolean {
        player.pouchManager.pouches.forEach { (id: Int, pouch: RCPouch) ->
            pouch.currentCap = pouch.capacity
            pouch.charges = 10
            var essence: Item? = null
            if (!pouch.container.isEmpty) {
                val ess = pouch.container[0].id
                val amount = pouch.container.getAmount(ess)
                essence = Item(ess, amount)
            }
            pouch.remakeContainer()
            if (essence != null) {
                pouch.container.add(essence)
            }
            if (id != 5509) {
                if (player.inventory.contains(id + 1, 1)) {
                    player.inventory.remove(Item(id + 1, 1))
                    player.inventory.add(Item(id, 1))
                }
                if (player.bank.contains(id + 1, 1)) {
                    player.bank.remove(Item(id + 1, 1))
                    player.bank.add(Item(id, 1))
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(2262)
    }
}