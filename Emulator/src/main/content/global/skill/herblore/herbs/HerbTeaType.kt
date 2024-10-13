package content.global.skill.herblore.herbs

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import org.rs.consts.Items
import org.rs.consts.QuestName

class HerbTeaType : InteractionListener {

    override fun defineListeners() {

        onUseWith(IntType.ITEM, Items.BOWL_OF_HOT_WATER_4456, Items.EMPTY_CUP_1980) { player, used, with ->
            replaceSlot(player, used.asItem().slot, Item(Items.BOWL_1923))
            replaceSlot(player, with.asItem().slot, Item(Items.CUP_OF_HOT_WATER_4460))
            sendMessage(player, "You pour the hot water into the tea cup.")
            return@onUseWith true
        }

        onUseWith(IntType.ITEM, ingredientsIDs, *herbMixes) { player, used, with ->
            if (!hasRequirement(player, QuestName.ONE_SMALL_FAVOUR, false)) {
                sendMessage(player, "You have no idea what effect this would have.")
                return@onUseWith true
            }

            if (getStatLevel(player, Skills.HERBLORE) < 18) {
                sendMessage(player, "You need a Herblore level of at least 18 in order to do this.")
                return@onUseWith true
            }

            val combineIngredients = mapOf(
                Items.CLEAN_HARRALANDER_255 to HerbTeaData(
                    firstItem = Items.CUP_OF_HOT_WATER_4460,
                    secondItem = Items.HERB_TEA_MIX_4464,
                    experience = 14.0,
                    message = "You place the harralander into the cup of hot tea."
                ),
                Items.CLEAN_GUAM_249 to HerbTeaData(
                    firstItem = Items.HERB_TEA_MIX_4464,
                    secondItem = Items.HERB_TEA_MIX_4466,
                    experience = 14.5,
                    message = "You place the guam into the cup of hot tea."
                ),
                Items.CLEAN_GUAM_249 to HerbTeaData(
                    firstItem = Items.HERB_TEA_MIX_4466,
                    secondItem = Items.HERB_TEA_MIX_4468,
                    experience = 15.0,
                    message = "You place the guam into the cup of hot tea."
                ),
                Items.CLEAN_MARRENTILL_251 to HerbTeaData(
                    firstItem = Items.HERB_TEA_MIX_4468,
                    secondItem = Items.GUTHIX_REST3_4419,
                    experience = 15.5,
                    message = "You place the marrentill into the steamy mixture and make Guthix Rest Tea."
                )
            )

            val itemMap = combineIngredients[used.id]
            if (itemMap != null && with.id == itemMap.firstItem) {
                if (removeItem(player, used.asItem())) {
                    sendMessage(player, itemMap.message)
                    rewardXP(player, Skills.HERBLORE, itemMap.experience)
                    replaceSlot(player, with.asItem().slot, Item(itemMap.secondItem))
                }
            } else {
                setTitle(player, 2)
                sendDialogueOptions(player, "Combining those will ruin the tea, are you sure?", "Yes.", "No.")
                addDialogueAction(player) { _, button ->
                    if (button == 2) {
                        replaceSlot(player, used.asItem().slot, Item(Items.RUINED_HERB_TEA_4462))
                    } else {
                        closeDialogue(player)
                    }
                }
            }

            return@onUseWith true
        }
    }

    companion object {
        val herbMixes = intArrayOf(
            Items.HERB_TEA_MIX_4464,
            Items.HERB_TEA_MIX_4466,
            Items.HERB_TEA_MIX_4468,
            Items.HERB_TEA_MIX_4470,
            Items.HERB_TEA_MIX_4472,
            Items.HERB_TEA_MIX_4474,
            Items.HERB_TEA_MIX_4476,
            Items.HERB_TEA_MIX_4478,
            Items.HERB_TEA_MIX_4480,
            Items.HERB_TEA_MIX_4482,
            Items.CUP_OF_HOT_WATER_4460,
        )
        val ingredientsIDs = intArrayOf(
            Items.CLEAN_HARRALANDER_255,
            Items.CLEAN_GUAM_249,
            Items.CLEAN_MARRENTILL_251
        )
    }
}

data class HerbTeaData(val firstItem: Int, val secondItem: Int, val experience: Double, val message: String)