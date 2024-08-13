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
 * @param tool Represents the tool required for fishing.
 * @param level Indicates the level of fishing skill needed.
 * @param animation Defines the animation associated with the fishing action.
 * @param bait Specifies the bait used for fishing, if any.
 * @param option Describes the fishing option in a string format.
 * @param fish Represents the various fish that can be caught with this option.
 * @constructor Fishing option Represents a new FishingOption with the specified parameters.
 */
enum class FishingOption(
    val tool: Int, // The integer value representing the fishing tool.
    val level: Int, // The integer value representing the required fishing level.
    val animation: Animation, // The Animation object that defines the fishing action's animation.
    val bait: IntArray?, // An optional array of integers representing the bait types.
    val option: String, // A string that describes the fishing option.
    vararg val fish: Fish // A variable number of Fish objects that can be caught with this option.
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
     * @return The name of the first bait item if available, otherwise "none".
     */
    fun getBaitName(): String {
        // Check if bait is not null and not empty
        if (bait != null && bait.isNotEmpty()) {
            // Return the name of the first bait item
            return getItemName(bait[0])
        }
        // Return "none" if no bait is available
        return "none"
    }

    /**
     * Has bait
     *
     * @param player The player whose inventory is checked for bait.
     * @return True if the player has any bait, otherwise false.
     */
    fun hasBait(player: Player): Boolean {
        // If bait is null, return true indicating the player has no bait
        return if (bait == null) true else {
            var anyBait = false // Initialize a flag to check for any bait
            // Iterate through each bait item
            for (b in bait) {
                // Check if the player has the current bait in their inventory
                anyBait = anyBait || inInventory(player, b)
            }
            // Return true if any bait was found in the player's inventory
            anyBait
        }
    }

    /**
     * Remove bait
     *
     * @param player The player from whom bait will be removed.
     * @return True if any bait was successfully removed, otherwise false.
     */
    fun removeBait(player: Player): Boolean {
        // If bait is null, return true indicating nothing to remove
        return if (bait == null) {
            true
        } else {
            // Iterate through the bait list in reverse order
            for (i in bait.size downTo 1) {
                // Attempt to remove the current bait item from the player's inventory
                if (removeItem(player, bait[i - 1], Container.INVENTORY)) {
                    // Return true if an item was successfully removed
                    return true
                }
            }
            // Return false if no items were removed
            false
        }
    }

    /**
     * Get start message
     *
     * @return A message indicating the action taken when starting to fish.
     */
    fun getStartMessage(): String {
        // Return a specific message based on the selected option
        return if (option == "net")
            "You cast out your net..."
        else
            "You attempt to catch a fish."
    }
}
