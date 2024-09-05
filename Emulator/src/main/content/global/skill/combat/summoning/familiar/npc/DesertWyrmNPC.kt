package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import content.global.skill.combat.summoning.familiar.Forager
import content.global.skill.gathering.mining.MiningNode
import cfg.consts.NPCs
import core.cache.def.impl.NPCDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.impl.Projectile
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillBonus
import core.game.node.entity.skill.Skills
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Direction
import core.game.world.map.RegionManager.getObject
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.PluginManager.definePlugin
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Desert wyrm familiar.
 */
@Initializable
class DesertWyrmNPC @JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.DESERT_WYRM_6831) :
    Forager(owner, id, 1900, 12049, 6, WeaponInterface.STYLE_AGGRESSIVE) {

    init {
        boosts.add(SkillBonus(Skills.MINING, 1.0))
    }

    override fun construct(owner: Player, id: Int): Familiar {
        return DesertWyrmNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        val target = special.node as Entity
        if (!canCombatSpecial(target)) {
            return false
        }
        faceTemporary(special.node as Entity, 2)
        visualize(Animation(7795), Graphic(1410))
        Projectile.magic(this, target, 1411, 40, 36, 51, 10).send()
        sendFamiliarHit(target, 5)
        return true
    }

    public override fun configureFamiliar() {
        definePlugin(object : OptionHandler() {
            override fun newInstance(arg: Any?): Plugin<Any> {
                for (i in ids) {
                    NPCDefinition.forId(i).handlers["option:burrow"] = this
                }
                return this
            }

            override fun handle(player: Player, node: Node, option: String): Boolean {
                val rock = getClosestRock(player)
                if (!player.familiarManager.isOwner(node as Familiar)) {
                    return true
                }
                if ((node as NPC).locks.isMovementLocked) {
                    return true
                }
                if (rock == null) {
                    player.packetDispatch.sendMessage("There are no rocks around here for the desert wyrm to mine from!")
                    return true
                }
                val resource = MiningNode.forId(rock.id)
                if (resource == null) {
                    player.packetDispatch.sendMessage("There are no rocks around here for the desert wyrm to mine from!")
                    return true
                }
                player.lock(9)
                node.lock(8)
                node.visualize(Animation(7800), Graphic(1412))
                Pulser.submit(object : Pulse(1, player, node) {
                    var counter: Int = 0

                    override fun pulse(): Boolean {
                        when (++counter) {
                            4 -> node.isInvisible = true
                            8 -> {
                                node.call()
                                GroundItemManager.create(Item(resource.reward), node.location, player)
                                return true
                            }
                        }
                        return false
                    }
                })
                return true
            }


            fun getClosestRock(player: Player): Scenery? {
                val rocks: MutableList<Scenery> = ArrayList(20)
                for (k in 0..6) {
                    for (i in 0..3) {
                        val dir = Direction.get(i)
                        val loc = player.location.transform(dir.stepX * k, dir.stepY * k, 0)
                        val `object` = getObject(loc)
                        if (`object` != null && `object`.name == "Rocks") {
                            rocks.add(`object`)
                        }
                    }
                }
                var ordinal = 0
                var o: Scenery? = null
                for (r in rocks) {
                    val resource = MiningNode.forId(r.id)
                    if (resource != null && MiningNode.SILVER_ORE_0.ordinal > resource.ordinal && resource.ordinal > ordinal) {
                        ordinal = resource.ordinal
                        o = r
                    }
                }
                return o
            }
        })
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DESERT_WYRM_6831, NPCs.DESERT_WYRM_6832)
    }

}
