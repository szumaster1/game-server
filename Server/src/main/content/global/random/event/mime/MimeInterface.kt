package content.global.random.event.mime

import core.api.*
import core.api.consts.Components
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.link.emote.Emotes

class MimeInterface : InterfaceListener {

    override fun defineInterfaceListeners() {

        on(Components.MACRO_MIME_EMOTES_188) { player, _, _, buttonID, _, _ ->

            when (buttonID) {
                2 -> emote(player, Emotes.THINK)
                3 -> emote(player, Emotes.CRY)
                4 -> emote(player, Emotes.LAUGH)
                5 -> emote(player, Emotes.DANCE)
                6 -> emote(player, Emotes.CLIMB_ROPE)
                7 -> emote(player, Emotes.LEAN_ON_AIR)
                8 -> emote(player, Emotes.GLASS_BOX)
                9 -> emote(player, Emotes.GLASS_WALL)
            }

            for (i in (2..9))
                if (i == buttonID && getAttribute(player, MimeUtils.COPY_ATTRIBUTE, -1) == i)
                    player.incrementAttribute(MimeUtils.CORRECT_ATTRIBUTE)
                else
                    setAttribute(player, MimeUtils.FAIL_ATTRIBUTE, 1)



            runTask(player, 5) {
                var correct = getAttribute(player, MimeUtils.CORRECT_ATTRIBUTE, -1)

                if (correct in 1..3) {
                    sendUnclosableDialogue(player, true, "", "Correct!")
                    emote(player, Emotes.CHEER)
                } else {
                    sendUnclosableDialogue(player, true, "", "Wrong!")
                    emote(player, Emotes.CRY)
                }

                removeAttribute(player, MimeUtils.EMOTE_ATTRIBUTE)
                MimeUtils.getContinue(player)
            }

            return@on true
        }
    }
}