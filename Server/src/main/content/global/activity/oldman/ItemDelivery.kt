package content.global.activity.oldman

import core.api.consts.Items
import core.utilities.RandomFunction

enum class ItemDelivery(val item: Int, val min: Int, val max: Int) {
    ANCHOVIES(Items.ANCHOVIES_319, 1,15),
    BALL_OF_WOOL(Items.BALL_OF_WOOL_1759, 1,15),
    BEER_GLASS(Items.BEER_GLASS_1919, 1,15),
    BONES(Items.BONES_526, 1,15),
    BREAD(Items.BREAD_2309, 1,15),
    BRONZE_ARROW(Items.BRONZE_ARROW_882, 1,15),
    BRONZE_BAR(Items.BRONZE_BAR_2349, 1,15),
    BRONZE_DAGGER(Items.BRONZE_DAGGER_1205, 1,15),
    BRONZE_AXE(Items.BRONZE_AXE_1351, 1,15),
    BRONZE_FULL_HELM(Items.BRONZE_FULL_HELM_1155, 1,15),
    BRONZE_KNIFE(Items.BRONZE_KNIFE_864, 1,15),
    BRONZE_MACE(Items.BRONZE_MACE_1422, 1,15),
    BRONZE_MEDIUM_HELM(Items.BRONZE_MED_HELM_1139, 1,15),
    BRONZE_SPEAR(Items.BRONZE_SPEAR_1237, 1,15),
    BRONZE_SWORD(Items.BRONZE_SWORD_1277, 1,15),
    BRONZE_WARHAMMER(Items.BRONZE_WARHAMMER_1337, 1,15),
    BRONZE_WIRE(Items.BRONZE_WIRE_1794, 1,15),
    BEER(Items.BEER_1917, 1,15),
    BOWSTRING(Items.BOW_STRING_1777, 1,15),
    CADAVA_BERRIES(Items.CADAVA_BERRIES_753, 1,15),
    COOKED_CHICKEN(Items.COOKED_CHICKEN_2140, 1,15),
    COOKED_MEAT(Items.COOKED_MEAT_2142, 1,15),
    COPPER_ORE(Items.COPPER_ORE_436, 1,15),
    COWHIDE(Items.COWHIDE_1739, 1,15),
    EGG(Items.EGG_1944, 1,15),
    FEATHERS(Items.FEATHER_314, 1,15),
    GRAIN(Items.GRAIN_1947, 1,15),
    HEADLESS_ARROWS(Items.HEADLESS_ARROW_53, 1,15),
    IRON_ARROWTIPS(Items.IRON_ARROWTIPS_40, 1,15),
    IRON_MACE(Items.IRON_MACE_1420, 1,15),
    IRON_ORE(Items.IRON_ORE_440, 1,15),
    IRON_THROWING_KNIVES(Items.IRON_THROWNAXE_801, 1,15),
    IRON_WARHAMMER(Items.IRON_WARHAMMER_1335, 1,15),
    LEATHER_BOOTS(Items.LEATHER_BOOTS_1061, 1,15),
    LEATHER_COWL(Items.LEATHER_COWL_1167, 1,15),
    LEATHER_GLOVES(Items.LEATHER_GLOVES_1059, 1,15),
    LOGS(Items.LOGS_1511, 1,15),
    MOLTEN_GLASS(Items.MOLTEN_GLASS_1775, 1,15),
    POT_OF_FLOUR(Items.POT_OF_FLOUR_1933, 1,15),
    POTATOES(Items.POTATO_1942, 1,15),
    RAW_ANCHOVIES(Items.RAW_ANCHOVIES_321, 1,15),
    RAW_RAT_MEAT(Items.RAW_RAT_MEAT_2134, 1,15),
    RUNE_ESSENCE(Items.RUNE_ESSENCE_1436, 1,15),
    PURE_ESSENCE(Items.PURE_ESSENCE_7936, 1,15),
    SHRIMP(Items.SHRIMPS_315, 1,15),
    SILK(Items.SILK_950, 1,15),
    SOFT_CLAY(Items.SOFT_CLAY_1761,1,15);

    val amount = RandomFunction.random(min, max)

    companion object {
        val deliveryMap = HashMap<Int, ItemDelivery>()


        init {
            for (delivery in ItemDelivery.values()) {
                deliveryMap[delivery.item] = delivery
            }

            for (amount in ItemDelivery.values()){
                deliveryMap[amount.item] = amount
            }
        }

        fun forId(task: Int): ItemDelivery? {
            for (id in ItemDelivery.values()) {
                if (id.item == task) {
                    return id
                }
            }
            return null
        }

    }

}
