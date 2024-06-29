package content.global.skill.production.smithing.item

import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

class DragonfireShieldPulse(player: Player?, val item: Item) : SkillPulse<Item>(player, null) {

    private val DRACONIC_VISAGE = Item(Items.DRACONIC_VISAGE_11286)
    private val ANTI_DRAGON_SHIELD = Item(Items.ANTI_DRAGON_SHIELD_1540)
    private val DRAGONFIRE_SHIELD = Item(Items.DRAGONFIRE_SHIELD_11284)
    private val ANIMATION = Animations.HUMAN_ANVIL_HAMMER_SMITHING_898
    private var tick = 0

    override fun checkRequirements(): Boolean {
        if (!inInventory(player, ANTI_DRAGON_SHIELD.id)) {
            sendDialogue(player, "You need to have an anti-dragon-shield to attach the visage onto.")
            return false
        }
        if (!inInventory(player, DRACONIC_VISAGE.id)) {
            sendDialogue(player, "You need to have a draconic visage so it can be attached on a shield.")
            return false
        }
        return true
    }

    override fun start() {
        super.start()
    }


    override fun animate() {

    }

    override fun reward(): Boolean {
        when (tick++) {
            0 -> {
                animate(player, ANIMATION)
                tick++
            }

            5 -> {
                animate(player, ANIMATION)
                tick++
            }

            9 -> {
                sendPlainDialogue(player, false, "Even for an experienced armourer it is not an easy task, but", "eventually it is ready. You have crafted the", "draconic visage and anti-dragonbreath shield into a", "dragonfire shield.")
                addDialogueAction(player) { player, button ->
                    if (button >= 2) {
                        ANTI_DRAGON_SHIELD
                        if (removeItem(player, ANTI_DRAGON_SHIELD) && removeItem(player, DRACONIC_VISAGE)) {
                            addItem(player, DRAGONFIRE_SHIELD.id)
                            rewardXP(player, Skills.SMITHING, 2000.0)
                        }
                    }
                    return@addDialogueAction
                }
            }
        }
        return false
    }

    override fun message(type: Int) {}

}
