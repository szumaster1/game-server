package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType

/**
 * The Achievement effect.
 */
class AchievementEffect(private val diary: DiaryType, private val level: Int, private val task: Int) : ConsumableEffect() {

    override fun activate(p: Player) {
        p.achievementDiaryManager.finishTask(p, diary, level, task)
    }

}
