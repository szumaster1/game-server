package content.activity.blastfurnace

import TestUtils
import content.minigame.blastfurnace.BlastUtils
import content.minigame.blastfurnace.BlastFurnace
import content.minigame.blastfurnace.BlastFurnaceListeners
import core.api.consts.Items
import core.ServerConstants
import core.api.addItem
import core.api.amountInInventory
import core.game.node.entity.skill.Skills
import core.game.world.map.Location
import core.game.world.map.RegionManager
import org.json.simple.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Blast furnace area tests.
 */
class BlastFurnaceAreaTests {
    /**
     * Should be able to enter bf area.
     */
    @Test
    fun shouldBeAbleToEnterBfArea() {
        TestUtils.getMockPlayer("bf-enterable").use { p ->
            p.skills.setStaticLevel(Skills.SMITHING, 60)
            p.location = Location(2932, 10195, 0)
            TestUtils.simulateInteraction(p, RegionManager.getObject(BlastUtils.STAIRLOC_ENTRANCE)!!, 0)
            TestUtils.advanceTicks(5, false)
            Assertions.assertEquals(BlastUtils.ENTRANCE_LOC, p.location)
        }
    }

    /**
     * Should be able to leave bf area.
     */
    @Test
    fun shouldBeAbleToLeaveBfArea() {
        TestUtils.getMockPlayer("bf-leavable").use { p ->
            p.location = BlastUtils.ENTRANCE_LOC
            TestUtils.simulateInteraction(p, RegionManager.getObject(BlastUtils.STAIRLOC_EXIT)!!, 0)
            TestUtils.advanceTicks(5, false)
            Assertions.assertEquals(BlastUtils.EXIT_LOC, p.location)
        }
    }

    /**
     * Should not be able to enter with less than60smithing.
     */
    @Test
    fun shouldNotBeAbleToEnterWithLessThan60Smithing() {
        TestUtils.getMockPlayer("bf-60smith").use { p ->
            p.location = Location(2932, 10195, 0)
            TestUtils.simulateInteraction(p, RegionManager.getObject(BlastUtils.STAIRLOC_ENTRANCE)!!, 0)
            TestUtils.advanceTicks(5, false)
            Assertions.assertNotEquals(BlastUtils.ENTRANCE_LOC, p.location)
        }
    }

    /**
     * Get fee price returns expected values.
     */
    @Test
    fun getFeePriceReturnsExpectedValues() {
        val testData = arrayOf(
            Triple(false, 10, 2500),
            Triple(true, 10, 1250),
            Triple(false, 60, 0),
            Triple(true, 60, 0),
            Triple(false, 59, 2500),
            Triple(true, 59, 1250)
        )
        for ((hasCharos, smithLevel, expected) in testData)
            Assertions.assertEquals(expected, BlastFurnace.getEntranceFee(hasCharos, smithLevel))
    }

    /**
     * Enter with fee should kick player out after 10 minutes.
     */
    @Test
    fun enterWithFeeShouldKickPlayerOutAfter10Minutes() {
        TestUtils.getMockPlayer("bf-fee-kickout").use { p ->
            BlastFurnace.enter(p, true)
            TestUtils.advanceTicks(BlastUtils.FEE_ENTRANCE_DURATION + 2, false)
            Assertions.assertNotEquals(BlastUtils.ENTRANCE_LOC, p.location)
        }
    }

    /**
     * Should be able to leave and enter freely while timer active.
     */
    @Test
    fun shouldBeAbleToLeaveAndEnterFreelyWhileTimerActive() {
        TestUtils.getMockPlayer("bf-fee-reentry").use { p ->
            BlastFurnace.enter(p, true)
            TestUtils.advanceTicks(2, false)
            Assertions.assertEquals(BlastUtils.ENTRANCE_LOC, p.location)
            TestUtils.simulateInteraction(p, RegionManager.getObject(BlastUtils.STAIRLOC_EXIT)!!, 0)
            TestUtils.advanceTicks(2, false)
            Assertions.assertEquals(BlastUtils.EXIT_LOC, p.location)
            TestUtils.simulateInteraction(p, RegionManager.getObject(BlastUtils.STAIRLOC_ENTRANCE)!!, 0)
            TestUtils.advanceTicks(2, false)
            Assertions.assertEquals(BlastUtils.ENTRANCE_LOC, p.location)

            p.location = BlastUtils.EXIT_LOC
            TestUtils.advanceTicks(BlastUtils.FEE_ENTRANCE_DURATION, false)
            //should not allow free reentry if timer has run out
            TestUtils.simulateInteraction(p, RegionManager.getObject(BlastUtils.STAIRLOC_ENTRANCE)!!, 0)
            TestUtils.advanceTicks(2, false)
            Assertions.assertEquals(BlastUtils.EXIT_LOC, p.location)
        }
    }

    /**
     * Player should only be teleported if inside blast furnace area.
     */
    @Test
    fun playerShouldOnlyBeTeleportedIfInsideBFArea() {
        TestUtils.getMockPlayer("bf-fee-timeout-notele-outside-bf").use { p ->
            BlastFurnace.enter(p, true)
            TestUtils.advanceTicks(2, false)
            Assertions.assertEquals(BlastUtils.ENTRANCE_LOC, p.location)

            p.location = ServerConstants.HOME_LOCATION

            TestUtils.advanceTicks(BlastUtils.FEE_ENTRANCE_DURATION, false)
            Assertions.assertEquals(ServerConstants.HOME_LOCATION, p.location)
        }
    }

    /**
     * Player should be able to place ore on belt.
     */
    @Test
    fun playerShouldBeAbleToPlaceOreOnBelt() {
        TestUtils.getMockPlayer("bf-placeoreonbelt").use { p ->
            addItem(p, Items.COAL_453, 2)
            BlastFurnace.placeAllOre(p)
            Assertions.assertEquals(2, BlastFurnace.getAmountOnBelt(p, Items.COAL_453))
            Assertions.assertEquals(0, amountInInventory(p, Items.COAL_453))
        }
    }

    /**
     * Player should not be able to place more ore than can fit on belt.
     */
    @Test
    fun playerShouldNotBeAbleToPlaceMoreOreThanCanFitOnBelt() {
        TestUtils.getMockPlayer("bf-toomuchoreonbelt").use { p ->
            val cont = BlastFurnace.getOreContainer(p)
            cont.addCoal(BlastUtils.COAL_LIMIT - 15)
            cont.addOre(Items.IRON_ORE_440, BlastUtils.ORE_LIMIT - 13)

            addItem(p, Items.COAL_453, 15)
            addItem(p, Items.IRON_ORE_440, 13)
            BlastFurnace.placeAllOre(p)

            addItem(p, Items.COAL_453, 15)
            addItem(p, Items.IRON_ORE_440, 13)
            BlastFurnace.placeAllOre(p)

            Assertions.assertEquals(15, amountInInventory(p, Items.COAL_453))
            Assertions.assertEquals(13, amountInInventory(p, Items.IRON_ORE_440))
        }
    }

    /**
     * Belt ore limits should account for created bars.
     */
    @Test
    fun beltOreLimitsShouldAccountForCreatedBars() {
        TestUtils.getMockPlayer("bf-baraccountedfor").use { p ->
            val cont = BlastFurnace.getOreContainer(p)
            cont.addOre(Items.IRON_ORE_440, BlastUtils.ORE_LIMIT - 15)
            cont.convertToBars()

            addItem(p, Items.IRON_ORE_440, 20)
            BlastFurnace.placeAllOre(p)

            Assertions.assertEquals(5, amountInInventory(p, Items.IRON_ORE_440))
        }
    }

    /**
     * Player should be able to only place specific ore on belt.
     */
    @Test
    fun playerShouldBeAbleToOnlyPlaceSpecificOreOnBelt() {
        TestUtils.getMockPlayer("bf-specific-oreplace").use { p ->
            addItem(p, Items.IRON_ORE_440, 5)
            addItem(p, Items.COAL_453, 3)
            addItem(p, Items.RUNITE_ORE_451, 10)
            BlastFurnace.placeAllOre(p, Items.RUNITE_ORE_451)
            Assertions.assertEquals(0, amountInInventory(p, Items.RUNITE_ORE_451))
            Assertions.assertEquals(5, amountInInventory(p, Items.IRON_ORE_440))
            Assertions.assertEquals(3, amountInInventory(p, Items.COAL_453))
        }
    }

    /**
     * Player should not be able to place ores they lack the level for.
     */
    @Test
    fun playerShouldNotBeAbleToPlaceOresTheyLackTheLevelFor() {
        TestUtils.getMockPlayer("bf-levelgate-oreplace").use { p ->
            addItem(p, Items.RUNITE_ORE_451, 10)
            BlastFurnace.placeAllOre(p, Items.RUNITE_ORE_451, accountForSkill = true)
            Assertions.assertEquals(10, amountInInventory(p, Items.RUNITE_ORE_451))
        }
    }

    /**
     * Should not be able to occupy extra bronze slots with more than28tin or copper.
     */
    @Test
    fun shouldNotBeAbleToOccupyExtraBronzeSlotsWithMoreThan28TinOrCopper() {
        TestUtils.getMockPlayer("bf-bronze-orelimit").use { p ->
            //Edge case - bronze bars have an edge case that allows 28 of both {copper, tin}, so this needs to make sure you can't, for example, add 56 copper.
            addItem(p, Items.COPPER_ORE_436, 28)
            BlastFurnace.placeAllOre(p)
            addItem(p, Items.COPPER_ORE_436, 28)
            BlastFurnace.placeAllOre(p)

            Assertions.assertEquals(28, BlastFurnace.getAmountOnBelt(p, Items.COPPER_ORE_436))
            Assertions.assertEquals(28, amountInInventory(p, Items.COPPER_ORE_436))

            p.inventory.clear()
            addItem(p, Items.TIN_ORE_438, 28)
            BlastFurnace.placeAllOre(p)
            addItem(p, Items.TIN_ORE_438, 28)
            BlastFurnace.placeAllOre(p)

            Assertions.assertEquals(28, BlastFurnace.getAmountOnBelt(p, Items.TIN_ORE_438))
            Assertions.assertEquals(28, amountInInventory(p, Items.TIN_ORE_438))
        }
    }

    /**
     * Should not be able to place more than28total non coal ore on the belt.
     */
    @Test
    fun shouldNotBeAbleToPlaceMoreThan28TotalNonCoalOreOnTheBelt() {
        TestUtils.getMockPlayer("bf-orelimit").use { p ->
            addItem(p, Items.GOLD_ORE_444, 28)
            BlastFurnace.placeAllOre(p)
            addItem(p, Items.RUNITE_ORE_451, 28)
            BlastFurnace.placeAllOre(p)

            Assertions.assertEquals(28, BlastFurnace.getAmountOnBelt(p, Items.GOLD_ORE_444))
            Assertions.assertEquals(0, amountInInventory(p, Items.GOLD_ORE_444))
            Assertions.assertEquals(0, BlastFurnace.getAmountOnBelt(p, Items.RUNITE_ORE_451))
            Assertions.assertEquals(28, amountInInventory(p, Items.RUNITE_ORE_451))
        }
    }

    /**
     * Should be able to place28copper and tin ore on the belt.
     */
    @Test
    fun shouldBeAbleToPlace28CopperAndTinOreOnTheBelt() {
        TestUtils.getMockPlayer("bf-orelimit").use { p ->
            addItem(p, Items.COPPER_ORE_436, 28)
            BlastFurnace.placeAllOre(p)
            addItem(p, Items.TIN_ORE_438, 28)
            BlastFurnace.placeAllOre(p)

            Assertions.assertEquals(28, BlastFurnace.getAmountOnBelt(p, Items.COPPER_ORE_436))
            Assertions.assertEquals(0, amountInInventory(p, Items.COPPER_ORE_436))
            Assertions.assertEquals(28, BlastFurnace.getAmountOnBelt(p, Items.TIN_ORE_438))
            Assertions.assertEquals(0, amountInInventory(p, Items.TIN_ORE_438))
        }
    }

    /**
     * Blast furnace area should persist info across player relogs.
     */
    @Test
    fun BFAreaShouldPersistInfoAcrossPlayerRelogs() {
        TestUtils.getMockPlayer("bf-persistence").use { p ->
            var container = BlastFurnace.getOreContainer(p)
            container.addOre(Items.IRON_ORE_440, 15)
            container.addCoal(40)

            BlastFurnace.addOreToBelt(p, Items.IRON_ORE_440, 4)
            BlastFurnace.addOreToBelt(p, Items.RUNITE_ORE_451, 10)
            BlastFurnace.addOreToBelt(p, Items.COAL_453, 20)

            val area = BlastFurnace()
            val saveObj = JSONObject()
            area.savePlayer(p, saveObj)

            BlastFurnace.playerStates.clear()

            area.parsePlayer(p, saveObj)
            container = BlastFurnace.getOreContainer(p)
            Assertions.assertEquals(15, container.getOreAmount(Items.IRON_ORE_440))
            Assertions.assertEquals(40, container.coalAmount())
            Assertions.assertEquals(4, BlastFurnace.getAmountOnBelt(p, Items.IRON_ORE_440))
            Assertions.assertEquals(10, BlastFurnace.getAmountOnBelt(p, Items.RUNITE_ORE_451))
            Assertions.assertEquals(20, BlastFurnace.getAmountOnBelt(p, Items.COAL_453))
        }
    }

    companion object {
        init {
            TestUtils.preTestSetup()
            BlastFurnaceListeners().defineListeners()
        }
    }
}