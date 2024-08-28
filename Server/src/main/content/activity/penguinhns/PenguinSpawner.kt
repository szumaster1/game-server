package content.activity.penguinhns

import cfg.consts.NPCs
import core.game.node.entity.npc.NPC
import core.game.world.map.Location

/**
 * Penguin spawner.
 *
 * This class is responsible for spawning penguins in the game world.
 */
class PenguinSpawner {

    /**
     * Spawn penguins.
     *
     * @param amount The number of penguins to spawn.
     * @return The list of ordinals of the spawned penguins.
     */
    fun spawnPenguins(amount: Int): ArrayList<Int> {
        var counter = 0
        val availableOrdinals = (0 until Penguin.values().size).toMutableList()
        val spawnedOrdinals = ArrayList<Int>()
        while (counter < amount) {
            val peng = Penguin.values()[availableOrdinals.random()]
            availableOrdinals.remove(peng.ordinal)
            spawnedOrdinals.add(peng.ordinal)
            NPC(peng.id, peng.location).also { PenguinManager.npcs.add(it);it.isNeverWalks = true; it.isWalks = false }
                .init()
            counter++
        }
        return spawnedOrdinals
    }

    /**
     * Spawn penguins.
     *
     * @param ordinals The list of ordinals of the penguins to spawn.
     */
    fun spawnPenguins(ordinals: List<Int>) {
        ordinals.forEach {
            val peng = Penguin.values()[it]
            NPC(peng.id, peng.location).also { PenguinManager.npcs.add(it); it.isNeverWalks = true; it.isWalks = false }
                .init()
        }
    }
}

/**
 * Penguin.
 *
 * This enum represents different types of penguins in the game.
 */
enum class Penguin(val id: Int, val hint: String, val location: Location) {
    /**
     * Cactus 1
     */
    CACTUS_1(NPCs.CACTUS_8107, "located in the northern desert.", Location(3310, 3157, 0)),

    /**
     * Cactus 2
     */
    CACTUS_2(NPCs.CACTUS_8107, "located in the southern desert.", Location.create(3259, 3052, 0)),

    /**
     * Bush 1
     */
    BUSH_1(NPCs.BUSH_8105, "located between Fremennik and barbarians.", Location.create(2532, 3588, 0)),

    /**
     * Bush 2
     */
    BUSH_2(NPCs.BUSH_8105, "located where banana smugglers dwell.", Location.create(2740, 3233, 0)),

    /**
     * Bush 3
     */
    BUSH_3(NPCs.BUSH_8105, "located south of Ardougne.", Location.create(2456, 3092, 0)),

    /**
     * Bush 4
     */
    BUSH_4(NPCs.BUSH_8105, "located deep in the jungle.", Location.create(2832, 3053, 0)),

    /**
     * Bush 5
     */
    BUSH_5(NPCs.BUSH_8105, "located where eagles fly.", Location.create(2326, 3516, 0)),

    /**
     * Bush 6
     */
    BUSH_6(NPCs.BUSH_8105, "located south of Ardougne.", Location.create(2513, 3154, 0)),

    /**
     * Bush 7
     */
    BUSH_7(NPCs.BUSH_8105, "located near a big tree surrounded by short people.", Location.create(2387, 3451, 0)),

    /**
     * Bush 8
     */
    BUSH_8(NPCs.BUSH_8105, "located in the kingdom of Asgarnia.", Location.create(2951, 3511, 0)),

    /**
     * Bush 9
     */
    BUSH_9(NPCs.BUSH_8105, "located in the northern desert.", Location.create(3350, 3311, 0)),

    /**
     * Bush 10
     */
    BUSH_10(NPCs.BUSH_8105, "located somewhere in the kingdom of Kandarin.", Location.create(2633, 3501, 0)),

    /**
     * Bush 11
     */
    BUSH_11(NPCs.BUSH_8105, "located south of Ardougne.", Location.create(2440, 3206, 0)),

    /**
     * Bush 12
     */
    BUSH_12(NPCs.BUSH_8105, "located where wizards study.", Location.create(3112, 3149, 0)),

    /**
     * Rock 1
     */
    ROCK_1(NPCs.ROCK_8109, "located where the Imperial Guard train.", Location.create(2852, 3578, 0)),

    /**
     * Rock 2
     */
    ROCK_2(NPCs.ROCK_8109, "located in the kingdom of Misthalin.", Location.create(3356, 3416, 0)),

    /**
     * Rock 3
     */
    ROCK_3(NPCs.ROCK_8109, "located near some ogres.", Location.create(2631, 2980, 0)),

    /**
     * Rock 4
     */
    ROCK_4(NPCs.ROCK_8109, "located in the Kingdom of Asgarnia.", Location.create(3013, 3501, 0)),

    /**
     * Rock 5
     */
    ROCK_5(NPCs.ROCK_8109, "located between Fremennik and barbarians.", Location.create(2532, 3630, 0)),

    /**
     * Crate 1
     */
    CRATE_1(NPCs.CRATE_8108, "located in the kingdom of Misthalin.", Location.create(3112, 3332, 0)),

    /**
     * Crate 2
     */
    CRATE_2(NPCs.CRATE_8108, "located in the Kingdom of Misthalin.", Location.create(3305, 3508, 0)),

    /**
     * Barrel 1
     */
    BARREL_1(NPCs.CRATE_8108, "located where no weapons may go.", Location.create(2806, 3383, 0)),

    /**
     * Toadstool 1
     */
    TOADSTOOL_1(NPCs.TOADSTOOL_8110, "located in the kingdom of Misthalin.", Location.create(3156, 3178, 0)),

    /**
     * Toadstool 2
     */
    TOADSTOOL_2(NPCs.TOADSTOOL_8110, "located in the fairy realm.", Location.create(2409, 4462, 0));

    companion object {
        private val locationMap = values().associateBy { it.location.toString() }

        fun forLocation(location: Location): Penguin? {
            return locationMap[location.toString()]
        }
    }
}
