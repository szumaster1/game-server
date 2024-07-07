package content.global.skill.support.thieving

import core.api.*
import core.game.event.ResourceProducedEvent
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.world.GameWorld
import core.game.world.map.RegionManager.getLocalNpcs
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction
import core.tools.StringUtils

/**
 * @author 'Vexia
 */
class StallThiefPulse(player: Player?, node: Scenery?, private val stall: Stall?) : SkillPulse<Scenery?>(player, node) {

    private var ticks = 0

    override fun start() {
        player.setAttribute("thieveDelay", GameWorld.ticks)
        super.start()
    }

    override fun checkRequirements(): Boolean {
        if (stall == null) {
            return false
        }
        if (player.inCombat()) {
            sendMessage(player, "You cant steal from the market stall during combat!")
            return false
        }
        if (player.getSkills().getLevel(Skills.THIEVING) < stall.level) {
            sendMessage(player, "You need to be level " + stall.level + " to steal from the " + node!!.name.lowercase() + ".")
            return false
        }
        if (player.inventory.freeSlots() == 0) {
            sendMessage(player,"You don't have enough inventory space.")
            return false
        }
        if (inBorders(player, getRegionBorders(10553)) && !isQuestComplete(player, "Fremennik Trials") && stall.fullIDs.contains(4278)) {
            sendDialogue(player, "The fur trader is staring at you suspiciously. You cannot steal from his stall while he distrusts you.")
            return false
        }
        if (inBorders(player, getRegionBorders(10553)) && !isQuestComplete(player, "Fremennik Trials") && stall.fullIDs.contains(4277)) {
            sendDialogue(player, "The fishmonger is staring at you suspiciously. You cannot steal from his stall while he distrusts you.")
            return false
        }
        return true
    }

    override fun hasInactiveNode(): Boolean {
        return if (player.getAttribute("thieveDelay", 0) <= GameWorld.ticks) {
            false
        } else super.hasInactiveNode()
    }

    override fun animate() {}
    override fun reward(): Boolean {
        if (ticks == 0) {
            player.animate(ANIMATION)
            player.locks.lockInteractions(2)
        }
        if (++ticks % 3 != 0) {
            return false
        }
        val success = success()
        if (success) {
            if (stall === Stall.SILK_STALL) {
                player.getSavedData().globalData.setSilkSteal(System.currentTimeMillis() + 1800000)
            }
            if (node!!.isActive) {
                SceneryBuilder.replace(node, node!!.transform(stall!!.getEmpty(node!!.id)), stall.delay)
            }
            val item = stall!!.randomLoot
            player.inventory.add(item)
            player.getSkills().addExperience(Skills.THIEVING, stall.experience, true)
            if (item.id == 1987) {
                player.packetDispatch.sendMessage("You steal grapes from the grape stall.")
                return true
            }
            if (stall === Stall.CANDLES) {
                return true
            }
            player.packetDispatch.sendMessage("You steal " + (if (StringUtils.isPlusN(item.name)) "an" else "a") + " " + item.name.lowercase() + " from the " + stall.name.lowercase().replace('_', ' ') + ".")
            player.dispatch(ResourceProducedEvent(item.id, item.amount, node!!, 0))
        }
        return true
    }

    override fun message(type: Int) {
        if (stall === Stall.CANDLES) {
            return
        }
        if (type == 0) {
            player.packetDispatch.sendMessage("You attempt to steal some " + stall!!.message + " from the " + stall.name.lowercase().replace('_', ' '))
        }
    }

    /**
     * Checks if the thief is successful.
     * @return `True` if so.
     */
    private fun success(): Boolean {
        val mod = 0
        if (RandomFunction.random(15 + mod) < 4) {
            if (stall === Stall.CANDLES) {
                stun(player, 15, false)
                impact(player, 1, ImpactHandler.HitsplatType.NORMAL)
                // Location playerLoc = player.getLocation();
                // forceMove(player, playerLoc, new Location(playerLoc.getX() - 1, playerLoc.getY() - 1), 0, 4, Direction.SOUTH_WEST, 819, null);
                sendMessage(player, "A higher power smites you")
                return false
            }
            for (npc in getLocalNpcs(player.location, 8)) {
                if (!npc.properties.combatPulse.isAttacking && (npc.id == 32 || npc.id == 2236)) {
                    npc.sendChat("Hey! Get your hands off there!")
                    npc.properties.combatPulse.attack(player)
                    return false
                }
            }
        }
        return true
    }

    companion object {
        /**
         * Represents the stealing animation.
         */
        private val ANIMATION = Animation(832)
    }
}
