package content.global.ame.quizmaster

import core.cache.def.impl.ItemDefinition
import core.game.interaction.InteractionListener
import core.tools.RandomFunction
import org.rs.consts.Items

/**
 * Quiz master event.
 */
class QuizMasterListener : InteractionListener {

    var score = 0
    var isStartedQuiz = false

    /**
     * Reset score.
     */
    fun resetScore() {
        score = 0
    }

    /**
     * Increment score.
     */
    fun incrementScore() {
        score++
    }

    override fun defineListeners() {}
}

/**
 * Quiz set.
 */
enum class QuizSet(vararg val ids: Int) {
    /**
     * Fish.
     */
    FISH(Items.NULL_6189, Items.NULL_6190),

    /**
     * Weaponry.
     */
    WEAPONRY(Items.NULL_6191, Items.NULL_6192),

    /**
     * Armour.
     */
    ARMOUR(Items.NULL_6193, Items.NULL_6194),

    /**
     * Tools.
     */
    TOOLS(Items.NULL_6195, Items.NULL_6196),

    /**
     * Jewellery.
     */
    JEWELLERY(Items.NULL_6197, Items.NULL_6198);

    /**
     * Get model.
     */
    fun getModel(index: Int): Int {
        return ItemDefinition.forId(ids[index]).interfaceModelId
    }

    companion object {
        val quizSet: Array<QuizSet?>
            get() {
                val sets: MutableList<QuizSet?> = ArrayList()
                for (s in values()) {
                    sets.add(s)
                }
                sets.shuffle()
                val set = arrayOfNulls<QuizSet>(2)
                set[0] = sets[0]
                sets.remove(set[0])
                set[1] = sets[RandomFunction.random(sets.size)]
                return set
            }
    }

}
