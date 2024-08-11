package core.api.regionspec.contracts

import core.game.world.map.build.DynamicRegion

/**
 * Chunk spec contract.
 */
interface ChunkSpecContract {
    /**
     * Populate chunks.
     */
    fun populateChunks(dyn: DynamicRegion)
}
