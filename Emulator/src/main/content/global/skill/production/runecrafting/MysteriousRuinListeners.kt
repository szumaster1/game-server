package content.global.skill.production.runecrafting

import content.global.skill.production.runecrafting.data.MysteriousRuin
import content.global.skill.production.runecrafting.data.Staff
import content.global.skill.production.runecrafting.data.Talisman
import content.global.skill.production.runecrafting.data.Tiara
import core.api.*
import core.game.container.impl.EquipmentContainer.SLOT_HAT
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation

/**
 * Mysterious ruin listeners.
 */
class MysteriousRuinListeners : InteractionListener {

    private val animation = Animation(827)
    private val allowedUsed = arrayOf(1438, 1448, 1444, 1440, 1442, 5516, 1446, 1454, 1452, 1462, 1458, 1456, 1450, 1460).toIntArray()
    private val allowedWith = allRuins()
    private val talismanStaff = Staff.values().map { it.item.id }.toIntArray()
    private val nothingInteresting = "Nothing interesting happens."

    override fun defineListeners() {

        /*
         * Handles use talisman to enter the altar.
         */

        onUseWith(IntType.SCENERY, allowedUsed, *allowedWith) { player, used, with ->
            return@onUseWith handleTalisman(player, used, with)
        }

        /*
         * Handles enter the altar with tiara or staff equip.
         */

        on(allowedWith, IntType.SCENERY, "enter", "search") { player, node ->
            if (anyInEquipment(player, *talismanStaff)) {
                handleStaff(player, node)
            } else {
                handleTiara(player, node)
            }
            return@on true
        }
    }

    /*
     * Map all mysterious ruins.
     */

    private fun allRuins(): IntArray {
        return MysteriousRuin
            .values()
            .flatMap { ruins -> ruins.objects.asList() }
            .toIntArray()
    }

    /*
     * Handles teleport using talisman.
     */

    private fun handleTalisman(player: Player, used: Node, with: Node): Boolean {
        val ruin = MysteriousRuin.forObject(with.asScenery())
        if (!checkQuestCompletion(player, ruin!!)) {
            return true
        }

        val talisman = Talisman.forItem(used.asItem())
        if (talisman != ruin.talisman && talisman != Talisman.ELEMENTAL) {
            sendMessage(player, nothingInteresting)
            return false
        }
        if (talisman == Talisman.ELEMENTAL && (ruin.talisman != Talisman.AIR && ruin.talisman != Talisman.WATER && ruin.talisman != Talisman.FIRE && ruin.talisman != Talisman.EARTH)) {
            sendMessage(player, nothingInteresting)
            return false
        }

        teleportToRuinTalisman(player, used.asItem(), ruin)
        return true
    }

    /*
     * Handles teleport with staff.
     */

    private fun handleStaff(player: Player, node: Node): Boolean {
        val ruin = MysteriousRuin.forObject(node.asScenery())

        if (!checkQuestCompletion(player, ruin!!)) {
            return true
        }

        submitTeleportPulse(player, ruin, 0)
        return true
    }

    /*
     * Handles teleport with tiara.
     */

    private fun handleTiara(player: Player, node: Node): Boolean {
        val ruin = MysteriousRuin.forObject(node.asScenery())

        if (!checkQuestCompletion(player, ruin!!)) {
            return true
        }

        val tiara = Tiara.forItem(player.equipment.get(SLOT_HAT))
        if (tiara != ruin.tiara) {
            sendMessage(player, nothingInteresting)
            return false
        }

        submitTeleportPulse(player, ruin, 0)
        return true
    }

    /*
     * Check if player has all requirements.
     */

    private fun checkQuestCompletion(player: Player, ruin: MysteriousRuin): Boolean {
        return when (ruin) {
            MysteriousRuin.DEATH -> hasRequirement(player, QuestReq(QuestRequirements.MEP_2), true)
            MysteriousRuin.BLOOD -> hasRequirement(player, QuestReq(QuestRequirements.SEERGAZE), true)
            else -> hasRequirement(player, QuestReq(QuestRequirements.RUNE_MYSTERIES), true)
        }
    }

    /*
     * Handles teleport to altar using talisman on object.
     */

    private fun teleportToRuinTalisman(player: Player, talisman: Item, ruin: MysteriousRuin) {
        lock(player, 4)
        animate(player, animation)
        sendMessage(player, "You hold the ${talisman.name} towards the mysterious ruins.")
        submitTeleportPulse(player, ruin, 3)
    }

    /*
     * Handles standard teleport to altar using tiara or rc staff.
     */

    private fun submitTeleportPulse(player: Player, ruin: MysteriousRuin, delay: Int) {
        sendMessage(player, "You feel a powerful force take hold of you.")
        submitWorldPulse(object : Pulse(delay, player) {
            override fun pulse(): Boolean {
                teleport(player, ruin.end)
                return true
            }
        })
    }

}
