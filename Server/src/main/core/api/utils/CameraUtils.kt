package core.api.utils

object CameraUtils

enum class CameraShakeType {
    TRUCK,      // Camera movement from left to right.
    PEDESTAL,   // Camera movement vertically up to down, fixated on one location.
    DOLLY,      // Camera movement forwards to backwards.
    PAN,        // Camera movement horizontally, fixed on a certain point.
    TILT        // Camera movement vertically, fixed on a certain point.
}
