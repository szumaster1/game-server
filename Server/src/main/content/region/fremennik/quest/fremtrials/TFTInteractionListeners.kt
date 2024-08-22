package content.region.fremennik.quest.fremtrials

import content.global.skill.gathering.woodcutting.WoodcuttingPulse
import content.global.travel.LyreTeleport
import content.region.fremennik.quest.fremtrials.dialogue.CouncilWorkerDialogue
import content.region.fremennik.quest.fremtrials.dialogue.FremennikFishermanDialogue
import content.region.fremennik.quest.fremtrials.npc.KoscheiSession
import core.api.*
import cfg.consts.Items
import cfg.consts.NPCs
import core.game.container.impl.EquipmentContainer.SLOT_WEAPON
import core.game.dialogue.FacialExpression
import core.game.global.action.ClimbActionHandler
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.player.link.music.MusicEntry
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.config.ItemConfigParser
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.map.RegionManager
import core.game.world.update.flag.context.Animation

/**
 * The Fremennik Trials interaction listeners.
 */
class TFTInteractionListeners : InteractionListener {

    companion object {
        private val BEER = intArrayOf(Items.BEER_3803, Items.BEER_1917)
        private val WORKER = NPCs.COUNCIL_WORKMAN_1287
        private val FISH_ALTAR = 4141
        private val FISH = intArrayOf(Items.RAW_BASS_363, Items.RAW_SHARK_383, Items.RAW_SEA_TURTLE_395, Items.RAW_MANTA_RAY_389)
        private val LOW_ALC_KEG = Items.LOW_ALCOHOL_KEG_3712
        private val KEG = Items.KEG_OF_BEER_3711
        private val TINDERBOX = Items.TINDERBOX_590
        private val CHERRY_BOMB = Items.STRANGE_OBJECT_3713
        private val LIT_BOMB = Items.LIT_STRANGE_OBJECT_3714
        private val PIPE = 4162
        private val PORTALIDs = intArrayOf(2273, 2274, 2506, 2507, 2505, 2503, 2504, 5138)
        private val SWENSEN_LADDER = 4158
        private val SWAYING_TREE = 4142
        private val KNIFE = Items.KNIFE_946
        private val TREE_BRANCH = Items.BRANCH_3692
        private val LYRE_IDs = intArrayOf(14591, 14590, 6127, 6126, 6125, 3691, 3690)
        private val THORVALD_LADDER = 34286
        private val THORVALD_LADDER_LOWER = 4188
        private val LALLIS_STEW = 4149
        private val UNSTRUNG_LYRE = Items.UNSTRUNG_LYRE_3688
        private val GOLDEN_FLEECE = Items.GOLDEN_FLEECE_3693
        private val GOLDEN_WOOL = Items.GOLDEN_WOOL_3694
        private val LONGHALL_BACKDOOR = 4148
        private val SHOPNPCS = intArrayOf(NPCs.YRSA_1301, NPCs.SKULGRIMEN_1303, NPCs.THORA_THE_BARKEEP_1300, NPCs.SIGMUND_THE_MERCHANT_1282, NPCs.FISH_MONGER_1315)
        private val SPINNING_WHEEL_IDS = intArrayOf(2644, 4309, 8748, 20365, 21304, 25824, 26143, 34497, 36970, 37476)
        private val STEW_INGREDIENT_IDS = intArrayOf(Items.POTATO_1942, Items.ONION_1957, Items.CABBAGE_1965, Items.PET_ROCK_3695)
        private val FISHERMAN = NPCs.FISHERMAN_1302
    }

    override fun defineListeners() {

        // Listener for talking to the fisherman NPC
        on(FISHERMAN, IntType.NPC, "talk-to") { player, _ ->
            // Opens the dialogue with the Fremennik Fisherman
            openDialogue(player, FremennikFishermanDialogue())
            return@on true
        }

        // Listener for using beer with the worker NPC
        onUseWith(IntType.NPC, BEER, WORKER) { player, beer, _ ->
            // Opens the Council Worker dialogue with the beer item
            player.dialogueInterpreter.open(CouncilWorkerDialogue(0, true, beer.id), NPC(WORKER))
            return@onUseWith true
        }

        // Listener for using fish with the fish altar
        onUseWith(IntType.SCENERY, FISH, FISH_ALTAR) { player, fish, _ ->
            // Checks if the player has a lyre in their inventory
            if (anyInInventory(player, Items.LYRE_3689, Items.ENCHANTED_LYRE_3690)) {
                // Checks if the player has raw bass in their inventory
                if(inInventory(player, Items.RAW_BASS_363)) {
                    // Sends a dialogue if the item is not a bass
                    sendNPCDialogue(player, NPCs.FOSSEGRIMEN_1273, "That's not a bass, it's a very small shark.").also {
                        // Submits a spirit pulse for the fish
                        Pulser.submit(SpiritPulse(player, fish.id))
                    }
                } else {
                    // Submits a spirit pulse for the fish
                    Pulser.submit(SpiritPulse(player, fish.id))
                }
            } else {
                // Checks if the player does not have a lyre
                if(!anyInInventory(player, Items.LYRE_3689, Items.ENCHANTED_LYRE_3690)){
                    // Sends a message indicating the player should have a lyre
                    sendMessage(player, "I should probably have my lyre with me.")
                } else {
                    // Sends a message indicating a greater offering is required
                    sendMessage(player, "Fossegrimen require a greater offering to enchant your lyre.")
                }
            }
            return@onUseWith true
        }

        // Listener for using a low alc keg with a keg
        onUseWith(IntType.ITEM, LOW_ALC_KEG, KEG) { player, _, _ ->
            // Checks if the keg has not been mixed yet
            if (!getAttribute(player, "fremtrials:keg-mixed", false)) {
                // Checks if the player has a cherry bomb
                if (getAttribute(player, "fremtrials:cherrybomb", false)) {
                    // Removes the low alc keg from the player's inventory
                    removeItem(player, LOW_ALC_KEG)
                    // Sets the keg mixed attribute to true
                    setAttribute(player, "/save:fremtrials:keg-mixed", true)
                    // Sends a message about the cherry bomb going off
                    sendMessage(player, "The cherry bomb in the pipe goes off.")
                    // Sends a chat message to local entities
                    RegionManager.getLocalEntitys(player).stream().forEach { e -> e.sendChat("What was THAT??") }
                    // Sends a message about mixing the kegs
                    sendMessage(player, "You mix the kegs together.")
                } else {
                    // Sends a dialogue indicating the player cannot do this right now
                    player.dialogueInterpreter?.sendDialogue("I can't do this right now. I should create", "a distraction.")
                }
            } else return@onUseWith false
            return@onUseWith true
        }

        // Listener for using a tinderbox with a cherry bomb
        onUseWith(IntType.ITEM, TINDERBOX, CHERRY_BOMB) { player, _, _ ->
            // Checks if the cherry bomb is removed successfully
            if (removeItem(player, CHERRY_BOMB)) {
                // Adds a lit bomb to the player's inventory
                addItem(player, LIT_BOMB)
                // Sends a message indicating the object is lit
                sendMessage(player, "You light the strange object.")
            }
            return@onUseWith true
        }

        // Listener for using a knife with a tree branch
        onUseWith(IntType.ITEM, KNIFE, TREE_BRANCH) { player, _, _ ->
            // Checks if the player has the required crafting level
            if (!player.skills.hasLevel(Skills.CRAFTING, 40)) {
                // Sends a dialogue indicating the required crafting level
                sendDialogue(player, "You need 40 crafting to do this!")
                return@onUseWith true
            }
            // Checks if the player has a knife in their inventory
            if (inInventory(player, KNIFE))
            // Submits a branch fletching pulse for the player
                Pulser.submit(BranchFletchingPulse(player))
            else
            // Sends a message indicating the player needs a knife
                sendMessage(player, "You need a knife to do this.")
            return@onUseWith true
        }

        // Listener for using stew ingredients with Lalli's stew
        onUseWith(IntType.SCENERY, STEW_INGREDIENT_IDS, LALLIS_STEW) { player, stewIngredient, _ ->
            // Checks the type of stew ingredient used
            when (stewIngredient.id) {
                Items.ONION_1957 -> {
                    // Sends a dialogue indicating an onion was added
                    sendDialogue(player, "You added an onion to the stew")
                    // Sets the onion added attribute to true
                    setAttribute(player, "/save:lalliStewOnionAdded", true)
                    // Removes the onion from the player's inventory
                    removeItem(player, stewIngredient)
                }

                Items.POTATO_1942 -> {
                    // Sends a dialogue indicating a potato was added
                    sendDialogue(player, "You added a potato to the stew")
                    // Sets the potato added attribute to true
                    setAttribute(player, "/save:lalliStewPotatoAdded", true)
                    // Removes the potato from the player's inventory
                    removeItem(player, stewIngredient)
                }

                Items.CABBAGE_1965 -> {
                    // Sends a dialogue indicating a cabbage was added
                    sendDialogue(player, "You added a cabbage to the stew")
                    // Sets the cabbage added attribute to true
                    setAttribute(player, "/save:lalliStewCabbageAdded", true)
                    // Removes the cabbage from the player's inventory
                    removeItem(player, stewIngredient)
                }

                Items.PET_ROCK_3695 -> {
                    // Sends a dialogue indicating a pet rock was added
                    sendDialogue(player, "You added your dear pet rock to the stew")
                    // Sets the rock added attribute to true
                    setAttribute(player, "/save:lalliStewRockAdded", true)
                    // Removes the pet rock from the player's inventory
                    removeItem(player, stewIngredient)
                }
            }
            return@onUseWith true
        }

        // Listener for using golden fleece with spinning wheel
        onUseWith(IntType.SCENERY, GOLDEN_FLEECE, *SPINNING_WHEEL_IDS) { player, _, _ ->
            // Checks if the golden fleece is removed successfully
            if (removeItem(player, GOLDEN_FLEECE)) {
                // Adds golden wool to the player's inventory
                addItem(player, GOLDEN_WOOL)
                // Animates the spinning action
                animate(player, 896)
                // Sends a dialogue indicating the fleece was spun
                sendDialogue(player, "You spin the Golden Fleece into a ball of Golden Wool")
            }
            return@onUseWith true
        }

        // Listener for using unstrung lyre with golden wool
        onUseWith(IntType.ITEM, UNSTRUNG_LYRE, GOLDEN_WOOL) { player, _, _ ->
            // Checks if the player has the required fletching level
            if (player.getSkills().getLevel(Skills.FLETCHING) >= 25) {
                // Checks if both items are removed successfully
                if (removeItem(player, GOLDEN_WOOL) &&
                    removeItem(player, Items.UNSTRUNG_LYRE_3688)
                ) {
                    // Animates the stringing action
                    animate(player, 1248)
                    // Adds the lyre to the player's inventory
                    addItem(player, Items.LYRE_3689)
                    // Sends a dialogue indicating the lyre was strung
                    sendDialogue(player, "You string the Lyre with the Golden Wool.")
                }
            } else sendDialogue(player, "You need 25 fletching to do this!")
            return@onUseWith true
        }

        // Listener for opening the long hall backdoor
        on(LONGHALL_BACKDOOR, IntType.SCENERY, "open") { player, node ->
            // Checks if the player has enchanted their lyre
            when {
                getAttribute(player, "LyreEnchanted", false) -> {
                    // Sends a dialogue indicating permission to pass
                    sendNPCDialogue(player, 1278, "Yeah you're good to go through. Olaf tells me you're some kind of outerlander bard here on tour. I doubt you're worse than Olaf is.")
                    // Handles the door action
                    DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
                }

                // Checks if the player has played the lyre concert
                getAttribute(player, "lyreConcertPlayed", false) -> {
                    // Handles the door action
                    DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
                }

                // If neither condition is met, sends a message denying access
                else -> {
                    sendNPCDialogue(player, 1278, "I didn't give you permission to go backstage!")
                }
            }
            return@on true
        }

        // Listener for playing the lyre
        on(LYRE_IDs, IntType.ITEM, "play") { player, lyre ->
            // Checks if the player is on stage and has not played the concert
            if (getAttribute(player, "onStage", false) && !getAttribute(player, "lyreConcertPlayed", false)) {
                // Submits a lyre concert pulse for the player
                Pulser.submit(LyreConcertPulse(player, lyre.id))
            } else if (getQuestStage(player, "Fremennik Trials") < 20 || !isQuestComplete(player, "Fremennik Trials")) {
                // Sends a message indicating the player lacks knowledge to play
                sendMessage(player, "You lack the knowledge to play this.")
            } else if (LYRE_IDs.isLast(lyre.id)) {
                // Sends a message indicating the lyre is out of charges
                sendMessage(player, "Your lyre is out of charges!")
            } else if (hasTimerActive(player, "teleblock")) {
                // Sends a message indicating a magical force is preventing teleportation
                sendMessage(player, "A magical force has stopped you from teleporting.")
            } else {
                // Checks if the lyre is removed successfully
                if (removeItem(player, lyre.asItem())) {
                    // Adds the next lyre to the player's inventory
                    addItem(player, LYRE_IDs.getNext(lyre.id))
                    // Submits a lyre teleport pulse for the player
                    Pulser.submit(LyreTeleport(player))
                }
            }
            return@on true
        }

        // Handle the action when a player interacts with the PIPE scenery
        on(PIPE, IntType.SCENERY, "put-inside") { player, _ ->
            // Check if the player has a lit bomb in their inventory
            if (inInventory(player, LIT_BOMB)) {
                // Notify the player that they have stuffed the lit object into the pipe
                sendMessage(player, "You stuff the lit object into the pipe.")
                // Set an attribute indicating the player has completed a specific action
                setAttribute(player, "/save:fremtrials:cherrybomb", true)
                // Remove the lit bomb from the player's inventory
                removeItem(player, LIT_BOMB)
            } else {
                // Notify the player that they cannot put anything in the pipe
                sendMessage(player, "What am I supposed to put in there? A shoe?")
            }
            // Indicate that the event was handled successfully
            return@on true
        }

        // Handle the action when a player interacts with a portal
        on(PORTALIDs, IntType.SCENERY, "use") { player, portal ->
            // Set the player's teleport location based on the portal ID
            player.properties?.teleportLocation = when (portal.id) {
                2273 -> DestRoom(2639, 10012, 2645, 10018).getCenter() // Teleport to specific coordinates
                2274 -> DestRoom(2650, 10034, 2656, 10040).getCenter() // Teleport to specific coordinates
                2506 -> DestRoom(2662, 10023, 2669, 10029).getCenter() // Teleport to specific coordinates
                2507 -> DestRoom(2626, 10023, 2633, 10029).getCenter() // Teleport to specific coordinates
                2505 -> DestRoom(2650, 10001, 2656, 10007).getCenter() // Teleport to specific coordinates
                2503 -> DestRoom(2662, 10012, 2668, 10018).getCenter() // Teleport to specific coordinates
                2504 -> {
                    // Set an attribute indicating the player has completed a maze
                    setAttribute(player, "/save:fremtrials:maze-complete", true)
                    // Teleport to specific coordinates
                    DestRoom(2662, 10034, 2668, 10039).getCenter()
                }
                else -> getRandomLocation(player) // Teleport to a random location if no match
            }
            // Indicate that the event was handled successfully
            return@on true
        }

        // Handle the action when a player interacts with the SWENSEN_LADDER
        on(SWENSEN_LADDER, IntType.SCENERY, "climb") { player, _ ->
            // Check if the player has not accepted the Swensen quest
            if (!getAttribute(player, "fremtrials:swensen-accepted", false)) {
                // Send a dialogue from the NPC indicating the player cannot proceed
                sendNPCDialogue(player, 1283, "Where do you think you're going?", FacialExpression.ANGRY)
            }
            // Indicate that the event was handled successfully
            return@on true
        }

        // Handle the action when a player interacts with the THORVALD_LADDER
        on(THORVALD_LADDER, IntType.SCENERY, "climb-down") { player, _ ->
            // Check if the player has completed the quest or voted for Thorvald
            if (isQuestComplete(player, "Fremennik Trials") || getAttribute(player, "fremtrials:thorvald-vote", false)) {
                // Notify the player they have no reason to go back down
                sendMessage(player, "You have no reason to go back down there.")
                return@on true
            } else if (!getAttribute(player, "fremtrials:warrior-accepted", false)) {
                // Send a dialogue from Thorvald warning the player
                player.dialogueInterpreter?.sendDialogues(NPCs.THORVALD_THE_WARRIOR_1289, FacialExpression.ANGRY, "Outerlander... do not test my patience. I do not take", "kindly to people wandering in here and acting as though", "they own the place.")
                return@on true
            } else if (hasEquippableItems(player)) {
                // Send a dialogue from Thorvald about not entering with equipment
                player.dialogueInterpreter?.sendDialogues(NPCs.THORVALD_THE_WARRIOR_1289, FacialExpression.ANGRY, "You may not enter the battleground with any armour", "or weaponry of any kind.")
                // Add an action to inform the player about storing equipment
                player.dialogueInterpreter.addAction { _, _ ->
                    player.dialogueInterpreter?.sendDialogues(NPCs.THORVALD_THE_WARRIOR_1289, FacialExpression.ANGRY, "If you need to place your equipment into your bank", "account, I recommend that you speak to the seer. He", "knows a spell that will do that for you.")
                }
                return@on true
            }

            // Close the Koschei session if it exists
            if (player.getExtension<Any?>(KoscheiSession::class.java) != null) {
                KoscheiSession.getSession(player).close()
            }
            // Handle the climbing action with animation and location
            ClimbActionHandler.climb(player, Animation(828), Location.create(2671, 10099, 2))
            // Submit a pulse for the Koschei session
            Pulser.submit(KoscheiPulse(player))
            // Indicate that the event was handled successfully
            return@on true
        }

        // Handle the action when a player interacts with the THORVALD_LADDER_LOWER
        on(THORVALD_LADDER_LOWER, IntType.SCENERY, "climb-up") { player, _ ->
            // Close the Koschei session if it exists
            if (player.getExtension<Any?>(KoscheiSession::class.java) != null) {
                KoscheiSession.getSession(player).close()
            }
            // Handle the climbing action with animation and location
            ClimbActionHandler.climb(player, Animation(828), Location.create(2666, 3694, 0))
            // Indicate that the event was handled successfully
            return@on true
        }

        // Handle the action when a player interacts with the SWAYING_TREE
        on(SWAYING_TREE, IntType.SCENERY, "cut-branch") { player, node ->
            // Start the woodcutting pulse for the player
            player.pulseManager.run(WoodcuttingPulse(player, node as Scenery))
            // Indicate that the event was handled successfully
            return@on true
        }

        // Handle the action when a player interacts with SHOPNPCS
        on(SHOPNPCS, IntType.NPC, "Trade") { player, npc ->
            // Check if the player has completed the quest
            if (isQuestComplete(player, "Fremennik Trials")) {
                // Open the NPC shop for the player
                openNpcShop(player, npc.id)
            } else when (npc.id) {
                // Send messages based on the NPC the player is interacting with
                NPCs.THORA_THE_BARKEEP_1300 -> sendMessage(player, "Only Fremenniks may buy drinks here.")
                NPCs.SKULGRIMEN_1303 -> sendMessage(player, "Only Fremenniks may purchase weapons and armour here.")
                NPCs.SIGMUND_THE_MERCHANT_1282 -> sendMessage(player, "Only Fremenniks may trade with this merchant.")
                NPCs.YRSA_1301 -> sendMessage(player, "Only Fremenniks may buy clothes here.")
                NPCs.FISH_MONGER_1315 -> sendMessage(player, "Only Fremenniks may purchase fish here.")
            }
            // Indicate that the event was handled successfully
            return@on true
        }
    }


    /**
     * This class represents a destination room with its boundaries.
     *
     * @param swx The x-coordinate of the southwest corner of the room.
     * @param swy The y-coordinate of the southwest corner of the room.
     * @param nex The x-coordinate of the northeast corner of the room.
     * @param ney The y-coordinate of the northeast corner of the room.
     * @constructor Creates a destination room with the specified boundaries.
     */
    class DestRoom(val swx: Int, val swy: Int, val nex: Int, val ney: Int)

    /**
     * Get center of the destination room.
     *
     * @return the center location of the room
     */
    fun DestRoom.getCenter(): Location {
        return Location((swx + nex) / 2, (swy + ney) / 2).transform(1, 0, 0)
    }

    /**
     * Get a random location for the player.
     *
     * @param player the player for whom the location is generated
     * @return a random location
     */
    fun getRandomLocation(player: Player?): Location {
        var obj: Scenery? = null

        while (obj?.id != 5138) {
            val objects = player?.viewport?.chunks?.random()?.random()?.objects
            obj = objects?.random()?.random()
            if (obj == null || obj.location?.equals(Location(0, 0, 0))!!) {
                continue
            }
        }
        return obj.location
    }

    /**
     * Check if the player has equippable items.
     *
     * @param player the player to check for equippable items
     * @return true if the player has equippable items, false otherwise
     */
    fun hasEquippableItems(player: Player?): Boolean {
        val container = arrayOf(player!!.inventory, player.equipment)
        for (c in container) {
            for (i in c.toArray()) {
                if (i == null) {
                    continue
                }
                if (i.name.lowercase().contains(" rune")) {
                    return true
                }
                var slot: Int = i.definition.getConfiguration(ItemConfigParser.EQUIP_SLOT, -1)
                if (slot == -1 && i.definition.getConfiguration(ItemConfigParser.WEAPON_INTERFACE, -1) != -1) {
                    slot = SLOT_WEAPON
                }
                if (slot >= 0) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * Represents a Spirit Pulse that interacts with a player and a fish.
     *
     * @param player the player interacting with the spirit pulse.
     * @param fish the fish involved in the interaction.
     * @constructor Creates a Spirit Pulse with the specified player and fish.
     */
    class SpiritPulse(val player: Player, val fish: Int) : Pulse() {
        var counter = 0
        val npc = NPC(1273, player.location)
        val sea_boots = intArrayOf(
            Items.FREMENNIK_SEA_BOOTS_1_14571,
            Items.FREMENNIK_SEA_BOOTS_2_14572,
            Items.FREMENNIK_SEA_BOOTS_3_14573
        )
        val hasboots = player.equipment.containsAtLeastOneItem(sea_boots)
        val hasring = player.equipment.containsItem(Item(Items.RING_OF_CHAROS_4202))
        override fun pulse(): Boolean {
            when (counter++) {
                0 -> {
                    npc.init()
                    player.lock()
                    removeItem(player, fish)
                }
                1 -> npc.moveStep()
                2 -> npc.face(player).also { player.face(npc) }
                3 -> player.dialogueInterpreter?.sendDialogues(npc, FacialExpression.HAPPY, "I will kindly accept this offering, and", "bestow upon you a gift in return.")
                4 -> if (!removeItem(player, Items.LYRE_3689)) {
                    removeItem(player, Items.ENCHANTED_LYRE_3690)
                }
                5 -> {
                    if (hasring) when (fish) {
                        363 -> {
                            finishDiaryTask(player, DiaryType.FREMENNIK, 1, 4)
                            if (hasboots) {
                                addItem(player, Items.ENCHANTED_LYRE3_6126)
                            } else {
                                addItem(player, Items.ENCHANTED_LYRE2_6125)
                            }
                        }
                    } else {
                        if (hasboots) when (fish) {
                            383 -> addItem(player, Items.ENCHANTED_LYRE3_6126)
                            389 -> addItem(player, Items.ENCHANTED_LYRE5_14590)
                            395 -> addItem(player, Items.ENCHANTED_LYRE6_14591)
                        } else when (fish) {
                            383 -> addItem(player, Items.ENCHANTED_LYRE2_6125)
                            389 -> addItem(player, Items.ENCHANTED_LYRE3_6126)
                            395 -> addItem(player, Items.ENCHANTED_LYRE4_6127)
                        }
                    }
                }
                6 -> player.unlock()
                10 -> npc.clear().also {
                    setAttribute(player, "/save:LyreEnchanted", true)
                    return true
                }
            }
            return false
        }
    }

    /**
     * Represents a Lyre concert pulse.
     *
     * @param player The player associated with the Lyre concert.
     * @param Lyre The Lyre identifier.
     * @constructor Creates a Lyre concert pulse with the given player and Lyre identifier.
     */
    class LyreConcertPulse(val player: Player, val Lyre: Int) : Pulse() {
        val GENERIC_LYRICS = arrayOf(
            "${player.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }} is my name,",
            "I haven't much to say",
            "But since I have to sing this song.",
            "I'll just go ahead and play."
        )
        val CHAMPS_LYRICS = arrayOf(
            "The thought of lots of questing,",
            "Leaves some people unfulfilled,",
            "But I have done my simple best, in",
            "Entering the Champions Guild."
        )
        val HEROES_LYRICS = arrayOf(
            "When it comes to fighting",
            "I hit my share of zeroes",
            "But I'm well respected at",
            "the Guild reserved for Heroes,"
        )
        val LEGENDS_LYRICS = arrayOf(
            "I cannot even start to list",
            "The amount of foes I've killed.",
            "I will simply tell you this:",
            "I've joined the Legends' Guild!"
        )
        var counter = 0
        val questPoints = getQuestPoints(player)
        val champGuild = player.achievementDiaryManager?.hasCompletedTask(DiaryType.VARROCK, 1, 1) ?: false
        val legGuild = questPoints >= 111
        val heroGuild = questPoints >= 5
        val masteredAmount = player.getSkills()?.masteredSkills!! > 0
        var SKILLNAME = getMasteredSkillNames(player)

        val LYRICS = when {
            masteredAmount -> {
                arrayOf("When people speak of training,", "Some people think they're fine.", "But they just all seem jealous that", "My ${SKILLNAME.random()}'s ninety-nine!")
            }

            legGuild -> LEGENDS_LYRICS
            heroGuild -> HEROES_LYRICS
            champGuild -> CHAMPS_LYRICS
            else -> GENERIC_LYRICS
        }

        override fun pulse(): Boolean {
            when (counter++) {
                0 -> {
                    player.lock()
                    animate(player, 1318, true)
                }

                2 -> {
                    animate(player, 1320, true)
                    player.musicPlayer.play(MusicEntry.forId(165))
                }

                4 -> {
                    animate(player, 1320, true)
                    player.musicPlayer.play(MusicEntry.forId(164))
                    sendChat(player, LYRICS[0])
                }

                6 -> {
                    animate(player, 1320, true)
                    player.musicPlayer.play(MusicEntry.forId(164))
                    sendChat(player, LYRICS[1])
                }

                8 -> {
                    animate(player, 1320, true)
                    player.musicPlayer.play(MusicEntry.forId(164))
                    sendChat(player, LYRICS[2])
                }

                10 -> {
                    animate(player, 1320, true)
                    player.musicPlayer.play(MusicEntry.forId(164))
                    sendChat(player, LYRICS[3])
                }

                14 -> {
                    setAttribute(player, "/save:lyreConcertPlayed", true)
                    removeAttribute(player, "LyreEnchanted")
                    if (removeItem(player, Lyre))
                        addItem(player, Items.ENCHANTED_LYRE_3690)
                    player.unlock()
                }
            }
            return false
        }
    }

    /**
     * Branch fletching pulse.
     *
     * @param player The player object.
     * @constructor Represents a new instance of the BranchFletchingPulse class with the specified player.
     */
    class BranchFletchingPulse(val player: Player) : Pulse() {
        var counter = 0
        /**
         * Executes the pulse logic.
         * @return True if the pulse is complete, False otherwise.
         */
        override fun pulse(): Boolean {
            when (counter++) {
                0 -> player.animator?.animate(Animation(1248)).also { player.lock() }
                3 -> {
                    if (removeItem(player, Items.BRANCH_3692))
                        addItem(player, Items.UNSTRUNG_LYRE_3688)
                    player.unlock()
                    return true
                }
            }
            return false
        }
    }

    /**
     * Class representing a Koschei pulse for a player.
     *
     * @param player The player associated with the pulse.
     * @constructor Creates a Koschei pulse for the specified player.
     */
    class KoscheiPulse(val player: Player) : Pulse() {
        var counter = 0
        override fun pulse(): Boolean {
            when (counter++) {
                0 -> sendMessage(player, "Explore this battleground and find your foe...")
                20 -> {
                    if (player.getExtension<Any?>(KoscheiSession::class.java) != null)
                        return true
                    KoscheiSession.create(player).start().also { return true }
                }
            }
            return false
        }
    }
}