package content.data

import core.game.node.entity.player.Player
import core.tools.StringUtils
import org.rs.consts.NPCs

/**
 * Keeps track of the amount of bosses the player has slain.
 */
enum class BossKillCounter(val npc: IntArray, val bossName: String) {
    KING_BLACK_DRAGON(
        npc = intArrayOf(NPCs.KING_BLACK_DRAGON_50),
        bossName = "King Black Dragon"
    ),
    BORK(
        npc = intArrayOf(NPCs.BORK_7133, NPCs.BORK_7134),
        bossName = "Bork"
    ),
    DAGANNOTH_SUPREME(
        npc = intArrayOf(NPCs.DAGANNOTH_SUPREME_2881),
        bossName = "Dagannoth Supreme"
    ),
    DAGANNOTH_PRIME(
        npc = intArrayOf(NPCs.DAGANNOTH_PRIME_2882),
        bossName = "Dagannoth Prime"
    ),
    DAGANNOTH_REX(
        npc = intArrayOf(NPCs.DAGANNOTH_REX_2883),
        bossName = "Dagannoth Rex"
    ),
    CHAOS_ELEMENTAL(
        npc = intArrayOf(NPCs.CHAOS_ELEMENTAL_3200),
        bossName = "Chaos Elemental"
    ),
    GIANT_MOLE(
        npc = intArrayOf(NPCs.GIANT_MOLE_3340),
        bossName = "Giant Mole"
    ),
    SARADOMIN(
        npc = intArrayOf(NPCs.COMMANDER_ZILYANA_6247),
        bossName = "Commander Zilyana"
    ),
    ZAMORAK(
        npc = intArrayOf(NPCs.KRIL_TSUTSAROTH_6203),
        bossName = "K'ril Tsutsaroth"
    ),
    BANDOS(
        npc = intArrayOf(NPCs.GENERAL_GRAARDOR_6260),
        bossName = "General Graardor"
    ),
    ARMADYL(
        npc = intArrayOf(NPCs.KREEARRA_6222),
        bossName = "Kree'arra"
    ),
    JAD(
        npc = intArrayOf(NPCs.TZTOK_JAD_2745),
        bossName = "Tz-Tok Jad"
    ),
    KALPHITE_QUEEN(
        npc = intArrayOf(NPCs.KALPHITE_QUEEN_1160),
        bossName = "Kalphite Queen"
    ),
    CORPOREAL_BEAST(
        npc = intArrayOf(NPCs.CORPOREAL_BEAST_8133),
        bossName = "Corporeal Beast"
    ),
    TORMENTED_DEMONS(
        npc = intArrayOf(NPCs.TORMENTED_DEMON_8349, NPCs.TORMENTED_DEMON_8350, NPCs.TORMENTED_DEMON_8351, NPCs.TORMENTED_DEMON_8352, NPCs.TORMENTED_DEMON_8353, NPCs.TORMENTED_DEMON_8354, NPCs.TORMENTED_DEMON_8355, NPCs.TORMENTED_DEMON_8356, NPCs.TORMENTED_DEMON_8357, NPCs.TORMENTED_DEMON_8358, NPCs.TORMENTED_DEMON_8359, NPCs.TORMENTED_DEMON_8360, NPCs.TORMENTED_DEMON_8361, NPCs.TORMENTED_DEMON_8362, NPCs.TORMENTED_DEMON_8363, NPCs.TORMENTED_DEMON_8364, NPCs.TORMENTED_DEMON_8365, NPCs.TORMENTED_DEMON_8366),
        bossName = "Tormented demon"
    );

    companion object {
        /**
         * Gets the counter for given npc.
         *
         * @param [npc] the npc.
         * @return the **kill count** or `null` if not found.
         */
        @JvmStatic
        fun forNPC(npc: Int): BossKillCounter? {
            for (kc in values()) {
                for (i in kc.npc) {
                    if (npc == i) {
                        return kc
                    }
                }
            }
            return null
        }

        /**
         * Add kill to player stats.
         */
        @JvmStatic
        fun addtoKillcount(killer: Player?, npcid: Int) {
            if (killer == null) {
                return
            }
            val boss = BossKillCounter.forNPC(npcid) ?: return
            killer.getSavedData().globalData.getBossCounters()[boss.ordinal]++
            killer.packetDispatch.sendMessage("Your " + StringUtils.formatDisplayName(boss.name) + " killcount is now: <col=ff0000>" + killer.getSavedData().globalData.getBossCounters()[boss.ordinal] + "</col>.")
        }

        /**
         * Add barrows chest to player stats.
         */
        @JvmStatic
        fun addtoBarrowsCount(player: Player?) {
            if (player == null) {
                return
            }
            player.getSavedData().globalData.setBarrowsLoots(player.getSavedData().globalData.getBarrowsLoots() + 1)
            player.packetDispatch.sendMessage("Your Barrows chest count is: <col=ff0000>" + player.getSavedData().globalData.getBarrowsLoots() + "</col>.")
        }
    }
}