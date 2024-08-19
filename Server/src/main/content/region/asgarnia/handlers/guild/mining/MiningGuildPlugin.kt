package content.region.asgarnia.handlers.guild.mining

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

/**
 * Represents the Mining guild plugin.
 */
@Initializable
class MiningGuildPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        // Set the climb-down option handler for the scenery with ID 2113
        SceneryDefinition.forId(2113).handlers["option:climb-down"] = this
        // Set the climb-up option handler for the scenery with ID 30941
        SceneryDefinition.forId(30941).handlers["option:climb-up"] = this
        // Set the open option handler for the scenery with ID 2112
        SceneryDefinition.forId(2112).handlers["option:open"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        if (option == "climb-down") {
            if (player.location.withinDistance(Location.create(3019, 3339, 0), 4)) {
                if (getDynLevel(player, Skills.MINING) < 60) {
                    // Open dialogue with NPC 382 and display message if player's mining level is less than 60
                    player.dialogueInterpreter.open(382, NPC.create(382, Location.create(0, 0, 0)), 1)
                    return true
                }
                // Perform climb animation and move player to specified location
                climb(player, Animation(828), Location.create(3021, 9739, 0))
                return true
            }
            // Handle climbing down ladder
            climbLadder(player, node as Scenery, option)
            return true
        }
        if (option == "open") {
            if (getDynLevel(player, Skills.MINING) < 60) {
                // Send NPC dialogue to player if mining level is less than 60
                sendNPCDialogue(
                    player,
                    NPCs.DWARF_382,
                    "Sorry, but you need level 60 Mining to go in there.",
                    FacialExpression.OLD_NORMAL
                )
                return true
            }
            // Handle autowalk door
            handleAutowalkDoor(player, (node as Scenery))
        }
        if (option == "climb-up") {
            if (player.location.withinDistance(Location(3019, 9739, 0))) {
                // Perform climb animation and move player to specified location
                climb(player, Animation(828), Location(3017, 3339, 0))
            } else {
                // Handle climbing up ladder
                climbLadder(player, node as Scenery, "climb-up")
            }
        }
        return true
    }
}
