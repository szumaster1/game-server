package core.game.dialogue

/**
 * Represents a generic expression evaluator.
 */
open class Topic<T> @JvmOverloads constructor(
    val expr: FacialExpression,
    val text: String,
    val toStage: T,
    val skipPlayer: Boolean = false
) {
    @JvmOverloads
    constructor(text: String, toStage: T, skipPlayer: Boolean = false) : this(
        FacialExpression.ASKING, text, toStage, skipPlayer
    )
}
/**
 * Represents a conditional topic that can be shown based on a specific condition.
 */
class IfTopic<T> @JvmOverloads constructor(
    expr: FacialExpression,
    text: String,
    toStage: T,
    val showCondition: Boolean,
    skipPlayer: Boolean = false
) : Topic<T>(expr, text, toStage, skipPlayer) {
    @JvmOverloads
    constructor(
        text: String, toStage: T, showCondition: Boolean, skipPlayer: Boolean = false
    ) : this(FacialExpression.ASKING, text, toStage, showCondition, skipPlayer)
}
