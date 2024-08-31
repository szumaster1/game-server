package core.game.node.entity.combat.spell

import core.api.hasTimerActive
import core.game.container.impl.EquipmentContainer
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills

/**
 * Represents the spell types.
 * @author Emperor
 *
 * @param accuracyMod to double.
 * @constructor Spell type.
 */
enum class SpellType(val accuracyMod: Double) {

    /**
     * The strike spell type.
     */
    STRIKE(1.0) {
        // Calculate the impact amount for the STRIKE spell type
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            // Check if the victim is a specific NPC with id 205
            if (victim is NPC && victim.id == 205) {
                return 8 + base // Return a higher impact for this specific NPC
            }
            return 2 * base // Default impact calculation
        }
    },

    /**
     * The bolt spell type.
     */
    BOLT(1.1) {
        // Calculate the impact amount for the BOLT spell type
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            // Check if the entity is a Player and has a specific equipment
            if (e is Player && e.equipment.getNew(EquipmentContainer.SLOT_HANDS).id == 777) {
                return 11 + base // Return a higher impact if the condition is met
            }
            return 8 + base // Default impact calculation
        }
    },

    /**
     * Crumble Undead.
     */
    CRUMBLE_UNDEAD(1.2) {
        // Calculate the impact amount for the CRUMBLE_UNDEAD spell type
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            return 15 // Hits as high as Earth blast
        }
    },

    /**
     * The blast spell type.
     */
    BLAST(1.2) {
        // Calculate the impact amount for the BLAST spell type
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            return 12 + base // Default impact calculation
        }
    },

    /**
     * The wave spell type.
     */
    WAVE(1.3) {
        // Calculate the impact amount for the WAVE spell type
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            return 16 + base // Default impact calculation
        }
    },

    /**
     * The rush spell type.
     */
    RUSH(1.1) {
        // Calculate the impact amount for the RUSH spell type
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            return 14 + base // Default impact calculation
        }
    },

    /**
     * The burst spell type.
     */
    BURST(1.2) {
        // Calculate the impact amount for the BURST spell type
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            return 18 + base // Default impact calculation
        }
    },

    /**
     * The blitz spell type.
     */
    BLITZ(1.3) {
        // Calculate the impact amount for the BLITZ spell type
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            return 22 + base // Default impact calculation
        }
    },

    /**
     * The barrage spell type.
     */
    BARRAGE(1.4) {
        // Calculate the impact amount for the BARRAGE spell type
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            return 26 + base // Default impact calculation
        }
    },

    /**
     * The confuse spell type.
     */
    CONFUSE(1.15),

    /**
     * The weaken spell type.
     */
    WEAKEN(1.15),

    /**
     * The curse spell type.
     */
    CURSE(1.15),

    /**
     * The vulnerability spell type.
     */
    VULNERABILITY(1.25),

    /**
     * The enfeeble spell type.
     */
    ENFEEBLE(1.25),

    /**
     * The stun spell type.
     */
    STUN(1.25),

    /**
     * The god strike spell type.
     */
    GOD_STRIKE(1.2) {
        // Calculate the impact amount for the GOD_STRIKE spell type
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            // Check if the entity is not a Player
            if (e !is Player) return 20 // Return a fixed impact for non-players
            // Check if the player has a specific timer active
            if (hasTimerActive(e, "magic:spellcharge")) {
                val cape = e.equipment.getNew(EquipmentContainer.SLOT_CAPE)
                // Check if the player is wearing a specific cape
                if (cape.id == 2412 || cape.id == 2413 || cape.id == 2414) {
                    return 30 // Return a higher impact if the condition is met
                }
            }
            return 20 // Default impact calculation
        }
    },

    /**
     * The bind spell type.
     */
    BIND(1.1),

    /**
     * The snare spell type.
     */
    SNARE(1.2),

    /**
     * The entangle spell type.
     */
    ENTANGLE(1.3),

    /**
     * The magic dart spell type.
     */
    MAGIC_DART(1.15) {
        // Calculate the impact amount for the MAGIC_DART spell type
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            return 10 + (e.getSkills().getLevel(Skills.MAGIC) / 10) // Impact based on magic level
        }
    },

    /**
     * The iban blast spell type.
     */
    IBANS_BLAST(1.4) {
        // Calculate the impact amount for the IBANS_BLAST spell type
        override fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
            return 25 // Fixed impact amount
        }
    },

    /**
     * The teleportation block spell type.
     */
    TELEBLOCK(1.3),

    /*
     * The null spell type.
     */

    /**
     * The null spell type.
     */
    NULL(0.0);


    /**
     * Get impact amount
     *
     * @param e The entity casting the spell
     * @param victim The entity being targeted
     * @param base The base impact value
     * @return The calculated impact amount
     */
    open fun getImpactAmount(e: Entity, victim: Entity?, base: Int): Int {
        return 2 // Default impact amount for unspecified spell types
    }
}