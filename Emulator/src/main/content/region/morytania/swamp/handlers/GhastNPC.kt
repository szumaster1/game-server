package content.region.morytania.swamp.handlers

import org.rs.consts.Items
import org.rs.consts.NPCs
import content.data.consumables.Consumables
import content.region.morytania.swamp.quest.druidspirit.handlers.NSUtils
import core.api.*
import core.game.consumable.Food
import core.game.interaction.MovementPulse
import core.game.node.entity.Entity
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.world.map.Location
import core.game.world.map.RegionManager
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.tools.RandomFunction
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Represents the Mort Myre Ghast NPC.
 */
@Initializable
class GhastNPC : AbstractNPC {

    constructor() : super(NPCs.GHAST_1052, null, true)
    private constructor(id: Int, location: Location) : super(id, location)

    override fun construct(id: Int, location: Location, vararg objects: Any?): AbstractNPC {
        isAggressive = id != ids[0]
        return GhastNPC(id, location)
    }

    /**
     * Handle tick actions.
     */
    override fun handleTickActions() {
        super.handleTickActions()
        if (id == ids[0] && RandomFunction.roll(35)) {
            val players = RegionManager.getLocalPlayers(this, 5).filter { !it.inCombat() }
            if (players.isNotEmpty()) {
                val player = players.random()
                submitIndividualPulse(this, object : MovementPulse(this, player) {
                    override fun pulse(): Boolean {
                        animate(Animation(1093))
                        attemptLifeSiphon(player)
                        return true
                    }
                })
            }
        } else {
            val ticksTransformed = getWorldTicks() - getAttribute(this, "woke", 0)
            if (!inCombat() && ticksTransformed > 10) {
                reTransform()
            }
        }
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GHAST_1052, NPCs.GHAST_1053)
    }

    /**
     * Attempt life siphon.
     *
     * @param player the player.
     */
    fun attemptLifeSiphon(player: Player) {
        var hasFood = false

        if (NSUtils.activatePouch(player, this)) return

        GlobalScope.launch {
            for (i in player.inventory.toArray()) {
                if (i == null) continue
                val consumable = Consumables.getConsumableById(i.id)
                if (consumable != null && consumable.consumable is Food) {
                    hasFood = true
                    removeItem(player, i, Container.INVENTORY)
                    addItem(player, Items.ROTTEN_FOOD_2959)
                    sendMessage(player, "You feel something attacking your backpack, and smell a terrible stench.")
                    break
                }
            }

            if (!hasFood && RandomFunction.roll(3)) {
                sendMessage(player, "An attacking Ghast just misses you.")
            } else if (!hasFood) {
                impact(player, RandomFunction.random(3, 6), ImpactHandler.HitsplatType.NORMAL)
                sendMessage(player, "A supernatural force draws energy from you.")
            }
        }
    }

    /**
     * Commence death
     *
     * @param killer the killer (player).
     */
    override fun commenceDeath(killer: Entity?) {
        super.commenceDeath(killer)
    }

    /**
     * Finalize death.
     *
     * @param killer the killer (player).
     */
    override fun finalizeDeath(killer: Entity?) {
        super.finalizeDeath(killer)
        if (id == ids[1]) {
            reTransform()
            if (killer is Player) {
                NSUtils.incrementGhastKC(killer)
                rewardXP(killer, Skills.PRAYER, 30.0)
                removeAttribute("woke")
            }
        }
    }
}
