package content.global.handlers.iface.plugin

import core.api.consts.Components
import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.combat.equipment.WeaponInterface.WeaponInterfaces
import core.game.node.entity.player.Player
import core.game.node.entity.player.info.Rights
import core.game.world.GameWorld.settings
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Game interface plugin
 *
 * @constructor Game interface plugin
 */
@Initializable
class GameInterfacePlugin : ComponentPlugin() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        ComponentDefinition.put(Components.GAME_INTERFACE_740, this)
        return this
    }

    override fun handle(
        player: Player,
        component: Component,
        opcode: Int,
        button: Int,
        slot: Int,
        itemId: Int
    ): Boolean {
        when (component.id) {
            Components.GAME_INTERFACE_740 -> {
                when (button) {
                    3 -> player.interfaceManager.closeChatbox()
                }
                return true
            }

            Components.TOPLEVEL_FULLSCREEN_746 -> {
                when (button) {
                    12 -> player.packetDispatch.sendString(
                        "When you have finished playing " + settings!!.name + ", always use the button below to logout safely. ",
                        182,
                        0
                    )

                    49 -> player.packetDispatch.sendString(
                        "Friends List - " + settings!!.name + " " + settings!!.worldId,
                        550,
                        3
                    )

                    110 -> configureWorldMap(player)
                }
                return true
            }

            Components.TOPLEVEL_548 -> {
                if (button >= 38 && button <= 44 || button >= 20 && button <= 26) {
                    player.interfaceManager.currentTabIndex = getTabIndex(button)
                }
                when (button) {
                    21 -> player.packetDispatch.sendString(
                        "Friends List -" + settings!!.name + " " + settings!!.worldId,
                        Components.FRIENDS2_550,
                        3
                    )

                    22 -> {}
                    24 -> {}
                    25 -> {}
                    26 -> {}
                    38 -> if (player.getExtension<Any>(WeaponInterface::class.java) === WeaponInterfaces.STAFF) {
                        val c = Component(WeaponInterfaces.STAFF.interfaceId)
                        player.interfaceManager.openTab(0, c)
                        val inter = player.getExtension<WeaponInterface>(WeaponInterface::class.java)
                        inter.updateInterface()
                    }

                    39 -> {}
                    40 -> player.getQuestRepository().syncronizeTab(player)
                    41 -> player.inventory.refresh()
                    42 -> {}
                    43 -> {}
                    44 -> {}
                    66, 110 -> configureWorldMap(player)
                    69 -> player.packetDispatch.sendString(
                        "When you have finished playing " + settings!!.name + ", always use the button below to logout safely. ",
                        182,
                        0
                    )

                    else -> throw IllegalStateException("Unexpected value: $button")
                }
                return true
            }

            Components.TOPSTAT_RUN_750 -> {
                when (opcode) {
                    155 -> when (button) {
                        1 -> player.settings.toggleRun()
                    }
                }
                return true
            }

            Components.FILTERBUTTONS_751 -> {
                when (opcode) {
                    155 -> when (button) {
                        27 -> openReport(player)
                    }
                }
                return true
            }
        }
        return true
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
        player.interfaceManager.openWindowsPane(Component(755))
        val posHash = (player.location.z shl 28) or (player.location.x shl 14) or player.location.y
        player.packetDispatch.sendScriptConfigs(622, posHash, "", 0)
        player.packetDispatch.sendScriptConfigs(674, posHash, "", 0)
    }

    companion object {

        fun openReport(player: Player) {
            player.interfaceManager.open(Component(553)).setCloseEvent { player1: Player, c: Component? ->
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

        private fun getTabIndex(button: Int): Int {
            var tabIndex = button - 38
            if (button < 27) {
                tabIndex = (button - 20) + 7
            }
            return tabIndex
        }
    }
}
