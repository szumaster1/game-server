package content.global.skill.production.fletching

import cfg.consts.Items
import content.global.skill.production.fletching.data.*
import content.global.skill.production.fletching.data.BowString
import content.global.skill.production.fletching.item.*
import core.api.*
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.system.task.Pulse
import kotlin.math.min

/**
 * Handles creating a product from ingredients.
 */
class FletchingListeners : InteractionListener {

    private val arrowShaftId = Items.ARROW_SHAFT_52
    private val fletchedShaftId = Items.HEADLESS_ARROW_53
    private val fligtedOgreArrowId = Items.FLIGHTED_OGRE_ARROW_2865
    private val featherIds = arrayOf(Items.BLUE_FEATHER_10089, Items.FEATHER_314, Items.ORANGE_FEATHER_10091, Items.RED_FEATHER_10088, Items.STRIPY_FEATHER_10087, Items.YELLOW_FEATHER_10090).toIntArray()
    private val stringIds = arrayOf(Items.BOW_STRING_1777, Items.CROSSBOW_STRING_9438).toIntArray()
    private val kebbitSpikeIds = arrayOf(Items.KEBBIT_SPIKE_10105, Items.LONG_KEBBIT_SPIKE_10107).toIntArray()
    private val gemIds = arrayOf(Items.OYSTER_PEARL_411, Items.OYSTER_PEARLS_413, Items.OPAL_1609, Items.JADE_1611, Items.RED_TOPAZ_1613, Items.SAPPHIRE_1607, Items.EMERALD_1605, Items.RUBY_1603, Items.DIAMOND_1601, Items.DRAGONSTONE_1615, Items.ONYX_6573).toIntArray()
    private val limbIds = Limb.values().map(Limb::limb).toIntArray()
    private val stockIds = Limb.values().map(Limb::stock).toIntArray()
    private val nailIds = BrutalArrow.values().map(BrutalArrow::base).toIntArray()
    private val unfinishedArrows = ArrowHead.values().map(ArrowHead::unfinished).toIntArray()
    private val unstrungBows = BowString.values().map(BowString::unfinished).toIntArray()
    private val boltBaseIds = GemBolt.values().map { it.base }.toIntArray()
    private val boltTipIds = GemBolt.values().map { it.tip }.toIntArray()

    override fun defineListeners() {

        /*
         * Stringing handler.
         */

        onUseWith(IntType.ITEM, stringIds, *unstrungBows) { player, string, bow ->
            handleStringing(player, string.asItem(), bow.asItem())
            return@onUseWith true
        }

        /*
         * Unfinished arrow handler.
         */

        onUseWith(IntType.ITEM, arrowShaftId, *featherIds) { player, shaft, feather ->
            openSkillDialogue(player, fletchedShaftId) { amount ->
                submitIndividualPulse(player, HeadlessArrowPulse(player, Item(shaft.id), Item(feather.id), amount))
            }
            return@onUseWith true
        }

        /*
         * Arrows handler.
         */

        onUseWith(IntType.ITEM, fletchedShaftId, *unfinishedArrows) { player, shaft, unfinished ->
            val head = ArrowHead.productMap[unfinished.id] ?: return@onUseWith false
            openSkillDialogue(player, head.finished) { amount ->
                submitIndividualPulse(player, ArrowHeadPulse(player, Item(shaft.id), head, amount))
            }
            return@onUseWith true
        }

        /*
         * Handle the ogre arrows.
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
         * Handles the creation of grapples.
         */

        onUseWith(IntType.ITEM, Items.MITHRIL_BOLTS_9142, Items.MITH_GRAPPLE_TIP_9416) { player, used, with ->
            checkRequirements(player, 59, Items.MITH_GRAPPLE_9418) {
                player.inventory.remove(Item(used.id), Item(with.id))
            }
            return@onUseWith true
        }
        onUseWith(IntType.ITEM, Items.ROPE_954, Items.MITH_GRAPPLE_9418) { player, used, with ->
            checkRequirements(player, 59, Items.MITH_GRAPPLE_9419) {
                player.inventory.remove(Item(used.id), Item(with.id))
            }
            return@onUseWith true
        }

        /*
         * Handles limb attachment to stocks.
         */

        onUseWith(IntType.ITEM, limbIds, *stockIds) { player, limb, stock ->
            val limbEnum = Limb.productMap[stock.id] ?: return@onUseWith false
            if (limbEnum.limb != limb.id) {
                sendMessage(player, "That's not the right limb to attach to that stock.")
                return@onUseWith true
            }
            openSkillDialogue(player, limbEnum.product) { amount ->
                submitIndividualPulse(player, LimbPulse(player, Item(stock.id), limbEnum, amount))
            }
            return@onUseWith true
        }

        /*
         * Handles the creation of gem-tipped bolts.
         */

        onUseWith(IntType.ITEM, Items.CHISEL_1755, *gemIds) { player, used, with ->
            val gem = GemBolt.productMap[with.id] ?: return@onUseWith true
            openSkillDialogue(player, gem.gem) { amount ->
                submitIndividualPulse(player, GemBoltCutPulse(player, Item(used.id), gem, amount))
            }
            return@onUseWith true
        }
        onUseWith(IntType.ITEM, boltBaseIds, *boltTipIds) { player, used, with ->
            val bolt = GemBolt.productMap[with.id] ?: return@onUseWith true
            if (used.id != bolt.base || with.id != bolt.tip) return@onUseWith true
            openSkillDialogue(player, bolt.product) { amount ->
                submitIndividualPulse(player, GemBoltPulse(player, Item(used.id), bolt, amount))
            }
            return@onUseWith true
        }

        /*
         * Handles the creation of kebbit bolts.
         */

        onUseWith(IntType.ITEM, Items.CHISEL_1755, *kebbitSpikeIds) { player, _, base ->
            openSkillDialogue(player, base.id) { amount ->
                submitIndividualPulse(
                    player,
                    KebbitBoltPulse(player, Item(base.id), KebbitBolt.forId(base.asItem())!!, amount)
                )
            }
            return@onUseWith true
        }

        /*
         * Handles the creation of flighted ogre arrows.
         */

        onUseWith(IntType.ITEM, Items.OGRE_ARROW_SHAFT_2864, *featherIds) { player, used, with ->
            val maxAmount = min(amountInInventory(player, used.id), amountInInventory(player, with.id))
            submitIndividualPulse(player, object : Pulse(3) {
                override fun pulse(): Boolean {
                    val amountThisIter = min(6, maxAmount)
                    if (removeItem(player, Item(Items.OGRE_ARROW_SHAFT_2864, amountThisIter)) && removeItem(player = player, item = Item(with.id, amountThisIter))) {
                        player.inventory.add(Item(fligtedOgreArrowId, amountThisIter))
                    }
                    return true
                }
            })
            return@onUseWith true
        }


        /*
         * Handles the creation of brutal arrows.
         */

        onUseWith(IntType.ITEM, fletchedShaftId, *nailIds) { player, used, with ->
            val brutalArrow = BrutalArrow.productMap[with.id] ?: return@onUseWith false
            openSkillDialogue(player, brutalArrow.product) { amount ->
                submitIndividualPulse(
                    player,
                    BrutalArrowPulse(player, Item(used.id), brutalArrow, amount)
                )
            }
            return@onUseWith true
        }
    }

    /*
     * Handles stringing of bows.
     */

    private fun handleStringing(player: Player, string: Item, bow: Item): Boolean {
        val enum = BowString.productMap[bow.id] ?: return false
        if (enum.string != string.id) {
            sendMessage(player, "That's not the right kind of string for this.")
            return true
        }
        openSkillDialogue(player, enum.product) { amount ->
            submitIndividualPulse(player, StringPulse(player, Item(string.id), enum, amount))
        }
        return true
    }

    /*
     * Opens a skill dialogue and runs the provided pulse creation logic.
     */

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

    /*
     * Checks the player's level and performs crafting if the level is sufficient.
     */

    private fun checkRequirements(player: Player, requiredLevel: Int, productId: Int, craftAction: () -> Unit): Boolean {
        if (getStatLevel(player, Skills.FLETCHING) < requiredLevel) {
            sendMessage(player, "You need a fletching level of $requiredLevel to make this.")
            return true
        }
        craftAction()
        addItem(player, productId)
        return true
    }

}
