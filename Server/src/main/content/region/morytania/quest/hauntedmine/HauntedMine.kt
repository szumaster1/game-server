/*package content.kingdom.morytania.quest.hauntedmine


import core.api.consts.Vars
import core.api.addItemOrDrop
import core.api.rewardXP
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.entity.skill.Skills
import core.plugin.Initializable

@Initializable
class HauntedMine : Quest("Haunted Mine", 73, 72, 2, Vars.VARP_QUEST_HAUNTED_MINE_382, 0, 1, 11) {

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

        player.packetDispatch.sendItemZoomOnInterface(Items.CRYSTAL_MINE_KEY_4077, 230, 277, 5)
        drawReward(player, "2 Quest Points", ln++)
        drawReward(player, "22,000 Strength XP", ln++)
        rewardXP(player, Skills.STRENGTH, 22000.0)
        addItemOrDrop(player, Items.CRYSTAL_MINE_KEY_4077)
    }

    override fun newInstance(`object`: Any?): Quest {
        return this
    }

}
//2 quest points
//22,000 Strength experience lamp
//Crystal-mine key (which can be added to the steel key ring)
//The ability to make salve amulets, which gives combat bonuses against undead creatures. Simply use the salve shard obtained at the end of the quest with a ball of wool.
//Access to the Lair of Tarn Razorlor miniquest, where players can create an enhanced version of the salve amulet
//2 Treasure Hunter keys (Ironman accounts will not receive these)
*/