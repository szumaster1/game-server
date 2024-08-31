package core.game.dialogue

/**
 * This class represents a generic expression evaluator.
 * @author Ceikry
 *
 * @param T The type of the expression being evaluated.
 * @param expr The expression to be evaluated.
 * @param text A textual representation of the expression.
 * @param toStage The stage to which the expression is being evaluated.
 * @param skipPlayer A flag indicating whether to skip the player in the evaluation.
 * @constructor Initializes the expression evaluator with the provided parameters.
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
 * IfTopic class represents a conditional topic that can be shown based on a specific condition.
 *
 * @param T The type of the stage to which this topic will transition.
 * @param showCondition A Boolean indicating whether the condition to show this topic is met.
 * @param expr The facial expression associated with this topic.
 * @param text The text content of the topic.
 * @param toStage The stage to transition to when this topic is activated.
 * @param skipPlayer A Boolean indicating whether to skip the player for this topic.
 */
class IfTopic<T> @JvmOverloads constructor(
    expr: FacialExpression, // The facial expression that will be displayed with this topic.
    text: String, // The text that will be shown to the user.
    toStage: T, // The next stage to transition to when this topic is activated.
    val showCondition: Boolean, // Condition that determines if this topic should be shown.
    skipPlayer: Boolean = false // Default value for skipping the player, set to false.
) : Topic<T>(expr, text, toStage, skipPlayer) { // Inherits from the Topic class, passing parameters to the superclass.
    @JvmOverloads
    constructor(
        text: String, toStage: T, showCondition: Boolean, skipPlayer: Boolean = false
    ) : this(FacialExpression.ASKING, text, toStage, showCondition, skipPlayer)
}
