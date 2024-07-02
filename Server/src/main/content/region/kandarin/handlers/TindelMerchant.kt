package content.region.kandarin.handlers

import content.data.item.BrokenItem
import content.data.item.BrokenItem.getRepair
import content.region.kandarin.dialogue.TindelMerchantDialogue
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.api.consts.Sounds
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Location
import core.utilities.RandomFunction

class TindelMerchant : InteractionListener {

    override fun defineListeners() {
        /*
         *  Antique shop ring bell interaction.
         */

        on(ANTIQUE_SHOP_STALL, IntType.SCENERY, "ring-bell") { player, _ ->
            playGlobalAudio(player.location, BELL_SOUND)
            sendDialogue(player, "You ring for attention.")
            queueScript(player, 1, QueueStrength.SOFT) {
                openDialogue(player, TindelMerchantDialogue())
                return@queueScript stopExecuting(player)
            }
            return@on true
        }

        /*
         *  Interaction for NPC before One Small Favour.
         */

        on(TINDEL, IntType.NPC, "talk-to", "Give-Sword") { player, _ ->
            when (getUsedOption(player)) {
                "talk-to" -> openDialogue(player, TindelMerchantDialogue())
                "give-sword" -> exchangeRustyWeapon(player)
                else -> sendMessage(player, "You can't reach!")
            }
            return@on true
        }
    }

    /*
     * Replaces the NPC destination for interaction in front of the stall.
     */

    override fun defineDestinationOverrides() {
        setDest(IntType.NPC, intArrayOf(TINDEL), "talk-to", "give-sword") { _, _ ->
            return@setDest Location(2678, 3152, 0)
        }
    }

    companion object {
        const val TINDEL = NPCs.TINDEL_MARCHANT_1799
        const val BELL_SOUND = Sounds.BELL_2192
        const val ANTIQUE_SHOP_STALL = Scenery.ANTIQUES_SHOP_STALL_5831
        private const val RUSTY_SWORD = Items.RUSTY_SWORD_686
        private const val RUSTY_SCIMITAR = Items.RUSTY_SCIMITAR_6721
        private const val FAKE_COINS = Items.COINS_8896

        /*
         * Rusty sword exchange interaction, calculates a success based
         * on the player's Smithing skill level.
         */

        fun exchangeRustyWeapon(player: Player): Boolean {
            if (!anyInInventory(player, RUSTY_SWORD, RUSTY_SCIMITAR)) {
                sendNPCDialogue(player, TINDEL, "Sorry my friend, but you don't seem to have any swords that need to be identified.", FacialExpression.HALF_GUILTY)
                return false
            }
            if (!inInventory(player, Items.COINS_995, 100)) {
                sendNPCDialogue(player, TINDEL, "Sorry, you don't have enough coins.", FacialExpression.HALF_GUILTY)
                return false
            }
            var rustySword: Item? = null
            var repairedItem: Item? = null
            val sendItem = if (inInventory(player, RUSTY_SWORD)) RUSTY_SWORD else RUSTY_SCIMITAR
            sendDoubleItemDialogue(player, sendItem, FAKE_COINS, "You hand Tindel 100 coins plus the ${getItemName(sendItem).lowercase()}.")
            addDialogueAction(player) { player, button ->
                if (button >= 1) {
                    val chance = RandomFunction.getSkillSuccessChance(50.0, 100.0, getStatLevel(player, Skills.SMITHING))
                    if (inInventory(player, RUSTY_SWORD, 1)) {
                        rustySword = Item(RUSTY_SWORD, 1)
                        repairedItem = getRepair(BrokenItem.EquipmentType.SWORDS)
                    } else if (inInventory(player, RUSTY_SCIMITAR, 1)) {
                        rustySword = Item(RUSTY_SCIMITAR, 1)
                        repairedItem = getRepair(BrokenItem.EquipmentType.SCIMITARS)
                    } else {
                        sendNPCDialogue(player, TINDEL, "Sorry my friend, but you don't seem to have any swords that need to be identified.", FacialExpression.HALF_GUILTY)
                    }
                    removeItem(player, Item(Items.COINS_995, 100))
                    removeItem(player, rustySword)
                    if (RandomFunction.random(0.0, 100.0) < chance) {
                        repairedItem?.let { sendItemDialogue(player, it.id, "Tindel gives you a ${getItemName(it.id).lowercase()}.") }
                        addItem(player, repairedItem!!.id)
                    } else {
                        sendNPCDialogue(player, TINDEL, "Sorry my friend, but the item wasn't worth anything. I've disposed of it for you.", FacialExpression.HALF_GUILTY)
                    }
                }
            }
            return true
        }
    }
}
