package content.region.karamja.handlers

import core.api.*
import core.api.consts.*
import core.game.dialogue.FacialExpression
import core.game.global.action.ClimbActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

/**
 * Karamja listeners.
 */
class KaramjaListeners : InteractionListener {

    companion object {
        // Define the locations and IDs of various objects and NPCs.
        private val MUSA_POINT_DUNGEON = Location(2856, 9567, 0)
        private val VOLCANO_RIM = Location(2856, 3167, 0)
        private val PINEAPPLE_PLANT = intArrayOf(Scenery.PINEAPPLE_PLANT_1408, Scenery.PINEAPPLE_PLANT_1409, Scenery.PINEAPPLE_PLANT_1410, Scenery.PINEAPPLE_PLANT_1411, Scenery.PINEAPPLE_PLANT_1412, Scenery.PINEAPPLE_PLANT_1413)
        private val CUSTOM_OFFICERS = intArrayOf(NPCs.CUSTOMS_OFFICER_380, NPCs.CUSTOMS_OFFICER_381)
        private const val MUSA_POINT_DUNGEON_ENTRANCE = Scenery.ROCKS_492
        private const val MUSA_POINT_DUNGEON_EXIT = Scenery.CLIMBING_ROPE_1764
        private const val PICK_PINEAPPLE_ANIMATION = 2282
        private const val PINEAPPLE = Items.PINEAPPLE_2114
        private const val SHAKE_TREE_ANIMATION = 2572
        private const val PALM_LEAF = Items.PALM_LEAF_2339
        private const val PALM_TREE_FULL = Scenery.LEAFY_PALM_TREE_2975
        private const val PALM_TREE_EMPTY = Scenery.LEAFY_PALM_TREE_2976
        private const val BANANA_TREE = Scenery.BANANA_TREE_2078
        private const val JIMINUA = NPCs.JIMINUA_560
        private const val NOTED_PURE_ESSENCE = Items.PURE_ESSENCE_7937
        private const val TIADECHE = NPCs.TIADECHE_1164
        private const val SLASH_SOUND = Sounds.MACHETE_SLASH_1286
        private const val WALK_ANIMATION = Animations.HUMAN_WALK_SHORT_819
        private const val SWIPE_WITH_MACHETE = Animations.SWIPE_WITH_MACHETE_TAI_BWO_WANNAI_CLEANUP_2382
        private val MACHETE_ID = intArrayOf(Items.MACHETE_975, Items.JADE_MACHETE_6315, Items.OPAL_MACHETE_6313, Items.RED_TOPAZ_MACHETE_6317)
        private val JUNGLE_BUSH = intArrayOf(Scenery.JUNGLE_BUSH_2892, Scenery.JUNGLE_BUSH_2893)
    }

    // Check if the player has the required item in their equipment or inventory.
    private fun checkRequirement(player: Player): Boolean {
        return anyInEquipment(player, *MACHETE_ID) || anyInInventory(player, *MACHETE_ID)
    }

    // Get the appropriate animation based on the item used.
    private fun getAnimation(item: Int): Animation {
        return when (item) {
            Items.MACHETE_975 -> Animation.create(Animations.MACHETE_910)
            Items.JADE_MACHETE_6315 -> Animation.create(Animations.JADE_MACHETE_2424)
            Items.OPAL_MACHETE_6313 -> Animation.create(Animations.OPAL_MACHETE_2429)
            Items.RED_TOPAZ_MACHETE_6317 -> Animation.create(Animations.RED_TOPAZ_MACHETE_2426)
            else -> Animation.create(Animations.MACHETE_910)
        }
    }

    override fun defineListeners() {

        /**
         * Interaction with jungle bushes.
         */
        on(JUNGLE_BUSH, IntType.SCENERY, "chop-down") { player, node ->
            val randomChop = (1..5).random()
            val chopDown = getAttribute(player, "chop-bush", randomChop)
            if (!checkRequirement(player)) {
                sendMessage(player, "You need a machete to get through this dense jungle.")
                return@on false
            }

            MACHETE_ID.indices.forEach { i ->
                animate(player, getAnimation(i))
                playAudio(player, SLASH_SOUND)
            }

            setAttribute(player, "chop-bush", 0)
            player.pulseManager.run(object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    counter++
                    if (counter != randomChop) {
                        animate(player, SWIPE_WITH_MACHETE)
                    }
                    if (counter == chopDown) {
                        player.walkingQueue.reset()
                        replaceScenery(node.asScenery(), Scenery.SLASHED_BUSH_2895, 20)
                        produceGroundItem(player, Items.LOGS_1511, 1, node.location)
                        rewardXP(player, Skills.WOODCUTTING, 100.0)
                        removeAttribute(player, "chop-bush")
                        player.walkingQueue.addPath(node.location.x, node.location.y, true)
                    }
                    return counter == chopDown
                }
            })
            return@on true
        }

        /**
         * Interaction with custom officers.
         */
        on(CUSTOM_OFFICERS, IntType.NPC, "pay-fare") { player, node ->
            if (!isQuestComplete(player, "Pirate's Treasure")) {
                openDialogue(player, node.asNpc().id, node)
                sendMessage(player, "You may only use the Pay-fare option after completing Pirate's Treasure.")
                return@on true
            }
            openDialogue(player, node.asNpc().id, node, true)
            return@on true
        }

        /**
         * Interaction with Musa Point dungeon entrance.
         */
        on(MUSA_POINT_DUNGEON_ENTRANCE, IntType.SCENERY, "climb-down") { player, _ ->
            ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_DOWN, MUSA_POINT_DUNGEON)
            sendMessage(player, "You climb down through the pot hole.")
            return@on true
        }

        /**
         * Interaction with Musa Point dungeon exit.
         */
        on(MUSA_POINT_DUNGEON_EXIT, IntType.SCENERY, "climb-up") { player, _ ->
            ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_UP, VOLCANO_RIM)
            sendMessage(player, "You climb up the hanging rope...")
            sendMessageWithDelay(player, "You appear on the volcano rim.", 1)
            return@on true
        }

        /**
         * Interaction with pineapple plants.
         */
        on(PINEAPPLE_PLANT, IntType.SCENERY, "pick") { player, node ->
            if (!hasSpaceFor(player, Item(PINEAPPLE))) {
                sendMessage(player, "You don't have enough space in your inventory.")
                return@on true
            }
            if (node.id == Scenery.PINEAPPLE_PLANT_1413) {
                sendMessage(player, "There are no pineapples left on this plant.")
                return@on true
            }
            val last: Boolean = node.id == Scenery.PINEAPPLE_PLANT_1412
            if (addItem(player, PINEAPPLE)) {
                animate(player, PICK_PINEAPPLE_ANIMATION)
                playAudio(player, Sounds.PICK_2581, 30)
                replaceScenery(node.asScenery(), node.id + 1, if (last) 270 else 40)
                sendMessage(player, "You pick a pineapple.")
            }
            return@on true
        }

        /**
         * Interaction with banana trees.
         */
        on(BANANA_TREE, IntType.SCENERY, "search") { player, _ ->
            sendMessage(player, "There are no bananas left on the tree.")
            return@on true
        }

        /**
         * Interaction with full palm trees.
         */
        on(PALM_TREE_FULL, IntType.SCENERY, "Shake") { player, node ->
            queueScript(player, 0, QueueStrength.WEAK) { stage: Int ->
                when (stage) {
                    0 -> {
                        lock(player, 2)
                        face(player, node)
                        animate(player, SHAKE_TREE_ANIMATION)
                        sendMessage(player, "You give the tree a good shake.")
                        replaceScenery(node.asScenery(), PALM_TREE_EMPTY, 60)
                        return@queueScript delayScript(player, 2)
                    }

                    1 -> {
                        produceGroundItem(player, PALM_LEAF, 1, getPathableRandomLocalCoordinate(player, 1, node.location))
                        sendMessage(player, "A palm leaf falls to the ground.")
                        return@queueScript stopExecuting(player)
                    }

                    else -> return@queueScript stopExecuting(player)
                }
            }
            return@on true
        }

        /**
         * Un-note essences interaction with Jiminua NPC.
         */
        onUseWith(IntType.NPC, NOTED_PURE_ESSENCE, JIMINUA) { player, used, _ ->
            assert(used.id == Items.PURE_ESSENCE_7937)
            val ess: Int = amountInInventory(player, Items.PURE_ESSENCE_7937)
            val coins: Int = amountInInventory(player, Items.COINS_995)
            val freeSpace = freeSlots(player)

            when {
                ess == 0 -> {
                    sendNPCDialogue(player, JIMINUA, "You don't have any essence for me to un-note.", FacialExpression.HALF_GUILTY)
                    return@onUseWith false
                }

                freeSpace == 0 -> {
                    sendNPCDialogue(player, JIMINUA, "You don't have any free space.", FacialExpression.HALF_GUILTY)
                    return@onUseWith false
                }

                coins <= 1 -> {
                    sendNPCDialogue(player, JIMINUA, "I charge 2 gold coins to un-note each pure essence.", FacialExpression.HALF_GUILTY)
                    return@onUseWith false
                }

                else -> {
                    val unnote = minOf(freeSpace.toDouble(), ess.toDouble(), (coins / 2).toDouble()).toInt()
                    removeItem(player, Item(NOTED_PURE_ESSENCE, unnote))
                    removeItem(player, (Item(Items.COINS_995, 2 * unnote)))
                    addItem(player, Items.PURE_ESSENCE_7936, unnote)
                    sendMessage(player, "You hand Jiminua some notes and coins, and she hands you back pure essence.")
                }
            }
            return@onUseWith true
        }

        /**
         * Trade interaction with NPC Tiadeche NPC.
         */
        on(TIADECHE, IntType.NPC, "trade") { player, _ ->
            if (!hasRequirement(player, "Tai Bwo Wannai Trio")) return@on true
            openNpcShop(player, TIADECHE)
            return@on true
        }
    }
}