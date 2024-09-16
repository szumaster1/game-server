package content.global.skill.production.runecrafting.data

import cfg.consts.Items
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Represents a talisman.
 *
 * @param talisman      the talisman item.
 * @param ruin          the mysterious ruin.
 */
enum class Talisman(val talisman: Item, private val ruin: MysteriousRuin?) {
    /**
     * The air talisman.
     */
    AIR(
        talisman = Item(Items.AIR_TALISMAN_1438),
        ruin = MysteriousRuin.AIR
    ),

    /**
     * The mind talisman.
     */
    MIND(
        talisman = Item(Items.MIND_TALISMAN_1448),
        ruin = MysteriousRuin.MIND
    ),

    /**
     * The water talisman.
     */
    WATER(
        talisman = Item(Items.WATER_TALISMAN_1444),
        ruin = MysteriousRuin.WATER
    ),

    /**
     * The earth talisman.
     */
    EARTH(
        talisman = Item(Items.EARTH_TALISMAN_1440),
        ruin = MysteriousRuin.EARTH
    ),

    /**
     * The fire talisman.
     */
    FIRE(
        talisman = Item(Items.FIRE_TALISMAN_1442),
        ruin = MysteriousRuin.FIRE
    ),

    /**
     * The elemental talisman.
     */
    ELEMENTAL(
        talisman = Item(Items.ELEMENTAL_TALISMAN_5516),
        ruin = null
    ),

    /**
     * The body talisman.
     */
    BODY(
        talisman = Item(Items.BODY_TALISMAN_1446),
        ruin = MysteriousRuin.BODY
    ),

    /**
     * The cosmic talisman.
     */
    COSMIC(
        talisman = Item(Items.COSMIC_TALISMAN_1454),
        ruin = MysteriousRuin.COSMIC
    ),

    /**
     * The chaos talisman.
     */
    CHAOS(
        talisman = Item(Items.CHAOS_TALISMAN_1452),
        ruin = MysteriousRuin.CHAOS
    ),

    /**
     * The nature talisman.
     */
    NATURE(
        talisman = Item(Items.NATURE_TALISMAN_1462),
        ruin = MysteriousRuin.NATURE
    ),

    /**
     * The law talisman.
     */
    LAW(
        talisman = Item(Items.LAW_TALISMAN_1458),
        ruin = MysteriousRuin.LAW
    ),

    /**
     * The death talisman.
     */
    DEATH(
        talisman = Item(Items.DEATH_TALISMAN_1456),
        ruin = MysteriousRuin.DEATH
    ),

    /**
     * The blood talisman.
     */
    BLOOD(
        talisman = Item(Items.BLOOD_TALISMAN_1450),
        ruin = MysteriousRuin.BLOOD
    ),

    /**
     * The soul talisman.
     */
    SOUL(
        talisman = Item(Items.SOUL_TALISMAN_1460),
        ruin = null
    );

    /**
     * Method used to locate a ruin.
     */
    fun locate(player: Player) {
        if (this == ELEMENTAL || getRuin() == null) {
            player.packetDispatch.sendMessage("You cannot tell which direction the Talisman is pulling...")
            return
        }
        var direction = ""
        val loc = getRuin()!!.base
        if (player.location.y > loc.y && player.location.x - 1 > loc.x) direction =
            "south-west" else if (player.location.x < loc.x && player.location.y > loc.y) direction =
            "south-east" else if (player.location.x > loc.x + 1 && player.location.y < loc.y) direction =
            "north-west" else if (player.location.x < loc.x && player.location.y < loc.y) direction =
            "north-east" else if (player.location.y < loc.y) direction =
            "north" else if (player.location.y > loc.y) direction =
            "south" else if (player.location.x < loc.x + 1) direction =
            "east" else if (player.location.x > loc.x + 1) direction = "west"
        player.packetDispatch.sendMessage("The talisman pulls towards the $direction.")
    }

    /**
     * Gets the ruin.
     *
     * @return The ruin.
     */
    fun getRuin(): MysteriousRuin? {
        for (ruin in MysteriousRuin.values()) {
            if (ruin.name == name) {
                return ruin
            }
        }
        return ruin
    }

    /**
     * Gets the tiara.
     *
     * @return the tiara.
     */
    val tiara: Tiara?
        get() {
            for (tiara in Tiara.values()) {
                if (tiara.name == name) {
                    return tiara
                }
            }
            return null
        }

    companion object {
        /**
         * Method used to get the [Talisman] by the item.
         *
         * @param item the item.
         * @return the [Talisman] or `null`.
         */
        @JvmStatic
        fun forItem(item: Item): Talisman? {
            for (talisman in values()) {
                if (talisman.talisman.id == item.id) {
                    return talisman
                }
            }
            return null
        }

        /**
         * Method used to get the [Talisman] by the item.
         *
         * @return the [Talisman] or `null`.
         */
        @JvmStatic
        fun forName(name: String): Talisman? {
            for (talisman in values()) {
                if (talisman.name == name) {
                    return talisman
                }
            }
            return null
        }
    }
}
