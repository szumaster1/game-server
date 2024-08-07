package content.global.skill.gathering.fishing

import content.global.skill.gathering.fishing.data.Fish
import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction

/**
 * Fishing option
 *
 * @property tool
 * @property level
 * @property animation
 * @property bait
 * @property option
 * @property fish
 * @constructor Fishing option
 */
enum class FishingOption(
    val tool: Int,
    val level: Int,
    val animation: Animation,
    val bait: IntArray?,
    val option: String,
    vararg val fish: Fish
) {
    /**
     * Crayfish Cage.
     */
    CRAYFISH_CAGE(
        tool = Items.CRAYFISH_CAGE_13431,
        level = 1,
        animation = Animation(Animations.USING_CRAYFISH_CAGE_10009),
        bait = null,
        option = "cage",
        Fish.CRAYFISH
    ),

    /**
     * Small Net.
     */
    SMALL_NET(
        tool = Items.SMALL_FISHING_NET_303,
        level = 1,
        animation = Animation(Animations.NET_FISHING_621),
        bait = null,
        option = "net",
        Fish.SHRIMP, Fish.ANCHOVIE
    ),

    /**
     * Bait.
     */
    BAIT(
        tool = Items.FISHING_ROD_307,
        level = 5,
        animation = Animation(Animations.ROD_FISHING_622),
        bait = intArrayOf(Items.FISHING_BAIT_313),
        option = "bait",
        Fish.SARDINE, Fish.HERRING
    ),

    /**
     * Lure.
     */
    LURE(
        tool = Items.FLY_FISHING_ROD_309,
        level = 20,
        animation = Animation(Animations.ROD_FISHING_622),
        bait = intArrayOf(Items.FEATHER_314, Items.STRIPY_FEATHER_10087),
        option = "lure",
        Fish.TROUT, Fish.SALMON, Fish.RAINBOW_FISH
    ),

    /**
     * Pike Bait.
     */
    PIKE_BAIT(
        tool = Items.FISHING_ROD_307,
        level = 25,
        animation = Animation(Animations.ROD_FISHING_622),
        bait = intArrayOf(Items.FISHING_BAIT_313),
        option = "bait",
        Fish.PIKE
    ),

    /**
     * Lobster Cage.
     */
    LOBSTER_CAGE(
        tool = Items.LOBSTER_POT_301,
        level = 40,
        animation = Animation(Animations.LOBSTER_CAGE_FISHING_619),
        bait = null,
        option = "cage",
        Fish.LOBSTER
    ),

    /**
     * Harpoon.
     */
    HARPOON(
        tool = Items.HARPOON_311,
        level = 35,
        animation = Animation(Animations.HARPOON_FISHING_618),
        bait = null,
        option = "harpoon",
        Fish.TUNA, Fish.SWORDFISH
    ),

    /**
     * Barb Harpoon.
     */
    BARB_HARPOON(
        tool = Items.BARB_TAIL_HARPOON_10129,
        level = 35,
        animation = Animation(Animations.HARPOON_FISHING_618),
        bait = null,
        option = "harpoon",
        Fish.TUNA, Fish.SWORDFISH
    ),

    /**
     * Big Net.
     */
    BIG_NET(
        tool = Items.BIG_FISHING_NET_305,
        level = 16,
        animation = Animation(Animations.NET_FISHING_620),
        bait = null,
        option = "net",
        Fish.MACKEREL, Fish.COD, Fish.BASS, Fish.SEAWEED
    ),

    /**
     * Shark Harpoon.
     */
    SHARK_HARPOON(
        tool = Items.HARPOON_311,
        level = 76,
        animation = Animation(Animations.HARPOON_FISHING_618),
        bait = null,
        option = "harpoon",
        Fish.SHARK
    ),

    /**
     * Monkfish Net.
     */
    MONKFISH_NET(
        tool = Items.SMALL_FISHING_NET_303,
        level = 62,
        animation = Animation(Animations.NET_FISHING_621),
        bait = null,
        option = "net",
        Fish.MONKFISH
    ),

    /**
     * Mortmyre Rod.
     */
    MORTMYRE_ROD(
        tool = Items.FISHING_ROD_307,
        level = 5,
        animation = Animation(Animations.ROD_FISHING_622),
        bait = intArrayOf(Items.FISHING_BAIT_313),
        option = "bait",
        Fish.SLIMY_EEL
    ),

    /**
     * Lumbdswamp Rod.
     */
    LUMBDSWAMP_ROD(
        tool = Items.FISHING_ROD_307,
        level = 5,
        animation = Animation(Animations.ROD_FISHING_622),
        bait = intArrayOf(Items.FISHING_BAIT_313),
        option = "bait",
        Fish.SLIMY_EEL, Fish.FROG_SPAWN
    ),

    /**
     * Kbwanji Net.
     */
    KBWANJI_NET(
        tool = Items.SMALL_FISHING_NET_303, level = 5,
        animation = Animation(Animations.NET_FISHING_621),
        bait = null,
        option = "net",
        Fish.KARAMBWANJI
    ),

    /**
     * Karambwan Ves.
     */
    KARAMBWAN_VES(
        tool = Items.KARAMBWAN_VESSEL_3157,
        level = 65,
        animation = Animation(Animations.FISHING_WITH_KARAMBWAN_VESSEL_1193),
        bait = intArrayOf((Items.RAW_KARAMBWANJI_3150)),
        option = "fish",
        Fish.KARAMBWAN
    ),

    /**
     * Oily Fishing Rod.
     */
    OILY_FISHING_ROD(
        tool = Items.OILY_FISHING_ROD_1585,
        level = 53,
        animation = Animation(Animations.ROD_FISHING_622),
        bait = intArrayOf(Items.FISHING_BAIT_313),
        option = "bait",
        Fish.LAVA_EEL
    );

    companion object {
        private val nameMap: HashMap<String, FishingOption> = HashMap()

        init {
            for (value in values()) {
                nameMap[value.option] = value
            }
        }

        fun forName(opName: String): FishingOption? {
            return nameMap[opName]
        }
    }

    /**
     * Roll fish
     *
     * @param player
     * @return
     */
    fun rollFish(player: Player): Fish? {
        if (this == BIG_NET) {
            when (RandomFunction.randomize(100)) {
                0 -> return Fish.OYSTER
                50 -> return Fish.CASKET
                90 -> return Fish.SEAWEED
            }
        }
        val vlvl = getDynLevel(player, Skills.FISHING)
        val ilvl = vlvl + player.familiarManager.getBoost(Skills.FISHING)
        for (f in fish) {
            if (f.level > vlvl) {
                continue
            }
            if (this == LURE && inInventory(player, Items.STRIPY_FEATHER_10087) != (f == Fish.RAINBOW_FISH)) {
                continue
            }
            val chance = f.getSuccessChance(ilvl)
            if (RandomFunction.random(0.0, 1.0) < chance) {
                return f
            }
        }
        return null
    }

    /**
     * Get bait name
     *
     * @return
     */
    fun getBaitName(): String {
        if (bait != null && bait.isNotEmpty()) {
            return getItemName(bait[0])
        }
        return "none"
    }

    /**
     * Has bait
     *
     * @param player
     * @return
     */
    fun hasBait(player: Player): Boolean {
        return if (bait == null) true else {
            var anyBait = false
            for (b in bait) {
                anyBait = anyBait || inInventory(player, b)
            }
            anyBait
        }
    }

    /**
     * Remove bait
     *
     * @param player
     * @return
     */
    fun removeBait(player: Player): Boolean {
        return if (bait == null) {
            true
        } else {
            for (i in bait.size downTo 1) {
                if (removeItem(player, bait[i - 1], Container.INVENTORY)) {
                    return true
                }
            }
            false
        }
    }

    /**
     * Get start message
     *
     * @return
     */
    fun getStartMessage(): String {
        return if (option == "net")
            "You cast out your net..."
        else
            "You attempt to catch a fish."
    }
}
