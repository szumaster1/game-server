package content.region.asgarnia.quest.rd

import content.region.asgarnia.quest.rd.dialogue.SirTinleyDialogueFile
import core.api.*
import cfg.consts.Items
import cfg.consts.NPCs
import cfg.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.zone.ZoneBorders

/**
 * Chicken fox and grain listener.
 */
class ChickenFoxAndGrainListener : InteractionListener {
    companion object {
        const val VARBIT_FOX_EAST = 680
        const val VARBIT_FOX_WEST = 681
        const val VARBIT_CHICKEN_EAST = 682
        const val VARBIT_CHICKEN_WEST = 683
        const val VARBIT_GRAIN_EAST = 684
        const val VARBIT_GRAIN_WEST = 685

        val EAST_BORDERS = ZoneBorders(2483, 4968, 2489, 4976)
        val WEST_BORDERS = ZoneBorders(2472, 4968, 2476, 4976)

        fun countEquipmentItems(player: Player): Int {
            var count = 0
            if(inEquipment(player, Items.GRAIN_5607)) { count++ }
            if(inEquipment(player, Items.FOX_5608)) { count++ }
            if(inEquipment(player, Items.CHICKEN_5609)) { count++ }
            return count
        }

        fun checkFinished(player: Player) {
            if (getVarbit(player, VARBIT_FOX_WEST) == 1 && getVarbit(player, VARBIT_CHICKEN_WEST) == 1 && getVarbit(player, VARBIT_GRAIN_WEST) == 1) {
                sendMessage(player, "Congratulations! You have solved this room's puzzle!")
                setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_PASSED, true)
            }
        }

        fun checkFail(player: Player): Boolean {
            return ((getVarbit(player, VARBIT_FOX_EAST) == 0 && getVarbit(player, VARBIT_CHICKEN_EAST) == 0 && getVarbit(player, VARBIT_GRAIN_EAST) == 1) ||
                    (getVarbit(player, VARBIT_FOX_EAST) == 1 && getVarbit(player, VARBIT_CHICKEN_EAST) == 0 && getVarbit(player, VARBIT_GRAIN_EAST) == 0) ||
                    (getVarbit(player, VARBIT_FOX_WEST) == 1 && getVarbit(player, VARBIT_CHICKEN_WEST) == 1 && getVarbit(player, VARBIT_GRAIN_WEST) == 0) ||
                    (getVarbit(player, VARBIT_FOX_WEST) == 0 && getVarbit(player, VARBIT_CHICKEN_WEST) == 1 && getVarbit(player, VARBIT_GRAIN_WEST) == 1))
        }
    }

    override fun defineListeners() {
        on(Scenery.PRECARIOUS_BRIDGE_7286, SCENERY, "cross") { player, _ ->
            if (countEquipmentItems(player) > 1) {
                sendDialogue(player, "I really don't think I should be carrying more than 5Kg across that rickety bridge...")
            } else if (checkFail(player)) {
                openDialogue(player, SirTinleyDialogueFile(2), NPC(NPCs.SIR_SPISHYUS_2282)) // Fail
            } else {
                lock(player, 5)
                sendMessage(player, "You carefully walk across the rickety bridge...")
                player.walkingQueue.reset()
                player.walkingQueue.addPath(2476, 4972)
            }
            return@on true
        }

        on(Scenery.PRECARIOUS_BRIDGE_7287, SCENERY, "cross") { player, _ ->
            if (countEquipmentItems(player) > 1) {
                sendDialogue(player, "I really don't think I should be carrying more than 5Kg across that rickety bridge...")
            } else if (checkFail(player)) {
                openDialogue(player, SirTinleyDialogueFile(2), NPC(NPCs.SIR_SPISHYUS_2282)) // Fail
            } else {
                lock(player, 5)
                sendMessage(player, "You carefully walk across the rickety bridge...")
                player.walkingQueue.reset()
                player.walkingQueue.addPath(2484, 4972)
            }
            return@on true
        }

        on(Scenery.GRAIN_7284, SCENERY, "pick-up") { player, _ ->
            if (EAST_BORDERS.insideBorder(player)) {
                player.equipment.add(Item(Items.GRAIN_5607), 1, true, false)
                setVarbit(player, VARBIT_GRAIN_EAST, 1)
            }
            if (WEST_BORDERS.insideBorder(player)) {
                player.equipment.add(Item(Items.GRAIN_5607), 1, true, false)
                setVarbit(player, VARBIT_GRAIN_WEST, 0)
            }
            return@on true
        }

        onUnequip(Items.GRAIN_5607) { player, _ ->
            if (EAST_BORDERS.insideBorder(player)) {
                removeItem(player, Items.GRAIN_5607, Container.EQUIPMENT)
                setVarbit(player, VARBIT_GRAIN_EAST, 0)
            }
            if (WEST_BORDERS.insideBorder(player)) {
                removeItem(player, Items.GRAIN_5607, Container.EQUIPMENT)
                setVarbit(player, VARBIT_GRAIN_WEST, 1)
                checkFinished(player)
            }
            return@onUnequip true
        }


        on(Scenery.FOX_7277, SCENERY, "pick-up") { player, _ ->
            if (EAST_BORDERS.insideBorder(player)) {
                player.equipment.add(Item(Items.FOX_5608), 3, true, false)
                setVarbit(player, VARBIT_FOX_EAST, 1)
            }
            if (WEST_BORDERS.insideBorder(player)) {
                player.equipment.add(Item(Items.FOX_5608), 3, true, false)
                setVarbit(player, VARBIT_FOX_WEST, 0)
            }
            return@on true
        }

        onUnequip(Items.FOX_5608) { player, _ ->
            if (EAST_BORDERS.insideBorder(player)) {
                removeItem(player, Items.FOX_5608, Container.EQUIPMENT)
                setVarbit(player, VARBIT_FOX_EAST, 0)
            }
            if (WEST_BORDERS.insideBorder(player)) {
                removeItem(player, Items.FOX_5608, Container.EQUIPMENT)
                setVarbit(player, VARBIT_FOX_WEST, 1)
                checkFinished(player)
            }
            return@onUnequip true
        }


        on(Scenery.CHICKEN_7281, IntType.SCENERY, "pick-up") { player, _ ->
            if (EAST_BORDERS.insideBorder(player)) {
                player.equipment.add(Item(Items.CHICKEN_5609), 5, true, false)
                setVarbit(player, VARBIT_CHICKEN_EAST, 1)
            }
            if (WEST_BORDERS.insideBorder(player)) {
                player.equipment.add(Item(Items.CHICKEN_5609), 5, true, false)
                setVarbit(player, VARBIT_CHICKEN_WEST, 0)
            }
            return@on true
        }

        onUnequip(Items.CHICKEN_5609) { player, _ ->
            if (EAST_BORDERS.insideBorder(player)) {
                removeItem(player, Items.CHICKEN_5609, Container.EQUIPMENT)
                setVarbit(player, VARBIT_CHICKEN_EAST, 0)
            }
            if (WEST_BORDERS.insideBorder(player)) {
                removeItem(player, Items.CHICKEN_5609, Container.EQUIPMENT)
                setVarbit(player, VARBIT_CHICKEN_WEST, 1)
                checkFinished(player)
            }
            return@onUnequip true
        }

    }

}