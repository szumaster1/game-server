package content.global.skill.production.herblore.data.potion

import core.game.node.item.Item

/**
 * Represents a generic potion which is used to transform incoming data
 * to represent a potion (finished or unfinished).
 */
class GenericPotion(
    val base: Item?,
    val ingredient: Item?,
    val level: Int,
    val experience: Double,
    val product: Item?
) {
    companion object {
        /**
         * Get transform an [UnfinishedPotion] into a [GenericPotion].
         *
         * @param potion the potion to transform.
         * @return the transformed potion.
         */
        fun transform(potion: UnfinishedPotion): GenericPotion {
            return GenericPotion(
                base = potion.base,
                ingredient = potion.ingredient,
                level = potion.level,
                experience = 0.0,
                product = potion.potion
            )
        }

        /**
         * Get transform a [FinishedPotion] into a [GenericPotion].
         *
         * @param potion the potion to transform.
         * @return the transformed potion.
         */
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
