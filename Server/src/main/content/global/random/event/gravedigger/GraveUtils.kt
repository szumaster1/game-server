package content.global.random.event.gravedigger

import core.api.*
import core.api.consts.Items
import core.api.consts.Scenery
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.emote.Emotes
import core.game.world.update.flag.context.Animation
import core.network.packet.PacketRepository
import core.network.packet.context.MinimapStateContext
import core.network.packet.outgoing.MinimapState


object GraveUtils {

    // attributes
    const val LEO_TASK = "gravedigger:task"
    const val LEO_COFFIN_POINTS = "gravedigger:points"
    const val LEO_LOCATION = "/save:original-loc"
    const val LEO_LOGOUT = "gravedigger:logout"

    val items = intArrayOf(7597, 7598, 7599, 7600, 7601, 7602, 7603, 7604, 7605, 7606, 7607, 7608, 7609, 7610, 7611, 7612, 7613, 7614, 7615, 7616, 7617, 7618, 7619)

    // bury animation
    val PICK_AND_DROP_ANIM = Animation(827)

    // gravestones
    private const val GRAVESTONE_1 = Scenery.GRAVESTONE_12716
    private const val GRAVESTONE_2 = Scenery.GRAVESTONE_12717
    private const val GRAVESTONE_3 = Scenery.GRAVESTONE_12718
    private const val GRAVESTONE_4 = Scenery.GRAVESTONE_12719
    private const val GRAVESTONE_5 = Scenery.GRAVESTONE_12720

    // coffin as item
    const val COFFIN_1 = Items.COFFIN_7587
    const val COFFIN_2 = Items.COFFIN_7588
    const val COFFIN_3 = Items.COFFIN_7589
    const val COFFIN_4 = Items.COFFIN_7590
    const val COFFIN_5 = Items.COFFIN_7591

    // interfaces id
    const val COFFIN_INTERFACE = 141
    const val GRAVESTONE_INTERFACE = 143

    // components for coffin items
    private val COMPONENTS = intArrayOf(3, 4, 5, 6, 7, 8, 9, 10, 11)

    // coffin content
    private val coffinContent = arrayListOf(

        // Woodcutting
        intArrayOf(
            13383, 13383, 13404,
            13383, 13383, 13388,
            13383, 13390, 13397
        ),

        // Cooking
        intArrayOf(
            13390, 13383, 13383,
            13385, 13386, 13390,
            13397, 13383, 13390
        ),

        // Mining
        intArrayOf(
            13383, 13392, 13383,
            13390, 13383, 13391,
            13397, 13383, 13383
        ),

        // Farming
        intArrayOf(
            13395, 13396, 13383,
            13397, 13383, 13383,
            13383, 13383, 13387
        ),

        // Crafting
        intArrayOf(
            13383, 13383, 13394,
            13384, 13383, 13388,
            13397, 13393, 13383
        ),
    )

    // all coffins
    val itemsCoffin = intArrayOf(COFFIN_1, COFFIN_2, COFFIN_3, COFFIN_4, COFFIN_5)

    // all gravestones
    val graveStones = intArrayOf(GRAVESTONE_1, GRAVESTONE_2, GRAVESTONE_3, GRAVESTONE_4, GRAVESTONE_5)

    // all plates
    private val gravePlates = intArrayOf(13398, 13400, 13401, 13402, 13403)

    fun getRandomCoffinContent(player: Player) {
        val coffin = coffinContent.random()
        if (player.interfaceManager.opened.id == COFFIN_INTERFACE) {
            for (i in COMPONENTS.indices) {
                player.packetDispatch.sendModelOnInterface(coffin[i], COFFIN_INTERFACE, COMPONENTS[i], 25)
            }
        }
    }

    fun getRandomGraveContent(player: Player) {
        val plate = gravePlates.random()
        if (player.interfaceManager.opened.id == GRAVESTONE_INTERFACE) {
            for (i in gravePlates.indices) {
                player.packetDispatch.sendModelOnInterface(plate, GRAVESTONE_INTERFACE, 2, 240)
            }
        }
    }

    fun cleanup(player: Player) {
        player.properties.teleportLocation = getAttribute(player, LEO_LOCATION, null)
        PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 0))
        clearLogoutListener(player, LEO_LOGOUT)
        removeAttributes(player, LEO_LOCATION, LEO_COFFIN_POINTS, LEO_TASK)
        removeAll(player, itemsCoffin)
    }

    fun reward(player: Player) {
        val hasHat = hasAnItem(player, Items.ZOMBIE_MASK_7594).container != null
        val hasTop = hasAnItem(player, Items.ZOMBIE_SHIRT_7592).container != null
        val hasShort = hasAnItem(player, Items.ZOMBIE_TROUSERS_7593).container != null
        val hasGloves = hasAnItem(player, Items.ZOMBIE_GLOVES_7595).container != null
        val hasBoots = hasAnItem(player, Items.ZOMBIE_BOOTS_7596).container != null
        when {
            (!hasHat) -> addItemOrDrop(player, Items.ZOMBIE_MASK_7594, 1)
            (!hasTop) -> addItemOrDrop(player, Items.ZOMBIE_SHIRT_7592, 1)
            (!hasShort) -> addItemOrDrop(player, Items.ZOMBIE_TROUSERS_7593, 1)
            (!hasGloves) -> addItemOrDrop(player, Items.ZOMBIE_GLOVES_7595, 1)
            (!hasBoots) -> addItemOrDrop(player, Items.ZOMBIE_BOOTS_7596, 1)
            else -> {
                addItemOrDrop(player, Items.COINS_995, 500)
                player.emoteManager.unlock(Emotes.ZOMBIE_DANCE)
                player.emoteManager.unlock(Emotes.ZOMBIE_WALK)
            }
        }
    }
}
