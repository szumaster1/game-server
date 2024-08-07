package content.global.skill.gathering.farming

import content.global.skill.gathering.farming.timers.CropGrowth
import core.api.getOrStartTimer
import core.cache.def.impl.SceneryDefinition
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery

/**
 * Farming patch
 *
 * @property varbit
 * @property type
 * @constructor Farming patch
 */
enum class FarmingPatch(val varbit: Int, val type: PatchType) {

    /**
     * S Falador Allotment NW.
     */
    S_FALADOR_ALLOTMENT_NW(708, PatchType.ALLOTMENT),

    /**
     * S Falador Allotment SE.
     */
    S_FALADOR_ALLOTMENT_SE(709, PatchType.ALLOTMENT),

    /**
     * Catherby Allotment N.
     */
    CATHERBY_ALLOTMENT_N(710, PatchType.ALLOTMENT),

    /**
     * Catherby Allotment S.
     */
    CATHERBY_ALLOTMENT_S(711, PatchType.ALLOTMENT),

    /**
     * Ardougne Allotment S.
     */
    ARDOUGNE_ALLOTMENT_S(713, PatchType.ALLOTMENT),

    /**
     * Ardougne Allotment N.
     */
    ARDOUGNE_ALLOTMENT_N(712, PatchType.ALLOTMENT),

    /**
     * Port Phas Allotment NW.
     */
    PORT_PHAS_ALLOTMENT_NW(714, PatchType.ALLOTMENT),

    /**
     * Port Phas Allotment SE.
     */
    PORT_PHAS_ALLOTMENT_SE(715, PatchType.ALLOTMENT),

    /**
     * Harmony Island Allotment.
     */
    HARMONY_ISLAND_ALLOTMENT(3402, PatchType.ALLOTMENT),

    /**
     * Catherby Herb.
     */
    CATHERBY_HERB_CE(781, PatchType.HERB_PATCH),

    /**
     * S Falador Herb NE.
     */
    S_FALADOR_HERB_NE(780, PatchType.HERB_PATCH),

    /**
     * Ardougne Herb Ce.
     */
    ARDOUGNE_HERB_CE(782, PatchType.HERB_PATCH),

    /**
     * Port Phas Herb Ne.
     */
    PORT_PHAS_HERB_NE(783, PatchType.HERB_PATCH),

    /**
     * Troll Stronghold Herb.
     */
    TROLL_STRONGHOLD_HERB(2788, PatchType.HERB_PATCH),

    /**
     * S Falador Flower C.
     */
    S_FALADOR_FLOWER_C(728, PatchType.FLOWER_PATCH),

    /**
     * Catherby Flower C.
     */
    CATHERBY_FLOWER_C(729, PatchType.FLOWER_PATCH),

    /**
     * Ardougne Flower C.
     */
    ARDOUGNE_FLOWER_C(730, PatchType.FLOWER_PATCH),

    /**
     * Port Phas Flower C.
     */
    PORT_PHAS_FLOWER_C(731, PatchType.FLOWER_PATCH),

    /**
     * Wilderness Flower.
     */
    WILDERNESS_FLOWER(5067, PatchType.FLOWER_PATCH),

    /**
     * N Falador Tree.
     */
    N_FALADOR_TREE(701, PatchType.TREE_PATCH),

    /**
     * Taverly Tree.
     */
    TAVERLY_TREE(700, PatchType.TREE_PATCH),

    /**
     * Gnome Stronghold Tree.
     */
    GNOME_STRONGHOLD_TREE(2953, PatchType.TREE_PATCH),

    /**
     * Lumbridge Tree.
     */
    LUMBRIDGE_TREE(703, PatchType.TREE_PATCH),

    /**
     * Varrock Tree.
     */
    VARROCK_TREE(702, PatchType.TREE_PATCH),


    /**
     * Gnome Stronghold Fruit Tree.
     */
    GNOME_STRONGHOLD_FRUIT_TREE(704, PatchType.FRUIT_TREE_PATCH),

    /**
     * Catherby Fruit Tree.
     */
    CATHERBY_FRUIT_TREE(707, PatchType.FRUIT_TREE_PATCH),

    /**
     * Tree Gnome Village Fruit Tree.
     */
    TREE_GNOME_VILLAGE_FRUIT_TREE(705, PatchType.FRUIT_TREE_PATCH),

    /**
     * Brimhaven Fruit Tree.
     */
    BRIMHAVEN_FRUIT_TREE(706, PatchType.FRUIT_TREE_PATCH),

    /**
     * Lletya Fruit Tree.
     */
    LLETYA_FRUIT_TREE(4317, PatchType.FRUIT_TREE_PATCH),

    /**
     * Entrana Hops.
     */
    ENTRANA_HOPS(717, PatchType.HOPS_PATCH),

    /**
     * Lumbridge Hops.
     */
    LUMBRIDGE_HOPS(718, PatchType.HOPS_PATCH),

    /**
     * Mcgrubor Hops.
     */
    MCGRUBOR_HOPS(719, PatchType.HOPS_PATCH),

    /**
     * Yanille Hops.
     */
    YANILLE_HOPS(716, PatchType.HOPS_PATCH),

    /**
     * Champions Guild Bush.
     */
    CHAMPIONS_GUILD_BUSH(732, PatchType.BUSH_PATCH),

    /**
     * Rimmington Bush.
     */
    RIMMINGTON_BUSH(733, PatchType.BUSH_PATCH),

    /**
     * Ardougne Bush.
     */
    ARDOUGNE_BUSH(735, PatchType.BUSH_PATCH),

    /**
     * Etceteria Bush.
     */
    ETCETERIA_BUSH(734, PatchType.BUSH_PATCH),

    /**
     * Etceteria Spirit Tree.
     */
    ETCETERIA_SPIRIT_TREE(722, PatchType.SPIRIT_TREE_PATCH),

    /**
     * Port Sarim Spirit Tree.
     */
    PORT_SARIM_SPIRIT_TREE(720, PatchType.SPIRIT_TREE_PATCH),

    /**
     * Karamja Spirit Tree.
     */
    KARAMJA_SPIRIT_TREE(724, PatchType.SPIRIT_TREE_PATCH),

    /**
     * Draynor Belladonna.
     */
    DRAYNOR_BELLADONNA(748, PatchType.BELLADONNA_PATCH),

    /**
     * Canifis Mushroom.
     */
    CANIFIS_MUSHROOM(746, PatchType.MUSHROOM_PATCH),

    /**
     * Alkharid Cactus.
     */
    ALKHARID_CACTUS(744, PatchType.CACTUS_PATCH),

    /**
     * Evil Turnip.
     */
    EVIL_TURNIP(4291, PatchType.EVIL_TURNIP_PATCH),

    /**
     * Calquat Tree.
     */
    CALQUAT_TREE(726, PatchType.FRUIT_TREE_PATCH), // Should be other, but I'd prefer if it worked.

    /**
     * Enriched Seed.
     */
    ENRICHED_SEED(5533, PatchType.SPECIAL_PATCH);


    companion object {
        @JvmField
        val patches = FarmingPatch.values().map { it.varbit to it }.toMap()
        val patchNodes = ArrayList<Int>()
        val nodeMap = HashMap<Int, SceneryDefinition>()

        init {
            patchNodes.addAll(8550..8557) //allotment wrappers
            patchNodes.addAll(7847..7853) //flower patch wrappers
            patchNodes.addAll(8150..8156) //herb patch wrappers
            patchNodes.addAll(8388..8391) // Tree patches
            patchNodes.add(19147) //Tree patch
            patchNodes.addAll(7962..7965) //fruit trees
            patchNodes.addAll(8173..8176) //hops
            patchNodes.addAll(7577..7580) //bush
            patchNodes.add(23760) //evil turnip
            patchNodes.add(7572) //belladonna
            patchNodes.add(8337) //mushroom
            patchNodes.add(27197) //jade vine
            patchNodes.add(7771) //cactus
            patchNodes.add(7807) //calquat
            patchNodes.addAll(8382..8383)//spirit trees
            patchNodes.add(8338) //spirit tree
            patchNodes.add(18816) //death plateau wrapper
            patchNodes.add(41339) // enriched seed

            for (patch in patchNodes) {
                val def = SceneryDefinition.forId(patch)
                nodeMap[def.varbitID] = def
            }
        }

        @JvmStatic
        fun forObject(obj: Scenery): FarmingPatch?{
            return forObjectID(obj.id)
        }

        @JvmStatic
        fun forObjectID(id: Int): FarmingPatch?{
            val objDef = SceneryDefinition.forId(id)
            return patches[objDef.varbitID]
        }

        fun getSceneryDefByVarbit (id: Int) : SceneryDefinition? {
            return nodeMap[id]
        }
    }

    /**
     * Get patch for
     *
     * @param player
     * @return
     */
    fun getPatchFor(player: Player): Patch {
        var crops = getOrStartTimer <CropGrowth> (player)
        return crops.getPatch(this)
    }
}
