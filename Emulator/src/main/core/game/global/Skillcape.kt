package core.game.global

import org.rs.consts.Items
import core.api.closeDialogue
import core.api.freeSlots
import core.api.removeItem
import core.api.sendDialogue
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Represents a utility class for purchasing skillcapes.
 * @author Vexia
 */
object Skillcape {

    val skillcapeIDs = intArrayOf(
        Items.ATTACK_CAPE_9747,
        Items.DEFENCE_CAPE_9753,
        Items.STRENGTH_CAPE_9750,
        Items.HITPOINTS_CAPE_9768,
        Items.RANGING_CAPE_9756,
        Items.PRAYER_CAPE_9759,
        Items.MAGIC_CAPE_9762,
        Items.COOKING_CAPE_9801,
        Items.WOODCUTTING_CAPE_9807,
        Items.FLETCHING_CAPE_9783,
        Items.FISHING_CAPE_9798,
        Items.FIREMAKING_CAPE_9804,
        Items.CRAFTING_CAPE_9780,
        Items.SMITHING_CAPE_9795,
        Items.MINING_CAPE_9792,
        Items.HERBLORE_CAPE_9774,
        Items.AGILITY_CAPE_9771,
        Items.THIEVING_CAPE_9777,
        Items.SLAYER_CAPE_9786,
        Items.FARMING_CAPE_9810,
        Items.RUNECRAFT_CAPE_9765,
        Items.HUNTER_CAPE_9948,
        Items.CONSTRUCT_CAPE_9789,
        Items.SUMMONING_CAPE_12169
    )

    /**
     * Method used to purchase a cape of accomplishment.
     *
     * @param [player] the player.
     * @param [skill] the skill.
     * @return `true` if purchased.
     */
    @JvmStatic
    fun purchase(player: Player, skill: Int): Boolean {
        if (!isMaster(player, skill)) {
            return false
        }
        if (freeSlots(player) < 2) {
            closeDialogue(player)
            sendDialogue(player, "You don't have enough space in your inventory.")
            return false
        }
        if (!removeItem(player, Item(Items.COINS_995, 99000))) {
            closeDialogue(player)
            sendDialogue(player, "You don't have enough coins.")
            return false
        } else return player.inventory.add(*getItems(player, skill))
    }

    /**
     * Method used to trim the players skillcapes.
     *
     * @param [player] the player.
     */
    @JvmStatic
    fun trim(player: Player) {
        val containers = arrayOf(player.inventory, player.equipment, player.bank)
        var skill = -1
        for (container in containers) {
            for (item in container.toArray()) {
                if (item == null || item.id < 9700) {
                    continue
                }
                skill = getCapeIndex(item)
                if (skill != -1) {
                    container.replace(Item(getTrimmed(skill).id, item.amount), item.slot)
                    skill = -1
                }
            }
        }
    }

    /**
     * Checks if the player has the appropriate level.
     *
     * @param [player] the player.
     * @param [skill] the skill.
     * @return `true` if so.
     */
    @JvmStatic
    fun isMaster(player: Player, skill: Int): Boolean {
        return player.getSkills().getStaticLevel(skill) == 99
    }

    /**
     * Gets the items to purchase.
     *
     * @param [player] the player.
     * @param [skill] the skill.
     * @return **Items** to buy.
     */
    fun getItems(player: Player, skill: Int): Array<Item> {
        return arrayOf(
            Item(skillcapeIDs[skill] + if (player.getSkills().getMasteredSkills() > 1) 1 else 0),
            Item(skillcapeIDs[skill] + 2)
        )
    }

    /**
     * Gets the trimmed item.
     *
     * @param [skill] the skill.
     * @return the trimmed cape.
     */
    private fun getTrimmed(skill: Int): Item {
        return Item(skillcapeIDs[skill] + 1)
    }

    /**
     * Gets the cape index by the item.
     *
     * @param [item] the item.
     * @return the skill index, if not `(-1)`.
     */
    private fun getCapeIndex(item: Item): Int {
        for (i in skillcapeIDs.indices) {
            if (skillcapeIDs[i] == item.id) {
                return i
            }
        }
        return -1
    }

}