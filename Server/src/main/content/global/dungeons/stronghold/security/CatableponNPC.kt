package content.global.dungeons.stronghold.security

import cfg.consts.NPCs
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.combat.CombatSwingHandler
import core.game.node.entity.combat.MultiSwingHandler
import core.game.node.entity.combat.equipment.SwitchAttack
import core.game.node.entity.combat.spell.CombatSpell
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.link.SpellBookManager.SpellBook
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable

/**
 * Catablepon NPC.
 */
@Initializable
class CatableponNPC : AbstractNPC {

    private val combatHandler = MultiSwingHandler(
        SwitchAttack(CombatStyle.MELEE.swingHandler, null).setUseHandler(true),
        SwitchAttack(CombatStyle.MELEE.swingHandler, null).setUseHandler(true),
        SwitchAttack(CombatStyle.MELEE.swingHandler, null).setUseHandler(true),
        SwitchAttack(CombatStyle.MAGIC.swingHandler, ANIMATION).setUseHandler(true)
    )

    constructor() : super(0, null)

    private constructor(id: Int, location: Location) : super(id, location) {
        this.isAggressive = true
    }

    /**
     * Configuration.
     */
    override fun configure() {
        super.configure()
        super.getProperties().spell = SpellBook.MODERN.getSpell(7) as CombatSpell?
        super.getProperties().autocastSpell = SpellBook.MODERN.getSpell(7) as CombatSpell?
    }

    /**
     * Construct
     *
     * @param id the id.
     * @param location the location.
     * @param objects the objects.
     * @return
     */
    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return CatableponNPC(id, location)
    }

    /**
     * Get swing handler.
     *
     * @param swing the swing boolean.
     * @return
     */
    override fun getSwingHandler(swing: Boolean): CombatSwingHandler {
        return combatHandler
    }

    /**
     * Get ids.
     *
     * @return
     */
    override fun getIds(): IntArray {
        return ID
    }

    companion object {
        private val ID = intArrayOf(NPCs.CATABLEPON_4397, NPCs.CATABLEPON_4398, NPCs.CATABLEPON_4399)
        private val ANIMATION = Animation(4272)
    }
}