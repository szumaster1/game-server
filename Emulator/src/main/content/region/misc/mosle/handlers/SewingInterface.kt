package content.region.misc.mosle.handlers

import core.api.*
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import org.rs.consts.Components
import org.rs.consts.Items

/**
 * Represents the Sewing interface.
 */
class SewingInterface : InterfaceListener {

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
                155 -> create(player, pirateStock)
            }
            return@on true
        }
    }

    /**
     * Function allows you to combine the two items into one expected product.
     *
     * @param player The player who is attempting to buy the stock.
     * @param stock The stock of pirate clothes that the player wants to create.
     */
    private fun create(player: Player, stock: PirateClothes) {
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

    /**
     * Represents the Pirate clothes.
     *
     * @param firstItem the first required item.
     * @param secondItem the second required item.
     * @param product the product.
     * @param buttonId button presenting a given product.
     * @return a created product.
     */
    enum class PirateClothes(val firstItem: Int, val secondItem: Int, val product: Item, val buttonId: Int) {
        /**
         * White Right Eye
         *
         * @constructor White Right Eye
         */
        WHITE_RIGHT_EYE(
            firstItem = Items.PIRATE_BANDANA_7112,
            secondItem = Items.EYE_PATCH_1025,
            product = Item(Items.BANDANA_AND_EYEPATCH_8924),
            buttonId = 38
        ),

        /**
         * White Double Eye
         *
         * @constructor White Double Eye
         */
        WHITE_DOUBLE_EYE(
            firstItem = Items.PIRATE_BANDANA_7112,
            secondItem = Items.DOUBLE_EYEPATCHES_13353,
            product = Item(Items.BANDANA_AND_EYEPATCHES_13340),
            buttonId = 66
        ),

        /**
         * White Left Eye
         *
         * @constructor White Left Eye
         */
        WHITE_LEFT_EYE(
            firstItem = Items.PIRATE_BANDANA_7112,
            secondItem = Items.LEFT_EYEPATCH_13355,
            product = Item(Items.BANDANA_AND_EYEPATCH_13339),
            buttonId = 68
        ),

        /**
         * Red Right Eye
         *
         * @constructor Red Right Eye
         */
        RED_RIGHT_EYE(
            firstItem = Items.PIRATE_BANDANA_7124,
            secondItem = Items.EYE_PATCH_1025,
            product = Item(Items.BANDANA_AND_EYEPATCH_8925),
            buttonId = 40
        ),

        /**
         * Red Double Eye
         *
         * @constructor Red Double Eye
         */
        RED_DOUBLE_EYE(
            firstItem = Items.PIRATE_BANDANA_7124,
            secondItem = Items.DOUBLE_EYEPATCHES_13353,
            product = Item(Items.BANDANA_AND_EYEPATCHES_13342),
            buttonId = 60
        ),

        /**
         * Red Left Eye
         *
         * @constructor Red Left Eye
         */
        RED_LEFT_EYE(
            firstItem = Items.PIRATE_BANDANA_7124,
            secondItem = Items.LEFT_EYEPATCH_13355,
            product = Item(Items.BANDANA_AND_EYEPATCH_13341),
            buttonId = 70
        ),

        /**
         * Blue Right Eye
         *
         * @constructor Blue Right Eye
         */
        BLUE_RIGHT_EYE(
            firstItem = Items.PIRATE_BANDANA_7130,
            secondItem = Items.EYE_PATCH_1025,
            product = Item(Items.BANDANA_AND_EYEPATCH_8926),
            buttonId = 42
        ),

        /**
         * Blue Double Eye
         *
         * @constructor Blue Double Eye
         */
        BLUE_DOUBLE_EYE(
            firstItem = Items.PIRATE_BANDANA_7130,
            secondItem = Items.DOUBLE_EYEPATCHES_13353,
            product = Item(Items.BANDANA_AND_EYEPATCHES_13344),
            buttonId = 64
        ),

        /**
         * Blue Left Eye
         *
         * @constructor Blue Left Eye
         */
        BLUE_LEFT_EYE(
            firstItem = Items.PIRATE_BANDANA_7130,
            secondItem = Items.LEFT_EYEPATCH_13355,
            product = Item(Items.BANDANA_AND_EYEPATCH_13343),
            buttonId = 72
        ),

        /**
         * Brown Right Eye
         *
         * @constructor Brown Right Eye
         */
        BROWN_RIGHT_EYE(
            firstItem = Items.PIRATE_BANDANA_7136,
            secondItem = Items.EYE_PATCH_1025,
            product = Item(Items.BANDANA_AND_EYEPATCH_8927),
            buttonId = 44
        ),

        /**
         * Brown Double Eye
         *
         * @constructor Brown Double Eye
         */
        BROWN_DOUBLE_EYE(
            firstItem = Items.PIRATE_BANDANA_7136,
            secondItem = Items.DOUBLE_EYEPATCHES_13353,
            product = Item(Items.BANDANA_AND_EYEPATCHES_13346),
            buttonId = 62
        ),

        /**
         * Brown Left Eye
         *
         * @constructor Brown Left Eye
         */
        BROWN_LEFT_EYE(
            firstItem = Items.PIRATE_BANDANA_7136,
            secondItem = Items.LEFT_EYEPATCH_13355,
            product = Item(Items.BANDANA_AND_EYEPATCH_13345),
            buttonId = 74
        ),

        /**
         * Grey Right Eye
         *
         * @constructor Grey Right Eye
         */
        GREY_RIGHT_EYE(
            firstItem = Items.BANDANA_13370,
            secondItem = Items.EYE_PATCH_1025,
            product = Item(Items.BANDANA_AND_EYEPATCH_13378),
            buttonId = 46
        ),

        /**
         * Grey Left Eye
         *
         * @constructor Grey Left Eye
         */
        GREY_LEFT_EYE(
            firstItem = Items.BANDANA_13370,
            secondItem = Items.LEFT_EYEPATCH_13355,
            product = Item(Items.BANDANA_AND_EYEPATCH_13347),
            buttonId = 76
        ),

        /**
         * Grey Double Eye
         *
         * @constructor Grey Double Eye
         */
        GREY_DOUBLE_EYE(
            firstItem = Items.BANDANA_13370,
            secondItem = Items.DOUBLE_EYEPATCHES_13353,
            product = Item(Items.BANDANA_AND_EYEPATCHES_13348),
            buttonId = 52
        ),

        /**
         * Purple Right Eye
         *
         * @constructor Purple Right Eye
         */
        PURPLE_RIGHT_EYE(
            firstItem = Items.BANDANA_13372,
            secondItem = Items.EYE_PATCH_1025,
            product = Item(Items.BANDANA_AND_EYEPATCH_13376),
            buttonId = 48
        ),

        /**
         * Purple Left Eye
         *
         * @constructor Purple Left Eye
         */
        PURPLE_LEFT_EYE(
            firstItem = Items.BANDANA_13372,
            secondItem = Items.LEFT_EYEPATCH_13355,
            product = Item(Items.BANDANA_AND_EYEPATCH_13349),
            buttonId = 78
        ),

        /**
         * Purple Double Eye
         *
         * @constructor Purple Double Eye
         */
        PURPLE_DOUBLE_EYE(
            firstItem = Items.BANDANA_13372,
            secondItem = Items.DOUBLE_EYEPATCHES_13353,
            product = Item(Items.BANDANA_AND_EYEPATCHES_13350),
            buttonId = 54
        ),

        /**
         * Orange Right Eye
         *
         * @constructor Orange Right Eye
         */
        ORANGE_RIGHT_EYE(
            firstItem = Items.BANDANA_13374,
            secondItem = Items.BANDANA_13374,
            product = Item(Items.BANDANA_AND_EYEPATCH_13377),
            buttonId = 50
        ),

        /**
         * Orange Double Eye
         *
         * @constructor Orange Double Eye
         */
        ORANGE_DOUBLE_EYE(
            firstItem = Items.BANDANA_13374,
            secondItem = Items.DOUBLE_EYEPATCHES_13353,
            product = Item(Items.BANDANA_AND_EYEPATCH_13351),
            buttonId = 56
        ),

        /**
         * Orange Left Double Eye
         *
         * @constructor Orange Left Double Eye
         */
        ORANGE_LEFT_DOUBLE_EYE(
            firstItem = Items.BANDANA_13374,
            secondItem = Items.LEFT_EYEPATCH_13355,
            product = Item(Items.BANDANA_AND_EYEPATCHES_13352),
            buttonId = 80
        ),

        /**
         * Pirate Hat Right Eye
         *
         * @constructor Pirate Hat Right Eye
         */
        PIRATE_HAT_RIGHT_EYE(
            firstItem = Items.PIRATES_HAT_2651,
            secondItem = Items.EYE_PATCH_1025,
            product = Item(Items.HAT_AND_EYEPATCH_8928),
            buttonId = 82
        ),

        /**
         * Pirate Hat Double Eye
         *
         * @constructor Pirate Hat Double Eye
         */
        PIRATE_HAT_DOUBLE_EYE(
            firstItem = Items.PIRATES_HAT_2651,
            secondItem = Items.DOUBLE_EYEPATCHES_13353,
            product = Item(Items.PIRATE_HAT_AND_EYEPATCHES_13354),
            buttonId = 58
        ),

        /**
         * Pirate Hat Left Eye
         *
         * @constructor Pirate Hat Left Eye
         */
        PIRATE_HAT_LEFT_EYE(
            firstItem = Items.PIRATES_HAT_2651,
            secondItem = Items.LEFT_EYEPATCH_13355,
            product = Item(Items.PIRATE_HAT_AND_EYEPATCH_13357),
            buttonId = 84
        ),

        /**
         * Double Patch
         *
         * @constructor Double Patch
         */
        DOUBLE_PATCH(
            firstItem = Items.LEFT_EYEPATCH_13355,
            secondItem = Items.EYE_PATCH_1025,
            product = Item(Items.DOUBLE_EYEPATCHES_13353),
            buttonId = 86
        ),

        /**
         * Crab Hand
         *
         * @constructor Crab Hand
         */
        CRAB_HAND(
            firstItem = Items.CRAB_CLAW_7537,
            secondItem = Items.PIRATES_HOOK_2997,
            product = Item(Items.CRABCLAW_AND_HOOK_8929),
            buttonId = 88
        ),

        /**
         * Cavalier And Mask
         *
         * @constructor Cavalier And Mask
         */
        CAVALIER_AND_MASK(
            firstItem = Items.HIGHWAYMAN_MASK_2631,
            secondItem = Items.BLACK_CAVALIER_2643,
            product = Item(Items.CAVALIER_AND_MASK_11280),
            buttonId = 90
        ),

        /**
         * Beret And Mask
         *
         * @constructor Beret And Mask
         */
        BERET_AND_MASK(
            firstItem = Items.MIME_MASK_3057,
            secondItem = Items.BLACK_BERET_2635,
            product = Item(Items.BERET_AND_MASK_11282),
            buttonId = 92
        );

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
