package content.global.handlers.iface.player

import core.api.closeInterface
import core.api.consts.Components
import core.api.getVarp
import core.api.openSingleTab
import core.api.setVarp
import core.game.component.Component
import core.game.interaction.InterfaceListener

class SettingTabInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.OPTIONS_261) { player, _, _, buttonID, _, _ ->

            for (i in SOUNDS_BUTTON.indices) if (buttonID == i) {
                val volume11 = 33 - buttonID
                player.settings.areaSoundVolume = volume11
                return@on true
            }
            for (i in SOUND_BUTTON.indices) if (buttonID == i) {
                val volume1 = 20 - buttonID
                player.settings.soundEffectVolume = volume1
                return@on true
            }
            for (i in MUSIC_BUTTON.indices) if (buttonID == i) {
                val volume = 15 - buttonID
                player.settings.musicVolume = volume
                return@on true
            }

            return@on when (buttonID) {
                BRIGHTNESS_BUTTON -> {
                    val brightness = buttonID - 7
                    player.settings.brightness = brightness
                    true
                }

                SETTING_BUTTON -> {
                    player.interfaceManager.open(Component(742))
                    true
                }

                SETTING_BUTTON_2 -> {
                    player.interfaceManager.open(Component(743))
                    true
                }

                MOUSE_BUTTON -> {
                    player.settings.toggleMouseButton()
                    true
                }

                EFFECTS_BUTTON -> {
                    player.settings.toggleChatEffects()
                    true
                }

                PRIVATE_CHAT_BUTTON -> {
                    player.settings.toggleSplitPrivateChat()
                    true
                }

                AID_BUTTON -> {
                    if (player.ironmanManager.checkRestriction()) {
                        return@on true
                    }
                    player.settings.toggleAcceptAid()
                    true
                }

                RUN_BUTTON -> {
                    player.settings.toggleRun()
                    true
                }

                HOUSE_BUTTON -> {
                    closeInterface(player)
                    setVarp(player, 261, getVarp(player, 261) and 0x1)
                    openSingleTab(player, Components.POH_HOUSE_OPTIONS_398)
                    true
                }

                else -> false
            }
        }
    }

    companion object {
        val HOUSE_BUTTON = 8
        val RUN_BUTTON = 3
        val AID_BUTTON = 7
        val PRIVATE_CHAT_BUTTON = 5
        val EFFECTS_BUTTON = 4
        val MOUSE_BUTTON = 6
        val SOUNDS_BUTTON = intArrayOf(29, 30, 31, 32, 33)
        val SOUND_BUTTON = intArrayOf(17, 19, 20)
        val MUSIC_BUTTON = intArrayOf(11, 12, 13, 14, 15)
        val SETTING_BUTTON = 16
        val SETTING_BUTTON_2 = 18
        val BRIGHTNESS_BUTTON = 10
    }
}

