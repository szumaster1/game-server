/*package content.region.miscquest.deviousminds

.RELIC_6820
import core.api.consts.Vars
import core.api.rewardXP
import core.api.setVarbit
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.entity.skill.Skills
import core.plugin.Initializable

@Initializable
class DeviousMinds : Quest("Devious Minds", 46, 45, 1, Vars.VARBIT_QUEST_DEVIOUS_MINDS_1465, 0, 1, 80) {

    override fun drawJournal(player: Player?, stage: Int) {
        super.drawJournal(player, stage)
        var line = 11
        player ?: return

        if (stage == 100) {
            line++
            line(player, "<col=FF0000>QUEST COMPLETE!", line, false)
        }
    }

    override fun finish(player: Player?) {
        super.finish(player)
        player ?: return
        var ln = 10

        player.packetDispatch.sendItemZoomOnInterface(RELIC_6820, 230, 277, 5)

        drawReward(player, "1 Quest Point", ln++)
        drawReward(player, "5,000 Fletching XP", ln++)
        drawReward(player, "5,000 Runecrafting XP", ln++)
        drawReward(player, "6,500 Smithing XP", ln++)
        rewardXP(player, Skills.FLETCHING, 5000.0)
        rewardXP(player, Skills.RUNECRAFTING, 5000.0)
        rewardXP(player, Skills.SMITHING, 6500.0)
        setVarbit(player, Vars.VARBIT_QUEST_DEVIOUS_MINDS_1465, 80, true)
    }

    override fun newInstance(`object`: Any?): Quest {
        return this
    }

}

 */