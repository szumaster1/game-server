package content.region.misc.tutorial.handlers

import core.api.removeAttribute
import core.api.setAttribute
import core.api.setVarbit
import core.api.setVarp
import core.game.component.Component
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.appearance.Gender
import core.tools.RandomFunction
import kotlin.math.abs

/*
 * MALE IDS
 * Male head ids : Script - 701 [0: 1 1: 2 2: 3 3: 4 4: 5 5: 6 6: 7 7: 8 8: 9 91: 10 92: 11 93: 12 94: 13 95: 14 96: 15 97: 16 261: 17 262: 18 263: 19 264: 20 265: 21 266: 22 267: 23 268: 24 ]
 * Male jaw ids: Script - 703 [10: 1 11: 2 12: 3 13: 4 14: 5 15: 6 16: 7 17: 8 98: 9 99: 10 100: 11 101: 12 102: 13 103: 14 104: 15 305: 16 306: 17 307: 18 308: 19 ]
 * Male torso ids: Script - 1128 [19: 1 20: 2 21: 3 22: 4 23: 5 24: 6 25: 7 111: 8 112: 9 113: 10 114: 11 115: 12 116: 13 ]
 * Male arm ids: Script - 1130 [27: 1 28: 2 29: 3 30: 4 31: 5 105: 6 106: 7 107: 8 108: 9 109: 10 110: 11 ]
 * Male hand ids: Script - 1132 [34: 1 84: 2 117: 3 118: 4 119: 5 120: 6 121: 7 122: 8 123: 9 124: 10 125: 11 126: 12 ]
 * Male leg ids: Script - 1134 [37: 1 38: 2 39: 3 40: 4 85: 5 86: 6 87: 7 88: 8 89: 9 90: 10 ]
 * Male feet ids: Script - 1136 [43: 1 ]
 * FEMALE IDS
 * Female head ids: Script - 689 [45: 1 46: 2 47: 3 48: 4 49: 5 50: 6 51: 7 52: 8 53: 9 54: 10 135: 11 136: 12 137: 13 138: 14 139: 15 140: 16 141: 17 142: 318 143: 19 144: 20 145: 21 146: 22 269: 23 270: 24 271: 25 272: 26 273: 27 274: 28 275: 29 276: 30 277: 31 278: 32 279: 33 280: 34 ]
 * Female torso ids: Script - 1129 [57: 1 58: 2 59: 3 60: 4 153: 5 154: 6 155: 7 156: 8 157: 9 158: 10 ]
 * Female arm ids: Script - 1131 [62: 1 63: 2 64: 3 65: 4 147: 5 148: 6 149: 7 150: 8 151: 9 152: 10 ]
 * Female hand ids: Script - 1133 [68: 1 127: 2 159: 3 160: 4 161: 5 162: 6 163: 7 164: 8 165: 9 166: 10 167: 11 168: 12 ]
 * Female leg ids: Script - 1135 [71: 1 72: 2 73: 3 74: 4 75: 5 76: 6 77: 7 128: 8 129: 9 130: 10 131: 11 132: 12 133: 13 134: 14 ]
 * Female feet ids: Script - 1137 [80: 1 ]
 */

object CharacterDesign {
    private val MALE_HEAD_IDS = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 91, 92, 93, 94, 95, 96, 97, 261, 262, 263, 264, 265, 266, 267, 268)
    private val FEMALE_HEAD_IDS = intArrayOf(45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280)
    private val MALE_JAW_IDS = intArrayOf(10, 11, 12, 13, 14, 15, 16, 17, 98, 99, 100, 101, 102, 103, 104, 305, 306, 307, 308)
    private val FEMALE_JAW_IDS = intArrayOf(1000)
    private val MALE_TORSO_IDS = intArrayOf(18, 19, 20, 21, 22, 23, 24, 25, 111, 112, 113, 114, 115, 116)
    private val FEMALE_TORSO_IDS = intArrayOf(56, 57, 58, 59, 60, 153, 154, 155, 156, 157, 158)
    private val MALE_ARMS_IDS = intArrayOf(26, 27, 28, 29, 30, 31, 105, 106, 107, 108, 109, 110)
    private val FEMALE_ARMS_IDS = intArrayOf(61, 62, 63, 64, 65, 147, 148, 149, 150, 151, 152)
    private val MALE_HANDS_IDS = intArrayOf(33, 34, 84, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126)
    private val FEMALE_HANDS_IDS = intArrayOf(67, 68, 127, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168)
    private val MALE_LEGS_IDS = intArrayOf(36, 37, 38, 39, 40, 85, 86, 87, 88, 89, 90)
    private val FEMALE_LEGS_IDS = intArrayOf(70, 71, 72, 73, 74, 75, 76, 77, 128, 129, 130, 131, 132, 133, 134)
    private val MALE_FEET_IDS = intArrayOf(42, 43)
    private val FEMALE_FEET_IDS = intArrayOf(79, 80)
    private val MALE_LOOK_IDS = arrayOf(MALE_HEAD_IDS, MALE_JAW_IDS, MALE_TORSO_IDS, MALE_ARMS_IDS, MALE_HANDS_IDS, MALE_LEGS_IDS, MALE_FEET_IDS)
    private val FEMALE_LOOK_IDS = arrayOf(FEMALE_HEAD_IDS, FEMALE_JAW_IDS, FEMALE_TORSO_IDS, FEMALE_ARMS_IDS, FEMALE_HANDS_IDS, FEMALE_LEGS_IDS, FEMALE_FEET_IDS)
    private val HAIR_COLORS = intArrayOf(20, 19, 10, 18, 4, 5, 15, 7, 0, 6, 21, 9, 22, 17, 8, 16, 11, 24, 23, 3, 2, 1, 14, 13, 12)
    private val TORSO_COLORS = intArrayOf(24, 23, 2, 22, 12, 11, 6, 19, 4, 0, 9, 13, 25, 8, 15, 26, 21, 7, 20, 14, 10, 28, 27, 3, 5, 18, 17, 1, 16)
    private val LEG_COLORS = intArrayOf(26, 24, 23, 3, 22, 13, 12, 7, 19, 5, 1, 10, 14, 25, 9, 0, 21, 8, 20, 15, 11, 28, 27, 4, 6, 18, 17, 2, 16)
    private val FEET_COLORS = intArrayOf(0, 1, 2, 3, 4, 5)
    private val SKIN_COLORS = intArrayOf(7, 6, 5, 4, 3, 2, 1, 0)

    @JvmStatic
    fun open(player: Player) {
        player.unlock()
        removeAttribute(player, "char-design:accepted")
        player.packetDispatch.sendPlayerOnInterface(771, 79)
        player.packetDispatch.sendAnimationInterface(9806, 771, 79)
        player.appearance.changeGender(player.appearance.gender)
        val c = player.interfaceManager.openComponent(771)
        c?.setCloseEvent { player, c ->
            player.getAttribute("char-design:accepted", false)
        }
        reset(player)
        player.packetDispatch.sendInterfaceConfig(771, 22, false)
        player.packetDispatch.sendInterfaceConfig(771, 92, false)
        player.packetDispatch.sendInterfaceConfig(771, 97, false)
        setVarp(player, 1262, if (player.appearance.isMale) 1 else -1)
    }

    @JvmStatic
    fun reopen(player: Player) {
        removeAttribute(player, "char-design:accepted")
        player.packetDispatch.sendPlayerOnInterface(771, 79)
        player.packetDispatch.sendAnimationInterface(9806, 771, 79)
        val c = player.interfaceManager.openComponent(771)
        c?.setCloseEvent { player1: Player, c1: Component? -> player1.getAttribute("char-design:accepted", false) }
        player.packetDispatch.sendInterfaceConfig(771, 22, false)
        player.packetDispatch.sendInterfaceConfig(771, 92, false)
        player.packetDispatch.sendInterfaceConfig(771, 97, false)
        setVarp(player, 1262, if (player.appearance.isMale) 1 else -1)
    }

    @JvmStatic
    fun handleButtons(player: Player, buttonId: Int): Boolean {
        when (buttonId) {
            37, 40 -> player.settings.toggleMouseButton()
            92, 93 -> changeLook(player, 0, buttonId == 93)
            97, 98 -> changeLook(player, 1, buttonId == 98)
            341, 342 -> changeLook(player, 2, buttonId == 342)
            345, 346 -> changeLook(player, 3, buttonId == 346)
            349, 350 -> changeLook(player, 4, buttonId == 350)
            353, 354 -> changeLook(player, 5, buttonId == 354)
            357, 358 -> changeLook(player, 6, buttonId == 358)
            49, 52 -> changeGender(player, buttonId == 49)
            321 -> {
                randomize(player, false)
                return true
            }

            169 -> {
                randomize(player, true)
                return true
            }

            362 -> {
                confirm(player, true)
                return true
            }
        }
        if (buttonId >= 100 && buttonId <= 124) {
            changeColor(player, 0, HAIR_COLORS, 100, buttonId)
        }
        if (buttonId >= 189 && buttonId <= 217) {
            changeColor(player, 2, TORSO_COLORS, 189, buttonId)
        }
        if (buttonId >= 248 && buttonId <= 276) {
            changeColor(player, 5, LEG_COLORS, 248, buttonId)
        }
        if (buttonId >= 307 && buttonId <= 312) {
            changeColor(player, 6, FEET_COLORS, 307, buttonId)
        }
        if (buttonId <= 158 && buttonId >= 151) {
            changeColor(player, 4, SKIN_COLORS, 158, buttonId)
        }
        return false
    }

    private fun changeGender(player: Player, male: Boolean) {
        setAttribute(player, "male", male)
        setVarp(player, 1262, if (male) 1 else -1)
        if (male) {
            setVarbit(player, 5008, 1)
            setVarbit(player, 5009, 0)
        } else {
            setVarbit(player, 5008, 0)
            setVarbit(player, 5009, 1)
        }
        reset(player)
    }

    private fun changeLook(player: Player, index: Int, increment: Boolean) {
        if (index < 2 && !player.getAttribute("first-click:$index", false)) {
            setAttribute(player, "first-click:$index", true)
            return
        }
        setAttribute(
            player, "look-val:$index", getValue(
                player, "look", index, player.getAttribute(
                    "look:$index", 0
                ), increment
            )
        )
    }

    private fun changeColor(player: Player, index: Int, array: IntArray, startId: Int, buttonId: Int) {
        val col = array[abs((buttonId - startId).toDouble()).toInt()]
        setAttribute(player, "color-val:$index", col)
    }

    private fun reset(player: Player) {
        for (i in player.appearance.appearanceCache.indices) {
            removeAttribute(player, "look:$i")
            removeAttribute(player, "look-val:$i")
            removeAttribute(player, "color-val:$i")
        }
        removeAttribute(player, "first-click:0")
        removeAttribute(player, "first-click:1")
    }

    @JvmStatic
    fun randomize(player: Player, head: Boolean) {
        if (head) {
            changeLook(player, 0, RandomFunction.random(2) == 1)
            changeLook(player, 1, RandomFunction.random(2) == 1)
            changeColor(player, 0, HAIR_COLORS, 100, RandomFunction.random(100, 124))
            changeColor(player, 4, SKIN_COLORS, 158, RandomFunction.random(158, 151))
        } else {
            for (i in player.appearance.appearanceCache.indices) {
                changeLook(player, i, RandomFunction.random(2) == 1)
            }
            changeColor(player, 2, TORSO_COLORS, 189, RandomFunction.random(189, 217))
            changeColor(player, 5, LEG_COLORS, 248, RandomFunction.random(248, 276))
            changeColor(player, 6, FEET_COLORS, 307, RandomFunction.random(307, 312))
        }
        confirm(player, false)
    }

    private fun confirm(player: Player, close: Boolean) {
        if (close) {
            setAttribute(player, "char-design:accepted", true)
            player.interfaceManager.close()
        }
        player.appearance.gender =
            if (player.getAttribute("male", player.appearance.isMale)) Gender.MALE else Gender.FEMALE
        for (i in player.appearance.appearanceCache.indices) {
            player.appearance.appearanceCache[i].changeLook(player.getAttribute("look-val:$i", player.appearance.appearanceCache[i].look))
            player.appearance.appearanceCache[i].changeColor(player.getAttribute("color-val:$i", player.appearance.appearanceCache[i].color))
        }
        player.appearance.sync()
    }

    private fun getValue(player: Player, key: String, index: Int, currentIndex: Int, increment: Boolean): Int {
        var currentIndex = currentIndex
        val array =
            if (player.getAttribute("male", player.appearance.isMale)) MALE_LOOK_IDS[index] else FEMALE_LOOK_IDS[index]
        var `val` = 0
        if (increment && currentIndex + 1 > array.size - 1) {
            `val` = array[0]
            currentIndex = 0
        } else if (!increment && currentIndex - 1 < 0) {
            `val` = array[array.size - 1]
            currentIndex = array.size - 1
        } else if (increment) {
            `val` = array[currentIndex + 1]
            currentIndex++
        } else {
            `val` = array[currentIndex - 1]
            currentIndex--
        }
        player.setAttribute("$key:$index", currentIndex)
        return `val`
    }

}

