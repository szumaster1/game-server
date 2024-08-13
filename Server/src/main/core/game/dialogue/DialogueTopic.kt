package core.game.dialogue

/**
 * Topic
 *
 * @param T
 * @param expr
 * @param text
 * @param toStage
 * @param skipPlayer
 * @constructor Topic
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
 * If topic
 *
 * @param T
 * @param showCondition
 * @constructor
 *
 * @param expr
 * @param text
 * @param toStage
 * @param skipPlayer
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
