package content.global.skill.combat.equipment.gloves

import core.game.node.entity.skill.Skills
import org.rs.consts.Items

enum class BrawlingGloves(val id: Int, val charges: Int, val indicator: Int, val skillSlot: Int) {
    MELEE(Items.BRAWLING_GLOVES_MELEE_13845, 500, 1, Skills.ATTACK),
    RANGED(Items.BRAWLING_GLOVES_RANGED_13846, 500, 2, Skills.RANGE),
    MAGIC(Items.BRAWLING_GLOVES_MAGIC_13847, 500, 3, Skills.MAGIC),
    PRAYER(Items.BRAWLING_GLOVES_PRAYER_13848, 450, 4, Skills.PRAYER),
    AGILITY(Items.BRAWLING_GLOVES_AGILITY_13849, 450, 5, Skills.AGILITY),
    WOODCUTTING(Items.BRAWLING_GLOVES_WC_13850, 450, 6, Skills.WOODCUTTING),
    FIREMAKING(Items.BRAWLING_GLOVES_FM_13851, 450, 7, Skills.FIREMAKING),
    MINING(Items.BRAWLING_GLOVES_MINING_13852, 450, 8, Skills.MINING),
    HUNTER(Items.BRAWLING_GLOVES_HUNTER_13853, 450, 9, Skills.HUNTER),
    THIEVING(Items.BRAWLING_GLOVES_THIEVING_13854, 450, 10, Skills.THIEVING),
    SMITHING(Items.BRAWLING_GLOVES_SMITHING_13855, 450, 11, Skills.SMITHING),
    FISHING(Items.BRAWLING_GLOVES_FISHING_13856, 450, 12, Skills.FISHING),
    COOKING(Items.BRAWLING_GLOVES_COOKING_13857, 450, 13, Skills.COOKING);

    companion object {
        private val glovesMap = mutableMapOf<Int, BrawlingGloves>()
        private val skillMap = mutableMapOf<Int, BrawlingGloves>()
        private val indicatorMap = mutableMapOf<Int, BrawlingGloves>()

        init {
            values().forEach { glove ->
                glovesMap[glove.id] = glove
                skillMap[glove.skillSlot] = glove
                indicatorMap[glove.indicator] = glove
            }
        }

        @JvmStatic fun forId(id: Int): BrawlingGloves? = glovesMap[id]
        @JvmStatic fun forIndicator(indicator: Int): BrawlingGloves? = indicatorMap[indicator]
        @JvmStatic fun forSkill(skillSlot: Int): BrawlingGloves? = skillMap[skillSlot]
    }
}
