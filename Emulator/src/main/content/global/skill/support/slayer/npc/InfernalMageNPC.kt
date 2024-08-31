package content.global.skill.support.slayer.npc

import content.global.skill.support.slayer.data.Tasks
import core.game.node.entity.combat.spell.CombatSpell
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.link.SpellBookManager.SpellBook
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Infernal mage NPC.
 */
@Initializable
class InfernalMageNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun init() {
        super.init()
        properties.autocastSpell = SpellBook.MODERN.getSpell(8) as CombatSpell?
    }

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return InfernalMageNPC(id, location)
    }

    override fun getIds(): IntArray {
        return Tasks.INFERNAL_MAGES.npcs
    }
}
