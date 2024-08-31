/*package content.region.miscquest.rocko


import cfg.consts.Vars
import core.api.rewardXP
import core.api.setVarbit
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.entity.skill.Skills
import core.plugin.Initializable

@Initializable
class RockingOut : Quest("Rocking Out", 154, 153, 2, Vars.VARBIT_QUEST_ROCKING_OUT_4797, 0, 1, 100) {

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

        player.packetDispatch.sendItemZoomOnInterface(Items.BONES_526, 230, 277, 5)

        drawReward(player, "2 Quest Points", ln++)
        drawReward(player, "25,000 Thieving XP", ln++)
        drawReward(player, "25,000 Smithing XP", ln++)
        drawReward(player, "25,000 Agility XP", ln++)
        drawReward(player, "25,000 Crafting XP", ln++)
        rewardXP(player, Skills.THIEVING, 25000.0)
        rewardXP(player, Skills.SMITHING, 25000.0)
        rewardXP(player, Skills.AGILITY, 25000.0)
        rewardXP(player, Skills.CRAFTING, 25000.0)
        setVarbit(player, Vars.VARBIT_QUEST_ROCKING_OUT_4797, 100, true)
    }

    override fun newInstance(`object`: Any?): Quest {
        return this
    }

}
//2 quest points
//25,000 Thieving experience
//25,000 Smithing experience
//25,000 Agility experience
//25,000 Crafting experience
//Free access to the Brimhaven Agility Arena
//Access to the Rock Island Prison, where players can steal from customs evidence files
//Access to a bank deposit box in Rimmington
//The ability to buy Braindeath 'rum' from The Rusty Anchor Inn
//The ability to understand pirates on Mos Le'Harmless without the book o' piracy
//The ability to catch pirate implings (with 76 Hunter)
//The ability to raise an ex-ex-parrot by speaking to Bill Teach and 50 Ships Mufassah (with 71 Summoning)
//2 Treasure Hunter keys (Ironman accounts will not receive these)

 */