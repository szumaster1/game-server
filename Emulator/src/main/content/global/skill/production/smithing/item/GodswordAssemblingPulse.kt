package content.global.skill.production.smithing.item

import core.api.*
import cfg.consts.Animations
import cfg.consts.Items
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Represents the godsword assembling into a godsword blade.
 *
 * @param player    the player who is assembling the godsword.
 * @param used      the used item itd.
 */
class GodswordAssemblingPulse(player: Player?, val used: Item) : SkillPulse<Item>(player, null) {

    private val animation = Animations.HUMAN_ANVIL_HAMMER_SMITHING_898
    var tick = 0

    override fun checkRequirements(): Boolean {
        return true
    }

    override fun start() {
        super.start()
    }

    override fun animate() {

    }

    override fun reward(): Boolean {
        when (tick++) {
            0 -> {
                animate(player, animation)
                tick++
            }

            5 -> {
                animate(player, animation)
                tick++
            }

            9 -> {
                var passBlade = true
                var remove = -1
                if (used.id == Items.GODSWORD_SHARDS_11692 && inInventory(player, Items.GODSWORD_SHARD_1_11710, 1)) {
                    passBlade = false
                    remove = Items.GODSWORD_SHARD_1_11710
                }
                if (used.id == Items.GODSWORD_SHARD_1_11710 && anyInInventory(player, Items.GODSWORD_SHARDS_11692)) {
                    passBlade = false
                    remove = Items.GODSWORD_SHARDS_11692
                }
                if (used.id == Items.GODSWORD_SHARDS_11688 && inInventory(player, Items.GODSWORD_SHARD_2_11712, 1)) {
                    passBlade = false
                    remove = Items.GODSWORD_SHARD_2_11712
                }
                if (used.id == Items.GODSWORD_SHARD_2_11712 && anyInInventory(player, Items.GODSWORD_SHARDS_11688)) {
                    passBlade = false
                    remove = Items.GODSWORD_SHARDS_11688
                }
                if (used.id == Items.GODSWORD_SHARDS_11686 && inInventory(player, Items.GODSWORD_SHARD_3_11714, 1)) {
                    passBlade = false
                    remove = Items.GODSWORD_SHARD_3_11714
                }
                if (used.id == Items.GODSWORD_SHARD_3_11714 && anyInInventory(player, Items.GODSWORD_SHARDS_11686)) {
                    passBlade = false
                    remove = Items.GODSWORD_SHARDS_11686
                }
                if (!passBlade) {
                    if (removeItem(player, used.id) && removeItem(player, Item(remove))) {
                        sendDialogueLines(player, "Even for an experienced smith it is not an easy task, but eventually", "it is done.")
                        rewardXP(player, Skills.SMITHING, 100.0)
                        addItem(player, Items.GODSWORD_BLADE_11690)
                    }
                    return true
                }
                var base = -1
                if (used.id == Items.GODSWORD_SHARD_1_11710) {
                    if (inInventory(player, Items.GODSWORD_SHARD_1_11710, 1)) {
                        base = Items.GODSWORD_SHARD_2_11712
                    } else if (inInventory(player, Items.GODSWORD_SHARD_3_11714, 1)) {
                        base = Items.GODSWORD_SHARD_3_11714
                    }
                }
                if (used.id == Items.GODSWORD_SHARD_2_11712) {
                    if (inInventory(player, Items.GODSWORD_SHARD_1_11710, 1)) {
                        base = Items.GODSWORD_SHARD_1_11710
                    } else if (inInventory(player, Items.GODSWORD_SHARD_3_11714, 1)) {
                        base = Items.GODSWORD_SHARD_3_11714
                    }
                }
                if (used.id == Items.GODSWORD_SHARD_3_11714) {
                    if (inInventory(player, Items.GODSWORD_SHARD_2_11712, 1)) {
                        base = Items.GODSWORD_SHARD_2_11712
                    } else if (inInventory(player, Items.GODSWORD_SHARD_1_11710, 1)) {
                        base = Items.GODSWORD_SHARD_1_11710
                    }
                }
                if (base == -1) {
                    sendMessage(player, "You didn't have all the required items.")
                    return false
                }
                if (player.inventory.remove(Item(used.id)) && player.inventory.remove(Item(base))) {
                    var shard = -1
                    if (used.id == Items.GODSWORD_SHARD_1_11710 && base == Items.GODSWORD_SHARD_2_11712 || used.id == Items.GODSWORD_SHARD_2_11712 && base == Items.GODSWORD_SHARD_1_11710) {
                        shard = Items.GODSWORD_SHARDS_11686
                    } else if (used.id == Items.GODSWORD_SHARD_1_11710 && base == Items.GODSWORD_SHARD_3_11714 || used.id == Items.GODSWORD_SHARD_3_11714 && base == Items.GODSWORD_SHARD_1_11710) {
                        shard = Items.GODSWORD_SHARDS_11688
                    }
                    if (used.id == Items.GODSWORD_SHARD_2_11712 && base == Items.GODSWORD_SHARD_3_11714 || used.id == Items.GODSWORD_SHARD_3_11714 && base == Items.GODSWORD_SHARD_2_11712) {
                        shard = Items.GODSWORD_SHARDS_11692
                    }
                    sendDialogueLines(player, "Even for an experienced smith it is not an easy task, but eventually", "it is done.")
                    rewardXP(player, Skills.SMITHING, 100.0)
                    addItem(player, shard)
                    return true
                }
            }
        }
        return false
    }

    override fun message(type: Int) {

    }
}