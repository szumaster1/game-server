package content.minigame.templetrekking

import core.api.hasRequirement
import core.api.isQuestComplete
import core.game.node.entity.player.Player

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
            if (!isQuestComplete(player, "Priest in Peril")) return false
            if (!isQuestComplete(player, "Nature Spirit")) return false
            if (!hasRequirement(player, "In Search of the Myreque", false)) return false
            if (!hasRequirement(player, "In Aid of the Myreque", false)) return false
            if (!hasRequirement(player, "Darkness of Hallowvale", false)) return false
            if (!hasRequirement(player, "Legacy of Seergaze", false)) return false
        }
        return true
    }

    val tentacleCombatEventRegion = 10318
    val logout = "temple-trekking-logout"
    val baseLocation = "temple-trekking:base_location"
    val templeTrekkingCombatSession = "temple-trekking:combat_session"
}
