package content.global.random.event.gravedigger

import core.api.*
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.scenery.*
import core.game.world.map.Location

/**
 * Grave listener.
 */
class GraveListener : InteractionListener {
    private val grave1 = Scenery.GRAVE_12722
    private val grave2 = Scenery.GRAVE_12724
    private val grave3 = Scenery.GRAVE_12721
    private val grave4 = Scenery.GRAVE_12723
    private val grave5 = Scenery.GRAVE_12725
    private val emptygrave1 = Scenery.GRAVE_12726
    private val emptygrave2 = Scenery.GRAVE_12727
    private val emptygrave3 = Scenery.GRAVE_12728
    private val emptygrave4 = Scenery.GRAVE_12729
    private val emptygrave5 = Scenery.GRAVE_12730
    private val mausoleum = Scenery.MAUSOLEUM_12731
    private val leo = NPCs.LEO_3508

    override fun defineListeners() {

        on(mausoleum, IntType.SCENERY, "deposit") { player, _ ->
            player.bank.openDepositBox()
            player.bank::refreshDepositBoxInterface
            return@on true
        }

        on(leo, IntType.NPC, "talk-to") { player, npc ->
            if (getAttribute(player, GraveUtils.LEO_COFFIN_POINTS, 0) >= 0) {
                openDialogue(player, LeoDialogue(), npc)
            } else if (getAttribute(player, GraveUtils.LEO_TASK, 0) == 1) {
                openDialogue(player, LeoDialogue(), npc)
            }
            return@on true
        }

        // Check coffin interface

        on(GraveUtils.itemsCoffin, IntType.ITEM, "check") { player, _ ->
            openInterface(player, GraveUtils.COFFIN_INTERFACE)
            GraveUtils.getRandomCoffinContent(player)
            return@on true
        }

        // Checking gravestone interface

        on(GraveUtils.graveStones, IntType.SCENERY, "read") { player, _ ->
            openInterface(player, GraveUtils.GRAVESTONE_INTERFACE)
            GraveUtils.getRandomGraveContent(player)
            sendMessage(player, "You look at the gravestone.")
            return@on true
        }

        // Take coffin item from grave

        on(grave1, IntType.SCENERY, "take-coffin") { player, _ ->
            lock(player, 3)
            animate(player, GraveUtils.PICK_AND_DROP_ANIM)
            replaceScenery(Scenery(12722, Location(1926, 4999, 0)), emptygrave1, -1)
            sendMessage(player, "You take the coffin from the grave.")
            addItemOrDrop(player, GraveUtils.COFFIN_1)
            return@on true
        }

        on(grave2, IntType.SCENERY, "take-coffin") { player, _ ->
            lock(player, 3)
            animate(player, GraveUtils.PICK_AND_DROP_ANIM)
            replaceScenery(Scenery(12724, Location(1930, 4999, 0)), emptygrave2, -1)
            sendMessage(player, "You take the coffin from the grave.")
            addItemOrDrop(player, GraveUtils.COFFIN_2)
            return@on true
        }

        on(grave3, IntType.SCENERY, "take-coffin") { player, _ ->
            lock(player, 3)
            animate(player, GraveUtils.PICK_AND_DROP_ANIM)
            replaceScenery(Scenery(12721, Location(1924, 4996, 0)), emptygrave3, -1)
            sendMessage(player, "You take the coffin from the grave.")
            addItemOrDrop(player, GraveUtils.COFFIN_3)
            return@on true
        }

        on(grave4, IntType.SCENERY, "take-coffin") { player, _ ->
            lock(player, 3)
            animate(player, GraveUtils.PICK_AND_DROP_ANIM)
            replaceScenery(Scenery(12723, Location(1928, 4996, 0)), emptygrave4, -1)
            sendMessage(player, "You take the coffin from the grave.")
            addItemOrDrop(player, GraveUtils.COFFIN_4)
            return@on true
        }

        on(grave5, IntType.SCENERY, "take-coffin") { player, _ ->
            lock(player, 3)
            animate(player, GraveUtils.PICK_AND_DROP_ANIM)
            replaceScenery(Scenery(12725, Location(1932, 4996, 0)), emptygrave5, -1)
            sendMessage(player, "You take the coffin from the grave.")
            addItemOrDrop(player, GraveUtils.COFFIN_5)
            return@on true
        }

        // Drop coffin item to grave

        onUseWith(SCENERY, GraveUtils.itemsCoffin, emptygrave1) { player, used, _ ->
            lock(player, 3)
            removeItem(player, used.asItem())
            player.animate(GraveUtils.PICK_AND_DROP_ANIM)
            replaceScenery(Scenery(emptygrave1, Location(1926, 4999, 0)), grave1, -1, location(1926, 4999, 0))
            sendMessage(player, "You put the coffin into the grave.")
            player.incrementAttribute(GraveUtils.LEO_COFFIN_POINTS)
            return@onUseWith true
        }

        onUseWith(SCENERY, GraveUtils.itemsCoffin, emptygrave2) { player, used, _ ->
            lock(player, 3)
            removeItem(player, used.asItem())
            player.animate(GraveUtils.PICK_AND_DROP_ANIM)
            replaceScenery(Scenery(emptygrave2, Location(1930, 4999, 0)), grave2, -1, location(1930, 4999, 0))
            sendMessage(player, "You put the coffin into the grave.")
            player.incrementAttribute(GraveUtils.LEO_COFFIN_POINTS)
            return@onUseWith true
        }

        onUseWith(SCENERY, GraveUtils.itemsCoffin, emptygrave3) { player, used, _ ->
            lock(player, 3)
            removeItem(player, used.asItem())
            player.animate(GraveUtils.PICK_AND_DROP_ANIM)
            replaceScenery(Scenery(emptygrave3, Location(1924, 4996, 0)), grave3, -1, location(1924, 4996, 0))
            sendMessage(player, "You put the coffin into the grave.")
            player.incrementAttribute(GraveUtils.LEO_COFFIN_POINTS)
            return@onUseWith true
        }

        onUseWith(SCENERY, GraveUtils.itemsCoffin, emptygrave4) { player, used, _ ->
            lock(player, 3)
            removeItem(player, used.asItem())
            player.animate(GraveUtils.PICK_AND_DROP_ANIM)
            replaceScenery(Scenery(emptygrave4, Location(1928, 4996, 0)), grave4, -1, location(1928, 4996, 0))
            sendMessage(player, "You put the coffin into the grave.")
            player.incrementAttribute(GraveUtils.LEO_COFFIN_POINTS)
            return@onUseWith true
        }

        onUseWith(SCENERY, GraveUtils.itemsCoffin, emptygrave5) { player, used, _ ->
            lock(player, 3)
            removeItem(player, used.asItem())
            player.animate(GraveUtils.PICK_AND_DROP_ANIM)
            replaceScenery(Scenery(emptygrave5, Location(1932, 4996, 0)), grave5, -1, location(1932, 4996, 0))
            sendMessage(player, "You put the coffin into the grave.")
            player.incrementAttribute(GraveUtils.LEO_COFFIN_POINTS)
            return@onUseWith true
        }
    }
}