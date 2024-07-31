package core.game.world.map.zone.impl

import content.global.handlers.item.equipment.gloves.BrawlingGloves
import content.region.wilderness.handlers.DeepWildyThreat.adjustThreat
import core.api.consts.NPCs
import core.api.sendNews
import core.game.component.Component
import core.game.interaction.Option
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.agg.AggressiveBehavior
import core.game.node.entity.npc.agg.AggressiveHandler
import core.game.node.entity.player.Player
import core.game.node.entity.player.info.Rights
import core.game.node.entity.player.link.SkullManager
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.world.GameWorld.settings
import core.game.world.map.Location
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneRestriction
import core.tools.RandomFunction
import kotlin.math.ln
import kotlin.math.pow

class WildernessZone(vararg borders: ZoneBorders) : MapZone("Wilderness", true, ZoneRestriction.RANDOM_EVENTS) {

    val borders: Array<ZoneBorders> = borders as Array<ZoneBorders>

    override fun configure() {
        for (border in borders) {
            register(border)
        }
    }

    fun getNewDropRate(combatLevel: Int): Int {
        val x = combatLevel.toDouble()
        val A = 44044.5491
        val B = -7360.19548
        return (A + (B * ln(x))).toInt()
    }

    override fun death(e: Entity, killer: Entity): Boolean {
        if (e is NPC) rollWildernessExclusiveLoot(e, killer)
        return false //DONT override default death handling.
    }

    private fun rollWildernessExclusiveLoot(e: Entity, killer: Entity) {
        if (killer !is Player) return

        val isDeepWildy = killer.skullManager.isDeepWilderness()
        val isRevOrCele = e.asNpc().name.contains("Revenant") || e.id == NPCs.CHAOS_ELEMENTAL_3200
        val isSufficientRisk =
            killer.getAttribute("deepwild-value-risk", 0L) > SkullManager.DEEP_WILD_DROP_RISK_THRESHOLD
        val isValidTarget = e is NPC && ((isDeepWildy && isSufficientRisk) || isRevOrCele)

        if (isDeepWildy) {
            adjustThreat(killer, 50)
        }

        if (!isValidTarget) return

        var pvpGearRate = getNewDropRate(e.asNpc().definition.combatLevel)
        if (isDeepWildy && isRevOrCele) pvpGearRate /= 2

        val cEleGloveRate = if (isDeepWildy) 50 else 150
        val normalGloveRate =
            if (isDeepWildy && isRevOrCele) 100 else ((1.0 / (1.0 - (1.0 - (1.0 / pvpGearRate.toDouble())).pow(16.0))) * 5.0 / 6.0).toInt()

        if (RandomFunction.roll(if (e.id == NPCs.CHAOS_ELEMENTAL_3200) cEleGloveRate else normalGloveRate)) {
            val glove = RandomFunction.random(1, 14).toByte()
            val reward = Item(BrawlingGloves.forIndicator(glove.toInt()).id)
            GroundItemManager.create(reward, e.asNpc().dropLocation, killer.asPlayer())
            sendNews(killer.getUsername() + " has received " + reward.name.lowercase() + " from a " + e.asNpc().name + "!")
            if (isDeepWildy) adjustThreat(killer, 750)
        }

        for (j in PVP_GEAR) {
            val chance = RandomFunction.roll(pvpGearRate)
            if (chance) {
                var reward = if (j == 13879 || j == 13883) { // checks if it's a javelin or throwing axe
                    Item(j, RandomFunction.random(15, 50))
                } else {
                    Item(j)
                }
                sendNews(killer.asPlayer().username + " has received a " + reward.name + " from a " + e.asNpc().name + "!")
                GroundItemManager.create(reward, (e as NPC).dropLocation, killer.asPlayer())
                if (isDeepWildy) adjustThreat(killer, 3000)
            }
        }
    }

    override fun interact(e: Entity, target: Node, option: Option): Boolean {
        if (target is NPC) {
            if (target.asNpc().name.contains("Revenant")) {
                e.asPlayer().properties.combatPulse.attack(target)
                return true
            }
        }
        return super.interact(e, target, option)
    }


    override fun enter(e: Entity): Boolean {
        if (e is Player) {
            val p = e
            if (!p.isArtificial) {
                show(p)
            } else {
                p.skullManager.isWilderness = true
                p.skullManager.level = (getWilderness(p))
            }
            for (i in 0..6) {
                if (i == 5 || i == 3) {
                    continue
                }
                if (p.attributes.containsKey("overload") || p.getSkills().getLevel(i) > 118) {
                    if (p.getSkills().getLevel(i) > p.getSkills().getStaticLevel(i)) {
                        p.getSkills().setLevel(i, p.getSkills().getStaticLevel(i))
                        p.removeAttribute("overload")
                    }
                }
            }
            if (p.familiarManager.hasFamiliar() && !p.familiarManager.hasPet()) {
                val familiar = p.familiarManager.familiar
                familiar.transform()
            }
            p.appearance.sync()
        } else if (e is NPC) {
            val n = e
            if (n.definition.hasAttackOption() && n.isAggressive) {
                //n.setAggressive(true);
                n.aggressiveHandler = AggressiveHandler(n, AggressiveBehavior.WILDERNESS)
            }
        }
        return true
    }

    override fun leave(e: Entity, logout: Boolean): Boolean {
        if (!logout && e is Player) {
            val p = e
            leave(p)
            if (p.familiarManager.hasFamiliar() && !p.familiarManager.hasPet()) {
                val familiar = p.familiarManager.familiar
                if (familiar.isCombatFamiliar) {
                    familiar.reTransform()
                }
            }
            p.appearance.sync()
        }
        return true
    }

    fun leave(p: Player) {
        val overlay = Component(381)
        if (overlay.id == 381) {
            p.interfaceManager.close(overlay)
        }
        p.interaction.remove(Option._P_ATTACK)
        p.skullManager.isWilderness = false
        p.skullManager.level = 0
    }

    override fun teleport(e: Entity, type: Int, node: Node): Boolean {
        if (e is Player) {
            val p = e
            if (p.details.rights === Rights.ADMINISTRATOR) {
                return true
            }
            if (!checkTeleport(p, (if (node is Item && (node.name.contains("glory") || node.name.contains("slaying"))) 30 else 20))) {
                return false
            }
        }
        return true
    }

    override fun continueAttack(e: Entity, target: Node, style: CombatStyle, message: Boolean): Boolean {
        if (e is Player && target is Player) {
            val p = e
            val t = target
            var level = p.skullManager.level
            if (t.skullManager.level < level) {
                level = t.skullManager.level
            }
            val combat = p.properties.currentCombatLevel
            val targetCombat = t.properties.currentCombatLevel
            if (combat - level > targetCombat || combat + level < targetCombat) {
                if (message) {
                    p.packetDispatch.sendMessage("The level difference between you and your opponent is too great.")
                }
                return false
            }
        }
        return true
    }

    override fun locationUpdate(e: Entity, last: Location) {
        if (e is Player && !e.asPlayer().isArtificial) {
            val p = e
            p.skullManager.level = getWilderness(p)
        }
    }

    companion object {
        private val PVP_GEAR = intArrayOf(13887, 13893, 13899, 13905, 13870, 13873, 13876, 13879, 13883, 13884, 13890, 13896, 13902, 13858, 13861, 13864, 13867)

        val instance: WildernessZone = WildernessZone(
            ZoneBorders(2944, 3525, 3400, 3975),
            ZoneBorders(3070, 9924, 3135, 10002),
            ZoneBorders.forRegion(12192),
            ZoneBorders.forRegion(12193),
            ZoneBorders.forRegion(11937)
        )

        const val WILDERNESS_PROT_ATTR: String = "/save:wilderness-protection-active"
        const val WILDERNESS_HIGHER_NEXTFEE: String = "/save:wilderness-higher-next-fee"

        fun show(player: Player) {
            if (player.skullManager.isWildernessDisabled) {
                return
            }
            player.interfaceManager.openOverlay(Component(381))
            player.skullManager.level = getWilderness(player)
            player.packetDispatch.sendString("Level: " + player.skullManager.level, 381, 1)
            if (settings!!.wild_pvp_enabled) {
                player.interaction.set(Option._P_ATTACK)
            }
            player.skullManager.isWilderness = true
        }

        fun checkTeleport(p: Player, level: Int): Boolean {
            if (p.skullManager.level > level && !p.skullManager.isWildernessDisabled) {
                message(p, "You can't teleport this deep in the wilderness!")
                return false
            }
            return true
        }

        @JvmStatic
        fun isInZone(e: Entity): Boolean {
            val l = e.location
            for (z in instance.borders) {
                if (z.insideBorder(e)) return true
            }
            return false
        }

        fun isInZone(l: Location?): Boolean {
            for (z in instance.borders) {
                if (z.insideBorder(l)) return true
            }
            return false
        }

        fun getWilderness(e: Entity): Int {
            val y = e.location.y
            return if (6400 < y) {
                (y - 9920) / 8 + 1
            } else {
                (y - 3520) / 8 + 1
            }
        }
    }
}
