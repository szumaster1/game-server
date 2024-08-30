package content.region.karamja.quest.monkeymadness

import content.region.karamja.quest.monkeymadness.dialogue.*
import content.region.karamja.dialogue.apeatoll.dungeon.ZooknockAfterBattleDialogueFile
import content.region.karamja.dialogue.apeatoll.dungeon.ZooknockDialogue
import content.region.karamja.dialogue.apeatoll.dungeon.ZooknockDialogueFile
import core.api.*
import cfg.consts.*
import core.game.component.Component
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.combat.DeathTask
import core.game.node.entity.npc.NPC
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.map.RegionManager.getLocalNpcs
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.network.packet.PacketRepository
import core.network.packet.context.CameraContext
import core.network.packet.context.ContainerContext
import core.network.packet.outgoing.CameraViewPacket
import core.network.packet.outgoing.ContainerPacket

/**
 * Monkey madness listeners.
 */
class MonkeyMadnessListeners : InteractionListener {

    companion object {
        val mspeakAmuletUnstrung = Items.MSPEAK_AMULET_4022
        val monkeyMadnessPuzzleComponent = Components.TRAIL_PUZZLE_363
        val itemIds = IntArray(4031 - 4024 + 1) { it + 4024 }
    }

    override fun defineListeners() {

        /*
         * Interaction with Zooknock.
         */

        on(intArrayOf(NPCs.ZOOKNOCK_1425, NPCs.ZOOKNOCK_1426), IntType.NPC, "talk-to"){ player, npc ->
            if (getQuestStage(player, "Monkey Madness") == 96) {
                openDialogue(player, ZooknockAfterBattleDialogueFile(), npc)
            } else if (getQuestStage(player, "Monkey Madness") == 30 || getQuestStage(player, "Monkey Madness") == 31) {
                openDialogue(player, ZooknockDialogue(), npc)
            } else {
                openDialogue(player, ZooknockDialogueFile(0), npc)
            }
            return@on true
        }

        /*
         * Interaction with Waydar NPC.
         */

        on(intArrayOf(NPCs.WAYDAR_1408, NPCs.WAYDAR_1409, NPCs.WAYDAR_1410), IntType.NPC, "talk-to"){ player, npc ->
            when(player.location.regionId) {
                9626 -> openDialogue(player, WaydarDialogue(), npc)
                11562 -> openDialogue(player, WaydarCrashIslandDialogue(), npc)
            }
            return@on true
        }

        /*
         * Interaction with Monkeys.
         */

        on(NPCs.MONKEY_1463, IntType.NPC, "talk-to"){ player, npc ->
            if (player.equipment.containsAtLeastOneItem(IntArray(4031 - 4024 + 1) { it + 4024 }) &&
                player.equipment.containsAtLeastOneItem(IntArray(4022 - 4021 + 1) { it + 4021 }) &&
                getQuestStage(player, "Monkey Madness") == 33)
            {
                openDialogue(player, MonkeyDialogue(), npc)
            }
            return@on true
        }

        /*
         * Interaction with Monkey child.
         */

        on(NPCs.MONKEY_CHILD_1452, IntType.NPC, "talk-to"){ player, npc ->
            if (getAttribute(player, "/save:mm:first-talk", false)) {
                openDialogue(player, MonkeyChildSecondDialogue(), npc)
            }
            if (getAttribute(player, "/save:mm:second-talk", false)) {
                openDialogue(player, MonkeyChildThirdDialogue(), npc)
            }
            if (getAttribute(player, "/save:mm:third-talk", false)) {
                openDialogue(player, MonkeyChildBananasDialogue(), npc)
            }
            if (getAttribute(player, "/save:mm:talk-banana", false)) {
                openDialogue(player, MonkeyChildTalismanDialogue(), npc)
            }
            if (player.equipment.containsItem(Item(Items.MSPEAK_AMULET_4022))) {
                openDialogue(player, MonkeyChildFirstDialogue(), npc)
            } else {
                openDialogue(player, MonkeyChildWithoutAmuletDialogue(), npc)
            }
            return@on true
        }

        /*
         * Interaction with Lumbo.
         */

        on(NPCs.LUMDO_1419, IntType.NPC, "talk-to"){ player, npc ->
            openDialogue(player, LumdoDialogue(), npc)
            return@on true
        }

        /*
         * Interaction with Kruk.
         */

        on(NPCs.KRUK_1441, IntType.NPC, "talk-to"){ player, npc ->
            if(player.questRepository.getQuest("Monkey Madness").getStage(player) >= 35) {
                openDialogue(player, KrukDialogue(), npc)
            }
            return@on true
        }

        /*
         * Interaction with Gate.
         */
        on(intArrayOf(4787,4788), IntType.SCENERY, "Open") { player, _ ->
            if(player.equipment.containsItem(Item(Items.MONKEY_GREEGREE_4031))){
                sendNPCDialogue(player, NPCs.KRUK_1441, "Open the gate! A monkey wishes to pass!")

                var walkToY = 0;
                if (player.location.y > 2766)
                    walkToY = 2764
                else
                    walkToY = 2768

                DoorActionHandler.handleAutowalkDoor(
                    player,
                    core.game.node.scenery.Scenery(
                        4788,
                        Location.create(2721, 2766, 0),
                        2
                    ),
                    Location.create(2721, walkToY, 0)
                )
            }
            return@on true
        }

        /*
         * Interaction with G.L.O. Carnock NPC.
         */
        on(NPCs.GLO_CARANOCK_1427, IntType.NPC, "talk-to"){ player, npc ->
            when (player.location.regionId) {
                11823 -> openDialogue(player, GLOCaranockDialogue(), npc)
            }
            return@on true
        }

        /*
         * Interaction with Garkor NPC.
         */
        on(NPCs.GARKOR_1411, IntType.NPC, "talk-to"){ player, npc ->
            if(player.equipment.containsAtLeastOneItem(itemIds)) {
                openDialogue(player, GarkorDialogue(), npc)
            }
            if (player.questRepository.getQuest("Monkey Madness").getStage(player) == 46){
                openDialogue(player, GarkorAfterChallengeDialogue(), npc)
            }
            if (player.questRepository.getQuest("Monkey Madness").getStage(player) == 50){
                openDialogue(player, GarkorFinalBattleDialogue(), npc)
            }
            if (player.questRepository.getQuest("Monkey Madness").getStage(player) == 99){
                openDialogue(player, GarkorAfterBattleDialogue(), npc)
            }
            if (getQuestStage(player, "Monkey Madness") == 25){
                openDialogue(player, GarkorFirstDialogue(), npc)
            }
            return@on true
        }

        /*
         * Interaction with Daero NPC.
         */
        on(NPCs.DAERO_1407, IntType.NPC, "talk-to") { player, npc ->
            if (getQuestStage(player, "Monkey Madness") == 100 &&
                !getAttribute(player, "/save:mm:xp_reward", false)
            ) {
                openDialogue(player, DaeroTrainingPostQuestDialogue(), npc)
            } else if (getAttribute(player, "/save:mm:xp_reward", false)) {
                // Call dialogue after choosing the skill
            } else {
                when (player.location.regionId) {
                    9782 -> openDialogue(player, DaeroDialogue(), npc)
                    9626 -> openDialogue(player, DaeroHangarDialogue(), npc)
                }
            }
            return@on true
        }

        /*
         * Interaction with Awowogei NPC.
         */
        on(Scenery.AWOWOGEI_4771, IntType.SCENERY, "talk-to") { player, npc ->

            if (player.questRepository.getQuest("Monkey Madness").getStage(player) == 32) {
                openDialogue(player, AwowogeiDialogue(), NPC(NPCs.AWOWOGEI_1448))
            } else {
                openDialogue(player, AwowogeiChallengeDialogue(), NPC(NPCs.AWOWOGEI_1448))
            }
            return@on true
        }

        /*
         * Interaction with Monkey Minder NPC.
         */
        on(NPCs.MONKEY_MINDER_1469, IntType.NPC, "talk-to") { player, npc ->
            if (player.equipment.containsItem(Item(Items.MONKEY_GREEGREE_4031))) {
                openDialogue(player, MonkeyMinderDialogue(), npc)
            } else {
                openDialogue(player, MonkeyMinderHumanDialogue(), npc)
            }
            return@on true
        }

        /*
         * Interaction with Crate.
         */
        on(Scenery.CRATE_4746, IntType.SCENERY, "Search") { player, npc ->
            openDialogue(player, HangarCrateDialogue(), npc)
            return@on true
        }

        /*
         * Interaction with controls.
         */
        on(Items.SPARE_CONTROLS_4002, IntType.ITEM, "View") { player, _ ->
            val puzzlePieces: Array<Item?>? = ((3904..3950 step 2).toList().map { Item(it) } + Item(-1)).toTypedArray()

            val settings = IfaceSettingsBuilder().build()
            player.packetDispatch.sendIfaceSettings(settings, 6, monkeyMadnessPuzzleComponent, 0, 25)
            player.interfaceManager.open(Component(monkeyMadnessPuzzleComponent))
            PacketRepository.send(
                ContainerPacket::class.java,
                ContainerContext(player, -1, -1, 140, puzzlePieces, 25, false)
            )
            return@on true
        }

        /*
         * Interaction with Waydar Puzzle.
         */
        on(Scenery.REINITIALISATION_PANEL_4871, IntType.SCENERY, "Operate") { player, _ ->
            if (!getAttribute(player, "/save:mm:puzzle-done", false)) {

                //Monkey Madness Puzzle = 363, Child 6, Pieces = 25
                val settings = IfaceSettingsBuilder().enableAllOptions().build()
                player.packetDispatch.sendIfaceSettings(settings, 6, monkeyMadnessPuzzleComponent, 0, 25)
                player.interfaceManager.open(Component(monkeyMadnessPuzzleComponent))

                // List of glider pieces
                var puzzlePieces: Array<Item?>? =
                    ((3904..3950 step 2).toList().map { Item(it) } + Item(-1)).toTypedArray()

                // Starting puzzle
                if (getAttribute(player, "/save:mm:puzzle", arrayOf(Item())).size == 1) {
                    val shuffledInitialPuzzle = puzzlePieces?.clone()
                    shuffledInitialPuzzle?.shuffle()

                    // Save puzzle state for session
                    setAttribute(player, "/save:mm:puzzle", shuffledInitialPuzzle)

                    // Show current puzzle state
                    PacketRepository.send(
                        ContainerPacket::class.java,
                        ContainerContext(player, -1, -1, 140, shuffledInitialPuzzle, 25, false)
                    )
                } else {
                    // For puzzle already started, just retrieve the last state
                    puzzlePieces = getAttribute(player, "/save:mm:puzzle", puzzlePieces?.clone())
                    PacketRepository.send(
                        ContainerPacket::class.java,
                        ContainerContext(player, -1, -1, 140, puzzlePieces, 25, false)
                    )
                }
            } else {
                sendMessage(player, "You have already solved the puzzle.")
            }

            return@on true
        }

        /*
         * Handle creating Monkey Speak amulet (U).
         */
        onUseWith(IntType.SCENERY, Items.ENCHANTED_BAR_4007, 37726) { player, _, _ ->
            if (!player.inventory.containsItem(Item(Items.MAMULET_MOULD_4020)))
                sendItemDialogue(
                    player,
                    Items.MAMULET_MOULD_4020,
                    "You need the M'amulet Mould in order to make a M'speak amulet"
                ).also { return@onUseWith true }
            player.animate(Animation(Animations.HUMAN_FURNACE_SMELTING_3243))
            removeItem(player, Items.ENCHANTED_BAR_4007)
            addItem(player, mspeakAmuletUnstrung)
            return@onUseWith true
        }

        /*
         * Handle creating Monkey Speak amulet using ball of wool and unstrug amulet.
         */
        onUseWith(IntType.ITEM, Items.BALL_OF_WOOL_1759, mspeakAmuletUnstrung) { player, _, _ ->
            removeItem(player, Item(Items.BALL_OF_WOOL_1759))
            removeItem(player, Item(mspeakAmuletUnstrung))
            addItem(player, Items.MSPEAK_AMULET_4021)
            return@onUseWith true
        }

        /*
         * Handle Banana tree search interaction.
         */
        on(Scenery.BANANA_TREE_4749, IntType.SCENERY, "Search") { player, npc ->
            addItem(player, Items.BANANA_1963)
            return@on true
        }

        val itemIds = IntArray(4031 - 4024 + 1) { it + 4024 }
        onEquip(itemIds) { player, _ ->
            player.graphics(Graphic(134, 96))
            player.appearance.transformNPC(NPCs.MONKEY_1463)
            val localNpcs = getLocalNpcs(player, 20)
            localNpcs.forEach { npc: NPC ->
                npc.isAggressive = false
            }
            return@onEquip true
        }

        onUnequip(itemIds) { player, _ ->
            player.graphics(Graphic(134, 96))
            player.appearance.transformNPC(-1)
            val localNpcs = getLocalNpcs(player, 20)
            localNpcs.forEach { npc: NPC ->
                npc.isAggressive = true
            }
            return@onUnequip true
        }

        /*
         * Handle equip the Tenth squad sigil.
         */
        onEquip(Items.TENTH_SQUAD_SIGIL_4035) { player, _ ->
            sendDialogueOptions(player, "Let the sigil teleport you when worn?")
            setQuestStage(player, "Monkey Madness", 94)

            PacketRepository.send(
                CameraViewPacket::class.java,
                CameraContext(player, CameraContext.CameraType.SHAKE, 4, 4, 0, 4, 4)
            )

//            PacketRepository.send(
//                CameraViewPacket::class.java,
//                CameraContext(player, CameraType.RESET, 0, 0, 0, 0, 0)
//

            //fadetoblack()
            teleport(player, Location.create(2705, 9175, 1))
            //fadefromblack()
            //cutscene
            //set all the squad there
            //show Garkor dialogue to ready yourself
            //summon demon


            val localNpcs = getLocalNpcs(player.location, 10)
            localNpcs.forEach { npc: NPC ->
                if (npc.name.equals("Jungle demon"))
                    npc.isAggressive = true
                npc.attack(player)

                if (DeathTask.isDead(npc)) {
                    setQuestStage(player, "Monkey Madness", 95)
                }

            }
            return@onEquip true
        }
    }
}
