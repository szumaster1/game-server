package core.game.diary

import core.api.inBorders
import core.game.node.entity.player.Player
import core.game.world.map.zone.ZoneBorders

/**
 * Represents a task within a specific area in a player's diary.
 *
 * @param zoneBorders The zone associated with this task.
 * @param diaryLevel The level of the diary to which this task belongs.
 * @param taskId The task.
 * @param condition An optional condition that must be met for the task to be considered satisfied.
 *                  If `null`, the task is considered satisfied if the player is within the specified zone.
 * @constructor Creates an instance of [AreaDiaryTask] with the given parameters.
 */
class AreaDiaryTask(
    val zoneBorders: ZoneBorders,
    val diaryLevel: DiaryLevel,
    val taskId: Int,
    private val condition: ((player: Player) -> Boolean)? = null
) {
    /**
     * Checks if the task's condition is met for the specified player, and if so, executes the provided action.
     *
     * @param player The player to evaluate the condition for.
     * @param then The action to execute if the condition is satisfied.
     */
    fun whenSatisfied(player: Player, then: () -> Unit) {
        var result = inBorders(player, zoneBorders)

        condition?.let {
            result = it(player)
        }

        if (result) then()
    }
}
