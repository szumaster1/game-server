package core.api.utils

object CameraUtils

/**
 * Camera shake type.
 */
enum class CameraShakeType {
    /**
     * Truck.
     */
    TRUCK, // Camera movement from left to right.

    /**
     * Pedestal.
     */
    PEDESTAL, // Camera movement vertically up to down, fixated on one location.

    /**
     * Dolly.
     */
    DOLLY, // Camera movement forwards to backwards.

    /**
     * Pan.
     */
    PAN, // Camera movement horizontally, fixed on a certain point.

    /**
     * Tilt.
     */
    TILT // Camera movement vertically, fixed on a certain point.
}
