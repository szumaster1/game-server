package content.global.skill.production.smithing

import core.api.consts.Items
import core.game.node.item.Item

enum class GodswordShardCombination(
    val item: Item,
    val product: Array<Item>
) {
    SHARDS_1_AND_2(Item(Items.GODSWORD_SHARDS_11686), arrayOf(Item(Items.GODSWORD_SHARD_1_11710), Item(Items.GODSWORD_SHARD_2_11712))),
    SHARDS_1_AND_3(Item(Items.GODSWORD_SHARDS_11688), arrayOf(Item(Items.GODSWORD_SHARD_1_11710), Item(Items.GODSWORD_SHARD_3_11714))),
    SHARDS_2_AND_3(Item(Items.GODSWORD_SHARDS_11692), arrayOf(Item(Items.GODSWORD_SHARD_2_11712), Item(Items.GODSWORD_SHARD_3_11714))),
    BLADE_1(Item(Items.GODSWORD_BLADE_11690), arrayOf(Item(Items.GODSWORD_SHARDS_11686), Item(Items.GODSWORD_SHARD_3_11714))),
    BLADE_2(Item(Items.GODSWORD_BLADE_11690), arrayOf(Item(Items.GODSWORD_SHARDS_11688), Item(Items.GODSWORD_SHARD_2_11712))),
    BLADE_3(Item(Items.GODSWORD_BLADE_11690), arrayOf(Item(Items.GODSWORD_SHARDS_11692), Item(Items.GODSWORD_SHARD_1_11710)));

    companion object {
        private val VALUES = values()

        fun getCombination(items: Collection<Item>): GodswordShardCombination? {
            for (combination in VALUES) {
                if (items.containsAll(combination.product.toList())) {
                    return combination
                }
            }
            return null
        }
    }
}