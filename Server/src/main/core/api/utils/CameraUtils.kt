package core.api.utils

object CameraUtils

enum class CameraShakeType {
    TRUCK,      // camera movement from left to right
    PEDESTAL,   // camera movement vertically up to down, fixated on one location
    DOLLY,      // camera movement forwards to backwards
    PAN,        // camera movement horizontally, fixed on a certain point
    TILT        // camera movement vertically, fixed on a certain point
}
