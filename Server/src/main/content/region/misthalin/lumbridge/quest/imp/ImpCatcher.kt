package content.region.misthalin.lumbridge.quest.imp

import core.api.addItemOrDrop
import cfg.consts.Components
import cfg.consts.Items
import cfg.consts.Vars
import core.api.inInventory
import core.api.rewardXP
import core.api.sendItemOnInterface
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.plugin.Initializable

/**
 * Represents the Imp catcher quest.
 */
@Initializable
class ImpCatcher : Quest("Imp Catcher", 21, 20, 1, Vars.VARP_QUEST_IMP_CATCHER_PROGRESS, 0, 1, 2) {

    /*
     * Quest description:
     * The Wizard Grayzag has summoned hundreds of little imps.
     * They have stolen a lot of things belonging to the Wizard Mizgog, including his magic beads.
     */

    override fun drawJournal(player: Player, stage: Int) {
        super.drawJournal(player, stage)
        var line = 11
        player ?: return
        if (getStage(player) == 0) {
            line(player, "I can start this quest by speaking to !!Wizard Mizgog?? who is", line++)
            line(player, "in the !!Wizard's Tower??", line++)
            line(player, "There are no requirements for this quest.", line++)
        } else if (getStage(player) == 10) {
            line(player, "<str>I have spoken to Wizard Mizgog.", line++)
            line(player, "I need to collect some items by killing !!Imps??.", line++)
            if (inInventory(player, Items.BLACK_BEAD_1474) && inInventory(player, Items.RED_BEAD_1470) && inInventory(player, Items.WHITE_BEAD_1476) && inInventory(player, Items.YELLOW_BEAD_1472)) {
                line(player, "I have collected all the missing beads and need to return", line++)
                line(player, "them to !!Wizard Mizgog??.", line)
                return
            }
            line++
            if (!inInventory(player, Items.BLACK_BEAD_1474)) {
                line(player, "!!1 Black Bead??", line++)
            } else {
                line(player, "<str>1 Black Bead", line++)
            }
            if (!inInventory(player, Items.RED_BEAD_1470)) {
                line(player, "!!1 Red Bead??", line++)
            } else {
                line(player, "<str>1 Red Bead", line++)
            }
            if (!inInventory(player, Items.WHITE_BEAD_1476)) {
                line(player, "!!1 White Bead??", line++)
            } else {
                line(player, "<str>1 White Bead", line++)
            }
            if (!inInventory(player, Items.YELLOW_BEAD_1472)) {
                line(player, "!!1 Yellow Bead??", line)
            } else {
                line(player, "<str>1 Yellow Bead", line)
            }
        } else {
            line++
            line(player, "<str>I have spoken to Wizard Mizgog.", line++)
            line(player, "<str>I have collected all the beads.", line++)
            line(player, "<str>Wizard Mizgog thanked me for finding his beads and gave", line++)
            line(player, "<str>me and Amulet of Accuracy.", line++ + 1)
            line(player, "<col=FF0000>QUEST COMPLETE!</col>", line)
        }
    }

    override fun finish(player: Player) {
        super.finish(player)
        player ?: return
        var ln = 10
        sendItemOnInterface(player, Components.QUEST_COMPLETE_SCROLL_277, 5, Items.AMULET_OF_ACCURACY_1478)

        drawReward(player, "1 Quest Point", ln++)
        drawReward(player, "875 Magic XP", ln++)
        drawReward(player, "Amulet of Accuracy", ln)

        rewardXP(player, Skills.MAGIC, 875.0)

        if (!player.inventory.add(Item(Items.AMULET_OF_ACCURACY_1478))) {
            addItemOrDrop(player, Items.AMULET_OF_ACCURACY_1478)
        }
    }

    override fun newInstance(`object`: Any?): Quest {
        return this
    }

}