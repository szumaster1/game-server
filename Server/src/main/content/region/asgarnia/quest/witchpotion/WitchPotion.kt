package content.region.asgarnia.quest.witchpotion

import core.api.closeChatBox
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.Vars
import core.api.rewardXP
import core.api.sendItemZoomOnInterface
import core.api.setInterfaceText
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.entity.skill.Skills
import core.plugin.Initializable

@Initializable
class WitchPotion : Quest("Witch's Potion", 31, 30, 1, Vars.VARP_QUEST_WITCHS_POTION_PROGRESS, 0, 1, 3) {

    override fun drawJournal(player: Player, stage: Int) {
        super.drawJournal(player, stage)
        var line = 11

        when (getStage(player)) {
            0 -> {
                line(player, BLUE + "I can start this quest by speaking to " + RED + "Hetty " + BLUE + "in her house in", line++)
                line(player, RED + "Rimmington" + BLUE + ", West of " + RED + "Port Sarim", line)
                line++
            }

            20 -> {
                line(player, "<str>I spoke to Hetty in her house at Rimmington. hetty told me", line++)
                line(player, "<str>she could increase my magic power if I can bring her", line++)
                line(player, "<str>certain ingredients for a potion.", line++)
                line(player, BLUE + "Hetty needs me to bring her the following:", line++)
                if (player.inventory.contains(1957, 1)) {
                    line(player, "<str>I have an onion with me", line++)
                } else {
                    line(player, RED + "An onion", line++)
                }
                if (player.inventory.contains(1957, 1)) {
                    line(player, "<str>I have an onion with me", line++)
                } else {
                    line(player, RED + "An onion", line++)
                }
                if (player.inventory.contains(300, 1)) {
                    line(player, "<str>I have a rat's tail with me", line++)
                } else {
                    line(player, RED + "A rat's tail", line++)
                }
                if (player.inventory.contains(2146, 1)) {
                    line(player, "<str>I have a piece of burnt meat with me", line++)
                } else {
                    line(player, RED + "A piece of burnt meat", line++)
                }
                if (player.inventory.contains(221, 1)) {
                    line(player, "<str>I have an eye of newt with me", line++)
                } else {
                    line(player, RED + "An eye of newt", line++)
                }
            }

            40 -> {
                line(player, "<str>I brought her an onion, a rat's tail, a piece of burnt meat", line++)
                line(player, "<str>and eye of newt which she used to make a potion.", line++)
                line(player, BLUE + "I should drink from the " + RED + "cauldron" + BLUE + " and improve my magic!", line++)
            }

            100 -> {
                line(player, "<str>I brought her an onion, a rat's tail, a piece of burnt meat", line++)
                line(player, "<str>and an eye of newt which she used to make a potion.", line++)
                line(player, "<str>I drank from the cauldron and my magic power increased!", line++)
                line(player, "<col=FF0000>QUEST COMPLETE!", line++)
            }
        }
    }

    override fun finish(player: Player) {
        super.finish(player)
        var ln = 10
        sendItemZoomOnInterface(player, Components.QUEST_COMPLETE_SCROLL_277, 5, Items.EYE_OF_NEWT_221, 240)
        setInterfaceText(player, "1 Quest Point", 277, ln++)
        setInterfaceText(player,"325 Magic XP", 277, ln)
        rewardXP(player, Skills.MAGIC, 325.0)
        closeChatBox(player)
    }

    override fun newInstance(`object`: Any?): Quest {
        return this
    }
}