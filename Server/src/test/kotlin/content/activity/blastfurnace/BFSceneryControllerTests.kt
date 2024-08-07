package content.activity.blastfurnace

import TestUtils
import content.minigame.blastfurnace.BFSceneryController
import core.api.getScenery
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * B f scenery controller tests
 *
 * @constructor B f scenery controller tests
 */
class BFSceneryControllerTests {
    init {
        TestUtils.preTestSetup(); BFSceneryController().resetAllScenery()
    }

    /**
     * Update broken should replace with correct variants
     *
     */
    @Test
    fun updateBrokenShouldReplaceWithCorrectVariants() {
        val scenCont = BFSceneryController()

        scenCont.updateBreakable(true, true, true, true)
        Assertions.assertEquals(BFSceneryController.BROKEN_BELT, getScenery(BFSceneryController.beltGearRight)?.id)
        Assertions.assertEquals(BFSceneryController.BROKEN_COG, getScenery(BFSceneryController.cogRightLoc)?.id)
        Assertions.assertEquals(BFSceneryController.BROKEN_POT_PIPE, getScenery(BFSceneryController.potPipeLoc)?.id)
        Assertions.assertEquals(BFSceneryController.BROKEN_PUMP_PIPE, getScenery(BFSceneryController.pumpPipeLoc)?.id)

        scenCont.updateBreakable(false, false, false, false)
        Assertions.assertEquals(BFSceneryController.DEFAULT_BELT, getScenery(BFSceneryController.beltGearRight)?.id)
        Assertions.assertEquals(BFSceneryController.DEFAULT_COG, getScenery(BFSceneryController.cogRightLoc)?.id)
        Assertions.assertEquals(BFSceneryController.DEFAULT_POT_PIPE, getScenery(BFSceneryController.potPipeLoc)?.id)
        Assertions.assertEquals(BFSceneryController.DEFAULT_PUMP_PIPE, getScenery(BFSceneryController.pumpPipeLoc)?.id)
    }

    /**
     * Stove id should correspond to temperature
     *
     */
    @Test
    fun stoveIdShouldCorrespondToTemperature() {
        val testData = arrayOf(
            Pair(0, BFSceneryController.STOVE_COLD),
            Pair(40, BFSceneryController.STOVE_WARM),
            Pair(80, BFSceneryController.STOVE_HOT)
        )
        for ((temp, expectedId) in testData) {
            val cont = BFSceneryController()
            cont.resetAllScenery()
            cont.updateStove(temp)
            Assertions.assertEquals(expectedId, getScenery(BFSceneryController.stoveLoc)!!.id)
        }
    }
}