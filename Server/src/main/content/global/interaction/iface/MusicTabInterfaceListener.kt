package content.global.interaction.iface

import core.api.consts.Components
import core.api.sendMessage
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.info.Rights
import core.game.node.entity.player.link.music.MusicEntry

class MusicTabInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.MUSIC_V3_187) { player, _, opcode, buttonID, slot, _ ->
            return@on when (opcode) {
                155 -> {
                    when (buttonID) {
                        11 -> {
                            player.musicPlayer.toggleLooping()
                            true
                        }
                        1 -> {
                            val entry = player.musicPlayer.unlocked[slot]
                            return@on if (entry == null) {
                                if (player.rights == Rights.ADMINISTRATOR) {
                                    for (ent in MusicEntry.getSongs().values) {
                                        if (ent.index == slot) {
                                            player.musicPlayer.unlock(ent.id)
                                            break
                                        }
                                    }
                                } else {
                                    sendMessage(player, "You have not unlocked this piece of music yet!</col>")
                                }
                                true
                            } else {
                                player.musicPlayer.isPlaying = false
                                player.musicPlayer.play(entry)
                                true
                            }
                        }

                        else -> false
                    }
                }

                else -> false
            }
        }
    }
}
