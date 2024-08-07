package content.region.kandarin.quest.chompybird

import core.api.*
import core.api.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.tools.colorize

/**
 * Chompy bird hunting
 *
 * @property id
 * @property kills
 * @property rankName
 * @constructor Chompy bird hunting
 */
enum class ChompyBirdHunting(val id: Int, val kills: Int, val rankName: String) {
    /**
     * O Bowman
     *
     * @constructor O Bowman
     */
    O_BOWMAN(Items.CHOMPY_BIRD_HAT_2978, 30, "an Ogre Bowman"),

    /**
     * Bowman
     *
     * @constructor Bowman
     */
    BOWMAN(Items.CHOMPY_BIRD_HAT_2979, 40, "a Bowman"),

    /**
     * O Yeoman
     *
     * @constructor O Yeoman
     */
    O_YEOMAN(Items.CHOMPY_BIRD_HAT_2980, 50, "an Ogre Yeoman"),

    /**
     * Yeoman
     *
     * @constructor Yeoman
     */
    YEOMAN(Items.CHOMPY_BIRD_HAT_2981, 70, "a Yeoman"),

    /**
     * O Marksman
     *
     * @constructor O Marksman
     */
    O_MARKSMAN(Items.CHOMPY_BIRD_HAT_2982, 95, "an Ogre Marksman"),

    /**
     * Marksman
     *
     * @constructor Marksman
     */
    MARKSMAN(Items.CHOMPY_BIRD_HAT_2983, 125, "a Marksman"),

    /**
     * O Woodsman
     *
     * @constructor O Woodsman
     */
    O_WOODSMAN(Items.CHOMPY_BIRD_HAT_2984, 170, "an Ogre Woodsman"),

    /**
     * Woodsman
     *
     * @constructor Woodsman
     */
    WOODSMAN(Items.CHOMPY_BIRD_HAT_2985, 225, "a Woodsman"),

    /**
     * O Forester
     *
     * @constructor O Forester
     */
    O_FORESTER(Items.CHOMPY_BIRD_HAT_2986, 300, "an Ogre Forester"),

    /**
     * Forester
     *
     * @constructor Forester
     */
    FORESTER(Items.CHOMPY_BIRD_HAT_2987, 400, "a Forester"),

    /**
     * O Bowmaster
     *
     * @constructor O Bowmaster
     */
    O_BOWMASTER(Items.CHOMPY_BIRD_HAT_2988, 550, "an Ogre Bowmaster"),

    /**
     * Bowmaster
     *
     * @constructor Bowmaster
     */
    BOWMASTER(Items.CHOMPY_BIRD_HAT_2989, 700, "a Bowmaster"),

    /**
     * O Expert
     *
     * @constructor O Expert
     */
    O_EXPERT(Items.CHOMPY_BIRD_HAT_2990, 1000, "an Ogre Expert"),

    /**
     * Expert
     *
     * @constructor Expert
     */
    EXPERT(Items.CHOMPY_BIRD_HAT_2991, 1300, "an Expert"),

    /**
     * O Da
     *
     * @constructor O Da
     */
    O_DA(Items.CHOMPY_BIRD_HAT_2992, 1700, "an Ogre Dragon Archer"),

    /**
     * Da
     *
     * @constructor Da
     */
    DA(Items.CHOMPY_BIRD_HAT_2993, 2250, "a Dragon Archer"),

    /**
     * Eo Da
     *
     * @constructor Eo Da
     */
    EO_DA(Items.CHOMPY_BIRD_HAT_2994, 3000, "an Expert Ogre Dragon Archer"),

    /**
     * E Da
     *
     * @constructor E Da
     */
    E_DA(Items.CHOMPY_BIRD_HAT_2995, 4000, "an Expert Dragon Archer");

    companion object {

        val killMap = values().map { it.kills to it }.toMap()

        fun checkForNewRank(player: Player) {
            val kills = getAttribute(player, "chompy-kills", 0)
            val newRank = killMap[kills] ?: return

            sendDialogueLines(
                player,
                colorize("%B*** Congratulations! $kills Chompies! ***"),
                colorize("%R~ You're ${newRank.rankName} ~")
            )
        }

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
 * Chompy equip listener
 *
 * @constructor Chompy equip listener
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
