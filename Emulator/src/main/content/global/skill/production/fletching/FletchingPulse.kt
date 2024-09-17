package content.global.skill.production.fletching

import org.rs.consts.Animations
import org.rs.consts.Items
import org.rs.consts.Vars
import content.global.skill.production.fletching.FletchingMap.FletchingItems
import content.region.kandarin.quest.zogre.handlers.ZUtils
import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.zone.ZoneBorders
import core.tools.RandomFunction
import core.tools.StringUtils

/**
 * Handles the fletch creation pulse.
 */
class FletchingPulse(player: Player?, node: Item?, amount: Int, fletch: FletchingItems) : SkillPulse<Item?>(player, node) {

    private val fletch: FletchingItems
    private var amount = 0

    init {
        this.amount = amount
        this.fletch = fletch
    }

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.FLETCHING) < fletch.level) {
            sendDialogue(player, "You need a fletching skill of " + fletch.level + " or above to make " + (if (StringUtils.isPlusN(getItemName(fletch.id).replace("(u)", "").trim())) "an" else "a") + " " + getItemName(fletch.id).replace("(u)", "").trim())
            return false
        }
        if (amount > player.inventory.getAmount(node)) {
            amount = player.inventory.getAmount(node)
        }
        if (fletch == FletchingItems.OGRE_ARROW_SHAFT) {
            if (!isQuestComplete(player, "Big Chompy Bird Hunting")) {
                sendMessage(player, "You must have started Big Chompy Bird Hunting to make those.")
                return false
            }
        }
        if(fletch == FletchingItems.OGRE_COMPOSITE_BOW) {
            if (!getAttribute(player!!, ZUtils.TALK_WITH_SITHIK_OGRE_DONE, false) || getVarbit(player, Vars.VARBIT_QUEST_ZORGE_FLESH_EATERS_PROGRESS) < 6) {
                sendMessage(player, "You must have started Zogre Flesh Eaters to make those.")
                return false
            }
        }
        return true
    }

    override fun animate() {
        animate(player, Animations.HUMAN_FlETCHING_LOGS_1248)
    }

    override fun reward(): Boolean {
        if (bankZone.insideBorder(player) && fletch == FletchingItems.MAGIC_SHORTBOW) {
            finishDiaryTask(player, DiaryType.SEERS_VILLAGE, 2, 2)
        }
        if (delay == 1) {
            super.setDelay(4)
            return false
        }
        if (player.inventory.remove(node)) {
            val item = Item(fletch.id, fletch.amount)
            if (fletch == FletchingItems.OGRE_ARROW_SHAFT) {
                item.amount = RandomFunction.random(3, 6)
            }
            if (fletch == FletchingItems.OGRE_COMPOSITE_BOW) {
                item.id = Items.UNSTRUNG_COMP_BOW_4825
            }
            player.inventory.add(item)
            rewardXP(player, Skills.FLETCHING, fletch.experience)
            val message = message
            sendMessage(player, message)

            if (fletch.id == FletchingItems.MAGIC_SHORTBOW.id && (ZoneBorders(2721, 3489, 2724, 3493, 0).insideBorder(player) || ZoneBorders(2727, 3487, 2730, 3490, 0).insideBorder(player)) && !player.achievementDiaryManager.hasCompletedTask(DiaryType.SEERS_VILLAGE, 2, 2)) {
                setAttribute(player, "/save:diary:seers:fletch-magic-short-bow", true)
            }
        } else {
            return true
        }
        amount--
        return amount == 0
    }

    val message: String
        get() = when (fletch) {
            FletchingItems.ARROW_SHAFT -> "You carefully cut the wood into 15 arrow shafts."
            FletchingItems.OGRE_COMPOSITE_BOW -> "You carefully cut the wood into composite ogre bow."
            else -> ("You carefully cut the wood into " + (if (StringUtils.isPlusN(getItemName(fletch.id))) "an" else "a") + " " + getItemName(fletch.id).replace("(u)", "").trim()) + "."
        }

    companion object {
        private val bankZone = ZoneBorders(2721, 3493, 2730, 3487)
    }

}
