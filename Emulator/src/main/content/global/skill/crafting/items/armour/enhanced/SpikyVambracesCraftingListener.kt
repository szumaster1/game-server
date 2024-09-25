package content.global.skill.crafting.items.armour.enhanced

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import org.rs.consts.Items

class SpikyVambracesCraftingListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.KEBBIT_CLAWS_10113, *itemIDs) { player, _, with ->
            if (getStatLevel(player, Skills.CRAFTING) >= 32) {
                sendMessage(player, "You need a crafting level of 32 to craft this.")
                return@onUseWith false
            }
            when (with.id) {
                Items.LEATHER_VAMBRACES_1063 -> craftVambraces(player, with.id, Items.SPIKY_VAMBRACES_10077, "leather")
                Items.GREEN_DHIDE_VAMB_1065 ->  craftVambraces(player, with.id, Items.GREEN_SPIKY_VAMBS_10079, "green dragonhide")
                Items.BLUE_DHIDE_VAMB_2487 ->   craftVambraces(player, with.id, Items.BLUE_SPIKY_VAMBS_10081, "blue dragonhide")
                Items.RED_DHIDE_VAMB_2489 ->    craftVambraces(player, with.id, Items.RED_SPIKY_VAMBS_10083, "red dragonhide")
                Items.BLACK_DHIDE_VAMB_2491 ->  craftVambraces(player, with.id, Items.BLACK_SPIKY_VAMBS_10085, "black dragonhide")
            }
            return@onUseWith true
        }
    }

    private fun craftVambraces(player: Player, vamb: Int, product: Int, vambLeather: String) {
        if (removeItem(player, vamb, Container.INVENTORY) && removeItem(player, Items.KEBBIT_CLAWS_10113, Container.INVENTORY)) {
            addItem(player, product)
            rewardXP(player, Skills.CRAFTING, 6.0)
            sendMessage(player, "You carefully attach the sharp claws to the $vambLeather vambraces.")
        }
    }

    companion object {
        private val itemIDs = intArrayOf(Items.LEATHER_VAMBRACES_1063, Items.GREEN_DHIDE_VAMB_1065, Items.BLUE_DHIDE_VAMB_2487, Items.RED_DHIDE_VAMB_2489, Items.BLACK_DHIDE_VAMB_2491)
    }
}
