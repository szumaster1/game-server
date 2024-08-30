package content.region.misthalin.draynor

import cfg.consts.Items
import cfg.consts.NPCs
import cfg.consts.Scenery
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders

object DraynorUtils {

    /**
     * Reference [TreeGuardDialogue][content.region.misthalin.draynor.dialogue.TreeGuardDialogue]
     */
    const val feedGuardAttribute = "/save:draynor:feed-tree-guard"

    /**
     * Reference [TreeGuardDialogue][content.region.misthalin.draynor.dialogue.TreeGuardDialogue]
     */
    val treeGuardChat = arrayOf("Hey - gerroff me!", "You'll blow my cover! I'm meant to be hidden!", "Don't draw attention to me!", "Will you stop that?", "Watch what you're doing with that hatchet, you nit!", "Ooooch!", "Ow! That really hurt!", "Oi!")

    /**
     * Reference [DraynorMarket]
     */
    val draynorMarket = ZoneBorders(3074, 3245, 3086, 3255)

    /**
     * Reference [DraynorMansionCourtyard]
     */
    val draynorMansionCourtyard = ZoneBorders(3100, 3333, 3114, 3346)

    /**
     * Reference [DraynorVillageListeners]
     */
    val bookshelf = intArrayOf(Scenery.OLD_BOOKSHELF_7065, Scenery.OLD_BOOKSHELF_7066, Scenery.OLD_BOOKSHELF_7068)
    const val trapdoor = Scenery.TRAPDOOR_6434
    const val telescope = Scenery.TELESCOPE_7092
    const val tree = Scenery.TREE_10041
    const val diango = NPCs.DIANGO_970
    const val aggie = NPCs.AGGIE_922

    /**
     * Reference [VampyreSlayerListener][content.region.misthalin.draynor.quest.vampire.VampireSlayerListener], [VampyreSlayer][content.region.misthalin.draynor.quest.vampire.VampireSlayer]
     */

    const val cupBoard = Scenery.CUPBOARD_33502
    const val openedCupBoard = Scenery.CUPBOARD_33503
    const val coffin = Scenery.COFFIN_2614
    const val openedCoffin = Scenery.COFFIN_2615
    const val stairsUp = Scenery.STAIRS_32835
    const val stairsBasement = Scenery.STAIRS_32836

    val basement = Location.create(3077, 9770, 0)
    val groundFloor = Location.create(3115, 3356, 0)

    const val garlic = Items.GARLIC_1550
    const val stake = Items.STAKE_1549
    const val hammer = Items.HAMMER_2347
}