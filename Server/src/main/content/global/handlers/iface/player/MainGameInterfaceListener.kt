package content.global.handlers.iface.player

import core.api.consts.Components
import core.game.component.CloseEvent
import core.game.component.Component
import core.game.interaction.InterfaceListener
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.combat.equipment.WeaponInterface.WeaponInterfaces
import core.game.node.entity.player.Player
import core.game.node.entity.player.info.Rights

class MainGameInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(FILTER_BUTTONS){player, _, _, buttonID, _, _ ->
            if(buttonID == 27)
                openReport(player)
            return@on true
        }

        on(RUN_BUTTON){player, _, _, _, _, _ ->
            player.settings.toggleRun()
            return@on true
        }

        on(TOPLEVEL_FS){player, _, _, buttonID, _, _ ->
            if (buttonID == 110)
                configureWorldMap(player)
            return@on true
        }

        on(TOPLEVEL){player, _, _, buttonID, _, _ ->
            when (buttonID) {
                38 -> {
                    if (player.getExtension<Any>(WeaponInterface::class.java) == WeaponInterfaces.STAFF) {
                        val c = Component(WeaponInterfaces.STAFF.interfaceId)
                        player.interfaceManager.openTab(0, c)
                        val inter = player.getExtension<WeaponInterface>(WeaponInterface::class.java)
                        inter.updateInterface()
                    }
                }
                40 -> player.questRepository.syncronizeTab(player)
                41 -> player.inventory.refresh()
                66, 110 -> configureWorldMap(player)
            }
            return@on true
        }
    }

    fun openReport(player: Player) {
        player.interfaceManager.open(Component(REPORT_ABUSE)).closeEvent =
            CloseEvent { player1: Player, c: Component? ->
                player1.packetDispatch.sendRunScript(80, "")
                player1.packetDispatch.sendRunScript(137, "")
                true
            }
        player.packetDispatch.sendRunScript(508, "")
        if (player.details.rights !== Rights.REGULAR_PLAYER) {
            for (i in 0..17) {
                player.packetDispatch.sendInterfaceConfig(553, i, false)
            }
        }
    }

    private fun configureWorldMap(player: Player) {
        if (player.inCombat()) {
            player.packetDispatch.sendMessage("It wouldn't be very wise opening the world map during combat.")
            return
        }
        if (player.locks.isInteractionLocked || player.locks.isMovementLocked) {
            player.packetDispatch.sendMessage("You can't do this right now.")
            return
        }
        player.interfaceManager.close()
        player.interfaceManager.openWindowsPane(Component(755))
        val posHash = player.location.z shl 28 or (player.location.x shl 14) or player.location.y
        player.packetDispatch.sendScriptConfigs(622, posHash, "", 0)
        player.packetDispatch.sendScriptConfigs(674, posHash, "", 0)
    }

    companion object {
        private const val TOPLEVEL = Components.TOPLEVEL_548
        private const val TOPLEVEL_FS = Components.TOPLEVEL_FULLSCREEN_746
        private const val RUN_BUTTON = Components.TOPSTAT_RUN_750
        private const val FILTER_BUTTONS = Components.FILTERBUTTONS_751
        private const val REPORT_ABUSE = Components.SNAPSHOT_MAIN_553
    }
}
