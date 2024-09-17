package content.global.skill.production.cooking.handlers

import org.rs.consts.Items
import org.rs.consts.Scenery
import core.api.inInventory
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Dairy churn listener.
 */
class DairyChurnListener : InteractionListener {

    override fun defineListeners() {
        on(CHURN, IntType.SCENERY, "churn") { player, _ ->
            if (!inInventory(player, Items.BUCKET_OF_MILK_1927, 1) && !inInventory(player, Items.POT_OF_CREAM_2130, 1) && !inInventory(player, Items.PAT_OF_BUTTER_6697, 1)) {
                sendMessage(player, "You need some milk, cream or butter to use in the churn.")
                return@on true
            }
            player.dialogueInterpreter.open(984374)
            return@on true
        }
    }

    companion object {
        val CHURN = intArrayOf(
            Scenery.DAIRY_CHURN_10093,
            Scenery.DAIRY_CHURN_10094,
            Scenery.DAIRY_CHURN_25720,
            Scenery.DAIRY_CHURN_34800,
            Scenery.DAIRY_CHURN_35931
        )
    }
}
