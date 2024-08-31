package core.api.regionspec.contracts

import core.game.world.map.build.DynamicRegion

/**
 * Region spec contract.
 */
interface RegionSpecContract {
    /**
     * Instantiate region.
     */
    fun instantiateRegion(): DynamicRegion
}
