package content.global.skill.production.herblore.data.potion

import core.game.node.item.Item

/**
 * Generic potion
 *
 * @param base The base item used for the potion, can be null.
 * @param ingredient The additional ingredient for the potion, can be null.
 * @param level The level required to create the potion.
 * @param experience The experience gained from creating the potion.
 * @param product The resulting product of the potion creation, can be null.
 * @constructor Generic potion that initializes the potion with the provided parameters.
 */
class GenericPotion(
    val base: Item?, // The base item used for the potion, which can be null if not specified.
    val ingredient: Item?, // The ingredient that enhances the potion, also nullable.
    val level: Int, // The level required to craft this potion.
    val experience: Double, // The experience points awarded for crafting the potion.
    val product: Item? // The final product of the potion, which may be null if not applicable.
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
