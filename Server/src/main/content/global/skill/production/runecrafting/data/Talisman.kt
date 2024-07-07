package content.global.skill.production.runecrafting.data

import core.api.consts.Items
import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item

enum class Talisman(
    val talisman: Item,
    private val ruin: MysteriousRuin?
) {
    AIR(
        Item(Items.AIR_TALISMAN_1438),
        MysteriousRuin.AIR
    ),
    MIND(
        Item(Items.MIND_TALISMAN_1448),
        MysteriousRuin.MIND
    ),
    WATER(
        Item(Items.WATER_TALISMAN_1444),
        MysteriousRuin.WATER
    ),
    EARTH(
        Item(Items.EARTH_TALISMAN_1440),
        MysteriousRuin.EARTH
    ),
    FIRE(
        Item(Items.FIRE_TALISMAN_1442),
        MysteriousRuin.FIRE
    ),
    ELEMENTAL(
        Item(Items.ELEMENTAL_TALISMAN_5516),
        null
    ),
    BODY(
        Item(Items.BODY_TALISMAN_1446),
        MysteriousRuin.BODY
    ),
    COSMIC(
        Item(Items.COSMIC_TALISMAN_1454),
        MysteriousRuin.COSMIC
    ),
    CHAOS(
        Item(Items.CHAOS_TALISMAN_1452),
        MysteriousRuin.CHAOS
    ),
    NATURE(
        Item(Items.NATURE_TALISMAN_1462),
        MysteriousRuin.NATURE
    ),
    LAW(
        Item(Items.LAW_TALISMAN_1458),
        MysteriousRuin.LAW
    ),
    DEATH(
        Item(Items.DEATH_TALISMAN_1456),
        MysteriousRuin.DEATH
    ),
    BLOOD(
        Item(Items.BLOOD_TALISMAN_1450),
        MysteriousRuin.BLOOD
    );

    fun locate(player: Player) {
        if (this == ELEMENTAL) {
            sendMessage(player, "You cannot tell which direction the Talisman is pulling...")
            return
        }
        var direction = ""
        val loc = ruin!!.base
        if (player.location.y > loc.y && player.location.x - 1 > loc.x) direction = "south-west"
        else if (player.location.x < loc.x && player.location.y > loc.y) direction = "south-east"
        else if (player.location.x > loc.x + 1 && player.location.y < loc.y) direction = "north-west"
        else if (player.location.x < loc.x && player.location.y < loc.y) direction = "north-east"
        else if (player.location.y < loc.y) direction = "north"
        else if (player.location.y > loc.y) direction = "south"
        else if (player.location.x < loc.x + 1) direction = "east"
        else if (player.location.x > loc.x + 1) direction = "west"
        sendMessage(player, "The talisman pulls towards the $direction.")
    }


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

        fun forItem(item: Item): Talisman? {
            for (talisman in values()) {
                if (talisman.talisman.id == item.id) {
                    return talisman
                }
            }
            return null
        }

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
