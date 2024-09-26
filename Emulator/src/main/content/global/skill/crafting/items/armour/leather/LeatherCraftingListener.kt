package content.global.skill.crafting.items.armour.leather

import content.global.skill.crafting.Leather
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class LeatherCraftingListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, Leather.NEEDLE, *itemIDs) { player, used, with ->
            if (with.id == Leather.LEATHER || used.id == Leather.LEATHER) {
                Leather.SoftLeather.open(player)
            } else if (with.id == Leather.HARD_LEATHER || used.id == Leather.HARD_LEATHER) {
                player.dialogueInterpreter.open(48923, "hard")
            } else {
                player.dialogueInterpreter.open(48923, "dragon", used.id)
            }
            return@onUseWith true
        }

    }

    companion object {
        private val itemIDs = intArrayOf(
            Leather.LEATHER,
            Leather.HARD_LEATHER,
            Leather.GREEN_LEATHER,
            Leather.BLUE_LEATHER,
            Leather.RED_LEATHER,
            Leather.BLACK_LEATHER
        )
    }
}
