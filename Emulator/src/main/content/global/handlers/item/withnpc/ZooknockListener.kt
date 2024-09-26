package content.global.handlers.item.withnpc

import content.region.karamja.apeatoll.dialogue.dungeon.ZooknockDialogueFile
import core.api.openDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Items
import org.rs.consts.NPCs

/**
 * Handles use of items on Zooknock.
 */
open class ZooknockListener : InteractionListener {

    val goldBar = Items.GOLD_BAR_2357
    val monkeyAmuletMould = Items.MAMULET_MOULD_4020
    val monkeyDentures = Items.MONKEY_DENTURES_4006
    val items = intArrayOf(goldBar, monkeyDentures, monkeyAmuletMould)

    val zooknock = NPCs.ZOOKNOCK_1425

    override fun defineListeners() {
        onUseWith(IntType.NPC, items, zooknock) { player, used, _ ->
            openDialogue(player, ZooknockDialogueFile(used.id))
            return@onUseWith false
        }
    }
}
