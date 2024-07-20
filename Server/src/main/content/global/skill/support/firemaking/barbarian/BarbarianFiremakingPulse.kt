package content.global.skill.support.firemaking.barbarian

import content.data.skill.SkillingTool
import content.global.skill.BarbarianTraining
import content.global.skill.support.firemaking.data.Log
import core.api.*
import core.api.consts.Items
import core.game.event.LitFireEvent
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.GroundItem
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.world.GameWorld
import core.tools.RandomFunction
import kotlin.math.ceil

class BarbarianFiremakingPulse(player: Player, node: Item, groundItem: GroundItem?) : SkillPulse<Item?>(player, node) {

    private val fire = Log.forId(node.id)
    private var groundItem: GroundItem? = null
    private var ticks = 0

    init {
        if (groundItem == null) {
            this.groundItem = GroundItem(node, player.location, player)
            setAttribute(player, "remove-log", true)
        } else {
            this.groundItem = groundItem
            removeAttribute(player, "remove-log")
        }
    }

    override fun checkRequirements(): Boolean {
        if (fire == null) {
            return false
        }
        if (player.ironmanManager.isIronman && !groundItem!!.droppedBy(player)) {
            sendMessage(player, "You can't do that as an Ironman.")
            return false
        }
        if (getScenery(player.location) != null || player.zoneMonitor.isInZone("bank")) {
            sendMessage(player, "You can't light a fire here.")
            return false
        }
        if (!anyInInventory(player, SkillingTool.getFiremakingTool(player)!!.id)) {
            sendMessage(player, "You do not have the required items to light this.")
            return false
        }
        if (getStatLevel(player, Skills.FIREMAKING) < fire.barbarianLevel) {
            sendMessage(player, "You need a Firemaking level of " + fire.barbarianLevel + " to light this log.")
            return false
        }
        if (getAttribute(player, "remove-log", false)) {
            removeAttribute(player, "remove-log")
            if (inInventory(player, node!!.id, 1)) {
                replaceSlot(player, node!!.slot, Item(node!!.id, (node!!.amount - 1)), node, Container.INVENTORY)
                GroundItemManager.create(groundItem)
            }
        }
        return true
    }

    override fun animate() {

    }

    override fun reward(): Boolean {
        if (lastFire >= GameWorld.ticks) {
            createFire()
            return true
        }
        if (ticks == 0) {
            animate(player, SkillingTool.getFiremakingTool(player)!!.animation)
        }
        if (++ticks % 3 != 0) {
            return false
        }
        if (ticks % 12 == 0) {
            animate(player, SkillingTool.getFiremakingTool(player)!!.animation)
        }
        if (!success()) {
            return false
        }
        createFire()
        return true
    }

    fun createFire() {
        if (!groundItem!!.isActive) {
            return
        }
        val scenery = Scenery(fire!!.fireId, player.location)
        SceneryBuilder.add(scenery, fire.life, getAsh(player, fire, scenery))
        GroundItemManager.destroy(groundItem)
        player.moveStep()
        face(player, scenery.getFaceLocation(player.location))
        rewardXP(player, Skills.FIREMAKING, fire.xp)

        val playerRegion = player.viewport.region.id

        setLastFire()
        player.dispatch(LitFireEvent(fire.logId))

        if (getAttribute(player, BarbarianTraining.BARBARIAN_FIREMAKING_TUTORIAL, false)) {
            removeAttribute(player, BarbarianTraining.BARBARIAN_FIREMAKING_TUTORIAL)
            setAttribute(player, "/save:${BarbarianTraining.BARBARIAN_FIREMAKING_COMPLETE}", true)
            sendDialogueLines(player, "You feel you have learned more of barbarian ways. Otto might wish", "to talk to you more.")
        }
    }

    override fun message(type: Int) {
        val name = if (node!!.id == Items.JOGRE_BONES_3125) "bones" else "logs"
        when (type) {
            0 -> sendMessage(player, "You attempt to light the $name.")
            1 -> sendMessage(player, "The fire catches and the $name begin to burn.")
        }
    }

    val lastFire: Int get() = getAttribute(player, "last-firemake", 0)

    fun setLastFire() {
        setAttribute(player, "last-firemake", GameWorld.ticks + 2)
    }

    private fun success(): Boolean {
        val level = 1 + player.getSkills().getLevel(Skills.FIREMAKING)
        val req = fire!!.barbarianLevel.toDouble()
        val successChance = ceil((level * 50 - req * 15) / req / 3 * 4)
        val roll = RandomFunction.random(99)
        return successChance >= roll
    }

    companion object {
        @JvmStatic
        fun getAsh(player: Player?, fire: Log?, scenery: Scenery): GroundItem {
            val ash = GroundItem(Item(Items.ASHES_592), scenery.location, player)
            ash.decayTime = fire!!.life + 200
            return ash
        }
    }
}
