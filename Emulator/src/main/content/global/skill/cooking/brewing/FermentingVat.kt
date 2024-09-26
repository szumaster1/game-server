package content.global.skill.cooking.brewing

import core.api.*
import org.rs.consts.Animations
import org.rs.consts.Items
import org.rs.consts.Scenery
import org.rs.consts.Vars
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import core.game.world.map.Location

/**
 * Listener for brewing.
 */
class FermentingVat : InteractionListener {

    val fermentingVat = 7437
    val valve = 7442
    val location = Location.create(2916, 10193, 1)

    val VARBIT_736 = 736
    val VARBIT_738 = 738

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, Items.BUCKET_OF_WATER_1929, Scenery.FERMENTING_VAT_7473) { player, used, with ->
            setAttribute(player, addWaterAttribute, baseValue)
            if (removeItem(player, used.asItem())) {
                face(player, with.asScenery())
                animate(player, Animations.POUR_BUCKET_OVER_GROUND_2283)
                replaceSlot(player, used.asItem().slot, Item(Items.BUCKET_1925, 1))
                player.incrementAttribute(addWaterAttribute)
            }
            if (getAttribute(player, addWaterAttribute, baseValue) == 2) {
                setVarbit(player, Vars.VARBIT_SCENERY_BREWING_VAT, 1)
            }
            return@onUseWith true
        }

        onUseWith(IntType.SCENERY, Items.BARLEY_MALT_6008, Scenery.FERMENTING_VAT_7438) { player, used, with ->
            return@onUseWith true
        }

        // You add some water to the vat.
        // This isn't the right time to be adding "name" hops.
        // You don't need to add ale yeast at this point.
        // You add some barley malt to the vat.
        // You add some "name" hops to the vat.
        // You add a pot of ale yeast into the vat.
        // The conents have begun to ferment.
        // You turn the valve.
        // The vat is filled with Dragon Bitter.
        // You pour a glass of Dragon Bitter.
        // The barrel is now full of Dragon Bitter.
        // The barrel is now empty.


    }

    companion object {
        val baseValue = 0
        val addWaterAttribute = "brewing:add-water"
        val addBarleyMaltAttribute = "brewing:add-barley-malt"
    }
}