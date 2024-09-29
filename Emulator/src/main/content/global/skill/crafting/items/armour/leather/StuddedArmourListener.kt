package content.global.skill.crafting.items.armour.leather

import core.api.*
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import org.rs.consts.Items

class StuddedArmourListener : InteractionListener {
    val use = StuddedArmour.values.map { it.item.id }.toIntArray()

    override fun defineListeners() {
        onUseWith(IntType.ITEM, use, Items.STEEL_STUDS_2370) { player, used, with ->
            val product = StuddedArmour.product[with.asItem()] ?: return@onUseWith false
            val handler: SkillDialogueHandler =
                object : SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, use) {
                    override fun create(amount: Int, index: Int) {
                        submitIndividualPulse(
                            entity = player,
                            pulse = StuddedArmourPulse(player, used.asItem(), product, amount)
                        )
                    }

                    override fun getAll(index: Int): Int {
                        return amountInInventory(player, Items.STEEL_STUDS_2370)
                    }
                }
            handler.open()
            return@onUseWith true
        }
    }


    /**
     * Represents a type of studded armour in the game.
     */
    enum class StuddedArmour(val item: Item, val studded: Item, val level: Int, val experience: Double) {
        CHAPS(
            item = Item(Items.LEATHER_CHAPS_1095),
            studded = Item(Items.STUDDED_CHAPS_1097),
            level = 44,
            experience = 42.0
        ),
        BODY(
            item = Item(Items.LEATHER_BODY_1129),
            studded = Item(Items.STUDDED_BODY_1133),
            level = 41,
            experience = 40.0
        );

        companion object {
            val values = enumValues<StuddedArmour>()
            val product = values.associateBy { it.item }
        }
    }
}
