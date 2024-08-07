package content.region.kandarin.guild.wizard

import content.global.travel.EssenceTeleport.teleport
import core.api.*
import core.api.consts.NPCs
import core.cache.def.impl.NPCDefinition
import core.cache.def.impl.SceneryDefinition
import core.game.global.action.ClimbActionHandler
import core.game.global.action.DoorActionHandler
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.scenery.Scenery
import core.game.world.map.Location
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Wizard guild handler.
 */
@Initializable
class WizardGuildHandler : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        NPCDefinition.forId(462).handlers["option:teleport"] = this
        SceneryDefinition.forId(1600).handlers["option:open"] = this
        SceneryDefinition.forId(1601).handlers["option:open"] = this
        SceneryDefinition.forId(2154).handlers["option:open"] = this
        SceneryDefinition.forId(2155).handlers["option:open"] = this
        SceneryDefinition.forId(1722).handlers["option:climb-up"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val id = if (node is Scenery) node.id else (node as NPC).id
        when (option) {
            "climb-up" -> when (id) {
                1722 -> if (node.location == Location(2590, 3089, 0)) {
                    ClimbActionHandler.climb(player, null, Location.create(2591, 3092, 1))
                } else {
                    ClimbActionHandler.climbLadder(player, node as Scenery, option)
                }
            }

            "open" -> when (id) {
                1600, 1601 -> {
                    if (getDynLevel(player, Skills.MAGIC) < 66) {
                        sendPlayerDialogue(player, "You need a Magic level of at least 66 to enter.")
                        return true
                    }
                    DoorActionHandler.handleAutowalkDoor(player, node as Scenery)
                }

                2155, 2154 -> sendNPCDialogue(player, NPCs.WIZARD_FRUMSCONE_460, "You can't attack the Zombies in the room, my Zombies are for magic target practice only and should be attacked from the other side of the fence.")
            }

            "teleport" -> {
                if (!isQuestComplete(player, "Rune Mysteries")) {
                    sendMessage(player, "You need to have completed the Rune Mysteries Quest to use this feature.")
                    return true
                }
                teleport((node as NPC), player)
                return true
            }
        }
        return true
    }

}
