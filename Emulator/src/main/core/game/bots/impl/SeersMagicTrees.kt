package core.game.bots.impl

import org.rs.consts.Items
import core.game.bots.*
import core.game.interaction.DestinationFlag
import core.game.interaction.IntType
import core.game.interaction.InteractionListeners
import core.game.interaction.MovementPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders
import core.tools.RandomFunction

/**
 * Seers magic trees.
 */
@PlayerCompatible
@ScriptName("Seers Magics")
@ScriptDescription("Start in Seers Bank with an axe equipped or in your inventory.")
@ScriptIdentifier("seers_magics")
class SeersMagicTrees : Script() {
    var state = State.INIT
    var stage = 0
    val bankZone = ZoneBorders(2722, 3490, 2727, 3493)
    val magicsZone = ZoneBorders(2700, 3396, 2704, 3399)
    var overlay: ScriptAPI.BottingOverlay? = null
    var logCounter = 0

    override fun tick() {
        when (state) {

            State.INIT -> {
                overlay = scriptAPI!!.getOverlay()
                overlay!!.init()
                overlay!!.setTitle("Woodcutting")
                overlay!!.setTaskLabel("Logs cut:")
                overlay!!.setAmount(0)
                state = State.RETURN_TO_TREES
            }

            State.CHOPPING -> {
                val tree = scriptAPI!!.getNearestNode(1306, true)
                bot!!.interfaceManager.close()
                tree?.let { InteractionListeners.run(tree.id, IntType.SCENERY, "Chop down", bot!!, tree) }
                if (bot!!.inventory.isFull) {
                    state = State.FIND_BANK
                }
                overlay!!.setAmount(logCounter + bot!!.inventory.getAmount(Items.MAGIC_LOGS_1513))
            }

            State.FIND_BANK -> {
                if (!bankZone.insideBorder(bot)) {
                    scriptAPI!!.walkTo(bankZone.randomLoc)
                } else {
                    state = State.BANKING
                }
            }

            State.BANKING -> {
                val bank = scriptAPI!!.getNearestNode(25808, true)
                if (bank != null) bot!!.pulseManager.run(object : MovementPulse(bot!!, bank, DestinationFlag.OBJECT) {
                    override fun pulse(): Boolean {
                        bot!!.faceLocation(bank.location)
                        logCounter += bot!!.inventory.getAmount(Items.MAGIC_LOGS_1513)
                        scriptAPI!!.bankItem(Items.MAGIC_LOGS_1513)
                        state = State.RETURN_TO_TREES
                        return true
                    }
                })
            }

            State.RETURN_TO_TREES -> {
                bot!!.interfaceManager.close()
                if (!magicsZone.insideBorder(bot)) {
                    scriptAPI!!.walkTo(magicsZone.randomLoc)
                } else {
                    state = State.CHOPPING
                }
            }

            State.TELE_GE -> {
                state = State.SELL_GE
                scriptAPI!!.teleportToGE()
            }

            State.SELL_GE -> {
                state = State.TELE_SEERS
                scriptAPI!!.sellOnGE(Items.MAGIC_LOGS_1513)
            }

            State.TELE_SEERS -> {
                state = State.RETURN_TO_TREES
                scriptAPI!!.teleport(Location.create(2756, 3478, 0))
            }
        }
    }

    init {
        inventory.add(Item(Items.RUNE_AXE_1359))
        skills[Skills.WOODCUTTING] == RandomFunction.random(75, 99)
    }

    /**
     * State
     *
     * @constructor State
     */
    enum class State {
        /**
         * Chopping
         *
         * @constructor Chopping
         */
        CHOPPING,

        /**
         * Find Bank
         *
         * @constructor Find Bank
         */
        FIND_BANK,

        /**
         * Banking
         *
         * @constructor Banking
         */
        BANKING,

        /**
         * Return To Trees
         *
         * @constructor Return To Trees
         */
        RETURN_TO_TREES,

        /**
         * Tele Ge
         *
         * @constructor Tele Ge
         */
        TELE_GE,

        /**
         * Sell Ge
         *
         * @constructor Sell Ge
         */
        SELL_GE,

        /**
         * Tele Seers
         *
         * @constructor Tele Seers
         */
        TELE_SEERS,

        /**
         * Init
         *
         * @constructor Init
         */
        INIT
    }

    override fun newInstance(): Script {
        val script = SeersMagicTrees()
        script.bot = SkillingBotAssembler().produce(SkillingBotAssembler.Wealth.AVERAGE, bot!!.startLocation)
        return script
    }
}
