package content.global.skill.combat.equipment.gloves

import org.rs.consts.Items
import core.api.sendMessage
import core.api.toIntArray
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import kotlin.math.min

/**
 * Fist of guthix gloves listener.
 */
class FOGGlovesListener : InteractionListener {

    companion object {
        val MAX_CHARGES = intArrayOf(100, 100, 100, 100, 1000, 1000, 1000, 1000, 1000, 1000)
        private val FOG_GLOVES = (Items.IRIT_GLOVES_12856..Items.EARTH_RUNECRAFTING_GLOVES_12865).toIntArray()
    }

    override fun defineListeners() {

        on(FOG_GLOVES, IntType.ITEM, "inspect") { player, node ->
            sendMessage(player, "${node.name}: ${min(node.asItem().charge, MAX_CHARGES[node.id - Items.IRIT_GLOVES_12856])} charge left.")
            return@on true
        }
    }
}
