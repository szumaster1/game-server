package content.region.kandarin.quest.holygrail

import content.region.kandarin.quest.holygrail.dialogue.*
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.global.action.ClimbActionHandler
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.node.item.GroundItem
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders
import core.utilities.secondsToTicks

class HolyGrailListeners : InteractionListener {
    override fun defineListeners() {

        on(NPCs.BLACK_KNIGHT_TITAN_221, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, BlackKnightTitanDialogueFile(false), NPCs.BLACK_KNIGHT_TITAN_221)
            return@on true
        }

        on(NPCs.FISHERMAN_219, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, FishermanHolyGrailDialogueFile(), NPCs.FISHERMAN_219)
            return@on true
        }

        on(NPCs.PEASANT_214, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, DiseasedPeasantHolyGrailDialogueFile(), NPCs.PEASANT_214)
            return@on true
        }

        on(NPCs.PEASANT_215, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, HealthyPeasantHolyGrailDialogueFile(), NPCs.PEASANT_215)
            return@on true
        }

        on(NPCs.GRAIL_MAIDEN_210, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, MaidenHolyGrailDialogueFile(false), NPCs.GRAIL_MAIDEN_210)
            return@on true
        }

        on(NPCs.THE_FISHER_KING_220, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, FisherKingHolyGrailDialogueFile(), NPCs.THE_FISHER_KING_220)
            return@on true
        }

        on(Items.GRAIL_BELL_17, IntType.ITEM, "Ring") { player, _ ->
            openDialogue(player, MaidenHolyGrailDialogueFile(true), NPCs.GRAIL_MAIDEN_210)
            return@on true
        }

        on(Items.MAGIC_GOLD_FEATHER_18, IntType.ITEM, "Blow-on") { player, _ ->
            if (getQuestStage(player, HolyGrail.HOLY_GRAIL) != 40) {
                sendMessage(player, "Nothing interesting happens.")
                return@on true
            }

            var dest = Location.create(2962, 3505, 0)
            var direction = "to the "+Direction.getDirection(player.location, dest).toString().lowercase().replace("_", " ")

            var zoneInBuilding = ZoneBorders(Location.create(2959, 3504, 0), Location.create(2962, 3507, 0))

            if (zoneInBuilding.insideBorder(player)) {
                direction = "at the sacks"
            } else if (dest.getDistance(player.location) <= 10)
                direction = "to somewhere nearby"


            sendMessage(player, "The feather points $direction.")
            return@on true
        }

        on(Items.MAGIC_WHISTLE_16, IntType.ITEM, "Blow") { player, _ ->
            var zoneCanTeleport = ZoneBorders(Location.create(2740, 3234, 0), Location.create(2743, 3237, 0))
            var zoneIsDiseased = ZoneBorders(Location.create(2741, 4650, 0), Location.create(2811, 4742, 0))
            var zoneIsHealthy = ZoneBorders(Location.create(2614, 4661, 0), Location.create(2698, 4746, 0))

            if (!zoneCanTeleport.insideBorder(player) && !zoneIsDiseased.insideBorder(player) && !zoneIsHealthy.insideBorder(player)) {
                sendDialogueLines(player, "The whistle makes no noise. It will not work in this location.")
                return@on true
            }

            // default to diseased
            var destLoc = Location.create(2806, 4715, 0)

            if (zoneCanTeleport.insideBorder(player)) {
                if (getQuestStage(player, HolyGrail.HOLY_GRAIL) >= 50) {
                    destLoc = Location.create(2678, 4715, 0)
                }
            } else if (zoneIsDiseased.insideBorder(player) || zoneIsHealthy.insideBorder(player)) {
                destLoc = Location.create(2741, 3235, 0)
            }

            teleport(player, destLoc)
            return@on true
        }

        on(Items.HOLY_GRAIL_19, IntType.ITEM, "Take") { player, grail ->
            player.faceLocation(grail.location)

            if (grail.location.getDistance(player.location) >= 2)
                return@on false

            if (player.inventory.contains(Items.HOLY_GRAIL_19, 1)) {
                sendDialogue(player, "You feel that taking more than one Holy Grail might be greedy...")
                return@on true
            }

            if (getQuestStage(player, HolyGrail.HOLY_GRAIL) <= 40) {
                sendDialogueLines(player, "You feel that the Grail shouldn't be moved.", "You must complete some task here before you are worthy.")
            } else {
                addItem(player, Items.HOLY_GRAIL_19, 1)
            }
            return@on true
        }

        on(NPCs.SIR_PERCIVAL_211, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, SirPercivalHolyGrailDialogueFile("Talk-to"))
            return@on true
        }

        on(core.api.consts.Scenery.SACKS_23, IntType.SCENERY, "Prod") { player, _ ->
            openDialogue(player, SirPercivalHolyGrailDialogueFile("Prod"))
            return@on true
        }

        on(core.api.consts.Scenery.SACKS_23, IntType.SCENERY, "Open") { player, _ ->
            openDialogue(player, SirPercivalHolyGrailDialogueFile("Open"))
            return@on true
        }

        on(core.api.consts.Scenery.STAIRCASE_1730, IntType.SCENERY, "walk-up") { player, stairs ->
            ClimbActionHandler.climbLadder(player, stairs.asScenery(), "walk-up")
            spawnGrail(player)
            return@on true
        }

        on(core.api.consts.Scenery.LADDER_1750, IntType.SCENERY, "climb-up") { player, stairs ->
            ClimbActionHandler.climbLadder(player, stairs.asScenery(), "climb-up")
            spawnGrail(player)
            return@on true
        }

        on(HolyGrail.MERLIN_DOOR_ID, IntType.SCENERY, "open") { player, door ->
            if (!door.location.equals(HolyGrail.MERLIN_DOOR_LOCATION_OPEN) && !door.location.equals(HolyGrail.MERLIN_DOOR_LOCATION_CLOSED)) {
                DoorActionHandler.handleDoor(player, door.asScenery())
                return@on false
            }

            if (getQuestStage(player, HolyGrail.HOLY_GRAIL) == 0) {
                sendMessage(player, "The door won't open.")
                return@on false
            }

            val moveToX = if (player.location.x <= HolyGrail.MERLIN_DOOR_LOCATION_CLOSED.x) HolyGrail.MERLIN_DOOR_LOCATION_OPEN.x else HolyGrail.MERLIN_DOOR_LOCATION_CLOSED.x
            DoorActionHandler.handleAutowalkDoor(player, door as Scenery, Location.create(moveToX, HolyGrail.MERLIN_DOOR_LOCATION_OPEN.y, HolyGrail.MERLIN_DOOR_LOCATION_OPEN.z))
            return@on true
        }

        on(core.api.consts.Scenery.DOOR_22, IntType.SCENERY, "open") { player, door ->
            if (!door.location.equals(HolyGrail.DOOR_MAGIC_WHISTLE_LOCATION)) {
                DoorActionHandler.handleDoor(player, door.asScenery())
                return@on true
            }

            val moveToY = if (player.location.y <= 3361) 3362 else 3361
            DoorActionHandler.handleAutowalkDoor(player, door as Scenery, Location.create(3106, moveToY, 2))

            if (getQuestStage(player, HolyGrail.HOLY_GRAIL) == 30 && player.hasItem(Item(Items.HOLY_TABLE_NAPKIN_15, 1))) {
                // For some reason 2 whistles always spawn: https://youtu.be/qD36sjWAGZA?si=P6xG-tSw9XbdWOpX&t=233
                GroundItemManager.create(GroundItem(Item(Items.MAGIC_WHISTLE_16, 1), Location.create(3107, 3359, 2), secondsToTicks(60), player))
                GroundItemManager.create(GroundItem(Item(Items.MAGIC_WHISTLE_16, 1), Location.create(3107, 3359, 2), secondsToTicks(60), player))
            }

            return@on true
        }
    }

    private fun spawnGrail(player: Player) {
        val loc = Location.create(2777, 4684, 2)

        queueScript(player, 2, QueueStrength.SOFT) { _ ->
            if (GroundItemManager.get(Items.HOLY_GRAIL_19, loc, player) == null) {
                GroundItemManager.create(GroundItem(Item(Items.HOLY_GRAIL_19, 1), loc, secondsToTicks(60 * 60), player))
            }
            return@queueScript stopExecuting(player)
        }
    }

}
