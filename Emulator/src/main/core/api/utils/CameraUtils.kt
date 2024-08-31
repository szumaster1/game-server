package core.api.utils

object CameraUtils

/**
 * Camera shake type.
 */
enum class CameraShakeType {
    /**
     * Camera movement from left to right.
     */
    TRUCK,

    /**
     * Camera movement vertically up to down, fixated on one location.
     */
    PEDESTAL,

    /**
     * Camera movement forwards to backwards.
     */
    DOLLY,

    /**
     * Camera movement horizontally, fixed on a certain point.
     */
    PAN,

    /**
     * Camera movement vertically, fixed on a certain point.
     */
    TILT
}
