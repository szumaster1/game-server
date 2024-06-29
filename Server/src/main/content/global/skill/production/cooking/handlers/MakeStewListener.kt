package content.global.skill.production.cooking.handlers

import core.api.consts.Items
import core.api.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class MakeStewListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, Items.UNCOOKED_STEW_2001, *RANGE) { player, used, _ ->
            player.dialogueInterpreter.open(43989, used.asItem().id, "stew")
            return@onUseWith true
        }
    }

    companion object {
        val RANGE = intArrayOf(Scenery.COOKING_RANGE_114, Scenery.RANGE_2728, Scenery.RANGE_2729, Scenery.RANGE_2730, Scenery.RANGE_2731, Scenery.COOKING_RANGE_2859, Scenery.RANGE_3039, Scenery.COOKING_RANGE_4172, Scenery.COOKING_RANGE_5275, Scenery.COOKING_RANGE_8750, Scenery.RANGE_9682, Scenery.RANGE_12102, Scenery.RANGE_14919, Scenery.COOKING_RANGE_16893, Scenery.RANGE_21792, Scenery.COOKING_RANGE_22154, Scenery.RANGE_22713, Scenery.RANGE_22714, Scenery.RANGE_24283, Scenery.RANGE_24284, Scenery.RANGE_25730, Scenery.RANGE_33500, Scenery.COOKING_RANGE_34410, Scenery.RANGE_34495, Scenery.RANGE_34546, Scenery.COOKING_RANGE_34565, Scenery.RANGE_36973, Scenery.RANGE_37629)
    }
}
