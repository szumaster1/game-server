package content.global.skill.production.smithing.data

import core.game.node.entity.player.Player
import core.game.node.item.Item

enum class SmithingType(
    val requiredBar: Int,
    val childId: Int,
    val nameId: Int,
    val button: IntArray,
    val productAmount: Int
) {
    TYPE_DAGGER(
        requiredBar = 1,
        childId = 18,
        nameId = 19,
        button = intArrayOf(24, 23, 22, 21),
        productAmount = 1
    ),
    TYPE_AXE(
        requiredBar = 1,
        childId = 26,
        nameId = 27,
        button = intArrayOf(32, 31, 30, 29),
        productAmount = 1
    ),
    TYPE_MACE(
        requiredBar = 1,
        childId = 34,
        nameId = 35,
        button = intArrayOf(40, 39, 38, 37),
        productAmount = 1
    ),
    TYPE_MEDIUM_HELM(
        requiredBar = 1,
        childId = 42,
        nameId = 43,
        button = intArrayOf(48, 47, 46, 45),
        productAmount = 1
    ),
    TYPE_CROSSBOW_BOLT(
        requiredBar = 1,
        childId = 50,
        nameId = 51,
        button = intArrayOf(56, 55, 54, 53),
        productAmount = 10
    ),
    TYPE_SWORD(
        requiredBar = 1,
        childId = 58,
        nameId = 59,
        button = intArrayOf(64, 63, 62, 61),
        productAmount = 1
    ),
    TYPE_DART_TIP(
        requiredBar = 1,
        childId = 66,
        nameId = 67,
        button = intArrayOf(72, 71, 70, 69),
        productAmount = 10
    ),
    TYPE_NAIL(
        requiredBar = 1,
        childId = 74,
        nameId = 75,
        button = intArrayOf(80, 79, 78, 77),
        productAmount = 15
    ),
    TYPE_BULLSEYE(
        requiredBar = 1,
        childId = 162,
        nameId = 163,
        button = intArrayOf(168, 167, 166, 165),
        productAmount = 1
    ),
    TYPE_SPIT_IRON(
        requiredBar = 1,
        childId = 90,
        nameId = 91,
        button = intArrayOf(96, 95, 94, 93),
        productAmount = 1
    ),
    TYPE_WIRE(
        requiredBar = 1,
        childId = 82,
        nameId = 83,
        button = intArrayOf(88, 87, 86, 85),
        productAmount = 1
    ),
    TYPE_ARROW_TIP(
        requiredBar = 1,
        childId = 106,
        nameId = 107,
        button = intArrayOf(112, 111, 110, 109),
        productAmount = 15
    ),
    TYPE_SCIMITAR(
        requiredBar = 2,
        childId = 114,
        nameId = 115,
        button = intArrayOf(120, 119, 118, 117),
        productAmount = 1
    ),
    TYPE_CROSSBOW_LIMB(
        requiredBar = 1,
        childId = 122,
        nameId = 123,
        button = intArrayOf(128, 127, 126, 125),
        productAmount = 1
    ),
    TYPE_LONGSWORD(
        requiredBar = 2,
        childId = 130,
        nameId = 131,
        button = intArrayOf(136, 135, 134, 133),
        productAmount = 1
    ),
    TYPE_THROWING_KNIFE(
        requiredBar = 1,
        childId = 138,
        nameId = 139,
        button = intArrayOf(144, 143, 142, 141),
        productAmount = 5
    ),
    TYPE_FULL_HELM(
        requiredBar = 2,
        childId = 146,
        nameId = 147,
        button = intArrayOf(152, 151, 150, 149),
        productAmount = 1
    ),
    TYPE_SQUARE_SHIELD(
        requiredBar = 2,
        childId = 154,
        nameId = 155,
        button = intArrayOf(160, 159, 158, 157),
        productAmount = 1
    ),
    TYPE_LANTERN(
        requiredBar = 1,
        childId = 162,
        nameId = 163,
        button = intArrayOf(168, 167, 166, 165),
        productAmount = 1
    ),
    TYPE_OIL_LANTERN(
        requiredBar = 1,
        childId = 162,
        nameId = 163,
        button = intArrayOf(168, 167, 166, 165),
        productAmount = 1
    ),
    TYPE_GRAPPLE_TIP(
        requiredBar = 1,
        childId = 170,
        nameId = 171,
        button = intArrayOf(175, 176, 175, 174, 173),
        productAmount = 1
    ),
    TYPE_STUDS(
        requiredBar = 1,
        childId = 98,
        nameId = 99,
        button = intArrayOf(104, 103, 102, 101, 100),
        productAmount = 1
    ),
    TYPE_WARHAMMER(
        requiredBar = 3,
        childId = 178,
        nameId = 179,
        button = intArrayOf(184, 183, 182, 181),
        productAmount = 1
    ),
    TYPE_BATTLE_AXE(
        requiredBar = 3,
        childId = 186,
        nameId = 187,
        button = intArrayOf(192, 191, 190, 189),
        productAmount = 1
    ),
    TYPE_CHAINBODY(
        requiredBar = 3,
        childId = 194,
        nameId = 195,
        button = intArrayOf(200, 199, 198, 197),
        productAmount = 1
    ),
    TYPE_KITE_SHIELD(
        requiredBar = 3,
        childId = 202,
        nameId = 203,
        button = intArrayOf(208, 207, 206, 205),
        productAmount = 1
    ),
    TYPE_CLAWS(
        requiredBar = 2,
        childId = 210,
        nameId = 211,
        button = intArrayOf(216, 215, 214, 213),
        productAmount = 1
    ),
    TYPE_TWO_HAND_SWORD(
        requiredBar = 3,
        childId = 218,
        nameId = 219,
        button = intArrayOf(224, 223, 222, 221),
        productAmount = 1
    ),
    TYPE_PLATE_SKIRT(
        requiredBar = 3,
        childId = 226,
        nameId = 227,
        button = intArrayOf(232, 231, 230, 229),
        productAmount = 1
    ),
    TYPE_Platelegs(
        requiredBar = 3,
        childId = 234,
        nameId = 235,
        button = intArrayOf(240, 239, 238, 237),
        productAmount = 1
    ),
    TYPE_PLATEBODY(
        requiredBar = 5,
        childId = 242,
        nameId = 243,
        button = intArrayOf(248, 247, 246, 245),
        productAmount = 1
    ),
    TYPE_Crossbow_Bolt(
        requiredBar = 1,
        childId = 251,
        nameId = 252,
        button = intArrayOf(257, 256, 255, 254),
        productAmount = 10
    ),
    TYPE_Crossbow_Limb(
        requiredBar = 1,
        childId = 259,
        nameId = 260,
        button = intArrayOf(265, 264, 263, 262),
        productAmount = 1
    ),
    TYPE_PICKAXE(
        requiredBar = 2,
        childId = 267,
        nameId = 268,
        button = intArrayOf(273, 272, 271, 270),
        productAmount = 1
    );


    companion object {
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
