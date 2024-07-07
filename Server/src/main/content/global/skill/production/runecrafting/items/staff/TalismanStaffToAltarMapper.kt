package content.global.skill.production.runecrafting.items.staff

import content.global.skill.production.runecrafting.data.Altar
import content.global.skill.production.runecrafting.data.TalismanStaff

object TalismanStaffToAltarMapper {
    fun map(staff: TalismanStaff): Altar? {
        return when (staff) {
            TalismanStaff.AIR -> Altar.AIR
            TalismanStaff.MIND -> Altar.MIND
            TalismanStaff.WATER -> Altar.WATER
            TalismanStaff.EARTH -> Altar.EARTH
            TalismanStaff.FIRE -> Altar.FIRE
            TalismanStaff.BODY -> Altar.BODY
            TalismanStaff.COSMIC -> Altar.COSMIC
            TalismanStaff.CHAOS -> Altar.CHAOS
            TalismanStaff.NATURE -> Altar.NATURE
            TalismanStaff.LAW -> Altar.LAW
            TalismanStaff.DEATH -> Altar.DEATH
            TalismanStaff.BLOOD -> Altar.BLOOD
            else -> null
        }
    }
}
