package content.global.skill.fletching.items.bow

import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.zone.ZoneBorders

/**
 * Represents the skill pulse of stringing.
 */
open class StringPulse(player: Player, node: Item, private val bow: BowString, private var amount: Int) : SkillPulse<Item>(player, node) {

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.FLETCHING) < bow.level) {
            sendDialogue(player, "You need a fletching level of ${bow.level} to string this bow.")
            return false
        }
        if (!inInventory(player, bow.unfinished)) {
            return false
        }
        if (!inInventory(player, bow.string)) {
            sendDialogue(player, "You seem to have run out of bow strings.")
            return false
        }
        return true
    }

    override fun animate() {
        animate(player!!, bow.animation)
    }

    override fun reward(): Boolean {
        if (delay == 1) {
            delay = 2
        }
        if (player.inventory.remove(Item(bow.unfinished), Item(bow.string))) {
            addItem(player, bow.product)
            rewardXP(player, Skills.FLETCHING, bow.experience)
            sendMessage(player, "You add a string to the bow.")

            if (bow == BowString.MAGIC_SHORTBOW && (ZoneBorders(2721, 3489, 2724, 3493, 0).insideBorder(player) || ZoneBorders(2727, 3487, 2730, 3490, 0).insideBorder(player)) && getAttribute(player, "diary:seers:fletch-magic-short-bow", false)) {
                finishDiaryTask(player, DiaryType.SEERS_VILLAGE, 2, 2)
            }
        }
        if (!inInventory(player, bow.string) || !inInventory(player, bow.unfinished)) {
            return true
        }
        amount--
        return amount == 0
    }

    override fun message(type: Int) {
    }
}


