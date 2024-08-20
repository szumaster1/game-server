package core.game.world.map.zone

import MockPlayer
import core.api.hasTimerActive
import core.api.registerTimer
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.system.timer.impl.Teleblock
import core.game.world.map.Location
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

/**
 * Zone monitor tests.
 */
class ZoneMonitorTests {

    val GLORIES = intArrayOf(1710, 1708, 1706, 1704)

    init {
        TestUtils.preTestSetup()
    }

    /**
     * Evaluates success of glory teleport using all glory charged items.
     *
     * @param expected A boolean indicating the expected outcome of the teleportation.
     * @param p The Player object representing the player attempting the teleport.
     */
    fun teleportWithGlory(expected : Boolean, p : Player) {
        for (id in GLORIES) {
            val glory = Item(id, 1)
            Assertions.assertEquals(expected, p.zoneMonitor.teleport(1, glory))
        }
    }

    /**
     * Glory teleport success.
     */
    @Nested
    inner class GloryTeleportSuccess {

        /**
         * Success on level 21 wilderness.
         */
        @Test
        fun successOnLevel21Wilderness() {
            TestUtils.getMockPlayer("").use {p ->
                // level 21 wilderness
                p.skullManager.level = 21;
                p.location = (Location.create(3067, 3683, 0))
                Assertions.assertTrue(p.locks.isTeleportLocked)
                teleportWithGlory(true, p)
            }
        }

        /**
         * Success on level 30 wilderness.
         */
        @Test
        fun successOnLevel30Wilderness() {
            TestUtils.getMockPlayer("").use {p ->
                // level 30 wilderness
                p.location = (Location.create(3069, 3755, 0))
                p.skullManager.level = 30;
                Assertions.assertTrue(p.locks.isTeleportLocked)
                teleportWithGlory(true, p)
            }
        }

        /**
         * Success on no restrictions.
         */
        @Test
        fun successOnNoRestrictions() {
            TestUtils.getMockPlayer("").use {p ->
            // lumbridge
            p.location = (Location.create(3222, 3218, 0))
            Assertions.assertFalse(p.locks.isTeleportLocked)
            Assertions.assertFalse(hasTimerActive(p, "teleblock"))
            Assertions.assertFalse(p.zoneMonitor.isRestricted(ZoneRestriction.TELEPORT))
            teleportWithGlory(true, p)
            }
        }

    }

    /**
     * Glory teleport failure.
     */
    @Nested
    inner class GloryTeleportFailure {

        /**
         * Fail on tele block.
         */
        @Test
        fun failOnTeleBlock() {
            TestUtils.getMockPlayer("").use { p ->
                registerTimer(p, Teleblock())
                Assertions.assertTrue(hasTimerActive(p, "teleblock"))
                teleportWithGlory(false, p)
            }
        }

        /**
         * Fail on lock.
         */
        @Test
        fun failOnLock() {
            TestUtils.getMockPlayer("").use { p ->
                p.locks.lockTeleport(100000)
                Assertions.assertTrue(p.locks.isTeleportLocked)
                teleportWithGlory(false, p)
            }
        }

    }

}
