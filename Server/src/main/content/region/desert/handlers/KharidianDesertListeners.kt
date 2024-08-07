package content.region.desert.handlers

import core.api.*
import core.api.consts.Items
import core.api.consts.Scenery
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.map.RegionManager.getLocalPlayers
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction

/**
 * Kharidian desert listeners.
 */
class KharidianDesertListeners : InteractionListener {

    companion object {
        val WATER_SKINS: Array<Item> = arrayOf(Item(1825), Item(1827), Item(1829), Item(1831))
        val ANIMATION: Animation = Animation(911)
        const val DRY_CACTUS: Int = 2671
        const val SPAWN_DELAY: Int = 45
        private const val TUNNEL = Scenery.TUNNEL_6481
        private const val PORTAL = Scenery.PORTAL_6551
        private const val STAIRS = Scenery.STAIRS_DOWN_28481
        private const val EXIT = Scenery.EXIT_28401
        private val PYRAMID_ENTRANCE = intArrayOf(Scenery.PYRAMID_ENTRANCE_6545, Scenery.PYRAMID_ENTRANCE_6547)
        private const val FALLEN_PILLAR_1 = Scenery.FALLEN_PILLAR_28515
        private const val FALLEN_PILLAR_2 = Scenery.FALLEN_PILLAR_28516
        private const val CACTUS = Scenery.KHARIDIAN_CACTUS_HEALTHY_2670
        private const val KNIFE = Items.KNIFE_946
    }

    override fun defineListeners() {

        on(TUNNEL, IntType.SCENERY, "enter") { player, _ ->
            teleport(player, Location(3233, 9313, 0))
            return@on true
        }

        on(PYRAMID_ENTRANCE, IntType.SCENERY, "open") { player, node ->
            // sendMessage(player, "A mystical power has sealed this door...")
            DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            return@on true
        }

        on(PORTAL, IntType.SCENERY, "use") { player, _ ->
            teleport(player, Location(3233, 2887, 0))
            return@on true
        }

        /*
            https://runescape.wiki/w/Battle_of_Ullek
         */

        on(STAIRS, IntType.SCENERY, "enter") { player, _ ->
            player.properties.teleportLocation = Location.create(3448, 9252, 1)
            return@on true
        }

        on(EXIT, IntType.SCENERY, "leave through") { player, _ ->
            player.properties.teleportLocation = Location.create(3412, 2848, 1)
            return@on true
        }

        on(FALLEN_PILLAR_1, IntType.SCENERY, "climb") { player, _ ->
            player.properties.teleportLocation = Location.create(3419, 2803, 1)
            return@on true
        }

        on(FALLEN_PILLAR_2, IntType.SCENERY, "climb") { player, _ ->
            player.properties.teleportLocation = Location.create(3419, 2801, 0)
            return@on true
        }

        /*
            Interaction with kharidian cactus's.
         */

        on(CACTUS, IntType.SCENERY, "cut") { player, node ->
            if (!inInventory(player, KNIFE)) {
                sendMessage(player, "You need a knife to cut this Cactus...")
                return@on true
            }

            var failed = false
            if (RandomFunction.random(3) == 1) {
                failed = true
                sendMessage(player, "You fail to cut the cactus correctly and it gives you no water this time.")
            }
            if (!failed) {
                val remove: Item = getWaterSkin(player)!!
                if (!removeItem(player, remove)) {
                    addItem(player, remove.id - 2)
                    sendMessage(player, "You top up your skin with water from the cactus.")
                } else {
                    sendMessage(player, "You have no empty waterskins to put the water in.")
                }
            }

            lock(player, 3)
            animate(player, ANIMATION)
            if (!failed) {
                rewardXP(player, Skills.WOODCUTTING, 10.0)
            }
            replaceScenery(
                node.asScenery(), DRY_CACTUS, SPAWN_DELAY + RandomFunction.random(getLocalPlayers(player).size / 2)
            )
            return@on true
        }
    }

    /**
     * Get water skin
     *
     * @param player
     * @return
     */
    fun getWaterSkin(player: Player): Item? {
        for (item in WATER_SKINS) {
            if (player.inventory.containsItem(item)) {
                return item
            }
        }
        return null
    }
}
