package content.region.misc.handlers.keldagrim

import content.region.misc.dialogue.keldagrim.BlastFusionHammerDialogue
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.link.TeleportManager
import core.game.world.map.Location
import core.tools.END_DIALOGUE

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
        private const val HIDDEN_TRAPDOOR = Scenery.HIDDEN_TRAPDOOR_28094
        private const val TUNNEL = Scenery.TUNNEL_5014
        private const val INN_KEEPER = NPCs.INN_KEEPER_2176
    }


    override fun defineListeners() {

        on(DOORWAY_1, IntType.SCENERY, "enter") { player, _ ->
            teleport(player, Location(2941, 10179, 0))
            return@on true
        }

        on(DOORWAY_2, IntType.SCENERY, "enter") { player, _ ->
            teleport(player, Location(2435, 5535, 0))
            return@on true
        }

        // Reinald - Smithing Emporium.
        on(REINALD, IntType.NPC, "change-armguards") { player, _ ->
            openInterface(player, 593)
            return@on true
        }

        // Interaction with foreman (Blast furnace mini-game).
        onUseWith(IntType.NPC, FUSION_HAMMER, FOREMAN) { player, _, npc ->
            openDialogue(player, BlastFusionHammerDialogue(), npc)
            return@onUseWith true
        }

        // Cave entrance to Keldagrim.
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

        // Travel interaction between Keldagrim and Grand exchange.
        on(HIDDEN_TRAPDOOR, IntType.SCENERY, "open") { player, _ ->
            val keldagrimVisited = getAttribute(player, "keldagrim-visited", false)
            openDialogue(player, object : DialogueFile() {
                override fun handle(componentID: Int, buttonID: Int) {
                    when (stage) {
                        0 -> {
                            if (!keldagrimVisited) {
                                sendDialogue(player, "Perhaps I should visit Keldagrim first.").also { stage = END_DIALOGUE }
                            } else {
                                options("Travel to Keldagrim", "Nevermind.").also { stage++ }
                            }
                        }

                        10 -> when (buttonID) {
                            1 -> MinecartTravel.goToKeldagrim(player).also { end() }
                            2 -> end()
                        }
                    }
                }
            })
            return@on true
        }

        // Library +1 bookcase interaction.
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
