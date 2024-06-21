package content.region.fremennik.handlers.rellekka

import content.region.fremennik.handlers.waterbirth.TravelDestination
import content.region.fremennik.handlers.waterbirth.WaterbirthTravel
import core.api.consts.NPCs
import core.api.requireQuest
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location

class RellekkaListeners : InteractionListener {

    companion object {

        private val UP1A: Location? = Location.create(2715, 3798, 0)
        private val UP1B: Location? = Location.create(2716, 3798, 0)
        private val UP2A: Location? = Location.create(2726, 3801, 0)
        private val UP2B: Location? = Location.create(2727, 3801, 0)

        private val DOWN1A: Location? = Location.create(2715, 3802, 1)
        private val DOWN1B: Location? = Location.create(2716, 3802, 1)
        private val DOWN2A: Location? = Location.create(2726, 3805, 1)
        private val DOWN2B: Location? = Location.create(2727, 3805, 1)

        private val STAIRS = intArrayOf(19690, 19691)
    }

    override fun defineListeners() {

        on(STAIRS, IntType.SCENERY, "ascend", "descend") { player, _ ->
            if (player.location.y < 3802) {
                player.properties.teleportLocation = when (player.location.x) {
                    2715 -> DOWN1A
                    2716 -> DOWN1B
                    2726 -> DOWN2A
                    2727 -> DOWN2B
                    else -> player.location
                }
            } else {
                player.properties.teleportLocation = when (player.location.x) {
                    2715 -> UP1A
                    2716 -> UP1B
                    2726 -> UP2A
                    2727 -> UP2B
                    else -> player.location
                }
            }
            return@on true
        }

        on(NPCs.MARIA_GUNNARS_5508, IntType.NPC, "ferry-neitiznot") { player, _ ->
            if (!requireQuest(player, "Fremennik Trials", "")) return@on true
            WaterbirthTravel.sail(player, TravelDestination.RELLEKKA_TO_NEITIZNOT)
            return@on true
        }

        on(NPCs.MARIA_GUNNARS_5507, IntType.NPC, "ferry-rellekka") { player, _ ->
            WaterbirthTravel.sail(player, TravelDestination.NEITIZNOT_TO_RELLEKKA)
            return@on true
        }

        on(NPCs.MORD_GUNNARS_5481, IntType.NPC, "ferry-jatizso") { player, _ ->
            if (!requireQuest(player, "Fremennik Trials", "")) return@on true
            WaterbirthTravel.sail(player, TravelDestination.RELLEKKA_TO_JATIZSO)
            return@on true
        }

        on(NPCs.MORD_GUNNARS_5482, IntType.NPC, "ferry-rellekka") { player, _ ->
            WaterbirthTravel.sail(player, TravelDestination.JATIZSO_TO_RELLEKKA)
            return@on true
        }

        on(NPCs.SAILOR_1385, IntType.NPC, "travel") { player, _ ->
            if (!requireQuest(player, "Fremennik Trials", "")) return@on true
            WaterbirthTravel.sail(player, TravelDestination.RELLEKA_TO_MISCELLANIA)
            return@on true
        }

        on(NPCs.SAILOR_1304, IntType.NPC, "travel") { player, _ ->
            if (!requireQuest(player, "Fremennik Trials", "")) return@on true
            WaterbirthTravel.sail(player, TravelDestination.MISCELLANIA_TO_RELLEKKA)
            return@on true
        }

    }
}
