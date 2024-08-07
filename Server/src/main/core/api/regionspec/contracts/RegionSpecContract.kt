package core.api.regionspec.contracts

import core.game.world.map.build.DynamicRegion

/**
 * Region spec contract
 *
 * @constructor Region spec contract
 */
interface RegionSpecContract {
    /**
     * Instantiate region
     *
     * @return
     */
    fun instantiateRegion(): DynamicRegion
}
