package content.global.skill.gathering.farming

/**
 * Farmers.
 *
 * @property id
 * @property patches
 * @constructor Farmers
 */
enum class Farmers(val id: Int, val patches: Array<FarmingPatch>) {
    /**
     * Lyra.
     */
    LYRA(2326, arrayOf(FarmingPatch.PORT_PHAS_ALLOTMENT_NW, FarmingPatch.PORT_PHAS_ALLOTMENT_SE)),

    /**
     * Elstan.
     */
    ELSTAN(2323, arrayOf(FarmingPatch.S_FALADOR_ALLOTMENT_NW, FarmingPatch.S_FALADOR_ALLOTMENT_SE)),

    /**
     * Heskel.
     */
    HESKEL(2340, arrayOf(FarmingPatch.N_FALADOR_TREE)),

    /**
     * Alain.
     */
    ALAIN(2339, arrayOf(FarmingPatch.TAVERLY_TREE)),

    /**
     * Dantaera.
     */
    DANTAERA(2324, arrayOf(FarmingPatch.CATHERBY_ALLOTMENT_N, FarmingPatch.CATHERBY_ALLOTMENT_S)),

    /**
     * Ellena.
     */
    ELLENA(2331, arrayOf(FarmingPatch.CATHERBY_FRUIT_TREE)),

    /**
     * Garth.
     */
    GARTH(2330,arrayOf(FarmingPatch.BRIMHAVEN_FRUIT_TREE)),

    /**
     * Gileth.
     */
    GILETH(2344,arrayOf(FarmingPatch.TREE_GNOME_VILLAGE_FRUIT_TREE)),

    /**
     * Amaethwr.
     */
    AMAETHWR(2860,arrayOf(FarmingPatch.LLETYA_FRUIT_TREE)),

    /**
     * Selena.
     */
    SELENA(2332, arrayOf(FarmingPatch.YANILLE_HOPS)),

    /**
     * Kragen.
     */
    KRAGEN(2325, arrayOf(FarmingPatch.ARDOUGNE_ALLOTMENT_N, FarmingPatch.ARDOUGNE_ALLOTMENT_S)),

    /**
     * Bolongo.
     */
    BOLONGO(2343, arrayOf(FarmingPatch.GNOME_STRONGHOLD_FRUIT_TREE)),

    /**
     * Prissy Scilla.
     */
    PRISSY_SCILLA(1037, arrayOf(FarmingPatch.GNOME_STRONGHOLD_TREE)),

    /**
     * Fayeth.
     */
    FAYETH(2342, arrayOf(FarmingPatch.LUMBRIDGE_TREE)),

    /**
     * Treznor.
     */
    TREZNOR(2341, arrayOf(FarmingPatch.VARROCK_TREE)),

    /**
     * Vasquen.
     */
    VASQUEN(2333, arrayOf(FarmingPatch.LUMBRIDGE_HOPS)),

    /**
     * Rhonen.
     */
    RHONEN(2334, arrayOf(FarmingPatch.MCGRUBOR_HOPS)),

    /**
     * Francis.
     */
    FRANCIS(2327, arrayOf(FarmingPatch.ENTRANA_HOPS)),

    /**
     * Dreven.
     */
    DREVEN(2335, arrayOf(FarmingPatch.CHAMPIONS_GUILD_BUSH)),

    /**
     * Taria.
     */
    TARIA(2336, arrayOf(FarmingPatch.RIMMINGTON_BUSH)),

    /**
     * Rhazien.
     */
    RHAZIEN(2337, arrayOf(FarmingPatch.ETCETERIA_BUSH)),

    /**
     * Torrell.
     */
    TORRELL(2338, arrayOf(FarmingPatch.ARDOUGNE_BUSH)),

    /**
     * Yulf Squecks.
     */
    YULF_SQUECKS(4561, arrayOf(FarmingPatch.ETCETERIA_SPIRIT_TREE)),

    /**
     * Imiago.
     */
    IMIAGO(8041, arrayOf(FarmingPatch.CALQUAT_TREE));

    ;

    companion object{
        @JvmField
        val farmers = values().map { it.id to it }.toMap()

        @JvmStatic
        fun forId(id: Int): Farmers?{
            return farmers[id]
        }
    }
}