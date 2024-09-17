package content.global.activity.mogre

import core.api.*
import org.rs.consts.Graphics
import org.rs.consts.Sounds
import core.game.interaction.QueueStrength
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders
import core.tools.RandomFunction

/**
 * Represents the Skippy NPC.
 */
class SkippyNPC : NPCBehavior(2795) {

    private val forceChat = arrayOf(
        "I'll get you, I'll get you all!",
        "The horror...The horror...",
        "They're coming out of the walls!",
        "Mudskippers, thousands of them!",
        "I've got a bottle with your name on it!"
    )

    override fun onCreation(self: NPC) {
        val route = arrayOf(
            Location.create(2980, 3198, 0),
            Location.create(2973, 3193, 0),
            Location.create(2977, 3196, 0),
            Location.create(2984, 3192, 0),
            Location.create(2982, 3196, 0)
        )
        if (!inBorders(self, SkippyUtils.TUTORIAL_ISLAND)) {
            self.configureMovementPath(*route)
            self.isWalks = true
        } else {
            self.isNeverWalks = true
            self.isWalks = false
        }
    }

    /**
     * Handles the behavior of Skippy NPC during each tick.
     *
     * @param self The Skippy NPC entity.
     * @return Boolean value indicating the tick status.
     */
    override fun tick(self: NPC): Boolean {
        if (!inBorders(self, SkippyUtils.TUTORIAL_ISLAND) && RandomFunction.random(30) == 5) {
            stopWalk(self)
            queueScript(self, 1, QueueStrength.SOFT) {
                animate(self, SkippyUtils.ANIMATION_THROW)
                sendChat(self, "Take this")
                self.faceLocation(self.location.transform(Direction.SOUTH))
                playGlobalAudio(self.location, Sounds.SKIPPY_THROWGLASS_1398, 1)
                spawnProjectile(
                    self.location,
                    self.location.transform(Direction.SOUTH, 4),
                    Graphics.SPINNING_VIAL_49,
                    30,
                    20,
                    10,
                    100,
                    0
                )
                return@queueScript stopExecuting(self)
            }
        } else if (RandomFunction.random(30) == 4) {
            sendChat(self, forceChat.random())
        } else {
            sendChat(self, "You can skip the tutorial by talking to me!")
            return true
        }
        return super.tick(self)
    }

}
