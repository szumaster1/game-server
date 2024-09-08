package content.global.guilds.runecrafting

import cfg.consts.Items
import content.global.skill.production.runecrafting.data.Talisman
import core.api.*
import core.game.node.entity.player.Player

/**
 * Utility object for Runecrafting Guild functionalities.
 */
object RCGUtils {

    /*
     * Constant string for showing Elriss items.
     */
    const val SHOW_ELRISS_ITEMS = "/save:rcguild:omni-talisman"

    /*
     * Constant string for accessing omni items.
     */
    const val ACCESS_TO_OMNI_ITEMS = "/save:rcguild:omni-access"

    const val AIR_ITEMS = "air"
    const val MIND_ITEMS = "mind"
    const val WATER_ITEMS = "water"
    const val EARTH_ITEMS = "earth"
    const val FIRE_ITEMS = "fire"
    const val BODY_ITEMS = "body"
    const val COSMIC_ITEMS = "cosmic"
    const val CHAOS_ITEMS = "chaos"
    const val NATURE_ITEMS = "nature"
    const val LAW_ITEMS = "law"
    const val DEATH_ITEMS = "death"
    const val BLOOD_ITEMS = "blood"

    val TALLYS = Talisman.values().map { it.talisman.id }.toIntArray()

    /**
     * Function to check if the player has all required items to receive a reward.
     *
     * Related with [WizardElrissDialogue][content.region.misthalin.handlers.guild.runecrafting.dialogue.WizardElrissDialogue]
     */
    fun checkReward(player: Player) {
        // List of required items for the reward
        val requiredItems = listOf(
            AIR_ITEMS, MIND_ITEMS, WATER_ITEMS, EARTH_ITEMS,
            FIRE_ITEMS, BODY_ITEMS, COSMIC_ITEMS, CHAOS_ITEMS,
            NATURE_ITEMS, LAW_ITEMS
        )

        // Check if the player has all required items
        val hasAllItems = requiredItems.all { getAttribute(player, it, 0) == 2 }

        // If the player has all required items
        if (hasAllItems) {
            // Set attribute to show Elriss items
            setAttribute(player, SHOW_ELRISS_ITEMS, true)

            // Remove the unused player's attributes
            removeAttributes(player, AIR_ITEMS, MIND_ITEMS, WATER_ITEMS, EARTH_ITEMS, FIRE_ITEMS, BODY_ITEMS, COSMIC_ITEMS, CHAOS_ITEMS, NATURE_ITEMS, LAW_ITEMS)

            // Send dialogue to the player indicating they received an omni-talisman
            sendItemDialogue(player, Items.OMNI_TALISMAN_13649, "Wizard Elriss gives you an omni-talisman.")

            // Add the omni-talisman to the player's inventory or drop it
            addItemOrDrop(player, Items.OMNI_TALISMAN_13649)

            // Set attribute to grant access to omni items
            setAttribute(player, ACCESS_TO_OMNI_ITEMS, true)
            return
        }
    }
}
/*
//===================================================================
// Repairing Pouches
// 9,000 coins for a Large Pouch (50 Runecrafting required)
// 12,000 coins for a Giant Pouch (75 Runecrafting required)
// Note: The Dark mage in the Abyss can repair all pouches for free.
//===================================================================
// Purchasing Pouches
// 25,000 coins for a Large Pouch (50 Runecrafting required to use)
// 50,000 coins for a Giant Pouch (75 Runecrafting required to use)
//===================================================================
// He gives players medium pouches for free; however, he does not replace small pouches.
// Keep in mind that players can still buy pouches if they do not have the required level,
// but they will be unable to use these pouches.
//===================================================================
//
// https://runescape.wiki/w/Wizard_Korvak?oldid=1990011
// https://www.tip.it/runescape/pages/view/runecrafting_guild.html
// Review https://www.youtube.com/watch?v=FtLRZuhinvo&ab_channel=TheChronicNoob
// RC Map https://runescape.wiki/images/archive/20100723065527%21Rune_Altar_Map.png?2314f
// Glitches & RC guild tab tp loc
// https://www.youtube.com/watch?v=Hi49BcN81hU&ab_channel=runescapewordspread
//===================================================================
// "distracting the eyes in the Abyss to help you sneak past"
//===================================================================
*/