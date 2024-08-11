package content.region.karamja.quest.tribaltotem

import core.api.*
import core.api.consts.Items
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import core.game.node.scenery.SceneryBuilder
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

/**
 * Tribal totem listeners.
 */
class TribalTotemListeners : InteractionListener {

    // Define the IDs for various objects in the game
    val frontDoor = 2706
    val wizCrate = 2707
    val realCrate = 2708
    val label = Items.ADDRESS_LABEL_1858
    val lockedDoor = 2705
    val stairs = 2711
    val closedChest = 2709
    val openChest = 2710

    override fun defineListeners() {
        // Listener for the front door
        on(frontDoor, IntType.SCENERY, "Open") { player, door ->
            // Check if the player has reached a certain stage in the "Tribal Totem" quest
            if (player.questRepository.getStage("Tribal Totem") >= 35) {
                DoorActionHandler.handleAutowalkDoor(player, door.asScenery())
            }
            sendMessage(player, "The door is locked shut.")
            return@on true
        }

        // Listener for the real crate
        on(realCrate, IntType.SCENERY, "Investigate") { player, _ ->
            // Check if the player is at a certain stage in the "Tribal Totem" quest and does not have the address label item
            if (player.questRepository.getStage("Tribal Totem") in 1..19 && !player.inventory.containsAtLeastOneItem(Items.ADDRESS_LABEL_1858)) {
                sendDialogue(player, "There is a label on this crate. It says; To Lord Handelmort, Handelmort Mansion Ardogune.You carefully peel it off and take it.")
                addItem(player, Items.ADDRESS_LABEL_1858, 1)
            } else if (player.questRepository.getStage("Tribal Totem") in 1..19 && player.inventory.containsAtLeastOneItem(Items.ADDRESS_LABEL_1858)) {
                sendDialogue(player, "There was a label on this crate, but it's gone now since you took it!")
            }
            return@on true
        }

        // Listener for the wizard's crate
        on(wizCrate, IntType.SCENERY, "Investigate") { player, _ ->
            sendDialogue(player, "There is a label on this crate: Senior Patents Clerk, Chamber of Invention, The Wizards' Tower, Misthalin. The crate is securely fastened shut and ready for delivery.")
            return@on true
        }

        // Listener for using the address label on the wizard's crate
        onUseWith(IntType.SCENERY, label, wizCrate) { player, _, _ ->
            sendDialogue(player, "You carefully place the delivery address label over the existing label, covering it completely.")
            removeItem(player, label)
            player.questRepository.getQuest("Tribal Totem").setStage(player, 20)
            return@onUseWith true
        }

        // Listener for the locked door
        on(lockedDoor, IntType.SCENERY, "Open") { player, node ->
            // Check if the door has been unlocked by the player
            if (player.getAttribute("TT:DoorUnlocked", false) == true) {
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            } else {
                openInterface(player, 369)
            }
            return@on true
        }

        // Listener for the stairs
        on(stairs, IntType.SCENERY, "Climb-up") { player, _ ->
            // Check if the player has checked the stairs for traps
            if (player.getAttribute("TT:StairsChecked", false)) {
                core.game.global.action.ClimbActionHandler.climb(player, Animation(828), Location.create(2629, 3324, 1))
            } else {
                sendMessage(player, "You set off a trap and the stairs give way under you, dropping you into the sewers.")
                player.teleport(Location.create(2641, 9721, 0))
            }
            return@on true
        }

        // Listener for investigating the stairs
        on(stairs, IntType.SCENERY, "Investigate") { player, _ ->
            // Check if the player's thieving level is high enough to detect the trap
            if (player.getSkills().getStaticLevel(Skills.THIEVING) >= 21) {
                sendDialogue(player, "Your trained senses as a thief enable you to see that there is a trap in these stairs. You make a note of its location for future reference when using these stairs")
                setAttribute(player, "/save:TT:StairsChecked", true)
            } else {
                sendDialogue(player, "You don't see anything out of place on these stairs.")
            }
            return@on true
        }

        // Listener for the closed chest
        on(closedChest, IntType.SCENERY, "Open") { _, node ->
            SceneryBuilder.replace(node.asScenery(), node.asScenery().transform(2710))
            return@on true
        }

        // Listener for the open chest
        on(openChest, IntType.SCENERY, "Close") { _, node ->
            SceneryBuilder.replace(node.asScenery(), node.asScenery().transform(2709))
            return@on true
        }

        // Listener for searching the open chest
        on(openChest, IntType.SCENERY, "Search") { player, _ ->
            // Check if the player already has the tribal totem item
            if (!player.inventory.containsAtLeastOneItem(Items.TOTEM_1857)) {
                sendDialogue(player, "Inside the chest you find the tribal totem.")
                addItem(player, Items.TOTEM_1857)
            } else {
                sendDialogue(player, "Inside the chest you don't find anything because you already took the totem!")
            }
            return@on true
        }
    }
}