package content.minigame.blastfurnace

import core.api.animate
import core.api.queueScript
import core.api.teleport
import core.game.interaction.QueueStrength
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.Direction
import core.game.world.map.Location
import org.json.simple.JSONObject

/**
 * Blast furnace belt ore.
 *
 * @property player The player who is interacting with the blast furnace belt ore.
 * @property id The ID of the ore.
 * @property amount The amount of ore.
 * @property location The current location of the ore.
 * @property npcInstance The instance of the NPC associated with the ore.
 * @constructor Initializes the BFBeltOre class.
 */
class BFBeltOre(
    val player: Player,
    val id: Int,
    val amount: Int,
    var location: Location,
    var npcInstance: NPC? = null
) {

    val state = BlastFurnace.getPlayerState(player)

    /**
     * Tick function that handles the behavior of the ore.
     *
     * @return Returns true if the ore has reached the end location and has been deposited, false otherwise.
     */
    fun tick(): Boolean {
        if (location == ORE_END_LOC && npcInstance != null) {
            state.container.addOre(id, amount)
            npcInstance?.clear()
            npcInstance = null
            return true
        } else if (npcInstance != null) {
            location = location.transform(Direction.SOUTH, 1)
            teleport(npcInstance!!, location)

            if (location == ORE_END_LOC) {
                val npc = npcInstance!!
                queueScript(npc, 1, QueueStrength.STRONG) { _ ->
                    animate(npc, ORE_DEPOSIT_ANIM)
                    return@queueScript true
                }
            }
        }
        return false
    }

    /**
     * Creates an NPC associated with the ore.
     */
    fun createNpc() {
        if (npcInstance != null) return
        val npcId = BlastFurnace.getNpcForOre(id)
        val npc = NPC.create(npcId, location)
        npc.isWalks = false
        npc.init()

        npcInstance = npc
    }

    /**
     * Converts the BFBeltOre object to a JSON object.
     *
     * @return Returns the JSON object representation of the BFBeltOre object.
     */
    fun toJson(): JSONObject {
        val root = JSONObject()
        root["id"] = id.toString()
        root["amount"] = amount.toString()
        root["location"] = location.toString()
        return root
    }

    companion object {
        val ORE_START_LOC = Location.create(1942, 4966, 0)
        val ORE_END_LOC = Location.create(1942, 4963, 0)
        val ORE_DEPOSIT_ANIM = 2434
    }
}
