package content.region.asgarnia.burthope.handlers.wguild

import org.rs.consts.Items
import core.game.component.Component
import core.game.container.impl.EquipmentContainer
import core.game.interaction.InteractionListener
import core.game.node.item.Item

class WarriorsGuildListener : InteractionListener {

    override fun defineListeners() {
        onEquip(Items.DEFENSIVE_SHIELD_8856) { player, node ->
            if (node is Item) {
                if (player.location != CatapultRoom.TARGET) {
                    player.packetDispatch.sendMessage("You may not equip this shield outside the target area in the Warrior's Guild.")
                    return@onEquip false
                }
                if (player.equipment[EquipmentContainer.SLOT_WEAPON] != null) {
                    player.dialogueInterpreter.sendDialogue("You will need to make sure your sword hand is free to equip this", "shield.")
                    return@onEquip false
                }
                player.interfaceManager.removeTabs(2, 3, 5, 6, 7, 11, 12)
                player.interfaceManager.openTab(4, Component(411))
                player.interfaceManager.setViewedTab(4)
                player.interfaceManager.open(Component(410))
                return@onEquip true
            }

            return@onEquip false
        }

        onUnequip(Items.DEFENSIVE_SHIELD_8856) { player, _ ->
            player.interfaceManager.restoreTabs()
            player.interfaceManager.openTab(4, Component(387))
            return@onUnequip true
        }
    }
}
