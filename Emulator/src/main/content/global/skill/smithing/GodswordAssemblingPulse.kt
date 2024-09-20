package content.global.skill.smithing

import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import org.rs.consts.Animations
import org.rs.consts.Items

/**
 * Represents the godsword assembling into a blade.
 */
class GodswordAssemblingPulse(player: Player?, val used: Item) : SkillPulse<Item>(player, null) {

    private val animation = Animations.HUMAN_ANVIL_HAMMER_SMITHING_898
    private var tick = 0

    override fun checkRequirements(): Boolean = true

    override fun start() {
        super.start()
    }

    override fun animate() {}

    override fun reward(): Boolean {
        when (tick++) {
            0, 5 -> animate(player, animation)

            9 -> {
                val (passBlade, remove) = checkBladeRequirements()
                if (!passBlade) {
                    if (removeItem(player, used.id) && removeItem(player, Item(remove))) {
                        sendDialogueLines(player, "Even for an experienced smith it is not an easy task, but eventually", "it is done.")
                        rewardXP(player, Skills.SMITHING, 100.0)
                        addItem(player, Items.GODSWORD_BLADE_11690)
                    }
                    return true
                }

                val base = findBaseItem() ?: run {
                    sendMessage(player, "You didn't have all the required items.")
                    return false
                }

                if (player.inventory.remove(Item(used.id)) && player.inventory.remove(Item(base))) {
                    val shard = determineShard(used.id, base)
                    if (shard != -1) {
                        sendDialogueLines(player, "Even for an experienced smith it is not an easy task, but eventually", "it is done.")
                        rewardXP(player, Skills.SMITHING, 100.0)
                        addItem(player, shard)
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun checkBladeRequirements(): Pair<Boolean, Int> {
        val bladeConditions = listOf(
            Items.GODSWORD_SHARDS_11692 to Items.GODSWORD_SHARD_1_11710,
            Items.GODSWORD_SHARD_1_11710 to Items.GODSWORD_SHARDS_11692,
            Items.GODSWORD_SHARDS_11688 to Items.GODSWORD_SHARD_2_11712,
            Items.GODSWORD_SHARD_2_11712 to Items.GODSWORD_SHARDS_11688,
            Items.GODSWORD_SHARDS_11686 to Items.GODSWORD_SHARD_3_11714,
            Items.GODSWORD_SHARD_3_11714 to Items.GODSWORD_SHARDS_11686
        )

        for ((shard, base) in bladeConditions) {
            if (used.id == shard && inInventory(player, base, 1) || used.id == base && anyInInventory(player, shard)) {
                return Pair(false, base)
            }
        }
        return Pair(true, -1)
    }

    private fun findBaseItem(): Int? {
        val baseConditions = listOf(
            Items.GODSWORD_SHARD_1_11710 to listOf(Items.GODSWORD_SHARD_2_11712, Items.GODSWORD_SHARD_3_11714),
            Items.GODSWORD_SHARD_2_11712 to listOf(Items.GODSWORD_SHARD_1_11710, Items.GODSWORD_SHARD_3_11714),
            Items.GODSWORD_SHARD_3_11714 to listOf(Items.GODSWORD_SHARD_1_11710, Items.GODSWORD_SHARD_2_11712)
        )

        for ((shard, bases) in baseConditions) {
            if (used.id == shard) {
                for (base in bases) {
                    if (inInventory(player, base, 1)) {
                        return base
                    }
                }
            }
        }
        return null
    }

    private fun determineShard(usedId: Int, baseId: Int): Int {
        return when {
            (usedId == Items.GODSWORD_SHARD_1_11710 && baseId == Items.GODSWORD_SHARD_2_11712) ||
                    (usedId == Items.GODSWORD_SHARD_2_11712 && baseId == Items.GODSWORD_SHARD_1_11710) -> Items.GODSWORD_SHARDS_11686

            (usedId == Items.GODSWORD_SHARD_1_11710 && baseId == Items.GODSWORD_SHARD_3_11714) ||
                    (usedId == Items.GODSWORD_SHARD_3_11714 && baseId == Items.GODSWORD_SHARD_1_11710) -> Items.GODSWORD_SHARDS_11688

            (usedId == Items.GODSWORD_SHARD_2_11712 && baseId == Items.GODSWORD_SHARD_3_11714) ||
                    (usedId == Items.GODSWORD_SHARD_3_11714 && baseId == Items.GODSWORD_SHARD_2_11712) -> Items.GODSWORD_SHARDS_11692

            else -> -1
        }
    }

    override fun message(type: Int) {}
}