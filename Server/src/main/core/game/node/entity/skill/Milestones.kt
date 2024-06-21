package core.game.node.entity.skill

import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.world.GameWorld
import core.game.world.update.flag.context.Graphic
import core.utilities.DARK_RED

fun Milestones(player: Player): Int {

    var combatLevel = player.properties.getCurrentCombatLevel()
    var totalLevel = player.skills.getTotalLevel()
    val totalLevelMilestones = intArrayOf(50, 75, 100, 150, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000, 2100, 2200, 2300, 2300)


    if (combatLevel == 137 && player.skills.updateCombatLevel()) {
        Graphic.send(
            Graphic(
                1636,
                0,
                50
            ), player.location)
        sendMessage(player,"Congratulations! Your Combat level is now 138! You've achieved the highest Combat level possible!")
    }

    if (combatLevel == 125 && !GameWorld.settings!!.isMembers && player.skills.updateCombatLevel()) {
        Graphic.send(
            Graphic(
                1636,
                0,
                50
            ), player.location)
        sendMessage(player,"Congratulations! Your Combat level is now 126 - the highest possible Combat level on free worlds!")
    }

    if (totalLevelMilestones.contains(totalLevel)) {
        Graphic.send(
            Graphic(
                1633,
                0,
                50
            ), player.location)
        sendMessage(player,DARK_RED + "Well done! You've reached the total level $totalLevel milestone!")
    }

    return 0
}
