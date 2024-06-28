package content.global.skill.production.crafting.data

enum class SpinningData(
    val button: Int,
    val need: Int,
    val product: Int,
    val level: Int,
    val exp: Double
) {
    WOOL(19, 1737, 1759, 1, 2.5),
    FLAX(17, 1779, 1777, 10, 15.0),
    ROOT(23, 6051, 6038, 19, 30.0),
    ROOT1(23, 6043, 6038, 19, 30.0),
    ROOT2(23, 6045, 6038, 19, 30.0),
    ROOT3(23, 6047, 6038, 19, 30.0),
    ROOT4(23, 6049, 6038, 19, 30.0),
    ROOT5(23, 6053, 6038, 19, 30.0),
    SINEW(27, 9436, 9438, 10, 15.0),
    TREE_ROOTS(31, 6043, 9438, 10, 15.0),
    YAK(35, 10814, 954, 30, 25.0);


    companion object {
        @JvmStatic
        fun forId(id: Int): SpinningData? {
            for (spin in values()) {
                if (spin.button == id) {
                    return spin
                }
            }
            return null
        }
    }
}
