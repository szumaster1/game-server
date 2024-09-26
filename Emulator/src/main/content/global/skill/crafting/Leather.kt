package content.global.skill.crafting

import core.game.component.Component
import core.game.node.entity.player.Player
import core.game.node.item.Item
import org.rs.consts.Components
import org.rs.consts.Items

object Leather {
    const val NEEDLE: Int = Items.NEEDLE_1733
    val THREAD: Item = Item(Items.THREAD_1734, 1)
    const val LEATHER: Int = Items.LEATHER_1741
    const val HARD_LEATHER: Int = Items.HARD_LEATHER_1743
    const val GREEN_LEATHER: Int = Items.GREEN_D_LEATHER_1745
    const val BLUE_LEATHER: Int = Items.BLUE_D_LEATHER_2505
    const val RED_LEATHER: Int = Items.RED_DRAGON_LEATHER_2507
    const val BLACK_LEATHER: Int = Items.BLACK_D_LEATHER_2509
    private val COMPONENT = Component(Components.LEATHER_CRAFTING_154)

    @JvmStatic
    fun isLastThread(player: Player): Boolean {
        return getThread(player)?.charge?.let { it >= 1004 } ?: false
    }

    @JvmStatic
    fun decayThread(player: Player) {
        getThread(player)?.let { thread ->
            thread.charge += 1
        }
    }

    @JvmStatic
    fun removeThread(player: Player) {
        if (player.inventory.remove(THREAD)) {
            player.packetDispatch.sendMessage("You use a reel of your thread.")
            getThread(player)?.charge = 1000
        }
    }

    @JvmStatic
    fun getThread(player: Player): Item? {
        return player.inventory[player.inventory.getSlot(THREAD)]
    }

    enum class DragonHide(val leather: Int, val amount: Int, val product: Int, val level: Int, val experience: Double) {
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
                return values().find { it.leather == leather }
            }
        }
    }

    enum class SoftLeather(val button: Int, val level: Int, val experience: Double, val product: Item) {
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
                return values().find { it.button == button }
            }
        }
    }
}