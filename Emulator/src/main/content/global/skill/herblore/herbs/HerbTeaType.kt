package content.global.skill.herblore.herbs

import org.rs.consts.Items

enum class HerbTeaType(val ingredients: IntArray, val product : Int) {
    HERB_TEA_0(intArrayOf(), Items.HERB_TEA_MIX_4464),// harralander
    HERB_TEA_1(intArrayOf(), Items.HERB_TEA_MIX_4466),// guam
    HERB_TEA_2(intArrayOf(), Items.HERB_TEA_MIX_4468),// marentill
    HERB_TEA_3(intArrayOf(), Items.HERB_TEA_MIX_4470),// harralander and marrentill.
    HERB_TEA_4(intArrayOf(), Items.HERB_TEA_MIX_4472),// harralander and guam.
    HERB_TEA_5(intArrayOf(), Items.HERB_TEA_MIX_4474),// 2 doses of guam.
    HERB_TEA_6(intArrayOf(), Items.HERB_TEA_MIX_4476),// guam and marrentill.
    HERB_TEA_7(intArrayOf(), Items.HERB_TEA_MIX_4478),// harralander, marrentill and guam.
    HERB_TEA_8(intArrayOf(), Items.HERB_TEA_MIX_4480),// 2 doses of guam and marrentill.
    HERB_TEA_9(intArrayOf(), Items.HERB_TEA_MIX_4482),// 2 doses of guam and harralander.
}