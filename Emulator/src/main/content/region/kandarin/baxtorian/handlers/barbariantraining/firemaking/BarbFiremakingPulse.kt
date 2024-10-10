package content.region.kandarin.baxtorian.handlers.barbariantraining.firemaking

import content.global.skill.firemaking.logs.Log
import content.global.skill.firemaking.logs.Log.Companion.forId
import content.global.skill.gather.SkillingTool
import content.region.kandarin.baxtorian.handlers.barbariantraining.BarbarianTraining
import core.api.*
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
import core.game.world.map.RegionManager.getObject
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.tools.RandomFunction
import org.rs.consts.Items
import kotlin.math.ceil

/**
 * Represents the pulse used to light a log using
 * barbarian training method.
 */
class BarbFiremakingPulse(player: Player, node: Item, groundItem: GroundItem?) :
    SkillPulse<Item?>(player, node) {

    /**
     * Represents the tools to use.
     */
    val tools = SkillingTool.getFiremakingTool(player)

    /**
     * Represents the animation to use.
     */
    private val animationId = Animation(tools!!.animation)

    /**
     * Represents the animation to use.
     */
    private val graphicId = Graphic(1169)

    /**
     * Represents the log being burned.
     */
    private val fire = forId(node.id)

    /**
     * Represents the ground item.
     */
    private var groundItem: GroundItem? = null

    /**
     * Represents the ticks.
     */
    private var ticks = 0


    /**
     * Constructs a new `FireMaking`.
     *
     * @param player     the player.
     * @param node       the node.
     * @param groundItem the ground item if not null.
     */
    init {
        if (groundItem == null) {
            this.groundItem = GroundItem(node, player.location, player)
            player.setAttribute("remove-log", true)
        } else {
            this.groundItem = groundItem
            player.removeAttribute("remove-log")
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
        if (getObject(player.location) != null || player.zoneMonitor.isInZone("bank")) {
            sendMessage(player, "You can't light a fire here.")
            return false
        }
        if (!inInventory(player, tools!!.id, 1)) {
            sendMessage(player, "You do not have the required items to light this.")
            return false
        }
        if (getStatLevel(player, Skills.FIREMAKING) < fire.barbarianLevel) {
            sendMessage(player, "You need a firemaking level of " + fire.barbarianLevel + " to light this log.")
            return false
        }
        if (player.getAttribute("remove-log", false)) {
            player.removeAttribute("remove-log")
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
            visualize(player, animationId, graphicId)
        }
        if (++ticks % 3 != 0) {
            return false
        }
        if (ticks % 12 == 0) {
            animate(player, animationId)
        }
        if (!success()) {
            return false
        }
        createFire()

        return true
    }

    /**
     * Creates the fire.
     */
    fun createFire() {
        if (!groundItem!!.isActive) {
            return
        }
        // Scenery originalOnSpot = RegionManager.getObject(player.getLocation());
        val `object` = Scenery(fire!!.fireId, player.location)
        SceneryBuilder.add(`object`, fire.life, getAsh(player, fire, `object`))
        GroundItemManager.destroy(groundItem)
        player.moveStep()
        player.faceLocation(`object`.getFaceLocation(player.location))
        player.getSkills().addExperience(Skills.FIREMAKING, fire.xp)

        val playerRegion = player.viewport.region.id

        setLastFire()
        player.dispatch(LitFireEvent(fire.logId))
        player.graphics(Graphic(-1))

        // Check if the player is in the firemaking tutorial.
        if (getAttribute(player, BarbarianTraining.FM_BASE, false)) {
            //removeAttribute(player, BarbarianTraining.FM_BASE)
            setAttribute(player, BarbarianTraining.FM_FULL, true)
            sendDialogueLines(
                player,
                "You feel you have learned more of barbarian ways. Otto might wish",
                "to talk to you more."
            )
        }
    }

    override fun message(type: Int) {
        val name = if (node!!.id == Items.JOGRE_BONES_3125) "bones" else "logs"
        when (type) {
            0 -> sendMessage(player, "You attempt to light the $name..")
            1 -> sendMessage(player, "The fire catches and the $name begin to burn.")
        }
    }

    val lastFire: Int
        /**
         * Gets the last firemake.
         *
         * @return the tick.
         */
        get() = player.getAttribute("last-firemake", 0)

    /**
     * Sets the last fire.
     */
    fun setLastFire() {
        player.setAttribute("last-firemake", GameWorld.ticks + 2)
    }

    /**
     * Checks if the player gets rewarded.
     *
     * @return `True` if so.
     */
    private fun success(): Boolean {
        val level = 1 + player.getSkills().getLevel(Skills.FIREMAKING)
        val req = fire!!.defaultLevel.toDouble()
        val successChance = ceil((level * 50 - req * 15) / req / 3 * 4)
        val roll = RandomFunction.random(99)
        if (successChance >= roll) {
            return true
        }
        return false
    }

    companion object {
        /**
         * Gets the ground item ash.
         *
         * @param player the player
         * @param fire   the fire
         * @param object the object.
         * @return `GroundItem` the itemm.
         */
        fun getAsh(player: Player?, fire: Log, `object`: Scenery): GroundItem {
            val ash = GroundItem(Item(Items.ASHES_592), `object`.location, player)
            ash.decayTime = fire.life + 200
            return ash
        }
    }
}
