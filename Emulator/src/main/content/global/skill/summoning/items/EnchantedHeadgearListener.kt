package content.global.skill.summoning.items

import core.api.*
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.node.item.Item
import org.rs.consts.Animations
import org.rs.consts.NPCs

class EnchantedHeadgearListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handles enchant option.
         */

        on(NPCs.PIKKUPSTIX_6970, IntType.NPC, "Enchant") { player, _ ->
            sendNPCDialogue(player, NPCs.PIKKUPSTIX_6970, "What would you like disenchanted or enchanted?")
            addDialogueAction(player) { _, _ ->
                sendItemSelect(player, "Choose") { index, slot ->
                    enchant(player, index, slot)
                    return@sendItemSelect
                }
            }
            return@on true
        }

        /*
         * Handles un-charge option for charged items.
         */

        on(chargedIDs, IntType.ITEM, "Uncharge") { player, node ->
            val enchantId = EnchantedHeadgear.forId(node.id) ?: return@on true
            sendMessages(player, "You remove the scrolls. You will need to use a Summoning scroll on it to charge the", "headgear up once more.")
            replaceSlot(player, node.asItem().slot, Item(enchantId.enchantedItem))

            /*
             * Scrolls: addItem(player, Items.NULL_5150)
             */

            return@on true
        }
    }

    companion object {
        private val chargedIDs = EnchantedHeadgear.values().map { it.chargedItem }.toIntArray()

        /**
         * Enchants the headgear for the player.
         *
         * @param player The player who is enchanting the item.
         * @param optionIndex The option index.
         * @param slot The item to be enchanted.
         */
        @JvmStatic
        fun enchant(player: Player, optionIndex: Int, slot: Int): Boolean {
            val item = EnchantedHeadgear.forId(slot) ?: return false
            return when (optionIndex) {
                0,155 -> {
                    /*
                     if(getStatLevel(player, Skills.SUMMONING) < enchant!!.requiredLevel) {
                         sendNPCDialogue(player, NPCs.PIKKUPSTIX_6970, "They had us in the first half, not gonna lie.")
                         return false
                     }
                     */
                    lock(player, 1)
                    lockMovement(findLocalNPC(player, NPCs.PIKKUPSTIX_6970)!!, 1)
                    queueScript(player, 1, QueueStrength.WEAK) {
                        visualize(player, Animations.CAST_SPELL_711, 1575)
                        replaceSlot(player, slot, Item(item.enchantedItem))
                        sendNPCDialogueLines(player, NPCs.PIKKUPSTIX_6970, FacialExpression.NEUTRAL, false, "Good choice. Here you go, you can now store spells on", "it.")
                        return@queueScript stopExecuting(player)
                    }
                    return true
                }
                else -> false
            }
        }

    }

}