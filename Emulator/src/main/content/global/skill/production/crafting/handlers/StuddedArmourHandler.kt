package content.global.skill.production.crafting.handlers

import core.api.*
import cfg.consts.Animations
import cfg.consts.Items
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Studded armour handler.
 */
@Initializable
class StuddedArmourHandler : UseWithHandler(Items.STEEL_STUDS_2370) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        for (armour in StuddedArmour.values()) {
            addHandler(armour.item.id, ITEM_TYPE, this)
        }
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        val armour = StuddedArmour.forItem(event.baseItem)
        val handler: SkillDialogueHandler =
            object : SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, armour!!.studded) {
                override fun create(amount: Int, index: Int) {
                    player.pulseManager.run(StudArmourPulse(player, event.baseItem, armour, amount))
                }

                override fun getAll(index: Int): Int {
                    return amountInInventory(player, STEEL_STUDS.id)
                }
            }
        if (amountInInventory(player, armour.item.id) == 1) {
            handler.create(1, 0)
        } else {
            handler.open()
        }
        return true
    }

    /**
     * Represents a type of studded armour in the game.
     *
     * @param item The base item representing the armour.
     * @param studded The item representing the studded version of the armour.
     * @param level The required level to equip this armour.
     * @param experience The experience gained from using this armour.
     * @constructor Creates an instance of StuddedArmour with specified parameters.
     */
    enum class StuddedArmour(val item: Item, val studded: Item, val level: Int, val experience: Double) {
        CHAPS(
            item = Item(Items.LEATHER_CHAPS_1095),
            studded = Item(Items.STUDDED_CHAPS_1097),
            level = 44,
            experience = 42.0
        ),
        BODY(
            item = Item(Items.LEATHER_BODY_1129),
            studded = Item(Items.STUDDED_BODY_1133),
            level = 41,
            experience = 40.0
        );

        companion object {
            fun forItem(item: Item): StuddedArmour? {
                for (armour in values()) {
                    if (armour.item.id == item.id) {
                        return armour
                    }
                }
                return null
            }
        }
    }

    /**
     * Represents a pulse for studded armor in the game.
     */
    class StudArmourPulse(player: Player?, node: Item?, private val armour: StuddedArmour?, private var amount: Int) : SkillPulse<Item?>(player, node) {

        private var ticks = 0

        override fun checkRequirements(): Boolean {
            if (getStatLevel(player, Skills.CRAFTING) < armour!!.level) {
                sendMessage(player, "You need a Crafting level of at least " + armour.level + " to do this.")
                return false
            }
            if (!inInventory(player, STEEL_STUDS.id)) {
                sendMessage(player, "You need studs in order to make studded armour.")
                return false
            }
            return inInventory(player, armour.item.id)
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
            if (player.inventory.remove(armour!!.item, STEEL_STUDS)) {
                player.inventory.add(armour.studded)
                rewardXP(player, Skills.CRAFTING, armour.experience)
                sendMessage(player, "You make a " + armour.studded.name.lowercase() + ".")
            }
            amount--
            return amount < 1
        }

        override fun message(type: Int) {
            when (type) {
                0 -> sendMessage(player, "You use the studs with the " + node!!.name.lowercase() + ".")
            }
        }

        companion object {
            private val ANIMATION = Animation.create(Animations.CRAFT_LEATHER_1249)
        }
    }

    companion object {
        private val STEEL_STUDS = Item(Items.STEEL_STUDS_2370)
    }
}
