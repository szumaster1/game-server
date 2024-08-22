/*package content.kingdom.misthalin.quest.member.tearsofguthix


import cfg.consts.Vars
import core.api.setVarbit
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.plugin.Initializable

@Initializable
class TearsOfGuthix : Quest("Tears of Guthix", 120, 119, 0, Vars.VARBIT_QUEST_TEARS_OF_GUTHIX_451, 0, 1, 2) {
    companion object {
        const val TearsOfGuthix = "Tears of Guthix"
    }

    override fun drawJournal(player: Player, stage: Int) {
        super.drawJournal(player, stage)
        var line = 11
        player ?: return

        if (stage == 100) {
            line++
            line(player, "<col=FF0000>QUEST COMPLETE!", line, false)
        }
    }

    override fun finish(player: Player) {
        super.finish(player)
        player ?: return
        var ln = 10
        player.packetDispatch.sendItemZoomOnInterface(Items.STEAM_RUNE_4694, 230, 277, 5)
        setVarbit(player, Vars.VARBIT_QUEST_TEARS_OF_GUTHIX_451, 2, true)
    }

    override fun newInstance(`object`: Any?): Quest {
        return this
    }

}
*/