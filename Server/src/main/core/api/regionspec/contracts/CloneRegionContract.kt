package core.api.regionspec.contracts

import core.game.world.map.build.DynamicRegion

/**
 * Represents a contract for cloning a region.
 *
 * @property regionId The ID of the region to be cloned.
 * @constructor Represents a new CloneRegionContract with the specified region ID.
 */
class CloneRegionContract(val regionId: Int) : RegionSpecContract {
    override fun instantiateRegion(): DynamicRegion {
        return DynamicRegion.create(regionId)
    }
}
