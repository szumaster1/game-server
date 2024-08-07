package core.api.utils

object CameraUtils

/**
 * Camera shake type
 *
 * @constructor Camera shake type
 */
enum class CameraShakeType {
    /**
     * Truck
     *
     * @constructor Truck
     */
    TRUCK,      // Camera movement from left to right.

    /**
     * Pedestal
     *
     * @constructor Pedestal
     */
    PEDESTAL,   // Camera movement vertically up to down, fixated on one location.

    /**
     * Dolly
     *
     * @constructor Dolly
     */
    DOLLY,      // Camera movement forwards to backwards.

    /**
     * Pan
     *
     * @constructor Pan
     */
    PAN,        // Camera movement horizontally, fixed on a certain point.

    /**
     * Tilt
     *
     * @constructor Tilt
     */
    TILT        // Camera movement vertically, fixed on a certain point.
}
