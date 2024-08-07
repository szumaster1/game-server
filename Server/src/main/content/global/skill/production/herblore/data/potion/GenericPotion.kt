package content.global.skill.production.herblore.data.potion

import core.game.node.item.Item

/**
 * Generic potion
 *
 * @property base
 * @property ingredient
 * @property level
 * @property experience
 * @property product
 * @constructor Generic potion
 */
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
                base = potion.base,
                ingredient = potion.ingredient,
                level = potion.level,
                experience = 0.0,
                product = potion.potion
            )
        }

        fun transform(potion: FinishedPotion): GenericPotion {
            return GenericPotion(
                base = potion.unfinished.potion,
                ingredient = potion.ingredient,
                level = potion.level,
                experience = potion.experience,
                product = potion.potion
            )
        }
    }
}
