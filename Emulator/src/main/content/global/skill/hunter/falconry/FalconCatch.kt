package content.global.skill.hunter.falconry

import core.game.node.entity.npc.NPC
import core.game.node.item.Item

/**
 * Represents a falcon catch.
 */
enum class FalconCatch(val npc: Int, val level: Int, val experience: Double, val item: Item) {
    SPOTTED_KEBBIT(5098, 43, 104.0, Item(10125)),
    DARK_KEBBIT(5099, 57, 132.0, Item(10115)),
    DASHING_KEBBIT(5100, 69, 156.0, Item(10127));

    companion object {
        /**
         * Gets the falcon catch.
         *
         * @param item the item.
         * @return the falcon catch.
         */
        @JvmStatic
        fun forItem(item: Item): FalconCatch? {
            for (falconCatch in values()) {
                if (item.id == falconCatch.item.id) {
                    return falconCatch
                }
            }
            return null
        }

        /**
         * Gets the falcon catch.
         *
         * @param npc the npc.
         * @return the falcon catch.
         */
        @JvmStatic
        fun forNPC(npc: NPC): FalconCatch? {
            for (falconCatch in values()) {
                if (npc.id == falconCatch.npc) {
                    return falconCatch
                }
            }
            return null
        }
    }
}