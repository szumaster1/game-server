package content.region.asgarnia.handlers.portsarim

import core.api.*
import core.api.consts.Animations
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.global.action.ClimbActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.repository.Repository.findNPC
import core.game.world.update.flag.context.Animation

class PortSceneryListeners : InteractionListener {

    override fun defineListeners() {
        on(LADDER, IntType.SCENERY, "climb-down", "climb-up") { player, _ ->
            when (getUsedOption(player)) {
                "climb-up" -> ClimbActionHandler.climb(player, Animation(828), Location(3048, 3208, 1))
                else -> ClimbActionHandler.climb(player, Animation(828), Location(3048, 9640, 1))
            }
            return@on true
        }

        on(Scenery.GANGPLANK_69, IntType.SCENERY, "cross"){ player, _ ->
            player.dialogueInterpreter.open(NPCs.ARHEIN_563, findNPC(NPCs.ARHEIN_563), true)
            return@on true
        }

        on(Scenery.GANGPLANK_2593, IntType.SCENERY, "cross"){ player, _ ->
            if (isQuestComplete(player, "Dragon Slayer")) {
                player.dialogueInterpreter.open(NPCs.KLARENSE_744, findNPC(NPCs.KLARENSE_744), true)
                return@on true
            }
            if (!player.getSavedData().questData.getDragonSlayerAttribute("ship")) {
                openDialogue(player, NPCs.KLARENSE_744, findNPC(NPCs.KLARENSE_744)!!, true)
            } else {
                sendMessage(player,"You board the ship.")
                cross(player, Location(3047, 3207, 1)) // [2]
            }
            return@on true
        }

        on(Scenery.GANGPLANK_11209, IntType.SCENERY, "cross") { player, _ ->
            sendDialogueLines(player,"I don't think that whoever owns this ship will be happy", "with me wandering all over it.")
            return@on true
        }

        on(PLANK, IntType.SCENERY, "cross") { player, node ->
            forceMove(player, player.location, node.location, 0, animationDuration(Animation(Animations.HUMAN_WALK_SHORT_819)), null, Animations.HUMAN_WALK_SHORT_819)
            if (getUsedOption(player) == "cross") {
                when (node.id) {
                    2081 -> cross(player, KARAMJA[0])
                    2082 -> cross(player, KARAMJA[1])
                    2083 -> cross(player, PORT_SARIM[4])
                    2084 -> cross(player, PORT_SARIM[5])
                    2085 -> cross(player, ARDOUGNE[0]).also { sendMessage(player, "You must speak to Captain Barnaby before it will set sail.") }
                    2086 -> cross(player, ARDOUGNE[1])
                    2087 -> cross(player, BRIMHAVEN[2]).also { sendMessage(player, "You must speak to the Customs Officer before it will set sail.") }
                    2088 -> cross(player, BRIMHAVEN[3])
                    2412 -> cross(player, PORT_SARIM[0])
                    2413 -> cross(player, PORT_SARIM[1])
                    2414 -> cross(player, ENTRANA[0])
                    2415 -> cross(player, ENTRANA[1])
                    2594 -> cross(player, Location(3047, 3204, 0)) // [1]
                    11211 -> cross(player, MOS_SHIP[0])
                    11212 -> cross(player, MOS_SHIP[1])
                    14304 -> cross(player, PORT_SARIM[6]).also { sendMessage(player, "You board the ship.") }
                    14305 -> cross(player, PORT_SARIM[7]).also { sendMessage(player, "You disembark the ship.") }
                    14306 -> cross(player, PEST_CONTROL[0]).also { sendMessage(player, "You board the ship.") }
                    14307 -> cross(player, PEST_CONTROL[1]).also { sendMessage(player, "You disembark the ship.") }
                    17392 -> cross(player, PORT_PHASMATYS[0])
                    17393 -> cross(player, PORT_PHASMATYS[1])
                    17394 -> cross(player, CATHERBY[0])
                    17395 -> cross(player, CATHERBY[1])
                    17398 -> cross(player, KARAMJA[2]).also { sendMessage(player, "You must speak to the Customs Officer before it will set sail.") }
                    17399 -> cross(player, KARAMJA[3]).also { sendMessage(player, "You must speak to the Customs Officer before it will set sail.") }
                    17400 -> cross(player, BRIMHAVEN[0])
                    17401 -> cross(player, BRIMHAVEN[1])
                    17402 -> cross(player, PORT_KHAZARD[0])
                    17403 -> cross(player, PORT_KHAZARD[1])
                    17404 -> cross(player, PORT_SARIM[3])
                    17405 -> cross(player, PORT_SARIM[2])
                    17406 -> cross(player, MOS_LE_HARMESS[0])
                    17407 -> cross(player, MOS_LE_HARMESS[1])
                    17408 -> cross(player, TYRAS[0])
                    17409 -> cross(player, TYRAS[1])
                    else -> sendDialogueLines(player,"I don't think that whoever owns this ship will be happy", "with me wandering all over it.")
                }
            }
            return@on true
        }
    }

    companion object {
        private val PLANK = intArrayOf(2081, 2082, 2083, 2084, 2085, 2086, 2087, 2088, 2412, 2413, 2414, 2415, 2594, 11211, 11212, 14304, 14305, 14306, 14307, 17392, 17393, 17394, 17395, 17398, 17399, 17400, 17401, 17402, 17403, 17404, 17405, 17406, 17407, 17408, 17409)
        private val LADDER = intArrayOf(Scenery.LADDER_2590, Scenery.LADDER_2592)
        private val PORT_SARIM = arrayOf(Location(3048, 3231, 1), Location.create(3048, 3234, 0), Location(3038, 3192, 0), Location(3038, 3189, 1), Location(3032, 3217, 1), Location(3029, 3217, 0), Location(3041, 3199, 1), Location(3041, 3202, 0))
        private val ENTRANA = arrayOf(Location(2834, 3331, 1), Location(2834, 3335, 0))
        private val KARAMJA = arrayOf(Location(2956, 3143, 1), Location(2956, 3146, 0), Location(2957, 3158, 1), Location(2954, 3158, 0))
        private val CATHERBY = arrayOf(Location(2792, 3417, 1), Location(2792, 3414, 0))
        private val BRIMHAVEN = arrayOf(Location(2763, 3238, 1), Location.create(2760, 3238, 0), Location(2775, 3234, 1), Location(2772, 3234, 0))
        private val ARDOUGNE = arrayOf(Location(2683, 3268, 1), Location(2683, 3271, 0))
        private val PORT_KHAZARD = arrayOf(Location(2674, 3141, 1), Location(2674, 3144, 0))
        private val PORT_PHASMATYS = arrayOf(Location(3705, 3503, 1), Location(3702, 3503, 0))
        private val PEST_CONTROL = arrayOf(Location(2662, 2676, 1), Location(2659, 2676, 0))
        private val MOS_LE_HARMESS = arrayOf(Location(3668, 2931, 1), Location(3671, 2931, 0))
        private val MOS_SHIP = arrayOf(Location(3684, 2950, 1), Location(3684, 2953, 0))
        private val TYRAS = arrayOf(Location(2142, 3125, 1), Location(2142, 3122, 0))
        /*
         * 17.07.2009
         */
        fun cross(player: Player, location: Location?) {
            queueScript(player, 1, QueueStrength.STRONG) {
                player.properties.teleportLocation = location
                return@queueScript stopExecuting(player)
            }
        }
    }
}
