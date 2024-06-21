package content.region.kandarin.quest.murdermystery

import core.api.consts.Items
import core.api.consts.Scenery
import core.api.setAttribute
import core.api.setQuestStage
import core.game.node.entity.player.Player
import core.game.world.map.zone.ZoneBorders

object MysteryUtils {

    val ATTRIBUTE_ELIZABETH = "murdermystery:elizabeth"
    val ATTRIBUTE_ANNA = "murdermystery:anna"
    val ATTRIBUTE_DAVID = "murdermystery:david"

    val MANSION_ROAD_ZONE = ZoneBorders(2739, 3557, 2744, 3570)

    val EVIDENCE_ITEMS = intArrayOf(Items.SILVER_NECKLACE_1796, Items.SILVER_CUP_1798, Items.SILVER_BOTTLE_1800, Items.SILVER_BOOK_1802, Items.SILVER_NEEDLE_1804, Items.SILVER_POT_1806, Items.PUNGENT_POT_1812, Items.CRIMINALS_DAGGER_1813)
    val EVIDENCE_ITEMS_2 = intArrayOf(Items.SILVER_NECKLACE_1797, Items.SILVER_CUP_1799, Items.SILVER_BOTTLE_1801, Items.SILVER_BOOK_1803, Items.SILVER_NEEDLE_1805, Items.SILVER_POT_1807, Items.PUNGENT_POT_1812, Items.CRIMINALS_DAGGER_1814)
    val GUILTY_NPC_PRINT_ITEMS = intArrayOf(Items.KILLERS_PRINT_1815, Items.ANNAS_PRINT_1816, Items.BOBS_PRINT_1817, Items.CAROLS_PRINT_1818, Items.DAVIDS_PRINT_1819, Items.ELIZABETHS_PRINT_1820, Items.FRANKS_PRINT_1821, Items.UNKNOWN_PRINT_1822)
    val MANSION_OBJECTS = intArrayOf(Scenery.ANNA_S_BARREL_2656, Scenery.BOB_S_BARREL_2657, Scenery.CAROL_S_BARREL_2658, Scenery.DAVID_S_BARREL_2659, Scenery.ELIZABETH_S_BARREL_2660, Scenery.FRANK_S_BARREL_2661)
    val CRIME_SCENE_OBJECTS = intArrayOf(Scenery.SINCLAIR_FAMILY_FOUNTAIN_2654, Scenery.SINCLAIR_MANSION_DRAIN_2843, Scenery.SINCLAIR_FAMILY_CREST_2655, Scenery.SPIDERS__NEST_26109, Scenery.SINCLAIR_FAMILY_BEEHIVE_26121, Scenery.SINCLAIR_FAMILY_COMPOST_HEAP_26120)

    fun initialSuspects(player: Player) {
        if (player.inventory.containItems(Items.CRIMINALS_THREAD_1808)) {
            setAttribute(player, "/save:$ATTRIBUTE_ELIZABETH", false)
        } else if (player.inventory.containItems(Items.CRIMINALS_THREAD_1809)) {
            setAttribute(player, "/save:$ATTRIBUTE_ANNA", false)
        } else if (player.inventory.containItems(Items.CRIMINALS_THREAD_1810)) {
            setAttribute(player, "/save:$ATTRIBUTE_DAVID", false)
        }
        setQuestStage(player, "Murder Mystery", 2)
    }
}