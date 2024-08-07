package core.game.interaction

import core.game.node.Node
import core.game.node.entity.player.Player

typealias UseWithExecutor = (Player, Node, Node, Int) -> Boolean
typealias InteractExecutor = (Player, Node, Int) -> Boolean
typealias VoidExecutor = (Int) -> Boolean

/**
 * Queue strength
 *
 * @constructor Queue strength
 */
enum class QueueStrength {
    /**
     * Weak
     *
     * @constructor Weak
     */
    WEAK,

    /**
     * Normal
     *
     * @constructor Normal
     */
    NORMAL,

    /**
     * Strong
     *
     * @constructor Strong
     */
    STRONG,

    /**
     * Soft
     *
     * @constructor Soft
     */
    SOFT
}

/**
 * Script
 *
 * @param T
 * @property execution
 * @property persist
 * @constructor Script
 */
open class Script<T>(val execution: T, val persist: Boolean) {
    var state: Int = 0
    var nextExecution = 0
}

/**
 * Interaction
 *
 * @property distance
 * @constructor
 *
 * @param execution
 * @param persist
 */
class Interaction(execution: InteractExecutor, val distance: Int, persist: Boolean) :
    Script<InteractExecutor>(execution, persist)

/**
 * Use with interaction
 *
 * @property distance
 * @property used
 * @property with
 * @constructor
 *
 * @param execution
 * @param persist
 */
class UseWithInteraction(
    execution: UseWithExecutor,
    val distance: Int,
    persist: Boolean,
    val used: Node,
    val with: Node
) : Script<UseWithExecutor>(execution, persist)

/**
 * Queued script
 *
 * @property strength
 * @constructor
 *
 * @param executor
 * @param persist
 */
class QueuedScript(executor: VoidExecutor, val strength: QueueStrength, persist: Boolean) :
    Script<VoidExecutor>(executor, persist)

/**
 * Queued use with
 *
 * @property strength
 * @property used
 * @property with
 * @constructor
 *
 * @param executor
 * @param persist
 */
class QueuedUseWith(
    executor: UseWithExecutor,
    val strength: QueueStrength,
    persist: Boolean,
    val used: Node,
    val with: Node
) : Script<UseWithExecutor>(executor, persist)