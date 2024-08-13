package content.global.skill.gathering.fishing

import core.api.consts.NPCs

/**
 * Fishing spot
 *
 * @property ids An array of integers representing unique identifiers for the fishing spot.
 * @property options Vararg parameter that allows multiple FishingOption instances to be passed.
 * @constructor Fishing spot Represents a new instance of the FishingSpot enum with specified ids and options.
 */
enum class FishingSpot(val ids: IntArray, vararg val options: FishingOption) {
    /**
     * Net Bait.
     */
    NET_BAIT(intArrayOf(NPCs.FISHING_SPOT_952, NPCs.FISHING_SPOT_316, NPCs.FISHING_SPOT_319, NPCs.FISHING_SPOT_320, NPCs.FISHING_SPOT_323, NPCs.FISHING_SPOT_325, NPCs.FISHING_SPOT_326, NPCs.FISHING_SPOT_327, NPCs.FISHING_SPOT_330, NPCs.FISHING_SPOT_332, NPCs.FISHING_SPOT_404, NPCs.FISHING_SPOT_1331, NPCs.FISHING_SPOT_2724, NPCs.FISHING_SPOT_4908, NPCs.FISHING_SPOT_7045), FishingOption.SMALL_NET, FishingOption.BAIT),

    /**
     * Cage.
     */
    CAGE(intArrayOf(NPCs.FISHING_SPOT_6267, NPCs.FISHING_SPOT_6996, NPCs.FISHING_SPOT_7862, NPCs.FISHING_SPOT_7863, NPCs.FISHING_SPOT_7864), FishingOption.CRAYFISH_CAGE),

    /**
     * Lure Bait.
     */
    LURE_BAIT(intArrayOf(NPCs.FISHING_SPOT_309, NPCs.FISHING_SPOT_310, NPCs.FISHING_SPOT_311, NPCs.FISHING_SPOT_314, NPCs.FISHING_SPOT_315, NPCs.FISHING_SPOT_317, NPCs.FISHING_SPOT_318, NPCs.FISHING_SPOT_328, NPCs.FISHING_SPOT_329, NPCs.FISHING_SPOT_331, NPCs.FISHING_SPOT_403, NPCs.FISHING_SPOT_927, NPCs.FISHING_SPOT_1189, NPCs.FISHING_SPOT_1190, NPCs.FISHING_SPOT_3019), FishingOption.LURE, FishingOption.PIKE_BAIT),

    /**
     * Cage Harpoon.
     */
    CAGE_HARPOON(intArrayOf(NPCs.FISHING_SPOT_312, NPCs.FISHING_SPOT_321, NPCs.FISHING_SPOT_324, NPCs.FISHING_SPOT_333, NPCs.FISHING_SPOT_405, NPCs.FISHING_SPOT_1332, NPCs.FISHING_SPOT_1399, NPCs.FISHING_SPOT_3804, NPCs.FISHING_SPOT_5470, NPCs.FISHING_SPOT_7046), FishingOption.LOBSTER_CAGE, FishingOption.HARPOON),

    /**
     * Net Harpoon.
     */
    NET_HARPOON(intArrayOf(NPCs.FISHING_SPOT_313, NPCs.FISHING_SPOT_322, NPCs.FISHING_SPOT_334, NPCs.FISHING_SPOT_406, NPCs.FISHING_SPOT_1191, NPCs.FISHING_SPOT_1333, NPCs.FISHING_SPOT_1405, NPCs.FISHING_SPOT_1406, NPCs.FISHING_SPOT_3574, NPCs.FISHING_SPOT_3575, NPCs.FISHING_SPOT_5471, NPCs.FISHING_SPOT_7044), FishingOption.BIG_NET, FishingOption.SHARK_HARPOON),

    /**
     * Harpoon Net.
     */
    HARPOON_NET(intArrayOf(NPCs.FISHING_SPOT_3848, NPCs.FISHING_SPOT_3849), FishingOption.HARPOON, FishingOption.MONKFISH_NET),

    /**
     * Spot Mortmyre.
     */
    SPOT_MORTMYRE(intArrayOf(NPCs.FISHING_SPOT_5748, NPCs.FISHING_SPOT_5749), FishingOption.MORTMYRE_ROD),

    /**
     * Spot Lumdswamp.
     */
    SPOT_LUMDSWAMP(intArrayOf(NPCs.FISHING_SPOT_2067, NPCs.FISHING_SPOT_2068), FishingOption.LUMBDSWAMP_ROD),

    /**
     * Spot Kbwanji.
     */
    SPOT_KBWANJI(intArrayOf(NPCs.FISHING_SPOT_1174), FishingOption.KBWANJI_NET),

    /**
     * Spot Karambwan.
     */
    SPOT_KARAMBWAN(intArrayOf(NPCs.FISHING_SPOT_1177), FishingOption.KARAMBWAN_VES),

    /**
     * Bait Eels.
     */
    BAIT_EELS(intArrayOf(NPCs.FISHING_SPOT_800), FishingOption.OILY_FISHING_ROD);

    companion object {
        private val spotMap: HashMap<Int, FishingSpot> = HashMap()
        private val array: ArrayList<Int> = ArrayList()

        init {
            val spots = values()
            for (spot in spots) {
                for (id in spot.ids) {
                    spotMap[id] = spot
                    array.add(id)
                }
            }
        }

        fun forId(npcID: Int): FishingSpot? {
            return spotMap[npcID]
        }

        fun getAllIds(): IntArray {
            return array.toIntArray()
        }
    }

    /**
     * Get option by name
     *
     * @param op The name of the fishing option to retrieve
     * @return The corresponding FishingOption object if found, otherwise null
     */
    fun getOptionByName(op: String): FishingOption? {
        for (o in options) {
            if (o.option == op.lowercase()) {
                return o
            }
        }
        return null
    }
}
