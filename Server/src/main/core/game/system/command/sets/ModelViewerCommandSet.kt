package core.game.system.command.sets

import content.global.handlers.iface.BookInterfaceListener
import core.api.*
import core.game.node.entity.player.Player
import core.game.system.command.Privilege
import core.plugin.Initializable

@Initializable
class ModelViewerCommandSet : CommandSet(Privilege.ADMIN) {

    companion object {

        const val DEF_BOOK = 10216
        const val TITLE = "Model Viewer"
        const val ATTRIBUTE_MODEL_NUMBER = "modelNumber"
        const val ATTRIBUTE_ZOOM = "modelZoom"
        const val ATTRIBUTE_PITCH = "modelPitch"
        const val ATTRIBUTE_YAW = "modelYaw"

        private fun display(player: Player, pageNum: Int, buttonID: Int): Boolean {
            BookInterfaceListener.clearBookLines(player, BookInterfaceListener.FANCY_BOOK_2_27, BookInterfaceListener.FANCY_BOOK_2_27_LINE_IDS)
            BookInterfaceListener.clearButtons(player, BookInterfaceListener.FANCY_BOOK_2_27, BookInterfaceListener.FANCY_BOOK_2_27_BUTTON_IDS)
            BookInterfaceListener.setTitle(player, BookInterfaceListener.FANCY_BOOK_2_27, BookInterfaceListener.FANCY_BOOK_2_27_LINE_IDS, TITLE)

            // Custom button interfaces for model book.
            // These are non-standard. No pages are "turned" here.
            player.packetDispatch.sendInterfaceConfig(BookInterfaceListener.FANCY_BOOK_2_27, 114, false)
            player.packetDispatch.sendInterfaceConfig(BookInterfaceListener.FANCY_BOOK_2_27, 116, false)
            player.packetDispatch.sendInterfaceConfig(BookInterfaceListener.FANCY_BOOK_2_27, 118, false)
            player.packetDispatch.sendInterfaceConfig(BookInterfaceListener.FANCY_BOOK_2_27, 122, false)
            player.packetDispatch.sendInterfaceConfig(BookInterfaceListener.FANCY_BOOK_2_27, 124, false)
            player.packetDispatch.sendInterfaceConfig(BookInterfaceListener.FANCY_BOOK_2_27, 126, false)
            player.packetDispatch.sendInterfaceConfig(BookInterfaceListener.FANCY_BOOK_2_27, 128, false)
            player.packetDispatch.sendInterfaceConfig(BookInterfaceListener.FANCY_BOOK_2_27, 144, false)
            player.packetDispatch.sendInterfaceConfig(BookInterfaceListener.FANCY_BOOK_2_27, 146, false)
            player.packetDispatch.sendInterfaceConfig(BookInterfaceListener.FANCY_BOOK_2_27, 148, false)
            player.packetDispatch.sendInterfaceConfig(BookInterfaceListener.FANCY_BOOK_2_27, 152, false)
            player.packetDispatch.sendInterfaceConfig(BookInterfaceListener.FANCY_BOOK_2_27, 154, false)
            player.packetDispatch.sendInterfaceConfig(BookInterfaceListener.FANCY_BOOK_2_27, 156, false)
            player.packetDispatch.sendInterfaceConfig(BookInterfaceListener.FANCY_BOOK_2_27, 158, false)
            player.packetDispatch.sendString("-1 zoom", BookInterfaceListener.FANCY_BOOK_2_27, 114)
            player.packetDispatch.sendString("-1 pitch", BookInterfaceListener.FANCY_BOOK_2_27, 116)
            player.packetDispatch.sendString("-1 yaw", BookInterfaceListener.FANCY_BOOK_2_27, 118)
            player.packetDispatch.sendString("-1", BookInterfaceListener.FANCY_BOOK_2_27, 122)
            player.packetDispatch.sendString("-10", BookInterfaceListener.FANCY_BOOK_2_27, 124)
            player.packetDispatch.sendString("-100", BookInterfaceListener.FANCY_BOOK_2_27, 126)
            player.packetDispatch.sendString("-1000", BookInterfaceListener.FANCY_BOOK_2_27, 128)
            player.packetDispatch.sendString("+1 zoom", BookInterfaceListener.FANCY_BOOK_2_27, 144)
            player.packetDispatch.sendString("+1 pitch", BookInterfaceListener.FANCY_BOOK_2_27, 146)
            player.packetDispatch.sendString("+1 yaw", BookInterfaceListener.FANCY_BOOK_2_27, 148)
            player.packetDispatch.sendString("+1", BookInterfaceListener.FANCY_BOOK_2_27, 152)
            player.packetDispatch.sendString("+10", BookInterfaceListener.FANCY_BOOK_2_27, 154)
            player.packetDispatch.sendString("+100", BookInterfaceListener.FANCY_BOOK_2_27, 156)
            player.packetDispatch.sendString("+1000", BookInterfaceListener.FANCY_BOOK_2_27, 158)

            // Attach buttons to setAttributes.
            when (buttonID) {
                114 -> setAttribute(player, ATTRIBUTE_ZOOM, getAttribute(player, ATTRIBUTE_ZOOM, 700) - 100)
                116 -> setAttribute(player, ATTRIBUTE_PITCH, getAttribute(player, ATTRIBUTE_PITCH, 0) - 100)
                118 -> setAttribute(player, ATTRIBUTE_YAW, getAttribute(player, ATTRIBUTE_YAW, 0) - 100)
                122 -> setAttribute(player, ATTRIBUTE_MODEL_NUMBER, getAttribute(player, ATTRIBUTE_MODEL_NUMBER, DEF_BOOK) - 1)
                124 -> setAttribute(player, ATTRIBUTE_MODEL_NUMBER, getAttribute(player, ATTRIBUTE_MODEL_NUMBER, DEF_BOOK) - 10)
                126 -> setAttribute(player, ATTRIBUTE_MODEL_NUMBER, getAttribute(player, ATTRIBUTE_MODEL_NUMBER, DEF_BOOK) - 100)
                128 -> setAttribute(player, ATTRIBUTE_MODEL_NUMBER, getAttribute(player, ATTRIBUTE_MODEL_NUMBER, DEF_BOOK) - 1000)
                144 -> setAttribute(player, ATTRIBUTE_ZOOM, getAttribute(player, ATTRIBUTE_ZOOM, 700) + 100)
                146 -> setAttribute(player, ATTRIBUTE_PITCH, getAttribute(player, ATTRIBUTE_PITCH, 0) + 100)
                148 -> setAttribute(player, ATTRIBUTE_YAW, getAttribute(player, ATTRIBUTE_YAW, 0) + 100)
                152 -> setAttribute(player, ATTRIBUTE_MODEL_NUMBER, getAttribute(player, ATTRIBUTE_MODEL_NUMBER, DEF_BOOK) + 1)
                154 -> setAttribute(player, ATTRIBUTE_MODEL_NUMBER, getAttribute(player, ATTRIBUTE_MODEL_NUMBER, DEF_BOOK) + 10)
                156 -> setAttribute(player, ATTRIBUTE_MODEL_NUMBER, getAttribute(player, ATTRIBUTE_MODEL_NUMBER, DEF_BOOK) + 100)
                158 -> setAttribute(player, ATTRIBUTE_MODEL_NUMBER, getAttribute(player, ATTRIBUTE_MODEL_NUMBER, DEF_BOOK) + 1000)
            }

            // Display model number.
            player.packetDispatch.sendString("No: " + getAttribute(player, ATTRIBUTE_MODEL_NUMBER, DEF_BOOK) + "   " + getAttribute(player, ATTRIBUTE_ZOOM, 700) + " " + getAttribute(player, ATTRIBUTE_PITCH, 0) + " " + getAttribute(player, ATTRIBUTE_YAW, 0), BookInterfaceListener.FANCY_BOOK_2_27, 38)
            player.packetDispatch.sendString("No: " + (getAttribute(player, ATTRIBUTE_MODEL_NUMBER, DEF_BOOK) + 1), BookInterfaceListener.FANCY_BOOK_2_27, 53)

            // Display the models in the middle.
            BookInterfaceListener.setModelOnPage(
                player,
                0,
                getAttribute(player, ATTRIBUTE_MODEL_NUMBER, DEF_BOOK),
                BookInterfaceListener.FANCY_BOOK_2_27,
                BookInterfaceListener.FANCY_BOOK_2_27_IMAGE_ENABLE_DRAW_IDS[7],
                BookInterfaceListener.FANCY_BOOK_2_27_IMAGE_DRAW_IDS[7],
                getAttribute(player, ATTRIBUTE_ZOOM, 700),
                getAttribute(player, ATTRIBUTE_PITCH, 0),
                getAttribute(player, ATTRIBUTE_YAW, 0)
            )
            BookInterfaceListener.setModelOnPage(
                player,
                0,
                getAttribute(player, ATTRIBUTE_MODEL_NUMBER, DEF_BOOK) + 1,
                BookInterfaceListener.FANCY_BOOK_2_27,
                BookInterfaceListener.FANCY_BOOK_2_27_IMAGE_ENABLE_DRAW_IDS[22],
                BookInterfaceListener.FANCY_BOOK_2_27_IMAGE_DRAW_IDS[22],
                getAttribute(player, ATTRIBUTE_ZOOM, 700),
                getAttribute(player, ATTRIBUTE_PITCH, 0),
                getAttribute(player, ATTRIBUTE_YAW, 0)
            )

            return true
        }
    }

    override fun defineCommands() {
        define("models") { player, args ->

            // Bad number of args.
            if (args.size > 2) {
                reject(player, "Usage: ::models")
                return@define
            }

            BookInterfaceListener.openBook(player, BookInterfaceListener.FANCY_BOOK_2_27, ::display)
            return@define
        }
    }
}
