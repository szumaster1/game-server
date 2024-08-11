package core.api.regionspec

import core.api.regionspec.contracts.*
import core.game.world.map.Region
import core.game.world.map.RegionChunk
import core.game.world.map.build.DynamicRegion

/**
 * Class representing a region specification.
 *
 * @property regionContract The contract defining the region.
 * @property chunkContracts The contracts defining the chunks within the region.
 * @constructor Initializes a RegionSpecification with a region contract and chunk contracts.
 */
class RegionSpecification(
    val regionContract: RegionSpecContract = EmptyRegionContract(),
    vararg val chunkContracts: ChunkSpecContract = arrayOf(EmptyChunkContract())
) {
    constructor(vararg chunkContracts: ChunkSpecContract) : this(EmptyRegionContract(), *chunkContracts)

    /**
     * Build
     *
     * @return
     */
    fun build(): DynamicRegion {
        val dyn = regionContract.instantiateRegion()
        Region.load(dyn)
        chunkContracts.forEach { it.populateChunks(dyn) }
        return dyn
    }
}

/**
 * Fill with
 *
 * @param chunk
 * @return
 */
fun fillWith(chunk: RegionChunk?): FillChunkContract {
    return FillChunkContract(chunk)
}

/**
 * Fill with
 *
 * @param delegate
 * @receiver
 * @return
 */
fun fillWith(delegate: (Int, Int, Int, Region) -> RegionChunk?): FillChunkContract {
    return FillChunkContract(delegate)
}

/**
 * Copy of
 *
 * @param regionId
 * @return
 */
fun copyOf(regionId: Int): RegionSpecContract {
    return CloneRegionContract(regionId)
}

/**
 * Using
 *
 * @param region
 * @return
 */
fun using(region: DynamicRegion): UseExistingRegionContract {
    return UseExistingRegionContract(region)
}
