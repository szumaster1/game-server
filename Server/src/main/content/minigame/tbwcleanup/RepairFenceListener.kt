package content.minigame.tbwcleanup

import content.data.skill.SkillingTool
import core.api.*
import cfg.consts.Items
import cfg.consts.Sounds
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.SceneryBuilder
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction.random
import core.game.container.impl.EquipmentContainer
import core.game.node.entity.skill.Skills

/**
 * Represents the Repair fence interaction.
 */
class RepairFenceListener : InteractionListener {

    private val VILLAGE_FENCE = arrayOf(9025, 9026, 9027, 9028, 9029)
    private val THATCH_SPAR_DENSE = Items.THATCH_SPAR_DENSE_6285
    private val THATCH_SPAR_MED = Items.THATCH_SPAR_MED_6283
    private val THATCH_SPAR_LIGHT = Items.THATCH_SPAR_LIGHT_6281


    override fun defineListeners() {
        on(IntType.SCENERY, "Repair", "Reinforce") { player, node ->
            if (node.id in VILLAGE_FENCE) {
                tryToRepairFence(player, node)
            }
            return@on true
        }
    }

    private fun tryToRepairFence(player: Player, node: Node) {
        if (!player.getAttribute("/save:startedTBWCleanup", false)) {
            player.sendMessage("I should probably talk to someone like Murcaily before I start repairing this.")
            return
        }
        if (SkillingTool.getMachete(player) == null) {
            player.packetDispatch.sendMessage("You do not have a machete to use.")
            return
        }
        if (!player.inventory.containsAtLeastOneItem(intArrayOf(6285, 6283, 6281))) {
            player.packetDispatch.sendMessage("You do not have any thatch spars to use.")
            return
        }

        if (player.inventory.contains(THATCH_SPAR_DENSE, 1))
            executeRepairFence(player, 5, THATCH_SPAR_DENSE, node)
        else if (player.inventory.contains(THATCH_SPAR_MED, 1))
            executeRepairFence(player, 3, THATCH_SPAR_MED, node)
        else if (player.inventory.contains(THATCH_SPAR_LIGHT, 1))
            executeRepairFence(player, 1, THATCH_SPAR_LIGHT, node)
    }

    private fun executeRepairFence(player: Player, awardPoints: Int, useItem: Int, node: Node) {
        val location_start = player.location
        val ticks_needed = random(2, 35)
        val EQUIPED = player.equipment[EquipmentContainer.SLOT_WEAPON]
        val MACHETE_TO_USE_IN_ANIMATION = if (EQUIPED != null && EQUIPED.id in arrayOf(975, 6313, 6315, 6317)) EQUIPED.id else SkillingTool.getMachete(player)!!.id
        val MACHETE_ANIMATION = when (MACHETE_TO_USE_IN_ANIMATION) {
            Items.MACHETE_975 -> 2385
            Items.OPAL_MACHETE_6313 -> 2421
            Items.JADE_MACHETE_6315 -> 2424
            Items.RED_TOPAZ_MACHETE_6317 -> 2427
            else -> 2385
        }

        player.animate(Animation(MACHETE_ANIMATION), 0)
        playAudio(player, Sounds.TBCU_REPAIR_FENCE_1275, 0, ticks_needed - 1, player.location, 1)

        queueScript(player, ticks_needed - 1, QueueStrength.SOFT) {
            if (location_start == player.location) {
                player.animate(Animation(2386), 0)
            }
            return@queueScript stopExecuting(player)
        }

        queueScript(player, ticks_needed, QueueStrength.SOFT) {
            if (location_start == player.location) {
                player.inventory.remove(Item(useItem, 1))
                awardTBWCleanupPoints(player, awardPoints)
                when (useItem) {
                    THATCH_SPAR_DENSE -> player.getSkills().addExperience(Skills.WOODCUTTING, 65.1)
                    THATCH_SPAR_MED -> player.getSkills().addExperience(Skills.WOODCUTTING, 42.1)
                    THATCH_SPAR_LIGHT -> player.getSkills().addExperience(Skills.WOODCUTTING, 28.1)
                }
                player.sendMessage(
                    "You use some ${
                        when (useItem) {
                            THATCH_SPAR_DENSE -> "dense"
                            THATCH_SPAR_MED -> "medium"
                            THATCH_SPAR_LIGHT -> "light"
                            else -> "Error!"
                        }
                    } thatch spar to repair the fence."
                )

                // updates fence model in world to improved version if not already at max
                if (node.id != 9029) {
                    SceneryBuilder.replaceWithTempBeforeNew(
                        node.asScenery(),
                        node.asScenery().transform(node.id + 1),
                        node.asScenery().transform(9025),
                        500,
                        true
                    )
                }
            }
            return@queueScript stopExecuting(player)
        }
    }
}