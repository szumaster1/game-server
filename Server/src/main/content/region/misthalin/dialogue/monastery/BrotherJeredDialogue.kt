package content.region.misthalin.dialogue.monastery

import core.api.addItem
import cfg.consts.Items
import cfg.consts.NPCs
import core.api.inInventory
import core.api.removeItem
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.global.Skillcape.isMaster
import core.game.global.Skillcape.purchase
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Brother Jered dialogue.
 */
@Initializable
class BrotherJeredDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (inInventory(player, Items.UNBLESSED_SYMBOL_1716, 1)) {
            player("Can you bless this symbol for me?")
            stage = 10
            return true
        }
        if (inInventory(player, Items.HOLY_ELIXIR_13754, 1) && inInventory(player, Items.SPIRIT_SHIELD_13734, 1)) {
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
                npc("Yes! Praise he who brings life to this world.").also { stage++ }
                stage = 1
            }

            1, 12 -> end()
            2 -> npc("Certainly! Right when you give me 99000 coins.").also { stage++ }
            3 -> options("Okay, here you go.", "No").also { stage++ }
            4 -> when (buttonId) {
                1 -> player("Okay, here you go.").also { stage++ }
                2 -> end()
            }

            5 -> {
                if (purchase(player, Skills.PRAYER)) {
                    npc("There you go! Enjoy.")
                }
                end()
            }

            10 -> npc("Sure thing! Just give me a moment, here...").also { stage++ }
            11 -> {
                npc("There we go! I have laid the blessing of", "Saradomin upon it.")
                if (removeItem(player, Item(Items.UNBLESSED_SYMBOL_1716, 1))) {
                    addItem(player, Items.HOLY_SYMBOL_1718, 1)
                    stage++
                } else {
                    end()
                    sendMessage(player, "You don't have required items.")
                }
            }

            100 -> npc("Yes I certainly can, but", "for 1,000,000 coins.").also { stage++ }
            101 -> options("Okay, sounds good.", "Oh, no thanks..").also { stage++ }
            102 -> when (buttonId) {
                1 -> if (inInventory(player, Items.COINS_995, 1000000)) {
                    player("Okay, sounds good!").also { stage = 110 }
                } else {
                    player("I'd like to but I don't have", "the coin.").also { stage = 120 }
                }

                2 -> player("Oh. No, thank you, then.").also { stage = END_DIALOGUE }
            }

            110 -> {
                npc("Here you are.")
                if (player.inventory.remove(Item(Items.HOLY_ELIXIR_13754), Item(Items.SPIRIT_SHIELD_13734), Item(995, 1000000))) {
                    player.inventory.add(Item(Items.BLESSED_SPIRIT_SHIELD_13736))
                }
                stage++
            }

            111 -> player("Thank you!").also { stage = END_DIALOGUE }
            120 -> npc("That's too bad then.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BROTHER_JERED_802)
    }
}