package content.data

import core.api.consts.NPCs
import core.game.node.entity.player.Player
import core.tools.StringUtils

/**
 * Keeps track of the amount of bosses the player has slain.
 * addtoKillcount(player, npcId) should be added in the finalizeDeath() method of the combat handler for the boss.
 * @author Splinter
 *
 * @param npc      the npc `intArrayOf(NPCs.ID)`.
 * @param bossName the boss name `toString()`.
 */
enum class BossKillCounter(val npc: IntArray, val bossName: String) {
    /**
     * King Black Dragon.
     */
    KING_BLACK_DRAGON(npc = intArrayOf(NPCs.KING_BLACK_DRAGON_50), bossName = "King Black Dragon"),

    /**
     * Bork.
     */
    BORK(npc = intArrayOf(NPCs.BORK_7133, NPCs.BORK_7134), bossName = "Bork"),

    /**
     * Dagannoth Supreme.
     */
    DAGANNOTH_SUPREME(npc = intArrayOf(NPCs.DAGANNOTH_SUPREME_2881), bossName = "Dagannoth Supreme"),

    /**
     * Dagannoth Prime.
     */
    DAGANNOTH_PRIME(npc = intArrayOf(NPCs.DAGANNOTH_PRIME_2882), bossName = "Dagannoth Prime"),

    /**
     * Dagannoth Rex.
     */
    DAGANNOTH_REX(npc = intArrayOf(NPCs.DAGANNOTH_REX_2883), bossName = "Dagannoth Rex"),

    /**
     * Chaos Elemental.
     */
    CHAOS_ELEMENTAL(npc = intArrayOf(NPCs.CHAOS_ELEMENTAL_3200), bossName = "Chaos Elemental"),

    /**
     * Giant Mole.
     */
    GIANT_MOLE(npc = intArrayOf(NPCs.GIANT_MOLE_3340), bossName = "Giant Mole"),

    /**
     * Saradomin.
     */
    SARADOMIN(npc = intArrayOf(NPCs.COMMANDER_ZILYANA_6247), bossName = "Commander Zilyana"),

    /**
     * Zamorak.
     */
    ZAMORAK(npc = intArrayOf(NPCs.KRIL_TSUTSAROTH_6203), bossName = "K'ril Tsutsaroth"),

    /**
     * Bandos.
     */
    BANDOS(npc = intArrayOf(NPCs.GENERAL_GRAARDOR_6260), bossName = "General Graardor"),

    /**
     * Armadyl.
     */
    ARMADYL(npc = intArrayOf(NPCs.KREEARRA_6222), bossName = "Kree'arra"),

    /**
     * Jad.
     */
    JAD(npc = intArrayOf(NPCs.TZTOK_JAD_2745), bossName = "Tz-Tok Jad"),

    /**
     * Kalphite Queen.
     */
    KALPHITE_QUEEN(npc = intArrayOf(NPCs.KALPHITE_QUEEN_1160), bossName = "Kalphite Queen"),

    /**
     * Corporeal Beast.
     */
    CORPOREAL_BEAST(npc = intArrayOf(NPCs.CORPOREAL_BEAST_8133), bossName = "Corporeal Beast"),

    /**
     * Tormented Demons.
     */
    TORMENTED_DEMONS(
        npc = intArrayOf(
            NPCs.TORMENTED_DEMON_8349,
            NPCs.TORMENTED_DEMON_8350,
            NPCs.TORMENTED_DEMON_8351,
            NPCs.TORMENTED_DEMON_8352,
            NPCs.TORMENTED_DEMON_8353,
            NPCs.TORMENTED_DEMON_8354,
            NPCs.TORMENTED_DEMON_8355,
            NPCs.TORMENTED_DEMON_8356,
            NPCs.TORMENTED_DEMON_8357,
            NPCs.TORMENTED_DEMON_8358,
            NPCs.TORMENTED_DEMON_8359,
            NPCs.TORMENTED_DEMON_8360,
            NPCs.TORMENTED_DEMON_8361,
            NPCs.TORMENTED_DEMON_8362,
            NPCs.TORMENTED_DEMON_8363,
            NPCs.TORMENTED_DEMON_8364,
            NPCs.TORMENTED_DEMON_8365,
            NPCs.TORMENTED_DEMON_8366
        ), bossName = "Tormented demon"
    );

    companion object {
        /**
         * Returns the BossKillCounter enum for the given NPC ID.
         *
         * @param npc the NPC ID.
         * @return the BossKillCounter enum or null if not found.
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
         * Adds the kill count for the boss to the player's saved data.
         *
         * @param killer the player who killed the boss.
         * @param npcid the NPC ID of the boss.
         */
        @JvmStatic
        fun addtoKillcount(killer: Player?, npcid: Int) {
            if (killer == null) {
                return
            }
            val boss = BossKillCounter.forNPC(npcid) ?: return
            killer.getSavedData().globalData.getBossCounters()[boss.ordinal]++;
            killer.packetDispatch.sendMessage("Your " + StringUtils.formatDisplayName(boss.name) + " killcount is now: <col=ff0000>" + killer.getSavedData().globalData.getBossCounters()[boss.ordinal] + "</col>.");
        }

        /**
         * Adds to the Barrows chest count for the player's saved data.
         *
         * @param player the player.
         */
        @JvmStatic
        fun addtoBarrowsCount(player: Player?) {
            if (player == null) {
                return
            }
            player.getSavedData().globalData.setBarrowsLoots(player.getSavedData().globalData.getBarrowsLoots() + 1);
            player.packetDispatch.sendMessage("Your Barrows chest count is: <col=ff0000>" + player.getSavedData().globalData.getBarrowsLoots() + "</col>.");
        }
    }
}

/*
 *  addBossPet(killer, npcid, boss);
 *
 *	/**
 *	 * Gives the player the pet if they killed a certain boss.
 *	 * The chance by default is 1/5000. This rate lowers to 1/2200 if the <GlobalEvents> for Boss Pets is active.
 *	 * Note: Not all bosses have pet versions of themselves.
 *	 */
 *	private static void addBossPet(Player killer, int npcid, BossKillCounter boss){
 *		if(boss.getPetId() == -1){ //The boss does not have a pet version.
 *			return;
 *		}
 *		int number = 5000;
 *		if (npcid == 2745) {
 *			number = 200;
 *			if (killer.getSlayer().getTask() == Tasks.JAD) {
 *				number = 100;
 *			}
 *		} else if (npcid == 3200) {
 *			number = 300;
 *		}
 *		int rand = number;
 *		if(rand == 10){
 *			for (int i = 0; i < killer.getFamiliarManager().getInsuredPets().size(); i++) {
 *				if (killer.getFamiliarManager().getInsuredPets().get(i).getBabyItemId() == boss.getPetId()) {
 *					return;
 *				}
 *			}
 *			if(killer.getFamiliarManager().hasFamiliar() && killer.getInventory().freeSlots() < 1){
 *				return;
 *			}
 *			if(!killer.getFamiliarManager().hasFamiliar()){
 *				killer.getFamiliarManager().summon(new Item(boss.getPetId()),  true);
 *				killer.sendNotificationMessage("You have a funny feeling like you're being followed.");
 *			} else if (killer.getInventory().freeSlots() > 0){
 *				killer.getInventory().add(new Item(boss.getPetId(), 1));
 *				killer.sendNotificationMessage("You feel something weird sneaking into your backpack.");
 *			}
 *			Repository.sendNews(killer.getUsername()+" now commands a miniature "+(boss.equals(CORPOREAL_BEAST) ? "Dark core" : boss.getName())+"!");
 *		}
 *	}
 */