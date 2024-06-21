package content.global.random.event.quizmaster

import core.api.consts.Items
import core.cache.def.impl.ItemDefinition
import core.game.interaction.InteractionListener
import core.utilities.RandomFunction

class QuizMasterEvent : InteractionListener {
    var score = 0
    var isStartedQuiz = false
    fun resetScore() {
        score = 0
    }

    fun incrementScore() {
        score++
    }

    override fun defineListeners() {}
}

enum class QuizSet(vararg val ids: Int) {
    FISH(Items.NULL_6189, Items.NULL_6190),
    WEAPONRY(Items.NULL_6191, Items.NULL_6192),
    ARMOUR(Items.NULL_6193, Items.NULL_6194),
    TOOLS(Items.NULL_6195, Items.NULL_6196),
    JEWELLERY(Items.NULL_6197, Items.NULL_6198);

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
