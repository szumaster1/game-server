package core.api.regionspec.contracts

import core.game.world.map.build.DynamicRegion

/**
 * Empty region contract.
 */
class EmptyRegionContract : RegionSpecContract {
    override fun instantiateRegion(): DynamicRegion {
        val borders = DynamicRegion.reserveArea(8, 8)
        val dyn = DynamicRegion(borders)
        return dyn
    }
}
