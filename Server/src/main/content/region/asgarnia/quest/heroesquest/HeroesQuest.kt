/*package content.kingdom.asgarnia.quest.member.hero


import core.api.consts.Vars
import core.api.rewardXP
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.entity.skill.Skills
import core.plugin.Initializable

@Initializable
class HeroesQuest : Quest("Heroes' Quest", 75, 74, 1, Vars.VARP_QUEST_HEROES_QUEST_188, 0, 1, 15) {

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

        player.packetDispatch.sendItemZoomOnInterface(Items.DRAGON_BATTLEAXE_1377, 230, 277, 5)

        drawReward(player, "1 Quest Point", ln++)
        drawReward(player, "Access to the Heroes' Guild",ln++)
        drawReward(player, "A total of 29,232 XP spread", ln++)
        drawReward(player, "over twelve skills", ln++)

        rewardXP(player, Skills.ATTACK, 3075.0)
        rewardXP(player, Skills.DEFENCE, 3075.0)
        rewardXP(player, Skills.STRENGTH, 3075.0)
        rewardXP(player, Skills.HITPOINTS, 3075.0)
        rewardXP(player, Skills.RANGE, 2075.0)
        rewardXP(player, Skills.FISHING, 2725.0)
        rewardXP(player, Skills.COOKING, 2825.0)
        rewardXP(player, Skills.WOODCUTTING, 1575.0)
        rewardXP(player, Skills.FIREMAKING, 1575.0)
        rewardXP(player, Skills.SMITHING, 2275.0)
        rewardXP(player, Skills.MINING, 2575.0)
        rewardXP(player, Skills.HERBLORE, 1325.0)
    }

    override fun newInstance(`object`: Any?): Quest {
        return this
    }

}
//1 Quest points Quest point
//Attack 3,075 Attack experience
//Defence 3,075 Defence experience
//Strength 3,075 Strength experience
//Hitpoints 3,075 Hitpoints experience
//Ranged 2,075 Ranged experience
//Fishing 2,725 Fishing experience
//Cooking 2,825 Cooking experience
//Woodcutting 1,575 Woodcutting experience
//Firemaking 1,575 Firemaking experience
//Smithing 2,275 Smithing experience
//Mining 2,575 Mining experience
//Herblore 1,325 Herblore experience
//Access to the Heroes' Guild
//The ability to purchase and wield dragon battleaxes and maces
//Ability to recharge amulets of glory at the Fountain of Rune (This grants 6 charges instead of the usual 4)
//Ability to use Charge dragonstone jewellery scrolls.
//Access to the Fountain of Heroes, where players can recharge their amulets of glory
*/