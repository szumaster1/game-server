package content.global.skill.combat.summoning.pet;

import core.game.node.entity.player.Player;
import core.utilities.Log;

import java.util.HashMap;
import java.util.Map;

import static core.api.ContentAPIKt.log;

/**
 * The enum Pets.
 */
public enum Pets {
    /**
     * Cat pets.
     */
    CAT(1555, 1561, 1567, 761, 768, 774, 0.0154320987654321, 0, 321, 319, 363, 365, 341, 339, 345, 347, 377, 379, 353, 355, 389, 391, 7944, 7946, 349, 351, 331, 329, 327, 325, 395, 397, 383, 385, 317, 315, 371, 373, 335, 333, 359, 361, 1927),
    /**
     * Cat 1 pets.
     */
    CAT_1(1556, 1562, 1568, 762, 769, 775, 0.0154320987654321, 0, 321, 319, 363, 365, 341, 339, 345, 347, 377, 379, 353, 355, 389, 391, 7944, 7946, 349, 351, 331, 329, 327, 325, 395, 397, 383, 385, 317, 315, 371, 373, 335, 333, 359, 361, 1927),
    /**
     * Cat 2 pets.
     */
    CAT_2(1557, 1563, 1569, 763, 770, 776, 0.0154320987654321, 0, 321, 319, 363, 365, 341, 339, 345, 347, 377, 379, 353, 355, 389, 391, 7944, 7946, 349, 351, 331, 329, 327, 325, 395, 397, 383, 385, 317, 315, 371, 373, 335, 333, 359, 361, 1927),
    /**
     * Cat 3 pets.
     */
    CAT_3(1558, 1564, 1570, 764, 771, 777, 0.0154320987654321, 0, 321, 319, 363, 365, 341, 339, 345, 347, 377, 379, 353, 355, 389, 391, 7944, 7946, 349, 351, 331, 329, 327, 325, 395, 397, 383, 385, 317, 315, 371, 373, 335, 333, 359, 361, 1927),
    /**
     * Cat 4 pets.
     */
    CAT_4(1559, 1565, 1571, 765, 772, 778, 0.0154320987654321, 0, 321, 319, 363, 365, 341, 339, 345, 347, 377, 379, 353, 355, 389, 391, 7944, 7946, 349, 351, 331, 329, 327, 325, 395, 397, 383, 385, 317, 315, 371, 373, 335, 333, 359, 361, 1927),
    /**
     * Cat 5 pets.
     */
    CAT_5(1560, 1566, 1572, 766, 773, 779, 0.0154320987654321, 0, 321, 319, 363, 365, 341, 339, 345, 347, 377, 379, 353, 355, 389, 391, 7944, 7946, 349, 351, 331, 329, 327, 325, 395, 397, 383, 385, 317, 315, 371, 373, 335, 333, 359, 361, 1927),
    /**
     * Hellcat pets.
     */
    HELLCAT(7583, 7582, 7581, 3505, 3504, 3503, 0.0154320987654321, 0, 321, 319, 363, 365, 341, 339, 345, 347, 377, 379, 353, 355, 389, 391, 7944, 7946, 349, 351, 331, 329, 327, 325, 395, 397, 383, 385, 317, 315, 371, 373, 335, 333, 359, 361, 1927),
    /**
     * Cat 7 pets.
     */
    CAT_7(14089, 14090, 15092, 8217, 8214, 8216, 0.0154320987654321, 0, 321, 319, 363, 365, 341, 339, 345, 347, 377, 379, 353, 355, 389, 391, 7944, 7946, 349, 351, 331, 329, 327, 325, 395, 397, 383, 385, 317, 315, 371, 373, 335, 333, 359, 361, 1927),
    /**
     * Clockwork cat pets.
     */
    CLOCKWORK_CAT(7771, 7772, -1, 3598, -1, -1, 0.0, 0),
    /**
     * Bulldog pets.
     */
    BULLDOG(12522, 12523, -1, 6969, 6968, -1, 0.0033333333333333, 4, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 526),
    /**
     * Bulldog 1 pets.
     */
    BULLDOG_1(12720, 12721, -1, 7259, 7257, -1, 0.0033333333333333, 4, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 526),
    /**
     * Bulldog 2 pets.
     */
    BULLDOG_2(12722, 12723, -1, 7260, 7258, -1, 0.0033333333333333, 4, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 526),
    /**
     * Dalmatian pets.
     */
    DALMATIAN(12518, 12519, -1, 6964, 6965, -1, 0.0033333333333333, 4, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 526),
    /**
     * Dalmatian 1 pets.
     */
    DALMATIAN_1(12712, 12713, -1, 7249, 7250, -1, 0.0033333333333333, 4, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 526),
    /**
     * Dalmatian 2 pets.
     */
    DALMATIAN_2(12714, 12715, -1, 7251, 7252, -1, 0.0033333333333333, 4, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 526),
    /**
     * Greyhound pets.
     */
    GREYHOUND(12514, 12515, -1, 6960, 6961, -1, 0.0033333333333333, 4, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 526),
    /**
     * Greyhound 1 pets.
     */
    GREYHOUND_1(12704, 12705, -1, 7241, 7242, -1, 0.0033333333333333, 4, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 526),
    /**
     * Greyhound 2 pets.
     */
    GREYHOUND_2(12706, 12707, -1, 7243, 7244, -1, 0.0033333333333333, 4, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 526),
    /**
     * Labrador pets.
     */
    LABRADOR(12516, 12517, -1, 6962, 6963, -1, 0.0033333333333333, 4, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 526),
    /**
     * Labrador 1 pets.
     */
    LABRADOR_1(12708, 12709, -1, 7245, 7246, -1, 0.0033333333333333, 4, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 526),
    /**
     * Labrador 2 pets.
     */
    LABRADOR_2(12710, 12711, -1, 7247, 7248, -1, 0.0033333333333333, 4, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 526),
    /**
     * Sheepdog pets.
     */
    SHEEPDOG(12520, 12521, -1, 6966, 6967, -1, 0.0033333333333333, 4, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 526),
    /**
     * Sheepdog 1 pets.
     */
    SHEEPDOG_1(12716, 12717, -1, 7253, 7254, -1, 0.0033333333333333, 4, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 526),
    /**
     * Sheepdog 2 pets.
     */
    SHEEPDOG_2(12718, 12719, -1, 7255, 7256, -1, 0.0033333333333333, 4, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 526),
    /**
     * Terrier pets.
     */
    TERRIER(12512, 12513, -1, 6958, 6859, -1, 0.0033333333333333, 4, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 526),
    /**
     * Terrier 1 pets.
     */
    TERRIER_1(12700, 12701, -1, 7237, 7238, -1, 0.0033333333333333, 4, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 526),
    /**
     * Terrier 2 pets.
     */
    TERRIER_2(12702, 12703, -1, 7239, 7240, -1, 0.0033333333333333, 4, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 526),
    /**
     * Gecko pets.
     */
    GECKO(12488, 12489, -1, 6915, 6916, -1, 0.005, 10, 12125, 12127),
    /**
     * Gecko 1 pets.
     */
    GECKO_1(12738, 12742, -1, 7277, 7281, -1, 0.005, 10, 12125, 12127),
    /**
     * Gecko 2 pets.
     */
    GECKO_2(12739, 12743, -1, 7278, 7282, -1, 0.005, 10, 12125, 12127),
    /**
     * Gecko 3 pets.
     */
    GECKO_3(12740, 12744, -1, 7279, 7283, -1, 0.005, 10, 12125, 12127),
    /**
     * Gecko 4 pets.
     */
    GECKO_4(12741, 12745, -1, 7280, 7284, -1, 0.005, 10, 12125, 12127),
    /**
     * Platypus pets.
     */
    PLATYPUS(12551, 12548, -1, 7018, 7015, -1, 0.0046296296296296, 10, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359, 313, 12129),
    /**
     * Platypus 1 pets.
     */
    PLATYPUS_1(12552, 12549, -1, 7019, 7016, -1, 0.0046296296296296, 10, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359, 313, 12129),
    /**
     * Platypus 2 pets.
     */
    PLATYPUS_2(12553, 12550, -1, 7020, 7017, -1, 0.0046296296296296, 10, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359, 313, 12129),
    /**
     * Broav pets.
     */
    BROAV(14533, -1, -1, 8491, -1, -1, 0.0, 23, 2970),
    /**
     * Penguin pets.
     */
    PENGUIN(12481, 12482, -1, 6908, 6909, -1, 0.0046296296296296, 30, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359),
    /**
     * Penguin 1 pets.
     */
    PENGUIN_1(12763, 12762, -1, 7313, 7314, -1, 0.0046296296296296, 30, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359),
    /**
     * Penguin 2 pets.
     */
    PENGUIN_2(12765, 12764, -1, 7316, 7317, -1, 0.0046296296296296, 30, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359),
    /**
     * Giant crab pets.
     */
    GIANT_CRAB(12500, 12501, -1, 6947, 6948, -1, 0.0069444444444444, 40, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359),
    /**
     * Giant crab 1 pets.
     */
    GIANT_CRAB_1(12746, 12747, -1, 7293, 7294, -1, 0.0069444444444444, 40, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359),
    /**
     * Giant crab 2 pets.
     */
    GIANT_CRAB_2(12748, 12749, -1, 7295, 7296, -1, 0.0069444444444444, 40, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359),
    /**
     * Giant crab 3 pets.
     */
    GIANT_CRAB_3(12750, 12751, -1, 7297, 7298, -1, 0.0069444444444444, 40, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359),
    /**
     * Giant crab 4 pets.
     */
    GIANT_CRAB_4(12752, 12753, -1, 7299, 7300, -1, 0.0069444444444444, 40, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359),
    /**
     * Raven pets.
     */
    RAVEN(12484, 12485, -1, 6911, 6912, -1, 0.00698888, 50, 313, 12129),
    /**
     * Raven 1 pets.
     */
    RAVEN_1(12724, 12725, -1, 7261, 7262, -1, 0.00698888, 50, 313, 12129),
    /**
     * Raven 2 pets.
     */
    RAVEN_2(12726, 12727, -1, 7263, 7264, -1, 0.00698888, 50, 313, 12129),
    /**
     * Raven 3 pets.
     */
    RAVEN_3(12728, 12729, -1, 7265, 7266, -1, 0.00698888, 50, 313, 12129),
    /**
     * Raven 4 pets.
     */
    RAVEN_4(12730, 12731, -1, 7267, 7268, -1, 0.00698888, 50, 313, 12129),
    /**
     * Raven 5 pets.
     */
    RAVEN_5(12732, 12733, -1, 7269, 7270, -1, 0.00698888, 50, 313, 12129),
    /**
     * Squirrel pets.
     */
    SQUIRREL(12490, 12491, -1, 6919, 6920, -1, 0.0071225071225071, 60, 12130),
    /**
     * Squirrel 1 pets.
     */
    SQUIRREL_1(12754, 12755, -1, 7301, 7302, -1, 0.0071225071225071, 60, 12130),
    /**
     * Squirrel 2 pets.
     */
    SQUIRREL_2(12756, 12757, -1, 7303, 7304, -1, 0.0071225071225071, 60, 12130),
    /**
     * Squirrel 3 pets.
     */
    SQUIRREL_3(12758, 12759, -1, 7305, 7306, -1, 0.0071225071225071, 60, 12130),
    /**
     * Squirrel 4 pets.
     */
    SQUIRREL_4(12760, 12761, -1, 7307, 7308, -1, 0.0071225071225071, 60, 12130),
    /**
     * Saradomin owl pets.
     */
    SARADOMIN_OWL(12503, 12504, 12505, 6949, 6950, 6951, 0.0069444444444444, 70, 313, 12129),
    /**
     * Zamorak hawk pets.
     */
    ZAMORAK_HAWK(12506, 12507, 12508, 6952, 6953, 6954, 0.0069444444444444, 70, 313, 12129),
    /**
     * Guthix raptor pets.
     */
    GUTHIX_RAPTOR(12509, 12510, 12511, 6955, 6956, 6957, 0.0069444444444444, 70, 313, 12129),
    /**
     * Ex ex parrot pets.
     */
    EX_EX_PARROT(13335, -1, -1, 7844, -1, -1, 0.0, 71, 13379),
    /**
     * Cute phoenix eggling pets.
     */
    CUTE_PHOENIX_EGGLING(14627, -1, -1, 8578, -1, -1, 0.0, 72, 592),
    /**
     * Mean phoenix eggling pets.
     */
    MEAN_PHOENIX_EGGLING(14626, -1, -1, 8577, -1, -1, 0.0, 72, 592),
    /**
     * Raccoon pets.
     */
    RACCOON(12486, 12487, -1, 6913, 6914, -1, 0.0029444444444444, 80, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359, 2132, 2134, 2136, 2138, 10816, 9986, 9978),
    /**
     * Raccoon 1 pets.
     */
    RACCOON_1(12734, 12735, -1, 7271, 7272, -1, 0.0029444444444444, 80, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359, 2132, 2134, 2136, 2138, 10816, 9986, 9978),
    /**
     * Raccoon 2 pets.
     */
    RACCOON_2(12736, 12737, -1, 7273, 7274, -1, 0.0029444444444444, 80, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359, 2132, 2134, 2136, 2138, 10816, 9986, 9978),
    /**
     * Vulture pets.
     */
    VULTURE(12498, 12499, -1, 6945, 6946, -1, 0.0078, 85, 313, 12129),
    /**
     * Vulture 1 pets.
     */
    VULTURE_1(12766, 12767, -1, 7319, 7320, -1, 0.0078, 85, 313, 12129),
    /**
     * Vulture 2 pets.
     */
    VULTURE_2(12768, 12769, -1, 7321, 7322, -1, 0.0078, 85, 313, 12129),
    /**
     * Vulture 3 pets.
     */
    VULTURE_3(12770, 12771, -1, 7323, 7324, -1, 0.0078, 85, 313, 12129),
    /**
     * Vulture 4 pets.
     */
    VULTURE_4(12772, 12773, -1, 7325, 7326, -1, 0.0078, 85, 313, 12129),
    /**
     * Vulture 5 pets.
     */
    VULTURE_5(12774, 12775, -1, 7327, 7328, -1, 0.0078, 85, 313, 12129),
    /**
     * Chameleon pets.
     */
    CHAMELEON(12492, 12493, -1, 6922, 6923, -1, 0.0069444444444444, 90, 12125),
    /**
     * Monkey pets.
     */
    MONKEY(12496, 12497, -1, 6942, 6943, -1, 0.0069444444444444, 95, 1963),
    /**
     * Monkey 1 pets.
     */
    MONKEY_1(12682, 12683, -1, 7210, 7211, -1, 0.0069444444444444, 95, 1963),
    /**
     * Monkey 2 pets.
     */
    MONKEY_2(12684, 12685, -1, 7212, 7213, -1, 0.0069444444444444, 95, 1963),
    /**
     * Monkey 3 pets.
     */
    MONKEY_3(12686, 12687, -1, 7214, 7215, -1, 0.0069444444444444, 95, 1963),
    /**
     * Monkey 4 pets.
     */
    MONKEY_4(12688, 12689, -1, 7216, 7217, -1, 0.0069444444444444, 95, 1963),
    /**
     * Monkey 5 pets.
     */
    MONKEY_5(12690, 12691, -1, 7218, 7219, -1, 0.0069444444444444, 95, 1963),
    /**
     * Monkey 6 pets.
     */
    MONKEY_6(12692, 12693, -1, 7220, 7221, -1, 0.0069444444444444, 95, 1963),
    /**
     * Monkey 7 pets.
     */
    MONKEY_7(12694, 12695, -1, 7222, 7223, -1, 0.0069444444444444, 95, 1963),
    /**
     * Monkey 8 pets.
     */
    MONKEY_8(12696, 12697, -1, 7224, 7225, -1, 0.0069444444444444, 95, 1963),
    /**
     * Monkey 9 pets.
     */
    MONKEY_9(12698, 12699, -1, 7226, 7227, -1, 0.0069444444444444, 95, 1963),
    /**
     * Baby dragon pets.
     */
    BABY_DRAGON(12469, 12470, -1, 6900, 6901, -1, 0.0052, 99, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359),
    /**
     * Baby dragon 1 pets.
     */
    BABY_DRAGON_1(12471, 12472, -1, 6902, 6903, -1, 0.0052, 99, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359),
    /**
     * Baby dragon 2 pets.
     */
    BABY_DRAGON_2(12473, 12474, -1, 6904, 6905, -1, 0.0052, 99, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359),
    /**
     * Baby dragon 3 pets.
     */
    BABY_DRAGON_3(12475, 12476, -1, 6906, 6907, -1, 0.0052, 99, 2132, 2134, 2136, 2138, 10816, 9986, 9978, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359);

    /**
     * The baby pets mapping.
     */
    private static final Map<Integer, Pets> babyPets = new HashMap<Integer, Pets>();

    /**
     * The grown pets mapping.
     */
    private static final Map<Integer, Pets> grownPets = new HashMap<Integer, Pets>();

    /**
     * The overgrown pets mapping.
     */
    private static final Map<Integer, Pets> overgrownPets = new HashMap<Integer, Pets>();

    static {
        for (Pets pet : Pets.values()) {
            babyPets.put(pet.babyItemId, pet);
            if (pet.grownItemId > 0) {
                grownPets.put(pet.grownItemId, pet);
                if (pet.getOvergrownItemId() > 0) {
                    overgrownPets.put(pet.overgrownItemId, pet);
                }
            }
        }
    }

    /**
     * Gets the pet object for the item id.
     *
     * @param itemId The item id.
     * @return The pet object.
     */
    public static Pets forId(int itemId) {
        Pets pet = babyPets.get(itemId);
        if (pet == null) {
            pet = grownPets.get(itemId);
            if (pet == null) {
                return overgrownPets.get(itemId);
            }
            return pet;
        }
        return pet;
    }

    /**
     * Checks if a player has a pet.
     *
     * @param player The player.
     * @return {@code True} if so.
     */
    public static boolean hasPet(Player player) {
        for (int itemId : babyPets.keySet()) {
            if (player.getInventory().containsAtLeastOneItem(itemId)) {
                return true;
            }
        }
        for (int itemId : grownPets.keySet()) {
            if (player.getInventory().containsAtLeastOneItem(itemId)) {
                return true;
            }
        }
        for (int itemId : overgrownPets.keySet()) {
            if (player.getInventory().containsAtLeastOneItem(itemId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The baby item id.
     */
    private final int babyItemId;

    /**
     * The grown pet's item id.
     */
    private final int grownItemId;

    /**
     * The overgrown pet's item id.
     */
    private final int overgrownItemId;

    /**
     * The baby pet NPC id.
     */
    private final int babyNpcId;

    /**
     * The grown pet NPC id.
     */
    private final int grownNpcId;

    /**
     * The overgrown pet NPC id.
     */
    private final int overgrownNpcId;

    /**
     * The growth rate.
     */
    private final double growthRate;

    /**
     * The summoning level required.
     */
    private final int summoningLevel;

    /**
     * The food this pet uses.
     */
    private final int[] food;

    /**
     * Constructs a new {@code Pets} {@code Object}.
     *
     * @param babyItemId      The baby pet item id.
     * @param grownItemId     The grown pet item id.
     * @param overgrownItemId The overgrown item id.
     * @param babyNpcId       The baby pet npc id.
     * @param grownNpcId      The grown pet npc id.
     * @param overgrownNpcId  The overgrown npc id.
     * @param growthRate      The growth rate (amount to increase growth with every
     *                        tick).
     * @param summoningLevel  The summoning level required.
     * @param food            The food item ids the pet uses.
     */
    private Pets(int babyItemId, int grownItemId, int overgrownItemId, int babyNpcId, int grownNpcId, int overgrownNpcId, double growthRate, int summoningLevel, int... food) {
        this.babyItemId = babyItemId;
        this.grownItemId = grownItemId;
        this.overgrownItemId = overgrownItemId;
        this.babyNpcId = babyNpcId;
        this.grownNpcId = grownNpcId;
        this.overgrownNpcId = overgrownNpcId;
        this.growthRate = growthRate;
        this.summoningLevel = summoningLevel;
        this.food = food;
    }

    /**
     * Gets the babyItemId.
     *
     * @return The babyItemId.
     */
    public int getBabyItemId() {
        return babyItemId;
    }

    /**
     * Gets the grownItemId.
     *
     * @return The grownItemId.
     */
    public int getGrownItemId() {
        return grownItemId;
    }

    /**
     * Gets the overgrownItemId.
     *
     * @return The overgrownItemId.
     */
    public int getOvergrownItemId() {
        return overgrownItemId;
    }

    /**
     * Gets the babyNpcId.
     *
     * @return The babyNpcId.
     */
    public int getBabyNpcId() {
        return babyNpcId;
    }

    /**
     * Gets the grownNpcId.
     *
     * @return The grownNpcId.
     */
    public int getGrownNpcId() {
        return grownNpcId;
    }

    /**
     * Gets the overgrownNpcId.
     *
     * @return The overgrownNpcId.
     */
    public int getOvergrownNpcId() {
        return overgrownNpcId;
    }

    /**
     * Gets the growthRate.
     *
     * @return The growthRate.
     */
    public double getGrowthRate() {
        return growthRate;
    }

    /**
     * Gets the summoningLevel.
     *
     * @return The summoningLevel.
     */
    public int getSummoningLevel() {
        return summoningLevel;
    }

    /**
     * Gets the food.
     *
     * @return The food.
     */
    public int[] getFood() {
        return food;
    }

    /**
     * Gets the NPC id for this pet.
     *
     * @param itemId An int giving the ID of the item for which we want to know the corresponding NPC id.
     * @return The NPC id.
     */
    public int getNpcId(int itemId) {
        if (itemId == babyItemId) {
            return babyNpcId;
        }
        if (itemId == grownItemId) {
            return grownNpcId;
        }
        if (itemId == overgrownItemId) {
            return overgrownNpcId;
        }
        log(this.getClass(), Log.ERR, "Could not locate NPC ID for pet item " + itemId);
        return -1;
    }

    /**
     * Gets the next growth stage's item ID for this pet.
     *
     * @param itemId An int giving the current pet's item ID.
     * @return The item ID for the next growth stage, or -1 if there isn't any (i.e. pet is already overgrown).
     */
    public int getNextStageItemId(int itemId) {
        if (itemId == babyItemId) {
            return grownItemId;
        }
        if (itemId == grownItemId) {
            return overgrownItemId;
        }
        return -1;
    }
}
