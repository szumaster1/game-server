package content.minigame.puropuro

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import core.game.world.map.Direction
import core.game.world.map.RegionManager.getObject
import core.tools.RandomFunction
import org.rs.consts.Components
import org.rs.consts.Items
import org.rs.consts.Scenery

class ImpetuousImpulsesListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handles passing the magical wheat.
         */

        on(wheatSceneryIDs, IntType.SCENERY, "push-through") { player, node ->
            if (getStatLevel(player, Skills.HUNTER) < 17) {
                sendMessage(player, "You need a Hunting level of at least 17 to enter the maze.")
                return@on true
            }
            if (anyInInventory(player, Items.MAGIC_BOX_10025, Items.IMP_IN_A_BOX2_10027, Items.IMP_IN_A_BOX1_10028)) {
                sendDialogue(player, "Something prevents you from entering. You think the portal is offended by your imp boxes. They are not popular on imp and impling planes.")
                return@on true
            }
            val dest = node.location.transform(Direction.getLogicalDirection(player.location, node.location), 1)
            if (getObject(dest) != null) {
                sendMessage(player, "The wheat here seems unusually stubborn. You cannot push through.")
                return@on true
            }
            if (RandomFunction.random(2) == 0) {
                sendMessage(player, "You use your strength to push through the wheat.")
            } else {
                sendMessage(player, "You use your strength to push through the wheat. It's hard work though.")
            }
            setAttribute(player, "cantMove", true)
            forceMove(player, player.location, dest, 0, 265, null, 6595, null)
            return@on true
        }

        /*
         * Handles interface for impling scroll.
         */

        on(Items.IMPLING_SCROLL_11273, IntType.ITEM, "toggle-view") { player, _ ->
            if (!inZone(player,"puro puro")) {
                sendMessage(player, "You can't do that while you're outside of minigame area.")
                return@on true
            }
            if (player.interfaceManager.overlay != null) {
                closeOverlay(player)
            } else {
                openOverlay(player, Components.IMPLING_SCROLL_169)
            }
            return@on true
        }

    }

    companion object {
        val wheatSceneryIDs = intArrayOf(Scenery.MAGICAL_WHEAT_25016, Scenery.MAGICAL_WHEAT_25029, Scenery.MAGICAL_WHEAT_25019, Scenery.MAGICAL_WHEAT_25018, Scenery.MAGICAL_WHEAT_25020, Scenery.MAGICAL_WHEAT_25021)
    }
}