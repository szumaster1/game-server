package core.game.diary

import core.api.inBorders
import core.game.node.entity.player.Player
import core.game.world.map.zone.ZoneBorders

/**
 * Area diary task
 *
 * @property zoneBorders
 * @property diaryLevel
 * @property taskId
 * @property condition
 * @constructor Area diary task
 */
class AreaDiaryTask(
    val zoneBorders: ZoneBorders,
    val diaryLevel: DiaryLevel,
    val taskId: Int,
    private val condition: ((player: Player) -> Boolean)? = null
) {
    /**
     * When satisfied
     *
     * @param player
     * @param then
     * @receiver
     */
    fun whenSatisfied(player: Player, then: () -> Unit) {
        var result = inBorders(player, zoneBorders)

        condition?.let {
            result = it(player)
        }

        if (result) then()
    }
}