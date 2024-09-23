package content.region.misthalin.lumbridge.handlers.gnomecopter

import core.game.component.Component
import core.game.node.entity.player.Player

/**
 * Gnome copter sign.
 *
 * @param button the button (string).
 * @param info the info (string).
 */
enum class GnomecopterSign(private val button: String, vararg info: String) {

    /*
     * Entrance text content.
     */
    ENTRANCE(
        "~ Gnomecopter Tours ~",
        "Welcome to Gnomecopter",
        "Tours: the unique flying",
        "experience!",
        "",
        "If you're a new flier, talk to",
        "<col=FF0000>Hieronymus</col> and prepare to be",
        "amazed by this triumph of",
        "gnomic engineering.",
        "",
        "",
        "Warning: all riders must be at",
        "least 2ft, 6ins tall."
    );

    private val info: Array<String> = info as Array<String>

    /**
     * Handle read option.
     *
     * @param player the player.
     */
    fun read(player: Player) {
        player.interfaceManager.openSingleTab(Component(723))
        player.packetDispatch.sendString(button, 723, 9)
        var information = info[0]
        for (i in 1 until info.size) {
            information += "<br>" + info[i]
        }
        player.packetDispatch.sendString(information, 723, 10)
    }
}