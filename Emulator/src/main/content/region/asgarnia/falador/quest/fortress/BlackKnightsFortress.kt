package content.region.asgarnia.falador.quest.fortress

import cfg.consts.Components
import cfg.consts.Items
import cfg.consts.Vars
import content.region.asgarnia.falador.quest.fortress.dialogue.SirAmikVarzeDialogue
import content.region.asgarnia.falador.quest.fortress.handlers.BlackKnightsFortressPlugin
import core.api.sendItemZoomOnInterface
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.plugin.Initializable
import core.plugin.PluginManager.definePlugins

/**
 * Black knights fortress quest.
 */
@Initializable
class BlackKnightsFortress : Quest("Black Knights' Fortress", 14, 13, 3, Vars.VARP_QUEST_BLACK_KNIGHTS_FORTRESS_PROGRESS, 0, 1, 4) {

    /*
     * The Black Knights are up to no good.
     * You are hired by the White Knights to spy on them and uncover their evil scheme.
     */

    override fun drawJournal(player: Player, stage: Int) {
        super.drawJournal(player, stage)
        var line = 11

        when (stage) {
            0 -> {
                line(player, "I can start this quest by speaking to the !!Sir Amik Varze?? at the", line++, true)
                line(player, "!!White Knight's Castle?? in !!Falador??.", line++, true)
                if (player.getQuestRepository().points < 12) {
                    line(player,"!!I must have a total of at least 12 Quest Points??", line++, true)
                } else {
                    line(player, "<str>I have a total of at least 12 Quest Points", line++, true)
                }
                line(player, "I would have an advantage if I could fight !!Level 33 Knights??", line++, true)
                line(player, "and if I had a smithing level of !!26??.", line++, true)
            }

            10 -> {
                line(player, "!!Sir Amik Varze?? has asked me to investigate the !!Black??", line++, true)
                line(player, "!!Knights' Fortress?? which is located on !!Ice Mountain??.", line++, true)
                line(player, "I need to disguise myself to gain entry to the !!Black??", line++, true)
                line(player, "!!Knights' Fortress??.", line++, true)
            }

            20 -> {
                line(player, "<str>Sir Amik Varze asked me to investigate the Black Knights'", line++, true)
                line(player, "<str>Fortress. I sneaked inside disguised as a Guard.", line++, true)
                line(player, "I eavesdropped on a Witch and the Black Knight Captain", line++, true)
                line(player, "and discovered that their invincibility potion can be", line++, true)
                line(player, "destroyed with a normal " + RED + "cabbage.", line++, true)
            }

            30 -> {
                line(player, "Sir Amik Varze asked me to investigate the Black Knights'", line++, true)
                line(player, "Fortress. I sneaked inside disguised as a Guard.", line++, true)
                line(player, "I eavesdropped on a Witch and the Black Knight Captain", line++, true)
                line(player, "and discovered that their invincibility potion could be", line++, true)
                line(player, "destroyed with a normal cabbage.", line++, true)
                line(player, "Now that I have sabotaged the witch's potion, I can claim", line++, true)
                line(player, "my !!reward?? from !!Sir Amik Varze?? in !!Falador Castle??.", line++, true)
            }

            100 -> {
                line(player, "Sir Amik Varze asked me to investigate the Black Knights'", line++, true)
                line(player, "Fortress. I sneaked inside disguised as a Guard.", line++, true)
                line(player, "I eavesdropped on a Witch and the Black Knight Captain", line++, true)
                line(player, "and discovered that their invincibility potion could be", line++, true)
                line(player, "destroyed with a normal cabbage.", line++, true)
                line(player, "I found a cabbage, and used it to a destroy the potion, then", line++, true)
                line(player, "claimed my reward for a job well done.", line++, true)
                line++
                line(player, "<col=FF0000>QUEST COMPLETE!</col>", line++, false)
                line++
                line(player, "!!Reward:??", line++, false)
                line(player, "3 Quest Points", line++, false)
                line(player, "2500gp", line, false)
            }
        }
    }

    override fun finish(player: Player) {
        super.finish(player)
        var line = 10
        drawReward(player, "3 Quests Points", line++)
        drawReward(player, "2500 Coins", line)
        sendItemZoomOnInterface(player, Components.QUEST_COMPLETE_SCROLL_277, 5, Items.BROKEN_CAULDRON_9591, 240)
    }

    override fun newInstance(`object`: Any?): Quest {
        definePlugins(BlackKnightsFortressPlugin(), SirAmikVarzeDialogue())
        return this
    }

}