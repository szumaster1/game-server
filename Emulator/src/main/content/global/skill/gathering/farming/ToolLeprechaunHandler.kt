package content.global.skill.gathering.farming

import content.minigame.vinesweeper.VinesweeperMinigame
import cfg.consts.Components
import cfg.consts.NPCs
import core.cache.def.impl.NPCDefinition
import core.game.component.Component
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.plugin.Plugin

val TL_IDS = arrayOf(NPCs.TOOL_LEPRECHAUN_3021,NPCs.GOTH_LEPRECHAUN_8000,NPCs.TOOL_LEPRECHAUN_4965,NPCs.TECLYN_2861)

/**
 * Tool leprechaun handler.
 */
@Initializable
class ToolLeprechaunHandler : OptionHandler() {
    override fun newInstance(arg: Any?): Plugin<Any> {
        for(id in TL_IDS){
            val def = NPCDefinition.forId(id)
            def.handlers["option:exchange"] = this
            def.handlers["option:teleport"] = this
        }
        return this
    }

    override fun handle(player: Player?, node: Node?, option: String?): Boolean {
        node ?: return false
        when(option){
            "exchange" -> player?.interfaceManager?.open(Component(Components.FARMING_TOOLS_125))
            "teleport" -> VinesweeperMinigame.Companion.VinesweeperTeleport.teleport(node as NPC, player!!)
        }
        return true
    }

}
