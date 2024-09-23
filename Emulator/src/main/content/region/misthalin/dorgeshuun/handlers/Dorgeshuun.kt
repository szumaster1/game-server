package content.region.misthalin.dorgeshuun.handlers

import org.rs.consts.Items
import org.rs.consts.NPCs
import org.rs.consts.Scenery
import content.region.misthalin.dorgeshuun.dialogue.CaveGoblinsDialogueFile
import core.api.*
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.world.map.Location
import core.tools.RandomFunction

/**
 * Represents the Dorgeshuun listeners.
 */
class Dorgeshuun : InteractionListener {

    companion object {
        val CAVE_GOBLINS = intArrayOf(NPCs.CAVE_GOBLIN_MINER_2069, NPCs.CAVE_GOBLIN_MINER_2070, NPCs.CAVE_GOBLIN_MINER_2071, NPCs.CAVE_GOBLIN_MINER_2072, NPCs.CAVE_GOBLIN_MINER_2075, NPCs.CAVE_GOBLIN_MINER_2076, NPCs.CAVE_GOBLIN_MINER_2077, NPCs.CAVE_GOBLIN_MINER_2078, NPCs.CAVE_GOBLIN_MINER_2079, NPCs.CAVE_GOBLIN_MINER_2080, NPCs.CAVE_GOBLIN_MINER_2081)
        val BONE_DOORS = intArrayOf(32952, 32953, 22945)
        val DOORS = intArrayOf(22913,22914)
    }

    private val npcMap = HashMap<Int, Int>()

    init {
        for (id in CAVE_GOBLINS) npcMap[id] = 0
    }

    override fun defineListeners() {

        npcMap[0]?.let {
            on(it, IntType.NPC, "talk-to") { player, _ ->
                openDialogue(player, CaveGoblinsDialogueFile())
                return@on true
            }
        }

        /*
         * Talk-to Ambassador.
         */

        on(NPCs.AMBASSADOR_ALVIJAR_5863, IntType.NPC, "talk-to") { player, _ ->
             // (WrapperID for Abassador Alvijar).
            player.dialogueInterpreter.open(5887)
            return@on true
        }

        /*
         * Handle exchange brooch for mining helmet with Mistag NPC.
         */

        onUseWith(IntType.NPC, Items.BROOCH_5008, NPCs.MISTAG_2084) { player, used, _ ->
            val randomHelm: Int = RandomFunction.getRandomElement(arrayOf(Items.MINING_HELMET_5013, Items.MINING_HELMET_5014))

            if (isQuestComplete(player, "Lost Tribe") && !removeItem(player, used.asItem(), Container.INVENTORY)) {
                sendMessage(player, "Nothing interesting happens.")
                return@onUseWith true
            } else {
                openDialogue(player, object : DialogueFile() {
                    override fun handle(componentID: Int, buttonID: Int) {
                        npc = NPC(NPCs.MISTAG_2084)
                        when (stage) {
                            0 -> playerl(FacialExpression.HALF_ASKING, "Is this your brooch?").also { stage++ }
                            1 -> npc(FacialExpression.OLD_NORMAL, "Yes! I thought I'd lost it. Thank you. Have one of these", "helmets. It will be useful if you want to work in the mine.").also { stage++ }
                            2 -> {
                                end()
                                addItemOrDrop(player, randomHelm, 1)
                                sendItemDialogue(player, randomHelm, "Mistag hands you a Mining helmet.")
                            }
                        }
                    }
                })
            }
            return@onUseWith true
        }

        /*
         * Handle the gate to Dorgeshuun interaction.
         */

        on(BONE_DOORS, IntType.SCENERY, "open") { player, node ->
            when (node.id) {
                32952, 32953 -> {
                    if (!isQuestComplete(player, "Lost Tribe")) sendNPCDialogue(player, NPCs.CAVE_GOBLIN_GUARD_2073, "Surface-dweller! You may not pass through that door!", FacialExpression.OLD_NORMAL).also { player.faceLocation(Location(3318, 9604, 0)) }
                    else teleport(player, location(if (node.id == Scenery.BONE_DOOR_32952) 2747 else 2748, 5374, 0))
                }
                22945 -> teleport(player, location(3318, 9602, 0))
            }
            return@on true
        }

        /*
         * Handling dorgeshuun gates.
         */

        on(DOORS, IntType.SCENERY, "open"){ player, node ->
            DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            return@on true
        }
    }


}
