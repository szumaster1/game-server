package core.api.utils

import content.data.tables.*
import content.global.activity.treasuretrails.ClueLevel
import content.global.activity.treasuretrails.clue.ClueScrollPlugin
import content.global.handlers.item.equipment.gloves.FOGGlovesManager
import core.api.consts.Items
import core.api.inEquipment
import core.cache.def.impl.ItemDefinition
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.tools.RandomFunction

/**
 * Weight based table
 *
 * @constructor Weight based table
 */
open class WeightBasedTable : ArrayList<WeightedItem>() {
    var totalWeight = 0.0
    val guaranteedItems = ArrayList<WeightedItem>()

    override fun add(element: WeightedItem): Boolean {
        return if (element.guaranteed) {
            guaranteedItems.add(element)
        } else {
            totalWeight += element.weight
            val randIndex = RandomFunction.random(0, size)
            val end = this.size
            super.add(element)

            val temp = this[randIndex]
            this[randIndex] = element
            this[end] = temp
            true
        }
    }

    /**
     * Roll
     *
     * @param receiver
     * @return
     */
    open fun roll(receiver: Entity? = null): ArrayList<Item> {
        return roll(receiver, 1)
    }

    /**
     * Roll
     *
     * @param receiver
     * @param times
     * @return
     */
    open fun roll(receiver: Entity? = null, times: Int = 1): ArrayList<Item> {
        val items = ArrayList<WeightedItem>((guaranteedItems.size + 1) * times)

        for (i in 0 until times) {
            items.addAll(guaranteedItems)

            if (size == 1) {
                items.add(get(0))
            } else if (isNotEmpty()) {
                var rngWeight = RandomFunction.randomDouble(totalWeight)
                for (item in this) {
                    rngWeight -= item.weight
                    if (rngWeight <= 0) {
                        items.add(item)
                        break
                    }
                }
            }
        }

        return convertWeightedItems(items, receiver)
    }

    /**
     * Convert weighted items
     *
     * @param weightedItems
     * @param receiver
     * @return
     */
    fun convertWeightedItems(weightedItems: ArrayList<WeightedItem>, receiver: Entity?): ArrayList<Item> {
        val safeItems = ArrayList<Item>()
        for (e in weightedItems) {
            val safeItem = when (e.id) {
                SLOT_CLUE_EASY -> ClueScrollPlugin.getClue(ClueLevel.EASY)
                SLOT_CLUE_MEDIUM -> ClueScrollPlugin.getClue(ClueLevel.MEDIUM)
                SLOT_CLUE_HARD -> ClueScrollPlugin.getClue(ClueLevel.HARD)
                SLOT_RDT -> RareDropTable.retrieve(receiver)
                SLOT_CELEDT -> CELEMinorTable.retrieve(receiver)
                SLOT_USDT -> UncommonSeedDropTable.retrieve(receiver)
                SLOT_HDT -> {
                    if (RandomFunction.nextBool() && receiver is Player) {
                        if (inEquipment(receiver, Items.IRIT_GLOVES_12856)) {
                            FOGGlovesManager.updateCharges(receiver)
                            Item(Items.GRIMY_IRIT_209)
                        } else if (inEquipment(receiver, Items.AVANTOE_GLOVES_12857)) {
                            FOGGlovesManager.updateCharges(receiver)
                            Item(Items.GRIMY_AVANTOE_211)
                        } else if (inEquipment(receiver, Items.KWUARM_GLOVES_12858)) {
                            FOGGlovesManager.updateCharges(receiver)
                            Item(Items.GRIMY_KWUARM_213)
                        } else if (inEquipment(receiver, Items.CADANTINE_GLOVES_12859)) {
                            FOGGlovesManager.updateCharges(receiver)
                            Item(Items.GRIMY_CADANTINE_215)
                        } else
                            HerbDropTable.retrieve(receiver)
                    } else {
                        HerbDropTable.retrieve(receiver)
                    }
                }

                SLOT_GDT -> GemDropTable.retrieve(receiver)
                SLOT_RSDT -> RareSeedDropTable.retrieve(receiver)
                SLOT_ASDT -> AllotmentSeedDropTable.retrieve(receiver)
                Items.DWARF_REMAINS_0 -> continue
                else -> e.getItem()
            }
            safeItems.add(safeItem ?: continue)
        }
        return safeItems
    }

    /**
     * Can roll
     *
     * @param player
     * @return
     */
    open fun canRoll(player: Player): Boolean {
        val guaranteed = guaranteedItems.map { it.getItem() }.toTypedArray()
        return (guaranteed.isNotEmpty() && player.inventory.hasSpaceFor(*guaranteed)) || !player.inventory.isFull
    }

    /**
     * Insert easy clue
     *
     * @param weight
     * @return
     */
    fun insertEasyClue(weight: Double): WeightBasedTable {
        this.add(WeightedItem(SLOT_CLUE_EASY, 1, 1, weight, false))
        return this
    }

    /**
     * Insert medium clue
     *
     * @param weight
     * @return
     */
    fun insertMediumClue(weight: Double): WeightBasedTable {
        this.add(WeightedItem(SLOT_CLUE_MEDIUM, 1, 1, weight, false))
        return this
    }

    /**
     * Insert hard clue
     *
     * @param weight
     * @return
     */
    fun insertHardClue(weight: Double): WeightBasedTable {
        this.add(WeightedItem(SLOT_CLUE_HARD, 1, 1, weight, false))
        return this
    }

    /**
     * Insert r d t roll
     *
     * @param weight
     * @return
     */
    fun insertRDTRoll(weight: Double): WeightBasedTable {
        this.add(WeightedItem(SLOT_RDT, 1, 1, weight, false))
        return this
    }

    /**
     * Insert c e l e d t roll
     *
     * @param weight
     * @return
     */
    fun insertCELEDTRoll(weight: Double): WeightBasedTable {
        this.add(WeightedItem(SLOT_CELEDT, 1, 1, weight, false))
        return this
    }

    /**
     * Insert s e e d d t roll
     *
     * @param weight
     * @return
     */
    fun insertSEEDDTRoll(weight: Double): WeightBasedTable {
        this.add(WeightedItem(SLOT_USDT, 1, 1, weight, false))
        return this
    }

    /**
     * Insert h e r b d t roll
     *
     * @param weight
     * @return
     */
    fun insertHERBDTRoll(weight: Double): WeightBasedTable {
        this.add(WeightedItem(SLOT_HDT, 1, 1, weight, false))
        return this
    }

    /**
     * Insert g d t roll
     *
     * @param weight
     * @return
     */
    fun insertGDTRoll(weight: Double): WeightBasedTable {
        this.add(WeightedItem(SLOT_GDT, 1, 1, weight, false))
        return this
    }

    /**
     * Insert r s d t roll
     *
     * @param weight
     * @return
     */
    fun insertRSDTRoll(weight: Double): WeightBasedTable {
        this.add(WeightedItem(SLOT_RSDT, 1, 1, weight, false))
        return this
    }

    /**
     * Insert a s d t roll
     *
     * @param weight
     * @return
     */
    fun insertASDTRoll(weight: Double): WeightBasedTable {
        this.add(WeightedItem(SLOT_ASDT, 1, 1, weight, false))
        return this
    }

    companion object {
        @JvmStatic
        fun create(vararg items: WeightedItem): WeightBasedTable {
            val table = WeightBasedTable()
            items.forEach {
                table.add(it)
            }
            return table
        }

        @JvmField
        val SLOT_RDT = Items.TINDERBOX_31
        val SLOT_CLUE_EASY = Items.TOOLKIT_1
        val SLOT_CLUE_MEDIUM = Items.ROTTEN_POTATO_5733
        val SLOT_CLUE_HARD = Items.GRANITE_LOBSTER_POUCH_12070
        val SLOT_CELEDT = Items.NULL_799
        val SLOT_USDT = Items.SACRED_CLAY_POUCH_CLASS_1_14422
        val SLOT_HDT = Items.SACRED_CLAY_POUCH_CLASS_2_14424
        val SLOT_GDT = Items.SACRED_CLAY_POUCH_CLASS_3_14426
        val SLOT_RSDT = Items.SACRED_CLAY_POUCH_CLASS_4_14428
        val SLOT_ASDT = Items.SACRED_CLAY_POUCH_CLASS_5_14430
    }

    override fun toString(): String {
        val builder = StringBuilder()
        for (item in this) {
            builder.append("${ItemDefinition.forId(item.id).name} || Weight: ${item.weight} || MinAmt: ${item.minAmt} || maxAmt: ${item.maxAmt}")
            builder.appendLine()
        }
        return builder.toString()
    }
}
