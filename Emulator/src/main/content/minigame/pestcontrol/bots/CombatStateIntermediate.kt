package content.minigame.pestcontrol.bots

import content.minigame.pestcontrol.PestControlHelper.GATE_ENTRIES
import content.minigame.pestcontrol.PestControlHelper.getMyPestControlSession2
import core.game.interaction.MovementPulse
import core.game.node.Node
import core.game.node.entity.npc.NPC
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.map.RegionManager
import core.game.world.map.path.Pathfinder
import core.tools.RandomFunction
import java.util.*

/**
 * Combat state intermediate.
 *
 * @param bot the bot id.
 */
class CombatStateIntermediate(val bot: PestControlTestBot2) {
    private val Random = Random()
    val randomtype = Random().nextInt(100)

    /**
     * Go to portals.
     */
    fun goToPortals() {
        bot.customState = "I'm at portals."
        val gate = bot.getClosestNodeWithEntry(75, GATE_ENTRIES)
        val sesh = getMyPestControlSession2(bot)

        var portal: Node? = null
        if (sesh != null && sesh.aportals.isNotEmpty()) {
            val removeList = ArrayList<NPC>()
            for (port in sesh.aportals) {
                if (!port.isActive) {
                    removeList.add(port)
                } else {
                    portal = port
                    break
                }
            }
            sesh.aportals.removeAll(removeList)
        }

        if (bot.pulseManager.hasPulseRunning() && portal == null) {
            return
        }

        if (bot.justStartedGame) {
            bot.customState = "Walking randomly"
            bot.justStartedGame = false
            bot.randomWalkAroundPoint(getMyPestControlSession2(bot)?.squire?.location ?: bot.location, 15)
            bot.movetimer = Random.nextInt(7) + 6
            return
        }

        if (gate != null && sesh?.aportals?.isEmpty() == true) {
            bot.customState = "Interacting gate ID " + gate.id
            bot.interact(gate)
            bot.openedGate = true
            if (Random.nextInt(4) == 1 && randomtype < 40) {
                bot.movetimer = Random.nextInt(2) + 1
            }
            return
        }

        if (portal != null) {
            if (bot.location.withinDistance(portal.location, 10) && portal.isActive) {
                val spinners = ArrayList<NPC>()
                RegionManager.getLocalNpcs(bot).forEach {
                    if (it.name.lowercase(Locale.getDefault()).equals("spinner") && it.location.withinDistance(
                            bot.location, 10
                        )
                    ) spinners.add(it)
                }
                if (spinners.isNotEmpty()) {
                    bot.attack(spinners.random())
                } else {
                    bot.attack(portal)
                }
            } else {
                randomWalkTo(portal.location, 5)
            }
            bot.movetimer = Random().nextInt(10) + 5
            return
        } else {
            bot.AttackNpcsInRadius(50)
        }

        bot.customState = "Absolutely nothing. Everything is dead"
    }

    /**
     * Fight NPCs.
     */
    fun fightNPCs() {
        bot.customState = "Fight NPCs"
        bot.AttackNpcsInRadius(8)
        bot.eat(379)


        if (!bot.inCombat()) {
        }
    }

    /**
     * Random walk to.
     *
     * @param loc the location.
     * @param radius the radius.
     */

    //Functions
    fun randomWalkTo(loc: Location, radius: Int) {
        var newloc = loc.transform(
            RandomFunction.random(radius, -radius), RandomFunction.random(radius, -radius), 0
        )
        if (!bot.walkingQueue.isMoving) {
            walkToIterator(newloc)
        }
    }

    private fun walkToIterator(loc: Location) {
        var diffX = loc.x - bot.location.x
        var diffY = loc.y - bot.location.y

        GameWorld.Pulser.submit(object : MovementPulse(bot, bot.location.transform(diffX, diffY, 0), Pathfinder.SMART) {
            override fun pulse(): Boolean {
                return true
            }
        })
    }
}
