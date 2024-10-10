package content.region.kandarin.baxtorian.handlers.barbariantraining.firemaking

import content.global.skill.firemaking.FireMakingPulse.getAsh
import content.global.skill.firemaking.logs.Log.Companion.forId
import content.global.skill.gather.SkillingTool
import content.region.kandarin.baxtorian.handlers.barbariantraining.BarbarianTraining
import core.api.*
import org.rs.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import core.game.node.item.GroundItemManager
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.RegionManager.getObject
import core.game.world.update.flag.context.Animation

class BarbFiremakingListener : InteractionListener {

    companion object {
        val crystalEquipment = intArrayOf(Items.NEW_CRYSTAL_BOW_4212, Items.CRYSTAL_BOW_FULL_4214, Items.CRYSTAL_BOW_9_10_4215, Items.CRYSTAL_BOW_8_10_4216, Items.CRYSTAL_BOW_7_10_4217, Items.CRYSTAL_BOW_6_10_4218, Items.CRYSTAL_BOW_5_10_4219, Items.CRYSTAL_BOW_4_10_4220, Items.CRYSTAL_BOW_3_10_4221, Items.CRYSTAL_BOW_2_10_4222, Items.CRYSTAL_BOW_1_10_4223, Items.NEW_CRYSTAL_SHIELD_4224)
        val tools = intArrayOf(Items.TRAINING_BOW_9705, Items.LONGBOW_839, Items.SHORTBOW_841, Items.OAK_SHORTBOW_843, Items.OAK_LONGBOW_845, Items.WILLOW_LONGBOW_847, Items.WILLOW_SHORTBOW_849, Items.MAPLE_LONGBOW_851, Items.MAPLE_SHORTBOW_853, Items.YEW_LONGBOW_855, Items.YEW_SHORTBOW_857, Items.MAGIC_LONGBOW_859, Items.MAGIC_SHORTBOW_861, Items.SEERCULL_6724)
        val logs = intArrayOf(Items.LOGS_1511, Items.OAK_LOGS_1521, Items.WILLOW_LOGS_1519, Items.MAPLE_LOGS_1517, Items.YEW_LOGS_1515, Items.MAGIC_LOGS_1513, Items.ACHEY_TREE_LOGS_2862, Items.PYRE_LOGS_3438, Items.OAK_PYRE_LOGS_3440, Items.WILLOW_PYRE_LOGS_3442, Items.MAPLE_PYRE_LOGS_3444, Items.YEW_PYRE_LOGS_3446, Items.MAGIC_PYRE_LOGS_3448, Items.TEAK_PYRE_LOGS_6211, Items.MAHOGANY_PYRE_LOG_6213, Items.MAHOGANY_LOGS_6332, Items.TEAK_LOGS_6333, Items.RED_LOGS_7404, Items.GREEN_LOGS_7405, Items.BLUE_LOGS_7406, Items.PURPLE_LOGS_10329, Items.WHITE_LOGS_10328, Items.SCRAPEY_TREE_LOGS_8934, Items.DREAM_LOG_9067, Items.ARCTIC_PYRE_LOGS_10808, Items.ARCTIC_PINE_LOGS_10810, Items.SPLIT_LOG_10812, Items.WINDSWEPT_LOGS_11035, Items.EUCALYPTUS_LOGS_12581, Items.EUCALYPTUS_PYRE_LOGS_12583, Items.JOGRE_BONES_3125)
    }

    override fun defineListeners() {
        onUseWith(IntType.ITEM, logs, *tools) { player, used, with ->
            val tool = SkillingTool.getFiremakingTool(player)
            val log = forId(used.id)

            if (with.id in crystalEquipment) {
                sendMessage(player, "The bow resists all attempts to light the fire. It seems that the sentient tools of the elves don't approve of you burning down forests.")
                return@onUseWith false
            }

            if (!getAttribute(player, BarbarianTraining.FM_BASE, false) || !getAttribute(player, BarbarianTraining.FM_FULL, false)) {
                sendMessage(player, "In order to be able to lighting fires, Otto Godblessed must be talked to.")
                return@onUseWith false
            }

            val ticks = animationDuration(Animation(tool!!.animation))

            if (getObject(player.location) != null || player.zoneMonitor.isInZone("bank")) {
                sendMessage(player, "You can't light a fire here.")
                return@onUseWith false
            }

            visualize(player, tool.animation, 1844)

            if (removeItem(player, used.asItem())) {
                val ground = GroundItemManager.create(used.asItem(), player.location, player)
                Pulser.submit(object : Pulse(ticks, player) {
                    override fun pulse(): Boolean {
                        if (!ground.isActive) {
                            return true
                        }
                        val `object` = Scenery(log!!.fireId, player.location)
                        player.moveStep()
                        GroundItemManager.destroy(ground)
                        rewardXP(player, Skills.FIREMAKING, log.xp)
                        player.faceLocation(`object`.getFaceLocation(player.location))
                        SceneryBuilder.add(`object`, log.life, getAsh(player, log, `object`))
                        return true
                    }
                })
            }
            return@onUseWith true
        }

    }
}
