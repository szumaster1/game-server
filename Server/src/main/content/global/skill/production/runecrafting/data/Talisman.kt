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
        talisman = Item(Items.AIR_TALISMAN_1438),
        ruin = MysteriousRuin.AIR
    ),
    MIND(
        talisman = Item(Items.MIND_TALISMAN_1448),
        ruin = MysteriousRuin.MIND
    ),
    WATER(
        talisman = Item(Items.WATER_TALISMAN_1444),
        ruin = MysteriousRuin.WATER
    ),
    EARTH(
        talisman = Item(Items.EARTH_TALISMAN_1440),
        ruin = MysteriousRuin.EARTH
    ),
    FIRE(
        talisman = Item(Items.FIRE_TALISMAN_1442),
        ruin = MysteriousRuin.FIRE
    ),
    ELEMENTAL(
        talisman = Item(Items.ELEMENTAL_TALISMAN_5516),
        ruin = null
    ),
    BODY(
        talisman = Item(Items.BODY_TALISMAN_1446),
        ruin = MysteriousRuin.BODY
    ),
    COSMIC(
        talisman = Item(Items.COSMIC_TALISMAN_1454),
        ruin = MysteriousRuin.COSMIC
    ),
    CHAOS(
        talisman = Item(Items.CHAOS_TALISMAN_1452),
        ruin = MysteriousRuin.CHAOS
    ),
    NATURE(
        talisman = Item(Items.NATURE_TALISMAN_1462),
        ruin = MysteriousRuin.NATURE
    ),
    LAW(
        talisman = Item(Items.LAW_TALISMAN_1458),
        ruin = MysteriousRuin.LAW
    ),
    DEATH(
        talisman = Item(Items.DEATH_TALISMAN_1456),
        ruin = MysteriousRuin.DEATH
    ),
    BLOOD(
        talisman = Item(Items.BLOOD_TALISMAN_1450),
        ruin = MysteriousRuin.BLOOD
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
