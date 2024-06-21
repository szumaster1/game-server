package content.global.random.event.supriseexam

import core.api.clearLogoutListener
import core.api.consts.Components
import core.api.consts.Items
import core.api.removeAttributes
import core.api.setAttribute
import core.game.node.entity.impl.PulseType
import core.game.node.entity.player.Player
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.system.task.Pulse


object SupriseExamUtils {

    val SE_KEY_LOC = "supexam:loc"
    val SE_KEY_INDEX = "supexam:index"
    val SE_LOGOUT_KEY = "suprise_exam"
    val SE_DOOR_KEY = "supexam:door"
    val INTER_PATTERN_CHILDS = intArrayOf(6, 7, 8)
    val INTER_OPTION_CHILDS = intArrayOf(10, 11, 12, 13)
    val SE_DOORS = intArrayOf(2188, 2189, 2192, 2193)
    val INTERFACE = Components.PATTERN_NEXT_103
    val SE_KEY_CORRECT = "supexam:correct"
    val sets = arrayOf(
        intArrayOf(Items.GARDENING_TROWEL_5325, Items.SECATEURS_5329, Items.SEED_DIBBER_5343, Items.RAKE_5341),
        intArrayOf(Items.SALMON_329, Items.SHARK_385, Items.TROUT_333, Items.SHRIMPS_315),
        intArrayOf(
            Items.BRONZE_SWORD_1277,
            Items.WOODEN_SHIELD_1171,
            Items.BRONZE_MED_HELM_1139,
            Items.ADAMANT_BATTLEAXE_1371
        ),
        intArrayOf(Items.FLY_FISHING_ROD_309, Items.BARBARIAN_ROD_11323, Items.SMALL_FISHING_NET_303, Items.HARPOON_311)
    )

    fun cleanup(player: Player) {
        player.properties.teleportLocation = player.getAttribute(SE_KEY_LOC, null)
        clearLogoutListener(player, SE_LOGOUT_KEY)
        removeAttributes(player, SE_KEY_LOC, SE_KEY_INDEX, SE_KEY_CORRECT)
        player.pulseManager.run(object : Pulse(2) {
            override fun pulse(): Boolean {
                val reward = Item(Items.BOOK_OF_KNOWLEDGE_11640)
                if (!player.inventory.add(reward)) {
                    GroundItemManager.create(reward, player)
                }
                return true
            }
        }, PulseType.CUSTOM_1)
    }

    fun generateInterface(player: Player) {
        val set = sets.random()
        val setIndex = sets.indexOf(set)

        val tempList = set.toList().shuffled().toMutableList()
        val correctOpt = tempList.random()
        val optIndex = tempList.indexOf(correctOpt)

        tempList.remove(correctOpt)
        for (i in INTER_PATTERN_CHILDS.indices) player.packetDispatch.sendItemOnInterface(
            tempList[i], 1, INTERFACE, INTER_PATTERN_CHILDS[i]
        )
        for (i in INTER_OPTION_CHILDS.indices) {
            if (i == optIndex) player.packetDispatch.sendItemOnInterface(
                correctOpt, 1, INTERFACE, INTER_OPTION_CHILDS[i]
            )
            else player.packetDispatch.sendItemOnInterface(
                getFalseSet(setIndex)[i], 1, INTERFACE, INTER_OPTION_CHILDS[i]
            )
        }
        setAttribute(player, SE_KEY_INDEX, optIndex)
    }

    fun getFalseSet(trueSetIndex: Int): IntArray {
        val tempSets = sets.toMutableList()
        tempSets.removeAt(trueSetIndex)
        return tempSets.random()
    }

    fun pickRandomDoor(player: Player) {
        setAttribute(player, SE_DOOR_KEY, SE_DOORS.random())
    }
}
