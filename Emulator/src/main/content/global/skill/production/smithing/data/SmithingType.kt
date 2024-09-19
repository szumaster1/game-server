package content.global.skill.production.smithing.data

import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Represents a type of smithing.
 */
enum class SmithingType(val bar: Int, val base: Int, val nameId: Int, val button: IntArray, val amount: Int) {
    TYPE_DAGGER(
        bar = 1,
        base = 18,
        nameId = 19,
        button = intArrayOf(24, 23, 22, 21),
        amount = 1
    ),
    TYPE_AXE(
        bar = 1,
        base = 26,
        nameId = 27,
        button = intArrayOf(32, 31, 30, 29),
        amount = 1
    ),
    TYPE_MACE(
        bar = 1,
        base = 34,
        nameId = 35,
        button = intArrayOf(40, 39, 38, 37),
        amount = 1
    ),
    TYPE_MEDIUM_HELM(
        bar = 1,
        base = 42,
        nameId = 43,
        button = intArrayOf(48, 47, 46, 45),
        amount = 1
    ),
    TYPE_CROSSBOW_BOLT(
        bar = 1,
        base = 50,
        nameId = 51,
        button = intArrayOf(56, 55, 54, 53),
        amount = 10
    ),
    TYPE_SWORD(
        bar = 1,
        base = 58,
        nameId = 59,
        button = intArrayOf(64, 63, 62, 61),
        amount = 1
    ),
    TYPE_DART_TIP(
        bar = 1,
        base = 66,
        nameId = 67,
        button = intArrayOf(72, 71, 70, 69),
        amount = 10
    ),
    TYPE_NAIL(
        bar = 1,
        base = 74,
        nameId = 75,
        button = intArrayOf(80, 79, 78, 77),
        amount = 15
    ),
    TYPE_BULLSEYE(
        bar = 1,
        base = 162,
        nameId = 163,
        button = intArrayOf(168, 167, 166, 165),
        amount = 1
    ),
    TYPE_SPIT_IRON(
        bar = 1,
        base = 90,
        nameId = 91,
        button = intArrayOf(96, 95, 94, 93),
        amount = 1
    ),
    TYPE_WIRE(
        bar = 1,
        base = 82,
        nameId = 83,
        button = intArrayOf(88, 87, 86, 85),
        amount = 1
    ),
    TYPE_ARROW_TIP(
        bar = 1,
        base = 106,
        nameId = 107,
        button = intArrayOf(112, 111, 110, 109),
        amount = 15
    ),
    TYPE_SCIMITAR(
        bar = 2,
        base = 114,
        nameId = 115,
        button = intArrayOf(120, 119, 118, 117),
        amount = 1
    ),
    TYPE_CROSSBOW_LIMB(
        bar = 1,
        base = 122,
        nameId = 123,
        button = intArrayOf(128, 127, 126, 125),
        amount = 1
    ),
    TYPE_LONGSWORD(
        bar = 2,
        base = 130,
        nameId = 131,
        button = intArrayOf(136, 135, 134, 133),
        amount = 1
    ),
    TYPE_THROWING_KNIFE(
        bar = 1,
        base = 138,
        nameId = 139,
        button = intArrayOf(144, 143, 142, 141),
        amount = 5
    ),
    TYPE_FULL_HELM(
        bar = 2,
        base = 146,
        nameId = 147,
        button = intArrayOf(152, 151, 150, 149),
        amount = 1
    ),
    TYPE_SQUARE_SHIELD(
        bar = 2,
        base = 154,
        nameId = 155,
        button = intArrayOf(160, 159, 158, 157),
        amount = 1
    ),
    TYPE_LANTERN(
        bar = 1,
        base = 162,
        nameId = 163,
        button = intArrayOf(168, 167, 166, 165),
        amount = 1
    ),
    TYPE_OIL_LANTERN(
        bar = 1,
        base = 162,
        nameId = 163,
        button = intArrayOf(168, 167, 166, 165),
        amount = 1
    ),
    TYPE_GRAPPLE_TIP(
        bar = 1,
        base = 170,
        nameId = 171,
        button = intArrayOf(175, 176, 175, 174, 173),
        amount = 1
    ),
    TYPE_STUDS(
        bar = 1,
        base = 98,
        nameId = 99,
        button = intArrayOf(104, 103, 102, 101, 100),
        amount = 1
    ),
    TYPE_WARHAMMER(
        bar = 3,
        base = 178,
        nameId = 179,
        button = intArrayOf(184, 183, 182, 181),
        amount = 1
    ),
    TYPE_BATTLE_AXE(
        bar = 3,
        base = 186,
        nameId = 187,
        button = intArrayOf(192, 191, 190, 189),
        amount = 1
    ),
    TYPE_CHAINBODY(
        bar = 3,
        base = 194,
        nameId = 195,
        button = intArrayOf(200, 199, 198, 197),
        amount = 1
    ),
    TYPE_KITE_SHIELD(
        bar = 3,
        base = 202,
        nameId = 203,
        button = intArrayOf(208, 207, 206, 205),
        amount = 1
    ),
    TYPE_CLAWS(
        bar = 2,
        base = 210,
        nameId = 211,
        button = intArrayOf(216, 215, 214, 213),
        amount = 1
    ),
    TYPE_TWO_HAND_SWORD(
        bar = 3,
        base = 218,
        nameId = 219,
        button = intArrayOf(224, 223, 222, 221),
        amount = 1
    ),
    TYPE_PLATE_SKIRT(
        bar = 3,
        base = 226,
        nameId = 227,
        button = intArrayOf(232, 231, 230, 229),
        amount = 1
    ),
    TYPE_Platelegs(
        bar = 3,
        base = 234,
        nameId = 235,
        button = intArrayOf(240, 239, 238, 237),
        amount = 1
    ),
    TYPE_PLATEBODY(
        bar = 5,
        base = 242,
        nameId = 243,
        button = intArrayOf(248, 247, 246, 245),
        amount = 1
    ),
    TYPE_Crossbow_Bolt(
        bar = 1,
        base = 251,
        nameId = 252,
        button = intArrayOf(257, 256, 255, 254),
        amount = 10
    ),
    TYPE_Crossbow_Limb(
        bar = 1,
        base = 259,
        nameId = 260,
        button = intArrayOf(265, 264, 263, 262),
        amount = 1
    ),
    TYPE_PICKAXE(
        bar = 2,
        base = 267,
        nameId = 268,
        button = intArrayOf(273, 272, 271, 270),
        amount = 1
    );

    companion object {
        /**
         * Gets the items based on the button.
         *
         * @param player the player interacting with the button.
         * @param bar the Bars object that contains smithing types.
         * @param button the button index that the player has pressed.
         * @param item the item to be counted in the player's inventory.
         * @return the count of items based on the button or `-1` if conditions are not met.
         */
        fun forButton(player: Player, bar: Bars?, button: Int, item: Int): Int {
            var count = 0
            if (bar == null) {
                return -1
            }
            for (i in bar.smithingType.button.indices) {
                if (bar.smithingType.button[i] != button) {
                    count++
                } else {
                    when (count) {
                        0 -> {
                            count = 1
                        }

                        1 -> {
                            count = 5
                        }

                        2 -> {
                            count = -1
                        }

                        3 -> {
                            count = player.inventory.getAmount(Item(item))
                        }
                    }
                    return count
                }
            }
            return -1
        }
    }
}
