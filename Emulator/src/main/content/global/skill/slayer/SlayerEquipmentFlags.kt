package content.global.skill.slayer

import content.global.skill.skillcape.SkillcapePerks
import core.api.EquipmentSlot
import core.api.getAttribute
import core.api.getItemFromEquipment
import core.game.node.entity.player.Player
import org.rs.consts.Items

/**
 * Slayer equipment flags.
 */
object SlayerEquipmentFlags {

    private val blackMasks = (Items.BLACK_MASK_10_8901..Items.BLACK_MASK_8921).map { it }.toIntArray()
    private val slayerItems = intArrayOf(Items.NOSE_PEG_4168, Items.EARMUFFS_4166, Items.FACE_MASK_4164, *blackMasks, Items.SPINY_HELMET_4551, Items.SLAYER_CAPET_9787, Items.SLAYER_CAPE_9786, Items.SLAYER_HELMET_13263, Items.WITCHWOOD_ICON_8923, Items.MIRROR_SHIELD_4156)

    /**
     * Updates the equipment flags for the given player based on their equipped items.
     *
     * @param player The player whose flags are to be updated.
     */
    @JvmStatic
    fun updateFlags(player: Player) {
        var flags = 0

        if (SkillcapePerks.isActive(SkillcapePerks.TRICKS_OF_THE_TRADE, player) &&
            getAttribute(player, "cape_perks:tott:helmet-stored", false)) {
            flags = 0x3F
        }

        else if (hasItem(player, Items.SLAYER_HELMET_13263)) flags = 0x1F
        else if (hasItem(player, Items.NOSE_PEG_4168)) flags = 1
        else if (hasItem(player, Items.EARMUFFS_4166)) flags = flags or (1 shl 1)
        else if (hasItem(player, Items.FACE_MASK_4164)) flags = flags or (1 shl 2)
        else if ((getItemFromEquipment(player, EquipmentSlot.HEAD)?.id ?: 0) in blackMasks) flags = flags or (1 shl 3)
        else if (hasItem(player, Items.SPINY_HELMET_4551)) flags = flags or (1 shl 4)

        if ((getItemFromEquipment(player, EquipmentSlot.NECK)?.id ?: 0) == Items.WITCHWOOD_ICON_8923) flags = flags or (1 shl 7)
        if ((getItemFromEquipment(player, EquipmentSlot.SHIELD)?.id ?: 0) == Items.MIRROR_SHIELD_4156) flags = flags or (1 shl 8)

        SlayerManager.getInstance(player).flags.equipmentFlags = flags
    }

    /**
     * Checks if the player has a **Nose Peg** equipped.
     *
     * @param player the player to check.
     * @return `true` if the player has a **Nose Peg**, otherwise `false`.
     */
    @JvmStatic
    fun hasNosePeg(player: Player): Boolean {
        return SlayerManager.getInstance(player).flags.equipmentFlags and 1 == 1
    }

    /**
     * Checks if the player has **Earmuffs** equipped.
     *
     * @param player the player to check.
     * @return `true` if the player has **Earmuffs**, otherwise `false`.
     */
    @JvmStatic
    fun hasEarmuffs(player: Player): Boolean {
        return (SlayerManager.getInstance(player).flags.equipmentFlags shr 1) and 1 == 1
    }

    /**
     * Checks if the player has a **Face Mask** equipped.
     * @param player the player to check.
     * @return `true` if the player has a **Face Mask**, otherwise `false`.
     */
    @JvmStatic
    fun hasFaceMask(player: Player): Boolean {
        return (SlayerManager.getInstance(player).flags.equipmentFlags shr 2) and 1 == 1
    }

    /**
     * Checks if the player has a **Black Mask** equipped.
     *
     * @param player the player to check.
     * @return `true` if the player has a **Black Mask**, otherwise `false`.
     */
    @JvmStatic
    fun hasBlackMask(player: Player): Boolean {
        return (SlayerManager.getInstance(player).flags.equipmentFlags shr 3) and 1 == 1
    }

    /**
     * Checks if the player has a **Spiny Helmet** equipped.
     *
     * @param player the player to check.
     * @return `true` if the player has a **Spiny Helmet**, otherwise `false`.
     */
    @JvmStatic
    fun hasSpinyHelmet(player: Player): Boolean {
        return (SlayerManager.getInstance(player).flags.equipmentFlags shr 4) and 1 == 1
    }

    /**
     * Checks if the player has a **Witchwood Icon** equipped.
     * @param player the player to check.
     * @return `true` if the player has a **Witchwood Icon**, otherwise `false`.
     */
    @JvmStatic
    fun hasWitchwoodIcon(player: Player): Boolean {
        return (SlayerManager.getInstance(player).flags.equipmentFlags shr 7) and 1 == 1
    }

    /**
     * Checks if the player has a **Mirror Shield** equipped.
     * @param player the player to check.
     * @return `true` if the player has a **Mirror Shield**, otherwise `false`.
     */
    @JvmStatic
    fun hasMirrorShield(player: Player): Boolean {
        return (SlayerManager.getInstance(player).flags.equipmentFlags shr 8) and 1 == 1
    }

    /**
     * Calculates the damage accuracy bonus for the player based on their equipment.
     *
     * @param player the player to calculate the bonus for.
     * @return the damage accuracy bonus as a Double.
     */
    @JvmStatic
    fun getDamAccBonus(player: Player): Double {
        val isCape = SlayerManager.getInstance(player).flags.equipmentFlags == 0x3F // Check if all perks are active
        val hasMask = hasBlackMask(player)
        return if (hasMask) 1.15
        else if (isCape) 1.075
        else 1.0
    }

    /**
     * Checks if the player has a specific item equipped.
     *
     * @param player the player to check.
     * @param id the item id to check for.
     * @return `true` if the player has the item, otherwise `false`.
     */
    private fun hasItem(player: Player, id: Int): Boolean {
        return (getItemFromEquipment(player, EquipmentSlot.HEAD)?.id ?: 0) == id
    }

    /**
     * Checks if the given item is a Slayer equipment item.
     *
     * @param item the item id to check.
     * @return `true` if the item is a Slayer equipment item, otherwise `false`.
     */
    fun isSlayerEq(item: Int): Boolean {
        return item in slayerItems
    }

}
