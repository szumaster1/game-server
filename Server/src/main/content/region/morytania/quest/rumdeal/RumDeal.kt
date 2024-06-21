/*package content.region.miscquest.deal


import core.api.consts.Vars
import core.api.addItemOrDrop
import core.api.rewardXP
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.entity.skill.Skills
import core.plugin.Initializable

@Initializable
class RumDeal : Quest("Rum Deal", 107, 106, 2, Vars.VARP_QUEST_RUM_DEAL_600, 0, 1, 19) {

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

        player.packetDispatch.sendItemZoomOnInterface(Items.HOLY_WRENCH_6714, 230, 277, 5)

        drawReward(player, "2 Quest Points", ln++)
        drawReward(player, "7,000 Farming XP", ln++)
        drawReward(player, "7,000 Fishing XP", ln++)
        drawReward(player, "7,000 Prayer XP", ln++)
        rewardXP(player, Skills.FARMING, 7000.0)
        rewardXP(player, Skills.FISHING, 7000.0)
        rewardXP(player, Skills.PRAYER, 7000.0)
        addItemOrDrop(player, Items.HOLY_WRENCH_6714)
    }

    override fun newInstance(`object`: Any?): Quest {
        return this
    }

}
//2 quest points
//7,000 Farming experience lamp
//7,000 Fishing experience lamp
//7,000 Prayer experience lamp
//Holy wrench (gives extra Prayer point(s) when drinking a prayer or super restore potion)
//Access to Braindeath Island.
//Tangled Fishbowl and net
//2 Treasure Hunter keys (Ironman accounts will not receive these)

 */