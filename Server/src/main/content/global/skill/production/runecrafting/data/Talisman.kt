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
        if (this == ELEMENTAL || ruin == null) {
            sendMessage(player, "You cannot tell which direction the Talisman is pulling...")
            return
        }
        val loc = ruin.base
        val direction = when {
            player.location.y > loc.y && player.location.x - 1 > loc.x -> "south-west"
            player.location.x < loc.x && player.location.y > loc.y -> "south-east"
            player.location.x > loc.x + 1 && player.location.y < loc.y -> "north-west"
            player.location.x < loc.x && player.location.y < loc.y -> "north-east"
            player.location.y < loc.y -> "north"
            player.location.y > loc.y -> "south"
            player.location.x < loc.x + 1 -> "east"
            player.location.x > loc.x + 1 -> "west"
            else -> ""
        }
        sendMessage(player, "The talisman pulls towards the $direction.")
    }

    fun getRuin(): MysteriousRuin? {
        return MysteriousRuin.values().find { it.name == name }
    }

    fun getTiara(): Tiara? {
        return Tiara.values().find { it.name == name }
    }

    companion object {

        fun forItem(item: Item): Talisman? {
            return values().find { it.talisman.id == item.id }
        }

        fun forName(name: String): Talisman? {
            return values().find { it.name == name }
        }
    }
}
