package content.global.handlers.item.equipment.gloves

import core.api.Container
import core.api.EquipmentSlot
import core.api.consts.Items
import core.api.getItemFromEquipment
import core.api.removeItem
import core.game.node.entity.player.Player
import core.tools.colorize
import kotlin.math.min

/**
 * F o g gloves manager
 *
 * @constructor F o g gloves manager
 */
class FOGGlovesManager {
    companion object {
        private val MAX_CHARGES = intArrayOf(100, 100, 100, 100, 1000, 1000, 1000, 1000, 1000, 1000)
        @JvmStatic
        fun updateCharges(player: Player, charges: Int = 1): Int {
            val gloves = getItemFromEquipment(player, EquipmentSlot.HANDS) ?: return 0
            gloves.charge = min(gloves.charge, MAX_CHARGES[gloves.id - Items.IRIT_GLOVES_12856])
            if (gloves.charge - charges <= 0) {
                removeItem(player, gloves, Container.EQUIPMENT)
                player.sendMessage(colorize("%RThe charges in your gloves have been used up and they crumble to dust."))
                return gloves.charge
            }
            gloves.charge -= charges
            return charges
        }
    }
}
