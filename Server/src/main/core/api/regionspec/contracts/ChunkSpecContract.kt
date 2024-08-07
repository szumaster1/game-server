package core.api.regionspec.contracts

import core.game.world.map.build.DynamicRegion

/**
 * Chunk spec contract
 *
 * @constructor Chunk spec contract
 */
interface ChunkSpecContract {
    /**
     * Populate chunks
     *
     * @param dyn
     */
    fun populateChunks(dyn: DynamicRegion)
}
