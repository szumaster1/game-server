package content.global.skill.production.herblore.data.potion

import core.game.node.item.Item

class GenericPotion(
    val base: Item?,
    val ingredient: Item?,
    val level: Int,
    val experience: Double,
    val product: Item?
) {
    companion object {
        fun transform(potion: UnfinishedPotion): GenericPotion {
            return GenericPotion(
                potion.base,
                potion.ingredient,
                potion.level,
                0.0,
                potion.potion
            )
        }

        fun transform(potion: FinishedPotion): GenericPotion {
            return GenericPotion(
                potion.unfinished.potion,
                potion.ingredient,
                potion.level,
                potion.experience,
                potion.potion
            )
        }
    }
}
