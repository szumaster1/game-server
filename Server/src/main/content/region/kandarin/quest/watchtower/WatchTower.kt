/*package content.kingdom.kandarin.quest.twatchtower


import core.api.consts.Vars
import core.api.addItemOrDrop
import core.api.rewardXP
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.entity.skill.Skills
import core.plugin.Initializable

@Initializable
class WatchTower : Quest("Watchtower", 131, 130, 4, Vars.VARP_QUEST_WATCHTOWER_212, 0, 1, 13) {

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
        player.packetDispatch.sendItemZoomOnInterface(Items.COINS_995, 230, 277, 5)
        drawReward(player, "4 Quest Points", ln++)
        drawReward(player, "15,250 Magic XP", ln++)
        drawReward(player, "5,000 Coins", ln++)
        rewardXP(player, Skills.MAGIC, 1500.0)
        addItemOrDrop(player, Items.COINS_995, 5000)
    }

    override fun newInstance(`object`: Any?): Quest {
        return this
    }

}
//4 quest points
//15,250 Magic experience
//5,000 coins
//A spell scroll, which will teach you how to use the Watchtower Teleport spell
//Access to Gu'Tanoth and the Ogre Enclave
//2 Treasure Hunter keys (Ironman accounts will not receive these)

 */