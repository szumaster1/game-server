package content.global.handlers.item.equipment.special

import core.api.playAudio
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.combat.MeleeSwingHandler
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.plugin.Plugin
import core.utilities.RandomFunction

@Initializable
class SunderSpecialHandler : MeleeSwingHandler(), Plugin<Any> {

    override fun newInstance(arg: Any?): Plugin<Any> {
        CombatStyle.MELEE.swingHandler.register(10887, this)
        return this
    }

    override fun swing(entity: Entity?, victim: Entity?, state: BattleState?): Int {
        if (!(entity as Player).settings.drainSpecial(50)) {
            return -1
        }


        state!!.style = CombatStyle.MELEE
        var hit = 0
        if (isAccurateImpact(entity, victim, CombatStyle.MELEE, 2.0, 1.0)) {
            hit = RandomFunction.random(calculateHit(entity, victim, 1.1))
        }

        state.estimatedHit = hit
        if (victim is Player) {
            victim.packetDispatch.sendMessage("You have been drained.")
        }


        var left = -victim!!.getSkills().updateLevel(Skills.DEFENCE, -hit, 0)
        if (left > 0) {
            left = -victim.getSkills().updateLevel(Skills.ATTACK, -left, 0)
            if (left > 0) {
                left = -victim.getSkills().updateLevel(Skills.RANGE, -left, 0)
                if (left > 0) {
                    left = -victim.getSkills().updateLevel(Skills.MAGIC, -left, 0)
                }
            }
        }
        playAudio(entity.asPlayer(), 3481)
        return 1
    }

    override fun visualize(entity: Entity, victim: Entity?, state: BattleState?) {
        entity.visualize(Animation(5870), Graphic(1027))
    }

    override fun fireEvent(identifier: String?, vararg args: Any?): Any? {
        return null
    }

}
