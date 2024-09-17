package content.global.skill.production.smithing

import content.global.skill.production.smithing.data.Bar
import content.global.skill.production.smithing.data.BarType
import content.global.skill.production.smithing.data.Bars
import content.global.skill.production.smithing.data.SmithingType
import core.api.*
import org.rs.consts.Components
import core.game.container.access.InterfaceContainer
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.tools.StringUtils

/**
 * Represents the smithing interface builder.
 */
class SmithingInterfaceBuilder(item: Item) {

    val type: BarType? = BarType.getBarTypeForId(item.id)
    val bar: Bar? = Bar.forId(item.id)

    /**
     * Build smithing interface.
     *
     * @param player the player.
     */
    fun build(player: Player) {
        player.gameAttributes.removeAttribute("smith-type")
        player.gameAttributes.setAttribute("smith-type", type)
        setComponentVisibility(player, Components.SMITHING_NEW_300, 267, false)
        val bars = Bars.getBars(type!!)
        for (value in bars) {
            if (value!!.smithingType == SmithingType.TYPE_GRAPPLE_TIP) {
                setComponentVisibility(player, Components.SMITHING_NEW_300, 169, false)
            }
            if (value.smithingType == SmithingType.TYPE_DART_TIP) {
                setComponentVisibility(player, Components.SMITHING_NEW_300, 65, false)
            }
            if (value.smithingType == SmithingType.TYPE_SPIT_IRON || value.smithingType == SmithingType.TYPE_WIRE || value.smithingType == SmithingType.TYPE_BULLSEYE) {
                setComponentVisibility(player, Components.SMITHING_NEW_300, 89, false)
            }
            if (value.smithingType == SmithingType.TYPE_CLAWS) {
                setComponentVisibility(player, Components.SMITHING_NEW_300, 209, false)
            }
            if (value.smithingType == SmithingType.TYPE_LANTERN || value.smithingType == SmithingType.TYPE_OIL_LANTERN) {
                setComponentVisibility(player, Components.SMITHING_NEW_300, 161, false)
            }
            if (value.smithingType == SmithingType.TYPE_STUDS) {
                setComponentVisibility(player, Components.SMITHING_NEW_300, 169, false)
            }
            if (value.smithingType == SmithingType.TYPE_Crossbow_Bolt || value.smithingType == SmithingType.TYPE_Crossbow_Limb) {
                setComponentVisibility(player, Components.SMITHING_NEW_300, 249, false)
                setComponentVisibility(player, Components.SMITHING_NEW_300, 15, true)
            }

            var color: String? = ""
            color = if (getStatLevel(player, Skills.SMITHING) < value.level) null else "<col=FFFFFF>"
            sendString(player, color + StringUtils.formatDisplayName(value.smithingType.name.replace("TYPE_", "")), Components.SMITHING_NEW_300, value.smithingType.nameId)
            color = if (anyInInventory(player, value.barType.bar, value.smithingType.bar)) "<col=2DE120>" else null
            if (color != null) {
                val amt = if (value.smithingType.bar > 1) "s" else ""
                sendString(player, color + value.smithingType.bar + " Bar" + amt, Components.SMITHING_NEW_300, value.smithingType.nameId + 1)
            }
            InterfaceContainer.generateItems(player, arrayOf(Item(value.product, value.smithingType.amount)), arrayOf(""), Components.SMITHING_NEW_300, value.smithingType.base - 1)
        }
        sendString(player, type.barName, Components.SMITHING_NEW_300, 14)
        openInterface(player, Components.SMITHING_NEW_300)
    }
}
