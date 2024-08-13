package content.global.handlers.item.withitem

import core.api.*
import core.api.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import kotlin.math.min

/**
 * Poisoned weapon listeners.
 */
class PoisonedWeaponListeners : InteractionListener {

    private val poisons = intArrayOf(Items.WEAPON_POISON_187, Items.WEAPON_POISON_PLUS_5937, Items.WEAPON_POISON_PLUS_PLUS_5940)
    private val cleaningCloth = Items.CLEANING_CLOTH_3188
    private val poisonableItems = PoisonSets.itemMap.keys.toIntArray()
    private val poisonedItems = PoisonSets.itemMap.values.toIntArray()
    private val karambwanPoison = Items.KARAMBWAN_PASTE_3153
    private val karamwanWeapons = KarambwanPoisonSets.values().map(KarambwanPoisonSets::base).toIntArray()

    override fun defineListeners() {
        // Used weapon poison potion on poison able items.
        onUseWith(IntType.ITEM, poisons, *poisonableItems) { player, used, with ->
            val index = poisons.indexOf(used.id)
            val product = PoisonSets.itemMap[with.id]!![index]
            val amt = min(5, with.asItem().amount)

            if (removeItem(player, Item(with.id, amt))) {
                removeItem(player, used.id)
                addItemOrDrop(player, product, amt)
                addItemOrDrop(player, Items.VIAL_229)
                sendMessage(player, "You poison the ${with.name.lowercase()}.")
            }
            return@onUseWith true
        }

        // Used karambwan poison on karambwan weapons.
        onUseWith(IntType.ITEM, karambwanPoison, *karamwanWeapons) { player, used, with ->
            val product = KarambwanPoisonSets.poisonMap[with.id] ?: return@onUseWith true
            if (removeItem(player, used.asItem())) {
                replaceSlot(player, with.asItem().slot, Item(product.kp, 1))
                sendMessage(player, "You smear the poisonous Karambwan paste over the " + "${if(!product.name.contains("spear", true)) "hasta" else "spear" }.")
            }
            return@onUseWith true
        }

        // Remove poison from the karambwan weapon interaction.
        onUseWith(IntType.ITEM, cleaningCloth, *karamwanWeapons) { player, used, with ->
            val product = KarambwanPoisonSets.poisonMap[with.id] ?: return@onUseWith true
            if (removeItem(player, used.asItem())) {
                replaceSlot(player, with.asItem().slot, Item(product.base, 1))
            }
            return@onUseWith true
        }

        // Remove poison from the poison weapon interaction.
        onUseWith(IntType.ITEM, cleaningCloth, *poisonedItems) { player, _, with ->
            val base = PoisonSets.getBase(with.id) ?: return@onUseWith false
            val amt = min(5, with.asItem().amount)
            removeItem(player, Item(with.id, amt))
            addItemOrDrop(player, base, amt)
            return@onUseWith true
        }
    }

    /**
     * Enum class representing different sets of Karambwan poison.
     *
     * @param base The base value of the poison set.
     * @param kp The kp value of the poison set.
     * @constructor Creates a new instance of KarambwanPoisonSets with the given base and kp values.
     */
    internal enum class KarambwanPoisonSets(val base: Int, val kp: Int) {
        /**
         * Bronze Spear.
         */
        BRONZE_SPEAR(Items.BRONZE_SPEAR_1237, Items.BRONZE_SPEARKP_3170),

        /**
         * Iron Spear.
         */
        IRON_SPEAR(Items.IRON_SPEAR_1239, Items.IRON_SPEARKP_3171),

        /**
         * Steel Spear.
         */
        STEEL_SPEAR(Items.STEEL_SPEAR_1241, Items.STEEL_SPEARKP_3172),

        /**
         * Black Spear.
         */
        BLACK_SPEAR(Items.BLACK_SPEAR_4580, Items.BLACK_SPEARKP_4584),

        /**
         * Mithril Spear.
         */
        MITHRIL_SPEAR(Items.MITHRIL_SPEAR_1243, Items.MITHRIL_SPEARKP_3173),

        /**
         * Adamant Spear.
         */
        ADAMANT_SPEAR(Items.ADAMANT_SPEAR_1245, Items.ADAMANT_SPEARKP_3174),

        /**
         * Rune Spear.
         */
        RUNE_SPEAR(Items.RUNE_SPEAR_1247, Items.RUNE_SPEARKP_3175),

        /**
         * Dragon Spear.
         */
        DRAGON_SPEAR(Items.DRAGON_SPEAR_1249, Items.DRAGON_SPEARKP_3176),

        /**
         * Bronze Hasta.
         */
        BRONZE_HASTA(Items.BRONZE_HASTA_11367, Items.BRONZE_HASTAKP_11381),

        /**
         * Iron Hasta.
         */
        IRON_HASTA(Items.IRON_HASTA_11369, Items.IRON_HASTAKP_11388),

        /**
         * Steel Hasta.
         */
        STEEL_HASTA(Items.STEEL_HASTA_11371, Items.STEEL_HASTAKP_11395),

        /**
         * Mithril Hasta.
         */
        MITHRIL_HASTA(Items.MITHRIL_HASTA_11373, Items.MITHRIL_HASTAKP_11402),

        /**
         * Adamant Hasta.
         */
        ADAMANT_HASTA(Items.ADAMANT_HASTA_11375, Items.ADAMANT_HASTAKP_11409),

        /**
         * Rune Hasta.
         */
        RUNE_HASTA(Items.RUNE_HASTA_11377, Items.RUNE_HASTAKP_11416);

        companion object {
            val poisonMap = HashMap<Int, KarambwanPoisonSets>()
            init {
                for (product in values()) {
                    poisonMap[product.base] = product
                }
            }
        }
    }

    /**
     * Enum representing different poison sets.
     *
     * @param base The base poison level.
     * @param p The first poison level.
     * @param pp The second poison level.
     * @param ppp The third poison level.
     * @constructor Creates a PoisonSets enum with specified poison levels.
     */
    internal enum class PoisonSets(val base: Int, val p: Int, val pp: Int, val ppp: Int) {

        /**
         * Bronze Arrow.
         */
        BRONZE_ARROW(Items.BRONZE_ARROW_882, Items.BRONZE_ARROWP_883, Items.BRONZE_ARROWP_PLUS_5616, Items.BRONZE_ARROWP_PLUS_PLUS_5622),

        /**
         * Iron Arrow.
         */
        IRON_ARROW(Items.IRON_ARROW_884, Items.IRON_ARROWP_885, Items.IRON_ARROWP_PLUS_5617, Items.IRON_ARROWP_PLUS_PLUS_5623),

        /**
         * Steel Arrow.
         */
        STEEL_ARROW(Items.STEEL_ARROW_886, Items.STEEL_ARROWP_887, Items.STEEL_ARROWP_PLUS_5618, Items.STEEL_ARROWP_PLUS_PLUS_5624),

        /**
         * Mithril Arrow.
         */
        MITHRIL_ARROW(Items.MITHRIL_ARROW_888, Items.MITHRIL_ARROWP_889, Items.MITHRIL_ARROWP_PLUS_5619, Items.MITHRIL_ARROWP_PLUS_PLUS_5625),

        /**
         * Adamant Arrow.
         */
        ADAMANT_ARROW(Items.ADAMANT_ARROW_890, Items.ADAMANT_ARROWP_891, Items.ADAMANT_ARROWP_PLUS_5620, Items.ADAMANT_ARROWP_PLUS_PLUS_5626),

        /**
         * Rune Arrow.
         */
        RUNE_ARROW(Items.RUNE_ARROW_892, Items.RUNE_ARROWP_893, Items.RUNE_ARROWP_PLUS_5621, Items.RUNE_ARROWP_PLUS_PLUS_5627),

        /**
         * Bronze Knife.
         */
        BRONZE_KNIFE(Items.BRONZE_KNIFE_864, Items.BRONZE_KNIFEP_870, Items.BRONZE_KNIFEP_PLUS_5654, Items.BRONZE_KNIFEP_PLUS_PLUS_5661),

        /**
         * Iron Knife.
         */
        IRON_KNIFE(Items.IRON_KNIFE_863, Items.IRON_KNIFEP_871, Items.IRON_KNIFEP_PLUS_5655, Items.IRON_KNIFEP_PLUS_PLUS_5662),

        /**
         * Steel Knife.
         */
        STEEL_KNIFE(Items.STEEL_KNIFE_865, Items.STEEL_KNIFEP_872, Items.STEEL_KNIFEP_PLUS_5656, Items.STEEL_KNIFEP_PLUS_PLUS_5663),

        /**
         * Black Knife.
         */
        BLACK_KNIFE(Items.BLACK_KNIFE_869, Items.BLACK_KNIFEP_874, Items.BLACK_KNIFEP_PLUS_5658, Items.BLACK_KNIFEP_PLUS_PLUS_5665),

        /**
         * Mithril Knife.
         */
        MITHRIL_KNIFE(Items.MITHRIL_KNIFE_866, Items.MITHRIL_KNIFEP_873, Items.MITHRIL_KNIFEP_PLUS_5657, Items.MITHRIL_KNIFEP_PLUS_PLUS_5664),

        /**
         * Adamant Knife.
         */
        ADAMANT_KNIFE(Items.ADAMANT_KNIFE_867, Items.ADAMANT_KNIFEP_875, Items.ADAMANT_KNIFEP_PLUS_5659, Items.ADAMANT_KNIFEP_PLUS_PLUS_5666),

        /**
         * Rune Knife.
         */
        RUNE_KNIFE(Items.RUNE_KNIFE_868, Items.RUNE_KNIFEP_876, Items.RUNE_KNIFEP_PLUS_5660, Items.RUNE_KNIFEP_PLUS_PLUS_5667),

        /**
         * Bronze Dart.
         */
        BRONZE_DART(Items.BRONZE_DART_806, Items.BRONZE_DARTP_812, Items.BRONZE_DARTP_PLUS_5628, Items.BRONZE_DARTP_PLUS_PLUS_5635),

        /**
         * Iron Dart.
         */
        IRON_DART(Items.IRON_DART_807, Items.IRON_DARTP_813, Items.IRON_DARTP_PLUS_5629, Items.IRON_DARTP_PLUS_PLUS_5636),

        /**
         * Steel Dart.
         */
        STEEL_DART(Items.STEEL_DART_808, Items.STEEL_DARTP_814, Items.STEEL_DARTP_PLUS_5630, Items.STEEL_DARTP_PLUS_PLUS_5637),

        /**
         * Black Dart.
         */
        BLACK_DART(Items.BLACK_DART_3093, Items.BLACK_DARTP_3094, Items.BLACK_DARTP_PLUS_5631, Items.BLACK_DARTP_PLUS_PLUS_5638),

        /**
         * Mithril Dart.
         */
        MITHRIL_DART(Items.MITHRIL_DART_809, Items.MITHRIL_DARTP_815, Items.MITHRIL_DARTP_PLUS_5632, Items.MITHRIL_DARTP_PLUS_PLUS_5639),

        /**
         * Adamant Dart.
         */
        ADAMANT_DART(Items.ADAMANT_DART_810, Items.ADAMANT_DARTP_816, Items.ADAMANT_DARTP_PLUS_5633, Items.ADAMANT_DARTP_PLUS_PLUS_5640),

        /**
         * Rune Dart.
         */
        RUNE_DART(Items.RUNE_DART_811, Items.RUNE_DARTP_817, Items.RUNE_DARTP_PLUS_5634, Items.RUNE_DARTP_PLUS_PLUS_5641),

        /**
         * Blurite Bolt.
         */
        BLURITE_BOLT(Items.BLURITE_BOLTS_9139, Items.BLURITE_BOLTSP_9286, Items.BLURITE_BOLTSP_PLUS_9293, Items.BLURITE_BOLTSP_PLUS_PLUS_9300),

        /**
         * Bronze Bolt.
         */
        BRONZE_BOLT(Items.BRONZE_BOLTS_877, Items.BRONZE_BOLTSP_878, Items.BRONZE_BOLTSP_PLUS_6061, Items.BRONZE_BOLTSP_PLUS_PLUS_6062),

        /**
         * Iron Bolt.
         */
        IRON_BOLT(Items.IRON_BOLTS_9140, Items.IRON_BOLTS_P_9287, Items.IRON_BOLTSP_PLUS_9294, Items.IRON_BOLTSP_PLUS_PLUS_9301),

        /**
         * Steel Bolt.
         */
        STEEL_BOLT(Items.STEEL_BOLTS_9141, Items.STEEL_BOLTS_P_9288, Items.STEEL_BOLTSP_PLUS_9295, Items.STEEL_BOLTSP_PLUS_PLUS_9302),

        /**
         * Black Bolt.
         */
        BLACK_BOLT(Items.BLACK_BOLTS_13083, Items.BLACK_BOLTSP_13084, Items.BLACK_BOLTSP_PLUS_13085, Items.BLACK_BOLTSP_PLUS_PLUS_13086),

        /**
         * Mithril Bolt.
         */
        MITHRIL_BOLT(Items.MITHRIL_BOLTS_9142, Items.MITHRIL_BOLTS_P_9289, Items.MITHRIL_BOLTSP_PLUS_9296, Items.MITHRIL_BOLTSP_PLUS_PLUS_9303),

        /**
         * Adamant Bolt.
         */
        ADAMANT_BOLT(Items.ADAMANT_BOLTS_9143, Items.ADAMANT_BOLTS_P_9290, Items.ADAMANT_BOLTSP_PLUS_9297, Items.ADAMANT_BOLTSP_PLUS_PLUS_9304),

        /**
         * Rune Bolt.
         */
        RUNE_BOLT(Items.RUNE_BOLTS_9144, Items.RUNITE_BOLTS_P_9291, Items.RUNITE_BOLTSP_PLUS_9298, Items.RUNITE_BOLTSP_PLUS_PLUS_9305),

        /**
         * Silver Bolt.
         */
        SILVER_BOLT(Items.SILVER_BOLTS_9145, Items.SILVER_BOLTS_P_9292, Items.SILVER_BOLTSP_PLUS_9299, Items.SILVER_BOLTSP_PLUS_PLUS_9306),

        /**
         * Iron Javelin.
         */
        IRON_JAVELIN(Items.IRON_JAVELIN_826, Items.BRONZE_JAVELINP_831, Items.IRON_JAVELINP_PLUS_5643, Items.BRONZE_JAVNP_PLUS_PLUS_5648),

        /**
         * Bronze Javelin.
         */
        BRONZE_JAVELIN(Items.IRON_JAVELIN_826, Items.IRON_JAVELINP_832, Items.BRONZE_JAVELINP_PLUS_5642, Items.IRON_JAVELINP_PLUS_PLUS_5649),

        /**
         * Steel Javelin.
         */
        STEEL_JAVELIN(Items.STEEL_JAVELIN_827, Items.STEEL_JAVELINP_833, Items.STEEL_JAVELINP_PLUS_5644, Items.STEEL_JAVELINP_PLUS_PLUS_5650),

        /**
         * Mithril Javelin.
         */
        MITHRIL_JAVELIN(Items.MITHRIL_JAVELIN_828, Items.MITHRIL_JAVELINP_834, Items.MITHRIL_JAVELINP_PLUS_5645, Items.MITHRIL_JAVELINP_PLUS_PLUS_5651),

        /**
         * Adamant Javelin.
         */
        ADAMANT_JAVELIN(Items.ADAMANT_JAVELIN_829, Items.ADAMANT_JAVELINP_835, Items.ADAMANT_JAVELINP_PLUS_5646, Items.ADAMANT_JAVELINP_PLUS_PLUS_5652),

        /**
         * Rune Javelin.
         */
        RUNE_JAVELIN(Items.RUNE_JAVELIN_830, Items.RUNE_JAVELINP_836, Items.RUNE_JAVELINP_PLUS_5647, Items.RUNE_JAVELINP_PLUS_PLUS_5653),

        /**
         * Morrigan Javelin.
         */
        MORRIGAN_JAVELIN(Items.MORRIGANS_JAVELIN_13879, Items.MORRIGANS_JAVELINP_13880, Items.MORRIGANS_JAVELINP_PLUS_13881, Items.MORRIGANS_JAVELINP_PLUS_PLUS_13882),

        /**
         * Iron Dagger.
         */
        IRON_DAGGER(Items.IRON_DAGGER_1203, Items.IRON_DAGGERP_1219, Items.IRON_DAGGERP_PLUS_5668, Items.IRON_DAGGERP_PLUS_PLUS_5686),

        /**
         * Bronze Dagger.
         */
        BRONZE_DAGGER(Items.BRONZE_DAGGER_1205, Items.BRONZE_DAGGERP_1221, Items.BRONZE_DAGGERP_PLUS_5670, Items.BRZE_DAGGERP_PLUS_PLUS_5688),

        /**
         * Steel Dagger.
         */
        STEEL_DAGGER(Items.STEEL_DAGGER_1207, Items.STEEL_DAGGERP_1223, Items.STEEL_DAGGERP_PLUS_5672, Items.STEEL_DAGGERP_PLUS_PLUS_5690),

        /**
         * Black Dagger.
         */
        BLACK_DAGGER(Items.BLACK_DAGGER_1217, Items.BLACK_DAGGERP_1233, Items.BLACK_DAGGERP_PLUS_5682, Items.BLACK_DAGGERP_PLUS_PLUS_5700),

        /**
         * Bone Dagger.
         */
        BONE_DAGGER(Items.BONE_DAGGER_8872, Items.BONE_DAGGER_P_8874, Items.BONE_DAGGER_P_PLUS_8876, Items.BONE_DAGGER_P_PLUS_PLUS_8878),

        /**
         * Mithril Dagger.
         */
        MITHRIL_DAGGER(Items.MITHRIL_DAGGER_1209, Items.MITHRIL_DAGGERP_1225, Items.MITHRIL_DAGGERP_PLUS_5674, Items.MITHRIL_DAGGERP_PLUS_PLUS_5692),

        /**
         * Adamant Dagger.
         */
        ADAMANT_DAGGER(Items.ADAMANT_DAGGER_1211, Items.ADAMANT_DAGGERP_1227, Items.ADAMANT_DAGGERP_PLUS_5676, Items.ADAMANT_DAGGERP_PLUS_PLUS_5694),

        /**
         * Rune Dagger.
         */
        RUNE_DAGGER(Items.RUNE_DAGGER_1213, Items.RUNE_DAGGERP_1229, Items.RUNE_DAGGERP_PLUS_5678, Items.RUNE_DAGGERP_PLUS_PLUS_5696),

        /**
         * Dragon Dagger.
         */
        DRAGON_DAGGER(Items.DRAGON_DAGGER_1215, Items.DRAGON_DAGGERP_1231, Items.DRAGON_DAGGERP_PLUS_5680, Items.DRAGON_DAGGERP_PLUS_PLUS_5698),

        /**
         * Bronze Spear.
         */
        BRONZE_SPEAR(Items.BRONZE_SPEAR_1237, Items.BRONZE_SPEARP_1251, Items.BRONZE_SPEARP_PLUS_5704, Items.BRONZE_SPEARP_PLUS_PLUS_5718),

        /**
         * Iron Spear.
         */
        IRON_SPEAR(Items.IRON_SPEAR_1239, Items.IRON_SPEARP_1253, Items.IRON_SPEARP_PLUS_5706, Items.IRON_SPEARP_PLUS_PLUS_5720),

        /**
         * Steel Spear.
         */
        STEEL_SPEAR(Items.STEEL_SPEAR_1241, Items.STEEL_SPEARP_1255, Items.STEEL_SPEARP_PLUS_5708, Items.STEEL_SPEARP_PLUS_PLUS_5722),

        /**
         * Black Spear.
         */
        BLACK_SPEAR(Items.BLACK_SPEAR_4580, Items.BLACK_SPEARP_4582, Items.BLACK_SPEARP_PLUS_5734, Items.BLACK_SPEARP_PLUS_PLUS_5736),

        /**
         * Mithril Spear.
         */
        MITHRIL_SPEAR(Items.MITHRIL_SPEAR_1243, Items.MITHRIL_SPEARP_1257, Items.MITHRIL_SPEARP_PLUS_5710, Items.MITHRIL_SPEARP_PLUS_PLUS_5724),

        /**
         * Adamant Spear.
         */
        ADAMANT_SPEAR(Items.ADAMANT_SPEAR_1245, Items.ADAMANT_SPEARP_1259, Items.ADAMANT_SPEARP_PLUS_5712, Items.ADAMANT_SPEARP_PLUS_PLUS_5726),

        /**
         * Rune Spear.
         */
        RUNE_SPEAR(Items.RUNE_SPEAR_1247, Items.RUNE_SPEARP_1261, Items.RUNE_SPEARP_PLUS_5714, Items.RUNE_SPEARP_PLUS_PLUS_5728),

        /**
         * Dragon Spear.
         */
        DRAGON_SPEAR(Items.DRAGON_SPEAR_1249, Items.DRAGON_SPEARP_1263, Items.DRAGON_SPEARP_PLUS_5716, Items.DRAGON_SPEARP_PLUS_PLUS_5730);

        companion object {
            val itemMap = values().map { it.base to intArrayOf(it.p, it.pp, it.ppp) }.toMap()

            fun getBase(poisoned: Int): Int? {
                for ((base, set) in itemMap.entries) {
                    if (set.contains(poisoned)) return base
                }
                return null
            }
        }
    }
}
