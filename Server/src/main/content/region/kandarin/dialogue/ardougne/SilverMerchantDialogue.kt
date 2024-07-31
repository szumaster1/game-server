package content.region.kandarin.dialogue.ardougne

import content.global.activity.enchkey.EnchantedKeyHelper
import content.region.kandarin.quest.makinghistory.MHUtils
import content.region.kandarin.quest.makinghistory.dialogue.SilverMerchantDialogueFile
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class SilverMerchantDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Erin, the Silver merchant, runs the
     * Ardougne Silver Stall in East Ardougne.
     * Location: 2659,3316
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "Silver! Silver! Best prices for buying and selling in all", "Kandarin!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val hasKey = hasAnItem(player, Items.ENCHANTED_KEY_6754).container != null
        val hasJournal = hasAnItem(player, Items.JOURNAL_6755).container == player.inventory
        when (stage) {
            0 -> if (getVarbit(player, MHUtils.PROGRESS) >= 1) {
                options("Yes please.", "No, thank you.", "Ask about the outpost.").also { stage = 2 }
            } else if (isQuestComplete(player, "Making History")) {
                npc("Hello, I hope Jorral was pleased with that Journal.").also { stage = 4 }
            } else {
                options("Yes please.", "No, thank you.").also { stage++ }
            }
            1 -> when (buttonId) {
                1 -> {
                    end()
                    openNpcShop(player, NPCs.SILVER_MERCHANT_569)
                }
                2 -> player(FacialExpression.NEUTRAL, "No, thank you.").also { stage = END_DIALOGUE }

            }
            2 -> when (buttonId) {
                1 -> {
                    end()
                    openNpcShop(player, NPCs.SILVER_MERCHANT_569)
                }
                2 -> player(FacialExpression.NEUTRAL, "No, thank you.").also { stage = END_DIALOGUE }
                3 -> {
                    end()
                    if (getQuestStage(player, "Making History") < 2) {
                        openDialogue(player, SilverMerchantDialogueFile(0))
                    } else if (getVarbit(player, MHUtils.ERIN_PROGRESS) == 1 || !hasKey) {
                        openDialogue(player, SilverMerchantDialogueFile(13))
                    } else if (inInventory(player, Items.CHEST_6759)) {
                        openDialogue(player, SilverMerchantDialogueFile(19))
                    } else if (getVarbit(player, MHUtils.ERIN_PROGRESS) in 1..3 && !hasJournal) {
                        player("I found a chest, but I lost it and any contents", "it had.").also { stage++ }
                    } else if (inInventory(player, Items.JOURNAL_6755)) {
                        openDialogue(player, SilverMerchantDialogueFile(21))
                    } else {
                        npc("Hello, I hope Jorral was pleased with that Journal.").also { stage = 4 }
                    }
                }
            }
            3 -> npc("Well I suggest you go back to where you found it.").also { stage = END_DIALOGUE }
            4 -> npc("I'm sure it's been a valuable find.").also { stage = END_DIALOGUE }
            5 -> npc("I'm sure it's been a valuable find.").also { stage++ }
            6 -> if (hasAnItem(player!!, Items.ENCHANTED_KEY_6754).exists()) {
                end()
                npc("You know you can use that enchanted key you have on","your keyring all over Gielinor. Who knows what you might find?")
            } else if (getAttribute(player!!, EnchantedKeyHelper.ENCHANTED_KEY_ATTR, 0) == 11) {
                player("Oh, You know that key you gave me?").also { stage = 11 }
            } else {
                player("What I came to ask was: I lost that key you gave me.").also { stage++ }
            }
            7 -> npc("Oh dear, luckily I found it, but it'll cost you 500gp","as I know it's valuable.").also { stage++ }
            8 -> options("Yes please.", "No thanks.").also { stage++ }
            9 -> when (buttonId) {
                1 -> player("Yes please.").also { stage++ }
                2 -> player("No thanks.").also { stage = END_DIALOGUE }
            }
            10 -> {
                end()
                if (freeSlots(player!!) < 0) {
                    npc("You don't have the space to carry it! Empty some space", "in your inventory.")
                } else if (!removeItem(player!!, Item(Items.COINS_995, 500))) {
                    npc("You don't have enough money, sorry.")
                } else {
                    npc("Thank you, enjoy!")
                    setAttribute(player!!, EnchantedKeyHelper.ENCHANTED_KEY_ATTR, 0)
                    addItemOrDrop(player!!, Items.ENCHANTED_KEY_6754, 1)
                }
            }
            11 -> npc("Yes?").also { stage++ }
            12 -> player("It dissolved!").also { stage++ }
            13 -> npc("Really? I suppose it served its purpose.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SILVER_MERCHANT_569)
    }
}
