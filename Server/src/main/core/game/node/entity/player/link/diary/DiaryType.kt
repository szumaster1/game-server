package core.game.node.entity.player.link.diary

import core.api.consts.Items
import core.api.consts.NPCs
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Diary type
 *
 * @property diaryName
 * @property buttonId
 * @property levelNames
 * @property achievements
 * @property rewards
 * @property info
 * @property npcs
 * @constructor Diary type
 */
enum class DiaryType(val diaryName: String, val buttonId: Int, val levelNames: Array<String>, val achievements: Array<Array<String>>, val rewards: Array<Array<Item>>, val info: String, val npcs: IntArray) {
    /**
     * Karamja
     *
     * @constructor Karamja
     */
    KARAMJA(
        diaryName = "Karamja", buttonId = 11, levelNames = arrayOf("Easy", "Medium", "Hard"),
        achievements = arrayOf(
            // --------------- KARAMJA EASY TASKS ----------------------
            arrayOf(
                "Pick 5 bananas from the plantation located east of the <br><br>volcano",
                "Use the rope swing to travel to the small island north-west<br><br>of Karamja, where the moss giants are",
                "Mine some gold from the rocks on the north-west<br><br>peninsula of Karamja",
                "Travel to Port Sarim via the dock, east of Musa Point",
                "Travel to Ardougne via the port near Brimhaven",
                "Explore Cairn Island to the west of Karamja",
                "Use the Fishing spots north of the banana plantation",
                "Collect 5 seaweed from anywhere on Karamja",
                "Attempt the TzHaar Fight Pits or Fight Cave",
                "Kill a Jogre in the Pothole dungeon"
            ),
            // --------------- KARAMJA MEDIUM TASKS ---------------------
            arrayOf(
                "Claim a ticket from the Agility arena in Brimhaven",
                "Discover hidden wall in the dungeon below the volcano",
                "Visit the Isle of Crandor via the dungeon below the volcano",
                "Use Vigroy and Hajedy's cart service",
                "Earn 100% favour in the village of Tai Bwo Wannai Cleanup",
                "Cook a spider on stick",
                "Charter the Lady of the Waves from Cairn Isle to Port Khazard",
                "Cut a log from a teak tree",
                "Cut a log from a mahogany tree",
                "Catch a karambwan",
                "Exchange gems, a gout tuber, trading sticks for a machete",
                "Use the gnome glider to travel to Karamja",
                "Grow a healthy fruit tree in the patch near Brimhaven",
                "Trap a Horned Graahk",
                "Chop the vines to gain deeper access to Brimhaven Dungeon",
                "Cross the lava using the stepping stones within Brimhaven<br><br>Dungeon",
                "Climb the stairs within Brimhaven Dungeon",
                "Charter a ship from the shipyard in the far east of Karamja",
                "Mine a red topaz from a gem rock"
            ),
            // --------------- KARAMJA HARD TASKS -----------------------
            arrayOf(
                "Become the Champion of the Fight Pits",
                "Successfully kill a Ket-Zek in the Fight Caves",
                "Eat an oomlie wrap",
                "Craft some nature runes",
                "Cook a karambwan thoroughly",
                "Kill a deathwing in the dungeon under the Kharazi Jungle",
                "Use the crossbow shortcut south of the volcano",
                "Collect five palm leaves",
                "Be assigned a Slayer task by Duradel or Lapalok in Shilo<br><br>Village.",
                "Kill a metal dragon in Brimhaven Dungeon."
            )
        ),
        // --------------- KARAMJA REWARDS ---------------
        rewards = arrayOf(
            arrayOf(Item(Items.KARAMJA_GLOVES_1_11136), Item(Items.ANTIQUE_LAMP_11137)),
            arrayOf(Item(Items.KARAMJA_GLOVES_2_11138), Item(Items.ANTIQUE_LAMP_11139)),
            arrayOf(Item(Items.KARAMJA_GLOVES_3_11140), Item(Items.ANTIQUE_LAMP_11141))
        ),
        info = "To start marking off tasks in your journal, speak to Pirate<br><br>Jackie the Fruit in Brimhaven, Kaleb Paramay in Shilo<br><br>Village or one of the Jungle Foresters north of the<br><br>Kharazi Jungle.",  // Tasks NPC:
        npcs = intArrayOf(NPCs.PIRATE_JACKIE_THE_FRUIT_1055, NPCs.KALEB_PARAMAYA_512, NPCs.JUNGLE_FORESTER_401)
        // Secondary reward:
        // Easy
        // - cheaper prices on karamja and cheaper ship rides
        // Medium
        // - 10% extra agility xp in arena
        // - 10% extra xp redeeming tickets
        // Hard
        // - Gem mine ladder access
        // - Teleport to underground gem mine
    ),

    /**
     * Varrock
     *
     * @constructor Varrock
     */
    VARROCK(
        diaryName = "Varrock",
        buttonId = 15,
        levelNames = arrayOf("Easy", "Medium", "Hard"),
        achievements = arrayOf(
            // --------------- VARROCK EASY TASKS ----------------------
            arrayOf(
                "Have Thessalia show you what outfits you can wear",
                "Have Aubury teleport you to the essence mine",
                "Mine some Iron in the south east mining patch near Varrock",
                "Make a plank at the sawmill",
                "Enter the second level of the Stronghold of Security",
                "Jump over the fence south of Varrock",
                "Chop down a dying tree in the Lumber Yard",
                "Buy a copy of the Varrock Herald",
                "Give a stray dog a bone",
                "Spin a bowl on the pottery wheel and fire it in the oven in<br><br>Barbarian Village",
                "Enter Edgeville Dungeon using the entrance to the south of<br><br>Edgeville",
                "Move your player-owned house portal to a different location<br><br>using the Varrock estate agent",
                "Speak to Haig Halen after obtaining at least 50 quest points",
                "Enter the Earth Altar using an earth tiara or talisman",
                "Have Elsie tell you a story",
                "Mine some limestone near Paterdomus, the temple to the east<br><br>of Varrock",
                "Catch a trout in the river to the east of Barbarian Village.",
                "Venture through the cobwebbed corridor in Varrock Sewers",
                "Find the highest point in Varrock"
            ),
            // --------------- VARROCK MEDIUM TASKS  ----------------------
            arrayOf(
                "Have the Apothecary in Varrock make you a strength potion.",
                "Enter the Champions Guild.",
                "Take the Dagon'Hai shortcut to the chaos portal",
                "Get a full complement of rats on your rat pole",
                "Escape from the spider lair in Varrock Sewers with some red<br><br>spiders eggs",
                "Use the spirit tree north of Varrock",
                "Perform the 4 emotes from the Stronghold of Security.",
                "Select a colour for a new kitten",
                "Use the shortcut under the wall, north-west of the Grand<br><br>Exchange",
                "Enter the A Soul's Bane rift",
                "Teleport to the Digsite using a Digsite pendant",
                "Craft an earth tiara on the Earth Altar",
                "Pickpocket a guard in the Varrock Palace courtyard",
                "Use the teleport to Varrock spell",
                "Get a Slayer task from Vannaka",
                "Buy twenty mahogany planks from the Sawmill Operator in one<br><br>transaction",
                "Pick a fruit from the White Tree",
                "Use the hot air balloon to travel from Varrock to somewhere<br><br>else",
                "Get a cat training medal from Gertrude",
                "Dial to the fairy ring west of Varrock",
                "Browse through Oziach's Armour Shop"
            ),
            // --------------- VARROCK HARD TASKS ----------------------
            arrayOf(
                "Pick poison ivy from your bush Farming patch in Varrock<br><br>(west of Champions' Guild)",
                "Use the pipe shortcut in Varrock Sewers, near the moss<br><br>giants",
                "Trade furs with the Fancy Dress Seller for a spottier cape",
                "Smith an adamantite medium helm on the south-east anvil in<br><br>Varrock, next to Aubury's Rune Shop",
                "Speak to Orlando Smith when you have achieved 153 kudos",
                "Talk to Romily Weaklax and give him a wild pie",
                "Craft an air battlestaff",
                "Give your player-owned house a tropical wood or fancy stone<br><br>finish at the Varrock estate agent's",
                "Make a Varrock teleport tablet on a mahogany lectern",
                "Obtain a new set of Family Crest gauntlets from Dimintheis",
                "Make a Waka Canoe near Edgeville",
                "Use the Home Teleport spell in the Ancient Magicks spellbook<br><br>to teleport to Edgeville",
                "Use the skull sceptre to teleport to Barbarian Village"
            )
        ),
        // --------------- VARROCK REWARDS ---------------
        rewards = arrayOf(
            arrayOf(Item(Items.VARROCK_ARMOUR_1_11756), Item(Items.ANTIQUE_LAMP_11753)),
            arrayOf(Item(Items.VARROCK_ARMOUR_2_11757), Item(Items.ANTIQUE_LAMP_11754)),
            arrayOf(Item(Items.VARROCK_ARMOUR_3_11758), Item(Items.ANTIQUE_LAMP_11755))
        ),
        info = "To start marking off tasks in your journal, speak to<br><br>Rat Burgiss south of the city, Reldo in the palace<br><br>library, or Vannaka in the sewers.",  // Tasks NPC:
        npcs = intArrayOf(NPCs.RAT_BURGISS_5833, NPCs.RELDO_2660, NPCs.VANNAKA_1597)
        // Secondary reward:
        // Easy
        // - chance of smelting two bars at once up to and including steel in edgeville furnace
        // - chance of mining two ores at once when mining ores up to and including coal
        // - better prices when buying and selling in Varrock Area
        // - 16 battlestaffs may be bought from Zaff's staffs store daily (8 + 8)
        // - Option to change Varrock tele location to the GE by talking to Rat Burgiss
        // Medium
        // - chance of smelting two bars at once up to and including Mithril in edgeville furnace
        // - chance of mining two ores at once when mining ores up to and including mithril
        // - chance of smithing at a slightly faster rate
        // - 32 battlestaffs may be bought from Zaff's staffs store daily (8 + 24)
        // - Option to change Varrock tele location to the GE by talking to Reldo
        // Hard
        // - chance of smelting two bars at once up to and including Adamantite in edgeville furnace
        // - chance of mining two ores at once when mining ores up to and including adamantite
        // - access to cook's guild 1st floor bank area
        // - use varrock armour to get into cook's guild rather than chef's hat
        // - 64 battlestaffs may be bought from Zaff's staffs store daily (8 + 56)
        // - Option to change Varrock tele location to the GE by talking to Vannaka

    ),

    /**
     * Lumbridge
     *
     * @constructor Lumbridge
     */
    LUMBRIDGE(
        diaryName = "Lumbridge",
        buttonId = 2,
        levelNames = arrayOf("Beginner", "Easy", "Medium"),
        achievements = arrayOf(
            // --------------- LUMBRIDGE BEGINNER TASKS ----------------------
            arrayOf(
                "Climb to the highest point in Lumbridge",
                "Raise the flag on the roof of the Lumbridge Bank",
                "Speak to the Duke of Lumbridge",
                "Speak with the Doomsayer about the Warning System",
                "Pass through the Al Kharid gate",
                "Mine some clay in the Mining patch north of the Champions'<br><br>Guild",
                "Make some soft clay in the Barbarian Village",
                "Make a pot on the potter's wheel in the Barbarian Village",
                "Fire a pot in the kiln in the Barbarian Village potter's<br><br>house",
                "Enter the courtyard of the spooky mansion in Draynor Village",
                "Visit the Draynor Village market",
                "Find out about the Rules of Conduct from the Draynor<br><br>Town Crier",
                "Climb to the top of the Wizards' Tower",
                "Mine some copper in the Mining spot to the south-east of<br><br>Lumbridge Swamp",
                "Catch some shrimp in the Fishing spot to the east of<br><br>Lumbridge Swamp",
                "Have the Fishing Tutor send you on an errand",
                "Look through Father Aereck's selection of gravestones",
                "Play the organ in the Lumbridge Church",
                "Ring the bell in the Lumbridge Church",
                "Find out about the Stronghold of Security from the Lumbridge<br><br>Guide",
                "Browse the Lumbridge General Store",
                "Visit Fred the Farmer's chicken and sheep farm",
                "Grind some flour in the windmill north of Lumbridge"
            ),
            // --------------- LUMBRIDGE EASY TASKS ----------------------
            arrayOf(
                "Mine some iron ore from the Al Kharid Mining spot",
                "Obtain a cow-hide from a cow in the field north-east of<br><br>Lumbridge",
                "Have Ellis tan your cow-hide to make soft leather at his<br><br>shop in Al Kharid",
                "Craft a pair of soft leather gloves",
                "Catch a pike in the river to the east of Lumbridge Castle",
                "Smelt a steel bar in the Lumbridge furnace",
                "Search the shed in Lumbridge Swamp",
                "Kill a giant rat in Lumbridge Swamp",
                "Cut down a dead tree in Lumbridge Swamp",
                "Light a campfire from normal logs in Lumbridge Swamp",
                "Cook some rat meat on a campfire in Lumbridge Swamp",
                "Craft a water rune at the Water Altar",
                "Get a replacement Ghostspeak Amulet from Father Urhney",
                "Taunt the demon at the top of the Wizard's Tower",
                "Have Sedridor teleport you to the Essence Mine",
                "Access the bank in Draynor Village",
                "Have the Wise Old man check your bank for unnecessary<br><br>quest-related items",
                "Discover what the Wise Old man is watching through his<br><br>telescope",
                "Defeat a zombie in the sewers under the jail"
            ),
            // --------------- LUMBRIDGE MEDIUM TASKS ----------------------
            arrayOf(
                "Smith a steel longsword on the anvil in the jailhouse<br><br>sewers",
                "Take a ride on Gnomecopter",
                "Use the teleport Lumbridge spell",
                "Light a willow log fire in Lumbridge Castle courtyard",
                "Cook a lobster on the range in Lumbridge Castle kitchen",
                "Obtain an Anti-dragonbreath shield from Duke Horacio",
                "Cut a willow tree, east of Lumbridge Castle",
                "Smelt a silver bar in the Lumbridge furnace",
                "Craft a holy symbol in the Lumbridge furnace",
                "Catch a salmon in the river to the east of Lumbridge Castle",
                "Mine some silver from the mining spot north of Al Kharid",
                "Mine some coal in the Mining spot south-west of Lumbridge<br><br>Swamp"
            )
        ),
        // --------------- LUMBRIDGE REWARDS ---------------
        rewards = arrayOf(
            arrayOf(Item(Items.EXPLORERS_RING_1_13560), Item(Items.ANTIQUE_LAMP_11753)),
            arrayOf(Item(Items.EXPLORERS_RING_2_13561), Item(Items.ANTIQUE_LAMP_11753)),
            arrayOf(Item(Items.EXPLORERS_RING_3_13562), Item(Items.ANTIQUE_LAMP_11753))
        ),
        info = "To start marking off tasks in your journal, speak to<br><br>Explorer Jack near the Lumbridge General Store, Bob<br><br>in his axe store or Ned in his house in Draynor Village.",  // Tasks NPC:
        npcs = intArrayOf(NPCs.EXPLORER_JACK_7969, NPCs.BOB_519, NPCs.NED_743)
    ),

    /**
     * Falador
     *
     * @constructor Falador
     */
    FALADOR(
        diaryName = "Falador",
        buttonId = 23,
        levelNames = arrayOf("Easy", "Medium", "Hard"),
        achievements = arrayOf(
            // --------------- FALADOR EASY TASKS --------------------------
            arrayOf(
                "Buy a Farming amulet from Sarah on the farm north of Port<br><br>Sarim",
                "Buy a stat-boosting beer from a waitress in the Rising Sun<br><br>Tavern",
                "Buy a black chainbody from Wayne's Chains, and try it on in<br><br>the shop",
                "Climb to the top of the White Knights' Castle",
                "Discover your family crest from Sir Renitee",
                "Enter the mole's lair under Falador Park",
                "Feed Ridgeley, the hairdresser's pet",
                "Fill a bucket from the pump north of the west Falador bank",
                "Heal an elemental wizard by casting an appropriate elemental<br><br>spell on him (Air, Water, Earth, Fire)",
                "Kill a duck in Falador Park",
                "Kill a highwayman on the road south of Falador",
                "Make an air tiara",
                "Pop a party balloon",
                "Recharge your Prayer points at the altar south-west of Port<br><br>Sarim",
                "Take the boat to Entrana"
            ),
            // --------------- FALADOR MEDIUM TASKS ------------------------
            arrayOf(
                "Craft a Fruit basket using the loom at the farm north of<br><br>Port Sarim",
                "Crawl under Falador's south wall",
                "Grapple up, and then jump off the north Falador wall",
                "Increase your reputation with the White Knights by killing<br><br>a black knight",
                "Kill an ice giant in the Asgarnian Ice Dungeon",
                "Light a bullseye lantern in the chemist's",
                "Pickpocket a Falador guard",
                "Place a scarecrow to protect your sweetcorn as it grows in<br><br>the patch north of Port Sarim",
                "Salute Sir Tiffy Cashien while wearing full initiate armour",
                "Smith blurite crossbow limbs on Thurgo's anvil",
                "Travel from Port Sarim to Musa Point for free (with a little<br><br>help from Charos)",
                "Visit the Port Sarim rat pits"
            ),
            // --------------- FALADOR HARD TASKS --------------------------
            arrayOf(
                "Ascend the Dark Wizards' Tower while wearing full proselyte<br><br>armour",
                "Change your family crest to the Saradomin symbol",
                "Craft 196 or more air runes simultaneously",
                "Cut down a Yew tree or Magic tree that you grew in Falador<br><br>Park",
                "Dial to the fairy ring on Mudskipper Point",
                "Dye a cape pink with Pink dye from Betty in Port Sarim",
                "Enter the Mining Guild",
                "Kill a mogre at Mudskipper Point",
                "Kill a skeletal wyvern in the Asgarnian Ice Dungeon",
                "Summon an Ibis in the Port Sarim fish store"
            )
        ),
        // --------------- FALADOR REWARDS ---------------
        rewards = arrayOf(
            arrayOf(Item(Items.FALADOR_SHIELD_1_14577), Item(Items.ANTIQUE_LAMP_14580)),
            arrayOf(Item(Items.FALADOR_SHIELD_2_14578), Item(Items.ANTIQUE_LAMP_14581)),
            arrayOf(Item(Items.FALADOR_SHIELD_3_14579), Item(Items.ANTIQUE_LAMP_14582))
        ),
        info = "To start marking off tasks in your journal, speak to<br><br>Redbeard Frank outside the bar in Port Sarim, The<br><br>Chemist west of Rimmington, or Sir Vyvin's Squire in the<br><br>Falador Castle courtyard.",  // Tasks NPC:
        npcs = intArrayOf(NPCs.REDBEARD_FRANK_375, NPCs.CHEMIST_367, NPCs.SQUIRE_606)
    ),

    /**
     * Fremennik
     *
     * @constructor Fremennik
     */
    FREMENNIK(
        diaryName = "Fremennik",
        buttonId = 19,
        levelNames = arrayOf("Easy", "Medium", "Hard"),
        achievements = arrayOf(
            // --------------- FREMENNIK EASY TASKS ----------------------
            arrayOf(
                "Kill a cave crawler in the Fremennik Slayer Dungeon",
                "Kill five rock crabs on the shore near Rellekka or on<br><br>Waterbirth Island",
                "Find the highest tree on the Fremennik mainland",
                "View the rewards in the Barbarian Assault tutorial",
                "Speak to Otto Godblessed about barbarian training",
                "Collect three seaweed from the shore north-east of Rellekka",
                "Find the Hunting Expert on the northern ice plains",
                "Catch a fish off one of Rellekka's piers",
                "Recharge your Summoning points near Rellekka's gate",
                "Kill an adult black unicorn"
            ),
            // --------------- FREMENNIK MEDIUM TASKS ----------------------
            arrayOf(
                "Learn of the history of the Fremennik and the Outerlanders<br><br>from Chieftain Brundt",
                "Watch a shouting match between Fremennik Isles tower guards<br><br>(the guards can be found between Jatizso and Neitiznot in<br><br>one of the towers)",
                "Interact with a Pet rock",
                "Make three vials in the furnace building at Rellekka",
                "Charm the Fossegrimen into accepting a raw bass",
                "Wear yak-hide armour and kill an ice troll",
                "Make cheese in the dairy churn in Rellekka",
                "Use a fairy ring to appear on a mountaintop, near the<br><br>windswept tree",
                "Look at Yrsa's options for recolouring your boots in her<br><br>clothes shop in Rellekka",
                "Successfully hunt a sabre-toothed kyatt",
                "Steal a fish from the fishing stall in the Rellekka<br><br>marketplace"
            ),
            // --------------- FREMENNIK HARD TASKS ----------------------
            arrayOf(
                "Kill three dagannoths in the first layer of the Waterbirth<br><br>Island Dungeon",
                "Wear rockshell, spined or skeletal armour and have the<br><br>locals use an honorific with your Fremennik name",
                "Complete the Barbarian Outpost Agility Course",
                "Mine pure essence on Lunar Isle",
                "Make a barbarian pyre ship from arctic pine",
                "Catch a tuna without a harpoon",
                "Bake a pie using Magic",
                "Kill a Mithril dragon",
                "Get mahogany from your Etceterian subjects"
            )
        ),
        // --------------- FREMENNIK REWARDS ---------------
        rewards = arrayOf(
            arrayOf(Item(Items.FREMENNIK_SEA_BOOTS_1_14571), Item(Items.ANTIQUE_LAMP_14574)),
            arrayOf(Item(Items.FREMENNIK_SEA_BOOTS_2_14572), Item(Items.ANTIQUE_LAMP_14575)),
            arrayOf(Item(Items.FREMENNIK_SEA_BOOTS_3_14573), Item(Items.ANTIQUE_LAMP_14576))

        ),
        info = "To start marking off tasks in your journal, speak to<br><br>Council Workman south of the city, Yrsa in Relleka,<br><br> or Advisor Ghrim in the 1st floor of the castle in Miscellania.",  // Tasks NPC:
        npcs = intArrayOf(NPCs.COUNCIL_WORKMAN_1287, NPCs.YRSA_1301, NPCs.ADVISOR_GHRIM_1375)
    ),

    /**
     * Seers Village
     *
     * @constructor Seers Village
     */
    SEERS_VILLAGE(
        diaryName = "Seers' Village",
        buttonId = 27,
        levelNames = arrayOf("Easy", "Medium", "Hard"),
        achievements = arrayOf(
            // --------------- SEERS VILLAGE EASY TASKS ---------------------
            arrayOf(
                "Pick five flax from the flax field",
                "Walk clockwise around the big mysterious statue",
                "Have Sir Galahad make you a cup of tea",
                "Take the poison chalice to King Arthur",
                "Spin five bow strings",
                "Fill five pots with flour from the Sinclair Mansion",
                "Give five locals a glass of cider in the Forester's Arms",
                "Plant some jute",
                "Use the churn in the Sinclair Mansion garden",
                "Buy a candle from the candle-maker",
                "Pray at the Seers' Village altar",
                "Catch a mackerel"
            ),
            // --------------- SEERS VILLAGE MEDIUM TASKS -------------------
            arrayOf(
                "Use the Sinclair Mansion to Fremennik agility shortcut",
                "Talk to Thormac the Sorcerer about making mystic staves",
                "Transport a full load of coal to Seers' Village",
                "Find the highest point in Seers' Village",
                "Defeat each type of elemental in the Elemental Workshop",
                "Teleport to Camelot",
                "Kill one guard on each tower of the Ranging Guild using a longbow",
                "Have the Ranging Guild competition judge congratulate you<br><br>for acquiring over 1,000 Archery tickets",
                "Buy something from the ticket exchange in the Ranging Guild",
                "Use a familiar to make a fire from maple logs within Seers'<br><br>Village",
                "Get a pet fish from Harry",
                "Catch and cook a bass in Catherby"
            ),
            // --------------- SEERS VILLAGE HARD TASKS ---------------------
            arrayOf(
                "Teleport to the Ranging Guild",
                "Cut five sets of yew logs",
                "Fletch a magic shortbow in the Seers' Village Bank",
                "Enter the Seers' Village courthouse with your Piety prayer<br><br>turned on",
                "Use the fairy ring in McGrubor's Wood",
                "Burn a magic log in Seers' Village",
                "High Alch a magic shortbow in the Seers' Village bank",
                "Catch five sharks in Catherby",
                "Cook 5 sharks in Catherby using the Cooking gauntlets",
                "Charge five water orbs in one go",
                "Use the grapple shortcut to get from the water obelisk<br><br>island to Catherby shore"
            )
        ),
        // --------------- SEERS VILLAGE REWARDS ---------------
        rewards = arrayOf(
            arrayOf(Item(Items.SEERS_HEADBAND_1_14631), Item(Items.ANTIQUE_LAMP_14633)),
            arrayOf(Item(Items.SEERS_HEADBAND_2_14659), Item(Items.ANTIQUE_LAMP_14634)),
            arrayOf(Item(Items.SEERS_HEADBAND_3_14660), Item(Items.ANTIQUE_LAMP_14635))
        ),
        info = "To start marking off tasks in your journal, speak to<br><br>any seer in Seers' Village, Stankers by the coal<br><br>trucks or Sir Kay in Camelot.",  // Tasks NPC:
        npcs = intArrayOf(NPCs.SEER_388, NPCs.STANKERS_383, NPCs.SIR_KAY_241)
        // Secondary reward:
        // Easy
        // - 30 flax a day from Flax (Geoffrey)
        // Medium
        // - 60 flax a day from Geoffrey
        // - 10% more experience cutting maples while wearing Seer's Headband
        // - Extra log given when cutting normal trees in Seer's Village
        // Hard
        // - 120 flax a day from Geoffrey
        // - Significant increase in speed spinning wheel in Seer's village
        // - Enhanced Excalibur - lady of the lake
    );

    /**
     * Get npc
     *
     * @param level
     * @return
     */
    fun getNpc(level: Int): Int {
        return npcs[level]
    }

    /**
     * Has reward equipment
     *
     * @param player
     * @return
     */
    fun hasRewardEquipment(player: Player): Boolean {
        for (tier in rewards) {
            for (item in tier) {
                if (!item.name.lowercase().contains("lamp") && player.hasItem(item)) return true
            }
        }
        return false
    }

    /**
     * Get rewards
     *
     * @param level
     * @return
     */
    fun getRewards(level: Int): Array<Item> {
        return rewards[level]
    }

    /**
     * Get achievements
     *
     * @param level
     * @return
     */
    fun getAchievements(level: Int): Array<String> {
        return achievements[level]
    }

    companion object {
        @JvmStatic
        fun forChild(child: Int): DiaryType? {
            for (type in values()) {
                for (i in 0..3) {
                    if (child == type.buttonId + i) {
                        return type
                    }
                }
            }
            return null
        }
    }
}