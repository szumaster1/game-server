package content.global.skill.production.runecrafting

import content.global.handlers.item.equipment.gloves.FOGGlovesManager.Companion.updateCharges
import content.global.skill.production.runecrafting.data.Altar
import content.global.skill.production.runecrafting.data.CombinationRune
import content.global.skill.production.runecrafting.data.Rune
import content.global.skill.production.runecrafting.data.Talisman
import core.Configuration
import core.api.*
import cfg.consts.Animations
import cfg.consts.Graphics
import cfg.consts.Items
import cfg.consts.Sounds
import core.Util
import core.game.container.impl.EquipmentContainer
import core.game.node.entity.impl.Animator.Priority
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.system.command.sets.STATS_BASE
import core.game.system.command.sets.STATS_RC
import core.game.world.GameWorld.ticks
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.tools.RandomFunction
import kotlin.math.max

/**
 * Runecrafting pulse
 *
 * @param altar Represents the altar used in the runecrafting process.
 * @param combination Indicates whether a combination of runes is being used.
 * @param combo Holds the specific combination of runes, if applicable.
*
 * @param player The player who is performing the runecrafting action.
 * @param node The item associated with the runecrafting action.
 */
class RunecraftingPulse(
    player: Player?, // The player involved in the runecrafting process, can be null.
    node: Item?, // The item that is being used in the runecrafting, can also be null.
    val altar: Altar, // The altar where the runecrafting takes place.
    private val combination: Boolean, // A flag indicating if a combination of runes is used.
    private val combo: CombinationRune? // The specific combination of runes, if any.
) : SkillPulse<Item?>(player, node) { // Inherits from SkillPulse, passing player and node.

    private val rune: Rune
    private var talisman: Talisman? = null

    init {
        this.rune = altar.rune!!
        this.resetAnimation = false
    }

    override fun checkRequirements(): Boolean {
        if (altar == Altar.ASTRAL) {
            if (!hasRequirement(player, "Lunar Diplomacy")) return false
        }
        if (altar == Altar.DEATH) {
            if (!hasRequirement(player, "Mourning's End Part II")) return false
        }
        if (altar == Altar.BLOOD) {
            if (!hasRequirement(player, "Legacy of Seergaze")) return false
        }
        if (!altar.isOurania && getStatLevel(player, Skills.RUNECRAFTING) < rune.level) {
            sendMessage(player, "You need a Runecrafting level of at least " + rune.level + " to craft this rune.")
            return false
        }
        if (combination && !player.inventory.containsItem(PURE_ESSENCE)) {
            sendMessage(player, "You need pure essence to craft this rune.")
            return false
        }
        if (!altar.isOurania && !rune.isNormal && !player.inventory.containsItem(PURE_ESSENCE)) {
            sendMessage(player, "You need pure essence to craft this rune.")
            return false
        }
        if (!altar.isOurania && rune.isNormal && !player.inventory.containsItem(PURE_ESSENCE) && !player.inventory.containsItem(RUNE_ESSENCE)) {
            sendMessage(player, "You need rune essence or pure essence in order to craft this rune.")
            return false
        }
        if (altar.isOurania && !player.inventory.containsItem(PURE_ESSENCE)) {
            sendMessage(player, "You need pure essence to craft this rune.")
            return false
        }
        if (combination && getStatLevel(player, Skills.RUNECRAFTING) < combo!!.level) {
            sendMessage(player, "You need a Runecrafting level of at least " + combo.level + " to combine this rune.")
            return false
        }
        if (node != null) {
            if (node!!.name.contains("rune") && !hasSpellImbue()) {
                val r = Rune.forItem(node!!)
                val t = Talisman.forName(r!!.name)
                if (!player.inventory.containsItem(t!!.talisman)) {
                    sendMessage(player, "You don't have the correct talisman to combine this rune.")
                    return false
                }
                talisman = t
            }
        }
        player.lock(4)
        return true
    }

    override fun animate() {
        visualize(player, ANIMATION, GRAPHIC)
        playAudio(player, Sounds.BIND_RUNES_2710)
    }

    override fun reward(): Boolean {
        if (!combination) {
            craft()
        } else {
            combine()
        }
        return true
    }

    override fun message(type: Int) {
        when (type) {
            1 -> if (altar != Altar.OURANIA) {
                sendMessage(player, "You bind the temple's power into " + (if (combination) combo!!.rune.name.lowercase() else rune.rune.name.lowercase() + "s."))
            } else {
                sendMessage(player, "You bind the temple's power into runes.")
            }
        }
    }

    /*
     * Method used to craft runes.
     */

    private fun craft() {
        val item = Item(essence.id, essenceAmount)
        val amount = player.inventory.getAmount(item)
        if (!altar.isOurania) {
            var total = 0
            for (j in 0 until amount) {
                /*
                 * since getMultiplier is stochastic, roll `amount` independent copies.
                 */
                total += multiplier
            }
            val i = Item(rune.rune.id, total)

            if (player.inventory.remove(item) && player.inventory.hasSpaceFor(i)) {
                player.inventory.add(i)
                player.incrementAttribute("/save:$STATS_BASE:$STATS_RC", amount)

                /*
                 * Fist of guthix gloves.
                 */
                var xp = rune.experience * amount
                if ((altar == Altar.AIR && inEquipment(player, Items.AIR_RUNECRAFTING_GLOVES_12863, 1))
                    || (altar == Altar.WATER && inEquipment(player, Items.WATER_RUNECRAFTING_GLOVES_12864, 1))
                    || (altar == Altar.EARTH && inEquipment(player, Items.EARTH_RUNECRAFTING_GLOVES_12865, 1))
                ) {
                    xp += xp * updateCharges(player, amount) / amount
                }
                rewardXP(player, Skills.RUNECRAFTING, xp)

                /*
                 * Achievement Diary handling.
                 */

                /*
                 * Craft some nature runes.
                 */
                if (altar == Altar.NATURE) {
                    finishDiaryTask(player, DiaryType.KARAMJA, 2, 3)
                }

                /*
                 * Craft 196 or more air runes simultaneously.
                 */
                if (altar == Altar.AIR && i.amount >= 196) {
                    finishDiaryTask(player, DiaryType.FALADOR, 2, 2)
                }

                /*
                 * Craft a water rune at the Water altar.
                 */
                if (altar == Altar.WATER && rune == Rune.WATER) {
                    finishDiaryTask(player, DiaryType.LUMBRIDGE, 1, 11)
                }

            }
        } else {
            if (player.inventory.remove(item)) {
                player.incrementAttribute("/save:$STATS_BASE:$STATS_RC", amount)
                for (i in 0 until amount) {
                    var rune: Rune? = null
                    while (rune == null) {
                        val temp = Rune.values()[RandomFunction.random(Rune.values().size)]
                        if (player.getSkills().getLevel(Skills.RUNECRAFTING) >= temp.level) {
                            rune = temp
                        } else {
                            if (RandomFunction.random(3) == 1) {
                                rune = temp
                            }
                        }
                    }
                    player.getSkills().addExperience(Skills.RUNECRAFTING, rune.experience * 2, true)
                    val runeItem = rune.rune
                    player.inventory.add(runeItem)
                }
            }
        }
    }

    /*
     * Method used to combine runes.
     */

    private fun combine() {
        val remove = if (node!!.name.contains("talisman")) node!! else if (talisman != null) talisman!!.talisman else Talisman.forName(Rune.forItem(node!!)!!.name)!!.talisman
        val imbued = hasSpellImbue()
        if (if (!imbued) player.inventory.remove(remove) else imbued) {
            var amount = 0
            val essenceAmt = player.inventory.getAmount(PURE_ESSENCE)
            val rune = if (node!!.name.contains("rune")) Rune.forItem(node!!)!!.talisman else Rune.forName(Talisman.forItem(node!!)!!.name)!!.rune
            val runeAmt = player.inventory.getAmount(rune)
            amount = if (essenceAmt > runeAmt) {
                runeAmt
            } else {
                essenceAmt
            }
            if (player.inventory.remove(Item(PURE_ESSENCE.id, amount)) && player.inventory.remove(Item(rune.id, amount))) {
                for (i in 0 until amount) {
                    if (RandomFunction.random(1, 3) == 1 || hasBindingNecklace()) {
                        player.inventory.add(Item(combo!!.rune.id, 1))
                        player.getSkills().addExperience(Skills.RUNECRAFTING, combo.experience, true)
                    }
                }
                if (hasBindingNecklace()) {
                    var chargeItem = player.equipment[EquipmentContainer.SLOT_AMULET].charge
                    chargeItem -= 1
                    sendMessage(player, "You have " + Util.convert(chargeItem - 1) + " charges left before your Binding necklace disintegrates.")
                    if (1000 - player.equipment[EquipmentContainer.SLOT_AMULET].charge > 14) {
                        player.equipment.remove(BINDING_NECKLACE, true)
                        sendMessage(player,"Your binding necklace crumbles into dust.")
                    }
                }
            }
        }
    }

    private fun hasSpellImbue(): Boolean {
        return player.getAttribute("spell:imbue", 0) > ticks
    }

    private val essenceAmount: Int
        get() {
            if (altar.isOurania && player.inventory.containsItem(PURE_ESSENCE)) {
                return player.inventory.getAmount(PURE_ESSENCE)
            }
            return if (!rune.isNormal && player.inventory.containsItem(PURE_ESSENCE)) {
                player.inventory.getAmount(PURE_ESSENCE)
            } else if (rune.isNormal && player.inventory.containsItem(PURE_ESSENCE)) {
                player.inventory.getAmount(PURE_ESSENCE)
            } else {
                player.inventory.getAmount(RUNE_ESSENCE)
            }
        }

    private val essence: Item
        /*
         * Gets the rune essence that needs to be defined.
         */
        get() {
            if (altar.isOurania && player.inventory.containsItem(PURE_ESSENCE)) {
                return PURE_ESSENCE
            }
            return if (!rune.isNormal && player.inventory.containsItem(PURE_ESSENCE)) {
                PURE_ESSENCE
            } else if (rune.isNormal && player.inventory.containsItem(PURE_ESSENCE)) {
                PURE_ESSENCE
            } else {
                RUNE_ESSENCE
            }
        }

    val multiplier: Int
        /*
         * Gets the multiplied amount of runes to make.
         */
        get() {
            if (altar.isOurania) {
                return 1
            }
            val rcLevel = player.getSkills().getLevel(Skills.RUNECRAFTING)
            val runecraftingFormulaRevision = Configuration.RUNECRAFTING_FORMULA_REVISION
            val lumbridgeDiary = player.achievementDiaryManager.getDiary(DiaryType.LUMBRIDGE)!!.isComplete(1)
            return getMultiplier(rcLevel, rune, runecraftingFormulaRevision, lumbridgeDiary)
        }

    /**
     * Has binding necklace
     *
     * @return
     *//*
     * Method used to check if the player has a binding necklace.
     */
    fun hasBindingNecklace(): Boolean {
        return player.equipment.containsItem(BINDING_NECKLACE)
    }

    companion object {
        private val RUNE_ESSENCE = Item(Items.RUNE_ESSENCE_1436)
        private val PURE_ESSENCE = Item(Items.PURE_ESSENCE_7936)
        private val BINDING_NECKLACE = Item(Items.BINDING_NECKLACE_5521)
        private val ANIMATION = Animation(Animations.OLD_RUNECRAFTING_791, Priority.HIGH)
        private val GRAPHIC = Graphic(Graphics.RUNECRAFTING_GRAPHIC_186, 100)

        /*
         * Gets multiplier.
         */
        fun getMultiplier(rcLevel: Int, rune: Rune, rcFormulaRevision: Int, lumbridgeDiary: Boolean): Int {
            val multipleLevels = rune.getMultiple()
            var i = 0
            for (level in multipleLevels!!) {
                if (rcLevel >= level) {
                    i++
                }
            }

            if (multipleLevels.size > i && rcFormulaRevision >= 573) {
                val a = max(multipleLevels[i - 1].toDouble(), rune.level.toDouble()).toInt()
                val b = multipleLevels[i]
                if (b <= 99 || rcFormulaRevision >= 581) {
                    val chance = (rcLevel.toDouble() - a.toDouble()) / (b.toDouble() - a.toDouble())
                    if (RandomFunction.random(0.0, 1.0) < chance) {
                        i += 1
                    }
                }
            }

            if ((lumbridgeDiary && ArrayList(listOf(Rune.AIR, Rune.WATER, Rune.FIRE, Rune.EARTH)).contains(rune)) &&
                RandomFunction.getRandom(10) == 0) { // approximately 10% chance.
                i += 1
            }

            return if (i != 0) i else 1
        }
    }
}
