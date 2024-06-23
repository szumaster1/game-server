package content.global.handlers.iface.quest

import core.api.consts.Components
import core.game.component.Component
import core.game.interaction.InterfaceListener

class QuestInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.QUESTJOURNAL_V2_274) { player, _, _, buttonID, _, _ ->
            when (buttonID) {
                3 -> {
                    player.achievementDiaryManager.openTab()
                    return@on true
                }
                else -> {
                    val quest = player.getQuestRepository().forButtonId(buttonID)
                    if (quest != null) {
                        player.interfaceManager.open(Component(Components.QUESTJOURNAL_SCROLL_275))
                        quest.drawJournal(player, quest.getStage(player))
                        return@on true
                    } else {
                        QuestTabUtils.showRequirementsInterface(player, buttonID)
                    }
                    return@on false
                }
            }
        }
    }
}
