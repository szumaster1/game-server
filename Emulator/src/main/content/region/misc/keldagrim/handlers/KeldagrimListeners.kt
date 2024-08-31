package content.region.misc.keldagrim.handlers

import cfg.consts.Components
import core.api.*
import cfg.consts.Items
import cfg.consts.NPCs
import cfg.consts.Scenery
import content.region.misc.keldagrim.dialogue.BlastFusionHammerDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.link.TeleportManager
import core.game.world.map.Location

/**
 * Keldagrim listeners.
 */
class KeldagrimListeners : InteractionListener {

    // SceneryDefinition.forId(Scenery.STAIRS_9084).handlers["option:climb-down"] = this
    // SceneryDefinition.forId(Scenery.STAIRS_9138).handlers["option:climb-up"] = this

    companion object {
        private val ENTRANCE = intArrayOf(5973,5998)
        private const val DOORWAY_1 = Scenery.DOORWAY_23286
        private const val DOORWAY_2 = Scenery.DOORWAY_23287
        private const val REINALD = NPCs.REINALD_2194
        private const val FUSION_HAMMER = Items.BLAST_FUSION_HAMMER_14478
        private const val FOREMAN = NPCs.BLAST_FURNACE_FOREMAN_2553
        private const val TUNNEL = Scenery.TUNNEL_5014
        private const val INN_KEEPER = NPCs.INN_KEEPER_2176
    }


    override fun defineListeners() {

        /*
         * Handling interaction with a doorway.
         */

        on(DOORWAY_1, IntType.SCENERY, "enter") { player, _ ->
            teleport(player, Location(2941, 10179, 0))
            return@on true
        }

        /*
         * Handling interaction with a doorway.
         */

        on(DOORWAY_2, IntType.SCENERY, "enter") { player, _ ->
            teleport(player, Location(2435, 5535, 0))
            return@on true
        }

        /*
         * Handling open the Reinald - Smithing Emporium shop.
         */

        on(REINALD, IntType.NPC, "change-armguards") { player, _ ->
            openInterface(player, Components.REINALD_SMITHING_EMPORIUM_593)
            return@on true
        }

        /*
         * Interaction with foreman (Blast furnace mini-game).
         */

        onUseWith(IntType.NPC,
            FUSION_HAMMER,
            FOREMAN
        ) { player, _, npc ->
            openDialogue(player, BlastFusionHammerDialogue(), npc)
            return@onUseWith true
        }

        /*
         * Handling the go through cave entrance to Keldagrim.
         */

        on(ENTRANCE, IntType.SCENERY, "go-through") { player, node ->
            if(node.id == Scenery.CAVE_ENTRANCE_5973){
                runTask(player, 1) {
                    teleport(player, Location(2838, 10125), TeleportManager.TeleportType.INSTANT)
                }
            } else {
                runTask(player, 1) {
                    teleport(player, Location(2780, 10161), TeleportManager.TeleportType.INSTANT)
                }
            }
            return@on true
        }

        /*
         * Library +1 bookcase interaction.
         */

        on(Scenery.BOOKCASE_6091, IntType.SCENERY, "search") { player, _ ->
            if (inInventory(player, Items.EXPLORERS_NOTES_11677)) {
                sendMessage(player, "You search the books...")
                sendMessage(player, "You find nothing of interest to you.")
            } else if (freeSlots(player) < 1) {
                sendMessage(player, "You need at least one free inventory space to take from the shelves.")
            } else {
                sendMessage(player, "You search the bookcase and find a book named 'Explorer's Notes'.")
                addItemOrDrop(player, Items.EXPLORERS_NOTES_11677)
            }
            return@on true
        }

        /*
         * Handling the tunnel entrance.
         */

        on(TUNNEL, IntType.SCENERY, "enter") { player, _ ->
            teleport(player, Location(2730, 3713, 0), TeleportManager.TeleportType.INSTANT)
            return@on true
        }
    }

    override fun defineDestinationOverrides() {
        setDest(IntType.NPC, intArrayOf(INN_KEEPER), "talk-to") { _, _ ->
            return@setDest Location.create(2843, 10193, 1)
        }
    }

}
