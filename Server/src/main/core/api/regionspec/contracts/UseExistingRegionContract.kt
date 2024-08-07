package core.api.regionspec.contracts

import core.game.world.map.build.DynamicRegion

/**
 * Use existing region contract
 *
 * @property region
 * @constructor Use existing region contract
 */
class UseExistingRegionContract(val region: DynamicRegion) : RegionSpecContract {
    override fun instantiateRegion(): DynamicRegion {
        return region
    }
}