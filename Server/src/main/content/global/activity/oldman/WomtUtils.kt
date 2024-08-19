package content.global.activity.oldman

import core.api.getAttribute
import core.game.node.entity.player.Player

/**
 * Womt utils.
 */
object WomtUtils {

    // Constants for task-related attributes
    val TASK = "womt-task" // Task attribute key
    // Task start attribute key
    val TASK_START = "womt-start"
    // Task complete attribute key
    val TASK_COMPLETE = "womt-complete"
    // Task amount attribute key
    val TASK_AMOUNT = "womt-amount"
    // Task counter attribute key
    val TASK_COUNTER: String = "womt-total"
    // Item delivery attribute key
    val ITEM_DELIVERY = "item-delivery"
    // Letter delivery attribute key
    val LETTER_DELIVERY = "letter-delivery"

    // Function to retrieve the task counter for a player
    fun getTaskCounter(player : Player){
        getAttribute(player, TASK_COUNTER, -1)
    }

    // Function to increment the task counter for a player
    fun setTaskCounter(player : Player){
        return player.incrementAttribute(TASK_COUNTER)
    }
}
