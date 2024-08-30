package content.region.kandarin.yanille.handlers

import content.global.skill.support.agility.AgilityHandler
import core.api.*
import cfg.consts.Items
import core.game.global.action.ClimbActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneBuilder
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction

/**
 * Yanille dungeon listeners.
 */
class YanilleDungeonListeners : InteractionListener, MapZone("Yanille agility", true) {

    companion object {
        private const val STAIRS_DOWN = 1728
        private const val STAIRS_UP = 1729
        private const val STAIRS_UP_2 = 2316
        private const val CLOSED_CHEST = 377
        private const val SEARCH_CHEST = 378
        private val STAIRS_2 = intArrayOf(2318, 2317)
        private val BALANCING_LEDGE = intArrayOf(35969, 2303)
        private val SINISTER_CHEST = arrayOf(Item(205, 2), Item(207, 3), Item(209), Item(211), Item(213), Item(219))
    }

    override fun configure() {
        register(ZoneBorders(2544, 9481, 2631, 9587))
    }

    override fun defineListeners() {

        ZoneBuilder.configure(this)

        // Handles the action of climbing down the stairs
        on(STAIRS_DOWN, IntType.SCENERY, "climb-down") { player, _ ->
            teleport(player, Location(2620, 9565, 0))
            return@on true
        }

        // Handles the action of climbing up the stairs
        on(STAIRS_UP, IntType.SCENERY, "climb-up") { player, _ ->
            teleport(player, Location(2620, 9496, 0))
            return@on true
        }

        // Handles the action of climbing up the second set of stairs
        on(STAIRS_UP_2, IntType.SCENERY, "climb-up") { player, _ ->
            teleport(player, Location(2569, 9525, 0))
            return@on true
        }

        // Handles the action of climbing up or down the stairs
        on(STAIRS_2, IntType.SCENERY, "climb-up", "climb-down") { player, target ->
            if (getStatLevel(player, Skills.AGILITY) < 67) {
                sendMessage(player, "You need an agility level of at least 67 in order to do this.")
                return@on true
            }
            lock(player, 3)
            queueScript(player, 1, QueueStrength.SOFT) {
                val loc = if (target.id == 2317) Location(2614, 9504, 0) else Location(2617, 9571, 0)
                ClimbActionHandler.climb(player, if (target.id == 2317) { ClimbActionHandler.CLIMB_UP } else { ClimbActionHandler.CLIMB_DOWN }, loc)
                sendMessage(player, "You climb " + if (target.id == 2317) { "up" } else { "down" } + " the pile of rubble...")
                rewardXP(player, Skills.AGILITY, 5.5)
                return@queueScript stopExecuting(player)
            }
            return@on true
        }

        // Handles the action of walking across the balancing ledge
        on(BALANCING_LEDGE, IntType.SCENERY, "walk-across") { player, target ->
            handleBalancingLedge(player, target.asScenery())
            return@on true
        }

        // Handles the action of opening the closed chest
        on(CLOSED_CHEST, IntType.SCENERY, "open") { player, target ->
            if (!inInventory(player, Items.SINISTER_KEY_993, 1)) {
                sendMessage(player, "The chest is locked.")
            } else {
                if (freeSlots(player) == 0) {
                    sendMessage(player, "You don't have enough inventory space.")
                    return@on true
                }
                lock(player, 1)
                if (removeItem(player, Items.SINISTER_KEY_993)) {
                    sendMessage(player, "You unlock the chest with your key... A foul gas seeps from the chest")
                    applyPoison(player, player, 2)
                    for (item in SINISTER_CHEST) {
                        addItemOrDrop(player, item.id, item.amount)
                    }
                    SceneryBuilder.replace(target.asScenery(), target.asScenery().transform(target.id + 1), 5)
                }
            }
            return@on true
        }

        // Handles the action of searching the empty chest
        on(SEARCH_CHEST, IntType.SCENERY, "search") { player, _ ->
            sendMessage(player, "The chest is empty.")
            return@on true
        }

        // Handles the action of shutting the chest
        on(SEARCH_CHEST, IntType.SCENERY, "shut") { _, _ ->
            return@on true
        }
    }

    /**
     * This function handles the player's interaction with a balancing ledge.
     * It takes the player and scenery as parameters.
     *
     * @param player The player interacting with the balancing ledge.
     * @param scenery The scenery object representing the balancing ledge.
     */
    fun handleBalancingLedge(player: Player, scenery: Scenery) {
        if (getStatLevel(player, Skills.AGILITY) < 40) {
            sendMessage(player, "You need an agility level of at least 40 in order to do this.")
            return
        }
        val dir = Direction.getLogicalDirection(player.location, scenery.location)
        val diff = if (player.location.y == 9512) {
            0
        } else {
            1
        }
        var end = scenery.location
        var xp = 0.0
        if (AgilityHandler.hasFailed(player, 40, 0.01)) {
            lock(player, 3)
            GameWorld.Pulser.submit(object : Pulse(2, player) {
                override fun pulse(): Boolean {
                    AgilityHandler.fail(
                        player,
                        1,
                        Location(2572, 9570, 0),
                        Animation.create(761 - diff),
                        RandomFunction.random(1, 3),
                        "You lost your balance!"
                    )
                    runTask(player, 2) {
                        animate(player, 5056, true)
                    }
                    return true
                }
            })
        } else {
            xp = 22.5
            end = scenery.location.transform(dir.stepX * 7, dir.stepY * 7, 0)
        }
        AgilityHandler.walk(player, -1, player.location, end, Animation.create(157 - diff), xp, null)
    }

}
