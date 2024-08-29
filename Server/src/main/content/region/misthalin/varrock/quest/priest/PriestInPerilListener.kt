package content.region.misthalin.varrock.quest.priest

import core.api.*
import cfg.consts.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.world.map.Location
import core.game.node.item.Item

/**
 * Represents the Priest in peril listener.
 */
class PriestInPerilListener : InteractionListener {

    override fun defineListeners() {
        on(intArrayOf(Scenery.TRAPDOOR_30571, Scenery.TRAPDOOR_30573), IntType.SCENERY, "open") { player, node ->
            replaceScenery(node.asScenery(), Scenery.LADDER_30572, 80)
            sendMessage(player, "The trapdoor opens...")
            return@on true
        }

        on(Scenery.LADDER_30572, IntType.SCENERY, "climb-down", "close") { player, node ->
            if (getUsedOption(player) == "climb-down") {
                animate(player, Animations.HUMAN_BURYING_BONES_827)
                sendMessage(player, "You climb down through the trapdoor...")
                if (player.location.y < 3506) {
                    queueScript(player, 2, QueueStrength.NORMAL) {
                        teleport(player, Location.create(3440, 9887, 0))
                    }
                } else {
                    queueScript(player, 2, QueueStrength.NORMAL) {
                        teleport(player, Location.create(3405, 9906, 0))
                    }
                }
            }
            if (getUsedOption(player) == "close") {
                if (player.location.y < 3506) {
                    removeScenery(core.game.node.scenery.Scenery(Scenery.LADDER_30572, Location(3422, 3484, 0)))
                    addScenery(Scenery.TRAPDOOR_30573, Location.create(3422, 3484, 0), rotation = 3)
                } else {
                    removeScenery(core.game.node.scenery.Scenery(Scenery.LADDER_30572, Location(3405, 3507, 0)))
                    addScenery(Scenery.TRAPDOOR_30573, Location(3405, 3507, 0))
                }
            }
            return@on true
        }

        on(Scenery.LADDER_30575, IntType.SCENERY, "climb-up") { player, _ ->
            animate(player, Animations.HUMAN_CLIMB_STAIRS_828)
            queueScript(player, 2, QueueStrength.NORMAL) {
                teleport(player, Location(3405, 3506, 0))
            }
            return@on true
        }

        onUseWith(IntType.SCENERY, Items.TINDERBOX_590, Scenery.MONUMENT_3493) { player, used, _ ->
            if (!getAttribute(player, "priest_in_peril:tinderbox", false) && removeItem(player, used)) {
                addItem(player, Items.GOLDEN_TINDERBOX_2946)
                sendMessage(player, "You swap the tinderbox for the golden tinderbox.")
                setAttribute(player, "/save:priest_in_peril:tinderbox", true)
            }

            return@onUseWith true
        }

        onUseWith(IntType.SCENERY, Items.CANDLE_36, Scenery.MONUMENT_3494) { player, used, _ ->
            if (!getAttribute(player, "priest_in_peril:candle", false) && removeItem(player, used)) {
                addItem(player, Items.GOLDEN_CANDLE_2947)
                sendMessage(player, "You swap the candle for the golden candle.")
                setAttribute(player, "/save:priest_in_peril:candle", true)
            }

            return@onUseWith true
        }

        onUseWith(IntType.SCENERY, Items.EMPTY_POT_1931, Scenery.MONUMENT_3495) { player, used, _ ->
            if (!getAttribute(player, "priest_in_peril:pot", false) && removeItem(player, used)) {
                addItem(player, Items.GOLDEN_POT_2948)
                sendMessage(player, "You swap the pot for the golden pot.")
                setAttribute(player, "/save:priest_in_peril:pot", true)
            }

            return@onUseWith true
        }

        onUseWith(IntType.SCENERY, Items.HAMMER_2347, Scenery.MONUMENT_3496) { player, used, _ ->
            if (!getAttribute(player, "priest_in_peril:hammer", false) && removeItem(player, used)) {
                addItem(player, Items.GOLDEN_HAMMER_2949)
                sendMessage(player, "You swap the hammer for the golden hammer.")
                setAttribute(player, "/save:priest_in_peril:hammer", true)
            }

            return@onUseWith true
        }

        onUseWith(IntType.SCENERY, Items.FEATHER_314, Scenery.MONUMENT_3497) { player, used, _ ->
            if (!getAttribute(player, "priest_in_peril:feather", false) && removeItem(player, Item(used.id, 1))) {
                addItem(player, Items.GOLDEN_FEATHER_2950)
                sendMessage(player, "You swap the feather for the golden feather.")
                setAttribute(player, "/save:priest_in_peril:feather", true)
            }

            return@onUseWith true
        }

        onUseWith(IntType.SCENERY, Items.NEEDLE_1733, Scenery.MONUMENT_3498) { player, used, _ ->
            if (!getAttribute(player, "priest_in_peril:needle", false) && removeItem(player, Item(used.id, 1))) {
                addItem(player, Items.GOLDEN_NEEDLE_2951)
                sendMessage(player, "You swap the needle for the golden needle.")
                setAttribute(player, "/save:priest_in_peril:needle", true)
            }

            return@onUseWith true
        }

        onUseWith(IntType.SCENERY, Items.GOLDEN_KEY_2944, Scenery.MONUMENT_3499) { player, used, _ ->
            if (!getAttribute(player, "priest_in_peril:key", false) && removeItem(player, used)) {
                addItem(player, Items.IRON_KEY_2945)
                sendMessage(player, "You swap the golden key for the iron key.")
                setAttribute(player, "/save:priest_in_peril:key", true)
            }

            return@onUseWith true
        }

        onUseWith(IntType.SCENERY, Items.BUCKET_1925, Scenery.WELL_3485) { player, used, _ ->
            if (!removeItem(player, used)) {
                return@onUseWith false
            }

            addItem(player, Items.BUCKET_OF_WATER_2953)
            sendMessage(player, "You fill the bucket from the well.")

            return@onUseWith true
        }

        onUseWith(IntType.SCENERY, Items.IRON_KEY_2945, Scenery.CELL_DOOR_3463) { player, used, _ ->
            if (!removeItem(player, used)) {
                return@onUseWith false
            }

            setQuestStage(player, "Priest in Peril", 15)
            sendMessage(player, "You have unlocked the cell door.")

            val npc = core.game.node.entity.npc.NPC.create(NPCs.DREZEL_7690, player.location)
            npc.name = "Drezel"
            sendNPCDialogue(
                player,
                npc.id,
                "Oh! Thank you! You have found the key!",
                core.game.dialogue.FacialExpression.HALF_GUILTY
            )

            return@onUseWith true
        }

        onUseWith(IntType.SCENERY, Items.BUCKET_OF_WATER_2954, Scenery.MORYTANIA_COFFIN_30728) { player, used, _ ->
            if (!removeItem(player, used)) {
                return@onUseWith false
            }
            addItem(player, Items.BUCKET_1925)
            animate(player, 1077)
            playAudio(player, Sounds.HOLY_WATER_POUR_1733)
            setQuestStage(player, "Priest in Peril", 16)
            sendMessage(player, "You pour the blessed water over the coffin...")
            return@onUseWith true
        }
    }
}
