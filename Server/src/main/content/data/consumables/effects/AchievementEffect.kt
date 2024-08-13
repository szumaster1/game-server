package content.data.consumables.effects

import core.api.finishDiaryTask
import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType

/**
 * Achievement effect.
 *
 * @param diary  Represents the type of diary for the achievement effect.
 * @param level  Represents the level of the achievement effect.
 * @param task   Represents the task ID for the achievement effect.
 * @constructor Represents an AchievementEffect with diary, level, and task.
 */
class AchievementEffect(var diary: DiaryType, var level: Int, var task: Int) : ConsumableEffect() {

    /**
     * Activates the achievement effect for a player.
     *
     * @param player The player for whom the achievement effect is activated.
     */
    override fun activate(player: Player) {
        finishDiaryTask(player, diary, level, task) // Calls the function to finish a diary task for the player.
    }

}
