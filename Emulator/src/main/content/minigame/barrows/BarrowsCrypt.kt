package content.minigame.barrows

import core.api.sendMessage
import core.api.setAttribute
import core.game.global.action.DigAction
import core.game.global.action.DigSpadeHandler.register
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.map.RegionManager.getTeleportLocation

/**
 * Handles a barrows crypt.
 * @author Emperor
 *
 * @param index The index of the crypt
 * @param npcId The ID of the NPC associated with the crypt
 * @param enterLocation The location where players enter the crypt
 * @param exitLocation The location where players exit the crypt
 * @constructor Creates a Barrows crypt with the specified properties
 */
open class BarrowsCrypt(val index: Int, val npcId: Int, val enterLocation: Location, val exitLocation: Location) {

    /**
     * Open sarcophagus
     *
     * @param player The player who is opening the sarcophagus
     * @param object The scenery object representing the sarcophagus
     */
    fun openSarcophagus(player: Player, `object`: Scenery) {
        if (index == player.getSavedData().activityData.barrowTunnelIndex) {
            player.dialogueInterpreter.open("barrow_tunnel", index)
            return
        }
        if (player.getSavedData().activityData.barrowBrothers[index] || player.getAttribute<Any?>("barrow:npc") != null) {
            sendMessage(player, "You don't find anything.")
            return
        }
        sendMessage(player, "You don't find anything.")
        val location = getTeleportLocation(
            `object`.location.transform(Direction.SOUTH_WEST),
            `object`.sizeX + 1,
            `object`.sizeY + 1
        )
        spawnBrother(player, location)
    }

    /**
     * Spawn brother
     *
     * @param player The player who is spawning the brother
     * @param location The location where the brother will be spawned
     * @return True if the brother was successfully spawned, false otherwise
     */
    fun spawnBrother(player: Player, location: Location?): Boolean {
        if (player.getAttribute("brother:$index", false)) {
            return false
        }
        val npc: NPC = BarrowBrotherNPC(player, npcId, location)
        npc.init()
        setAttribute(player, "barrow:npc", npc)
        setAttribute(player, "brother:$index", true)
        return true
    }

    /**
     * Enter the crypt
     *
     * @param player The player who is entering the crypt
     */
    protected fun enter(player: Player) {
        player.addExtension(BarrowsCrypt::class.java, this)
        sendMessage(player, "You've broken into a crypt!")
        player.properties.teleportLocation = enterLocation
    }

    companion object {
        const val AHRIM = 0
        const val DHAROK = 1
        const val GUTHAN = 2
        const val KARIL = 3
        const val TORAG = 4
        const val VERAC = 5

        private val CRYPTS = arrayOf(
            BarrowsCrypt(AHRIM, 2025, Location.create(3557, 9703, 3), Location.create(3565, 3289, 0)),
            BarrowsCrypt(DHAROK, 2026, Location.create(3556, 9718, 3), Location.create(3575, 3298, 0)),
            BarrowsCrypt(GUTHAN, 2027, Location.create(3534, 9704, 3), Location.create(3577, 3283, 0)),
            BarrowsCrypt(KARIL, 2028, Location.create(3546, 9684, 3), Location.create(3565, 3276, 0)),
            BarrowsCrypt(TORAG, 2029, Location.create(3568, 9683, 3), Location.create(3553, 3283, 0)),
            BarrowsCrypt(VERAC, 2030, Location.create(3578, 9706, 3), Location.create(3557, 3298, 0))
        )

        /**
         * Initialize the Barrows crypts
         */
        fun init() {
            for (crypt in CRYPTS) {
                val action: DigAction = object : DigAction {

                    override fun run(player: Player?) {
                        crypt.enter(player!!)
                    }
                }
                val base = crypt.exitLocation
                for (x in -2..2) {
                    for (y in -2..2) {
                        register(base.transform(x, y, 0), action)
                    }
                }
            }
        }

        /**
         * Get the Barrows crypt with the specified index
         *
         * @param index The index of the crypt
         * @return The Barrows crypt with the specified index
         */
        fun getCrypt(index: Int): BarrowsCrypt {
            return CRYPTS[index]
        }
    }
}
