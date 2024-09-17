package core.game.bots.impl

import org.rs.consts.Items
import content.global.skill.production.fletching.FletchingMap
import content.global.skill.production.fletching.FletchingPulse
import core.game.bots.Script
import core.game.bots.SkillingBotAssembler
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Fletching bankstander.
 */
class FletchingBankstander : Script() {

    var state = State.FLETCHING
    override fun tick() {
        val bank = scriptAPI!!.getNearestNode("Bank booth")
        bot!!.faceLocation(bank?.location)
        state = when (state) {
            State.FLETCHING -> {
                bot!!.inventory.add(Item(Items.KNIFE_946))
                bot!!.inventory.add(Item(Items.LOGS_1511, 27))
                bot!!.pulseManager.run(
                    FletchingPulse(
                        bot!!, Item(Items.LOGS_1511), 27, FletchingMap.FletchingItems.ARROW_SHAFT
                    )
                )
                State.BANKING
            }

            State.BANKING -> {
                bot!!.inventory.clear()
                State.FLETCHING
            }
        }
    }

    init {
        skills[Skills.FLETCHING] == 99
    }

    override fun newInstance(): Script {
        val script = FletchingBankstander()
        script.bot = SkillingBotAssembler().produce(SkillingBotAssembler.Wealth.AVERAGE, bot!!.startLocation)
        return script
    }

    /**
     * State
     *
     * @constructor State
     */
    enum class State {
        /**
         * Fletching
         *
         * @constructor Fletching
         */
        FLETCHING,

        /**
         * Banking
         *
         * @constructor Banking
         */
        BANKING
    }
}