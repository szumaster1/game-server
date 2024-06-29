package content.global.skill.production.herblore.item

import content.global.skill.production.herblore.data.Tar
import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation
import java.util.*

class HerbTarPulse(player: Player?, node: Item?, val tar: Tar, private var amount: Int) :
    SkillPulse<Item?>(player, node) {

    override fun checkRequirements(): Boolean {
        if (!isQuestComplete(player, "Druidic Ritual")) {
            sendMessage(player, "You must complete the Druidic Ritual quest before you can use Herblore.")
            return false
        }
        if (getDynLevel(player, Skills.HERBLORE) < tar.level) {
            sendMessage(player, "You need a Herblore level of at least " + tar.level + " in order to do this.")
            return false
        }
        if (!inInventory(player, PESTLE_AND_MORTAR)) {
            sendMessage(player, "You need Pestle and Mortar in order to crush the herb.")
            return false
        }
        if (!player.inventory.containsItem(SWAMP_TAR)) {
            sendMessage(player, "You need at least 15 swamp tar in order to do this.")
            return false
        }
        return true
    }

    override fun animate() {
        animate(player, ANIMATION)
    }

    override fun reward(): Boolean {
        if (delay == 1) {
            delay = 4
            return false
        }
        if (player.inventory.containsItem(SWAMP_TAR) && player.inventory.containsItem(tar.ingredient) && player.inventory.remove(SWAMP_TAR) && player.inventory.remove(tar.ingredient)) {
            addItem(player, tar.tar.id, 15)
            rewardXP(player, Skills.HERBLORE, tar.experience)
            sendMessage(player, "You add the " + tar.ingredient.name.lowercase(Locale.getDefault()).replace("clean", "").trim { it <= ' ' } + " to the swamp tar.")
        } else {
            return true
        }
        amount--
        return amount == 0
    }

    override fun message(type: Int) {}

    companion object {
        private val ANIMATION = Animation(Animations.PESTLE_MORTAR_364)
        private val PESTLE_AND_MORTAR = Items.PESTLE_AND_MORTAR_233
        private val SWAMP_TAR = Item(Items.SWAMP_TAR_1939, 15)
    }
}
