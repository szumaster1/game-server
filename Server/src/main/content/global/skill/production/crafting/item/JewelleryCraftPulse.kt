package content.global.skill.production.crafting.item

import content.global.skill.production.crafting.data.JewelleryData
import core.api.animate
import core.api.consts.Animations
import core.api.consts.Sounds
import core.api.getStatLevel
import core.api.playAudio
import core.api.rewardXP
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation

class JewelleryCraftPulse(
    player: Player?,
    node: Item?,
    val type: JewelleryData.JewelleryItem,
    var amount: Int
) : SkillPulse<Item?>(player, node) {

    companion object {
        private val ANIMATION = Animation(Animations.HUMAN_FURNACE_SMELTING_3243)
    }

    var ticks = 0
    override fun checkRequirements(): Boolean {
        return getStatLevel(player, Skills.CRAFTING) >= type.level
    }

    override fun animate() {
        if (ticks % 5 == 0) {
            animate(player, ANIMATION)
            playAudio(player, Sounds.FURNACE_2725)
        }
    }

    override fun reward(): Boolean {
        if (++ticks % 5 != 0) {
            return false
        }
        if (player.inventory.remove(*items)) {
            val item = Item(type.sendItem)
            player.inventory.add(item)
            rewardXP(player, Skills.CRAFTING, type.experience)
        }
        amount--
        return amount < 1
    }

    private val items: Array<Item?>
        get() {
            val items = arrayOfNulls<Item>(type.items.size)
            for ((index, i) in type.items.indices.withIndex()) {
                items[index] = Item(type.items[i], 1)
            }
            return items
        }

}
