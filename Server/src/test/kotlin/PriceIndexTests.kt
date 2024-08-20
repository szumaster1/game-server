import core.game.ge.GEDB
import core.game.ge.PriceIndex
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Price index tests.
 */
class PriceIndexTests {
    companion object {
        init {
            TestUtils.preTestSetup(); GEDB.init("ge_test.db")
        }
    }

    /**
     * Should allow checking if item can be sold on Grand Exchange
     */
    @Test
    fun shouldAllowCheckingIfItemCanBeSoldOnGE() {
        PriceIndex.allowItem(4151)
        val canSellWhip = PriceIndex.canTrade(4151)
        Assertions.assertEquals(true, canSellWhip)
    }

    /**
     * Should allow banning items
     */
    @Test
    fun shouldAllowBanningItems() {
        PriceIndex.allowItem(1333)
        Assertions.assertEquals(true, PriceIndex.canTrade(1333))
        PriceIndex.banItem(1333)
        Assertions.assertEquals(false, PriceIndex.canTrade(1333))
    }

    /**
     * Should allow value to be influenced
     */
    @Test
    fun shouldAllowValueToBeInfluenced() {
        PriceIndex.allowItem(1333)
        val defaultValue = PriceIndex.getValue(1333)
        PriceIndex.addTrade(1333, 100, (defaultValue * 1.15).toInt())
        Assertions.assertNotEquals(defaultValue, PriceIndex.getValue(1333))
    }

    /**
     * Should allow value to go down with high volume of lower value trades
     */
    @Test
    fun shouldAllowValueToGoDownWithHighVolumeOfLowerValueTrades() {
        PriceIndex.allowItem(1334)
        val defaultValue = PriceIndex.getValue(1334)
        PriceIndex.addTrade(1334, 1000, (defaultValue * 1.15).toInt())
        Assertions.assertEquals(true, PriceIndex.getValue(1334) > defaultValue)
        PriceIndex.addTrade(1334, 2000, (defaultValue * 0.85).toInt())
        Assertions.assertEquals(
            true,
            PriceIndex.getValue(1334) < defaultValue,
            "Old: $defaultValue, New: ${PriceIndex.getValue(1334)}"
        )
    }
}