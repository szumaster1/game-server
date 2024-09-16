package content.global.travel

import cfg.consts.Components
import cfg.consts.Items
import cfg.consts.Scenery
import core.api.*
import core.game.component.Component
import core.game.event.FairyRingDialEvent
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.TeleportManager.TeleportType
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.map.RegionManager
import core.tools.RandomFunction

/**
 * Represents the fairy ring transportation method.
 */
class FairyRings : InterfaceListener, InteractionListener {
    val RING_1 = arrayOf('a', 'd', 'c', 'b')
    val RING_2 = arrayOf('i', 'l', 'k', 'j')
    val RING_3 = arrayOf('p', 's', 'r', 'q')

    private val RINGS = intArrayOf(12003, 12095, 14058, 14061, 14064, 14067, 14070, 14073, 14076, 14079, 14082, 14085, 14088, 14091, 14094, 14097, 14100, 14103, 14106, 14109, 14112, 14115, 14118, 14121, 14124, 14127, 14130, 14133, 14136, 14139, 14142, 14145, 14148, 14151, 14154, 14157, 14160, 16181, 16184, 23047, 27325, 3772)
    private val ZANARIS_MARKET_RING = getScenery(2486, 4471, 0)!!
    private val MAIN_RING = Scenery.FAIRY_RING_12128
    private val ENTRY_RING = Scenery.FAIRY_RING_12094

    /*
     * Fairy ring interactions.
     */

    fun fairyMagic(player: Player): Boolean {
        if (!hasRequirement(player, "Fairytale I - Growing Pains")) {
            sendMessage(player, "The fairy ring is inert.")
            return false
        }
        if (!anyInEquipment(player, Items.DRAMEN_STAFF_772, Items.LUNAR_STAFF_9084)) {
            sendMessage(player, "The fairy ring only works for those who wield fairy magic.")
            return false
        }
        return true
    }

    fun reset(player: Player) {
        removeAttribute(player, "fairy-delay")
        removeAttribute(player, "fairy_location_combo")
        for (i in 0..2) {
            setVarp(player, 816 + i, 0)
        }
    }

    fun openFairyRing(player: Player) {
        reset(player)
        player.interfaceManager.openSingleTab(Component(735))
        player.interfaceManager.open(Component(734))
    }

    override fun defineListeners() {

        /*
         * Handling the fairy ring use.
         */

        on(RINGS, IntType.SCENERY, "use") { player, ring ->
            if (!fairyMagic(player)) return@on true
            teleport(
                player,
                if (ring == ZANARIS_MARKET_RING) Location.create(3261, 3168, 0) else Location.create(2412, 4434, 0),
                TeleportType.FAIRY_RING
            )
            return@on true
        }

        /*
         * Handling the main ring use.
         */

        on(MAIN_RING, IntType.SCENERY, "use") { player, _ ->
            if (!fairyMagic(player)) return@on true
            openFairyRing(player)
            return@on true
        }

        /*
         * Handling the entry ring use.
         */

        on(ENTRY_RING, IntType.SCENERY, "use") { player, _ ->
            if (!fairyMagic(player)) return@on true
            teleport(player, Location.create(3203, 3168, 0), TeleportType.FAIRY_RING)
            return@on true
        }
    }

    override fun defineInterfaceListeners() {

        onOpen(Components.FAIRY_RINGS_734) { player, _ ->
            player.interfaceManager.openSingleTab(Component(Components.FAIRY_TRAVEL_LOG_735))
            setAttribute(player, "fr:ring1", 0)
            setAttribute(player, "fr:ring2", 0)
            setAttribute(player, "fr:ring3", 0)
            FairyRing.drawLog(player)
            return@onOpen true
        }

        onClose(Components.FAIRY_RINGS_734) { player, _ ->
            closeTabInterface(player)
            removeAttribute(player, "fr:ring1")
            removeAttribute(player, "fr:ring2")
            removeAttribute(player, "fr:ring3")
            setVarp(player, 816, 0)
            closeTabInterface(player)
            return@onClose true
        }

        on(Components.FAIRY_RINGS_734) { player, _, _, buttonID, _, _ ->
            if (player.getAttribute("fr:time", 0L) > System.currentTimeMillis()) return@on true
            var delayIncrementer = 1750L
            when (buttonID) {
                23 -> delayIncrementer += increment(player, 1)
                25 -> delayIncrementer += increment(player, 2)
                27 -> delayIncrementer += increment(player, 3)
                24 -> decrement(player, 1)
                26 -> decrement(player, 2)
                28 -> decrement(player, 3)
                21 -> confirm(player)
            }
            setAttribute(player, "fr:time", System.currentTimeMillis() + delayIncrementer)
            return@on true
        }

        on(Components.FAIRY_TRAVEL_LOG_735, 12) { player, _, _, _, _, _ ->
            toggleSortOrder(player)
            return@on true
        }

    }

    private fun toggleSortOrder(player: Player): Long {
        val ring1index = player.getAttribute("fr:ring1", 0)
        var toSet = player.getAttribute("fr:sortorder", true)
        toSet = !toSet
        setAttribute(player, "fr:sortorder", toSet)
        if (toSet) {
            setVarp(player, 816, ring1index)
            setAttribute(player, "fr:ring2", 0)
            setAttribute(player, "fr:ring3", 0)
        }
        return -1750L
    }

    /**
     * Increment.
     *
     * @param player the player.
     * @param ring the ring.
     * @return
     */
    fun increment(player: Player, ring: Int): Long {
        val curIndex = player.getAttribute("fr:ring$ring", 0)
        var nextIndex = 0
        if (curIndex == 3) nextIndex = 0
        else if (curIndex == 1) nextIndex = 3
        else if (curIndex == 2) nextIndex = 2
        else nextIndex = curIndex + 1
        setAttribute(player, "fr:ring$ring", nextIndex)
        return if (curIndex == 1) 1750L else 0L
    }

    private fun decrement(player: Player, ring: Int) {
        val curIndex = player.getAttribute("fr:ring$ring", 0)
        var nextIndex = 0
        if (curIndex == 0) nextIndex = 3
        else nextIndex = curIndex - 1
        setAttribute(player, "fr:ring$ring", nextIndex)
    }

    private fun confirm(player: Player) {
        val ring1index = player.getAttribute("fr:ring1", 0)
        val ring2index = player.getAttribute("fr:ring2", 0)
        val ring3index = player.getAttribute("fr:ring3", 0)
        val code = "${RING_1[ring1index]}${RING_2[ring2index]}${RING_3[ring3index]}"
        val fairyRing: FairyRing? = try {
            FairyRing.valueOf(code.uppercase())
        } catch (e: Exception) {
            null
        }

        var tile = fairyRing?.tile
        if (fairyRing?.checkAccess(player) != true) {
            sendDialogue(player, "The ring seems to reject you.")
            tile = null
        }
        if (fairyRing == null || tile == null) {
            val center = Location(2412, 4434, 0)
            tile = if (RandomFunction.random(2) == 1) {
                center.transform(RandomFunction.random(2, 6), RandomFunction.random(2, 6), 0)
            } else {
                center.transform(RandomFunction.random(-2, -6), RandomFunction.random(-2, -6), 0)
            }
            if (!RegionManager.isTeleportPermitted(tile) || RegionManager.getObject(tile) != null) {
                tile = Location.create(2412, 4431, 0)
            }
            GameWorld.Pulser.submit(object : Pulse(4, player) {
                override fun pulse(): Boolean {
                    sendPlayerDialogue(
                        player,
                        "Wow, fairy magic sure is useful, I hardly moved at all!",
                        core.game.dialogue.FacialExpression.AMAZED
                    )
                    return true
                }
            })
        } else {
            if (!player.savedData.globalData.hasTravelLog(fairyRing.ordinal)) {
                player.savedData.globalData.setTravelLog(fairyRing.ordinal)
            }
        }
        closeInterface(player)

        fairyRing.let {
            if (it == null) {
                return@let
            }

            player.dispatch(FairyRingDialEvent(it))
            teleport(player, tile!!, TeleportType.FAIRY_RING)
        }
    }
}

/**
 * Fairy ring.
 *
 * @param tile the title.
 * @param tip the tip.
 * @param childId the childid.
 */
enum class FairyRing(val tile: Location?, val tip: String = "", val childId: Int = -1) {
    /**
     * Aiq.
     */
    AIQ(Location.create(2996, 3114, 0), "Asgarnia: Mudskipper Point", 15),

    /**
     * Air.
     */
    AIR(Location.create(2700, 3247, 0), "Islands: South of Witchaven", 16),

    /**
     * Ajq.
     */
    AJQ(Location.create(2735, 5221, 0), "Dungeons: Dark cave south of Dorgesh-Kaann", 19),

    /**
     * Alr.
     */
    ALR(Location.create(3059, 4875, 0), "Other realms: Abyssal Area", 28),

    /**
     * Ajr.
     */
    AJR(Location.create(2780, 3613, 0), "Kandarin: Slayer cave south-east of Rellekka", 20),

    /**
     * Ajs.
     */
    AJS(Location.create(2500, 3896, 0), "Islands: Penguins near Miscellania", 21),

    /**
     * Akq.
     */
    AKQ(Location.create(2319, 3619, 0), "Kandarin: Piscatoris Hunter area", 23),

    /**
     * Aks.
     */
    AKS(Location.create(2571, 2956, 0), "Feldip Hills: Jungle Hunter area", 25),

    /**
     * Alq
     *.
     */
    ALQ(Location.create(3597, 3495, 0), "Morytania: Haunted Woods east of Canifis", 27) {
        override fun checkAccess(player: Player): Boolean {
            return requireQuest(player, "Priest in Peril", "to use this ring.")
        }
    },

    /**
     * Als.
     */
    ALS(Location.create(2644, 3495, 0), "Kandarin: McGrubor's Wood", 29),

    /**
     * Bip.
     */
    BIP(Location.create(3410, 3324, 0), "Islands: River Salve", 30),

    /**
     * Biq.
     */
    BIQ(Location.create(3251, 3095, 0), "Kharidian Desert: Near Kalphite hive", 31),

    /**
     * Bis.
     */
    BIS(Location.create(2635, 3266, 0), "Kandarin: Ardougne Zoo unicorns", 33),

    /**
     * Bjr.
     */
    BJR(null, "Other Realms: Realm of the Fisher King", 36),

    /**
     * Bkp.
     */
    BKP(Location.create(2385, 3035, 0), "Feldip Hills: South of Castle Wars", 38),

    /**
     * Bkq.
     */
    BKQ(Location.create(3041, 4532, 0), "Other realms: Enchanted Valley", 39),

    /**
     * Bkr.
     */
    BKR(Location.create(3469, 3431, 0), "Morytania: Mort Myre, south of Canifis", 40) {
        override fun checkAccess(player: Player): Boolean {
            return requireQuest(player, "Priest in Peril", "to use this ring.")
        }
    },

    /**
     * Blp.
     */
    BLP(Location.create(2437, 5126, 0), "Dungeons: TzHaar area", 42),

    /**
     * Blq.
     */
    BLQ(null, "Yu'biusk", 43),//Location.create(2229, 4244, 1)

    /**
     * Blr.
     */
    BLR(Location.create(2740, 3351, 0), "Kandarin: Legends' Guild", 44),

    /**
     * Cip.
     */
    CIP(Location.create(2513, 3884, 0), "Islands: Miscellania", 46) {
        override fun checkAccess(player: Player): Boolean {
            return requireQuest(player, "Fremennik Trials", "to use this ring.")
        }
    },

    /**
     * Ciq.
     */
    CIQ(Location.create(2528, 3127, 0), "Kandarin: North-west of Yanille", 47),

    /**
     * Cjr.
     */
    CJR(Location.create(2705, 3576, 0), "Kandarin: Sinclair Mansion", 52),

    /**
     * Ckp.
     */
    CKP(Location.create(2075, 4848, 0), "Other realms: Cosmic Entity's plane", 54),

    /**
     * Ckr.
     */
    CKR(Location.create(2801, 3003, 0), "Karamja: South of Tai Bwo Wannai Village", 56),

    /**
     * Cks.
     */
    CKS(Location.create(3447, 3470, 0), "Morytania: Canifis", 57) {
        override fun checkAccess(player: Player): Boolean {
            return requireQuest(player, "Priest in Peril", "to use this ring.")
        }
    },

    /**
     * Clp.
     */
    CLP(Location.create(3082, 3206, 0), "Islands: South of Draynor Village", 58),

    /**
     * Cls.
     */
    CLS(Location.create(2682, 3081, 0), "Islands: Jungle spiders near Yanille", 61),

    /**
     * Dir.
     */
    DIR(Location.create(3038, 5348, 0), "Other realms: Goraks' Plane", 64),

    /**
     * Dis.
     */
    DIS(Location.create(3108, 3149, 0), "Misthalin: Wizards' Tower", 65),

    /**
     * Djp.
     */
    DJP(Location.create(2658, 3230, 0), "Kandarin: Tower of Life", 66),

    /**
     * Djr.
     */
    DJR(Location.create(2676, 3587, 0), "Kandarin: Sinclair Mansion (west)", 68),

    /**
     * Dkp.
     */
    DKP(Location.create(2900, 3111, 0), "Karamja: South of Musa Point", 70),

    /**
     * Dkr.
     */
    DKR(Location.create(3129, 3496, 0), "Misthalin: Edgeville", 72),

    /**
     * Dks.
     */
    DKS(Location.create(2744, 3719, 0), "Kandarin: Snowy Hunter area", 73),

    /**
     * Dlq.
     */
    DLQ(Location.create(3423, 3016, 0), "Kharidian Desert: North of Nardah", 75),

    /**
     * Dlr.
     */
    DLR(Location.create(2213, 3099, 0), "Islands: Poison Waste south of Isafdar", 76),

    /**
     * Ais.
     */
    AIS(null),

    /**
     * Aip.
     */
    AIP(null),

    /**
     * Akp.
     */
    AKP(null),

    /**
     * Fairy Home.
     */
    FAIRY_HOME(Location.create(2412, 4434, 0));

    /**
     * Check access
     *
     * @param player the player.
     * @return access.
     */
    open fun checkAccess(player: Player): Boolean {
        return true
    }

    companion object {

        fun drawLog(player: Player) {
            for (i in FairyRing.values().indices) {
                if (!player.savedData.globalData.hasTravelLog(i)) {
                    continue
                }
                val fairyRing = FairyRing.values()[i]
                if (fairyRing.childId == -1) {
                    continue
                }
                sendString(player, "<br>${fairyRing.tip}", 735, fairyRing.childId)
            }
        }
    }
}
