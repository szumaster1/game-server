package core.api.regionspec.contracts

import core.game.world.map.BuildRegionChunk
import core.game.world.map.Region
import core.game.world.map.RegionChunk
import core.game.world.map.build.DynamicRegion

/**
 * Fill chunk contract
 *
 * @property chunk
 * @constructor Fill chunk contract
 */
open class FillChunkContract(var chunk: RegionChunk? = null) : ChunkSpecContract {
    constructor(chunk: (Int, Int, Int, Region) -> RegionChunk?) : this(null) {
        this.chunkDelegate = chunk
    }

    lateinit var sourceRegion: Region
    var planes: IntArray = intArrayOf(0)
    var replaceCondition: (Int, Int, Int) -> Boolean = { _, _, _ -> true }
    var chunkDelegate: (Int, Int, Int, Region) -> RegionChunk? = { _, _, _, _ -> chunk }

    override fun populateChunks(dyn: DynamicRegion) {
        for (plane in planes) {
            for (x in 0 until 8)
                for (y in 0 until 8)
                    if (replaceCondition.invoke(x, y, plane)) {
                        val chunk = getChunk(x, y, plane, dyn)
                        dyn.replaceChunk(
                            plane,
                            x,
                            y,
                            chunk,
                            sourceRegion
                        )
                        afterSetting(chunk, x, y, plane, dyn)
                    }
        }
    }

    /**
     * Get chunk
     *
     * @param x
     * @param y
     * @param plane
     * @param dyn
     * @return
     */
    open fun getChunk(x: Int, y: Int, plane: Int, dyn: DynamicRegion): BuildRegionChunk? {
        return chunkDelegate.invoke(x, y, plane, sourceRegion)?.copy(dyn.planes[plane])
    }

    /**
     * After setting
     *
     * @param chunk
     * @param x
     * @param y
     * @param plane
     * @param dyn
     */
    open fun afterSetting(chunk: BuildRegionChunk?, x: Int, y: Int, plane: Int, dyn: DynamicRegion) {}

    /**
     * From
     *
     * @param region
     * @return
     */
    fun from(region: Region): FillChunkContract {
        this.sourceRegion = region
        return this
    }

    /**
     * On planes
     *
     * @param planes
     * @return
     */
    fun onPlanes(vararg planes: Int): FillChunkContract {
        this.planes = planes
        return this
    }

    /**
     * On condition
     *
     * @param cond
     * @receiver
     * @return
     */
    fun onCondition(cond: (Int, Int, Int) -> Boolean): FillChunkContract {
        this.replaceCondition = cond
        return this
    }
}
