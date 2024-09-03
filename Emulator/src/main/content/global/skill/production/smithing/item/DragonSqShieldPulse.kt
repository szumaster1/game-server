package content.global.skill.production.smithing.item

import core.api.*
import cfg.consts.Animations
import cfg.consts.Items
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Represents the creation of dragon square shield.
 *
 * @param item Represents the item associated with the Dragon Square Shield.
 * @param player The player who is using the Dragon Square Shield.
 */
class DragonSqShieldPulse(player: Player?, val item: Item) : SkillPulse<Item>(player, null) {

    private val SHIELD_LEFT_HALF = Items.SHIELD_LEFT_HALF_2366
    private val SHIELD_RIGHT_HALF = Items.SHIELD_RIGHT_HALF_2368
    private val DRAGON_SQ_SHIELD = Item(Items.DRAGON_SQ_SHIELD_1187)
    private val ANIMATION = Animations.HUMAN_ANVIL_HAMMER_SMITHING_898
    private var tick = 0

    override fun checkRequirements(): Boolean {
        if (!inInventory(player, Items.SHIELD_LEFT_HALF_2366)) {
            sendDialogue(player, "You need to have a shield left half to attach the other half to.")
            return false
        }
        if (!inInventory(player, Items.SHIELD_RIGHT_HALF_2368)) {
            sendDialogue(player, "You need to have a shield right half to attach the other half to.")
            return false
        }
        if (!inInventory(player, Items.SHIELD_LEFT_HALF_2366) && !inInventory(player, Items.SHIELD_RIGHT_HALF_2368)) {
            sendDialogue(player, "You need to have a left and right shield half to attach to.")
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
                sendPlainDialogue(player, false, "Even for an experienced armourer it is not an easy task, but", "eventually it is ready. You have restored the dragon square shield to", "its former glory.")
                addDialogueAction(player) { player, button ->
                    if (button >= 2) {
                        if (removeItem(player, SHIELD_LEFT_HALF) && removeItem(player, SHIELD_RIGHT_HALF)) {
                            addItem(player, DRAGON_SQ_SHIELD.id)
                            rewardXP(player, Skills.SMITHING, 75.0)
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
