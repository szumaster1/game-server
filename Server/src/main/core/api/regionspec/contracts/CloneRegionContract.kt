package core.api.regionspec.contracts

import core.game.world.map.build.DynamicRegion

/**
 * Clone region contract
 *
 * @property regionId
 * @constructor Clone region contract
 */
class CloneRegionContract(val regionId: Int) : RegionSpecContract {
    override fun instantiateRegion(): DynamicRegion {
        return DynamicRegion.create(regionId)
    }
}
