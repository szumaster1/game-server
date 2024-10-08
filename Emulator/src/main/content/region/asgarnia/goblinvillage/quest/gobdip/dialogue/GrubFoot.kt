package content.region.asgarnia.goblinvillage.quest.gobdip.dialogue

import core.api.getVarp
import core.api.setVarp
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Represents the different types of GrubFoot.
 */
enum class GrubFoot(val id: Int, val value: Int, val mail: Item) {
    /**
     * Normal.
     */
    NORMAL(4495, 1, Item(288)),

    /**
     * Orange.
     */
    ORANGE(4497, 4, Item(286)),

    /**
     * Blue.
     */
    BLUE(4498, 5, Item(287)),

    /**
     * Brown.
     */
    BROWN(4496, 6, Item(288));

    /**
     * Set config
     *
     * @param player the player.
     */
    fun setConfig(player: Player?) {
        setVarp(player!!, 62, value)
    }

    companion object {
        @JvmStatic
        fun forConfig(player: Player?): GrubFoot {
            val config = getVarp(player!!, 62)
            for (foot in values()) {
                if (foot.value == config) {
                    if (foot.ordinal + 1 >= values().size) {
                        return BROWN
                    }
                    return values()[foot.ordinal + 1]
                }
            }
            return NORMAL
        }
    }
}
