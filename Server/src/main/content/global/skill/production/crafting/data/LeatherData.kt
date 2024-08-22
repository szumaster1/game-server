package content.global.skill.production.crafting.data

import cfg.consts.Items
import core.game.component.Component
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Leather data.
 */
object LeatherData {

    const val NEEDLE: Int = 1733
    @JvmField
    val THREAD: Item = Item(1734, 1)
    const val LEATHER: Int = 1741
    const val HARD_LEATHER: Int = 1743
    const val GREEN_LEATHER: Int = 1745
    const val BLUE_LEATHER: Int = 2505
    const val RED_LEATHER: Int = 2507
    const val BLACK_LEATHER: Int = 2509
    private val COMPONENT = Component(154)


    @JvmStatic
    fun isLastThread(player: Player): Boolean {
        val thread = getThread(player) ?: return false
        val charge = thread.charge
        return charge >= 1004
    }

    @JvmStatic
    fun decayThread(player: Player) {
        val thread = getThread(player) ?: return
        val charge = thread.charge
        thread.charge = charge + 1
    }

    @JvmStatic
    fun removeThread(player: Player) {
        if (player.inventory.remove(THREAD)) {
            player.packetDispatch.sendMessage("You use a reel of your thread.")
            val thread = getThread(player)
            if (thread != null) {
                thread.charge = 1000
            }
        }
    }

    @JvmStatic
    fun getThread(player: Player): Item {
        return player.inventory[player.inventory.getSlot(THREAD)]
    }

    enum class DragonHide(
        @JvmField val leather: Int,
        @JvmField val amount: Int,
        @JvmField val product: Int,
        @JvmField val level: Int,
        @JvmField val experience: Double
    ) {
        GREEN_D_HIDE_VAMBS(
            leather = Items.GREEN_D_LEATHER_1745,
            amount = 1,
            product = Items.GREEN_DHIDE_VAMB_1065,
            level = 57,
            experience = 62.0
        ),
        GREEN_D_HIDE_CHAPS(
            leather = Items.GREEN_D_LEATHER_1745,
            amount = 2,
            product = Items.GREEN_DHIDE_CHAPS_1099,
            level = 60,
            experience = 124.0
        ),
        GREEN_D_HIDE_BODY(
            leather = Items.GREEN_D_LEATHER_1745,
            amount = 3,
            product = Items.GREEN_DHIDE_BODY_1135,
            level = 63,
            experience = 186.0
        ),
        BLUE_D_HIDE_VAMBS(
            leather = Items.BLUE_D_LEATHER_2505,
            amount = 1,
            product = Items.BLUE_DHIDE_VAMB_2487,
            level = 66,
            experience = 70.0
        ),
        BLUE_D_HIDE_CHAPS(
            leather = Items.BLUE_D_LEATHER_2505,
            amount = 2,
            product = Items.BLUE_DHIDE_CHAPS_2493,
            level = 68,
            experience = 140.0
        ),
        BLUE_D_HIDE_BODY(
            leather = Items.BLUE_D_LEATHER_2505,
            amount = 3,
            product = Items.BLUE_DHIDE_BODY_2499,
            level = 71,
            experience = 210.0
        ),
        RED_D_HIDE_VAMBS(
            leather = Items.BLACK_D_LEATHER_2509,
            amount = 1,
            product = Items.RED_DHIDE_VAMB_2489,
            level = 73,
            experience = 78.0
        ),
        RED_D_HIDE_CHAPS(
            leather = Items.BLACK_D_LEATHER_2509,
            amount = 2,
            product = Items.RED_DHIDE_CHAPS_2495,
            level = 75,
            experience = 156.0
        ),
        RED_D_HIDE_BODY(
            leather = Items.RED_DRAGON_LEATHER_2507,
            amount = 3,
            product = Items.RED_DHIDE_BODY_2501,
            level = 77,
            experience = 234.0
        ),
        BLACK_D_HIDE_VAMBS(
            leather = Items.BLACK_D_LEATHER_2509,
            amount = 1,
            product = Items.BLACK_DHIDE_VAMB_2491,
            level = 79,
            experience = 86.0
        ),
        BLACK_D_HIDE_CHAPS(
            leather = Items.BLACK_D_LEATHER_2509,
            amount = 2,
            product = Items.BLACK_DHIDE_CHAPS_2497,
            level = 82,
            experience = 172.0
        ),
        BLACK_D_HIDE_BODY(
            leather = Items.BLACK_D_LEATHER_2509,
            amount = 3,
            product = Items.BLACK_DHIDE_BODY_2503,
            level = 84,
            experience = 258.0
        );


        companion object {

            @JvmStatic
            fun forLeather(leather: Int): DragonHide? {
                for (hide in values()) {
                    if (hide.leather == leather) {
                        return hide
                    }
                }
                return null
            }
        }
    }

    enum class SoftLeather(
        val button: Int,
        @JvmField val level: Int,
        @JvmField val experience: Double,
        @JvmField val product: Item
    ) {
        ARMOUR(
            button = 28,
            level = 14,
            experience = 25.0,
            product = Item(Items.LEATHER_BODY_1129)
        ),
        GLOVES(
            button = 29,
            level = 1,
            experience = 13.8,
            product = Item(Items.LEATHER_GLOVES_1059)
        ),
        BOOTS(
            button = 30,
            level = 7,
            experience = 16.3,
            product = Item(Items.LEATHER_BOOTS_1061)
        ),
        VAMBRACES(
            button = 31,
            level = 11,
            experience = 22.0,
            product = Item(Items.LEATHER_VAMBRACES_1063)
        ),
        CHAPS(
            button = 32,
            level = 18,
            experience = 27.0,
            product = Item(Items.LEATHER_CHAPS_1095)
        ),
        COIF(
            button = 33,
            level = 38,
            experience = 37.0,
            product = Item(Items.COIF_1169)
        ),
        COWL(
            button = 34,
            level = 9,
            experience = 18.5,
            product = Item(Items.LEATHER_COWL_1167)
        );


        companion object {

            @JvmStatic
            fun open(player: Player) {
                player.interfaceManager.open(COMPONENT)
            }

            @JvmStatic
            fun forButton(button: Int): SoftLeather? {
                for (soft in values()) {
                    if (soft.button == button) {
                        return soft
                    }
                }
                return null
            }
        }
    }
}
