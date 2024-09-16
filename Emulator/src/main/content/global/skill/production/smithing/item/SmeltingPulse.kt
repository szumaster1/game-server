package content.global.skill.production.smithing.item

import content.global.skill.production.smithing.data.Bar
import core.api.*
import cfg.consts.Animations
import cfg.consts.Graphics
import cfg.consts.Items
import cfg.consts.Sounds
import core.game.container.impl.EquipmentContainer
import core.game.event.ResourceProducedEvent
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.update.flag.context.Graphic
import core.tools.RandomFunction
import core.tools.StringUtils

/**
 * Represents the smelting pulse.
 */
class SmeltingPulse : SkillPulse<Item?> {

    private val bar: Bar?
    private val superHeat: Boolean
    private var ticks = 0
    private var amount: Int

    constructor(player: Player?, node: Item?, bar: Bar?, amount: Int) : super(player, node) {
        this.bar = bar
        this.amount = amount
        this.superHeat = false
    }

    constructor(player: Player?, node: Item?, bar: Bar?, amount: Int, heat: Boolean) : super(player, node) {
        this.bar = bar
        this.amount = amount
        this.superHeat = heat
        this.resetAnimation = false
    }

    override fun checkRequirements(): Boolean {
        closeChatBox(player)
        if (bar == null || player == null) {
            return false
        }
        if (bar == Bar.BLURITE && !isQuestComplete(player, "The Knight's Sword")) {
            return false
        }
        if (getStatLevel(player, Skills.SMITHING) < bar.level) {
            sendMessage(player, "You need a Smithing level of at least " + bar.level + " in order to smelt " + bar.product.name.lowercase().replace("bar", "") + ".")
            closeChatBox(player)
            return false
        }
        for (item in bar.ores) {
            if (!inInventory(player, item.id, item.amount)) {
                sendMessage(player, "You do not have the required ores to make this bar.")
                return false
            }
        }
        return true
    }

    override fun animate() {
        if (ticks == 0 || ticks % 5 == 0) {
            if (superHeat) {
                visualize(player, Animations.HUMAN_CAST_SUPERHEAT_SPELL_725, Graphic(Graphics.SUPER_HEAT_148, 96))
            } else {
                animate(player, Animations.HUMAN_FURNACE_SMELTING_3243)
                playAudio(player, Sounds.FURNACE_2725)
            }
        }
    }

    override fun reward(): Boolean {
        if (!superHeat && ++ticks % 5 != 0) {
            return false
        }
        if (!superHeat) {
            sendMessage(player, "You place the required ores and attempt to create a bar of " + StringUtils.formatDisplayName(bar.toString().lowercase()) + ".")
        }
        for (i in bar!!.ores) {
            if (!removeItem(player, i)) {
                return true
            }
        }
        if (success(player)) {

            var amt = if (((freeSlots(player) != 0 && !superHeat && withinDistance(player, Location(3107, 3500, 0)) && player.inventory.containsItems(*bar.ores)) && player.achievementDiaryManager.getDiary(DiaryType.VARROCK)!!.level != -1 && player.achievementDiaryManager.checkSmithReward(bar) && RandomFunction.random(100) <= 10)) 2 else 1
            if (amt != 1) {
                if (!removeItem(player, bar.ores)) {
                    amt = 1
                } else {
                    sendMessage(player, "The magic of the Varrock armour enables you to smelt 2 bars at the same time.")
                }
            }
            addItem(player, bar.product.id, amt)
            player.dispatch(ResourceProducedEvent(bar.product.id, 1, player, -1))
            var xp = bar.experience * amt

            if (((player.equipment[EquipmentContainer.SLOT_HANDS] != null && player.equipment[EquipmentContainer.SLOT_HANDS].id == Items.GOLDSMITH_GAUNTLETS_776)) && bar.product.id == Items.GOLD_BAR_2357) {
                xp = 56.2 * amt
            }
            rewardXP(player, Skills.SMITHING, xp)
            if (!superHeat) {
                sendMessage(player, "You retrieve a bar of " + bar.product.name.lowercase().replace(" bar", "") + ".")
            }
        } else {
            sendMessage(player, "The ore is too impure and you fail to refine it.")
        }
        amount--
        return amount < 1
    }

    /**
     * Checks if the player has the Forging Ring equipped.
     *
     * @param player The player whose equipment is being checked.
     * @return True if the player has the Forging Ring equipped, otherwise false.
     */
    fun hasForgingRing(player: Player): Boolean {
        return inEquipment(player, RING_OF_FORGING)
    }

    /**
     * Success of a forging.
     *
     * @param player The player attempting to forge an item.
     * @return Returns true if success, false otherwise.
     */
    fun success(player: Player): Boolean {
        if (bar == Bar.IRON && !superHeat) {
            return if (hasForgingRing(player)) {
                val ring = getItemFromEquipment(player, EquipmentSlot.RING)
                if (ring != null) {
                    if (getCharge(ring) == 1000) setCharge(ring, 140)
                    adjustCharge(ring, -1)
                    if (getCharge(ring) == 0) {
                        removeItem(player, ring)
                        sendMessage(player, "Your ring of forging uses up its last charge and disintegrates.")
                    }
                }
                true
            } else {
                RandomFunction.getRandom(100) <= (if (getStatLevel(player, Skills.SMITHING) >= 45) 80 else 50)
            }
        }
        return true
    }

    companion object {
        private val RING_OF_FORGING = Items.RING_OF_FORGING_2568
    }
}
