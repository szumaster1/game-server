package content.global.skill.production.crafting.data

import core.api.consts.Items
import core.game.component.Component
import core.game.node.entity.player.Player
import core.game.node.item.Item

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
        GREEN_D_HIDE_VAMBS(Items.GREEN_D_LEATHER_1745, 1, Items.GREEN_DHIDE_VAMB_1065, 57, 62.0),
        GREEN_D_HIDE_CHAPS(Items.GREEN_D_LEATHER_1745, 2, Items.GREEN_DHIDE_CHAPS_1099, 60, 124.0),
        GREEN_D_HIDE_BODY(Items.GREEN_D_LEATHER_1745, 3, Items.GREEN_DHIDE_BODY_1135, 63, 186.0),
        BLUE_D_HIDE_VAMBS(Items.BLUE_D_LEATHER_2505, 1, Items.BLUE_DHIDE_VAMB_2487, 66, 70.0),
        BLUE_D_HIDE_CHAPS(Items.BLUE_D_LEATHER_2505, 2, Items.BLUE_DHIDE_CHAPS_2493, 68, 140.0),
        BLUE_D_HIDE_BODY(Items.BLUE_D_LEATHER_2505, 3, Items.BLUE_DHIDE_BODY_2499, 71, 210.0),
        RED_D_HIDE_VAMBS(Items.BLACK_D_LEATHER_2509, 1, Items.RED_DHIDE_VAMB_2489, 73, 78.0),
        RED_D_HIDE_CHAPS(Items.BLACK_D_LEATHER_2509, 2, Items.RED_DHIDE_CHAPS_2495, 75, 156.0),
        RED_D_HIDE_BODY(Items.RED_DRAGON_LEATHER_2507, 3, Items.RED_DHIDE_BODY_2501, 77, 234.0),
        BLACK_D_HIDE_VAMBS(Items.BLACK_D_LEATHER_2509, 1, Items.BLACK_DHIDE_VAMB_2491, 79, 86.0),
        BLACK_D_HIDE_CHAPS(Items.BLACK_D_LEATHER_2509, 2, Items.BLACK_DHIDE_CHAPS_2497, 82, 172.0),
        BLACK_D_HIDE_BODY(Items.BLACK_D_LEATHER_2509, 3, Items.BLACK_DHIDE_BODY_2503, 84, 258.0);


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
        ARMOUR(28, 14, 25.0, Item(Items.LEATHER_BODY_1129)),
        GLOVES(29, 1, 13.8, Item(Items.LEATHER_GLOVES_1059)),
        BOOTS(30, 7, 16.3, Item(Items.LEATHER_BOOTS_1061)),
        VAMBRACES(31, 11, 22.0, Item(Items.LEATHER_VAMBRACES_1063)),
        CHAPS(32, 18, 27.0, Item(Items.LEATHER_CHAPS_1095)),
        COIF(33, 38, 37.0, Item(Items.COIF_1169)),
        COWL(34, 9, 18.5, Item(Items.LEATHER_COWL_1167));


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
