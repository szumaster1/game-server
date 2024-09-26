package content.global.skill.cooking.type.meat

import core.api.addItem
import org.rs.consts.Items
import core.api.getStatLevel
import core.api.removeItem
import core.api.sendDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills

class SkeweredMeatListener : InteractionListener {

    val skewered = SkeweredMeat.values().map { it.raw.id }.toIntArray()

    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.IRON_SPIT_7225, *skewered) { player, used, with ->
            if (getStatLevel(player, Skills.FIREMAKING) < 20) {
                sendDialogue(player, "You meed a Firemaking level of at least 20 in order to do this.")
                return@onUseWith false
            }
            val item = SkeweredMeat.forItem(used.asItem())
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                addItem(player, item!!.product.id)
            }
            return@onUseWith true
        }
    }
}