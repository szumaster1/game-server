package core.game.bots.impl

import cfg.consts.Items
import core.game.bots.*
import core.game.interaction.DestinationFlag
import core.game.interaction.IntType
import core.game.interaction.InteractionListeners
import core.game.interaction.MovementPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.map.path.Pathfinder
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.tools.RandomFunction
import kotlin.random.Random

/**
 * Lobster catcher.
 */
@PlayerCompatible
@ScriptName("Catherby Lobs")
@ScriptDescription("Start in Catherby bank with a lobster pot in your inventory.")
@ScriptIdentifier("cath_lobs")
class LobsterCatcher : Script() {
    private val ANIMATION = Animation(714)
    val offers = HashMap<Int, Int>()
    val limit = 2000
    var myCounter = 0

    /**
     * Represents the graphics to use.
     */
    private val Graphic = Graphic(308, 100, 50)

    /**
     * Sets
     *
     * @param equipment
     * @constructor Sets
     */
    internal enum class Sets(val equipment: List<Item>) {
        /**
         * Set 1
         *
         * @constructor Set 1
         */
        SET_1(listOf(Item(2643), Item(9470), Item(10756), Item(10394), Item(88), Item(9793))),

        /**
         * Set 2
         *
         * @constructor Set 2
         */
        SET_2(listOf(Item(2643), Item(6585), Item(10750), Item(10394), Item(88), Item(9793))),

        /**
         * Set 3
         *
         * @constructor Set 3
         */
        SET_3(listOf(Item(9472), Item(9470), Item(10750), Item(10394), Item(88), Item(9786))),

        /**
         * Set 4
         *
         * @constructor Set 4
         */
        SET_4(listOf(Item(2639), Item(6585), Item(10752), Item(10394), Item(88), Item(9786))),

        /**
         * Set 5
         *
         * @constructor Set 5
         */
        SET_5(listOf(Item(2639), Item(9470), Item(10750), Item(10394), Item(88), Item(9784))),

        /**
         * Set 6
         *
         * @constructor Set 6
         */
        SET_6(listOf(Item(2639), Item(6585), Item(10750), Item(10394), Item(88), Item(9784)));
    }

    private var bots = 0
    private var lobstopper = false
    var overlay: ScriptAPI.BottingOverlay? = null
    var fishCounter = 0

    private var state = State.INIT
    private var tick = 0
    override fun tick() {
        when (state) {

            State.INIT -> {
                overlay = scriptAPI!!.getOverlay()
                overlay!!.init()
                overlay!!.setTitle("Fishing")
                overlay!!.setTaskLabel("Lobs Caught:")
                overlay!!.setAmount(0)
                state = State.FIND_SPOT
            }

            State.BANKING -> {
                fishCounter += bot!!.inventory.getAmount(Items.RAW_LOBSTER_377)
                scriptAPI!!.bankItem(Items.RAW_LOBSTER_377)
                state = State.IDLE
            }

            State.FISHING -> {
                bot!!.interfaceManager.close()
                val spot = scriptAPI!!.getNearestNode(333, false)
                if (spot == null) {
                    state = State.IDLE
                } else {
                    InteractionListeners.run(spot.id, IntType.NPC, "cage", bot!!, spot)
                }
                if (bot!!.inventory.isFull) {
                    state = State.FIND_BANK
                }
                overlay!!.setAmount(fishCounter + bot!!.inventory.getAmount(Items.RAW_LOBSTER_377))
            }

            State.IDLE -> {
                if (Random.nextBoolean()) {
                    state = State.FIND_SPOT
                } else if (myCounter++ >= RandomFunction.random(1, 25)) {
                    state = State.FIND_SPOT
                }
            }

            State.FIND_SPOT -> {
                val spot = scriptAPI!!.getNearestNode(333, false)
                if (spot != null) {
                    bot!!.walkingQueue.reset()
                    state = State.FISHING
                } else {
                    if (bot!!.location.x < 2837) {
                        scriptAPI!!.walkTo(Location.create(2837, 3435, 0))
                    } else {
                        scriptAPI!!.walkTo(Location.create(2854, 3427, 0))
                    }
                }
            }

            State.FIND_BANK -> {
                val bank = scriptAPI!!.getNearestGameObject(bot!!.location, 2213)

                class BankingPulse : MovementPulse(bot!!, bank, DestinationFlag.OBJECT) {
                    override fun pulse(): Boolean {
                        bot!!.faceLocation(bank!!.location)
                        state = State.BANKING
                        return true
                    }
                }
                if (bank != null) {
                    bot!!.pulseManager.run(BankingPulse())
                } else {
                    if (bot!!.location.x > 2837) {
                        Pathfinder.find(bot!!, Location.create(2837, 3435, 0)).walk(bot)
                    } else if (bot!!.location.x > 2821) {
                        Pathfinder.find(bot!!, Location.create(2821, 3435, 0)).walk(bot)
                    } else if (bot!!.location.x > 2809) {
                        Pathfinder.find(bot!!, Location.create(2809, 3436, 0)).walk(bot)
                    }
                }
            }

            State.TELEPORT_GE -> {
                scriptAPI!!.teleportToGE()
                state = State.SELL_GE
            }

            State.SELL_GE -> {
                scriptAPI!!.sellOnGE(Items.RAW_LOBSTER_377)
                state = State.TELE_CATH
            }

            State.TELE_CATH -> {
                if (tick++ == 10) {
                    bot!!.lock()
                    bot!!.visualize(ANIMATION, Graphic)
                    bot!!.impactHandler.disabledTicks = 4
                    val location = Location.create(2819, 3437, 0)
                    GameWorld.Pulser.submit(object : Pulse(4, bot) {
                        override fun pulse(): Boolean {
                            bot!!.unlock()
                            bot!!.properties.teleportLocation = location
                            bot!!.animator.reset()
                            state = State.IDLE
                            return true
                        }
                    })
                }
            }
        }
    }

    init {
        val setUp = RandomFunction.random(Sets.values().size)
        equipment.addAll(Sets.values()[setUp].equipment)
        inventory.add(Item(301))
        skills[Skills.FISHING] == 40
    }

    /**
     * State
     *
     * @constructor State
     */
    enum class State {
        /**
         * Fishing
         *
         * @constructor Fishing
         */
        FISHING,

        /**
         * Banking
         *
         * @constructor Banking
         */
        BANKING,

        /**
         * Find Bank
         *
         * @constructor Find Bank
         */
        FIND_BANK,

        /**
         * Find Spot
         *
         * @constructor Find Spot
         */
        FIND_SPOT,

        /**
         * Teleport Ge
         *
         * @constructor Teleport Ge
         */
        TELEPORT_GE,

        /**
         * Sell Ge
         *
         * @constructor Sell Ge
         */
        SELL_GE,

        /**
         * Tele Cath
         *
         * @constructor Tele Cath
         */
        TELE_CATH,

        /**
         * Idle
         *
         * @constructor Idle
         */
        IDLE,

        /**
         * Init
         *
         * @constructor Init
         */
        INIT
    }

    override fun newInstance(): Script {
        if (!lobstopper && bots <= 0) {
            val script = LobsterCatcher()
            script.bot = AIPlayer(bot!!.startLocation)
            script.state = State.FIND_SPOT
            bots = 1
            return script
        } else if (tick++ == 6000 && lobstopper) {
            tick = 0
            lobstopper = false
        }
        return newInstance()
    }
}
