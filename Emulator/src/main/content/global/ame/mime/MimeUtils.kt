package content.global.ame.mime

import core.api.*
import core.game.interaction.QueueStrength
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

    val MIME_EVENT_LOCATION = Location(2008, 4764, 0)
    val MIME_LOCATION = Location(2008, 4762, 0)
    val PLAYER_LOCATION = Location(2007, 4761, 0)
    val SCENERY_LOCATION = Location(2010, 4761, 0)

    const val TELEPORT_ATTRIBUTE = "/save:original-loc"
    const val LOGOUT_ATTRIBUTE = "mime:save-location"
    const val EMOTE_ATTRIBUTE = "mime:emote"
    const val COPY_ATTRIBUTE = "mime:emote-copy"
    const val CORRECT_ATTRIBUTE = "mime:emote-correct"
    const val FAIL_ATTRIBUTE = "mime:emote-wrong"

    // Define item constants
    const val LIGH_ON = 3644
    const val LIGHT_OFF = 3645

    fun cleanup(player: Player) {
        player.properties.teleportLocation = getAttribute(player, TELEPORT_ATTRIBUTE, null)
        clearLogoutListener(player, LOGOUT_ATTRIBUTE)
        removeAttributes(player, LOGOUT_ATTRIBUTE, TELEPORT_ATTRIBUTE, EMOTE_ATTRIBUTE, COPY_ATTRIBUTE, CORRECT_ATTRIBUTE, FAIL_ATTRIBUTE)
        restoreTabs(player)
        closeInterface(player)
        unlock(player)
    }

    /**
     * Reward.
     *
     * @param [player] the player to reward.
     */
    fun reward(player: Player) {
        val hasMask = hasAnItem(player, Items.MIME_MASK_3057).container != null
        val hasTop = hasAnItem(player, Items.MIME_TOP_3058).container != null
        val hasLegs = hasAnItem(player, Items.MIME_LEGS_3059).container != null
        val hasBoots = hasAnItem(player, Items.MIME_BOOTS_3061).container != null
        val hasGloves = hasAnItem(player, Items.MIME_GLOVES_3060).container != null
        when {
            (!hasMask) -> {
                sendDialogue(player, "You can now use Lean on air emote!")
                addItemOrDrop(player, Items.MIME_MASK_3057, 1)
                unlockEmote(player, 28)
            }
            (!hasTop) -> {
                sendDialogue(player, "You can now use Climb Rope emote!")
                addItemOrDrop(player, Items.MIME_TOP_3058, 1)
                unlockEmote(player, 27)
            }
            (!hasLegs) -> {
                sendDialogue(player, "You can now use Glass Wall emote!")
                addItemOrDrop(player, Items.MIME_LEGS_3059, 1)
                unlockEmote(player, 29)
            }
            (!hasBoots) && (!hasGloves) -> {
                sendDialogue(player, "You can now use Glass Box emote!")
                addItemOrDrop(player, Items.MIME_GLOVES_3060, 1)
                addItemOrDrop(player, Items.MIME_BOOTS_3061, 1)
                unlockEmote(player, 26)
            }
            else -> addItemOrDrop(player, Items.COINS_995, 500)
        }
    }

    /**
     * Get emote.
     *
     * @param [player] the player to get the emote for.
     */
    fun getEmote(player: Player) {
        val npc = findNPC(NPCs.MIME_1056)
        var emoteId = getAttribute(player, EMOTE_ATTRIBUTE, -1)
        sendUnclosableDialogue(player, true, "", "Watch the Mime.", "See what emote he performs.")
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
     * @param [player] the player to continue.
     */
    fun getContinue(player: Player) {
        submitIndividualPulse(player, object : Pulse() {
            var counter = 0
            override fun pulse(): Boolean {
                when (counter++) {
                    4 -> {
                        if (getAttribute(player, CORRECT_ATTRIBUTE, -1) == 2) {
                            cleanup(player)
                            openInterface(player, Components.CHATDEFAULT_137)
                            queueScript(player, 2, QueueStrength.STRONG) {
                                reward(player)
                                return@queueScript stopExecuting(player)
                            }
                        } else {
                            if (getAttribute(player, FAIL_ATTRIBUTE, -1) == 1) {
                                setAttribute(player, EMOTE_ATTRIBUTE, RandomFunction.random(2, 9))
                                removeAttribute(player, FAIL_ATTRIBUTE)
                                openInterface(player, Components.CHATDEFAULT_137)
                                replaceScenery(Scenery(LIGH_ON, PLAYER_LOCATION), LIGHT_OFF, -1)
                                replaceScenery(Scenery(LIGHT_OFF, SCENERY_LOCATION), LIGH_ON, -1)
                                sendUnclosableDialogue(player, true, "", "Watch the Mime.", "See what emote he performs.")
                                getEmote(player)
                            }
                        }
                    }
                }
                return false
            }
        })
    }
}
