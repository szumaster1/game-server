package content.region.misthalin.quest.member.lostcity

import core.api.consts.Items
import core.api.*
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.link.TeleportManager.TeleportType
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.api.consts.Scenery as Sceneries

/**
 * Lost city listeners.
 */
@Initializable
class LostCityListeners : InteractionListener {

    override fun defineListeners() {

        on(Sceneries.DOOR_2406, IntType.SCENERY, "open") { player, node ->
            DoorActionHandler.handleAutowalkDoor(player, node as Scenery)
            val quest = "Lost City"
            val isOutsideShed = player.location.x < node.location.x
            val canDramenTeleport =
                inEquipment(player, Items.DRAMEN_STAFF_772) && (getQuestStage(player, quest) > 20) && isOutsideShed
            if (canDramenTeleport) {
                var count = 0
                GameWorld.Pulser.submit(object : Pulse(2) {
                    override fun pulse(): Boolean {
                        when (count++) {
                            0 -> {
                                sendMessageWithDelay(player, "The world starts to shimmer...", 1)
                                teleport(player, Location(2452, 4473, 0), TeleportType.FAIRY_RING)
                            }

                            1 -> return isQuestComplete(player, quest)
                            2 -> {
                                finishQuest(player, quest)
                                return true
                            }
                        }
                        return false
                    }
                })
            }
            return@on true
        }

        onUseWith(IntType.ITEM, Items.KNIFE_946, Items.DRAMEN_BRANCH_771) { player, _, _ ->
            if (getStatLevel(player, Skills.CRAFTING) < 31) {
                sendDialogue(player, "You need a crafting level of 31 to do this.")
                return@onUseWith false
            }

            if (removeItem(player, Item(Items.DRAMEN_BRANCH_771, 1), Container.INVENTORY)) {
                sendMessage(player, "You carve the branch into a staff.")
                addItem(player, Items.DRAMEN_STAFF_772, 1, Container.INVENTORY)
            }
            return@onUseWith true
        }
    }
}
