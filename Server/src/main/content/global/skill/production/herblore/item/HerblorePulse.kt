package content.global.skill.production.herblore.item

import content.data.consumables.Consumables.Companion.getConsumableById
import content.global.skill.production.herblore.data.potion.GenericPotion
import content.global.skill.skillcape.SkillcapePerks
import content.global.skill.skillcape.SkillcapePerks.Companion.isActive
import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.api.consts.Sounds
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation
import core.utilities.RandomFunction
import core.utilities.StringUtils
import java.util.*

class HerblorePulse(player: Player?, node: Item?, var amount: Int, potion: GenericPotion) :
    SkillPulse<Item?>(player, node) {

    private val potion: GenericPotion
    private val initialAmount: Int
    private var cycles = 0

    init {
        initialAmount = amount
        this.potion = potion
    }

    override fun checkRequirements(): Boolean {
        if (!isQuestComplete(player, "Druidic Ritual")) {
            sendMessage(player, "You must complete the Druidic Ritual quest before you can use Herblore.")
            return false
        }
        if (getDynLevel(player, Skills.HERBLORE) < potion.level) {
            sendMessage(player, "You need a Herblore level of at least " + potion.level + " in order to do this.")
            return false
        }
        return player.inventory.containsItem(potion.base) && player.inventory.containsItem(potion.ingredient)
    }

    override fun animate() {}
    override fun reward(): Boolean {
        if (potion.base!!.id == VIAL_OF_WATER.id) {
            if (initialAmount == 1 && delay == 1) {
                animate(player, ANIMATION)
                delay = 3
                return false
            }
            handleUnfinished()
        } else {
            if (initialAmount == 1 && delay == 1) {
                animate(player, ANIMATION)
                delay = 3
                return false
            }
            if (delay == 1) {
                delay = 3
                animate(player, ANIMATION)
                return false
            }
            handleFinished()
        }
        amount--
        return amount == 0
    }

    fun handleUnfinished() {
        if (cycles == 0) {
            animate(player, ANIMATION)
        }
        if (player.inventory.containsItem(potion.base) && player.inventory.containsItem(potion.ingredient) && player.inventory.remove(potion.base, potion.ingredient)) {
            val item = potion.product
            player.inventory.add(item)
            sendMessage(player, "You put the" + StringUtils.formatDisplayName(potion.ingredient!!.name.lowercase().replace("clean", "")) + " leaf into the vial of water.")
            playAudio(player, Sounds.GRIND_2608)
            if (cycles++ == 3) {
                animate(player, ANIMATION)
                cycles = 0
            }
        }
    }

    fun handleFinished() {
        if (player.inventory.containsItem(potion.base) && player.inventory.containsItem(potion.ingredient) && player.inventory.remove(potion.base, potion.ingredient)) {
            var item = potion.product
            if (isActive(SkillcapePerks.BREWMASTER, player)) {
                if (RandomFunction.random(100) < 15) {
                    val consume = getConsumableById(item!!.id)
                    if (consume != null) item = Item(consume.consumable.ids[0], item.amount)
                    sendMessage(player, "Due to your expertise, you manage to make an extra dose.")
                }
            }
            player.inventory.add(item)
            rewardXP(player, Skills.HERBLORE, potion.experience)
            sendMessage(player, "You mix the " + potion.ingredient!!.name.lowercase(Locale.getDefault()) + " into your potion.")
            playAudio(player, Sounds.GRIND_2608)
            player.animate(ANIMATION)
        }
    }

    companion object {
        val VIAL_OF_WATER = Item(Items.VIAL_OF_WATER_227)
        val COCONUT_MILK = Item(Items.COCONUT_MILK_5935)
        private val ANIMATION = Animation(Animations.MIX_POTION_363)
    }
}
