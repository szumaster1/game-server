package content.global.skill.summoning.items

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import org.rs.consts.NPCs

class EnchantedHeadgearListener : InteractionListener {

    val gearIDs = EnchantedHeadgear.values().map { it.baseItem }.toIntArray()
    val chargedIDs = EnchantedHeadgear.values().map { it.chargedItem }.toIntArray()

    override fun defineListeners() {

        /*
         * Handles enchant option.
         */

        on(NPCs.PIKKUPSTIX_6970, IntType.NPC, "Enchant") { player, _ ->
            sendNPCDialogue(player, NPCs.PIKKUPSTIX_6970, "What would you like disenchanted or enchanted?")

            return@on true
        }

        /*
         * Handles uncharge option for charged items.
         */

        on(chargedIDs, IntType.ITEM, "Uncharge") { player, node ->
            val enchantId = EnchantedHeadgear.forId(node.id) ?: return@on true
            sendMessages(player, "You remove the scrolls. You will need to use a Summoning scroll on it to charge the", "headgear up once more.")
            replaceSlot(player, node.asItem().slot, Item(gearIDs[enchantId.emptyItem]))

            /*
             * Scrolls: addItem(player, Items.NULL_5150)
             */

            return@on true
        }
    }

    companion object {
        /**
         * Enchants the headgear for the player.
         *
         * @param player The player who is enchanting the item.
         * @param item The item to be enchanted.
         */

    }
}