package content.global.skill.production.cooking.brewing

import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.api.consts.Scenery
import core.api.consts.Vars
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item

class FermentingVat : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, Items.BUCKET_OF_WATER_1929, Scenery.FERMENTING_VAT_7473) { player, used, with ->
            setAttribute(player, addWaterAttribute, baseValue)
            if(removeItem(player, used.asItem())) {
                face(player, with.asScenery())
                animate(player, Animations.POUR_BUCKET_OVER_GROUND_2283)
                replaceSlot(player, used.asItem().slot, Item(Items.BUCKET_1925, 1))
                player.incrementAttribute(addWaterAttribute)
            }
            if(getAttribute(player, addWaterAttribute, baseValue) == 2) {
                setVarbit(player, Vars.VARBIT_SCENERY_BREWING_VAT, 1)
            }
            return@onUseWith true
        }

        onUseWith(IntType.SCENERY, Items.BARLEY_MALT_6008, Scenery.FERMENTING_VAT_7438) { player, used, with ->
            return@onUseWith true
        }



    }
    companion object {
        val baseValue = 0
        val addWaterAttribute = "brewing:add-water"
        val addBarleyMaltAttribute = "brewing:add-barley-malt"
    }
}