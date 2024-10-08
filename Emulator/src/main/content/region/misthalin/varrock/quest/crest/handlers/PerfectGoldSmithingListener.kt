package content.region.misthalin.varrock.quest.crest.handlers

import org.rs.consts.Animations
import org.rs.consts.Items
import content.global.skill.smithing.smelting.SmeltingInteraction
import core.api.*
import core.game.interaction.*
import core.game.node.entity.skill.Skills

/**
 * Represents the Perfect gold smithing listener.
 */
class PerfectGoldSmithingListener : InteractionListener {

    private val furnaceIDs: IntArray = SmeltingInteraction.furnaceIDs

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, Items.PERFECT_GOLD_ORE_446, *furnaceIDs) { player, used, _ ->
            queueScript(player, 1, QueueStrength.SOFT) { stage: Int ->
                when (stage) {
                    0 -> {
                        if (removeItem(player, used.asItem())) {
                            sendMessage(player, "You place a lump of gold in the furnace.")
                            lock(player, 4)
                            lockInteractions(player, 4)
                            animate(player, Animations.HUMAN_FURNACE_SMELTING_3243)
                        }
                        return@queueScript delayScript(player, 2)
                    }

                    1 -> {
                        sendMessage(player, "You retrieve a bar of gold from the furnace.")
                        addItem(player, Items.PERFECT_GOLD_BAR_2365)
                        rewardXP(player, Skills.SMITHING, 22.5)
                        return@queueScript stopExecuting(player)
                    }

                    else -> return@queueScript stopExecuting(player)
                }
            }
            return@onUseWith true
        }
    }
}