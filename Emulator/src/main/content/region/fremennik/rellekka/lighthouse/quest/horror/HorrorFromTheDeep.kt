package content.region.fremennik.rellekka.lighthouse.quest.horror

import core.api.*
import cfg.consts.Components
import cfg.consts.Items
import cfg.consts.Vars
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.entity.skill.Skills
import core.plugin.Initializable

/**
 * Horror from the deep quest.
 */
@Initializable
class HorrorFromTheDeep : Quest("Horror from the Deep", 77, 76, 2, Vars.VARBIT_QUEST_HORROR_FROM_THE_DEEP_PROGRESS, 0, 1, 10) {

    /*
     * Quest description:
     * The Lighthouse protecting Kandarin's north-western coastline has
     * mysteriously stopped operating, and contact with the Lighthouse-keeper
     * Jossik has been lost. The council would greatly appreciate it if
     * somebody could discover for them what has happened to this most
     * vital landscape feature.
     */

    override fun drawJournal(player: Player, stage: Int) {
        super.drawJournal(player, stage)
        var line = 11
        player ?: return
        if (stage == 0) {
            line(player, "I can start this quest by speaking to !!Larrissa?? at the", line++)
            line(player, "!!Lighthouse?? to the !!North?? of the !!Barbarian Outpost??.", line++)
            line++
            line(player, "To complete this quest I need:", line++)
            line(player, if (hasLevelStat(player, Skills.AGILITY, 35)) "---Level 35 agility/--" else "!!Level 35 agility??", line++)
            line(player, if (hasLevelStat(player, Skills.MAGIC, 13)) "---Level 13 or higher magic will be an advantage/--" else "!!Level 13 or higher magic will be an advantage??", line++)
            line(player, "!!I must also be able to defeat strong level 100 enemies??", line++)
            line++
        }
        if (stage >= 1) {
            line(player, "I travelled to an isolated !!Lighthouse?? north of the !!Barbarian outpost??, ", line++, stage >= 2)
            line(player, "to find a !!Fremennik?? girl called !!Larrissa?? locked outside, ", line++, stage >= 2)
            line(player, "and worried about her boyfriend !!Jossik??.", line++, stage >= 2)
            line++
            line(player, "I recovered a !!spare key?? from Larrissa's cousin !!Gunnjorn??", line++, inInventory(player, Items.LIGHTHOUSE_KEY_3848) || stage >= 5)
            line(player, "and repaired the !!bridge?? to Rellekka with some planks.", line++, getAttribute(player,
                content.region.fremennik.rellekka.lighthouse.quest.horror.HorrorUtils.fixBridge, 0) == 2)
            line++
        }
        if (stage >= 20) {
            line(player, "I should talk to !!Larrissa??.", line++, stage >= 40)
            line++
        }
        if (stage >= 40 && getAttribute(player,
                content.region.fremennik.rellekka.lighthouse.quest.horror.HorrorUtils.STRANGE_WALL_DISCOVER, false)){
            line(player, "After I entered the !!lighthouse??, and repaired the !!lighting mechanism??, ", line++, stage >= 55)
            line(player, "I discovered a !!strange wall?? that blocked the entrance to an underground cavern, ", line++, stage >= 55)
            line(player, "where !!Jossik?? was.", line++, stage >= 55)
            line++
        }
        if (stage >= 60) {
            line(player, "After I killed some strange !!sea monsters??, ", line++, stage >= 90)
            line(player, "I managed to get !!Jossik?? out of the cavern", line++, stage >= 90)
            line(player, "and back to the !!lighthouse??.", line++, stage >= 90)
            line++
        }
        if (stage >= 90) {
            line(player, "I found a !!strange casket?? on the dead body of the !!sea monster??, ", line++, stage >= 100)
            line(player, "which !!Jossik?? said he could tell me about.", line++, stage >= 100)
            line++
        }
        if (stage == 100) {
            line(player, "<col=FF0000>QUEST COMPLETE!</col>", line, false)
        }
    }

    override fun finish(player: Player) {
        super.finish(player)
        player ?: return
        var ln = 10
        player.packetDispatch.sendString("You have survived the Horror From The Deep!", 277, 4)
        sendItemZoomOnInterface(player, Components.QUEST_COMPLETE_SCROLL_277, 5, HorrorUtils.QUEST_CASKET)
        drawReward(player, "2 Quest Points", ln++)
        drawReward(player, "4662 XP in each of: Ranged,", ln++)
        drawReward(player, "Magic, Strength", ln)
        setVarbit(player, Vars.VARBIT_QUEST_HORROR_FROM_THE_DEEP_PROGRESS, 10, true)
        rewardXP(player, Skills.RANGE, 4662.0)
        rewardXP(player, Skills.MAGIC, 4662.0)
        rewardXP(player, Skills.STRENGTH, 4662.0)
        removeAttributes(player,
            HorrorUtils.fixLighthouseMechanism,
            HorrorUtils.fixBridge,
            HorrorUtils.PUZZLE_PROGRESS,
            HorrorUtils.fixLighthouseMechanism,
            HorrorUtils.fixBridge,
            HorrorUtils.STRANGE_WALL_DISCOVER,
            HorrorUtils.PUZZLE_PROGRESS,
            HorrorUtils.STRANGE_W_AIR,
            HorrorUtils.STRANGE_W_FIRE,
            HorrorUtils.STRANGE_W_EARTH,
            HorrorUtils.STRANGE_W_WATER,
            HorrorUtils.STRANGE_W_ARROW,
            HorrorUtils.STRANGE_W_SWORD
        )
    }

    override fun newInstance(`object`: Any?): Quest {
        return this
    }

}