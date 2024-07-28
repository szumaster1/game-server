package content.region.asgarnia.guild

import core.api.consts.NPCs
import core.api.getDynLevel
import core.api.sendNPCDialogue
import core.cache.def.impl.SceneryDefinition
import core.game.dialogue.FacialExpression
import core.game.global.action.ClimbActionHandler.climb
import core.game.global.action.ClimbActionHandler.climbLadder
import core.game.global.action.DoorActionHandler.handleAutowalkDoor
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.scenery.Scenery
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class MiningGuildPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        SceneryDefinition.forId(2113).handlers["option:climb-down"] = this
        SceneryDefinition.forId(30941).handlers["option:climb-up"] = this
        SceneryDefinition.forId(2112).handlers["option:open"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        if (option == "climb-down") {
            if (player.location.withinDistance(Location.create(3019, 3339, 0), 4)) {
                if (getDynLevel(player, Skills.MINING) < 60) {
                    player.dialogueInterpreter.open(382, NPC.create(382, Location.create(0, 0, 0)), 1)
                    return true
                }
                climb(player, Animation(828), Location.create(3021, 9739, 0))
                return true
            }
            climbLadder(player, node as Scenery, option)
            return true
        }
        if (option == "open") {
            if (getDynLevel(player, Skills.MINING) < 60) {
                sendNPCDialogue(
                    player,
                    NPCs.DWARF_382,
                    "Sorry, but you need level 60 Mining to go in there.",
                    FacialExpression.OLD_NORMAL
                )
                return true
            }
            handleAutowalkDoor(player, (node as Scenery))
        }
        if (option == "climb-up") {
            if (player.location.withinDistance(Location(3019, 9739, 0))) {
                climb(player, Animation(828), Location(3017, 3339, 0))
            } else {
                climbLadder(player, node as Scenery, "climb-up")
            }
        }
        return true
    }
}
