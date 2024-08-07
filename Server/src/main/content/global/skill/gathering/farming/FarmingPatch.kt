package content.global.skill.gathering.farming

import content.global.skill.gathering.farming.timers.CropGrowth
import core.api.getOrStartTimer
import core.cache.def.impl.SceneryDefinition
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery

/**
 * Farming patch.
 *
 * @property varbit The varbit value of the farming patch.
 * @property type   The type of patch (e.g., ALLOTMENT, HERB_PATCH, etc.).
 * @constructor Creates a farming patch with the specified varbit and type.
 */
enum class FarmingPatch(val varbit: Int, val type: PatchType) {

    // Allotment patches
    S_FALADOR_ALLOTMENT_NW(708, PatchType.ALLOTMENT),
    S_FALADOR_ALLOTMENT_SE(709, PatchType.ALLOTMENT),
    CATHERBY_ALLOTMENT_N(710, PatchType.ALLOTMENT),
    CATHERBY_ALLOTMENT_S(711, PatchType.ALLOTMENT),
    ARDOUGNE_ALLOTMENT_S(713, PatchType.ALLOTMENT),
    ARDOUGNE_ALLOTMENT_N(712, PatchType.ALLOTMENT),
    PORT_PHAS_ALLOTMENT_NW(714, PatchType.ALLOTMENT),
    PORT_PHAS_ALLOTMENT_SE(715, PatchType.ALLOTMENT),
    HARMONY_ISLAND_ALLOTMENT(3402, PatchType.ALLOTMENT),

    // Herb patches
    CATHERBY_HERB_CE(781, PatchType.HERB_PATCH),
    S_FALADOR_HERB_NE(780, PatchType.HERB_PATCH),
    ARDOUGNE_HERB_CE(782, PatchType.HERB_PATCH),
    PORT_PHAS_HERB_NE(783, PatchType.HERB_PATCH),
    TROLL_STRONGHOLD_HERB(2788, PatchType.HERB_PATCH),

    // Flower patches
    S_FALADOR_FLOWER_C(728, PatchType.FLOWER_PATCH),
    CATHERBY_FLOWER_C(729, PatchType.FLOWER_PATCH),
    ARDOUGNE_FLOWER_C(730, PatchType.FLOWER_PATCH),
    PORT_PHAS_FLOWER_C(731, PatchType.FLOWER_PATCH),
    WILDERNESS_FLOWER(5067, PatchType.FLOWER_PATCH),

    // Tree patches
    N_FALADOR_TREE(701, PatchType.TREE_PATCH),
    TAVERLY_TREE(700, PatchType.TREE_PATCH),
    GNOME_STRONGHOLD_TREE(2953, PatchType.TREE_PATCH),
    LUMBRIDGE_TREE(703, PatchType.TREE_PATCH),
    VARROCK_TREE(702, PatchType.TREE_PATCH),

    // Fruit tree patches
    GNOME_STRONGHOLD_FRUIT_TREE(704, PatchType.FRUIT_TREE_PATCH),
    CATHERBY_FRUIT_TREE(707, PatchType.FRUIT_TREE_PATCH),
    TREE_GNOME_VILLAGE_FRUIT_TREE(705, PatchType.FRUIT_TREE_PATCH),
    BRIMHAVEN_FRUIT_TREE(706, PatchType.FRUIT_TREE_PATCH),
    LLETYA_FRUIT_TREE(4317, PatchType.FRUIT_TREE_PATCH),

    // Hops patches
    ENTRANA_HOPS(717, PatchType.HOPS_PATCH),
    LUMBRIDGE_HOPS(718, PatchType.HOPS_PATCH),
    MCGRUBOR_HOPS(719, PatchType.HOPS_PATCH),
    YANILLE_HOPS(716, PatchType.HOPS_PATCH),

    // Bush patches
    CHAMPIONS_GUILD_BUSH(732, PatchType.BUSH_PATCH),
    RIMMINGTON_BUSH(733, PatchType.BUSH_PATCH),
    ARDOUGNE_BUSH(735, PatchType.BUSH_PATCH),
    ETCETERIA_BUSH(734, PatchType.BUSH_PATCH),

    // Spirit tree patches
    ETCETERIA_SPIRIT_TREE(722, PatchType.SPIRIT_TREE_PATCH),
    PORT_SARIM_SPIRIT_TREE(720, PatchType.SPIRIT_TREE_PATCH),
    KARAMJA_SPIRIT_TREE(724, PatchType.SPIRIT_TREE_PATCH),

    // Other patches
    DRAYNOR_BELLADONNA(748, PatchType.BELLADONNA_PATCH),
    CANIFIS_MUSHROOM(746, PatchType.MUSHROOM_PATCH),
    ALKHARID_CACTUS(744, PatchType.CACTUS_PATCH),
    EVIL_TURNIP(4291, PatchType.EVIL_TURNIP_PATCH),
    CALQUAT_TREE(726, PatchType.FRUIT_TREE_PATCH),
    ENRICHED_SEED(5533, PatchType.SPECIAL_PATCH);

    companion object {
        @JvmField
        val patches = FarmingPatch.values().map { it.varbit to it }.toMap()
        val patchNodes = ArrayList<Int>()
        val nodeMap = HashMap<Int, SceneryDefinition>()

        init {
            patchNodes.addAll(8550..8557) // Allotment wrappers
            patchNodes.addAll(7847..7853) // Flower patch wrappers
            patchNodes.addAll(8150..8156) // Herb patch wrappers
            patchNodes.addAll(8388..8391) // Tree patches
            patchNodes.add(19147) // Tree patch
            patchNodes.addAll(7962..7965) // Fruit trees
            patchNodes.addAll(8173..8176) // Hops
            patchNodes.addAll(7577..7580) // Bush
            patchNodes.add(23760) // Evil turnip
            patchNodes.add(7572) // Belladonna
            patchNodes.add(8337) // Mushroom
            patchNodes.add(27197) // Jade vine
            patchNodes.add(7771) // Cactus
            patchNodes.add(7807) // Calquat
            patchNodes.addAll(8382..8383) // Spirit trees
            patchNodes.add(8338) // Spirit tree
            patchNodes.add(18816) // Death plateau wrapper
            patchNodes.add(41339) // Enriched seed

            for (patch in patchNodes) {
                val def = SceneryDefinition.forId(patch)
                nodeMap[def.varbitID] = def
            }
        }

        /**
         * Returns the FarmingPatch enum value for the given Scenery object.
         *
         * @param obj The Scenery object
         * @return The corresponding FarmingPatch enum value, or null if not found
         */
        @JvmStatic
        fun forObject(obj: Scenery): FarmingPatch? {
            return forObjectID(obj.id)
        }

        /**
         * Returns the FarmingPatch enum value for the given object ID.
         *
         * @param id The object ID
         * @return The corresponding FarmingPatch enum value, or null if not found
         */
        @JvmStatic
        fun forObjectID(id: Int): FarmingPatch? {
            val objDef = SceneryDefinition.forId(id)
            return patches[objDef.varbitID]
        }

        /**
         * Returns the SceneryDefinition for the given varbit ID.
         *
         * @param id The varbit ID
         * @return The corresponding SceneryDefinition, or null if not found
         */
        fun getSceneryDefByVarbit(id: Int): SceneryDefinition? {
            return nodeMap[id]
        }
    }

    /**
     * Returns the Patch object for the specified player.
     *
     * @param player The player
     * @return The Patch object
     */
    fun getPatchFor(player: Player): Patch {
        var crops = getOrStartTimer<CropGrowth>(player)
        return crops.getPatch(this)
    }
}
