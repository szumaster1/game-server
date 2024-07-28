package content.global.skill.combat.summoning.pet

import core.api.consts.Items
import core.api.consts.NPCs
import core.api.log
import core.game.node.entity.player.Player
import core.tools.Log

enum class Pets(
    @JvmField val babyItemId: Int,
    @JvmField val grownItemId: Int,
    @JvmField val overgrownItemId: Int,
    @JvmField val babyNpcId: Int,
    val grownNpcId: Int,
    val overgrownNpcId: Int,
    @JvmField val growthRate: Double,
    @JvmField val summoningLevel: Int,
    @JvmField vararg val food: Int
) {
    /*
     * Cats.
     */
    CAT(
        babyItemId = Items.PET_KITTEN_1555,
        grownItemId = Items.PET_CAT_1561,
        overgrownItemId = Items.OVERGROWN_CAT_1567,
        babyNpcId = NPCs.KITTEN_761,
        grownNpcId = NPCs.CAT_768,
        overgrownNpcId = NPCs.OVERGROWN_CAT_774,
        growthRate = 0.0154320987654321,
        summoningLevel = 0,
        food = intArrayOf(321, 319, 363, 365, 341, 339, 345, 347, 377, 379, 353, 355, 389, 391, 7944, 7946, 349, 351, 331, 329, 327, 325, 395, 397, 383, 385, 317, 315, 371, 373, 335, 333, 359, 361, 1927)
    ),
    CAT_1(
        babyItemId = Items.PET_KITTEN_1556,
        grownItemId = Items.PET_CAT_1562,
        overgrownItemId = Items.OVERGROWN_CAT_1568,
        babyNpcId = NPCs.KITTEN_762,
        grownNpcId = NPCs.CAT_769,
        overgrownNpcId = NPCs.OVERGROWN_CAT_775,
        growthRate = 0.0154320987654321,
        summoningLevel = 0,
        food = intArrayOf(321, 319, 363, 365, 341, 339, 345, 347, 377, 379, 353, 355, 389, 391, 7944, 7946, 349, 351, 331, 329, 327, 325, 395, 397, 383, 385, 317, 315, 371, 373, 335, 333, 359, 361, 1927)
    ),
    CAT_2(
        babyItemId = Items.PET_KITTEN_1557,
        grownItemId = Items.PET_CAT_1563,
        overgrownItemId = Items.OVERGROWN_CAT_1569,
        babyNpcId = NPCs.KITTEN_763,
        grownNpcId = NPCs.CAT_770,
        overgrownNpcId = NPCs.OVERGROWN_CAT_776,
        growthRate = 0.0154320987654321,
        summoningLevel = 0,
        food = intArrayOf(321, 319, 363, 365, 341, 339, 345, 347, 377, 379, 353, 355, 389, 391, 7944, 7946, 349, 351, 331, 329, 327, 325, 395, 397, 383, 385, 317, 315, 371, 373, 335, 333, 359, 361, 1927)
    ),
    CAT_3(
        babyItemId = Items.PET_KITTEN_1558,
        grownItemId = Items.PET_CAT_1564,
        overgrownItemId = Items.OVERGROWN_CAT_1570,
        babyNpcId = NPCs.KITTEN_764,
        grownNpcId = NPCs.CAT_771,
        overgrownNpcId = NPCs.OVERGROWN_CAT_777,
        growthRate = 0.0154320987654321,
        summoningLevel = 0,
        food = intArrayOf(321, 319, 363, 365, 341, 339, 345, 347, 377, 379, 353, 355, 389, 391, 7944, 7946, 349, 351, 331, 329, 327, 325, 395, 397, 383, 385, 317, 315, 371, 373, 335, 333, 359, 361, 1927)
    ),
    CAT_4(
        babyItemId = Items.PET_KITTEN_1559,
        grownItemId = Items.PET_CAT_1565,
        overgrownItemId = Items.OVERGROWN_CAT_1571,
        babyNpcId = NPCs.KITTEN_765,
        grownNpcId = NPCs.CAT_772,
        overgrownNpcId = NPCs.OVERGROWN_CAT_778,
        growthRate = 0.0154320987654321,
        summoningLevel = 0,
        food = intArrayOf(321, 319, 363, 365, 341, 339, 345, 347, 377, 379, 353, 355, 389, 391, 7944, 7946, 349, 351, 331, 329, 327, 325, 395, 397, 383, 385, 317, 315, 371, 373, 335, 333, 359, 361, 1927)
    ),
    CAT_5(
        babyItemId = Items.PET_KITTEN_1560,
        grownItemId = Items.PET_CAT_1566,
        overgrownItemId = Items.OVERGROWN_CAT_1572,
        babyNpcId = NPCs.KITTEN_766,
        grownNpcId = NPCs.CAT_773,
        overgrownNpcId = NPCs.OVERGROWN_CAT_779,
        growthRate = 0.0154320987654321,
        summoningLevel = 0,
        food = intArrayOf(321, 319, 363, 365, 341, 339, 345, 347, 377, 379, 353, 355, 389, 391, 7944, 7946, 349, 351, 331, 329, 327, 325, 395, 397, 383, 385, 317, 315, 371, 373, 335, 333, 359, 361, 1927)
    ),
    HELLCAT(
         babyItemId = Items.HELL_KITTEN_7583,
         grownItemId = Items.HELL_CAT_7582,
         overgrownItemId = Items.OVERGROWN_HELLCAT_7581,
         babyNpcId = NPCs.HELL_KITTEN_3505,
         grownNpcId = NPCs.HELLCAT_3504,
         overgrownNpcId = NPCs.OVERGROWN_HELLCAT_3503,
         growthRate = 0.0154320987654321,
         summoningLevel = 0,
         food = intArrayOf(321, 319, 363, 365, 341, 339, 345, 347, 377, 379, 353, 355, 389, 391, 7944, 7946, 349, 351, 331, 329, 327, 325, 395, 397, 383, 385, 317, 315, 371, 373, 335, 333, 359, 361, 1927)
    ),
    CAT_7(
         babyItemId = Items.PET_KITTEN_14089,
         grownItemId = Items.PET_CAT_14090,
         overgrownItemId = Items.OVERGROWN_CAT_14092,
         babyNpcId = NPCs.KITTEN_8217,
         grownNpcId = NPCs.CAT_8214,
         overgrownNpcId = NPCs.OVERGROWN_CAT_8216,
         growthRate = 0.0154320987654321,
         summoningLevel = 0,
         food = intArrayOf(321, 319, 363, 365, 341, 339, 345, 347, 377, 379, 353, 355, 389, 391, 7944, 7946, 349, 351, 331, 329, 327, 325, 395, 397, 383, 385, 317, 315, 371, 373, 335, 333, 359, 361, 1927)
    ),
    CLOCKWORK_CAT(
        babyItemId = Items.CLOCKWORK_CAT_7771,
        grownItemId = Items.CLOCKWORK_CAT_7772,
        overgrownItemId = -1,
        babyNpcId = NPCs.CLOCKWORK_CAT_3598,
        grownNpcId = -1,
        overgrownNpcId = -1,
        growthRate = 0.0,
        summoningLevel = 0
    ),

    /*
     * Bulldogs.
     */
    BULLDOG(
         babyItemId = 12522,
         grownItemId = 12523,
         overgrownItemId = -1,
         babyNpcId = 6969,
         grownNpcId = 6968,
         overgrownNpcId = -1,
         growthRate = 0.0033333333333333,
         summoningLevel = 4,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 526)
    ),
    BULLDOG_1(
        babyItemId = 12720,
        grownItemId = 12721,
        overgrownItemId = -1,
        babyNpcId = 7259,
        grownNpcId = 7257,
        overgrownNpcId = -1,
        growthRate = 0.0033333333333333,
        summoningLevel = 4,
        food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 526)
    ),
    BULLDOG_2(
         babyItemId = 12722,
         grownItemId = 12723,
         overgrownItemId = -1,
         babyNpcId = 7260,
         grownNpcId = 7258,
         overgrownNpcId = -1,
         growthRate = 0.0033333333333333,
         summoningLevel = 4,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 526)
    ),

    /*
     * Dalmatians.
     */
    DALMATIAN(
         babyItemId = 12518,
         grownItemId = 12519,
         overgrownItemId = -1,
         babyNpcId = 6964,
         grownNpcId = 6965,
         overgrownNpcId = -1,
         growthRate = 0.0033333333333333,
         summoningLevel = 4,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 526)
    ),
    DALMATIAN_1(
         babyItemId = 12712,
         grownItemId = 12713,
         overgrownItemId = -1,
         babyNpcId = 7249,
         grownNpcId = 7250,
         overgrownNpcId = -1,
         growthRate = 0.0033333333333333,
         summoningLevel = 4,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 526)
    ),
    DALMATIAN_2(
         babyItemId = 12714,
         grownItemId = 12715,
         overgrownItemId = -1,
         babyNpcId = 7251,
         grownNpcId = 7252,
         overgrownNpcId = -1,
         growthRate = 0.0033333333333333,
         summoningLevel = 4,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 526)
    ),

    /*
     * Greyhounds.
     */
    GREYHOUND(
         babyItemId = 12514,
         grownItemId = 12515,
         overgrownItemId = -1,
         babyNpcId = 6960,
         grownNpcId = 6961,
         overgrownNpcId = -1,
         growthRate = 0.0033333333333333,
         summoningLevel = 4,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 526)
    ),
    GREYHOUND_1(
         babyItemId = 12704,
         grownItemId = 12705,
         overgrownItemId = -1,
         babyNpcId = 7241,
         grownNpcId = 7242,
         overgrownNpcId = -1,
         growthRate = 0.0033333333333333,
         summoningLevel = 4,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 526)
    ),
    GREYHOUND_2(
         babyItemId = 12706,
         grownItemId = 12707,
         overgrownItemId = -1,
         babyNpcId = 7243,
         grownNpcId = 7244,
         overgrownNpcId = -1,
         growthRate = 0.0033333333333333,
         summoningLevel = 4,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 526)
    ),

    /*
     * Labradors.
     */
    LABRADOR(
         babyItemId = 12516,
         grownItemId = 12517,
         overgrownItemId = -1,
         babyNpcId = 6962,
         grownNpcId = 6963,
         overgrownNpcId = -1,
         growthRate = 0.0033333333333333,
         summoningLevel = 4,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 526)
    ),
    LABRADOR_1(
         babyItemId = 12708,
         grownItemId = 12709,
         overgrownItemId = -1,
         babyNpcId = 7245,
         grownNpcId = 7246,
         overgrownNpcId = -1,
         growthRate = 0.0033333333333333,
         summoningLevel = 4,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 526)
    ),
    LABRADOR_2(
         babyItemId = 12710,
         grownItemId = 12711,
         overgrownItemId = -1,
         babyNpcId = 7247,
         grownNpcId = 7248,
         overgrownNpcId = -1,
         growthRate = 0.0033333333333333,
         summoningLevel = 4,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 526)
    ),

    /*
     * Sheepdogs.
     */
    SHEEPDOG(
         babyItemId = 12520,
         grownItemId = 12521,
         overgrownItemId = -1,
         babyNpcId = 6966,
         grownNpcId = 6967,
         overgrownNpcId = -1,
         growthRate = 0.0033333333333333,
         summoningLevel = 4,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 526)
    ),
    SHEEPDOG_1(
         babyItemId = 12716,
         grownItemId = 12717,
         overgrownItemId = -1,
         babyNpcId = 7253,
         grownNpcId = 7254,
         overgrownNpcId = -1,
         growthRate = 0.0033333333333333,
         summoningLevel = 4,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 526)
    ),
    SHEEPDOG_2(
         babyItemId = 12718,
         grownItemId = 12719,
         overgrownItemId = -1,
         babyNpcId = 7255,
         grownNpcId = 7256,
         overgrownNpcId = -1,
         growthRate = 0.0033333333333333,
         summoningLevel = 4,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 526)
    ),

    /*
     * Terriers.
     */
    TERRIER(
         babyItemId = 12512,
         grownItemId = 12513,
         overgrownItemId = -1,
         babyNpcId = 6958,
         grownNpcId = 6859,
         overgrownNpcId = -1,
         growthRate = 0.0033333333333333,
         summoningLevel = 4,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 526)
    ),
    TERRIER_1(
         babyItemId = 12700,
         grownItemId = 12701,
         overgrownItemId = -1,
         babyNpcId = 7237,
         grownNpcId = 7238,
         overgrownNpcId = -1,
         growthRate = 0.0033333333333333,
         summoningLevel = 4,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 526)
    ),
    TERRIER_2(
         babyItemId = 12702,
         grownItemId = 12703,
         overgrownItemId = -1,
         babyNpcId = 7239,
         grownNpcId = 7240,
         overgrownNpcId = -1,
         growthRate = 0.0033333333333333,
         summoningLevel = 4,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 526)
    ),

    /*
     * Gecko.
     */
    GECKO(
         babyItemId = 12488,
         grownItemId = 12489,
         overgrownItemId = -1,
         babyNpcId = 6915,
         grownNpcId = 6916,
         overgrownNpcId = -1,
         growthRate = 0.005,
         summoningLevel = 10,
         food = intArrayOf(12125, 12127)
    ),
    GECKO_1(
         babyItemId = 12738,
         grownItemId = 12742,
         overgrownItemId = -1,
         babyNpcId = 7277,
         grownNpcId = 7281,
         overgrownNpcId = -1,
         growthRate = 0.005,
         summoningLevel = 10,
         food = intArrayOf(12125, 12127)
    ),
    GECKO_2(
         babyItemId = 12739,
         grownItemId = 12743,
         overgrownItemId = -1,
         babyNpcId = 7278,
         grownNpcId = 7282,
         overgrownNpcId = -1,
         growthRate = 0.005,
         summoningLevel = 10,
         food = intArrayOf(12125, 12127)
    ),
    GECKO_3(
         babyItemId = 12740,
         grownItemId = 12744,
         overgrownItemId = -1,
         babyNpcId = 7279,
         grownNpcId = 7283,
         overgrownNpcId = -1,
         growthRate = 0.005,
         summoningLevel = 10,
         food = intArrayOf(12125, 12127)
    ),
    GECKO_4(
         babyItemId = 12741,
         grownItemId = 12745,
         overgrownItemId = -1,
         babyNpcId = 7280,
         grownNpcId = 7284,
         overgrownNpcId = -1,
         growthRate = 0.005,
         summoningLevel = 10,
         food = intArrayOf(12125, 12127)
    ),

    /*
     * Platypus.
     */
    PLATYPUS(
         babyItemId = 12551,
         grownItemId = 12548,
         overgrownItemId = -1,
         babyNpcId = 7018,
         grownNpcId = 7015,
         overgrownNpcId = -1,
         growthRate = 0.0046296296296296,
         summoningLevel = 10,
         food = intArrayOf(321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359, 313, 12129)
    ),
    PLATYPUS_1(
         babyItemId = 12552,
         grownItemId = 12549,
         overgrownItemId = -1,
         babyNpcId = 7019,
         grownNpcId = 7016,
         overgrownNpcId = -1,
         growthRate = 0.0046296296296296,
         summoningLevel = 10,
         food = intArrayOf(321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359, 313, 12129)
    ),
    PLATYPUS_2(
         babyItemId = 12553,
         grownItemId = 12550,
         overgrownItemId = -1,
         babyNpcId = 7020,
         grownNpcId = 7017,
         overgrownNpcId = -1,
         growthRate = 0.0046296296296296,
         summoningLevel = 10,
         food = intArrayOf(321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359, 313, 12129)
    ),

    /*
     * Broav.
     */
    BROAV(
        babyItemId = 14533,
        grownItemId = -1,
        overgrownItemId = -1,
        babyNpcId = 8491,
        grownNpcId = -1,
        overgrownNpcId = -1,
        growthRate = 0.0,
        summoningLevel = 23,
        food = intArrayOf(2970)
    ),

    PENGUIN(
        babyItemId = 12481,
        grownItemId = 12482,
        overgrownItemId = -1,
        babyNpcId = 6908,
        grownNpcId = 6909,
        overgrownNpcId = -1,
        growthRate = 0.0046296296296296,
        summoningLevel = 30,
        food = intArrayOf(321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359)
    ),
    PENGUIN_1(
         babyItemId = 12763,
         grownItemId = 12762,
         overgrownItemId = -1,
         babyNpcId = 7313,
         grownNpcId = 7314,
         overgrownNpcId = -1,
         growthRate = 0.0046296296296296,
         summoningLevel = 30,
         food = intArrayOf(321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359)
    ),
    PENGUIN_2(
         babyItemId = 12765,
         grownItemId = 12764,
         overgrownItemId = -1,
         babyNpcId = 7316,
         grownNpcId = 7317,
         overgrownNpcId = -1,
         growthRate = 0.0046296296296296,
         summoningLevel = 30,
         food = intArrayOf(321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359)
    ),

    /*
     * Giant crabs.
     */
    GIANT_CRAB(
         babyItemId = 12500,
         grownItemId = 12501,
         overgrownItemId = -1,
         babyNpcId = 6947,
         grownNpcId = 6948,
         overgrownNpcId = -1,
         growthRate = 0.0069444444444444,
         summoningLevel = 40,
         food = intArrayOf(321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359)
    ),
    GIANT_CRAB_1(
         babyItemId = 12746,
         grownItemId = 12747,
         overgrownItemId = -1,
         babyNpcId = 7293,
         grownNpcId = 7294,
         overgrownNpcId = -1,
         growthRate = 0.0069444444444444,
         summoningLevel = 40,
         food = intArrayOf(321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359)
    ),
    GIANT_CRAB_2(
         babyItemId = 12748,
         grownItemId = 12749,
         overgrownItemId = -1,
         babyNpcId = 7295,
         grownNpcId = 7296,
         overgrownNpcId = -1,
         growthRate = 0.0069444444444444,
         summoningLevel = 40,
         food = intArrayOf(321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359)
    ),
    GIANT_CRAB_3(
        babyItemId = 12750,
        grownItemId = 12751,
        overgrownItemId = -1,
        babyNpcId = 7297,
        grownNpcId = 7298,
        overgrownNpcId = -1,
        growthRate = 0.0069444444444444,
        summoningLevel = 40,
        food = intArrayOf(321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359)
    ),
    GIANT_CRAB_4(
         babyItemId = 12752,
         grownItemId = 12753,
         overgrownItemId = -1,
         babyNpcId = 7299,
         grownNpcId = 7300,
         overgrownNpcId = -1,
         growthRate = 0.0069444444444444,
         summoningLevel = 40,
         food = intArrayOf(321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359)
    ),

    /*
     * Ravens.
     */
    RAVEN(
         babyItemId = 12484,
         grownItemId = 12485,
         overgrownItemId = -1,
         babyNpcId = 6911,
         grownNpcId = 6912,
         overgrownNpcId = -1,
         growthRate = 0.00698888,
         summoningLevel = 50,
         food = intArrayOf(313, 12129)
    ),
    RAVEN_1(
         babyItemId = 12724,
         grownItemId = 12725,
         overgrownItemId = -1,
         babyNpcId = 7261,
         grownNpcId = 7262,
         overgrownNpcId = -1,
         growthRate = 0.00698888,
         summoningLevel = 50,
         food = intArrayOf(313, 12129)
    ),
    RAVEN_2(
         babyItemId = 12726,
         grownItemId = 12727,
         overgrownItemId = -1,
         babyNpcId = 7263,
         grownNpcId = 7264,
         overgrownNpcId = -1,
         growthRate = 0.00698888,
         summoningLevel = 50,
         food = intArrayOf(313, 12129)
    ),
    RAVEN_3(
         babyItemId = 12728,
         grownItemId = 12729,
         overgrownItemId = -1,
         babyNpcId = 7265,
         grownNpcId = 7266,
         overgrownNpcId = -1,
         growthRate = 0.00698888,
         summoningLevel = 50,
         food = intArrayOf(313, 12129)
    ),
    RAVEN_4(
         babyItemId = 12730,
         grownItemId = 12731,
         overgrownItemId = -1,
         babyNpcId = 7267,
         grownNpcId = 7268,
         overgrownNpcId = -1,
         growthRate = 0.00698888,
         summoningLevel = 50,
         food = intArrayOf(313, 12129)
    ),
    RAVEN_5(
         babyItemId = 12732,
         grownItemId = 12733,
         overgrownItemId = -1,
         babyNpcId = 7269,
         grownNpcId = 7270,
         overgrownNpcId = -1,
         growthRate = 0.00698888,
         summoningLevel = 50,
         food = intArrayOf(313, 12129)
    ),

    /*
     * Squirrels.
     */
    SQUIRREL(
         babyItemId = 12490,
         grownItemId = 12491,
         overgrownItemId = -1,
         babyNpcId = 6919,
         grownNpcId = 6920,
         overgrownNpcId = -1,
         growthRate = 0.0071225071225071,
         summoningLevel = 60,
         food = intArrayOf(12130)
    ),
    SQUIRREL_1(
         babyItemId = 12754,
         grownItemId = 12755,
         overgrownItemId = -1,
         babyNpcId = 7301,
         grownNpcId = 7302,
         overgrownNpcId = -1,
         growthRate = 0.0071225071225071,
         summoningLevel = 60,
         food = intArrayOf(12130)
    ),
    SQUIRREL_2(
         babyItemId = 12756,
         grownItemId = 12757,
         overgrownItemId = -1,
         babyNpcId = 7303,
         grownNpcId = 7304,
         overgrownNpcId = -1,
         growthRate = 0.0071225071225071,
         summoningLevel = 60,
         food = intArrayOf(12130)
    ),
    SQUIRREL_3(
         babyItemId = 12758,
         grownItemId = 12759,
         overgrownItemId = -1,
         babyNpcId = 7305,
         grownNpcId = 7306,
         overgrownNpcId = -1,
         growthRate = 0.0071225071225071,
         summoningLevel = 60,
         food = intArrayOf(12130)
    ),
    SQUIRREL_4(
         babyItemId = 12760,
         grownItemId = 12761,
         overgrownItemId = -1,
         babyNpcId = 7307,
         grownNpcId = 7308,
         overgrownNpcId = -1,
         growthRate = 0.0071225071225071,
         summoningLevel = 60,
         food = intArrayOf(12130)
    ),

    /*
     * Saradomin owl.
     */
    SARADOMIN_OWL(
         babyItemId = 12503,
         grownItemId = 12504,
         overgrownItemId = 12505,
         babyNpcId = 6949,
         grownNpcId = 6950,
         overgrownNpcId = 6951,
         growthRate = 0.0069444444444444,
         summoningLevel = 70,
         food = intArrayOf(313, 12129)
    ),

    /*
     * Zamorak hawk.
     */
    ZAMORAK_HAWK(
         babyItemId = 12506,
         grownItemId = 12507,
         overgrownItemId = 12508,
         babyNpcId = 6952,
         grownNpcId = 6953,
         overgrownNpcId = 6954,
         growthRate = 0.0069444444444444,
         summoningLevel = 70,
         food = intArrayOf(313, 12129)
    ),

    /*
     * Guthix raptor.
     */
    GUTHIX_RAPTOR(
         babyItemId = 12509,
         grownItemId = 12510,
         overgrownItemId = 12511,
         babyNpcId = 6955,
         grownNpcId = 6956,
         overgrownNpcId = 6957,
         growthRate = 0.0069444444444444,
         summoningLevel = 70,
         food = intArrayOf(313, 12129)
    ),

    /*
     * Ex ex parrot.
     */
    EX_EX_PARROT(
         babyItemId = 13335,
         grownItemId = -1,
         overgrownItemId = -1,
         babyNpcId = 7844,
         grownNpcId = -1,
         overgrownNpcId = -1,
         growthRate = 0.0,
         summoningLevel = 71,
         food = intArrayOf(13379)
    ),
    /*
     * Phoenix.
     */
    CUTE_PHOENIX_EGGLING(
         babyItemId = 14627,
         grownItemId = -1,
         overgrownItemId = -1,
         babyNpcId = 8578,
         grownNpcId = -1,
         overgrownNpcId = -1,
         growthRate = 0.0,
         summoningLevel = 72,
         food = intArrayOf(592)
    ),
    MEAN_PHOENIX_EGGLING(
         babyItemId = 14626,
         grownItemId = -1,
         overgrownItemId = -1,
         babyNpcId = 8577,
         grownNpcId = -1,
         overgrownNpcId = -1,
         growthRate = 0.0,
         summoningLevel = 72,
         food = intArrayOf(592)
    ),

    /*
     * Raccoons.
     */
    RACCOON(
         babyItemId = 12486,
         grownItemId = 12487,
         overgrownItemId = -1,
         babyNpcId = 6913,
         grownNpcId = 6914,
         overgrownNpcId = -1,
         growthRate = 0.0029444444444444,
         summoningLevel = 80,
         food = intArrayOf(321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359, 2132, 2134, 2136, 2138, 10816, 9986, 9978)
    ),
    RACCOON_1(
         babyItemId = 12734,
         grownItemId = 12735,
         overgrownItemId = -1,
         babyNpcId = 7271,
         grownNpcId = 7272,
         overgrownNpcId = -1,
         growthRate = 0.0029444444444444,
         summoningLevel = 80,
         food = intArrayOf(321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359, 2132, 2134, 2136, 2138, 10816, 9986, 9978)
    ),
    RACCOON_2(
         babyItemId = 12736,
         grownItemId = 12737,
         overgrownItemId = -1,
         babyNpcId = 7273,
         grownNpcId = 7274,
         overgrownNpcId = -1,
         growthRate = 0.0029444444444444,
         summoningLevel = 80,
         food = intArrayOf(321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359, 2132, 2134, 2136, 2138, 10816, 9986, 9978)
    ),

    /*
     * Vultures.
     */
    VULTURE(
         babyItemId = 12498,
         grownItemId = 12499,
         overgrownItemId = -1,
         babyNpcId = 6945,
         grownNpcId = 6946,
         overgrownNpcId = -1,
         growthRate = 0.0078,
         summoningLevel = 85,
         food = intArrayOf(313, 12129)
    ),
    VULTURE_1(
        babyItemId = 12766,
        grownItemId = 12767,
        overgrownItemId = -1,
        babyNpcId = 7319,
        grownNpcId = 7320,
        overgrownNpcId = -1,
        growthRate = 0.0078,
        summoningLevel = 85,
        food = intArrayOf(313, 12129)
    ),
    VULTURE_2(
         babyItemId = 12768,
         grownItemId = 12769,
         overgrownItemId = -1,
         babyNpcId = 7321,
         grownNpcId = 7322,
         overgrownNpcId = -1,
         growthRate = 0.0078,
         summoningLevel = 85,
         food = intArrayOf(313, 12129)
    ),
    VULTURE_3(
         babyItemId = 12770,
         grownItemId = 12771,
         overgrownItemId = -1,
         babyNpcId = 7323,
         grownNpcId = 7324,
         overgrownNpcId = -1,
         growthRate = 0.0078,
         summoningLevel = 85,
         food = intArrayOf(313, 12129)
    ),
    VULTURE_4(
         babyItemId = 12772,
         grownItemId = 12773,
         overgrownItemId = -1,
         babyNpcId = 7325,
         grownNpcId = 7326,
         overgrownNpcId = -1,
         growthRate = 0.0078,
         summoningLevel = 85,
         food = intArrayOf(313, 12129)
    ),
    VULTURE_5(
         babyItemId = 12774,
         grownItemId = 12775,
         overgrownItemId = -1,
         babyNpcId = 7327,
         grownNpcId = 7328,
         overgrownNpcId = -1,
         growthRate = 0.0078,
         summoningLevel = 85,
         food = intArrayOf(313, 12129)
    ),

    /*
     * Chameleon.
     */
    CHAMELEON(
         babyItemId = 12492,
         grownItemId = 12493,
         overgrownItemId = -1,
         babyNpcId = 6922,
         grownNpcId = 6923,
         overgrownNpcId = -1,
         growthRate = 0.0069444444444444,
         summoningLevel = 90,
         food = intArrayOf(12125)
    ),

    /*
     * Monkeys.
     */
    MONKEY(
         babyItemId = 12496,
         grownItemId = 12497,
         overgrownItemId = -1,
         babyNpcId = 6942,
         grownNpcId = 6943,
         overgrownNpcId = -1,
         growthRate = 0.0069444444444444,
         summoningLevel = 95,
         food = intArrayOf(1963)
    ),
    MONKEY_1(
         babyItemId = 12682,
         grownItemId = 12683,
         overgrownItemId = -1,
         babyNpcId = 7210,
         grownNpcId = 7211,
         overgrownNpcId = -1,
         growthRate = 0.0069444444444444,
         summoningLevel = 95,
         food = intArrayOf(1963)
    ),
    MONKEY_2(
         babyItemId = 12684,
         grownItemId = 12685,
         overgrownItemId = -1,
         babyNpcId = 7212,
         grownNpcId = 7213,
         overgrownNpcId = -1,
         growthRate = 0.0069444444444444,
         summoningLevel = 95,
         food = intArrayOf(1963)
    ),
    MONKEY_3(
         babyItemId = 12686,
         grownItemId = 12687,
         overgrownItemId = -1,
         babyNpcId = 7214,
         grownNpcId = 7215,
         overgrownNpcId = -1,
         growthRate = 0.0069444444444444,
         summoningLevel = 95,
         food = intArrayOf(1963)
    ),
    MONKEY_4(
         babyItemId = 12688,
         grownItemId = 12689,
         overgrownItemId = -1,
         babyNpcId = 7216,
         grownNpcId = 7217,
         overgrownNpcId = -1,
         growthRate = 0.0069444444444444,
         summoningLevel = 95,
         food = intArrayOf(1963)
    ),
    MONKEY_5(
         babyItemId = 12690,
         grownItemId = 12691,
         overgrownItemId = -1,
         babyNpcId = 7218,
         grownNpcId = 7219,
         overgrownNpcId = -1,
         growthRate = 0.0069444444444444,
         summoningLevel = 95,
         food = intArrayOf(1963)
    ),
    MONKEY_6(
         babyItemId = 12692,
         grownItemId = 12693,
         overgrownItemId = -1,
         babyNpcId = 7220,
         grownNpcId = 7221,
         overgrownNpcId = -1,
         growthRate = 0.0069444444444444,
         summoningLevel = 95,
         food = intArrayOf(1963)
    ),
    MONKEY_7(
         babyItemId = 12694,
         grownItemId = 12695,
         overgrownItemId = -1,
         babyNpcId = 7222,
         grownNpcId = 7223,
         overgrownNpcId = -1,
         growthRate = 0.0069444444444444,
         summoningLevel = 95,
         food = intArrayOf(1963)
    ),
    MONKEY_8(
         babyItemId = 12696,
         grownItemId = 12697,
         overgrownItemId = -1,
         babyNpcId = 7224,
         grownNpcId = 7225,
         overgrownNpcId = -1,
         growthRate = 0.0069444444444444,
         summoningLevel = 95,
         food = intArrayOf(1963)
    ),
    MONKEY_9(
         babyItemId = 12698,
         grownItemId = 12699,
         overgrownItemId = -1,
         babyNpcId = 7226,
         grownNpcId = 7227,
         overgrownNpcId = -1,
         growthRate = 0.0069444444444444,
         summoningLevel = 95,
         food = intArrayOf(1963)
    ),

    /*
     * Baby dragons.
     */
    BABY_DRAGON(
         babyItemId = 12469,
         grownItemId = 12470,
         overgrownItemId = -1,
         babyNpcId = 6900,
         grownNpcId = 6901,
         overgrownNpcId = -1,
         growthRate = 0.0052,
         summoningLevel = 99,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359)
    ),
    BABY_DRAGON_1(
         babyItemId = 12471,
         grownItemId = 12472,
         overgrownItemId = -1,
         babyNpcId = 6902,
         grownNpcId = 6903,
         overgrownNpcId = -1,
         growthRate = 0.0052,
         summoningLevel = 99,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359)
    ),
    BABY_DRAGON_2(
         babyItemId = 12473,
         grownItemId = 12474,
         overgrownItemId = -1,
         babyNpcId = 6904,
         grownNpcId = 6905,
         overgrownNpcId = -1,
         growthRate = 0.0052,
         summoningLevel = 99,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359)
    ),
    BABY_DRAGON_3(
         babyItemId = 12475,
         grownItemId = 12476,
         overgrownItemId = -1,
         babyNpcId = 6906,
         grownNpcId = 6907,
         overgrownNpcId = -1,
         growthRate = 0.0052,
         summoningLevel = 99,
         food = intArrayOf(2132, 2134, 2136, 2138, 10816, 9986, 9978, 321, 363, 341, 345, 377, 353, 389, 7944, 349, 331, 327, 395, 383, 317, 371, 335, 359)
    );

    /**
     * Gets the NPC id for this pet.
     */
    fun getNpcId(itemId: Int): Int {
        if (itemId == babyItemId) {
            return babyNpcId
        }
        if (itemId == grownItemId) {
            return grownNpcId
        }
        if (itemId == overgrownItemId) {
            return overgrownNpcId
        }
        log(this.javaClass, Log.ERR, "Could not locate NPC ID for pet item $itemId")
        return -1
    }

    /**
     * Gets the next growth stage's item ID for this pet.
     *
     * @param itemId An int giving the current pet's item ID.
     * @return The item ID for the next growth stage, or -1 if there isn't any (i.e. pet is already overgrown).
     */
    fun getNextStageItemId(itemId: Int): Int {
        if (itemId == babyItemId) {
            return grownItemId
        }
        if (itemId == grownItemId) {
            return overgrownItemId
        }
        return -1
    }

    companion object {
        private val babyPets: MutableMap<Int, Pets> = HashMap()
        private val grownPets: MutableMap<Int, Pets> = HashMap()
        private val overgrownPets: MutableMap<Int, Pets> = HashMap()

        init {
            for (pet in values()) {
                babyPets[pet.babyItemId] = pet
                if (pet.grownItemId > 0) {
                    grownPets[pet.grownItemId] = pet
                    if (pet.overgrownItemId > 0) {
                        overgrownPets[pet.overgrownItemId] = pet
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
        @JvmStatic
        fun forId(itemId: Int): Pets? {
            var pet = babyPets[itemId]
            if (pet == null) {
                pet = grownPets[itemId]
                if (pet == null) {
                    return overgrownPets[itemId]
                }
                return pet
            }
            return pet
        }

        /**
         * Checks if a player has a pet.
         *
         * @param player The player.
         * @return `True` if so.
         */
        @JvmStatic
        fun hasPet(player: Player): Boolean {
            for (itemId in babyPets.keys) {
                if (player.inventory.containsAtLeastOneItem(itemId)) {
                    return true
                }
            }
            for (itemId in grownPets.keys) {
                if (player.inventory.containsAtLeastOneItem(itemId)) {
                    return true
                }
            }
            for (itemId in overgrownPets.keys) {
                if (player.inventory.containsAtLeastOneItem(itemId)) {
                    return true
                }
            }
            return false
        }
    }
}
