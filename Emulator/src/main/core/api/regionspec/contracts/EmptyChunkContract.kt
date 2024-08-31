package core.api.regionspec.contracts

import core.game.world.map.build.DynamicRegion

/**
 * Empty chunk contract.
 */
class EmptyChunkContract : ChunkSpecContract {
    override fun populateChunks(dyn: DynamicRegion) {}
}
