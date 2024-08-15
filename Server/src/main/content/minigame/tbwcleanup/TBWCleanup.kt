package content.minigame.tbwcleanup

import content.region.karamja.diary.KaramjaAchievementDiary.Companion.MediumTasks.TAI_BWO_WANNAI_EARN_FULL_FAVOR
import core.api.*
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.world.map.Direction
import core.game.world.map.RegionManager.isTeleportPermitted
import core.game.world.map.zone.ZoneBorders
import org.rs09.consts.Items
import kotlin.math.min
import kotlin.math.max


class TBWCleanup : MapArea {
    override fun defineAreaBorders(): Array<ZoneBorders> {
        return arrayOf(getRegionBorders(11056), getRegionBorders(1055)) // 11055
    }

    override fun areaLeave(entity: Entity, logout: Boolean) {
        if (entity is Player) {
            entity.interfaceManager.closeOverlay()
        }
    }
}

// value to change for debugging
var ChanceOfEventPerRewardTick = 16 // (realistic is around 16 I believe) chance out of 1000 to spawn event when hacking jungle
fun changeSpawnChance(chance: Int) {
    ChanceOfEventPerRewardTick = chance
}

fun spawnNPC(player: Player, npcId: Int, node: Node, favourValue: Int = 0) {
    animateStartledByEvent(player, node)
    animateScenery(node.asScenery(),2383)
    playAudio(player, 1287)

    val npc = NPC.create(npcId, player.location)
    player.sendMessage("A ${npc.name} appears.")
    npc.setAttribute("spawned:npc", true)
    npc.setAttribute("TBWC:Points", favourValue)
    npc.setAttribute("TBWC:SpawnedByPlayerName", player.name)
    npc.setAttribute("TBWC", true)
    npc.isRespawn = false
    npc.direction = player.direction
    npc.walkRadius = 20
    npc.isWalks = true
    clearScripts(player)
    node.setActive(false)
    npc.init()
    npc.locks.lockMovement(3)
    if (!player.inCombat()) npc.attack(player)
    node.setActive(true)
}

fun awardTBWCleanupPoints(player: Player, points: Int) {
    val NEW_POINTS = min(player.getAttribute("/save:tbwcleanup", 0) + points, 1000)
    if (NEW_POINTS == 1000) player.achievementDiaryManager.finishTask(player, DiaryType.KARAMJA, 1, TAI_BWO_WANNAI_EARN_FULL_FAVOR)
    player.setAttribute("/save:tbwcleanup", NEW_POINTS)
//    player.sendMessage("You now have ${NEW_POINTS.toDouble()/10}% Tai Bwo Wannai Favour.")
    openOverlay(player, 377)
    setVarbit(player, 907, (NEW_POINTS/10))
}

fun spendTBWCleanupPoints(player: Player, points: Int) {
    val NEW_POINTS = max(player.getAttribute("/save:tbwcleanup", 0) - points, 0)
    player.setAttribute("/save:tbwcleanup", NEW_POINTS)
//    player.sendMessage("You now have ${NEW_POINTS.toDouble()/10}% Tai Bwo Wannai Favour.")
    openOverlay(player, 377)
    setVarbit(player, 907, (NEW_POINTS/10))
}

private fun animateStartledByEvent(player: Player, node: Node) {
    val start = player.location
    val diffX = node.location.x - player.location.x
    val diffY = node.location.y - player.location.y
    var dest = start.transform(-diffX,-diffY,0)

    if (!isTeleportPermitted(dest)) dest = start

    val direction = Direction.getDirection(diffX, diffY)

    forceMove(player, start, dest,0,10,direction,2390)
}