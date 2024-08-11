package core.game.diary

import core.api.inBorders
import core.game.node.entity.player.Player
import core.game.world.map.zone.ZoneBorders

/**
 * Represents a task related to an area in the diary.
 *
 * @property zoneBorders The borders of the zone for the task.
 * @property diaryLevel The level of the diary.
 * @property taskId The ID of the task.
 * @property condition The condition that needs to be met for the task.
 * @constructor Creates an AreaDiaryTask with the specified parameters.
 */
class AreaDiaryTask(
    val zoneBorders: ZoneBorders,
    val diaryLevel: DiaryLevel,
    val taskId: Int,
    private val condition: ((player: Player) -> Boolean)? = null
) {
    /**
     * Executes a given action when the task is satisfied.
     *
     * @param player The player to check the condition for.
     * @param then The action to execute when the condition is met.
     * @receiver The AreaDiaryTask instance.
     */
    fun whenSatisfied(player: Player, then: () -> Unit) {
        var result = inBorders(player, zoneBorders) // Checks if the player is within the specified zone borders.

        condition?.let {
            result = it(player) // Evaluates the additional condition if provided.
        }

        if (result) then() // Executes the action if the conditions are met.
    }
}
