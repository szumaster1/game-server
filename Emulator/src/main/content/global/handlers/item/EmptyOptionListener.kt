package content.global.handlers.item

import core.api.playAudio
import core.api.replaceSlot
import core.api.sendMessage
import core.cache.def.impl.ItemDefinition
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import org.rs.consts.Items
import org.rs.consts.Sounds

class EmptyOptionListener : InteractionListener {

    override fun defineListeners() {
        /*
         * A collection that allows you to empty containers such as vials, buckets,
         * pots.
         */

        on(EmptyItem.emptyItemList.toIntArray(), IntType.ITEM, "empty", "empty bowl", "empty dish") { player, node ->
            if (node.name.contains("brew") || node.name.contains("potion") || node.name.lowercase().contains("poison") || node.name.lowercase().contains("serum") || node.name.contains("cure") || node.name.contains("mix") || node.name.contains("balm") ) {
                replaceSlot(player, node.asItem().slot, Item(EmptyItem.getEmpty(Items.POTION_195)!!), node.asItem())
                playAudio(player, EmptyItem.getEmptyAudio(Items.POTION_195)!!)
                return@on true
            }
            if (EmptyItem.emptyItemMap[node.id] != null) {
                replaceSlot(player, node.asItem().slot, Item(EmptyItem.getEmpty(node.id)!!), node.asItem())
                if (EmptyItem.getEmptyAudio(node.id) != -1) playAudio(player, EmptyItem.getEmptyAudio(node.id)!!)
                EmptyItem.getEmptyMessage(node.id)?.let { sendMessage(player, it) }
            }
            return@on true
        }
    }

    /**
     * Represents the empty item data.
     *
     * @param [fullId]        the id for the full item.
     * @param [emptyId]       the id for the empty item.
     * @param [emptyMessage]  the message to display for the empty item.
     * @param [audioId]       the id for the audio associated with the item (default is -1 if no audio).
     */
    enum class EmptyItem(var fullId: Int, var emptyId: Int, var emptyMessage: String, var audioId: Int = -1) {
        BONE_MEAL(Items.BONEMEAL_4255, Items.EMPTY_POT_1931, "You empty the pot of crushed bones."),
        BOWL_OF_HOT_WATER(Items.BOWL_OF_HOT_WATER_4456, Items.BOWL_1923, "You empty the bowl of hot water onto the floor."),
        BOWL_OF_WATER(Items.BOWL_OF_WATER_1921, Items.BOWL_1923, "You empty the contents of the bowl onto the floor.", Sounds.LIQUID_2401),
        BUCKET_OF_COMPOST(Items.COMPOST_6032, Items.BUCKET_1925, "You empty the bucket of compost."),
        BUCKET_OF_MILK(Items.BUCKET_OF_MILK_1927, Items.BUCKET_1925, "You empty the contents of the bucket onto the floor.", Sounds.LIQUID_2401),
        BUCKET_OF_SAND(Items.BUCKET_OF_SAND_1783, Items.BUCKET_1925, "You empty the contents of the bucket onto the floor.", Sounds.SAND_BUCKET_2584),
        BUCKET_OF_SLIME(Items.BUCKET_OF_SLIME_4286, Items.BUCKET_1925, "You empty the contents of the bucket on the floor.", Sounds.LIQUID_2401),
        BUCKET_OF_SUPERCOMPOST(Items.SUPERCOMPOST_6034, Items.BUCKET_1925, "You empty the bucket of supercompost."),
        BUCKET_OF_WATER(Items.BUCKET_OF_WATER_1929, Items.BUCKET_1925, "You empty the contents of the bucket onto the floor.", Sounds.LIQUID_2401),
        BURNT_CURRY(Items.BURNT_CURRY_2013, Items.BOWL_1923, "You empty the contents of the bowl onto the floor.", Sounds.LIQUID_2401),
        BURNT_EGG(Items.BURNT_EGG_7090, Items.BOWL_1923, "You empty the contents of the bowl onto the floor."),
        BURNT_GNOMEBOWL(Items.BURNT_GNOMEBOWL_2175, Items.GNOMEBOWL_MOULD_2166, "You empty the contents of the gnomebowl onto the floor."),
        BURNT_MUSHROOM(Items.BURNT_MUSHROOM_7094, Items.BOWL_1923, "You empty the contents of the bowl onto the floor."),
        BURNT_ONION(Items.BURNT_ONION_7092, Items.BOWL_1923, "You empty the contents of the bowl onto the floor."),
        BURNT_PIE(Items.BURNT_PIE_2329, Items.PIE_DISH_2313, "You empty the pie dish."),
        BURNT_STEW(Items.BURNT_STEW_2005, Items.BOWL_1923, "You empty the contents of the bowl onto the floor.", Sounds.LIQUID_2401),
        CUP_OF_HOT_WATER(Items.CUP_OF_HOT_WATER_4460, Items.EMPTY_CUP_1980,"You empty the cup of hot water onto the floor."),
        HERB_TEA_0(Items.HERB_TEA_MIX_4464, Items.EMPTY_CUP_1980, "You empty the cup."),
        HERB_TEA_1(Items.HERB_TEA_MIX_4466, Items.EMPTY_CUP_1980, "You empty the cup."),
        HERB_TEA_2(Items.HERB_TEA_MIX_4468, Items.EMPTY_CUP_1980, "You empty the cup."),
        HERB_TEA_3(Items.HERB_TEA_MIX_4470, Items.EMPTY_CUP_1980, "You empty the cup."),
        HERB_TEA_4(Items.HERB_TEA_MIX_4472, Items.EMPTY_CUP_1980, "You empty the cup."),
        HERB_TEA_5(Items.HERB_TEA_MIX_4474, Items.EMPTY_CUP_1980, "You empty the cup."),
        HERB_TEA_6(Items.HERB_TEA_MIX_4476, Items.EMPTY_CUP_1980, "You empty the cup."),
        HERB_TEA_7(Items.HERB_TEA_MIX_4478, Items.EMPTY_CUP_1980, "You empty the cup."),
        HERB_TEA_8(Items.HERB_TEA_MIX_4480, Items.EMPTY_CUP_1980, "You empty the cup."),
        HERB_TEA_9(Items.HERB_TEA_MIX_4482, Items.EMPTY_CUP_1980, "You empty the cup."),
        JUG_OF_WATER(Items.JUG_OF_WATER_1937, Items.JUG_1935, "You empty the contents of the jug onto the floor.", Sounds.LIQUID_2401),
        NETTLE_TEA(Items.NETTLE_TEA_4239, Items.BOWL_1923, "You empty the contents of the bowl onto the floor.", Sounds.LIQUID_2401),
        NETTLE_TEA_CUP(Items.CUP_OF_TEA_4242, Items.EMPTY_CUP_1980, "You empty the cup."),
        NETTLE_TEA_MILKY(Items.NETTLE_TEA_4240, Items.BOWL_1923, "You empty the contents of the bowl onto the floor.", Sounds.LIQUID_2401),
        NETTLE_TEA_MILKY_P_CUP(Items.CUP_OF_TEA_4246, Items.PORCELAIN_CUP_4244, "You empty the porcelain cup."),
        NETTLE_TEA_P_CUP(Items.CUP_OF_TEA_4245, Items.PORCELAIN_CUP_4244, "You empty the porcelain cup."),
        NETTLE_WATER(Items.NETTLE_WATER_4237, Items.BOWL_1923, "You empty the contents of the bowl onto the floor.", Sounds.LIQUID_2401),
        POTION(Items.POTION_195, Items.VIAL_229, "You empty the vial.", Sounds.LIQUID_2401),
        POT_OF_CORNFLOUR(Items.POT_OF_CORNFLOUR_7468, Items.EMPTY_POT_1931, "You empty the contents of the pot onto the floor."),
        POT_OF_FLOUR(Items.POT_OF_FLOUR_1933, Items.EMPTY_POT_1931, "You empty the contents of the pot onto the floor."),
        VIAL_OF_COCONUT(Items.COCONUT_MILK_5935, Items.VIAL_229, "You empty the vial.", Sounds.LIQUID_2401),
        VIAL_OF_WATER(Items.VIAL_OF_WATER_227, Items.VIAL_229, "You empty the vial.", Sounds.LIQUID_2401);

        companion object {
            var emptyItemMap = HashMap<Int, Int?>()
            var emptyMessageMap = HashMap<Int, String>()
            var emptyAudioMap = HashMap<Int, Int>()
            var emptyItemList = ArrayList<Int>()

            init {
                for (item in values()) {
                    emptyItemMap.putIfAbsent(item.fullId, item.emptyId)
                    emptyMessageMap.putIfAbsent(item.fullId, item.emptyMessage)
                    emptyAudioMap.putIfAbsent(item.fullId, item.audioId)
                    emptyItemList.add(item.fullId)
                }
                for (item in ItemDefinition.getDefinitions().values) if (item.name.contains("potion") || item.name.contains("brew") || item.name.contains("poison") || item.name.lowercase().contains("serum") || item.name.contains("cure") || item.name.contains("mix") || item.name.contains("balm")) {
                    emptyItemList.add(item.id)
                }
            }

            fun getEmpty(id: Int): Int? {
                return emptyItemMap[id]
            }

            fun getEmptyMessage(id: Int): String? {
                return emptyMessageMap[id]
            }

            fun getEmptyAudio(id: Int): Int? {
                return emptyAudioMap[id]
            }
        }
    }

}
