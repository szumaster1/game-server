package content.region.asgarnia.quest.rd.handlers

import cfg.consts.Items
import cfg.consts.NPCs
import cfg.consts.Scenery
import content.region.asgarnia.quest.rd.RDUtils
import content.region.asgarnia.quest.rd.RecruitmentDrive
import content.region.asgarnia.quest.rd.dialogue.SirTinleyDialogueFile
import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.zone.ZoneBorders

/**
 * Chicken, Fox, and Grain listener.
 */
class SirSpishyusRoomListeners : InteractionListener {

    companion object {
        private val EAST_BORDERS = ZoneBorders(2483, 4968, 2489, 4976)
        private val WEST_BORDERS = ZoneBorders(2472, 4968, 2476, 4976)

        /**
         * Counts the number of specific equipment items the player has equipped.
         *
         * @param player The player whose equipment is being checked.
         * @return The count of specific items in the player's equipment.
         */
        private fun countEquipmentItems(player: Player): Int {
            return listOf(Items.GRAIN_5607, Items.FOX_5608, Items.CHICKEN_5609).count { inEquipment(player, it) }
        }

        /**
         * Checks if the player has completed the puzzle in the current room.
         *
         * @param player The player to check for completion.
         */
        private fun checkFinished(player: Player) {
            if (getVarbit(player, RDUtils.VARBIT_FOX_WEST) == 1 &&
                getVarbit(player, RDUtils.VARBIT_CHICKEN_WEST) == 1 &&
                getVarbit(player, RDUtils.VARBIT_GRAIN_WEST) == 1
            ) {
                sendMessage(player, "Congratulations! You have solved this room's puzzle!")
                setAttribute(player, RecruitmentDrive.stagePass, true)
            }
        }


        /**
         * Checks if the player has failed the puzzle conditions.
         *
         * @param player The player to check for failure conditions.
         * @return True if the player has failed, otherwise false.
         */
        private fun checkFail(player: Player): Boolean {
            val eastStatus = listOf(
                getVarbit(player, RDUtils.VARBIT_FOX_EAST),
                getVarbit(player, RDUtils.VARBIT_CHICKEN_EAST),
                getVarbit(player, RDUtils.VARBIT_GRAIN_EAST)
            )
            val westStatus = listOf(
                getVarbit(player, RDUtils.VARBIT_FOX_WEST),
                getVarbit(player, RDUtils.VARBIT_CHICKEN_WEST),
                getVarbit(player, RDUtils.VARBIT_GRAIN_WEST)
            )

            return (eastStatus[0] == 1 && eastStatus[1] == 0 && eastStatus[2] == 0) ||
                   (eastStatus[0] == 0 && eastStatus[1] == 1 && eastStatus[2] == 1) ||
                   (westStatus[0] == 1 && westStatus[1] == 1 && westStatus[2] == 0) ||
                   (westStatus[0] == 0 && westStatus[1] == 0 && westStatus[2] == 1)
        }

        /*
         * Handles the interaction of crossing the bridge.
         */

        private fun handleCrossBridge(player: Player, x: Int, y: Int) {
            if (countEquipmentItems(player) > 1) {
                sendDialogue(
                    player,
                    "I really don't think I should be carrying more than 5Kg across that rickety bridge..."
                )
            } else if (checkFail(player)) {
                openDialogue(player, SirTinleyDialogueFile(2), NPC(NPCs.SIR_SPISHYUS_2282))
            } else {
                lock(player, 5)
                sendMessage(player, "You carefully walk across the rickety bridge...")
                player.walkingQueue.reset()
                player.walkingQueue.addPath(x, y)
            }
        }
    }

    override fun defineListeners() {

        /*
         * Handle cross bridge interaction.
         */

        on(Scenery.PRECARIOUS_BRIDGE_7286, SCENERY, "cross") { player, _ ->
            handleCrossBridge(player, 2476, 4972)
            return@on true
        }

        /*
         * Handle cross bridge interaction.
         */

        on(Scenery.PRECARIOUS_BRIDGE_7287, SCENERY, "cross") { player, _ ->
            handleCrossBridge(player, 2484, 4972)
            return@on true
        }

        /*
         * Handle pickup the Grain from ground.
         */

        on(Scenery.GRAIN_7284, SCENERY, "pick-up") { player, _ ->
            val item = Item(Items.GRAIN_5607)
            if (EAST_BORDERS.insideBorder(player)) {
                player.equipment.add(item, 1, true, false)
                setVarbit(player, RDUtils.VARBIT_GRAIN_EAST, 1)
            }
            if (WEST_BORDERS.insideBorder(player)) {
                player.equipment.add(item, 1, true, false)
                setVarbit(player, RDUtils.VARBIT_GRAIN_WEST, 0)
            }
            return@on true
        }

        /*
         * Handle unequipped the Grain on ground.
         */

        onUnequip(Items.GRAIN_5607) { player, _ ->
            if (EAST_BORDERS.insideBorder(player)) {
                removeItem(player, Items.GRAIN_5607, Container.EQUIPMENT)
                setVarbit(player, RDUtils.VARBIT_GRAIN_EAST, 0)
            }
            if (WEST_BORDERS.insideBorder(player)) {
                removeItem(player, Items.GRAIN_5607, Container.EQUIPMENT)
                setVarbit(player, RDUtils.VARBIT_GRAIN_WEST, 1)
                checkFinished(player)
            }
            return@onUnequip true
        }

        /*
         * Handle pickup the Fox from ground.
         */

        on(Scenery.FOX_7277, SCENERY, "pick-up") { player, _ ->
            val item = Item(Items.FOX_5608)
            if (EAST_BORDERS.insideBorder(player)) {
                player.equipment.add(item, 3, true, false)
                setVarbit(player, RDUtils.VARBIT_FOX_EAST, 1)
            }
            if (WEST_BORDERS.insideBorder(player)) {
                player.equipment.add(item, 3, true, false)
                setVarbit(player, RDUtils.VARBIT_FOX_WEST, 0)
            }
            return@on true
        }

        /*
         * Handle unequipped the Fox on ground.
         */

        onUnequip(Items.FOX_5608) { player, _ ->
            if (EAST_BORDERS.insideBorder(player)) {
                removeItem(player, Items.FOX_5608, Container.EQUIPMENT)
                setVarbit(player, RDUtils.VARBIT_FOX_EAST, 0)
            }
            if (WEST_BORDERS.insideBorder(player)) {
                removeItem(player, Items.FOX_5608, Container.EQUIPMENT)
                setVarbit(player, RDUtils.VARBIT_FOX_WEST, 1)
                checkFinished(player)
            }
            return@onUnequip true
        }

        /*
         * Handle pickup the Chicken from ground.
         */

        on(Scenery.CHICKEN_7281, IntType.SCENERY, "pick-up") { player, _ ->
            val item = Item(Items.CHICKEN_5609)
            if (EAST_BORDERS.insideBorder(player)) {
                player.equipment.add(item, 5, true, false)
                setVarbit(player, RDUtils.VARBIT_CHICKEN_EAST, 1)
            }
            if (WEST_BORDERS.insideBorder(player)) {
                player.equipment.add(item, 5, true, false)
                setVarbit(player, RDUtils.VARBIT_CHICKEN_WEST, 0)
            }
            return@on true
        }

        /*
         * Handle unequipped the Chicken on ground.
         */

        onUnequip(Items.CHICKEN_5609) { player, _ ->
            if (EAST_BORDERS.insideBorder(player)) {
                removeItem(player, Items.CHICKEN_5609, Container.EQUIPMENT)
                setVarbit(player, RDUtils.VARBIT_CHICKEN_EAST, 0)
            }
            if (WEST_BORDERS.insideBorder(player)) {
                removeItem(player, Items.CHICKEN_5609, Container.EQUIPMENT)
                setVarbit(player, RDUtils.VARBIT_CHICKEN_WEST, 1)
                checkFinished(player)
            }
            return@onUnequip true
        }
    }
}