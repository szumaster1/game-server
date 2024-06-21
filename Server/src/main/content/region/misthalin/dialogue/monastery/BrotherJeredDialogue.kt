package content.region.misthalin.dialogue.monastery

import core.api.consts.Items
import core.game.dialogue.Dialogue
import core.game.global.Skillcape.isMaster
import core.game.global.Skillcape.purchase
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.plugin.Initializable

@Initializable
class BrotherJeredDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (player.inventory.contains(Items.UNBLESSED_SYMBOL_1716, 1)) {
            player("Can you bless this symbol for me?")
            stage = 10
            return true
        }
        if (player.inventory.contains(
                Items.HOLY_ELIXIR_13754,
                1
            ) && player.inventory.contains(Items.SPIRIT_SHIELD_13734, 1)
        ) {
            player("Can you bless this spirit shield for me?")
            stage = 100
            return true
        }
        stage = if (isMaster(player, Skills.PRAYER)) {
            player("Can I buy a Skillcape of Prayer?")
            2
        } else {
            player("Praise to Saradomin!")
            0
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                npc("Yes! Praise he who brings life to this world.")
                stage = 1
            }

            1, 12 -> end()
            2 -> {
                npc("Certainly! Right when you give me 99000 coins.")
                stage = 3
            }

            3 -> {
                options("Okay, here you go.", "No")
                stage = 4
            }

            4 -> when (buttonId) {
                1 -> {
                    player("Okay, here you go.")
                    stage = 5
                }

                2 -> end()
            }

            5 -> {
                if (purchase(player, Skills.PRAYER)) {
                    npc("There you go! Enjoy.")
                }
                stage = 6
            }

            6 -> end()
            10 -> {
                npc("Sure thing! Just give me a moment, here...")
                stage++
            }

            11 -> {
                npc("There we go! I have laid the blessing of", "Saradomin upon it.")
                player.inventory.remove(Item(Items.UNBLESSED_SYMBOL_1716))
                player.inventory.add(Item(Items.HOLY_SYMBOL_1718))
                stage++
            }

            100 -> {
                npc("Yes I certainly can, but", "for 1,000,000 coins.")
                stage++
            }

            101 -> {
                options("Okay, sounds good.", "Oh, no thanks..")
                stage++
            }

            102 -> when (buttonId) {
                1 -> stage = if (player.inventory.contains(995, 1000000)) {
                    player("Okay, sounds good!")
                    110
                } else {
                    player("I'd like to but I don't have", "the coin.")
                    120
                }

                2 -> {
                    player("Oh. No, thank you, then.")
                    stage = 1000
                }
            }

            110 -> {
                npc("Here you are.")
                if (player.inventory.remove(
                        Item(Items.HOLY_ELIXIR_13754),
                        Item(Items.SPIRIT_SHIELD_13734),
                        Item(995, 1000000)
                    )
                ) {
                    player.inventory.add(Item(Items.BLESSED_SPIRIT_SHIELD_13736))
                }
                stage++
            }

            111 -> {
                player("Thank you!")
                stage = 1000
            }

            120 -> {
                npc("That's too bad then.")
                stage = 1000
            }

            1000 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(802)
    }
}