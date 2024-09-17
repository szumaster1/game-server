package content.minigame.pyramidplunder

import core.api.*
import org.rs.consts.Components
import org.rs.consts.Items
import core.game.component.Component
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.world.map.Direction
import core.game.world.map.Location
import core.tools.RandomFunction

/**
 * Plunder utils.
 */
object PlunderUtils {
    // Check if a player is currently participating in the Pyramid Plunder minigame
    fun hasPlayer(player: Player): Boolean {
        return PlunderData.playerLocations[player] != null
    }

    // Register a player for the Pyramid Plunder minigame
    fun registerPlayer(player: Player) {
        PlunderData.players.add(player)
        PlunderData.timeLeft[player] = 500 // 5 minutes
    }

    // Unregister a player from the Pyramid Plunder minigame
    fun unregisterPlayer(player: Player) {
        PlunderData.players.remove(player)
        PlunderData.playerLocations.remove(player)
        PlunderData.timeLeft.remove(player)
        removeAttribute(player, "pyramid-entrance")
    }

    // Expel a player from the Pyramid Plunder minigame
    fun expel(player: Player, summonMummy: Boolean) {
        teleport(player, Location.create(3288, 2802, 0))
        unregisterPlayer(player)
        resetOverlay(player)
        resetObjectVarbits(player)

        if (!summonMummy) {
            sendDialogue(player, "You've run out of time and the guardian mummy has thrown you out.")
        } else {
            sendDialogue(player, "You've been expelled by the guardian mummy.")
        }
    }

    // Decrement the remaining time for each player in the Pyramid Plunder minigame
    fun decrementTimeRemaining(): ArrayList<Player> {
        val timesUp = ArrayList<Player>()
        PlunderData.timeLeft.forEach { player, left ->
            if (left <= 0) timesUp.add(player).also { sendMessage(player, "Your time is up...") }
            PlunderData.timeLeft[player] = left - 1
            updateOverlay(player)
        }
        return timesUp
    }

    // Load the next room for a player in the Pyramid Plunder minigame
    fun loadNextRoom(player: Player) {
        val current = PlunderData.playerLocations[player]

        val next: PlunderRoom = if (current == null) getRoom(1)
        else getRoom(current.room + 1)

        if (PlunderData.playerLocations.filter { it.value == next }.isEmpty()) {
            PlunderData.doors[next.room - 1] = PlunderData.doorVarbits.random()
        }

        teleport(player, next.entrance)
        PlunderData.playerLocations[player] = next
    }

    // Get a specific room in the Pyramid Plunder minigame
    fun getRoom(number: Int): PlunderRoom {
        return PlunderData.rooms[number - 1]
    }

    // Get the current room for a player in the Pyramid Plunder minigame
    fun getRoom(player: Player): PlunderRoom? {
        return PlunderData.playerLocations[player]
    }

    // Get the level of the current room for a player in the Pyramid Plunder minigame
    fun getRoomLevel(player: Player): Int {
        return 11 + (10 * getRoom(player)!!.room)
    }

    // Get the destination location for a spear trap in the Pyramid Plunder minigame
    fun getSpearDestination(player: Player): Location {
        val room = getRoom(player)!!
        return when (room.spearDirection) {
            Direction.NORTH -> player.location.transform(0, 3, 0)
            Direction.SOUTH -> player.location.transform(0, -3, 0)
            Direction.WEST -> player.location.transform(-3, 0, 0)
            Direction.EAST -> player.location.transform(3, 0, 0)
            else -> player.location
        }
    }

    // Get the experience gained from opening an urn in the Pyramid Plunder minigame
    fun getUrnXp(player: Player, check: Boolean): Double {
        val room = getRoom(player)!!.room
        return if (check) {
            when (room) {
                1 -> 20.0
                2 -> 30.0
                3 -> 50.0
                4 -> 70.0
                5 -> 100.0
                6 -> 150.0
                7 -> 225.0
                8 -> 275.0
                else -> 0.0
            }
        } else {
            when (room) {
                1 -> 60.0
                2 -> 90.0
                3 -> 150.0
                4 -> 215.0
                5 -> 300.0
                6 -> 450.0
                7 -> 675.0
                8 -> 825.0
                else -> 0.0
            }
        }
    }

    // Reset the object varbits for a player in the Pyramid Plunder minigame
    fun resetObjectVarbits(player: Player) {
        setVarp(player, 821, 0)
        setVarp(player, 820, 0)
        PlunderData.doorVarbits.forEach { setVarbit(player, it, 0) }
    }

    // Open the overlay interface for a player in the Pyramid Plunder minigame
    fun openOverlay(player: Player) {
        player.interfaceManager.openOverlay(Component(Components.NTK_OVERLAY_428))
        updateOverlay(player)
    }

    // Update the overlay interface for a player in the Pyramid Plunder minigame
    fun updateOverlay(player: Player) {
        setVarbit(player, 2375, 500 - (PlunderData.timeLeft[player] ?: 0))
        setVarbit(player, 2376, 11 + (getRoom(player)!!.room * 10))
        setVarbit(player, 2377, getRoom(player)!!.room)
    }

    // Reset the overlay interface for a player in the Pyramid Plunder minigame
    fun resetOverlay(player: Player) {
        setVarbit(player, 2375, 0)
        player.packetDispatch.resetInterface(Components.NTK_OVERLAY_428)
        player.interfaceManager.closeOverlay()
    }

    // Get the door varbit for a player in the Pyramid Plunder minigame
    fun getDoor(player: Player): Int {
        if (getRoom(player) == null) return -1
        val room = getRoom(player)!!.room
        return PlunderData.doors[room - 1]
    }

    // Roll for a chance to obtain the Pharaoh's Sceptre in the Pyramid Plunder minigame
    fun rollSceptre(player: Player): Boolean {
        val room = getRoom(player)!!.room
        val chance = when (room) {
            1 -> 1500
            2 -> 1350
            3 -> 1250
            4 -> 1150
            else -> 1000
        }

        if (RandomFunction.roll(chance)) {
            expel(player, true)
            runTask(player, 2) {
                addItemOrDrop(player, Items.PHARAOHS_SCEPTRE_9050)
            }
            return true
        }
        return false
    }

    // Get the experience gained from opening a sarcophagus in the Pyramid Plunder minigame
    fun getSarcophagusXp(player: Player): Double {
        val room = getRoom(player)!!.room
        return when (room) {
            1 -> 20.0
            2 -> 30.0
            3 -> 50.0
            4 -> 70.0
            5 -> 100.0
            6 -> 150.0
            7 -> 225.0
            8 -> 275.0
            else -> 0.0
        }
    }

    // Get the experience gained from opening a chest in the Pyramid Plunder minigame
    fun getChestXp(player: Player): Double {
        if (getRoom(player) == null) {
            expel(player, false)
            return 0.0
        }
        val room = getRoom(player)!!.room
        return when (room) {
            1 -> 60.0
            2 -> 90.0
            3 -> 150.0
            4 -> 215.0
            5 -> 300.0
            6 -> 450.0
            7 -> 675.0
            8 -> 825.0
            else -> 0.0
        } * 0.66
    }

    // Get the experience gained from picking a door lock in the Pyramid Plunder minigame
    fun getDoorXp(player: Player, lockpick: Boolean): Double {
        val room = getRoom(player)?.room ?: return 0.0
        var reward = when (room) {
            1 -> 60.0
            2 -> 90.0
            3 -> 150.0
            4 -> 215.0
            5 -> 300.0
            6 -> 450.0
            7 -> 675.0
            8 -> 825.0
            else -> 0.0
        } * 0.66

        if (lockpick) reward /= 2.0

        return reward
    }

    // Roll for a chance to obtain an artifact in the Pyramid Plunder minigame
    fun rollArtifact(player: Player, tier: Int): Int {
        val room = getRoom(player)!!.room
        val divisor = (room * 2) * (tier * 35)
        val goldRate = divisor / 650.0
        val stoneRate = divisor / 250.0

        val roll = RandomFunction.RANDOM.nextDouble()
        if (goldRate > roll) return PlunderData.artifacts[2].random()
        if (stoneRate > roll) return PlunderData.artifacts[1].random()
        return PlunderData.artifacts[0].random()
    }

    // Check if it's time to switch the entrance in the Pyramid Plunder minigame
    fun checkEntranceSwitch() {
        if (System.currentTimeMillis() > PlunderData.nextEntranceSwitch) {
            PlunderData.currentEntrance = PlunderData.pyramidEntranceVarbits.random()
            PlunderData.nextEntranceSwitch = System.currentTimeMillis() + (60000 * 15)
        }
    }

    // Check if a door matches the current entrance in the Pyramid Plunder minigame
    fun checkEntrance(door: Node): Boolean {
        return door.asScenery().definition.varbitID == PlunderData.currentEntrance
    }

    // Roll for a chance to successfully steal from an urn in the Pyramid Plunder minigame
    fun rollUrnSuccess(player: Player, charmed: Boolean = false): Boolean {
        val level = getDynLevel(player, Skills.THIEVING)

        if (getRoom(player) == null) {
            return false
        }

        val room = getRoom(player)!!.room
        return RandomFunction.random(level) > (room * if (charmed) 2 else 4)
    }
}
