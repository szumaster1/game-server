package core.game.node.entity.combat.equipment

import core.api.applyPoison
import core.api.registerTimer
import core.api.spawnTimer
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.impl.Animator.Priority
import core.game.node.entity.player.Player
import core.game.system.task.NodeTask
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction

/**
 * The fire types.
 * @author Emperor
 *
 * @param animation The animation associated with the fire type.
 * @param projectileId The ID of the projectile used for the fire type.
 * @param task The task that defines the behavior of the fire type.
 * @constructor Fire type
 */
enum class FireType(val animation: Animation, val projectileId: Int, val task: NodeTask) {

    /**
     * The normal dragon fire type.
     */
    FIERY_BREATH(Animation(81, Priority.HIGH), 393, object : NodeTask(0) {
        override fun exec(node: Node, vararg n: Node): Boolean {
            // Always returns true, indicating the task was executed successfully.
            return true
        }
    }),

    /**
     * The shocking breath fire type.
     */
    SHOCKING_BREATH(Animation(84, Priority.HIGH), 396, object : NodeTask(0) {
        override fun exec(node: Node, vararg n: Node): Boolean {
            // Randomly determines if the shock effect occurs (30% chance).
            if (RandomFunction.random(10) < 3) {
                // Updates the skill level of the entity, reducing it by 5.
                (node as Entity).getSkills().updateLevel(RandomFunction.random(3), -5, 0)
                // Sends a message to the player indicating they have been shocked.
                if (node is Player) {
                    node.packetDispatch.sendMessage("You have been shocked.")
                }
            }
            // Always returns true, indicating the task was executed successfully.
            return true
        }
    }),

    /**
     * The toxic breath fire type.
     */
    TOXIC_BREATH(Animation(82, Priority.HIGH), 394, object : NodeTask(0) {
        override fun exec(node: Node, vararg n: Node): Boolean {
            // Applies poison to the target entity with a specified damage value.
            applyPoison((node as Entity), (n[0] as Entity), 40)
            // Always returns true, indicating the task was executed successfully.
            return true
        }
    }),

    /**
     * The freezing breath fire type.
     */
    ICY_BREATH(Animation(83, Priority.HIGH), 395, object : NodeTask(0) {
        override fun exec(node: Node, vararg n: Node): Boolean {
            // Registers a timer for the frozen effect on the entity.
            registerTimer((node as Entity), spawnTimer("frozen", 7, true))
            // Always returns true, indicating the task was executed successfully.
            return true
        }
    })

}
