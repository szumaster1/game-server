package content.global.skill.crafting.items.armour.leather

import core.api.amountInInventory
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import org.rs.consts.Items

class StuddedArmourListener : InteractionListener {

    private val leatherItem = StuddedArmour.values().map { it.leather }.toIntArray()

    override fun defineListeners() {
        onUseWith(IntType.ITEM, leatherItem, Items.STEEL_STUDS_2370) { player, used, _ ->
            val item = StuddedArmour.forId(used.id) ?: return@onUseWith true
            val handler: SkillDialogueHandler =
                object : SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, Item(item.product)) {
                    override fun create(amount: Int, index: Int) {
                        player.pulseManager.run(StuddedArmourPulse(player, Item(item.leather), item, amount))
                    }

                    override fun getAll(index: Int): Int {
                        return amountInInventory(player, used.id)
                    }
                }
            handler.open()
            return@onUseWith true
        }
    }
}
