package content.global.skill.production.crafting.handlers

import core.api.*
import core.api.consts.Items
import core.cache.def.impl.SceneryDefinition
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin
import core.utilities.StringUtils

@Initializable
class WeaveOptionHandler : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        SceneryDefinition.setOptionHandler("weave", this)
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        object : SkillDialogueHandler(player, SkillDialogue.THREE_OPTION, WeavingItem.SACK.product, WeavingItem.BASKET.product, WeavingItem.CLOTH.product) {
            override fun create(amount: Int, index: Int) {
                player.pulseManager.run(WeavePulse(player, node as Scenery, WeavingItem.values()[index], amount))
            }
        }.open()
        return true
    }

    class WeavePulse(player: Player?, node: Scenery?, private val type: WeavingItem, private var amount: Int) : SkillPulse<Scenery?>(player, node) {

        private var ticks = 0
        override fun checkRequirements(): Boolean {
            if (getStatLevel(player, Skills.CRAFTING) < type.level) {
                sendMessage(player, "You need a Crafting level of at least " + type.level + " in order to do this.")
                return false
            }
            if (!player.inventory.containsItem(type.required)) {
                sendMessage(player, "You need " + type.required.amount + " " + type.required.name.lowercase().replace("ball", "balls") + (if (type == WeavingItem.SACK) "s" else if (type == WeavingItem.CLOTH) "" else "es") + " to weave " + (if (StringUtils.isPlusN(type.product.name.lowercase())) "an" else "a") + " " + type.product.name.lowercase() + ".")
                return false
            }
            return true
        }

        override fun animate() {
            if (ticks % 5 == 0) {
                player.animate(ANIMATION)
            }
        }

        override fun reward(): Boolean {
            if (++ticks % 5 != 0) {
                return false
            }
            if (removeItem(player, type.required)) {
                player.inventory.add(type.product)
                rewardXP(player, Skills.CRAFTING, type.experience)
                sendMessage(
                    player,
                    "You weave the "
                        + type.required.name.lowercase().replace("ball", "balls")
                        + (if (type == WeavingItem.SACK) "s" else if (type == WeavingItem.CLOTH) "" else "es")
                        + " into " + (if (StringUtils.isPlusN(type.product.name.lowercase())) "an" else "a")
                        + " " + type.product.name.lowercase() + "."
                )
                if (type == WeavingItem.BASKET && node!!.id == 8717 && withinDistance(player, Location(3039, 3287, 0)) && !hasDiaryTaskComplete(player, DiaryType.FALADOR, 1, 0)) {
                    finishDiaryTask(player, DiaryType.FALADOR, 1, 0)
                }
            }
            amount--
            return amount < 1
        }

        companion object {
            private val ANIMATION = Animation(2270)
        }
    }

    enum class WeavingItem(val product: Item, val required: Item, val level: Int, val experience: Double) {
        SACK(Item(Items.EMPTY_SACK_5418), Item(Items.JUTE_FIBRE_5931, 4), 21, 38.0),
        BASKET(Item(Items.BASKET_5376), Item(Items.WILLOW_BRANCH_5933, 6), 36, 56.0),
        CLOTH(Item(Items.STRIP_OF_CLOTH_3224), Item(Items.BALL_OF_WOOL_1759, 4), 10, 12.0)

    }
}
