package content.minigame.sorceressgarden

import content.minigame.sorceressgarden.SorceressGarden.SeasonDefinitions.Companion.forGateId
import content.minigame.sorceressgarden.dialogue.SorceressApprenticeDialogue
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.sendItemDialogue
import core.api.sendMessage
import core.api.sendNPCDialogue
import core.game.component.Component
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.network.packet.PacketRepository
import core.network.packet.context.MinimapStateContext
import core.network.packet.outgoing.MinimapState
import core.plugin.Plugin
import core.tools.RandomFunction

/**
 * Sorceress garden.
 */
class SorceressGarden : InteractionListener {
    val GATES = intArrayOf(21709, 21753, 21731, 21687)
    val APPRENTICE = NPCs.APPRENTICE_5532
    val SQIRK_TREES = intArrayOf(21767, 21768, 21769, 21766)
    val FOUNTAIN = 21764
    val HERBS = HerbDefinition.values().map { it.id }.toIntArray()
    val SHELVES = 21794
    private val HERBS_ITEMS = intArrayOf(199, 201, 203, 205, 207, 209, 211, 213, 215, 217, 219, 2485, 3049, 3051, 199, 201, 203, 205)

    override fun defineListeners() {

        on(GATES, IntType.SCENERY, "open") { player, node ->
            val def = forGateId((node as Scenery).id)
            if (def != null) {
                if (player.getSkills().getStaticLevel(Skills.THIEVING) < def.level) {
                    sendItemDialogue(
                        player,
                        Items.HIGHWAYMAN_MASK_10692,
                        "You need Thieving level of " + def.level + " to pick the lock of this gate."
                    )
                    return@on true
                }
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            }
            return@on true
        }

        on(APPRENTICE, IntType.NPC, "teleport") { player, node ->
            val npc = node as NPC
            if (player.getSavedData().globalData.hasSpokenToApprentice()) {
                SorceressApprenticeDialogue.teleport(npc, player)
            } else {
                sendNPCDialogue(player, node.id, "I can't do that now, I'm far too busy sweeping.")
            }
            return@on true
        }

        SqirkJuicePlugin().newInstance(null)
        SqirkMakingDialogue().init()

        on(SQIRK_TREES, IntType.SCENERY, "pick-fruit") { player, node ->
            val def = SeasonDefinitions.forTreeId(node.id)
            if (def != null) {
                player.lock()
                player.logoutListeners["garden"] = { p -> p.location = def.respawn }
                player.animate(PICK_FRUIT)
                player.skills.addExperience(Skills.THIEVING, def.exp, true)
                player.skills.addExperience(Skills.FARMING, def.farmExp, true)
                GameWorld.Pulser.submit(object : Pulse(2, player) {
                    var counter = 0
                    override fun pulse(): Boolean {
                        if (counter == 1) {
                            player.inventory.add(Item(def.fruitId))
                            player.interfaceManager.openOverlay(Component(115))
                            PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 2))
                        } else if (counter == 3) player.properties.teleportLocation =
                            def.respawn else if (counter == 4) {
                            player.unlock()
                            player.logoutListeners.remove("garden")
                            player.packetDispatch.sendMessage("An elemental force emanating from the garden teleports you away.")
                            PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 0))
                            player.interfaceManager.close()
                            player.interfaceManager.closeOverlay()
                            player.unlock()
                            return true
                        }
                        counter++
                        return false
                    }
                })
            }
            return@on true
        }


        on(FOUNTAIN, IntType.SCENERY, "drink-from") { player, _ ->
            player.lock()
            GameWorld.Pulser.submit(object : Pulse(1, player) {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        1 -> player.animate(DRINK_ANIM)
                        4 -> player.graphics(Graphic)
                        5 -> player.animate(TELE)
                        6 -> player.interfaceManager.openOverlay(Component(115))
                        7 -> PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 2))
                        9 -> player.properties.teleportLocation = Location(3321, 3141, 0)
                        11 -> {
                            player.unlock()
                            player.animate(Animation(-1))
                            player.interfaceManager.close()
                            player.interfaceManager.closeOverlay()
                            PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 0))
                            return true
                        }
                    }
                    return false
                }
            })

            return@on true
        }

        on(SHELVES, IntType.SCENERY, "search") { player, node ->
            if (player.inventory.freeSlots() < 1) {
                sendMessage(player, "You don't have enough space in your inventory to take a beer glass.")
            } else {
                sendMessage(player, "You take an empty beer glass off the shelves.")
                player.inventory.add(Item(1919, 1))
            }

            return@on true
        }

        on(HERBS, IntType.SCENERY, "pick") { player, node ->
            val herbDef = HerbDefinition.forId(node.id)
            if (herbDef != null) {
                handleElementalGarden(player, node.asScenery(), herbDef)
            }
            return@on true
        }

    }

    private fun handleElementalGarden(player: Player, `object`: Scenery, herbDef: HerbDefinition) {
        player.lock()
        player.logoutListeners["garden"] = { p -> p.location = herbDef.respawn }
        player.animate(ANIMATION)
        player.skills.addExperience(Skills.FARMING, herbDef.exp, true)
        GameWorld.Pulser.submit(object : Pulse(2, player) {
            var counter = 0
            override fun pulse(): Boolean {
                if (counter == 1) {
                    player.inventory.add(Item(HERBS_ITEMS[RandomFunction.random(0, HERBS_ITEMS.size)], +1))
                    player.inventory.add(Item(HERBS_ITEMS[RandomFunction.random(0, HERBS_ITEMS.size)], +1))
                    player.packetDispatch.sendMessage("You pick up a herb.")
                    player.interfaceManager.openOverlay(Component(115))
                    PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 2))
                } else if (counter == 3) player.properties.teleportLocation =
                    Location.create(herbDef.respawn) else if (counter == 4) {
                    player.unlock()
                    player.packetDispatch.sendMessage("An elemental force emanating from the garden teleports you away.")
                    PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 0))
                    player.interfaceManager.close()
                    player.interfaceManager.closeOverlay()
                    player.logoutListeners.remove("garden")
                    player.unlock()
                    return true
                }
                counter++
                return false
            }
        })
    }

    /**
     * Herb definition
     *
     * @param id Unique identifier for the herb
     * @param exp Experience points gained from harvesting the herb
     * @param respawn Location where the herb will respawn after being harvested
     * @constructor Herb definition
     */
    enum class HerbDefinition(val id: Int, val exp: Double, val respawn: Location) {
        /**
         * Winter
         *
         * @constructor Winter
         */
        WINTER(21671, 30.0, Location(2907, 5470, 0)),

        /**
         * Spring
         *
         * @constructor Spring
         */
        SPRING(21668, 40.0, Location(2916, 5473, 0)),

        /**
         * Autumn
         *
         * @constructor Autumn
         */
        AUTUMN(
            21670,
            50.0,
            Location(2913, 5467, 0)
        ),

        /**
         * Summer
         *
         * @constructor Summer
         */
        SUMMER(21669, 60.0, Location(2910, 5476, 0));

        companion object {
            /**
             * Retrieves a HerbDefinition based on its unique identifier
             *
             * @param id Unique identifier for the herb
             * @return HerbDefinition? Returns the corresponding HerbDefinition or null if not found
             */
            fun forId(id: Int): HerbDefinition? {
                for (def in values()) {
                    if (def.id == id) {
                        return def // Return the matching HerbDefinition
                    }
                }
                return null // Return null if no match is found
            }
        }
    }


    /**
     * Season definitions
     *
     * @param treeId Unique identifier for the tree associated with the season
     * @param level The level required to access this season
     * @param farmExp Experience points gained from farming in this season
     * @param exp General experience points gained in this season
     * @param fruitId Unique identifier for the fruit produced in this season
     * @param juiceId Unique identifier for the juice made from the fruit
     * @param fruitAmt Amount of fruit produced in this season
     * @param boost Boost effect provided by this season
     * @param energy Energy cost associated with activities in this season
     * @param osmanExp Experience points gained from Osman-related activities in this season
     * @param gateId Unique identifier for the gate associated with this season
     * @param respawn Location where entities respawn in this season
     * @constructor Season definitions
     */
    enum class SeasonDefinitions(
        val treeId: Int, // Unique identifier for the tree associated with the season
        val level: Int, // The level required to access this season
        val farmExp: Double, // Experience points gained from farming in this season
        val exp: Double, // General experience points gained in this season
        val fruitId: Int, // Unique identifier for the fruit produced in this season
        val juiceId: Int, // Unique identifier for the juice made from the fruit
        val fruitAmt: Int, // Amount of fruit produced in this season
        val boost: Int, // Boost effect provided by this season
        val energy: Int, // Energy cost associated with activities in this season
        val osmanExp: Double, // Experience points gained from Osman-related activities in this season
        val gateId: Int, // Unique identifier for the gate associated with this season
        val respawn: Location // Location where entities respawn in this season
    ) {
        /**
         * Winter
         *
         * @constructor Winter
         */
        WINTER(
            treeId = 21769,
            level = 1,
            farmExp = 30.0,
            exp = 70.0,
            fruitId = 10847,
            juiceId = 10851,
            fruitAmt = 5,
            boost = 0,
            energy = 10,
            osmanExp = 350.0,
            gateId = 21709,
            respawn = Location(2907, 5470, 0)
        ),

        /**
         * Spring
         *
         * @constructor Spring
         */
        SPRING(
            treeId = 21767,
            level = 25,
            farmExp = 40.0,
            exp = 337.5,
            fruitId = 10844,
            juiceId = 10848,
            fruitAmt = 4,
            boost = 1,
            energy = 20,
            osmanExp = 1350.0,
            gateId = 21753,
            respawn = Location(2916, 5473, 0)
        ),

        /**
         * Autumn
         *
         * @constructor Autumn
         */
        AUTUMN(
            treeId = 21768,
            level = 45,
            farmExp = 50.0,
            exp = 783.3,
            fruitId = 10846,
            juiceId = 10850,
            fruitAmt = 3,
            boost = 2,
            energy = 30,
            osmanExp = 2350.0,
            gateId = 21731,
            respawn = Location(2913, 5467, 0)
        ),

        /**
         * Summer
         *
         * @constructor Summer
         */
        SUMMER(
            treeId = 21766,
            level = 65,
            farmExp = 60.0,
            exp = 1500.0,
            fruitId = 10845,
            juiceId = 10849,
            fruitAmt = 2,
            boost = 3,
            energy = 40,
            osmanExp = 3000.0,
            gateId = 21687,
            respawn = Location(2910, 5476, 0)
        );

        companion object {
            /**
             * For fruit id
             *
             * @param fruitId The ID of the fruit to search for
             * @return The corresponding SeasonDefinitions object or null if not found
             */
            fun forFruitId(fruitId: Int): SeasonDefinitions? {
                // Iterate through all defined SeasonDefinitions values
                for (def in values()) {
                    // Skip null definitions
                    if (def == null) continue
                    // Check if the current definition's fruitId matches the provided fruitId
                    if (fruitId == def.fruitId) return def
                }
                // Return null if no matching definition is found
                return null
            }

            /**
             * For gate id
             *
             * @param gateId The ID of the gate to search for
             * @return The corresponding SeasonDefinitions object or null if not found
             */
            @JvmStatic
            fun forGateId(gateId: Int): SeasonDefinitions? {
                // Iterate through all defined SeasonDefinitions values
                for (def in values()) {
                    // Check if the current definition's gateId matches the provided gateId
                    if (gateId == def.gateId) return def
                }
                // Return null if no matching definition is found
                return null
            }

            /**
             * For juice id
             *
             * @param juiceId The ID of the juice to search for
             * @return The corresponding SeasonDefinitions object or null if not found
             */
            fun forJuiceId(juiceId: Int): SeasonDefinitions? {
                // Iterate through all defined SeasonDefinitions values
                for (def in values()) {
                    // Skip null definitions
                    if (def == null) continue
                    // Check if the current definition's juiceId matches the provided juiceId
                    if (juiceId == def.juiceId) return def
                }
                // Return null if no matching definition is found
                return null
            }

            /**
             * For tree id
             *
             * @param treeId The ID of the tree to search for
             * @return The corresponding SeasonDefinitions object or null if not found
             */
            fun forTreeId(treeId: Int): SeasonDefinitions? {
                // Iterate through all defined SeasonDefinitions values
                for (def in values()) {
                    // Skip null definitions
                    if (def == null) continue
                    // Check if the current definition's treeId matches the provided treeId
                    if (treeId == def.treeId) return def
                }
                // Return null if no matching definition is found
                return null
            }
        }
    }


    /**
     * Sqirk juice plugin.
     */
    class SqirkJuicePlugin : UseWithHandler(10844, 10845, 10846, 10847) {
        override fun handle(event: NodeUsageEvent): Boolean {
            val item: Item = event.usedItem
            val with: Item = event.baseItem
            val player: Player = event.player
            val def = SeasonDefinitions.forFruitId(item.id)
            if (with == null || player == null || def == null) return true
            val amt = player.inventory.getAmount(item)
            if (!player.inventory.containItems(1919)) {
                player.dialogueInterpreter.open(43382, 0)
                return true
            }
            if (amt < def.fruitAmt) {
                player.dialogueInterpreter.open(43382, 1, item.id)
                return true
            }
            player.animate(CRUSH_ITEM)
            player.skills.addExperience(Skills.COOKING, 5.0, true)
            player.inventory.remove(Item(item.id, def.fruitAmt))
            player.inventory.remove(Item(1919))
            player.inventory.add(Item(def.juiceId))
            player.dialogueInterpreter.sendDialogue("You squeeze " + def.fruitAmt + " sq'irks into an empty glass.")
            return true
        }

        @Throws(Throwable::class)
        override fun newInstance(arg: Any?): Plugin<Any?> {
            addHandler(233, ITEM_TYPE, this)
            return this
        }

        companion object {

            private val CRUSH_ITEM = Animation(364)
        }
    }

    companion object {
        private val ANIMATION = Animation(827)
        private val HERBS = intArrayOf(199, 201, 203, 205, 207, 209, 211, 213, 215, 217, 219, 2485, 3049, 3051, 199, 201, 203, 205)
        private val DRINK_ANIM = Animation(5796)
        private val TELE = Animation(714)
        private val Graphic = Graphic(111, 100, 1)
        private val PICK_FRUIT = Animation(2280)
    }
}

/**
 * Sqirk making dialogue.
 */
class SqirkMakingDialogue(player: Player? = null) : Dialogue(player) {
    private var dialogueId = 0
    private var definition: SorceressGarden.SeasonDefinitions? = null

    override fun getIds(): IntArray {
        return intArrayOf(43382)
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (dialogueId) {
            0 -> end()
            1 -> when (stage) {
                0 -> {
                    interpreter.sendDialogue("You need " + definition!!.fruitAmt + " sq'irks of this kind to fill a glass of juice.")
                    stage = 1
                }

                1 -> end()
            }
        }
        return true
    }

    override fun open(vararg args: Any): Boolean {
        dialogueId = args[0] as Int
        when (dialogueId) {
            0 -> player(FacialExpression.THINKING, "I should get an empty beer glass to", "hold the juice before I squeeze the fruit.")

            1 -> {
                definition = SorceressGarden.SeasonDefinitions.forFruitId(args[1] as Int)
                if (definition == null) end()
                player(FacialExpression.THINKING, "I think I should wait till I have", "enough fruits to make a full glass.")
            }
        }
        stage = 0
        return true
    }
}
