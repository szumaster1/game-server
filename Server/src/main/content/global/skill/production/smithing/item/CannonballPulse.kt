package content.global.skill.production.smithing.item

import content.region.kandarin.quest.dwarfcannon.DwarfCannon
import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.api.consts.Sounds
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Cannonball pulse
 *
 * @property item Represents the item associated with the cannonball pulse.
 * @property amount Indicates the quantity of the item.
 * @constructor Represents a new instance of CannonballPulse.
 *
 * @param player The player associated with this cannonball pulse.
 */
class CannonballPulse(player: Player?, val item: Item?, private var amount: Int) :
    SkillPulse<Item?>(player, null) {

    private var tick = 0
    override fun checkRequirements(): Boolean {
        if (!isQuestComplete(player, DwarfCannon.NAME)) {
            sendDialogue(player, "You need to complete the ${DwarfCannon.NAME} quest in order to do this.")
            return false
        }
        if (getDynLevel(player, Skills.SMITHING) < 35) {
            sendDialogue(player, "You need a Smithing level of at least 35 in order to do this.")
            return false
        }
        if (!inInventory(player, Items.AMMO_MOULD_4)) {
            sendDialogue(player, "You need an ammo mould in order to make a cannon ball.")
            return false
        }
        return true
    }

    override fun animate() {
    }

    override fun reward(): Boolean {
        when (tick++) {
            0 -> {
                playAudio(player, Sounds.FURNACE_2725)
                sendMessage(player, "You heat the steel bar into a liquid state.")
                animate(player, Animations.USE_FURNACE_3243)
            }

            3 -> {
                sendMessage(player, "You pour the molten metal into your cannonball mould.")
                animate(player, Animations.HUMAN_BURYING_BONES_827)
            }

            4 -> {
                sendMessage(player, "The molten metal cools slowly to form 4 cannonballs.")
            }

            7 -> {
                if (removeItem(player, item)) {
                    addItem(player, Items.CANNONBALL_2, 4)
                    rewardXP(player, Skills.SMITHING, 25.6)
                }
                animate(player, Animations.HUMAN_BURYING_BONES_827)
            }

            10 -> {
                if (--amount == 0 || !inInventory(player, Items.STEEL_BAR_2353)) {
                    return true
                }
                tick = 0
            }
        }
        return false
    }
}
