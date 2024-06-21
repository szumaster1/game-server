package core.game.node.entity.combat.spell

import core.api.hasTimerActive
import core.game.container.impl.EquipmentContainer
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills

enum class SpellType(val accuracyMod: Double) {

    /*
     * The strike spell type.
     */

    STRIKE(1.0) {
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            if (victim is NPC && victim.id == 205) {
                return 8 + base
            }
            return 2 * base
        }
    },

    /*
     * The bolt spell type.
     */

    BOLT(1.1) {
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            if (e is Player && e.equipment.getNew(EquipmentContainer.SLOT_HANDS).id == 777) {
                return 11 + base
            }
            return 8 + base
        }
    },

    /*
     * Crumble undead.
     */

    CRUMBLE_UNDEAD(1.2) {
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            return 15 // Hits as high as Earth blast
        }
    },

    /*
     * The blast spell type.
     */

    BLAST(1.2) {
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            return 12 + base
        }
    },

    /*
     * The wave spell type.
     */

    WAVE(1.3) {
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            return 16 + base
        }
    },

    /*
     * The rush spell type.
     */

    RUSH(1.1) {
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            return 14 + base
        }
    },

    /*
     * The burst spell type.
     */

    BURST(1.2) {
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            return 18 + base
        }
    },

    /*
     * The blitz spell type.
     */

    BLITZ(1.3) {
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            return 22 + base
        }
    },

    /*
     * The barrage spell type.
     */

    BARRAGE(1.4) {
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            return 26 + base
        }
    },

    /*
     * The confuse spell type.
     */

    CONFUSE(1.15),

    /*
     * The weaken spell type.
     */

    WEAKEN(1.15),

    /*
     * The curse spell type.
     */

    CURSE(1.15),

    /*
     * The vulnerability spell type.
     */

    VULNERABILITY(1.25),

    /*
     * The enfeeble spell type.
     */

    ENFEEBLE(1.25),

    /*
     * The stun spell type.
     */

    STUN(1.25),

    /*
     * The god strike spell type.
     */

    GOD_STRIKE(1.2) {
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            if (e !is Player) return 20
            if (hasTimerActive(e, "magic:spellcharge")) {
                val cape = e.equipment.getNew(EquipmentContainer.SLOT_CAPE)
                if (cape.id == 2412 || cape.id == 2413 || cape.id == 2414) {
                    return 30
                }
            }
            return 20
        }
    },

    /*
     * The bind spell type.
     */

    BIND(1.1),

    /*
     * The snare spell type.
     */

    SNARE(1.2),

    /*
     * The entangle spell type.
     */

    ENTANGLE(1.3),

    /*
     * The magic dart spell type.
     */

    MAGIC_DART(1.15) {
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            return 10 + (e.getSkills().getLevel(Skills.MAGIC) / 10)
        }
    },

    /*
     * The iban blast spell type.
     */

    IBANS_BLAST(1.4) {
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            return 25
        }
    },

    /*
     * The teleportation block spell type.
     */

    TELEBLOCK(1.3),

    /*
     * The null spell type.
     */

    NULL(0.0);


    open fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
        return 2
    }
}
