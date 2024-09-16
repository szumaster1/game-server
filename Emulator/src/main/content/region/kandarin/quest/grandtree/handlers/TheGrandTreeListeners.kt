package content.region.kandarin.quest.grandtree.handlers

import content.global.handlers.iface.ScrollInterface
import content.region.kandarin.quest.grandtree.cutscene.BlackDemonCutscene
import content.region.kandarin.quest.grandtree.dialogue.KingNarnodeUnderGroundDialogue
import content.region.kandarin.quest.grandtree.dialogue.ShipyardWorkerDialogueFile
import core.api.*
import cfg.consts.Components
import cfg.consts.Items
import cfg.consts.NPCs
import cfg.consts.Sounds
import content.region.karamja.apeatoll.quest.mm.dialogue.KingNarnodeMonkeyMadnessDialogue
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

/**
 * The grand tree listeners.
 */
class TheGrandTreeListeners : InteractionListener {

    /**
     * Unlock t u z o door.
     */
    fun unlockTUZODoor(player: Player) {
        if (getAttribute(player, GTUtils.TWIG_0, false) &&
            getAttribute(player, GTUtils.TWIG_1, false) &&
            getAttribute(player, GTUtils.TWIG_2, false) &&
            getAttribute(player, GTUtils.TWIG_3, false)
        ) {
            sendDialogue(player, "With a grinding of machinery, a trapdoor snaps open!")
            //SceneryBuilder.replace(Scenery(2444, Location(2487,3464,2), 22 , 2), Scenery(2445, Location(2487,3464,2), 22 , 2),20)
        }
    }

    override fun defineDestinationOverrides() {
        setDest(IntType.NPC, intArrayOf(NPCs.CHARLIE_673), "talk-to") { player, _ ->
            return@setDest player.location
        }
    }

    override fun defineListeners() {
        on(NPCs.KING_NARNODE_SHAREEN_670, IntType.NPC, "talk-to") { player, npc ->
            val aboveground = 9782
            when {
                getQuestStage(player, "The Grand Tree") == 100 -> openDialogue(player, KingNarnodeMonkeyMadnessDialogue(), npc)
                player.location.regionId == aboveground -> openDialogue(player, KingNarnodeUnderGroundDialogue(), npc)
                player.location.regionId != aboveground -> openDialogue(player, KingNarnodeUnderGroundDialogue(), npc)
            }
            return@on true
        }

        on(GTUtils.HAZELMERE_SCROLL, IntType.ITEM, "read") { player, node ->
            scrollHandler(player, node.asItem())
            return@on true
        }

        on(GTUtils.LUMBER_ORDER_SCROLL, IntType.ITEM, "read") { player, node ->
            scrollHandler(player, node.asItem())
            return@on true
        }

        on(GTUtils.INVASION_PLANS_SCROLL, IntType.ITEM, "read") { player, _ ->
            ScrollInterface.scrollSetup(player, Components.MESSAGESCROLL_220, GTUtils.INVASION_PLANS_SCROLL_CONTENT)
            return@on true
        }

        on(2444, IntType.SCENERY, "open") { player, node ->
            var loc = Location(2487, 3464, 2)
            if (node.location == loc && !isQuestComplete(player, "The Grand Tree")) {
                if (getAttribute(player, GTUtils.TWIG_0, false) &&
                    getAttribute(player, GTUtils.TWIG_1, false) &&
                    getAttribute(player, GTUtils.TWIG_2, false) &&
                    getAttribute(player, GTUtils.TWIG_3, false)
                ) {
                    animate(player, 827)
                    BlackDemonCutscene(player).start()
                }
            } else {
                sendDialogue(player, "The trapdoor won't open.")
            }
            return@on true
        }

        on(2446, IntType.SCENERY, "open") { player, node ->
            if (node.location == Location(2463, 3497, 0) && isQuestComplete(player, "The Grand Tree")) {
                player.animator.animate(Animation(828))
                // Go to tunnels
                teleport(player, Location(2464, 9897, 0))
            }
            return@on true
        }

        on(2436, IntType.SCENERY, "open"){ player, _ ->
            sendMessage(player, "The chest is locked.")
            return@on true
        }

        onUseWith(IntType.SCENERY, 788, 2436) { player, _, _ ->
            if (!inInventory(player, Items.INVASION_PLANS_794)) {
                SceneryBuilder.replace(
                    Scenery(2436, Location(2482, 3462, 1)),
                    Scenery(2437, Location(2482, 3462, 1)),
                    2
                )
                animate(player, 538)
                sendItemDialogue(player, Items.INVASION_PLANS_794, "You found a scroll!")
                addItemOrDrop(player, Items.INVASION_PLANS_794)
                if (getQuestStage(player, "The Grand Tree") < 60)
                    setQuestStage(player, "The Grand Tree", 60)
            } else {
                sendDialogue(player, "The chest is locked.")
            }
            return@onUseWith true
        }
        onUseWith(IntType.SCENERY, Items.TWIGS_789, 2440) { player, used, with ->
            setAttribute(player, GTUtils.TWIG_0, true)
            removeItem(player, used.asItem())
            GroundItemManager.create(used.asItem(), with.location, player)
            unlockTUZODoor(player)
            return@onUseWith true
        }
        onUseWith(IntType.SCENERY, Items.TWIGS_790, 2441) { player, used, with ->
            setAttribute(player, GTUtils.TWIG_1, true)
            removeItem(player, used.asItem())
            GroundItemManager.create(used.asItem(), with.location, player)
            unlockTUZODoor(player)
            return@onUseWith true
        }
        onUseWith(IntType.SCENERY, Items.TWIGS_791, 2442) { player, used, with ->
            setAttribute(player, GTUtils.TWIG_2, true)
            removeItem(player, used.asItem())
            GroundItemManager.create(used.asItem(), with.location, player)
            unlockTUZODoor(player)
            return@onUseWith true
        }
        onUseWith(IntType.SCENERY, Items.TWIGS_792, 2443) { player, used, with ->
            setAttribute(player, GTUtils.TWIG_3, true)
            removeItem(player, used.asItem())
            GroundItemManager.create(used.asItem(), with.location, player)
            unlockTUZODoor(player)
            return@onUseWith true
        }
        // Removing twigs
        on(Items.TWIGS_789, IntType.GROUNDITEM, "take") { player, _ ->
            setAttribute(player, GTUtils.TWIG_0, false)
            return@on true
        }
        on(Items.TWIGS_790, IntType.GROUNDITEM, "take") { player, _ ->
            setAttribute(player, GTUtils.TWIG_1, false)
            return@on true
        }
        on(Items.TWIGS_791, IntType.GROUNDITEM, "take") { player, _ ->
            setAttribute(player, GTUtils.TWIG_2, false)
            return@on true
        }
        on(Items.TWIGS_792, IntType.GROUNDITEM, "take") { player, _ ->
            setAttribute(player, GTUtils.TWIG_3, false)
            return@on true
        }
        on(2435, IntType.SCENERY, "search") { player, _ ->
            if (getQuestStage(player, "The Grand Tree") >= 47 && !inInventory(
                    player,
                    Items.GLOUGHS_JOURNAL_785
                )
            ) {
                sendItemDialogue(player, Items.GLOUGHS_JOURNAL_785, "You've found Glough's Journal!")
                addItemOrDrop(player, Items.GLOUGHS_JOURNAL_785)
            } else if (freeSlots(player) < 1) {
                sendDialogue(player, "You have no free space to hold any more items.")
            } else {
                sendDialogue(player, "The cupboard is empty.")
            }
            return@on true
        }

        // Roots for Daconia rock
        on(32319, IntType.SCENERY, "search") { player, node ->
            if (getQuestStage(player, "The Grand Tree") < 99 || player.hasItem(Item(Items.DACONIA_ROCK_793))) {
                return@on true
            }
            // RNG for which root the rock is under
            if (node.location == GTUtils.ROOTS_LOCATION[getAttribute(player, GTUtils.DRACONIA_ROCK, 1)]) {
                sendItemDialogue(player, Item(Items.DACONIA_ROCK_793), "You've found a Daconia rock!")
                addItemOrDrop(player, Items.DACONIA_ROCK_793)
            }
            return@on true
        }

        on(GTUtils.KARAMJA_GATE, IntType.SCENERY, "open") { player, _ ->
            if (getQuestStage(player, "The Grand Tree") == 55) {
                if (player.location.x < 2945) {
                    findLocalNPC(player, NPCs.SHIPYARD_WORKER_675)?.let {
                        face(it, player, 1)
                        face(player, it, 1)
                    }
                    openDialogue(player, ShipyardWorkerDialogueFile(), NPC(NPCs.SHIPYARD_WORKER_675))
                } else {
                    DoorActionHandler.autowalkFence(player, Scenery(2438, Location(2945, 3041, 0)), 2432, 2439)
                }
            } else {
                findLocalNPC(player, NPCs.SHIPYARD_WORKER_675)?.let { face(player, it) }
                player.dialogueInterpreter.open(675)
            }
            return@on true
        }

        on(2451, IntType.SCENERY, "push") { player, roots ->
            if (hasRequirement(player, "The Grand Tree")) {
                val outsideMine =
                    player.location == Location.create(2467, 9903, 0) || player.location == Location.create(
                        2468,
                        9903,
                        0
                    )
                if (outsideMine) {
                    forceMove(player, player.location, player.location.transform(0, 2, 0), 25, 60, null, 819)
                } else {
                    forceMove(player, player.location, player.location.transform(0, -2, 0), 25, 60, null, 819)
                }
                animate(player, 2572, false)
                animateScenery(roots.asScenery(), 452)
                playGlobalAudio(player.location, Sounds.TANGLEVINE_APPEAR_2316)
            }
            return@on true
        }
    }

    private fun scrollHandler(player: Player, item: Item) {
        val id = item.id

        openInterface(player, Components.BLANK_SCROLL_222).also {
            when (id) {
                GTUtils.HAZELMERE_SCROLL -> sendString(player, GTUtils.HAZELMERE_SCROLL_CONTENT.joinToString("<br>"), Components.BLANK_SCROLL_222, 5)
                GTUtils.LUMBER_ORDER_SCROLL -> sendString(player, GTUtils.LUMBER_ORDER_SCROLL_CONTENT.joinToString("<br>"), Components.BLANK_SCROLL_222, 1)
            }
        }
    }
}

