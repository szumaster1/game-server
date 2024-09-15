package content.global.skill.production.herblore.data.potion

import core.game.node.item.Item

/**
 * Represents a generic potion which is used to transform incoming data to represent a potion (finished or unfinished).
 *
 * @param base          the base item used for the potion.
 * @param ingredient    the ingredient required for the potion.
 * @param level         the level required to craft this potion.
 * @param experience    the experience points awarded for crafting the potion.
 * @param product       the final product of the potion.
 * @return the [GenericPotion].
 */
class GenericPotion(val base: Item?, val ingredient: Item?, val level: Int, val experience: Double, val product: Item?) {

    companion object {
        /**
         * Method used to transform an unfinished potion into a generic potion.
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
         * Method used to transform a finished potion into a generic potion.
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
