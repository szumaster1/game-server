package core.game.interaction

import core.game.node.Node
import core.game.node.entity.player.Player

typealias UseWithExecutor = (Player, Node, Node, Int) -> Boolean
typealias InteractExecutor = (Player, Node, Int) -> Boolean
typealias VoidExecutor = (Int) -> Boolean

/**
 * Represents the QueueStrength enum class.
 */
enum class QueueStrength {
    /**
     * Weak strength.
     */
    WEAK,

    /**
     * Normal strength.
     */
    NORMAL,

    /**
     * Strong strength.
     */
    STRONG,

    /**
     * Soft strength.
     */
    SOFT
}

/**
 * Script.
 *
 * @param T The type parameter for the Script class.
 * @property execution The execution function of the script.
 * @property persist Indicates if the script should persist.
 * @constructor Represents the Script class.
 */
open class Script<T>(val execution: T, val persist: Boolean) {
    var state: Int = 0
    var nextExecution = 0
}

/**
 * Interaction.
 *
 * @property distance The distance for the interaction.
 * @constructor Represents the Interaction class.
 *
 * @param execution The execution function for the interaction.
 * @param persist Indicates if the interaction should persist.
 */
class Interaction(execution: InteractExecutor, val distance: Int, persist: Boolean) :
    Script<InteractExecutor>(execution, persist)

/**
 * Use with interaction.
 *
 * @property distance The distance for the use-with interaction.
 * @property used The node being used.
 * @property with The node being used with.
 * @constructor Represents the UseWithInteraction class.
 *
 * @param execution The execution function for the use-with interaction.
 * @param persist Indicates if the use-with interaction should persist.
 */
class UseWithInteraction(
    execution: UseWithExecutor,
    val distance: Int,
    persist: Boolean,
    val used: Node,
    val with: Node
) : Script<UseWithExecutor>(execution, persist)

/**
 * Queued script.
 *
 * @property strength The strength of the queued script.
 * @constructor Represents the QueuedScript class.
 *
 * @param executor The executor function for the queued script.
 * @param persist Indicates if the queued script should persist.
 */
class QueuedScript(executor: VoidExecutor, val strength: QueueStrength, persist: Boolean) :
    Script<VoidExecutor>(executor, persist)

/**
 * Queued use with.
 *
 * @property strength The strength of the queued use-with interaction.
 * @property used The node being used.
 * @property with The node being used with.
 * @constructor Represents the QueuedUseWith class.
 *
 * @param executor The executor function for the queued use-with interaction.
 * @param persist Indicates if the queued use-with interaction should persist.
 */
class QueuedUseWith(
    executor: UseWithExecutor,
    val strength: QueueStrength,
    persist: Boolean,
    val used: Node,
    val with: Node
) : Script<UseWithExecutor>(executor, persist)
