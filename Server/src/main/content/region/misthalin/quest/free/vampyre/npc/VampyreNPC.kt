package content.region.misthalin.quest.free.vampyre.npc

import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Location
import core.plugin.Initializable
import core.tools.RandomFunction

/**
 * Represents the Vampyre slayer NPC.
 */
@Initializable
class VampyreNPC : AbstractNPC {

    constructor() : super(0, null)

    private constructor(id: Int, location: Location) : super(id, location)

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return VampyreNPC(id, location)
    }

    override fun init() {
        super.init()
        getSkills().lifepoints = 40
        getSkills().setStaticLevel(Skills.HITPOINTS, 40)
        getSkills().setLevel(Skills.HITPOINTS, 40)
    }

    override fun tick() {
        val p = getAttribute<Player>("player", null)
        if (p != null) {
            if (p.location.getDistance(getLocation()) >= 16) {
                clear()
                return
            }
            if (!properties.combatPulse.isAttacking) {
                properties.combatPulse.attack(p)
            }
            if (p.properties.combatPulse.isAttacking && p.properties.combatPulse.getVictim() === this) {
                for (l in CANDLE_LOCATION) {
                    if (p.location == l) {
                        p.sendChat(FORCE_CHAT[RandomFunction.random(FORCE_CHAT.size)])
                        p.packetDispatch.sendMessage("The candles burn your feet!")
                        break
                    }
                }
                if (!p.inventory.containsItem(HAMMER) || !p.inventory.containsItem(STAKE)) {
                    getSkills().heal(10)
                }
                if (RandomFunction.random(7) == 2) {
                    getSkills().heal(RandomFunction.random(1, if (!p.inventory.containsItem(GARLIC)) 12 else 2))
                }
            }
        }
        super.tick()
    }

    override fun onImpact(entity: Entity, state: BattleState) {
        if (entity is Player) {
            val p = entity
            if (!p.inventory.containsItem(HAMMER) || !p.inventory.containsItem(STAKE)) {
                getSkills().heal(10)
            }
        }
        super.onImpact(entity, state)
    }

    override fun finalizeDeath(killer: Entity) {
        isRespawn = false
        super.finalizeDeath(killer)
        if (killer !is Player) {
            return
        }
        val p = killer
        val quest = p.getQuestRepository().getQuest("Vampire Slayer")
        if (p.inventory.containsItem(HAMMER) && p.inventory.remove(STAKE)) {
            if (quest.getStage(p) == 30) {
                quest.finish(p)
                p.packetDispatch.sendMessage("You hammer the stake into the vampire's chest!")
            }
        } else {
            p.packetDispatch.sendMessage("The vampire returns to his coffin. Next time use a stake and hammer.")
        }
        isRespawn = false
    }

    override fun checkImpact(state: BattleState) {
        if (state.attacker is Player) {
            val p = state.attacker as Player
            if (!p.inventory.containsItem(HAMMER) || !p.inventory.containsItem(STAKE)) {
                if (state.estimatedHit > -1) {
                    state.estimatedHit = 0
                }
                if (state.secondaryHit > -1) {
                    state.secondaryHit = 0
                }
            }
        }
    }

    override fun isAttackable(entity: Entity, style: CombatStyle, message: Boolean): Boolean {
        val player = (entity as Player)
        val pl = getAttribute<Player>("player", null)
        return pl != null && pl === player
    }

    override fun getIds(): IntArray {
        return ID
    }

    companion object {
        private val STAKE = Item(1549)
        private val HAMMER = Item(2347)
        private val GARLIC = Item(1550)
        private val CANDLE_LOCATION = arrayOf(Location.create(3076, 9772, 0), Location.create(3079, 9772, 0), Location.create(3075, 9778, 0), Location.create(3080, 9778, 0))
        private val FORCE_CHAT = arrayOf("Eeek!", "Oooch!", "Gah!", "Ow!")
        private val ID = intArrayOf(757)
    }
}
