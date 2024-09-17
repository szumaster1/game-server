package content.global.random.event.mime

import core.api.*
import org.rs.consts.Components
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.emote.Emotes
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.tools.RandomFunction

/**
 * Mime utils.
 */
object MimeUtils {

    // Define the location of the mime event
    val MIME_EVENT_LOCATION = Location(2008, 4764, 0)
    // Define the location of the mime
    val MIME_LOCATION = Location(2008, 4762, 0)
    // Define the location of the player
    val PLAYER_LOCATION = Location(2007, 4761, 0)
    // Define the location of the scenery
    val SCENERY_LOCATION = Location(2010, 4761, 0)

    // Define attribute constants
    const val TELEPORT_ATTRIBUTE = "/save:original-loc"
    const val LOGOUT_ATTRIBUTE = "mime:save-location"
    const val EMOTE_ATTRIBUTE = "mime:emote"
    const val COPY_ATTRIBUTE = "mime:emote-copy"
    const val CORRECT_ATTRIBUTE = "mime:emote-correct"
    const val FAIL_ATTRIBUTE = "mime:emote-wrong"

    // Define item constants
    const val LIGH_ON = 3644
    const val LIGHT_OFF = 3645

    /**
     * Cleanup.
     *
     * @param player The player to clean up.
     */
    fun cleanup(player: Player) {
        // Restore the player's teleport location
        player.properties.teleportLocation = getAttribute(player, TELEPORT_ATTRIBUTE, null)
        // Clear the logout listener
        clearLogoutListener(player, LOGOUT_ATTRIBUTE)
        // Remove all attributes related to the mime event
        removeAttributes(player, LOGOUT_ATTRIBUTE, TELEPORT_ATTRIBUTE, EMOTE_ATTRIBUTE, COPY_ATTRIBUTE, CORRECT_ATTRIBUTE, FAIL_ATTRIBUTE)
        // Restore the player's tabs
        restoreTabs(player)
        // Close the interface
        closeInterface(player)
        // Unlock the player
        unlock(player)
    }

    /**
     * Reward.
     *
     * @param player The player to reward.
     */
    fun reward(player: Player) {
        // Check if the player has the mime mask
        val hasMask = hasAnItem(player, Items.MIME_MASK_3057).container != null
        // Check if the player has the mime top
        val hasTop = hasAnItem(player, Items.MIME_TOP_3058).container != null
        // Check if the player has the mime legs
        val hasLegs = hasAnItem(player, Items.MIME_LEGS_3059).container != null
        // Check if the player has the mime boots
        val hasBoots = hasAnItem(player, Items.MIME_BOOTS_3061).container != null
        // Check if the player has the mime gloves
        val hasGloves = hasAnItem(player, Items.MIME_GLOVES_3060).container != null
        // Check which items the player is missing
        when {
            // If the player doesn't have the mime mask
            (!hasMask) -> {
                // Send a dialogue message to the player
                sendDialogue(player, "You can now use Lean on air emote!")
                // Add the mime mask to the player's inventory
                addItemOrDrop(player, Items.MIME_MASK_3057, 1)
                // Unlock the Lean on air emote for the player
                unlockEmote(player, 28)
            }
            // If the player doesn't have the mime top
            (!hasTop) -> {
                // Send a dialogue message to the player
                sendDialogue(player, "You can now use Climb Rope emote!")
                // Add the mime top to the player's inventory
                addItemOrDrop(player, Items.MIME_TOP_3058, 1)
                // Unlock the Climb Rope emote for the player
                unlockEmote(player, 27)
            }
            // If the player doesn't have the mime legs
            (!hasLegs) -> {
                // Send a dialogue message to the player
                sendDialogue(player, "You can now use Glass Wall emote!")
                // Add the mime legs to the player's inventory
                addItemOrDrop(player, Items.MIME_LEGS_3059, 1)
                // Unlock the Glass Wall emote for the player
                unlockEmote(player, 29)
            }
            // If the player doesn't have the mime boots and gloves
            (!hasBoots) && (!hasGloves) -> {
                // Send a dialogue message to the player
                sendDialogue(player, "You can now use Glass Box emote!")
                // Add the mime gloves to the player's inventory
                addItemOrDrop(player, Items.MIME_GLOVES_3060, 1)
                // Add the mime boots to the player's inventory
                addItemOrDrop(player, Items.MIME_BOOTS_3061, 1)
                // Unlock the Glass Box emote for the player
                unlockEmote(player, 26)
            }
            // If the player has all the required items
            else -> addItemOrDrop(player, Items.COINS_995, 500)
        }
    }

    /**
     * Get emote.
     *
     * @param player The player to get the emote for.
     */
    fun getEmote(player: Player) {
        // Find the mime NPC
        val npc = findNPC(NPCs.MIME_1056)
        // Get the emote ID from the player's attribute
        var emoteId = getAttribute(player, EMOTE_ATTRIBUTE, -1)
        // Send a dialogue message to the player
        sendUnclosableDialogue(player, true, "", "Watch the Mime.", "See what emote he performs.")
        // Submit a pulse for the mime NPC
        submitIndividualPulse(npc!!, object : Pulse() {
            var counter = 0
            override fun pulse(): Boolean {
                when (counter++) {
                    0 -> npc.faceLocation(location(2011, 4759, 0))
                    1 -> replaceScenery(Scenery(LIGH_ON, PLAYER_LOCATION), LIGHT_OFF, -1)
                    3 -> {
                        when (emoteId) {
                            2 -> emote(npc, Emotes.THINK)
                            3 -> emote(npc, Emotes.CRY)
                            4 -> emote(npc, Emotes.LAUGH)
                            5 -> emote(npc, Emotes.DANCE)
                            6 -> emote(npc, Emotes.CLIMB_ROPE)
                            7 -> emote(npc, Emotes.LEAN_ON_AIR)
                            8 -> emote(npc, Emotes.GLASS_BOX)
                            9 -> emote(npc, Emotes.GLASS_WALL)
                        }
                        setAttribute(player, COPY_ATTRIBUTE, emoteId)
                    }
                    10 -> npc.faceLocation(location(2008, 4762, 0))
                    11 -> emote(npc, Emotes.BOW)
                    14 -> replaceScenery(Scenery(LIGH_ON, SCENERY_LOCATION), LIGHT_OFF, -1)
                    15 -> {
                        replaceScenery(Scenery(LIGHT_OFF, PLAYER_LOCATION), LIGH_ON, -1)
                        openInterface(player, Components.MACRO_MIME_EMOTES_188)
                        return true
                    }
                }
                return false
            }
        })
    }

    /**
     * Get continue.
     *
     * @param player The player to continue.
     */
    fun getContinue(player: Player) {
        // Submit a pulse for the player
        submitIndividualPulse(player, object : Pulse() {
            var counter = 0
            override fun pulse(): Boolean {
                when (counter++) {
                    4 -> {
                        // If the player answered correctly
                        if (getAttribute(player, CORRECT_ATTRIBUTE, -1) == 2) {
                            // Clean up the player
                            cleanup(player)
                            // Open the default chat interface
                            openInterface(player, Components.CHATDEFAULT_137)
                            // Submit a world pulse to reward the player
                            submitWorldPulse(object : Pulse(2) {
                                override fun pulse(): Boolean {
                                    reward(player)
                                    return true
                                }
                            })
                            return false
                        }
                        // If the player answered incorrectly
                        else if (getAttribute(player, FAIL_ATTRIBUTE, -1) == 1) {
                            // Get a random emote ID
                            setAttribute(player, EMOTE_ATTRIBUTE, RandomFunction.random(2, 9))
                            // Remove the fail attribute
                            removeAttribute(player, FAIL_ATTRIBUTE)
                            // Open the default chat interface
                            openInterface(player, Components.CHATDEFAULT_137)
                            // Replace the scenery
                            replaceScenery(Scenery(LIGH_ON, PLAYER_LOCATION), LIGHT_OFF, -1)
                            replaceScenery(Scenery(LIGHT_OFF, SCENERY_LOCATION), LIGH_ON, -1)
                            // Send a dialogue message to the player
                            sendUnclosableDialogue(player, true, "", "Watch the Mime.", "See what emote he performs.")
                            // Get the emote for the player
                            getEmote(player)
                            return true
                        }
                    }
                }
                return false
            }
        })
    }
}
