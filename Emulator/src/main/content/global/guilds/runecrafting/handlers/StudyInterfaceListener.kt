package content.global.guilds.runecrafting.handlers

import cfg.consts.Components
import cfg.consts.Items
import cfg.consts.Scenery
import content.global.guilds.runecrafting.RCGUtils
import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.InterfaceListener
import core.game.world.GameWorld

/**
 * Represents the Map table interface listener.
 * @author Szumaster
 */
class StudyInterfaceListener : InterfaceListener, InteractionListener {

    override fun defineInterfaceListeners() {

        /*
         * Open an interface showing location of altars.
         */

        onOpen(STUDY_INTERFACE) { player, _ ->
            if (inEquipment(player, Items.OMNI_TALISMAN_STAFF_13642) || inEquipment(player, Items.OMNI_TIARA_13655))
                for (rune in ALTAR_MAP_MODELS) setComponentVisibility(player, STUDY_INTERFACE, rune, false).also {
                    setInterfaceText(player, "All the altars of " + GameWorld.settings!!.name + ".", STUDY_INTERFACE, 33)
                }
            return@onOpen true
        }
    }

    override fun defineListeners() {

        /*
         * Use talisman with Map table to show altar location.
         */

        onUseWith(IntType.SCENERY, RCGUtils.TALLYS, MAP_TABLE) { player, used, _ ->
            if (anyInInventory(player, *RCGUtils.TALLYS)) {
                openInterface(player, STUDY_INTERFACE)
                setComponentVisibility(player, STUDY_INTERFACE, swapIds(used.id), false)
            }
            return@onUseWith true
        }

        /*
         * Use Omni items to show all altar locations.
         */

        onUseWith(IntType.SCENERY, OMNI_TALISMAN, MAP_TABLE) { player, _, _ ->
            if (!inEquipment(player, OMNI_TALISMAN) || !inEquipment(player, OMNI_TIARA)) {
                openInterface(player, STUDY_INTERFACE)
                for (rune in ALTAR_MAP_MODELS) setComponentVisibility(player, STUDY_INTERFACE, rune, false).also {
                    setInterfaceText(player, "All the altars of " + GameWorld.settings!!.name + ".", STUDY_INTERFACE, 33)
                }
            }
            return@onUseWith true
        }
    }

    private fun swapIds(id: Int): Int {
        return when (id) {
            1438 -> 35
            1440 -> 38
            1442 -> 40
            1444 -> 48
            1446 -> 36
            1448 -> 37
            1450 -> 43
            1452 -> 41
            1454 -> 39
            1456 -> 47
            1458 -> 42
            1462 -> 44
            else -> 0
        }
    }

    companion object {
        private const val MAP_TABLE = Scenery.MAP_TABLE_38315
        private const val OMNI_TIARA = Items.OMNI_TIARA_13655
        private const val OMNI_TALISMAN = Items.OMNI_TALISMAN_13649
        private const val STUDY_INTERFACE = Components.RCGUILD_MAP_780
        private val ALTAR_MAP_MODELS = intArrayOf(35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 47, 48)
    }


}
