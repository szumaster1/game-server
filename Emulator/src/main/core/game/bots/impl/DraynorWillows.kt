package core.game.bots.impl

import core.game.bots.*
import core.game.component.Component
import core.game.interaction.DestinationFlag
import core.game.interaction.IntType
import core.game.interaction.InteractionListeners
import core.game.interaction.MovementPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.zone.ZoneBorders
import core.tools.colorize
import org.rs.consts.Items

/**
 * Draynor willows.
 */
@PlayerCompatible
@ScriptName("Draynor Willows")
@ScriptDescription("Start in Draynor with an axe equipped or in inventory.")
@ScriptIdentifier("draynor_trees")
class DraynorWillows : Script() {
    val willowZone = ZoneBorders(3084, 3225, 3091, 3239)
    val bankZone = ZoneBorders(3092, 3245, 3092, 3242)
    var state = State.INIT
    var logCount = 0

    override fun tick() {
        when (state) {
            State.INIT -> {
                if (true) {
                    bot!!.interfaceManager.openOverlay(Component(195))
                    bot!!.packetDispatch.sendString("Woodcutting", 195, 7)
                    bot!!.packetDispatch.sendString(colorize("%BLogs Chopped:"), 195, 8)
                    bot!!.packetDispatch.sendString(colorize("%B0"), 195, 9)
                    bot!!.packetDispatch.sendInterfaceConfig(195, 5, true)
                }
                state = State.CHOPPING
            }

            State.CHOPPING -> {
                if (!willowZone.insideBorder(bot)) scriptAPI!!.walkTo(willowZone.randomLoc)
                else {
                    val willowtree = scriptAPI!!.getNearestNode("willow", true)
                    bot!!.interfaceManager.close()
                    willowtree?.let {
                        InteractionListeners.run(
                            willowtree.id, IntType.SCENERY, "Chop down", bot!!, willowtree
                        )
                    }
                    if (bot!!.inventory.isFull) state = State.BANKING
                }

                bot!!.packetDispatch.sendString(
                    colorize("%B${logCount + bot!!.inventory.getAmount(Items.WILLOW_LOGS_1519)}"), 195, 9
                )
            }

            State.BANKING -> {
                if (!bankZone.insideBorder(bot)) scriptAPI!!.walkTo(bankZone.randomLoc)
                else {
                    val bank = scriptAPI!!.getNearestNode("Bank Booth", true)
                    if (bank != null) {
                        bot!!.pulseManager.run(object : MovementPulse(bot!!, bank, DestinationFlag.OBJECT) {
                            override fun pulse(): Boolean {
                                val logs = bot!!.inventory.getAmount(Item(Items.WILLOW_LOGS_1519))
                                logCount += logs
                                bot!!.inventory.remove(Item(Items.WILLOW_LOGS_1519, logs))
                                bot!!.bank.add(Item(Items.WILLOW_LOGS_1519, logs))
                                state = State.CHOPPING
                                return true
                            }
                        })
                    }
                }
            }


        }
    }

    init {
        inventory.add(Item(Items.ADAMANT_AXE_1357))
        skills[Skills.WOODCUTTING] == 35
    }

    override fun newInstance(): Script {
        val script = DraynorWillows()
        return script
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
         * Banking
         *
         * @constructor Banking
         */
        BANKING,

        /**
         * Init
         *
         * @constructor Init
         */
        INIT
    }
}
