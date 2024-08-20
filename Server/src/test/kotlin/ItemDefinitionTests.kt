package core.cache

import TestUtils
import core.api.consts.Items
import core.game.node.item.Item
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.math.roundToInt

/**
 * Item definition tests.
 */
class ItemDefinitionTests {
    companion object {
        init {
            TestUtils.preTestSetup()
        }
    }

    /**
     * Item high alch is60percent value random item1
     */
    @Test
    fun itemHighAlchIs60PercentValueRandomItem1() {
        val natureRune = Item(Items.NATURE_RUNE_561)
        Assertions.assertEquals(
            (natureRune.definition.value * 0.6).roundToInt(),
            natureRune.definition.getAlchemyValue(true)
        )
    }

    /**
     * Item high alch is60percent value random item2
     */
    @Test
    fun itemHighAlchIs60PercentValueRandomItem2() {
        val tuna = Item(Items.TUNA_361)
        Assertions.assertEquals((tuna.definition.value * 0.6).roundToInt(), tuna.definition.getAlchemyValue(true))
    }

    /**
     * Item high alch is60percent value random item3
     */
    @Test
    fun itemHighAlchIs60PercentValueRandomItem3() {
        val bass = Item(Items.BASS_365)
        Assertions.assertEquals((bass.definition.value * 0.6).roundToInt(), bass.definition.getAlchemyValue(true))
    }

    /**
     * Item high alch is60percent value random item4
     */
    @Test
    fun itemHighAlchIs60PercentValueRandomItem4() {
        val goldBar = Item(Items.GOLD_BAR_2357)
        Assertions.assertEquals((goldBar.definition.value * 0.6).roundToInt(), goldBar.definition.getAlchemyValue(true))
    }

    /**
     * Item low alch is40percent value random item1
     */
    @Test
    fun itemLowAlchIs40PercentValueRandomItem1() {
        val natureRune = Item(Items.NATURE_RUNE_561)
        Assertions.assertEquals(
            (natureRune.definition.value * 0.4).roundToInt(),
            natureRune.definition.getAlchemyValue(false),
            natureRune.name
        )
    }

    /**
     * Item low alch is40percent value random item2
     */
    @Test
    fun itemLowAlchIs40PercentValueRandomItem2() {
        val tuna = Item(Items.TUNA_361)
        Assertions.assertEquals(
            (tuna.definition.value * 0.4).roundToInt(),
            tuna.definition.getAlchemyValue(false),
            tuna.name
        )
    }

    /**
     * Item low alch is40percent value random item3
     */
    @Test
    fun itemLowAlchIs40PercentValueRandomItem3() {
        val bass = Item(Items.BASS_365)
        Assertions.assertEquals(
            (bass.definition.value * 0.4).roundToInt(),
            bass.definition.getAlchemyValue(false),
            bass.name
        )
    }

    /**
     * Item low alch is40percent value random item4
     */
    @Test
    fun itemLowAlchIs40PercentValueRandomItem4() {
        val goldBar = Item(Items.GOLD_BAR_2357)
        Assertions.assertEquals(
            (goldBar.definition.value * 0.4).roundToInt(),
            goldBar.definition.getAlchemyValue(false),
            goldBar.name
        )
    }
}
