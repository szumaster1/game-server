package content.global.skill.combat.equipment

import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Barrows equipment register.
 */
@Initializable
class BarrowsEquipmentRegister : Plugin<Any> {
    companion object {
        /*
         * Barrows equipment lasts for 15 hours of
         * combat, and each piece has 4 stages of
         * degradation.
         */
        const val TICKS = (15 * 6000) / 4
    }

    private val equipmentSets = mapOf(
        "Dharok" to arrayOf(4716, 4880, 4881, 4882, 4883, 4884, 4718, 4886, 4887, 4888, 4889, 4890, 4720, 4892, 4893, 4894, 4895, 4896, 4722, 4898, 4899, 4900, 4901, 4902),
        "Guthan" to arrayOf(4724, 4904, 4905, 4906, 4907, 4908, 4726, 4910, 4911, 4912, 4913, 4914, 4728, 4916, 4917, 4918, 4919, 4920, 4730, 4922, 4923, 4924, 4925, 4926),
        "Torag" to arrayOf(4745, 4952, 4953, 4954, 4955, 4956, 4747, 4958, 4959, 4960, 4961, 4962, 4749, 4964, 4965, 4966, 4967, 4968, 4751, 4970, 4971, 4972, 4973, 4974),
        "Verac" to arrayOf(4753, 4976, 4977, 4978, 4979, 4980, 4755, 4982, 4983, 4984, 4985, 4986, 4757, 4988, 4989, 4990, 4991, 4992, 4759, 4994, 4995, 4996, 4997, 4998),
        "Ahrim" to arrayOf(4708, 4856, 4857, 4858, 4859, 4860, 4710, 4862, 4863, 4864, 4865, 4866, 4712, 4868, 4869, 4870, 4871, 4872, 4714, 4874, 4875, 4876, 4877, 4878),
        "Karil" to arrayOf(4732, 4928, 4929, 4930, 4931, 4932, 4734, 4934, 4935, 4936, 4937, 4938, 4736, 4940, 4941, 4942, 4943, 4944, 4738, 4946, 4947, 4948, 4949, 4950)
    )

    override fun newInstance(arg: Any?): Plugin<Any> {
        equipmentSets.values.forEach { set ->
            EquipmentDegrader.registerSet(TICKS, set)
        }
        return this
    }

    override fun fireEvent(identifier: String?, vararg args: Any?): Any {
        return Unit
    }
}
