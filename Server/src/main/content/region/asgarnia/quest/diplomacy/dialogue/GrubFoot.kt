package content.region.asgarnia.quest.diplomacy.dialogue

import core.api.getVarp
import core.api.setVarp
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Grub foot
 *
 * @property id
 * @property value
 * @property mail
 * @constructor Grub foot
 */
enum class GrubFoot(val id: Int, val value: Int, val mail: Item) {
    /**
     * Normal
     *
     * @constructor Normal
     */
    NORMAL(4495, 1, Item(288)),

    /**
     * Orange
     *
     * @constructor Orange
     */
    ORANGE(4497, 4, Item(286)),

    /**
     * Blue
     *
     * @constructor Blue
     */
    BLUE(4498, 5, Item(287)),

    /**
     * Brown
     *
     * @constructor Brown
     */
    BROWN(4496, 6, Item(288));

    /**
     * Set config
     *
     * @param player
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
