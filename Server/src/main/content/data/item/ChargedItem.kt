package content.data.item

import cfg.consts.Items
import core.api.toIntArray
import core.cache.def.impl.ItemDefinition

/**
 * Represents a distinct charged item, i.e. items with (#).
 * Not to be confused with items with internal charge, like degradation.
 * @author RiL
 */
enum class ChargedItem(val ids: IntArray) {
    /**
     * Amulet Of Glory.
     */
    AMULET_OF_GLORY(ids = (Items.AMULET_OF_GLORY4_1712 downTo Items.AMULET_OF_GLORY_1704 step 2).toIntArray()),

    /**
     * FairyRing Of Duelling.
     */
    RING_OF_DUELLING(ids = (Items.RING_OF_DUELLING8_2552..Items.RING_OF_DUELLING1_2566 step 2).toIntArray()),

    /**
     * Games Necklace.
     */
    GAMES_NECKLACE(ids = (Items.GAMES_NECKLACE8_3853..Items.GAMES_NECKLACE1_3867 step 2).toIntArray()),

    /**
     * Broodoo Shield (a).
     */
    BROODOO_SHIELDA(ids = (Items.BROODOO_SHIELD_10_6215..Items.BROODOO_SHIELD_6235 step 2).toIntArray()),

    /**
     * Broodoo Shield (b).
     */
    BROODOO_SHIELDB((Items.BROODOO_SHIELD_10_6237..Items.BROODOO_SHIELD_6257 step 2).toIntArray()),

    /**
     * Broodoo Shield (c).
     */
    BROODOO_SHIELDC(ids = (Items.BROODOO_SHIELD_10_6259..Items.BROODOO_SHIELD_6279 step 2).toIntArray()),

    /**
     * Rod Of Ivandis.
     */
    ROD_OF_IVANDIS(ids = (Items.ROD_OF_IVANDIS10_7639..Items.ROD_OF_IVANDIS1_7648).toIntArray()),

    /**
     * Black Mask.
     */
    BLACK_MASK(ids = (Items.BLACK_MASK_10_8901..Items.BLACK_MASK_8921 step 2).toIntArray()),

    /**
     * Amulet Of Glory (t).
     */
    AMULET_OF_GLORYT(ids = (Items.AMULET_OF_GLORYT4_10354..Items.AMULET_OF_GLORYT_10362 step 2).toIntArray()),

    /**
     * Castlewar Brace.
     */
    CASTLEWAR_BRACE(ids = (Items.CASTLEWAR_BRACE3_11079..Items.CASTLEWAR_BRACE1_11083 step 2).toIntArray()),

    /**
     * Forinthry Brace.
     */
    FORINTHRY_BRACE(ids = (Items.FORINTHRY_BRACE5_11095..Items.FORINTHRY_BRACE1_11103 step 2).toIntArray()),

    /**
     * Skills Necklace.
     */
    SKILLS_NECKLACE(ids = (Items.SKILLS_NECKLACE4_11105..Items.SKILLS_NECKLACE_11113 step 2).toIntArray()),

    /**
     * Combat Bracelet.
     */
    COMBAT_BRACELET(ids = (Items.COMBAT_BRACELET4_11118..Items.COMBAT_BRACELET_11126 step 2).toIntArray()),

    /**
     * Digsite Pendant.
     */
    DIGSITE_PENDANT(ids = (Items.DIGSITE_PENDANT_5_11194 downTo Items.DIGSITE_PENDANT_1_11190).toIntArray()),

    /**
     * Void Seal.
     */
    VOID_SEAL(ids = (Items.VOID_SEAL8_11666..Items.VOID_SEAL1_11673).toIntArray()),

    /**
     * Amulet Of Farming.
     */
    AMULET_OF_FARMING(ids = (Items.AMULET_OF_FARMING8_12622 downTo Items.AMULET_OF_FARMING1_12608 step 2).toIntArray()),

    /**
     * Ivandis Flail.
     */
    IVANDIS_FLAIL(ids = (Items.IVANDIS_FLAIL_30_13117..Items.IVANDIS_FLAIL_1_13146).toIntArray()),

    /**
     * FairyRing Of Slaying.
     */
    RING_OF_SLAYING(ids = (Items.RING_OF_SLAYING8_13281..Items.RING_OF_SLAYING1_13288).toIntArray()),

    /**
     * FairyRing Of Wealth.
     */
    RING_OF_WEALTH(ids = (Items.RING_OF_WEALTH4_14646 downTo Items.RING_OF_WEALTH_14638 step 2).toIntArray()
    );

    /**
     * For charge.
     *
     * @param charge The charge level.
     * @return The item ID for the specified charge level.
     */
    fun forCharge(charge: Int): Int {
        return ids[maxCharge() - if (charge < 1) 1 + maxCharge() - ids.size else if (charge > maxCharge()) maxCharge() else charge]
    }

    /**
     * Max charge.
     *
     * @return The maximum charge level for this charged item.
     */
    fun maxCharge(): Int = maxCharges[ordinal]

    companion object {
        private val CHARGE_REGEX = Regex("""\(\D?(\d+)\)""")
        private val idMap = HashMap<Int, ChargedItem>()
        private val maxCharges = IntArray(values().size)

        init {
            values().forEach { chargedItem ->
                maxCharges[chargedItem.ordinal] = getMaxCharge(chargedItem)
                chargedItem.ids.forEach { idMap[it] = chargedItem }
            }
        }

        private fun getMaxCharge(chargedItem: ChargedItem): Int {
            return CHARGE_REGEX.find(ItemDefinition.forId(chargedItem.ids.first()).name)!!.groups[1]!!.value.toInt()
        }

        /**
         * Check if the given item ID is a charged item.
         *
         * @param id The item ID to check.
         * @return True if the item ID is a charged item, false otherwise.
         */
        fun contains(id: Int): Boolean = idMap.containsKey(id)

        /**.
         * Get the ChargedItem enum value for the given item ID
         *
         * @param id The item ID.
         * @return The ChargedItem enum value for the given item ID, or null if not found.
         */
        fun forId(id: Int): ChargedItem? = idMap[id]

        /**
         * Get the charge level for the given item ID.
         *
         * @param id The item ID.
         * @return The charge level for the given item ID, or null if not found.
         */
        fun getCharge(id: Int): Int? {
            val chargedItem = forId(id) ?: return null
            return chargedItem.maxCharge() - chargedItem.ids.indexOf(id)
        }
    }
}
