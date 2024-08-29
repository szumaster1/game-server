package content.dd.trails.scroll

import content.dd.trails.ClueLevel
import content.dd.trails.clue.ClueScrollPlugin
import core.api.setInterfaceText
import core.game.interaction.Option
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.zone.ZoneBorders

/**
 * Abstract class representing a challenge clue scroll.
 *
 * @param question the question associated with the clue.
 * @param npc the NPC related to the clue.
 * @param answer the answer to the clue.
 * @param name the name of the clue scroll.
 * @param clueId the unique identifier of the clue.
 * @param level the difficulty level of the clue.
 * @param borders the zone borders where the clue is located.
 */
abstract class ChallengeClueScroll(
    name: String?,
    clueId: Int,
    level: ClueLevel?,
    val question: String?,
    val npc: Int?,
    val answer: Int?,
    vararg borders: ZoneBorders
) : ClueScrollPlugin(name, clueId, level, 345, *borders) {

    override fun read(player: Player) {
        for (i in 1..8) {
            player.packetDispatch.sendString("", interfaceId, i)
        }
        super.read(player)
        setInterfaceText(player, "<br><br><br><br><br> Speak to " +
                NPC(npc!!).name.lowercase().replace("_", " ")
                    .replaceFirstChar { it.uppercaseChar() } + ".", interfaceId, 1)
    }

    override fun interact(e: Entity, target: Node, option: Option): Boolean {
        return super.interact(e, target, option)
    }
}
