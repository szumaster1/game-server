package content.region.misthalin.handlers.runecrafting

import content.global.skill.production.runecrafting.data.Talisman
import core.api.getAttribute
import core.api.setAttribute
import core.game.node.entity.player.Player

object RunecraftGuildUtils {

    const val SHOW_ELRISS_ITEMS = "/save:rcguild:omni-talisman"
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
     * Check for omni talisman reward.
     */
    fun checkReward(player: Player) {
        for (i in TALLYS.indices) {
            if (getAttribute(player, AIR_ITEMS, 0) == 2 && getAttribute(player, MIND_ITEMS, 0) == 2 &&
                getAttribute(player, WATER_ITEMS, 0) == 2 && getAttribute(player,
                    EARTH_ITEMS, 0) == 2 &&
                getAttribute(player, FIRE_ITEMS, 0) == 2 && getAttribute(player, BODY_ITEMS, 0) == 2 &&
                getAttribute(player, COSMIC_ITEMS, 0) == 2 && getAttribute(player,
                    CHAOS_ITEMS, 0) == 2 &&
                getAttribute(player, NATURE_ITEMS, 0) == 2 && getAttribute(player,
                    LAW_ITEMS, 0) == 2
            ) {
                setAttribute(player, SHOW_ELRISS_ITEMS, true)
            }
        }
    }

}
/**
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