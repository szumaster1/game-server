package content.region.misthalin.dialogue.wizardstower

import core.api.consts.Items
import core.api.consts.NPCs
import core.api.sendInputDialogue
import core.game.component.Component
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.network.packet.PacketRepository
import core.network.packet.context.ChildPositionContext
import core.network.packet.outgoing.RepositionChild
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.StringUtils

/**
 * Represents the Wizard dialogue.
 */
@Initializable
class WizardDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc("Hello there, can I help you?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("What do you do here?", "What's that you're wearing?", "Can you make me some armour please?", "No thanks.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player("What do you do here?").also { stage = 10 }
                2 -> player("What's that you're wearing?").also { stage = 20 }
                3 -> player("Can you make me some armour please?").also { stage = 30 }
                4 -> player("No thanks.").also { stage = END_DIALOGUE }
            }

            30 -> npc("Certainly, what would like to me to make?").also { stage++ }
            31 -> {
                player.interfaceManager.openChatbox(Component(306))
                var count = 2
                val shift = 75
                PacketRepository.send(RepositionChild::class.java, ChildPositionContext(player, 306, 26, 10, shift))
                PacketRepository.send(RepositionChild::class.java, ChildPositionContext(player, 306, 22, 88, shift))
                PacketRepository.send(RepositionChild::class.java, ChildPositionContext(player, 306, 18, 288, shift))
                PacketRepository.send(
                    RepositionChild::class.java,
                    ChildPositionContext(player, 306, 14, 188, shift + 5)
                )
                PacketRepository.send(RepositionChild::class.java, ChildPositionContext(player, 306, 10, 380, shift))
                val indexes = intArrayOf(26, 22, 14, 18, 10)
                for (bark in SplitBark.values()) {
                    player.packetDispatch.sendItemZoomOnInterface(bark.itemId, 170, 306, count)
                    player.packetDispatch.sendString(
                        StringUtils.formatDisplayName(bark.name),
                        306,
                        indexes[bark.ordinal]
                    )
                    count++
                }
                stage++
            }

            32 -> {
                val bark = SplitBark.forButton(buttonId)
                if (bark == null) {
                    end()
                    return true
                }
                if (!player.inventory.contains(BARK.id, bark.amt)) {
                    val name =
                        if (bark == SplitBark.HELM) "a splitbark helm" else if (bark == SplitBark.BODY) "a splitbark body" else if (bark == SplitBark.BOOTS) "splitbark boots" else if (bark == SplitBark.GAUNTLETS) "splitbark gauntlets" else "splitbark legs"
                    npc("You need " + bark.amt + " pieces of bark for " + name + ".")
                    return true
                }
                val amount = getAmt(buttonId)
                if (amount == -1) {
                    sendInputDialogue(player, true, "Enter the amount:") { value: Any -> make(bark, value as Int) }
                    return true
                }
                make(bark, amount)
                end()
            }
            20 -> npc("Split-bark armour is special armour for mages, it's much", "more resistant to physical attacks than normal robes.", "It's actually very easy for me to make, but I've been", "having trouble getting hold of the pieces.").also { stage = 15 }
            10 -> npc("I've been studying the practice of making split-bark", "armour.").also { stage++ }
            11 -> options("Split-bark armour, what's that?", "Can you make me some?").also { stage++ }
            12 -> when (buttonId) {
                1 -> player("Split-bark armour, what's that?").also { stage++ }
                2 -> player("Can you make me some?").also { stage = 50 }
            }
            13 -> npc("Split-bark armour is special armour for mages, it's much", "more resistant to physical attacks than normal robes.", "It's actually very easy for me to make, but I've been", "having trouble getting hold of the pieces.").also { stage++ }
            14 -> options("Well good luck with that.", "Can you make me some?").also { stage++ }
            15 -> when (buttonId) {
                1 -> player("Well good luck with that.").also { stage = END_DIALOGUE }
                2 -> player("Can you make me some?").also { stage++ }
            }
            50 -> npc("I need bark from a hollow tree, and some fine cloth.", "Unfortunately both these items can be found in", "Morytania, especially the cloth which is found in the", "tombs of shades.").also { stage++ }
            51 -> npc("Of course I'd happily sell you some at a discounted", "price if you bring me those items.").also { stage++ }
            52 -> options("Ok, guess I'll go looking then!", "Ok, how much do I need?").also { stage++ }
            53 -> when (buttonId) {
                1 -> player("Ok, guess I'll go looking then!").also { stage = END_DIALOGUE }
                2 -> player("Ok, how much do I need?").also { stage++ }
            }
            54 -> npc("1 need 1 piece of each for either gloves or boots,", "2 pieces of each for a hat,", "3 pieces of each for leggings,", "and 4 pieces of each for a top.").also { stage++ }
            55 -> npc("I'll charge you 1,000 coins for either gloves or boots,", "6,000 coins for a hat", "32,000 coins for leggings,", "and 37,000 for a top.").also { stage++ }
            56 -> player("Ok, guess I'll go looking then!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.WIZARD_1263)
    }

    /**
     * This function creates a specific bark based on the input parameters.
     *
     * @param bark The type of bark to be created.
     * @param amount The quantity of bark to be produced.
     */
    fun make(bark: SplitBark, amount: Int) {
        val barkAmt = player.inventory.getAmount(BARK)
        if (barkAmt < amount * bark.amt) {
            npc("You don't have enough bark to make that many.")
            stage = 33
            return
        }
        if (amount < 1) {
            end()
            return
        }
        if (player.inventory.freeSlots() < amount) {
            player("Sorry, I don't seem to have enough inventory space.")
            stage = 33
            return
        }
        if (!player.inventory.contains(995, bark.cost * amount)) {
            player("Sorry, I don't seem to have enough coins.")
            stage = 33
            return
        }
        val money = Item(Items.COINS_995, bark.cost * amount)
        val barkRemove = Item(BARK.id, amount * bark.amt)
        if (player.inventory.remove(money) && player.inventory.remove(barkRemove)) {
            player.inventory.add(Item(bark.itemId, amount))
            npc("There you go, enjoy your new armour!")
            return
        }
        end()
    }

    /**
     * This function retrieves the amount associated with a specific button.
     *
     * @param buttonId The ID of the button for which the amount is requested.
     * @return The amount associated with the button.
     */
    fun getAmt(buttonId: Int): Int {
        var amount = -1
        when (buttonId) {
            10, 14, 18, 22, 26 -> amount = 1
            9, 13, 17, 21, 25 -> amount = 5
            8, 12, 16, 20, 24 -> amount = 10
            7, 11, 15, 19, 23 -> amount = -1
        }
        return amount
    }

    /**
     * Enum class representing different types of Split Bark.
     *
     * @param itemId The unique identifier of the Split Bark item.
     * @param cost The cost of the Split Bark.
     * @param amt The amount of Split Bark.
     * @param buttonId The identifier of the button associated with the Split Bark.
     * @constructor Represents a Split Bark with the specified properties.
     */
    enum class SplitBark(val itemId: Int, val cost: Int, val amt: Int, val buttonId: Int) {
        /**
         * Helm.
         */
        HELM(3385, 6000, 2, 9),

        /**
         * Body.
         */
        BODY(3387, 37000, 4, 13),

        /**
         * Legs.
         */
        LEGS(3389, 32000, 3, 17),

        /**
         * Gauntlets.
         */
        GAUNTLETS(3391, 1000, 1, 21),

        /**
         * Boots.
         */
        BOOTS(3393, 1000, 1, 25);

        companion object {
            fun forButton(buttonId: Int): SplitBark? {
                var bark: SplitBark? = null
                if (buttonId >= 7 && buttonId <= 10) {
                    bark = HELM
                }
                if (buttonId >= 11 && buttonId <= 14) {
                    bark = BODY
                }
                if (buttonId >= 15 && buttonId <= 18) {
                    bark = LEGS
                }
                if (buttonId >= 19 && buttonId <= 22) {
                    bark = GAUNTLETS
                }
                if (buttonId >= 23 && buttonId <= 26) {
                    bark = BOOTS
                }
                if (buttonId == 26) {
                    bark = HELM
                }
                return bark
            }
        }
    }

    companion object {
        private val BARK = Item(Items.BARK_3239)
    }
}
