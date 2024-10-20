package content.global.skill.thieving.stalls

import core.api.*
import core.game.event.ResourceProducedEvent
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.world.GameWorld
import core.game.world.map.RegionManager.getLocalNpcs
import core.tools.RandomFunction
import core.tools.StringUtils
import org.rs.consts.Animations
import org.rs.consts.QuestName

/**
 * Thieving stall pulse.
 */
class ThievingStallPulse(player: Player?, node: Scenery?, private val stall: Stall?) : SkillPulse<Scenery?>(player, node) {

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
        if (getStatLevel(player, Skills.THIEVING) < stall.level) {
            sendMessage(player, "You need to be level " + stall.level + " to steal from the " + node!!.name.lowercase() + ".")
            return false
        }
        if (freeSlots(player) == 0) {
            sendMessage(player,"You don't have enough inventory space.")
            return false
        }
        if (inBorders(player, getRegionBorders(10553)) && !isQuestComplete(player, QuestName.THE_FREMENNIK_TRIALS) && stall.fullIDs.contains(4278)) {
            sendDialogue(player, "The fur trader is staring at you suspiciously. You cannot steal from his stall while he distrusts you.")
            return false
        }
        if (inBorders(player, getRegionBorders(10553)) && !isQuestComplete(player, QuestName.THE_FREMENNIK_TRIALS) && stall.fullIDs.contains(4277)) {
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
            animate(player, ANIMATION)
            lockInteractions(player, 2)
        }
        if (++ticks % 3 != 0) {
            return false
        }
        val success = success()
        if (success) {
            if (stall == Stall.SILK_STALL) {
                player.getSavedData().globalData.setSilkSteal(System.currentTimeMillis() + 1200000)
            }
            if (stall == Stall.TEA_STALL) {
                player.getSavedData().globalData.setTeaSteal(System.currentTimeMillis() + 1200000)
            }
            if (node!!.isActive) {
                SceneryBuilder.replace(node, node!!.transform(stall!!.getEmpty(node!!.id)), stall.delay)
            }
            val item = stall!!.randomLoot
            player.inventory.add(item)
            rewardXP(player, Skills.THIEVING, stall.experience)
            if (item.id == 1987) {
                sendMessage(player,"You steal grapes from the grape stall.")
                return true
            }
            if (stall == Stall.CANDLES) {
                return true
            }
            if(stall == Stall.FISH_STALL) {
                finishDiaryTask(player, DiaryType.FREMENNIK, 1, 4)
            }
            player.packetDispatch.sendMessage("You steal " + (if (StringUtils.isPlusN(item.name)) "an" else "a") + " " + item.name.lowercase() + " from the " + stall.name.lowercase().replace('_', ' ') + ".")
            player.dispatch(ResourceProducedEvent(item.id, item.amount, node!!, 0))
        }
        return true
    }

    override fun message(type: Int) {
        if (stall == Stall.CANDLES) {
            return
        }
        if (type == 0) {
            sendMessage(player,"You attempt to steal some " + stall!!.message + " from the " + stall.name.lowercase().replace('_', ' '))
        }
    }

    /**
     * Checks if the thief is successful.
     * @return `True` if so.
     */
    private fun success(): Boolean {
        val mod = 0
        if (RandomFunction.random(15 + mod) < 4) {
            if (stall == Stall.CANDLES) {
                stun(player, 15, false)
                impact(player, 3, ImpactHandler.HitsplatType.NORMAL)
                sendMessage(player, "A higher power smites you.")
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
        private const val ANIMATION = Animations.MULTI_USE_TAKE_832
    }
}
