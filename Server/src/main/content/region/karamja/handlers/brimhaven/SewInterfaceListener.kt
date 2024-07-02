package content.region.karamja.handlers.brimhaven

import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.Player
import core.game.node.item.Item

class SewInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.SEW_INTERFACE_419) { player, _, opcode, button, _, _ ->
            val pirateStock: PirateClothes? = when (button) {
                38 -> PirateClothes.WHITE_RIGHT_EYE
                66 -> PirateClothes.WHITE_DOUBLE_EYE
                68 -> PirateClothes.WHITE_LEFT_EYE
                40 -> PirateClothes.RED_RIGHT_EYE
                60 -> PirateClothes.RED_DOUBLE_EYE
                70 -> PirateClothes.RED_LEFT_EYE
                42 -> PirateClothes.BLUE_RIGHT_EYE
                64 -> PirateClothes.BLUE_DOUBLE_EYE
                72 -> PirateClothes.BLUE_LEFT_EYE
                44 -> PirateClothes.BROWN_RIGHT_EYE
                62 -> PirateClothes.BROWN_DOUBLE_EYE
                74 -> PirateClothes.BROWN_LEFT_EYE
                46 -> PirateClothes.GREY_RIGHT_EYE
                76 -> PirateClothes.GREY_LEFT_EYE
                52 -> PirateClothes.GREY_DOUBLE_EYE
                48 -> PirateClothes.PURPLE_RIGHT_EYE
                78 -> PirateClothes.PURPLE_LEFT_EYE
                54 -> PirateClothes.PURPLE_DOUBLE_EYE
                50 -> PirateClothes.ORANGE_RIGHT_EYE
                56 -> PirateClothes.ORANGE_DOUBLE_EYE
                80 -> PirateClothes.ORANGE_LEFT_DOUBLE_EYE
                82 -> PirateClothes.PIRATE_HAT_RIGHT_EYE
                58 -> PirateClothes.PIRATE_HAT_DOUBLE_EYE
                84 -> PirateClothes.PIRATE_HAT_LEFT_EYE
                86 -> PirateClothes.DOUBLE_PATCH
                88 -> PirateClothes.CRAB_HAND
                90 -> PirateClothes.CAVALIER_AND_MASK
                92 -> PirateClothes.BERET_AND_MASK
                else -> null
            }

            pirateStock ?: return@on false

            when (opcode) {
                155 -> buy(player, pirateStock)
            }
            return@on true
        }
    }


    private fun buy(player: Player, stock: PirateClothes) {
        if (freeSlots(player) < 1) {
            sendMessage(player, "You don't have enough inventory space for this.")
            return
        }

        if (amountInInventory(player, Items.COINS_995) < 500) {
            sendDialogue(player, "You can't afford that.")
            return
        }

        if (!inInventory(player, stock.firstItem) || !inInventory(player, stock.secondItem)) {
            sendDialogue(player, "You don't have required items in your inventory.")
            return
        }

        removeItem(player, stock.firstItem, Container.INVENTORY)
        removeItem(player, stock.secondItem, Container.INVENTORY)
        removeItem(player, Item(Items.COINS_995, 500), Container.INVENTORY)
        addItem(player, stock.product.id, 1)
    }



    enum class PirateClothes(val firstItem: Int, val secondItem: Int, val product: Item, val buttonId: Int) {
        WHITE_RIGHT_EYE(Items.PIRATE_BANDANA_7112, Items.EYE_PATCH_1025, Item(Items.BANDANA_AND_EYEPATCH_8924), 38),
        WHITE_DOUBLE_EYE(Items.PIRATE_BANDANA_7112, Items.DOUBLE_EYEPATCHES_13353, Item(Items.BANDANA_AND_EYEPATCHES_13340), 66),
        WHITE_LEFT_EYE(Items.PIRATE_BANDANA_7112, Items.LEFT_EYEPATCH_13355, Item(Items.BANDANA_AND_EYEPATCH_13339), 68),
        RED_RIGHT_EYE(Items.PIRATE_BANDANA_7124, Items.EYE_PATCH_1025, Item(Items.BANDANA_AND_EYEPATCH_8925), 40),
        RED_DOUBLE_EYE(Items.PIRATE_BANDANA_7124, Items.DOUBLE_EYEPATCHES_13353, Item(Items.BANDANA_AND_EYEPATCHES_13342), 60),
        RED_LEFT_EYE(Items.PIRATE_BANDANA_7124, Items.LEFT_EYEPATCH_13355, Item(Items.BANDANA_AND_EYEPATCH_13341), 70),
        BLUE_RIGHT_EYE(Items.PIRATE_BANDANA_7130, Items.EYE_PATCH_1025, Item(Items.BANDANA_AND_EYEPATCH_8926), 42),
        BLUE_DOUBLE_EYE(Items.PIRATE_BANDANA_7130, Items.DOUBLE_EYEPATCHES_13353, Item(Items.BANDANA_AND_EYEPATCHES_13344), 64),
        BLUE_LEFT_EYE(Items.PIRATE_BANDANA_7130, Items.LEFT_EYEPATCH_13355, Item(Items.BANDANA_AND_EYEPATCH_13343), 72),
        BROWN_RIGHT_EYE(Items.PIRATE_BANDANA_7136, Items.EYE_PATCH_1025, Item(Items.BANDANA_AND_EYEPATCH_8927), 44),
        BROWN_DOUBLE_EYE(Items.PIRATE_BANDANA_7136, Items.DOUBLE_EYEPATCHES_13353, Item(Items.BANDANA_AND_EYEPATCHES_13346), 62),
        BROWN_LEFT_EYE(Items.PIRATE_BANDANA_7136, Items.LEFT_EYEPATCH_13355, Item(Items.BANDANA_AND_EYEPATCH_13345), 74),
        GREY_RIGHT_EYE(Items.BANDANA_13370, Items.EYE_PATCH_1025, Item(Items.BANDANA_AND_EYEPATCH_13378), 46),
        GREY_LEFT_EYE(Items.BANDANA_13370, Items.LEFT_EYEPATCH_13355, Item(Items.BANDANA_AND_EYEPATCH_13347), 76),
        GREY_DOUBLE_EYE(Items.BANDANA_13370, Items.DOUBLE_EYEPATCHES_13353, Item(Items.BANDANA_AND_EYEPATCHES_13348), 52),
        PURPLE_RIGHT_EYE(Items.BANDANA_13372, Items.EYE_PATCH_1025, Item(Items.BANDANA_AND_EYEPATCH_13376), 48),
        PURPLE_LEFT_EYE(Items.BANDANA_13372, Items.LEFT_EYEPATCH_13355, Item(Items.BANDANA_AND_EYEPATCH_13349), 78),
        PURPLE_DOUBLE_EYE(Items.BANDANA_13372, Items.DOUBLE_EYEPATCHES_13353, Item(Items.BANDANA_AND_EYEPATCHES_13350), 54),
        ORANGE_RIGHT_EYE(Items.BANDANA_13374, Items.BANDANA_13374, Item(Items.BANDANA_AND_EYEPATCH_13377), 50),
        ORANGE_DOUBLE_EYE(Items.BANDANA_13374, Items.DOUBLE_EYEPATCHES_13353, Item(Items.BANDANA_AND_EYEPATCH_13351), 56),
        ORANGE_LEFT_DOUBLE_EYE(Items.BANDANA_13374, Items.LEFT_EYEPATCH_13355, Item(Items.BANDANA_AND_EYEPATCHES_13352), 80),
        PIRATE_HAT_RIGHT_EYE(Items.PIRATES_HAT_2651, Items.EYE_PATCH_1025, Item(Items.HAT_AND_EYEPATCH_8928), 82),
        PIRATE_HAT_DOUBLE_EYE(Items.PIRATES_HAT_2651, Items.DOUBLE_EYEPATCHES_13353, Item(Items.PIRATE_HAT_AND_EYEPATCHES_13354), 58),
        PIRATE_HAT_LEFT_EYE(Items.PIRATES_HAT_2651, Items.LEFT_EYEPATCH_13355, Item(Items.PIRATE_HAT_AND_EYEPATCH_13357), 84),
        DOUBLE_PATCH(Items.LEFT_EYEPATCH_13355, Items.EYE_PATCH_1025, Item(Items.DOUBLE_EYEPATCHES_13353), 86),
        CRAB_HAND(Items.CRAB_CLAW_7537, Items.PIRATES_HOOK_2997, Item(Items.CRABCLAW_AND_HOOK_8929), 88),
        CAVALIER_AND_MASK(Items.HIGHWAYMAN_MASK_2631, Items.BLACK_CAVALIER_2643, Item(Items.CAVALIER_AND_MASK_11280), 90),
        BERET_AND_MASK(Items.MIME_MASK_3057, Items.BLACK_BERET_2635, Item(Items.BERET_AND_MASK_11282), 92);

        companion object {
            val productMap = HashMap<Int, PirateClothes>()

            init {
                for (product in PirateClothes.values()) {
                    productMap[product.product.id] = product
                }
            }
        }

    }

}
