package content.global.skill.combat.summoning.familiar

import content.global.skill.combat.summoning.pet.Pets
import core.cache.def.impl.ItemDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Familiar item option plugin.
 */
@Initializable
class FamiliarItemOptionPlugin : OptionHandler() {
    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any> {
        for (p in Pets.values()) {
            val def = ItemDefinition.forId(p.babyItemId) ?: continue
            def.handlers["option:drop"] = this
            def.handlers["option:release"] = this
            if (p.grownItemId > -1) {
                ItemDefinition.forId(p.grownItemId).handlers["option:drop"] = this
                ItemDefinition.forId(p.grownItemId).handlers["option:release"] = this
            }
            if (p.overgrownItemId > -1) {
                ItemDefinition.forId(p.overgrownItemId).handlers["option:drop"] = this
                ItemDefinition.forId(p.overgrownItemId).handlers["option:release"] = this
            }
        }
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        when (option) {
            "drop" -> {
                player.familiarManager.summon((node as Item), true)
                return true
            }

            "release" -> {
                if (node.id == 7771) {
                    player.familiarManager.summon((node as Item), true)
                    return true
                }
                if (player.inventory.remove((node as Item))) {
                    player.dialogueInterpreter.sendDialogues(player, null, "Run along; I'm setting you free.")
                }
                return true
            }
        }
        return true
    }

    override fun isWalk(): Boolean {
        return false
    }
}
