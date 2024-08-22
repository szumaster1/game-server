package content.minigame.mta.impl

import content.minigame.mta.MTAType
import content.minigame.mta.MTAZone
import cfg.consts.Music
import core.api.sendDialogueLines
import core.api.setAttribute
import core.game.interaction.Option
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.GameWorld.settings
import core.game.world.map.Location
import core.game.world.map.RegionManager.getNpc
import core.game.world.map.zone.ZoneBorders
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction

/**
 * Represents the Alchemist zone.
 */
class AlchemistZone :
    MTAZone("Alchemists' Playground", arrayOf(Item(8890), Item(6893), Item(6894), Item(6895), Item(6896), Item(6897))) {

    override fun leave(entity: Entity, logout: Boolean): Boolean {
        if (entity is Player && PLAYERS.contains(entity)) {
            PLAYERS.remove(entity)
            if (logout && entity.asPlayer().inventory.containsItem(COINS)) {
                val deposit = entity.asPlayer().inventory.getAmount(COINS)
                val `val` = deposit / 100
                val earn = if (`val` < 1) 0 else `val`
                incrementPoints(entity.asPlayer(), MTAType.ALCHEMISTS.ordinal, earn)
                entity.asPlayer().inventory.remove(COINS)
            }
            entity.asPlayer().removeAttribute("alchemist-session")
        }
        return super.leave(entity, logout)
    }

    override fun update(player: Player?) {
        player!!.packetDispatch.sendString(
            "" + player.getSavedData().activityData.getPizazzPoints(type!!.ordinal),
            type!!.overlay.id,
            3
        )
    }

    override fun enter(entity: Entity): Boolean {
        if (guardian == null) {
            guardian = getNpc(Location(3363, 9627, 2), 3099, 20)
        }
        if (entity is Player && !PLAYERS.contains(entity)) {
            PLAYERS.add(entity.asPlayer())
            if (!PULSE.isRunning) {
                PULSE.restart()
                PULSE.start()
                Pulser.submit(PULSE)
            }
            entity.asPlayer().removeAttribute("alch-earn")
            setSession(entity.asPlayer())
            updateInterface(entity.asPlayer())
            if(!entity.asPlayer().musicPlayer.hasUnlocked(Music.GOLDEN_TOUCH_535)){
                entity.asPlayer().musicPlayer.unlock(Music.GOLDEN_TOUCH_535)
            }
        }
        return super.enter(entity)
    }

    override fun interact(e: Entity, target: Node, option: Option): Boolean {
        if (e is Player) {
            val player = e.asPlayer()
            if (target.id == 10734) {
                deposit(player)
                return true
            } else if (target.id in 6893..6897 && option.name != "drop" && option.name != "take") {
                player.dialogueInterpreter.sendDialogue("This item isn't yours to wield, it belongs to the arena!")
                return true
            } else if (target.name == "Cupboard") {
                search(player, target.asScenery())
                return true
            }
        }
        return super.interact(e, target, option)
    }

    private fun search(player: Player, `object`: Scenery) {
        val session = getSession(player)
        val item = session.getItem(`object`.id)
        if (`object`.id % 2 != 0) {
            player.animate(Animation.create(3032))
            SceneryBuilder.replace(`object`, `object`.transform(`object`.id + 1), 35)
        }
        if (item == null) {
            player.sendMessage("The cupboard is empty.")
            return
        }
        if (player.inventory.freeSlots() < 1) {
            player.sendMessage("You have no free space to hold any more items.")
            return
        }
        player.lock(1)
        player.inventory.add(item.item)
        player.sendMessage("You found: " + item.item.name)
    }

    private fun deposit(player: Player) {
        if (!player.inventory.containsItem(COINS)) {
            player.dialogueInterpreter.sendDialogue("You don't have any coins to deposit.")
            return
        }
        val deposit = player.inventory.getAmount(COINS)
        if (deposit >= 12000) {
            player.teleport(Location(3363, 3302, 0))
            sendDialogueLines(player,
                "You have been ejected from the arena! You were warned",
                "not to deposit more than 12000 coins at once."
            )
            return
        }
        if (player.inventory.remove(Item(COINS.id, deposit))) {
            val `val` = deposit / 100
            val earn = if (`val` < 1) 0 else `val`
            val exp = deposit * 2
            var taking = player.getAttribute("alch-earn", 0)
            val add = (if (`val` > 0) deposit / 100 * 10 else 0)
            if (add != 0) {
                taking += add
            }
            if (earn != 0) {
                incrementPoints(player, MTAType.ALCHEMISTS.ordinal, earn)
            }
            if (taking != 0) {
                setAttribute(player, "alch-earn", taking)
            }
            player.getSkills().addExperience(Skills.MAGIC, exp.toDouble(), true)
            sendDialogueLines(player,
                "You've just deposited $deposit coins, earning you $earn Alchemist Pizazz",
                "Points and $exp magic XP. So far you're taking $taking coins as a",
                "a reward when you leave!"
            )
        }
    }

    override fun configure() {
        shufflePrices()
        PULSE.stop()
        register(ZoneBorders(3341, 9618, 3393, 9654, 2))
    }


    /**
     * Alchemist session
     *
     * @param player The player participating in the alchemist session
     * @constructor Initializes an Alchemist session with the specified player
     */
    class AlchemistSession(val player: Player) {
        private var indexer = 0 // Indexer to track the current position in the session

        init {
            shuffleObjects() // Shuffle objects at the start of the session
        }

        /**
         * Get item
         *
         * @param id The identifier of the item to retrieve
         * @return The AlchemistItem associated with the given id, or null if not found
         */
        fun getItem(id: Int): AlchemistItem? {
            val ids = intArrayOf(10797, 10795, 10793, 10791, 10789, 10787, 10785, 10783)
            var index = 0
            for (i in ids.indices) {
                if (ids[i] == id || ids[i] + 1 == id) {
                    index = i
                    break
                }
            }
            var alchIndex = indexer + index
            if (indexer != 0) {
                alchIndex -= if (indexer >= 4 && index < 4) {
                    if (indexer == 4 && indexer - index < 4) {
                        return null
                    }
                    if (indexer == 4) {
                        return AlchemistItem.LEATHER_BOOTS
                    } else {
                        if (indexer == 6 && index == 0) {
                            return AlchemistItem.ADAMANT_HELM
                        } else if (indexer == 6 && index == 1) {
                            return AlchemistItem.ADAMANT_KITESHIELD
                        } else if (indexer == 6 && index == 2) {
                            return AlchemistItem.LEATHER_BOOTS
                        } else if (indexer == 7 && index == 0) {
                            return AlchemistItem.EMERALD
                        } else if (indexer == 7 && index == 1) {
                            return AlchemistItem.ADAMANT_HELM
                        } else if (indexer == 7 && index == 2) {
                            return AlchemistItem.ADAMANT_KITESHIELD
                        } else if (indexer == 7 && index == 3) {
                            return AlchemistItem.LEATHER_BOOTS
                        } else if (indexer == 5 && index == 0) {
                            return AlchemistItem.ADAMANT_KITESHIELD
                        } else if (indexer == 5 && index == 1) {
                            return AlchemistItem.LEATHER_BOOTS
                        }
                    }
                    return null
                } else {
                    indexer + indexer
                }
            }
            val finalIndex = AlchemistItem.values().size - 1 - alchIndex
            return if (finalIndex > AlchemistItem.values().size - 1 || finalIndex < 0) {
                null
            } else AlchemistItem.values()[finalIndex]
        }

        /**
         * Shuffle objects
         *
         */
        fun shuffleObjects() {
            indexer++
            if (indexer > 7) {
                indexer = 0
            }
        }
    }


    /**
     * Alchemist item
     *
     * @param item Represents the item associated with the AlchemistItem.
     * @constructor Alchemist item initializes the enum with a specific item.
     */
    enum class AlchemistItem(val item: Item) {
        /**
         * Leather Boots
         *
         * @constructor Leather Boots initializes the enum with the item ID for Leather Boots.
         */
        LEATHER_BOOTS(Item(6893)),

        /**
         * Adamant Kiteshield
         *
         * @constructor Adamant Kiteshield initializes the enum with the item ID for Adamant Kiteshield.
         */
        ADAMANT_KITESHIELD(Item(6894)),

        /**
         * Adamant Helm
         *
         * @constructor Adamant Helm initializes the enum with the item ID for Adamant Helm.
         */
        ADAMANT_HELM(Item(6895)),

        /**
         * Emerald
         *
         * @constructor Emerald initializes the enum with the item ID for Emerald.
         */
        EMERALD(Item(6896)),

        /**
         * Rune Longsword
         *
         * @constructor Rune Longsword initializes the enum with the item ID for Rune Longsword.
         */
        RUNE_LONGSWORD(Item(6897));

        // Cost of the item, initialized to 0 and can only be modified within the class.
        var cost = 0
            private set

        // Calculates the child value based on the ordinal position of the enum.
        val child: Int
            get() = 14 + ordinal

        companion object {
            /**
             * Retrieves the AlchemistItem associated with a given item ID.
             *
             * @param id The ID of the item to search for.
             * @return The corresponding AlchemistItem or null if not found.
             */
            fun forItem(id: Int): AlchemistItem? {
                // Iterates through all values of the enum to find a match.
                for (item in values()) {
                    // Checks if the current item's ID matches the provided ID.
                    if (item.item.id == id) {
                        return item // Returns the matching AlchemistItem.
                    }
                }
                return null // Returns null if no match is found.
            }
        }
    }

    companion object {

        var ZONE = AlchemistZone()


        val COINS = Item(8890)
        private val PLAYERS: MutableList<Player> = ArrayList(20)
        private var guardian: NPC? = null
        var freeConvert: AlchemistItem? = null
        private val PULSE: Pulse = object : Pulse(if (settings!!.isDevMode) 15 else 53) {
            override fun pulse(): Boolean {
                if (PLAYERS.isEmpty()) {
                    return true
                }
                shufflePrices()
                var forceChat = "The costs are changing!"
                if (freeConvert == null && RandomFunction.random(3) < 3) {
                    freeConvert = RandomFunction.getRandomElement(AlchemistItem.values())
                    forceChat =
                        "The " + freeConvert!!.item.name.lowercase() + " " + (if (freeConvert == AlchemistItem.LEATHER_BOOTS) "are" else "is") + " free to convert!"
                } else if (freeConvert != null) {
                    freeConvert = null
                }
                guardian!!.sendChat(forceChat)
                for (p in PLAYERS) {
                    if (p == null || !p.isActive) {
                        continue
                    }
                    getSession(p)!!.shuffleObjects()
                    updateInterface(p)
                }
                return false
            }
        }


        fun shufflePrices() {
            val list: List<Int> = mutableListOf(1, 5, 8, 10, 15, 20, 30).shuffled()
            for (i in AlchemistItem.values().indices)
                AlchemistItem.values()[i].cost == (list[i])
        }


        fun updateInterface(player: Player) {
            for (i in AlchemistItem.values()) {
                player.packetDispatch.sendInterfaceConfig(194, i.child, if (freeConvert == null) true else if (freeConvert == i) false else true)
                player.packetDispatch.sendString(i.cost.toString() + "", 194, 9 + i.ordinal)
            }
        }


        fun setSession(player: Player): AlchemistSession {
            val session = AlchemistSession(player)
            setAttribute(player, "alchemist-session", session)
            return session
        }


        fun getSession(player: Player): AlchemistSession {
            var session = player.getAttribute<AlchemistSession>("alchemist-session", null)
            if (session == null) {
                session = setSession(player)
            }
            return session
        }
    }
}
