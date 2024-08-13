package content.global.skill.production.crafting.handlers

import core.api.*
import core.api.consts.Animations
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
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.StringUtils

/**
 * Weave option handler.
 */
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

    /**
     * Weave pulse
     *
     * @param type The type of weaving item associated with this pulse.
     * @param amount The quantity of the weaving item.
        *
     * @param player The player who is executing the weaving action.
     * @param node The scenery node where the weaving action takes place.
     */
    class WeavePulse(player: Player?, node: Scenery?, private val type: WeavingItem, private var amount: Int) :
        SkillPulse<Scenery?>(player, node) {

        private var ticks = 0
        override fun checkRequirements(): Boolean {
            if (getStatLevel(player, Skills.CRAFTING) < type.level) {
                sendMessage(player, "You need a Crafting level of at least " + type.level + " in order to do this.")
                return false
            }
            if (!inInventory(player, type.required.id)) {
                sendMessage(player, "You need " + type.required.amount + " " + type.required.name.lowercase().replace("ball", "balls") + (if (type == WeavingItem.SACK) "s" else if (type == WeavingItem.CLOTH) "" else "es") + " to weave " + (if (StringUtils.isPlusN(type.product.name.lowercase())) "an" else "a") + " " + type.product.name.lowercase() + ".")
                return false
            }
            return true
        }

        override fun animate() {
            if (ticks % 5 == 0) {
                animate(player, ANIMATION)
            }
        }

        override fun reward(): Boolean {
            if (++ticks % 5 != 0) {
                return false
            }
            if (removeItem(player, type.required)) {
                addItem(player, type.product.id)
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
            private const val ANIMATION = Animations.PULLING_ROPE_2270
        }
    }

    /**
     * Weaving item
     *
     * @param product The item that is produced through weaving.
     * @param required The item that is needed to create the weaving product.
     * @param level The skill level required to weave the item.
     * @param experience The amount of experience gained from weaving the item.
     * @constructor Weaving item
     */
    enum class WeavingItem(val product: Item, val required: Item, val level: Int, val experience: Double) {
        /**
         * Sack.
         */
        SACK(
            product = Item(Items.EMPTY_SACK_5418),
            required = Item(Items.JUTE_FIBRE_5931, 4),
            level = 21,
            experience = 38.0
        ),

        /**
         * Basket.
         */
        BASKET(
            product = Item(Items.BASKET_5376),
            required = Item(Items.WILLOW_BRANCH_5933, 6),
            level = 36,
            experience = 56.0
        ),

        /**
         * Cloth.
         */
        CLOTH(
            product = Item(Items.STRIP_OF_CLOTH_3224),
            required = Item(Items.BALL_OF_WOOL_1759, 4),
            level = 10,
            experience = 12.0
        )
    }
}
