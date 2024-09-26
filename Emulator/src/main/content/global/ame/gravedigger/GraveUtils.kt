package content.global.ame.gravedigger

import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.emote.Emotes
import core.game.world.update.flag.context.Animation
import org.rs.consts.Items
import org.rs.consts.NPCs
import org.rs.consts.Scenery

/**
 * Grave utilities for managing gravedigger events.
 */
object GraveUtils {
    const val MAUSOLEUM = Scenery.MAUSOLEUM_12731
    const val LEO = NPCs.LEO_3508

    // Attributes
    const val LEO_TASK = "gravedigger:task"
    const val LEO_COFFIN_POINTS = "gravedigger:points"
    const val LEO_LOCATION = "/save:original-loc"
    const val LEO_LOGOUT = "gravedigger:logout"

    // Animations
    val PICK_AND_DROP_ANIM = Animation(827) // The pick and drop animation.

    val items = intArrayOf(7597, 7598, 7599, 7600, 7601, 7602, 7603, 7604, 7605, 7606, 7607, 7608, 7609, 7610, 7611, 7612, 7613, 7614, 7615, 7616, 7617, 7618, 7619)

    // Scenery
    val gravestones = arrayOf(
        Scenery.GRAVESTONE_12716,
        Scenery.GRAVESTONE_12717,
        Scenery.GRAVESTONE_12718,
        Scenery.GRAVESTONE_12719,
        Scenery.GRAVESTONE_12720
    )

    // Items
    val coffins = arrayOf(Items.COFFIN_7587, Items.COFFIN_7588, Items.COFFIN_7589, Items.COFFIN_7590, Items.COFFIN_7591)

    // Interfaces
    const val COFFIN_INTERFACE = 141
    const val GRAVESTONE_INTERFACE = 143

    // Components
    val COMPONENTS = (3..11).toList().toIntArray()

    // Coffin content
    val coffinContent = listOf(
        intArrayOf(13383, 13383, 13404, 13383, 13383, 13388, 13383, 13390, 13397),
        intArrayOf(13390, 13383, 13383, 13385, 13386, 13390, 13397, 13383, 13390),
        intArrayOf(13383, 13392, 13383, 13390, 13383, 13391, 13397, 13383, 13383),
        intArrayOf(13395, 13396, 13383, 13397, 13383, 13383, 13383, 13383, 13387),
        intArrayOf(13383, 13383, 13394, 13384, 13383, 13388, 13397, 13393, 13383)
    )

    // All coffins and gravestone scenery
    val itemsCoffin = coffins.toIntArray()
    val graveStones = gravestones.toIntArray()
    val gravePlates = intArrayOf(13398, 13400, 13401, 13402, 13403)

    /**
     * Get random coffin content for the player.
     */
    fun getRandomCoffinContent(player: Player) {
        if (player.interfaceManager.opened.id == COFFIN_INTERFACE) {
            coffinContent.random().forEachIndexed { index, item ->
                player.packetDispatch.sendModelOnInterface(item, COFFIN_INTERFACE, COMPONENTS[index], 25)
            }
        }
    }

    /**
     * Get random grave content for the player.
     */
    fun getRandomGraveContent(player: Player) {
        if (player.interfaceManager.opened.id == GRAVESTONE_INTERFACE) {
            val plate = gravePlates.random()
            gravePlates.forEach { _ ->
                player.packetDispatch.sendModelOnInterface(plate, GRAVESTONE_INTERFACE, 2, 240)
            }
        }
    }

    /**
     * Cleanup player attributes and state.
     */
    fun cleanup(player: Player) {
        player.properties.teleportLocation = getAttribute(player, LEO_LOCATION, null)
        setMinimapState(player, 0)
        clearLogoutListener(player, LEO_LOGOUT)
        removeAttributes(player, LEO_LOCATION, LEO_COFFIN_POINTS, LEO_TASK)
        removeAll(player, itemsCoffin)
    }

    /**
     * Reward the player based on their inventory.
     */
    fun reward(player: Player) {
        val itemsToCheck = listOf(
            Items.ZOMBIE_MASK_7594,
            Items.ZOMBIE_SHIRT_7592,
            Items.ZOMBIE_TROUSERS_7593,
            Items.ZOMBIE_GLOVES_7595,
            Items.ZOMBIE_BOOTS_7596
        )

        for (item in itemsToCheck) {
            if (hasAnItem(player, item).container != null) {
                addItemOrDrop(player, item, 1)
                return
            }
        }

        addItemOrDrop(player, Items.COINS_995, 500)
        player.emoteManager.unlock(Emotes.ZOMBIE_DANCE)
        player.emoteManager.unlock(Emotes.ZOMBIE_WALK)
    }
}
