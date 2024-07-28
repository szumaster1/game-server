package content.global.handlers.iface.plugin

import content.global.skill.combat.magic.SpellListener
import content.global.skill.combat.magic.SpellListeners
import content.global.skill.combat.magic.SpellUtils
import core.api.consts.Components
import core.api.getAttribute
import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.node.entity.combat.spell.MagicSpell
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.SpellBookManager.SpellBook
import core.game.world.GameWorld
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class MagicBookTabInterfacePlugin : ComponentPlugin() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        ComponentDefinition.put(Components.MAGIC_192, this)
        ComponentDefinition.put(Components.MAGIC_ZAROS_193, this)
        ComponentDefinition.put(Components.MAGIC_LUNAR_430, this)
        return this
    }

    override fun handle(player: Player, component: Component, opcode: Int, button: Int, slot: Int, itemId: Int): Boolean {
        if (GameWorld.ticks < getAttribute(player, "magic:delay", -1)) {
            return true
        }

        val spellBook = when (component.id) {
            Components.MAGIC_192 -> SpellBook.MODERN
            Components.MAGIC_ZAROS_193 -> SpellBook.ANCIENT
            else -> SpellBook.LUNAR
        }

        SpellListeners.run(button, SpellListener.NONE, SpellUtils.getBookFromInterface(component.id), player, null)
        val result = MagicSpell.castSpell(player, spellBook, button, player)

        return result
    }

}

