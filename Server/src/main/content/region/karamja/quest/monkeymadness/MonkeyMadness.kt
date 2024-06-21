/*package content.region.miscquest.mm


import core.api.consts.Vars
import core.api.addItemOrDrop
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.plugin.Initializable

@Initializable
class MonkeyMadness : Quest("Monkey Madness", 88, 87, 3, Vars.VARP_QUEST_MONKEY_MADNESS_365, 0, 1, 9) {

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

        player.packetDispatch.sendItemZoomOnInterface(Items.DRAGON_SCIMITAR_4587, 230, 277, 5)
        drawReward(player, "3 Quest Points", ln++)
        addItemOrDrop(player, Items.COINS_995, 10000)
        addItemOrDrop(player, Items.DIAMOND_1602, 3)
    }

    override fun newInstance(`object`: Any?): Quest {
        return this
    }

}
// 3 Quest points
// 10,000 Coins
// 3 Diamonds
// Speaking to Daero after the quest gives the choice of either, 35,000 experience in Attack Attack and Defence Defence and 20,000 experience in Strength Strength and Hitpoints Hitpoints OR 35,000 experience in Strength Strength and Hitpoints Hitpoints and 20,000 experience in Attack Attack and Defence Defence. The skills you tell Daero you want to focus on will receive 35,000 XP each, and the others 20,000 each. Returning to Ape Atoll after completing the quest no longer requires undergoing Daero's training.
// The ability to buy and wield the Dragon scimitar.
// The ability to turn into a monkey in Ape Atoll & Ardougne Zoo, using the Monkey greegree.
// Full access to Ape Atoll, with exception of the bank and javelin store, which are unlocked after Monkey Madness II.

 */