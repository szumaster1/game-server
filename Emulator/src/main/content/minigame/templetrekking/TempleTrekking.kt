package content.minigame.templetrekking

import core.api.hasRequirement
import core.api.isQuestComplete
import core.game.node.entity.player.Player
import org.rs.consts.QuestName

/**
 * Temple Trekking
 * 1. [Guide](https://www.youtube.com/watch?v=DapmGFLJD4c)
 * 2. [Guide](https://www.youtube.com/watch?v=_2Rq94MhPcI)
 * 3. [Guide](https://www.youtube.com/watch?v=iApqG5iVt2k)
 * 4. [Guide](https://www.youtube.com/watch?v=1sqyd1Szm6Q)
 * 5. [Rewards](https://www.youtube.com/watch?v=2cyZDMDn-Jw)
 * 6. [NPC loot](https://www.youtube.com/watch?v=KeSnbBBzhDk)
 * 7. [Example route](https://www.youtube.com/watch?v=NAuNkCZkLio)
 */
object TempleTrekking {

    fun hasRequirements(player: Player?): Boolean {
        if (player != null) {
            if (!isQuestComplete(player, QuestName.PRIEST_IN_PERIL)) return false
            if (!isQuestComplete(player, QuestName.NATURE_SPIRIT)) return false
            if (!hasRequirement(player, QuestName.IN_SEARCH_OF_THE_MYREQUE, false)) return false
            if (!hasRequirement(player, QuestName.IN_AID_OF_THE_MYREQUE, false)) return false
            if (!hasRequirement(player, QuestName.DARKNESS_OF_HALLOWVALE, false)) return false
            if (!hasRequirement(player, QuestName.LEGACY_OF_SEERGAZE, false)) return false
        }
        return true
    }

    val tentacleCombatEventRegion = 10318
    val logout = "temple-trekking-logout"
    val baseLocation = "temple-trekking:base_location"
    val templeTrekkingCombatSession = "temple-trekking:combat_session"
}
