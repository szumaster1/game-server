package content.region.kandarin.baxtorian.barbariantraining.smithing

import content.global.skill.firemaking.Origami
import org.rs.consts.Items

/**
 * Represents the Barbarian weapons.
 */
enum class BarbarianWeapon(val requiredWood: Int, val requiredBar: Int, val spearId: Int, val hastaId: Int, var amount: Int, var requiredLevel: Int, val experience: Double) {
    BRONZE(
        requiredWood = Items.LOGS_1511,
        requiredBar = Items.BRONZE_BAR_2349,
        spearId = Items.BRONZE_SPEAR_1237,
        hastaId = Items.BRONZE_HASTA_11367,
        amount = 1,
        requiredLevel = 5,
        experience = 25.00
    ),
    IRON(
        requiredWood = Items.OAK_LOGS_1521,
        requiredBar = Items.IRON_BAR_2351,
        spearId = Items.IRON_SPEAR_1239,
        hastaId = Items.IRON_HASTA_11369,
        amount = 1,
        requiredLevel = 20,
        experience = 50.00
    ),
    STEEL(
        requiredWood = Items.WILLOW_LOGS_1519,
        requiredBar = Items.STEEL_BAR_2353,
        spearId = Items.STEEL_SPEAR_1241,
        hastaId = Items.STEEL_HASTA_11371,
        amount = 1,
        requiredLevel = 35,
        experience = 75.00
    ),
    MITHRIL(
        requiredWood = Items.MAPLE_LOGS_1517,
        requiredBar = Items.MITHRIL_BAR_2359,
        spearId = Items.MITHRIL_SPEAR_1243,
        hastaId = Items.MITHRIL_HASTA_11373,
        amount = 1,
        requiredLevel = 55,
        experience = 100.00
    ),
    ADAMANT(
        requiredWood = Items.YEW_LOGS_1515,
        requiredBar = Items.ADAMANTITE_BAR_2361,
        spearId = Items.ADAMANT_SPEAR_1245,
        hastaId = Items.ADAMANT_HASTA_11375,
        amount = 1,
        requiredLevel = 75,
        experience = 125.00
    ),
    RUNE(
        requiredWood = Items.MAGIC_LOGS_1513,
        requiredBar = Items.RUNITE_BAR_2363,
        spearId = Items.RUNE_SPEAR_1247,
        hastaId = Items.RUNE_HASTA_11377,
        amount = 1,
        requiredLevel = 90,
        experience = 150.00
    );

    companion object {
        val weaponMap: MutableMap<Int, BarbarianWeapon> = HashMap()

        @JvmStatic
        fun forId(itemId: Int): Origami? {
            for (product in Origami.values()) {
                if (product.base == itemId) {
                    return product
                }
            }
            return null
        }

        fun getWeapon(id: Int): BarbarianWeapon? {
            return weaponMap[id]
        }
    }
}
