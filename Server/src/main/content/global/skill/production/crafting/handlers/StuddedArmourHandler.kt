package content.global.skill.production.crafting.handlers

import core.api.consts.Items
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
                    return player.inventory.getAmount(STEEL_STUDS)
                }
            }
        if (player.inventory.getAmount(armour.item) == 1) {
            handler.create(1, 0)
        } else {
            handler.open()
        }
        return true
    }

    enum class StuddedArmour(val item: Item, val studded: Item, val level: Int, val experience: Double) {
        CHAPS(Item(1095), Item(1097), 44, 42.0),
        BODY(Item(1129), Item(1133), 41, 40.0);

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

    class StudArmourPulse(player: Player?, node: Item?, private val armour: StuddedArmour?, private var amount: Int) :
        SkillPulse<Item?>(player, node) {
        private var ticks = 0
        override fun checkRequirements(): Boolean {
            if (player.getSkills().getLevel(Skills.CRAFTING) < armour!!.level) {
                player.packetDispatch.sendMessage("You need a Crafting level of at least " + armour.level + " to do this.")
                return false
            }
            if (!player.inventory.containsItem(STEEL_STUDS)) {
                player.packetDispatch.sendMessage("You need studs in order to make studded armour.")
                return false
            }
            return player.inventory.containsItem(armour.item)
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
            if (player.inventory.remove(armour!!.item, STEEL_STUDS)) {
                player.inventory.add(armour.studded)
                player.getSkills().addExperience(Skills.CRAFTING, armour.experience, true)
                player.packetDispatch.sendMessage("You make a " + armour.studded.name.lowercase() + ".")
            }
            amount--
            return amount < 1
        }

        override fun message(type: Int) {
            when (type) {
                0 -> player.packetDispatch.sendMessage("You use the studs with the " + node!!.name.lowercase() + ".")
            }
        }

        companion object {
            private val ANIMATION = Animation.create(1249)
        }
    }

    companion object {
        private val STEEL_STUDS = Item(2370)
    }
}
