/*package content.region.miscquest.swept


import org.rs.consts.Vars
import core.api.addItemOrDrop
import core.api.setVarbit
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.plugin.Initializable

@Initializable
class SweptAway : Quest("Swept Away", 160, 159, 2, Vars.VARBIT_QUEST_SWEPT_AWAY_5448, 0, 1, 50) {

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
        player.packetDispatch.sendItemZoomOnInterface(Items.BROOMSTICK_14057, 230, 277, 5)
        drawReward(player, "2 Quest Points", ln++)
        addItemOrDrop(player, Items.BROOMSTICK_14057, 1)
        addItemOrDrop(player, Items.GOULASH_14058, 10)
        setVarbit(player, Vars.VARBIT_QUEST_SWEPT_AWAY_5448, 50, true)
    }

    override fun newInstance(`object`: Any?): Quest {
        return this
    }

}
//2 quest points (or 1 quest point; depending on whether you had completed Witch's Potion/Sheep Shearer before they got demoted to a miniquest)
//A broomstick.
//10 portions of experience-giving goulash, and a bowl to collect it with. Each drink gives experience equal to ten times the corresponding skill level, or five times if used for Invention.
//Access to the Purple cat miniquest.
//A purple cat after talking to Wendy and completing the miniquest.
//2 Treasure Hunter keys (Ironman accounts will not receive these)
//Music unlocked
//Magic and Mystery

 */