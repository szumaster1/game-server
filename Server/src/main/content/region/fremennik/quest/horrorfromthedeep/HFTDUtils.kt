package content.region.fremennik.quest.horrorfromthedeep

import cfg.consts.Items
import cfg.consts.Scenery

/**
 * Horror from the Deep utils.
 */
object HFTDUtils {

    // Quest attributes
    const val FIX_LIGHTHOUSE_MECHANISM = "/save:hftd:lighthouse-fixed"
    const val FIX_BRIDGE = "/save:hftd:lighthouse-bridge"
    const val STRANGE_WALL_DISCOVER = "/save:hftd:strange-wall"
    const val PUZZLE_PROGRESS = "/save:hftd:item-placed"

    // Interface attributes
    const val STRANGE_W_AIR = "/save:hftd:air"
    const val STRANGE_W_FIRE = "/save:hftd:fire"
    const val STRANGE_W_EARTH = "/save:hftd:earth"
    const val STRANGE_W_WATER = "/save:hftd:water"
    const val STRANGE_W_ARROW = "/save:hftd:arrow"
    const val STRANGE_W_SWORD = "/save:hftd:sword"

    // Items
    val STRANGE_W_REQ_ITEMS = intArrayOf(Items.BRONZE_ARROW_882, Items.BRONZE_SWORD_1277, Items.AIR_RUNE_556, Items.FIRE_RUNE_554, Items.EARTH_RUNE_557, Items.WATER_RUNE_555)
    const val QUEST_CASKET = Items.RUSTY_CASKET_3849

    // Scenery
    const val BROKEN_BRIDGE_1 = Scenery.BROKEN_BRIDGE_4615
    const val BROKEN_BRIDGE_2 = Scenery.BROKEN_BRIDGE_4616
    const val LIGHTHOUSE_FRONT_DOORS = Scenery.DOORWAY_4577

    val STRANGE_DOORS = intArrayOf(Scenery.STRANGE_WALL_4545, Scenery.STRANGE_WALL_4546)
    val METAL_DOORS = intArrayOf(Scenery.STRANGE_WALL_4544, Scenery.STRANGE_WALL_4543)
}
