package content.global.handlers.item

import core.api.*
import core.api.consts.Items
import core.cache.def.impl.ItemDefinition
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.link.TeleportManager
import core.game.node.item.Item
import core.game.world.map.Location

/**
 * Enum class representing Teleport Tabs.
 *
 * @param item The item ID of the teleport tab.
 * @param location The destination location of the teleport tab.
 * @param exp The experience gained when using the teleport tab.
 * @constructor Creates a Teleport Tab with the specified item ID, location, and experience.
 */
enum class TeleTabs(val item: Int, val location: Location, val exp: Double) {
    /**
     * Addougne Teleport.
     */
    ADDOUGNE_TELEPORT(
        item = Items.ARDOUGNE_TELEPORT_8011,
        location = Location.create(2662, 3307, 0),
        exp = 61.0
    ),

    /**
     * Air Altar Teleport.
     */
    AIR_ALTAR_TELEPORT(
        item = Items.AIR_ALTAR_TELEPORT_13599,
        location = Location.create(2978, 3296, 0),
        exp = 0.0
    ),

    /**
     * Astral Altar Teleport.
     */
    ASTRAL_ALTAR_TELEPORT(
        item = Items.ASTRAL_ALTAR_TELEPORT_13611,
        location = Location.create(2156, 3862, 0),
        exp = 0.0
    ),

    /**
     * Blood Altar Teleport.
     */
    BLOOD_ALTAR_TELEPORT(
        item = Items.BLOOD_ALTAR_TELEPORT_13610,
        location = Location.create(3559, 9778, 0),
        exp = 0.0
    ),

    /**
     * Body Altar Teleport.
     */
    BODY_ALTAR_TELEPORT(
        item = Items.BODY_ALTAR_TELEPORT_13604,
        location = Location.create(3055, 3443, 0),
        exp = 0.0
    ),

    /**
     * Camelot Teleport.
     */
    CAMELOT_TELEPORT(
        item = Items.CAMELOT_TELEPORT_8010,
        location = Location.create(2757, 3477, 0),
        exp = 55.5
    ),

    /**
     * Chaos Altar Teleport.
     */
    CHAOS_ALTAR_TELEPORT(
        item = Items.CHAOS_ALTAR_TELEPORT_13606,
        location = Location.create(3058, 3593, 0),
        exp = 0.0
    ),

    /**
     * Cosmic Altar Teleport.
     */
    COSMIC_ALTAR_TELEPORT(
        item = Items.COSMIC_ALTAR_TELEPORT_13605,
        location = Location.create(2411, 4380, 0),
        exp = 0.0
    ),

    /**
     * Death Altar Teleport.
     */
    DEATH_ALTAR_TELEPORT(
        item = Items.DEATH_ALTAR_TELEPORT_13609,
        location = Location.create(1863, 4639, 0),
        exp = 0.0
    ),

    /**
     * Earth Altar Teleport.
     */
    EARTH_ALTAR_TELEPORT(
        item = Items.EARTH_ALTAR_TELEPORT_13602,
        location = Location.create(3304, 3472, 0),
        exp = 0.0
    ),

    /**
     * Falador Teleport.
     */
    FALADOR_TELEPORT(
        item = Items.FALADOR_TELEPORT_8009,
        location = Location.create(2966, 3380, 0),
        exp = 47.0
    ),

    /**
     * Fire Altar Teleport.
     */
    FIRE_ALTAR_TELEPORT(
        item = Items.FIRE_ALTAR_TELEPORT_13603,
        location = Location.create(3311, 3252, 0),
        exp = 0.0
    ),

    /**
     * Law Altar Teleport.
     */
    LAW_ALTAR_TELEPORT(
        item = Items.LAW_ALTAR_TELEPORT_13608,
        location = Location.create(2857, 3378, 0),
        exp = 0.0
    ),

    /**
     * Lumbridge Teleport.
     */
    LUMBRIDGE_TELEPORT(
        item = Items.LUMBRIDGE_TELEPORT_8008,
        location = Location.create(3222, 3218, 0),
        exp = 41.0
    ),

    /**
     * Mind Altar Teleport.
     */
    MIND_ALTAR_TELEPORT(
        item = Items.MIND_ALTAR_TELEPORT_13600,
        location = Location.create(2979, 3510, 0),
        exp = 0.0
    ),

    /**
     * Nature Altar Teleport.
     */
    NATURE_ALTAR_TELEPORT(
        item = Items.NATURE_ALTAR_TELEPORT_13607,
        location = Location.create(2868, 3013, 0),
        exp = 0.0
    ),

    /**
     * Varrock Teleport.
     */
    VARROCK_TELEPORT(
        item = Items.VARROCK_TELEPORT_8007,
        location = Location.create(3212, 3423, 0),
        exp = 35.00
    ),

    /**
     * Watch Tower Teleport.
     */
    WATCH_TOWER_TELEPORT(
        item = Items.WATCHTOWER_TPORT_8012,
        location = Location.create(2548, 3114, 0),
        exp = 68.00
    ),

    /**
     * Water Altar Teleport.
     */
    WATER_ALTAR_TELEPORT(
        item = Items.WATER_ALTAR_TELEPORT_13601,
        location = Location.create(3182, 3162, 0),
        exp = 0.0
    ),

    /**
     * Runecrafting Guild Teleport.
     */
    RUNECRAFTING_GUILD_TELEPORT(
        item = Items.RUNECRAFTING_GUILD_TELEPORT_13598,
        location = Location.create(1696, 5463, 2),
        exp = 0.0
    );

    companion object {
        val idMap = values().map { it.item to it }.toMap()
        fun forId(id: Int): TeleTabs? {
            return idMap[id]
        }
    }
}

/**
 * Tele tabs listener.
 */
class TeleTabsListener : InteractionListener {

    override fun defineListeners() {
        val tabIDs = TeleTabs.values().map { it.item }.toIntArray()
        on(tabIDs, IntType.ITEM, "break") { player, node ->
            val tab = node.id
            val tabEnum = TeleTabs.forId(tab)
            if (tabEnum != null && inInventory(player, tab)) {
                if (!isQuestComplete(player, "Rune Mysteries")) {
                    sendMessage(player, "You need complete the Rune Mysteries quest in order to use this.")
                    return@on true
                }
                val tabloc = tabEnum.location
                if (inInventory(player, tab)) {
                    if (tab == Items.ARDOUGNE_TELEPORT_8011 && !isQuestComplete(player, "Plague City")) return@on true
                    if (tab == Items.ASTRAL_ALTAR_TELEPORT_13611 && !hasRequirement(player, QuestReq(QuestRequirements.LUNAR_DIPLOMACY)))
                        return@on true
                    if (tab == Items.COSMIC_ALTAR_TELEPORT_13605 && !isQuestComplete(player, "Lost City"))
                        return@on true
                    if (tab == Items.DEATH_ALTAR_TELEPORT_13609 && !hasRequirement(player, QuestReq(QuestRequirements.MEP_2)))
                        return@on true
                    if (tab == Items.BLOOD_ALTAR_TELEPORT_13610 && !hasRequirement(player, QuestReq(QuestRequirements.SEERGAZE)))
                        return@on true
                    if (tab == Items.LAW_ALTAR_TELEPORT_13608 && !ItemDefinition.canEnterEntrana(player)) {
                        sendMessage(player, "You can't take weapons and armour into the law rift.")
                        return@on false
                    }
                    if (teleport(player, tabloc, TeleportManager.TeleportType.TELETABS)) {
                        removeItem(player, Item(node.id, 1))
                    }
                }
            }
            return@on true
        }
    }
}
