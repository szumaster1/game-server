package content.global.skill.support.agility.shortcuts

import content.global.skill.support.agility.AgilityHandler
import core.api.consts.Scenery
import core.cache.def.impl.SceneryDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.Skills
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin

import core.api.sendMessage

@Initializable
class MossGiantRopeShortcut : OptionHandler() {

    override fun newInstance(arg: Any): Plugin<Any?> {
        SceneryDefinition.forId(Scenery.ROPESWING_2322).handlers["option:swing-on"] = this
        SceneryDefinition.forId(Scenery.ROPESWING_2323).handlers["option:swing-on"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        if (!player.location.withinDistance(node.location, 4)) {
            player.sendMessage("I can't reach that.")
            return true
        }
        if (player.skills.getStaticLevel(Skills.AGILITY) < 10) {
            sendMessage(player, "You need an agility level of at least 10 in order to do that.")
            return true
        }
        val end = if (node.id == Scenery.ROPESWING_2322) Location.create(2704, 3209, 0) else Location.create(2709, 3205, 0)
        player.packetDispatch.sendSceneryAnimation(node.asScenery(), Animation.create(497), true)
        AgilityHandler.forceWalk(player, 0, player.location, end, Animation.create(751), 50, 22.0, "You skillfully swing across.", 1)
        player.achievementDiaryManager.finishTask(player, DiaryType.KARAMJA, 0, 1)
        return true
    }

    override fun getDestination(node: Node, n: Node): Location {
        return if (n.id == Scenery.ROPESWING_2322) Location.create(2709, 3209, 0) else Location.create(2705, 3205, 0)
    }
}
