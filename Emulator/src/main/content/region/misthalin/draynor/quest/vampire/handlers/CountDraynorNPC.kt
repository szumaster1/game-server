package content.region.misthalin.draynor.quest.vampire.handlers

import core.api.*
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.tools.RandomFunction
import org.rs.consts.Items
import org.rs.consts.NPCs
import org.rs.consts.QuestName

/**
 * Represents the Count Draynor NPC (Vampire slayer quest).
 */
@Initializable
class CountDraynorNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return CountDraynorNPC(id, location)
    }

    override fun init() {
        super.init()
        getSkills().lifepoints = 40
        getSkills().setStaticLevel(Skills.HITPOINTS, 40)
        getSkills().setLevel(Skills.HITPOINTS, 40)
        this.faceLocation(Location.create(3078, 9770, 0))
        /*
         * NPC is animated slightly next to coffin.
         * OSRS had same problem.
         * https://youtu.be/xAAsSFp-dlY?si=twI9VrEz4nM2Z71R&t=294
         */
        this.animator.animate(Animation.create(3114))
    }

    override fun tick() {
        val p = getAttribute<Player>("player", null)
        if (p != null) {
            if (p.location.getDistance(getLocation()) >= 16) {
                clear()
                return
            }
            if (!properties.combatPulse.isAttacking && !this.animator.isAnimating) {
                properties.combatPulse.attack(p)
            }
            if (p.properties.combatPulse.isAttacking && p.properties.combatPulse.getVictim() == this) {
                for (l in CANDLE_LOCATION) {
                    if (p.location == l) {
                        sendChat(p,FORCE_CHAT[RandomFunction.random(FORCE_CHAT.size)])
                        sendMessage(p, "The candles burn your feet!")
                        break
                    }
                }
                if (!inInventory(p, Items.HAMMER_2347) || !inInventory(p, Items.STAKE_1549)) {
                    getSkills().heal(10)
                    // sendMessage(p, "The vampire seems to regenerate!")
                }
                if (RandomFunction.random(7) == 2) {
                    getSkills().heal(RandomFunction.random(1, if (!inInventory(p, Items.GARLIC_1550)) 12 else 2))
                }
            }
        }
        super.tick()
    }

    override fun onImpact(entity: Entity, state: BattleState) {
        if (entity is Player) {
            val p = entity
            if (!inInventory(p, Items.HAMMER_2347) || !inInventory(p, Items.STAKE_1549)) {
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
        val quest = p.getQuestRepository().getQuest(QuestName.VAMPIRE_SLAYER)
        if (inInventory(p, Items.HAMMER_2347) && p.inventory.remove(Item(Items.STAKE_1549))) {
            if (quest.getStage(p) == 30) {
                quest.finish(p)
                sendMessage(p,"You hammer the stake into the vampire's chest!")
            }
        } else {
            sendMessage(p, "You're unable to push the stake far enough in!")
        }
        isRespawn = false
    }

    override fun checkImpact(state: BattleState) {
        if (state.attacker is Player) {
            val p = state.attacker as Player
            if (!inInventory(p, Items.HAMMER_2347) || !inInventory(p, Items.STAKE_1549)) {
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
        return pl != null && pl == player
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.COUNT_DRAYNOR_757)
    }

    companion object {
        private val CANDLE_LOCATION = arrayOf(Location.create(3076, 9772, 0), Location.create(3079, 9772, 0), Location.create(3075, 9778, 0), Location.create(3080, 9778, 0))
        private val FORCE_CHAT = arrayOf("Eeek!", "Oooch!", "Gah!", "Ow!")
    }
}
