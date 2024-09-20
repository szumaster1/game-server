package core.api.regionspec

import core.api.regionspec.contracts.*
import core.game.world.map.Region
import core.game.world.map.RegionChunk
import core.game.world.map.build.DynamicRegion

/**
 * Class representing a region specification.
 *
 * @param regionContract The contract defining the region.
 * @param chunkContracts The contracts defining the chunks within the region.
 * @constructor Represents a RegionSpecification with a region contract and chunk contracts.
 */
class RegionSpecification(
    val regionContract: RegionSpecContract = EmptyRegionContract(),
    vararg val chunkContracts: ChunkSpecContract = arrayOf(EmptyChunkContract())
) {
    constructor(vararg chunkContracts: ChunkSpecContract) : this(EmptyRegionContract(), *chunkContracts)

    /**
     * Build
     *
     * @return A DynamicRegion instance created from the region contract and populated with chunks.
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
 * @param chunk The chunk to fill with.
 * @return A FillChunkContract instance initialized with the provided chunk.
 */
fun fillWith(chunk: RegionChunk?): FillChunkContract {
    return FillChunkContract(chunk) // Return a FillChunkContract initialized with the specified chunk
}

/**
 * Fill with
 *
 * @param delegate A function that defines how to create a RegionChunk.
 * @receiver The receiver of the function.
 * @return A FillChunkContract instance initialized with the provided delegate function.
 */
fun fillWith(delegate: (Int, Int, Int, Region) -> RegionChunk?): FillChunkContract {
    return FillChunkContract(delegate) // Return a FillChunkContract initialized with the specified delegate function
}

/**
 * Copy of
 *
 * @param regionId The ID of the region to copy.
 * @return A RegionSpecContract instance that represents a clone of the specified region.
 */
fun copyOf(regionId: Int): RegionSpecContract {
    return CloneRegionContract(regionId) // Return a CloneRegionContract initialized with the specified region ID
}

/**
 * Using
 *
 * @param region The existing dynamic region to use.
 * @return A UseExistingRegionContract instance initialized with the provided region.
 */
fun using(region: DynamicRegion): UseExistingRegionContract {
    return UseExistingRegionContract(region) // Return a UseExistingRegionContract initialized with the specified dynamic region
}
