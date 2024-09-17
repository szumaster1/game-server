package content.region.kandarin.quest.chompybird.handlers

import core.api.*
import org.rs.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.tools.colorize

/**
 * Chompy bird hunting.
 *
 * @param id The ID of the Chompy bird hunting.
 * @param kills The number of kills required for the Chompy bird hunting.
 * @param rankName The rank name of the Chompy bird hunting.
 * @constructor Represents a Chompy bird hunting with the given ID, kills, and rank name.
 */
enum class ChompyBirdHunting(val id: Int, val kills: Int, val rankName: String) {
    /**
     * Ogre Bowman Chompy bird hunting.
     */
    O_BOWMAN(Items.CHOMPY_BIRD_HAT_2978, 30, "an Ogre Bowman"),

    /**
     * Bowman Chompy bird hunting.
     */
    BOWMAN(Items.CHOMPY_BIRD_HAT_2979, 40, "a Bowman"),

    /**
     * Ogre Yeoman Chompy bird hunting.
     */
    O_YEOMAN(Items.CHOMPY_BIRD_HAT_2980, 50, "an Ogre Yeoman"),

    /**
     * Yeoman Chompy bird hunting.
     */
    YEOMAN(Items.CHOMPY_BIRD_HAT_2981, 70, "a Yeoman"),

    /**
     * Ogre Marksman Chompy bird hunting.
     */
    O_MARKSMAN(Items.CHOMPY_BIRD_HAT_2982, 95, "an Ogre Marksman"),

    /**
     * Marksman Chompy bird hunting.
     */
    MARKSMAN(Items.CHOMPY_BIRD_HAT_2983, 125, "a Marksman"),

    /**
     * Ogre Woodsman Chompy bird hunting.
     */
    O_WOODSMAN(Items.CHOMPY_BIRD_HAT_2984, 170, "an Ogre Woodsman"),

    /**
     * Woodsman Chompy bird hunting.
     */
    WOODSMAN(Items.CHOMPY_BIRD_HAT_2985, 225, "a Woodsman"),

    /**
     * Ogre Forester Chompy bird hunting.
     */
    O_FORESTER(Items.CHOMPY_BIRD_HAT_2986, 300, "an Ogre Forester"),

    /**
     * Forester Chompy bird hunting.
     */
    FORESTER(Items.CHOMPY_BIRD_HAT_2987, 400, "a Forester"),

    /**
     * Ogre Bowmaster Chompy bird hunting.
     */
    O_BOWMASTER(Items.CHOMPY_BIRD_HAT_2988, 550, "an Ogre Bowmaster"),

    /**
     *  Bowmaster Chompy bird hunting.
     */
    BOWMASTER(Items.CHOMPY_BIRD_HAT_2989, 700, "a Bowmaster"),

    /**
     * Ogre Expert Chompy bird hunting.
     */
    O_EXPERT(Items.CHOMPY_BIRD_HAT_2990, 1000, "an Ogre Expert"),

    /**
     * Expert Chompy bird hunting.
     */
    EXPERT(Items.CHOMPY_BIRD_HAT_2991, 1300, "an Expert"),

    /**
     * Ogre Dragon Archer.
     */
    O_DA(Items.CHOMPY_BIRD_HAT_2992, 1700, "an Ogre Dragon Archer"),

    /**
     * Dragon Archer.
     */
    DA(Items.CHOMPY_BIRD_HAT_2993, 2250, "a Dragon Archer"),

    /**
     * Expert Ogre Dragon Archer.
     */
    EO_DA(Items.CHOMPY_BIRD_HAT_2994, 3000, "an Expert Ogre Dragon Archer"),

    /**
     * Expert Dragon Archer.
     */
    E_DA(Items.CHOMPY_BIRD_HAT_2995, 4000, "an Expert Dragon Archer");

    companion object {

        val killMap = values().map { it.kills to it }.toMap()

        /**
         * Checks if the player has reached a new rank based on
         * their number of kills.
         *
         * @param player The player to check.
         */
        fun checkForNewRank(player: Player) {
            val kills = getAttribute(player, "chompy-kills", 0)
            val newRank = killMap[kills] ?: return

            sendDialogueLines(
                player,
                colorize("%B*** Congratulations! $kills Chompies! ***"),
                colorize("%R~ You're ${newRank.rankName} ~")
            )
        }

        /**
         * Gets the applicable Chompy bird hunting hats for the player.
         *
         * @param player The player to get the hats for.
         * @return The list of applicable hat IDs.
         */
        fun getApplicableHats(player: Player): List<Int> {
            val kills = getAttribute(player, "chompy-kills", 0)
            val hats = ArrayList<Int>()
            for (hat in values()) {
                if (hat.kills > kills) break
                if (inInventory(player, hat.id) || inEquipment(player, hat.id) || inBank(player, hat.id)) continue
                hats.add(hat.id)
            }
            return hats
        }
    }
}

/**
 * Chompy equip listener.
 */
class ChompyEquipListener : InteractionListener {

    override fun defineListeners() {
        ChompyBirdHunting.values().forEach { hat ->
            onEquip(hat.id) { player, node ->
                val kills = getAttribute(player, "chompy-kills", 0)
                if (kills < hat.kills) {
                    sendItemDialogue(player, node.id, "You haven't earned this!")
                    removeItem(player, node.asItem())
                    return@onEquip false
                }
                return@onEquip true
            }
        }

        on(Items.COMP_OGRE_BOW_4827, IntType.ITEM, "check kills", "operate") { player, _ ->
            val kills = getAttribute(player, "chompy-kills", 0)
            sendDialogue(player, "You kill $kills chompy "+ (if(kills == 1) "bird" else "birds") + ".")
            return@on true
        }
    }
}
