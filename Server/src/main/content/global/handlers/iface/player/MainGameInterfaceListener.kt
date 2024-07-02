package content.global.handlers.iface.player

import core.api.consts.Components
import core.api.setInterfaceText
import core.game.component.CloseEvent
import core.game.component.Component
import core.game.interaction.InterfaceListener
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.combat.equipment.WeaponInterface.WeaponInterfaces
import core.game.node.entity.player.Player
import core.game.node.entity.player.info.Rights
import core.game.world.GameWorld

class MainGameInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.FILTERBUTTONS_751, 27) { player, _, _, _, _, _ ->
            openReport(player)
            return@on true
        }

        on(Components.TOPSTAT_RUN_750) { player, _, _, _, _, _ ->
            player.settings.toggleRun()
            return@on true
        }

        on(Components.TOPLEVEL_FULLSCREEN_746) { player, _, _, buttonID, _, _ ->
            when (buttonID) {
                12 -> setInterfaceText(player, "When you have finished playing<br>${GameWorld.settings!!.name}, always use the<br>button below to logout safely.", Components.LOGOUT_182, 0)
                49 -> setInterfaceText(player, "Friends List - World ${GameWorld.settings!!.worldId}", Components.FRIENDS2_550, 3)
                110 -> configureWorldMap(player)
            }
            return@on true
        }

        on(Components.TOPLEVEL_548) { player, _, _, buttonID, _, _ ->
            when (buttonID) {
                21 -> setInterfaceText(player, "Friends List - World ${GameWorld.settings!!.worldId}", Components.FRIENDS2_550, 3)
                38 -> {
                    if (player.getExtension<Any>(WeaponInterface::class.java) === WeaponInterfaces.STAFF) {
                        val c = Component(WeaponInterfaces.STAFF.interfaceId)
                        player.interfaceManager.openTab(0, c)
                        val inter = player.getExtension<WeaponInterface>(WeaponInterface::class.java)
                        inter.updateInterface()
                    }
                }

                40 -> player.questRepository.syncronizeTab(player)
                41 -> player.inventory.refresh()
                66, 110 -> configureWorldMap(player)
                69 -> setInterfaceText(player, "When you have finished playing<br>${GameWorld.settings!!.name}, always use the<br>button below to logout safely.", Components.LOGOUT_182, 0)
            }
            return@on true
        }
    }

    fun openReport(player: Player) {
        player.interfaceManager.open(Component(Components.SNAPSHOT_MAIN_553)).closeEvent =
            CloseEvent { player1: Player, c: Component? ->
                player1.packetDispatch.sendRunScript(80, "")
                player1.packetDispatch.sendRunScript(137, "")
                true
            }
        player.packetDispatch.sendRunScript(508, "")
        if (player.details.rights !== Rights.REGULAR_PLAYER) {
            for (i in 0..17) {
                player.packetDispatch.sendInterfaceConfig(Components.SNAPSHOT_MAIN_553, i, false)
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
        player.interfaceManager.openWindowsPane(Component(Components.WORLDMAP_755))
        val posHash = player.location.z shl 28 or (player.location.x shl 14) or player.location.y
        player.packetDispatch.sendScriptConfigs(622, posHash, "", 0)
        player.packetDispatch.sendScriptConfigs(674, posHash, "", 0)
    }
}
