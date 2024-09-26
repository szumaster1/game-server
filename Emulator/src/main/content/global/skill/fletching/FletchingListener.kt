package content.global.skill.fletching

import content.global.skill.fletching.items.arrow.*
import content.global.skill.fletching.items.bolt.*
import content.global.skill.fletching.items.bow.BowString
import content.global.skill.fletching.items.bow.StringPulse
import content.global.skill.fletching.items.crossobw.Limb
import content.global.skill.fletching.items.crossobw.LimbPulse
import core.api.*
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.system.task.Pulse
import org.rs.consts.Items
import kotlin.math.min

class FletchingListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handles fletch the unstrung bow.
         */

        onUseWith(IntType.ITEM, FletchingMap.stringIds, *FletchingMap.unstrungBows) { player, string, bow ->
            val enum = BowString.product[bow.id] ?: return@onUseWith false
            if (enum.string != string.id) {
                sendMessage(player, "That's not the right kind of string for this.")
                return@onUseWith true
            }
            openSkillDialogue(player, enum.product) { amount ->
                submitIndividualPulse(
                    entity = player,
                    pulse = StringPulse(player, Item(string.id), enum, amount)
                )
            }
            return@onUseWith true
        }

        /*
         * Handles fletch the headless arrows.
         */

        onUseWith(IntType.ITEM, FletchingMap.arrowShaftId, *FletchingMap.featherIds) { player, shaft, feather ->
            openSkillDialogue(player, FletchingMap.fletchedShaftId) { amount ->
                submitIndividualPulse(
                    entity = player,
                    pulse = HeadlessArrowPulse(player, Item(shaft.id), Item(feather.id), amount)
                )
            }
            return@onUseWith true
        }

        /*
         * Handles fletch the arrows.
         */

        onUseWith(IntType.ITEM, FletchingMap.fletchedShaftId, *FletchingMap.unfinishedArrows) { player, shaft, unfinished ->
            val head = ArrowHead.product[unfinished.id] ?: return@onUseWith false
            openSkillDialogue(player, head.finished) { amount ->
                submitIndividualPulse(
                    entity = player,
                    pulse = ArrowHeadPulse(player, Item(shaft.id), head, amount)
                )
            }
            return@onUseWith true
        }

        /*
         * Handles fletch the ogre arrows.
         */

        onUseWith(IntType.ITEM, Items.WOLFBONE_ARROWTIPS_2861, Items.FLIGHTED_OGRE_ARROW_2865) { player, used, with ->
            fun getMaxAmount(_unused: Int = 0): Int {
                val tips = amountInInventory(player, Items.WOLFBONE_ARROWTIPS_2861)
                val shafts = amountInInventory(player, Items.FLIGHTED_OGRE_ARROW_2865)
                return min(tips, shafts)
            }

            fun process() {
                val amountThisIter = min(6, getMaxAmount())
                if (removeItem(player, Item(used.id, amountThisIter)) && removeItem(player, Item(with.id, amountThisIter))) {
                    addItem(player, Items.OGRE_ARROW_2866, amountThisIter)
                    rewardXP(player, Skills.FLETCHING, 6.0)
                    sendMessage(player, "You make $amountThisIter ogre arrows.")
                }
            }

            sendSkillDialogue(player) {
                create { _, amount ->
                    runTask(player, delay = 2, repeatTimes = min(amount, getMaxAmount() / 6 + 1), task = ::process)
                }
                calculateMaxAmount(::getMaxAmount)
                withItems(Item(Items.OGRE_ARROW_2866, 5))
            }
            return@onUseWith true
        }

        /*
         * Handles fletch the mithril grapple base.
         */

        onUseWith(IntType.ITEM, Items.MITHRIL_BOLTS_9142, Items.MITH_GRAPPLE_TIP_9416) { player, used, tip ->
            if (getStatLevel(player, Skills.FLETCHING) < 59) {
                sendMessage(player, "You need a fletching level of 59 to make this.")
                return@onUseWith true
            }
            if (removeItem(player, used.asItem()) && removeItem(player, tip.asItem())) {
                addItem(player, Items.MITH_GRAPPLE_9418)
            }
            return@onUseWith true
        }


        /*
         * Handles fletch the mithril grapple.
         */

        onUseWith(IntType.ITEM, Items.ROPE_954, Items.MITH_GRAPPLE_9418) { player, rope, grapple ->
            if (getStatLevel(player, Skills.FLETCHING) < 59) {
                sendMessage(player, "You need a fletching level of 59 to make this.")
                return@onUseWith true
            }
            if (player.inventory.remove(rope.asItem(), grapple.asItem())) {
                addItem(player, Items.MITH_GRAPPLE_9419)
            }
            return@onUseWith true
        }

        /*
         * Handles fletch the unstrung crossbow.
         */

        onUseWith(IntType.ITEM, FletchingMap.limbIds, *FletchingMap.stockIds) { player, limb, stock ->
            val limbEnum = Limb.product[stock.id] ?: return@onUseWith false
            if (limbEnum.limb != limb.id) {
                sendMessage(player, "That's not the right limb to attach to that stock.")
                return@onUseWith true
            }
            openSkillDialogue(player, limbEnum.product) { amount ->
                submitIndividualPulse(
                    entity = player,
                    pulse = LimbPulse(player, Item(stock.id), limbEnum, amount)
                )
            }
            return@onUseWith true
        }

        /*
         * Handles fletch the bolt tips.
         */

        onUseWith(IntType.ITEM, Items.CHISEL_1755, *FletchingMap.gemIds) { player, used, with ->
            val gem = GemBolt.product[with.id] ?: return@onUseWith true
            openSkillDialogue(player, gem.gem) { amount ->
                submitIndividualPulse(
                    entity = player,
                    pulse = GemBoltCutPulse(player, Item(used.id), gem, amount)
                )
            }
            return@onUseWith true
        }

        /*
         * Handles fletch the gem bolts.
         */

        onUseWith(IntType.ITEM, FletchingMap.boltBaseIds, *FletchingMap.boltTipIds) { player, used, with ->
            val bolt = GemBolt.product[with.id] ?: return@onUseWith true
            if (used.id != bolt.base || with.id != bolt.tip) return@onUseWith true
            openSkillDialogue(player, bolt.product) { amount ->
                submitIndividualPulse(
                    entity = player,
                    pulse = GemBoltPulse(player, Item(used.id), bolt, amount)
                )
            }
            return@onUseWith true
        }

        /*
         * Handles fletch the kebbit bolts.
         */

        onUseWith(IntType.ITEM, Items.CHISEL_1755, *FletchingMap.kebbitSpikeIds) { player, _, base ->
            openSkillDialogue(player, base.id) { amount ->
                submitIndividualPulse(
                    entity = player,
                    pulse = KebbitBoltPulse(player, Item(base.id), KebbitBolt.forId(base.asItem())!!, amount)
                )
            }
            return@onUseWith true
        }

        /*
         * Handles fletch the ogre arrows.
         */

        onUseWith(IntType.ITEM, Items.OGRE_ARROW_SHAFT_2864, *FletchingMap.featherIds) { player, used, with ->
            val maxAmount = min(amountInInventory(player, used.id), amountInInventory(player, with.id))
            submitIndividualPulse(player, object : Pulse(3) {
                override fun pulse(): Boolean {
                    val amountThisIter = min(6, maxAmount)
                    if (removeItem(player, Item(Items.OGRE_ARROW_SHAFT_2864, amountThisIter)) && removeItem(player, Item(with.id, amountThisIter))) {
                        player.inventory.add(Item(FletchingMap.fligtedOgreArrowId, amountThisIter))
                    }
                    return true
                }
            })
            return@onUseWith true
        }

        /*
         * Handles fletch the brutal arrows.
         */

        onUseWith(IntType.ITEM, FletchingMap.fletchedShaftId, *FletchingMap.nailIds) { player, used, with ->
            val brutalArrow = BrutalArrow.product[with.id] ?: return@onUseWith false
            openSkillDialogue(player, brutalArrow.product) { amount ->
                submitIndividualPulse(
                    entity = player,
                    pulse = BrutalArrowPulse(player, Item(used.id), brutalArrow, amount)
                )
            }
            return@onUseWith true
        }
    }

    private fun openSkillDialogue(player: Player, productId: Int, createPulse: (Int) -> Unit) {
        val handler = object : SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, Item(productId)) {
            override fun create(amount: Int, index: Int) {
                createPulse(amount)
            }

            override fun getAll(index: Int): Int {
                return amountInInventory(player, productId)
            }
        }
        handler.open()
    }

}
