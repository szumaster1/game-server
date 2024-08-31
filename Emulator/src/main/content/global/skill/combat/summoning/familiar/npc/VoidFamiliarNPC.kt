package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import content.global.skill.combat.summoning.familiar.Forager
import core.cache.def.impl.NPCDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillBonus
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.ClassScanner.definePlugin
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Void familiar NPC.
 */
@Initializable
class VoidFamiliarNPC : Plugin<Any> {

    override fun newInstance(arg: Any?): Plugin<Any> {
        definePlugin(VoidRavagerNPC())
        definePlugin(VoidShifterNPC())
        definePlugin(VoidSpinnerNPC())
        definePlugin(VoidTorcherNPC())
        return this
    }

    override fun fireEvent(identifier: String, vararg args: Any): Any? {
        return null
    }

    /**
     * Handles the call to arms scroll.
     *
     * @param familiar The familiar that is being summoned.
     * @param special An optional special ability associated with the familiar.
     * @return Returns true if the call to arms was successful, false otherwise.
     */
    fun callToArms(familiar: Familiar, special: FamiliarSpecial?): Boolean {
        val owner = familiar.owner
        owner.lock()
        Pulser.submit(object : Pulse(1, owner) {
            var counter: Int = 0

            override fun pulse(): Boolean {
                when (++counter) {
                    1 -> owner.visualize(Animation.create(8136), Graphic.create(1503))
                    3 -> {
                        owner.unlock()
                        owner.properties.teleportLocation = Location.create(2659, 2658, 0)
                        owner.visualize(Animation.create(8137), Graphic.create(1502))
                        return true
                    }
                }
                return false
            }
        })
        return true
    }

    /**
     * Void ravager NPC.
     */
    inner class VoidRavagerNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 7370) :
        Forager(owner, id, 2700, 12818, 3, WeaponInterface.STYLE_AGGRESSIVE, *ITEMS) {

        init {
            boosts.add(SkillBonus(Skills.MINING, 1.0))
        }

        override fun construct(owner: Player, id: Int): Familiar {
            return VoidRavagerNPC(owner, id)
        }

        override fun specialMove(special: FamiliarSpecial): Boolean {
            return callToArms(this, special)
        }

        override fun getIds(): IntArray {
            return intArrayOf(7370, 7371)
        }
    }

    /**
     * Void shifter NPC.
     */
    inner class VoidShifterNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 7367) :
        Familiar(owner, id, 9400, 12814, 3, WeaponInterface.STYLE_ACCURATE) {

        override fun construct(owner: Player, id: Int): Familiar {
            return VoidShifterNPC(owner, id)
        }

        override fun adjustPlayerBattle(state: BattleState) {
            super.adjustPlayerBattle(state)
            val percentage = (owner.getSkills().getStaticLevel(Skills.HITPOINTS) * 0.10).toInt()
            if (owner.getSkills().lifepoints < percentage) {
                owner.properties.teleportLocation = Location.create(2659, 2658, 0)
            }
        }

        override fun specialMove(special: FamiliarSpecial): Boolean {
            return callToArms(this, special)
        }

        override fun getIds(): IntArray {
            return intArrayOf(7367, 7368)
        }
    }


    /**
     * Void spinner NPC.
     */
    inner class VoidSpinnerNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 7333) :
        Familiar(owner, id, 2700, 12780, 3, WeaponInterface.STYLE_DEFENSIVE) {
        // The delay till the next heal.
        private var healDelay = 0

        public override fun handleFamiliarTick() {
            super.handleFamiliarTick()
            if (healDelay < GameWorld.ticks) {
                getSkills().heal(1)
                healDelay = GameWorld.ticks + 25
            }
        }

        override fun construct(owner: Player, id: Int): Familiar {
            return VoidSpinnerNPC(owner, id)
        }

        override fun specialMove(special: FamiliarSpecial): Boolean {
            return callToArms(this, special)
        }

        override fun getIds(): IntArray {
            return intArrayOf(7333, 7334)
        }
    }

    /**
     * Void torcher NPC.
     */
    inner class VoidTorcherNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 7351) :
        Familiar(owner, id, 9400, 12798, 3, WeaponInterface.STYLE_CAST) {
        override fun construct(owner: Player, id: Int): Familiar {
            return VoidTorcherNPC(owner, id)
        }

        public override fun configureFamiliar() {
            definePlugin(object : OptionHandler() {

                override fun newInstance(arg: Any?): Plugin<Any> {
                    for (i in ids) {
                        NPCDefinition.forId(i).handlers["option:strike"] = this
                    }
                    return this
                }

                override fun handle(player: Player, node: Node, option: String): Boolean {
                    val familiar = node as Familiar
                    if (!player.familiarManager.isOwner(familiar)) {
                        return true
                    }
                    // TODO:
                    return true
                }
            })
        }

        override fun specialMove(special: FamiliarSpecial): Boolean {
            return callToArms(this, special)
        }

        override fun getIds(): IntArray {
            return intArrayOf(7351, 7352)
        }
    }

    companion object {
        private val ITEMS = arrayOf(Item(434), Item(440), Item(453), Item(444), Item(447))
    }
}
