package content.global.skill.production.fletching

import content.global.skill.production.fletching.data.*
import content.global.skill.production.fletching.data.BowString
import content.global.skill.production.fletching.item.*
import core.api.*
import cfg.consts.Components
import cfg.consts.Items
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.network.packet.PacketRepository
import core.network.packet.context.ChildPositionContext
import core.network.packet.outgoing.RepositionChild
import kotlin.math.min

/**
 * Fletching listeners.
 */
class FletchingListeners : InteractionListener {

    private val arrowShaftId = Items.ARROW_SHAFT_52
    private val fletchedShaftId = Items.HEADLESS_ARROW_53
    private val fligtedOgreArrowId = Items.FLIGHTED_OGRE_ARROW_2865
    private val featherIds = intArrayOf(Items.BLUE_FEATHER_10089, Items.FEATHER_314, Items.ORANGE_FEATHER_10091, Items.RED_FEATHER_10088, Items.STRIPY_FEATHER_10087, Items.YELLOW_FEATHER_10090)
    private val stringIds = intArrayOf(Items.BOW_STRING_1777, Items.CROSSBOW_STRING_9438)
    private val kebbitSpikeIds = intArrayOf(Items.KEBBIT_SPIKE_10105, Items.LONG_KEBBIT_SPIKE_10107)
    private val gemIds = intArrayOf(Items.OYSTER_PEARL_411, Items.OYSTER_PEARLS_413, Items.OPAL_1609, Items.JADE_1611, Items.RED_TOPAZ_1613, Items.SAPPHIRE_1607, Items.EMERALD_1605, Items.RUBY_1603, Items.DIAMOND_1601, Items.DRAGONSTONE_1615, Items.ONYX_6573)
    private val limbIds = Limb.values().map(Limb::limb).toIntArray()
    private val stockIds = Limb.values().map(Limb::stock).toIntArray()
    private val nailIds = BrutalArrow.values().map(BrutalArrow::base).toIntArray()
    private val unfinishedArrows = ArrowHead.values().map(ArrowHead::unfinished).toIntArray()
    private val unstrungBows = BowString.values().map(BowString::unfinished).toIntArray()
    private val boltBaseIds = GemBolt.values().map { it.base }.toIntArray()
    private val boltTipIds = GemBolt.values().map { it.tip }.toIntArray()

    override fun defineListeners() {

        /**
         * String.
         */
        onUseWith(IntType.ITEM, stringIds, *unstrungBows) { player, string, bow ->
            val enum = BowString.productMap[bow.id] ?: return@onUseWith false
            if (enum.string != string.id) {
                sendMessage(player, "That's not the right kind of string for this.")
                return@onUseWith true
            }
            val handler: SkillDialogueHandler =
                object : SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, Item(enum.product)) {
                    override fun create(amount: Int, index: Int) {
                        player.pulseManager.run(
                            StringPulse(player,
                                Item(string.id), enum, amount
                            )
                        )
                    }

                    override fun getAll(index: Int): Int {
                        return player.inventory.getAmount(string.id)
                    }
                }
            handler.open()
            PacketRepository.send(
                RepositionChild::class.java, ChildPositionContext(player, Components.SKILL_MULTI1_309, 2, 215, 10)
            )
            return@onUseWith true
        }

        /**
         * Headless arrows.
         */
        onUseWith(IntType.ITEM, arrowShaftId, *featherIds) { player, shaft, feather ->
            val handler: SkillDialogueHandler =
                object : SkillDialogueHandler(player, SkillDialogue.MAKE_SET_ONE_OPTION, Item(fletchedShaftId)) {
                    override fun create(amount: Int, index: Int) {
                        player.pulseManager.run(
                            HeadlessArrowPulse(
                                player, Item(shaft.id), Item(feather.id), amount
                            )
                        )
                    }
                    override fun getAll(index: Int): Int {
                        return player.inventory.getAmount(fletchedShaftId)
                    }
                }
            handler.open()
            return@onUseWith true
        }

        /**
         * Arrow heads.
         */
        onUseWith(IntType.ITEM, fletchedShaftId, *unfinishedArrows) { player, shaft, unfinished ->
            val head = ArrowHead.productMap[unfinished.id] ?: return@onUseWith false
            val handler: SkillDialogueHandler =
                object : SkillDialogueHandler(player, SkillDialogue.MAKE_SET_ONE_OPTION, Item(head.finished)) {
                    override fun create(amount: Int, index: Int) {
                        player.pulseManager.run(
                            ArrowHeadPulse(player,
                                Item(shaft.id), head, amount
                            )
                        )
                    }

                    override fun getAll(index: Int): Int {
                        return player.inventory.getAmount(head.unfinished)
                    }
                }
            handler.open()
            return@onUseWith true
        }

        /**
         * Grapple.
         */
        onUseWith(IntType.ITEM, Items.MITHRIL_BOLTS_9142, Items.MITH_GRAPPLE_TIP_9416) { player, used, with ->
            if (getStatLevel(player, Skills.FLETCHING) < 59) {
                sendMessage(player, "You need a fletching level of 59 to make this.")
                return@onUseWith true
            }
            if (player.inventory.remove(Item(used.id, 1), Item(with.id))) {
                player.inventory.add(Item(Items.MITH_GRAPPLE_9418))
            }
            return@onUseWith true
        }

        onUseWith(IntType.ITEM, Items.ROPE_954, Items.MITH_GRAPPLE_9418) { player, used, with ->
            if (getStatLevel(player, Skills.FLETCHING) < 59) {
                sendMessage(player, "You need a fletching level of 59 to make this.")
                return@onUseWith true
            }
            if (player.inventory.remove(Item(used.id), Item(with.id))) {
                addItem(player, Items.MITH_GRAPPLE_9419)
            }
            return@onUseWith true
        }

        /**
         * Limbs.
         */
        onUseWith(IntType.ITEM, limbIds, *stockIds) { player, limb, stock ->
            val limbEnum = Limb.productMap[stock.id] ?: return@onUseWith false
            if (limbEnum.limb != limb.id) {
                sendMessage(player, "That's not the right limb to attach to that stock.")
                return@onUseWith true
            }
            val handler: SkillDialogueHandler =
                object : SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, Item(limbEnum.product)) {
                    override fun create(amount: Int, index: Int) {
                        player.pulseManager.run(
                            LimbPulse(player,
                                Item(stock.id), limbEnum, amount
                            )
                        )
                    }

                    override fun getAll(index: Int): Int {
                        return player.inventory.getAmount(stock.id)
                    }
                }
            handler.open()
            PacketRepository.send(
                RepositionChild::class.java, ChildPositionContext(player, Components.SKILL_MULTI1_309, 2, 210, 10)
            )
            return@onUseWith true
        }

        /**
         * Bolt gem tips.
         */
        onUseWith(IntType.ITEM, Items.CHISEL_1755, *gemIds) { player, used, with ->
            val gem = GemBolt.productMap[with.id] ?: return@onUseWith true
            object : SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, Item(gem.gem)) {
                override fun create(amount: Int, index: Int) {
                    player.pulseManager.run(
                        GemBoltCutPulse(player,
                            Item(used.id), gem, amount
                        )
                    )
                }

                override fun getAll(index: Int): Int {
                    return player.inventory.getAmount(gem.gem)
                }
            }.open()
            return@onUseWith true
        }

        /**
         * Bolt tips.
         */
        onUseWith(IntType.ITEM, boltBaseIds, *boltTipIds) { player, used, with ->
            val bolt = GemBolt.productMap[with.id] ?: return@onUseWith true
            if (used.id != bolt.base || with.id != bolt.tip) return@onUseWith true
            val handler: SkillDialogueHandler =
                object : SkillDialogueHandler(player, SkillDialogue.MAKE_SET_ONE_OPTION, Item(bolt.product)) {
                    override fun create(amount: Int, index: Int) {
                        player.pulseManager.run(
                            GemBoltPulse(player,
                                Item(used.id), bolt, amount
                            )
                        )
                    }

                    override fun getAll(index: Int): Int {
                        return min(amountInInventory(player, used.id), amountInInventory(player, with.id))
                    }
                }
            handler.open()
            return@onUseWith true
        }

        /**
         * Kebbit bolts.
         */
        onUseWith(IntType.ITEM, Items.CHISEL_1755, *kebbitSpikeIds) { player, _, base ->
            val bolt = KebbitBolt.productMap[base.id] ?: return@onUseWith false
            val handler: SkillDialogueHandler =
                object : SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, Item(bolt.product)) {
                    override fun create(amount: Int, index: Int) {
                        player.pulseManager.run(
                            KebbitBoltPulse(player,
                                Item(bolt.base), bolt, amount
                            )
                        )
                    }

                    override fun getAll(index: Int): Int {
                        return player.inventory.getAmount(bolt.base)
                    }
                }
            handler.open()
            PacketRepository.send(
                RepositionChild::class.java, ChildPositionContext(player, Components.SKILL_MULTI1_309, 2, 210, 10)
            )
            return@onUseWith true
        }

        /**
         * Brutal arrows.
         */
        onUseWith(IntType.ITEM, Items.OGRE_ARROW_SHAFT_2864, *featherIds) { player, used, with ->
            val shaftAmount = amountInInventory(player, used.id)
            val featherAmount = amountInInventory(player, with.id)
            var maxAmount = min(shaftAmount, featherAmount)

            submitIndividualPulse(player, object : Pulse(3) {
                override fun pulse(): Boolean {
                    val iterAmount = min(maxAmount, 6)
                    val amountThisIter = min(6, maxAmount)
                    if (removeItem(player, Item(Items.OGRE_ARROW_SHAFT_2864, iterAmount)) && removeItem(player, Item(with.id, iterAmount))) {
                        addItem(player, Items.FLIGHTED_OGRE_ARROW_2865, iterAmount)
                        rewardXP(player, Skills.FLETCHING, 0.9 * iterAmount)
                        maxAmount -= iterAmount
                        sendMessage(player, "You attach $amountThisIter feathers to $iterAmount ogre arrow shafts.")
                    }
                    return maxAmount == 0
                }
            })

            return@onUseWith true
        }

        onUseWith(IntType.ITEM, Items.WOLF_BONES_2859, Items.CHISEL_1755) { player, used, _ ->
            val maxAmount = amountInInventory(player, used.id)

            if (getQuestStage(player, "Big Chompy Bird Hunting") == 0) {
                sendMessage(player, "You must have started Big Chompy Bird Hunting to make these.")
                return@onUseWith true
            }

            if (getStatLevel(player, Skills.FLETCHING) < 5) {
                sendMessage(player, "You need a Fletching level of 5 to make these.")
                return@onUseWith true
            }

            fun process() {
                if (removeItem(player, Item(used.id))) {
                    addItem(player, Items.WOLFBONE_ARROWTIPS_2861, 4)
                    rewardXP(player, Skills.FLETCHING, 2.5)
                    sendMessage(player, "You chisel 4 wolf bone arrow tips.")
                }
            }

            sendSkillDialogue(player) {
                create { _, amount ->
                    var actualAmount = min(amount, maxAmount)
                    runTask(player, delay = 2, repeatTimes = actualAmount, task = ::process)
                }
                withItems(Item(Items.WOLFBONE_ARROWTIPS_2861, 5))
            }

            return@onUseWith true
        }

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

        onUseWith(IntType.ITEM, fligtedOgreArrowId, *nailIds) { player, used, with ->
            val brutal = BrutalArrow.productMap[with.id] ?: return@onUseWith false
            val handler: SkillDialogueHandler =
                object : SkillDialogueHandler(player, SkillDialogue.MAKE_SET_ONE_OPTION, Item(brutal.product)) {
                    override fun create(amount: Int, index: Int) {
                        player.pulseManager.run(
                            BrutalArrowPulse(player,
                                Item(used.id), brutal, amount
                            )
                        )
                    }

                    override fun getAll(index: Int): Int {
                        return min(amountInInventory(player, used.id), amountInInventory(player, with.id))
                    }
                }
                handler.open()
            return@onUseWith true
        }
    }
}
