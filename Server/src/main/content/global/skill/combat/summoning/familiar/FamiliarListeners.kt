package content.global.skill.combat.summoning.familiar

import content.global.skill.combat.summoning.pet.Pet
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Familiar listeners.
 */
class FamiliarListeners : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.NPC, FOOD_ID[0], FAMILIAR_ID[1]) { player, used, with ->
            val f = with.asNpc() as Familiar as? Pet ?: return@onUseWith false
            player.familiarManager.eat(used.id, f)
            return@onUseWith true
        }
    }

    companion object {
        private val FOOD_ID = intArrayOf(221, 225, 313, 315, 317, 319, 321, 321, 325, 327, 329, 331, 333, 335, 339, 341, 345, 347, 349, 351, 353, 355, 359, 361, 363, 365, 371, 373, 377, 379, 383, 385, 389, 391, 395, 397, 526, 592, 1059, 1927, 1927, 1963, 1977, 2132, 2134, 2136, 2138, 2970, 7944, 7946, 9978, 9986, 10816, 12125, 12127, 12129, 12130, 13379,)
        private val FAMILIAR_ID = intArrayOf(761, 762, 763, 764, 765, 766, 3505, 3598, 6969, 7259, 7260, 6964, 7249, 7251, 6960, 7241, 7243, 6962, 7245, 7247, 6966, 7253, 7255, 6958, 7237, 7239, 6915, 7277, 7278, 7279, 7280, 7018, 7019, 7020, 6908, 7313, 7316, 6947, 7293, 7295, 7297, 7299, 6911, 7261, 7263, 7265, 7267, 7269, 6919, 7301, 7303, 7305, 7307, 6949, 6952, 6955, 6913, 7271, 7273, 6945, 7319, 7321, 7323, 7325, 7327, 6922, 6942, 7210, 7212, 7214, 7216, 7218, 7220, 7222, 7224, 7226, 6900, 6902, 6904, 6906, 768, 769, 770, 771, 772, 773, 3504, 8214, 6968, 7257, 7258, 6965, 7250, 7252, 6961, 7242, 7244, 6963, 7246, 7248, 6967, 7254, 7256, 6859, 7238, 7240, 6916, 7281, 7282, 7283, 7284, 7015, 7016, 7017, 6909, 7314, 7317, 11413, 6948, 7294, 7296, 7298, 7300, 6912, 7262, 7264, 7266, 7268, 7270, 6920, 7302, 7304, 7306, 7308, 6950, 6953, 6956, 6914, 7272, 7274, 13090, 6946, 7320, 7322, 7324, 7326, 7328, 6923, 6943, 7211, 7213, 7215, 7217, 7219, 7221, 7223, 7225, 7227, 6901, 6903, 6905, 6907, 774, 775, 776, 777, 778, 779, 3503, 8216, 6951, 6954, 6957)

        val idMap = HashMap<Int, Int>()

        init {
            for (id in FOOD_ID) idMap[id] = 0
            for (id in FAMILIAR_ID) idMap[id] = 1

        }
    }
}