package content.region.asgarnia.burthope.quest.death.handlers

import content.region.asgarnia.burthope.quest.death.dialogue.DoorPlateauDialogueFile
import core.api.*
import org.rs.consts.Items
import org.rs.consts.Scenery
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.GroundItemManager
import org.rs.consts.QuestName

/**
 * Death plateau listeners.
 */
class DeathPlateauListener : InteractionListener {

    companion object {
        val stoneBalls = intArrayOf(
            Items.STONE_BALL_3109, // Red
            Items.STONE_BALL_3110, // Blue
            Items.STONE_BALL_3111, // Yellow
            Items.STONE_BALL_3112, // Purple
            Items.STONE_BALL_3113  // Green
        )
        val stoneMechanisms = intArrayOf(
            Scenery.STONE_MECHANISM_3676, // 4 Outer stone plates
            Scenery.STONE_MECHANISM_3677 // 2 Inner stone plates
        )
    }

    override fun defineListeners() {
        on(Scenery.DOOR_3747, SCENERY, "open") { player, node ->
            if(player.location.x == 2906 && player.location.y == 3543) {
                openDialogue(player, DoorPlateauDialogueFile(1))
                return@on true
            } else if(player.location.y == 3542) {
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            } else {
                DoorActionHandler.handleDoor(player, node.asScenery())
            }
            return@on true
        }

        on(Scenery.DOOR_3745, SCENERY, "open") { player, node ->
            if (node.location == location(2823, 3555, 0)) {
                // 1st Door to Tenzing
                openDialogue(player, DoorPlateauDialogueFile(2))
            } else if (node.location == location(2820, 3558, 0)) {
                // 2nd Door to chicken pen
                openDialogue(player, DoorPlateauDialogueFile(3))
            }
            return@on true
        }

        on(Items.IOU_3103, ITEM, "read") { player, _ ->
            openDialogue(player, CombinationScrollDialogue())
            return@on true
        }

        onUseWith(IntType.SCENERY,
            stoneBalls, *stoneMechanisms
        ) { player, used, with ->
            val stoneBall = used.asItem()
            val stoneMechanism = with.asScenery()

            // Place item on table
            if (removeItem(player, stoneBall)) {
                produceGroundItem(player, stoneBall.id, 1, stoneMechanism.location)
            }
            // Check if order was correct

            if (GroundItemManager.get(Items.STONE_BALL_3109, location(2894, 3563, 0), player) != null &&
                GroundItemManager.get(Items.STONE_BALL_3110, location(2894, 3562, 0), player) != null &&
                GroundItemManager.get(Items.STONE_BALL_3111, location(2895, 3562, 0), player) != null &&
                GroundItemManager.get(Items.STONE_BALL_3112, location(2895, 3563, 0), player) != null &&
                GroundItemManager.get(Items.STONE_BALL_3113, location(2895, 3564, 0), player) != null
            ) {
                if (getQuestStage(player, QuestName.DEATH_PLATEAU) == 16) {
                    sendMessage(player, "The equipment room door has unlocked.")
                    setQuestStage(player, QuestName.DEATH_PLATEAU, 19)
                }
            }
            return@onUseWith true
        }

        on(Scenery.LARGE_DOOR_3743, SCENERY, "open") { player, node ->
            if (getQuestStage(player, QuestName.DEATH_PLATEAU) > 16) {
                DoorActionHandler.handleAutowalkDoor(player, node as core.game.node.scenery.Scenery)
            } else {
                sendMessage(player, "The door is locked.")
            }
            return@on true
        }
    }
}