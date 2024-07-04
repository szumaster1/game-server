package content.minigame.mta.impl

import content.minigame.mta.MTAZone
import core.api.setAttribute
import core.game.interaction.Option
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.GroundItem
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.GameWorld.settings
import core.game.world.map.Location
import core.game.world.map.RegionManager.getNpc
import core.game.world.map.zone.ZoneBorders
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction

class EnchantingZone :
    MTAZone("Enchantment Chamber", arrayOf(Item(6899), Item(6898), Item(6900), Item(6901), Item(6903), Item(6902))) {

    override fun leave(entity: Entity, logout: Boolean): Boolean {
        if (entity is Player && PLAYERS.contains(entity)) {
            PLAYERS.remove(entity)
        }
        return super.leave(entity, logout)
    }

    override fun enter(entity: Entity): Boolean {
        if (guardian == null) {
            guardian = getNpc(Location(3361, 9647, 0), 3100, 20)
        }
        if (entity is Player && !PLAYERS.contains(entity)) {
            PLAYERS.add(entity.asPlayer())
            if (!PULSE.isRunning) {
                PULSE.restart()
                PULSE.start()
                Pulser.submit(PULSE)
            }
            createGroundSpawns(entity.asPlayer())
            BONUS_SHAPE.setAsBonus(entity.asPlayer())
            update(entity.asPlayer())
        }
        return super.enter(entity)
    }

    override fun interact(e: Entity, target: Node, option: Option): Boolean {
        if (e is Player) {
            val player = e.asPlayer()
            if (target.id >= 10799 && target.id <= 10802) {
                Shapes.forId(target.id)!!.take(player, target.asScenery())
                return true
            }
            if (target.id == 10803) {
                deposit(player)
                return true
            }
        }
        return super.interact(e, target, option)
    }


    fun createGroundSpawns(player: Player) {
        if (DSPAWNS.containsKey(player.name)) {
            val items = getGroundSpawns(player)
            for (item in items) {
                item.dropper = player
            }
            DSPAWNS[player.name] = items
            return
        }
        val items = getGroundSpawns(player)
        for (location in STONES) {
            val item: GroundItem = object : GroundItem(Item(6903), location, player) {
                override fun isAutoSpawn(): Boolean {
                    return true
                }

                override fun isActive(): Boolean {
                    return true
                }

                override fun isRemainPrivate(): Boolean {
                    return true
                }

                override fun respawn() {
                    Pulser.submit(getRespawnPulse(this))
                }
            }
            items.add(item)
            GroundItemManager.create(item)
        }
        DSPAWNS[player.name] = items
    }

    fun getRespawnPulse(item: GroundItem?): Pulse {
        return object : Pulse(if (settings!!.isDevMode) 45 else RandomFunction.random(700, 800)) {
            override fun pulse(): Boolean {
                GroundItemManager.create(item)
                return true
            }
        }
    }

    fun removeGroundSpawns(player: Player) {
        val items: List<GroundItem> = getGroundSpawns(player)
        for (item in items) {
            GroundItemManager.destroy(item)
        }
    }

    fun getGroundSpawns(player: Player): MutableList<GroundItem> {
        var items = DSPAWNS[player.name]
        if (items == null) {
            items = ArrayList()
        }
        DSPAWNS[player.name] = items
        return items
    }

    private fun deposit(player: Player) {
        if (!player.inventory.containsItem(ORB)) {
            player.sendMessage("You don't have any orbs to deposit.")
            return
        }
        val amount = player.inventory.getAmount(ORB)
        player.animate(Animation.create(832))
        player.inventory.remove(Item(ORB.id, amount))
        var prevAmt = player.getAttribute("mta-orb", 0)
        prevAmt += amount
        if (prevAmt >= 20) {
            prevAmt = 0
            player.inventory.add(RandomFunction.getRandomElement(RUNES), player)
            player.dialogueInterpreter.sendDialogue("Congratulations! You've been rewarded with an item for your efforts.")
        }
        setAttribute(player, "mta-orb", prevAmt)
    }

    override fun configure() {
        PULSE.stop()
        register(ZoneBorders(3335, 9612, 3389, 9663, 0, true))
    }


    enum class Shapes(val objectId: Int, val item: Item) {
        CUBE(10799, Item(6899)),
        CYLINDER(10800, Item(6898)),
        PENTAMID(10802, Item(6901)),
        ICOSAHEDRON(10801, Item(6900));

        fun take(player: Player, `object`: Scenery?) {
            if (!player.inventory.hasSpaceFor(item)) {
                player.sendMessage("You have no space left in your inventory.")
                return
            }
            player.lock(1)
            player.inventory.add(item)
            player.animate(Animation.create(827))
        }

        fun setAsBonus(player: Player) {
            for (s in values()) {
                player.packetDispatch.sendInterfaceConfig(195, s.child, true)
            }
            player.packetDispatch.sendInterfaceConfig(195, child, false)
        }

        val child: Int
            get() = 1 + ordinal

        companion object {

            fun forItem(item: Item): Shapes? {
                for (s in values()) {
                    if (s.item.id == item.id) {
                        return s
                    }
                }
                return null
            }


            fun forId(id: Int): Shapes? {
                for (s in values()) {
                    if (s.objectId == id) {
                        return s
                    }
                }
                return null
            }
        }
    }

    companion object {
        val ZONE = EnchantingZone()
        var BONUS_SHAPE = RandomFunction.getRandomElement(Shapes.values())
        private val RUNES = arrayOf(Item(560, 3), Item(565, 3), Item(564, 3))
        private val STONES = arrayOf(
            Location.create(3354, 9645, 0),
            Location.create(3353, 9635, 0),
            Location.create(3359, 9632, 0),
            Location.create(3375, 9633, 0),
            Location.create(3374, 9643, 0),
            Location.create(3373, 9651, 0)
        )
        private val ORB = Item(6902)
        private val PLAYERS: MutableList<Player> = ArrayList(20)
        private var guardian: NPC? = null
        private val DSPAWNS: MutableMap<String, MutableList<GroundItem>> = HashMap()
        private val PULSE: Pulse = object : Pulse(36) {
            override fun pulse(): Boolean {
                if (PLAYERS.isEmpty()) {
                    return true
                }
                var shape: Shapes? = null
                while (shape == null) {
                    shape = RandomFunction.getRandomElement(Shapes.values())
                    if (shape == BONUS_SHAPE) {
                        shape = null
                    }
                }
                BONUS_SHAPE = shape
                for (player in PLAYERS) {
                    if (player == null || !player.isActive) {
                        continue
                    }
                    BONUS_SHAPE.setAsBonus(player)
                }
                if (guardian != null) {
                    guardian!!.sendChat(
                        "The bonus shape has changed to the " + BONUS_SHAPE.name.lowercase().replace("_", " ")
                            .trim { it <= ' ' } + ".")
                }
                return false
            }
        }
    }
}
