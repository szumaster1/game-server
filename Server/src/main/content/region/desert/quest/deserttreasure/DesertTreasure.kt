/*package content.kingdom.desert.quest.member.deserttreasure

import core.api.consts.Vars
import core.api.isQuestComplete
import core.api.rewardXP
import core.api.setVarbit
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.entity.skill.Skills
import core.plugin.Initializable

@Initializable
class DesertTreasure : Quest("Desert Treasure",45, 44, 3,Vars.VARBIT_QUEST_DESERT_TREASURE_358,0,1,15){

    override fun drawJournal(player: Player, stage: Int) {
        super.drawJournal(player, stage)
        var line = 12

        val stage = player?.questRepository?.getStage("Desert Treasure")!!

        when (stage) {
            0 -> {
                line(player,"I can start this quest by speaking to !!The Archaeologist??", line++)
                line(player,"who is exploring the !!Bedabin Camp?? South West of the", line++)
                line(player,"!!Shantay Pass.??", line++)
                line(player,"To complete this quest I will need:", line++)
                if (player.skills.getStaticLevel(Skills.SLAYER) < 10) {
                    line(player,"Level 10 Slayer", line++)
                } else {
                    line(player,"---Level 10 Slayer/--", line++)
                }
                if (player.skills.getStaticLevel(Skills.SLAYER) < 10) {
                    line(player,"Level 50 Firemaking", line++)
                }
                if (player.skills.getStaticLevel(Skills.SLAYER) < 10) {
                    line(player,"Level 50 Magic", line++)
                }
                if (player.skills.getStaticLevel(Skills.SLAYER) < 10) {
                    line(player,"Level 53 Thieving", line++)
                }
                line(player,"I must have completed the following quests:", line++)

                if (isQuestComplete(player,"Dig Site")) {
                    line(player,"---!!The Dig Site Quest??/--", line++)
                } else {
                    line(player,"!!The Dig Site Quest??", line++)
                }

                if (isQuestComplete(player,"The Tourist Trap")) {
                    line(player,"---!!The Tourist Trap??/--", line++)
                } else {
                    line(player,"!!The Tourist Trap??", line++)
                }

                if (isQuestComplete(player,"Temple of Ikov")) {
                    line(player,"---!!Temple of Ikov??/--", line++)
                } else {
                    line(player,"!!Temple of Ikov??", line++)
                }

                if (isQuestComplete(player,"Priest In Peril")) {
                    line(player,"---!!Priest In Peril??/--", line++)
                } else {
                    line(player,"!!Priest In Peril??", line++)
                }

                if (isQuestComplete(player,"Waterfall Quest")) {
                    line(player,"---!!Waterfall Quest??/--", line++)
                } else {
                    line(player,"!!Waterfall Quest??", line++)
                }

                if (isQuestComplete(player,"Troll Stronghold")) {
                    line(player,"---!!Troll Stronghold??/--", line++)
                } else {
                    line(player,"!!Troll Stronghold??", line++)
                }


                line(player,"", line++)
            }
        }
    }

    override fun finish(player: Player) {
        var ln = 10
        super.finish(player)
        player.packetDispatch.sendString("You have completed Desert Treasure!", 277, 4)
        player.packetDispatch.sendItemZoomOnInterface(1891, 240, 277, 5)

        drawReward(player,"3 Quest Points", ln++)
        drawReward(player,"20,000 Magic XP", ln++)
        drawReward(player,"Ability to use", ln++)
        drawReward(player,"Ancient Magicks", ln)
        rewardXP(player, Skills.MAGIC, 20000.0)
        setVarbit(player, Vars.VARBIT_QUEST_DESERT_TREASURE_358,15, true)
    }

    override fun newInstance(`object`: Any?): Quest {
        return this
    }

}
*/