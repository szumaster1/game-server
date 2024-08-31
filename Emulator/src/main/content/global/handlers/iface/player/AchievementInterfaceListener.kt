package content.global.handlers.iface.player

import cfg.consts.Components
import core.api.setInterfaceText
import core.game.component.Component
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.link.diary.DiaryType

/**
 * Achievement interface listener.
 */
class AchievementInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.AREA_TASK_259) { player, _, _, buttonID, _, _ ->
            val diary = DiaryType.forChild(buttonID)?.let { player.achievementDiaryManager.getDiary(it) }
            when (buttonID) {
                8 -> {
                    player.interfaceManager.openTab(2, Component(Components.QUESTJOURNAL_V2_274))
                    return@on true
                }
                else -> {
                    diary?.open(player)
                    return@on true
                }
            }
        }
    }
}
