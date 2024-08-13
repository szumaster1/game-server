package core.game.node.entity.combat.spell

import core.api.playGlobalAudio
import core.game.component.Component
import core.game.event.SpellCastEvent
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.combat.CombatSwingHandler
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.combat.spell.MagicStaff.Companion.forId
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.SpellBookManager.SpellBook
import core.game.node.entity.player.link.audio.Audio
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.GameWorld.ticks
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Plugin
import core.tools.RandomFunction
import java.util.function.Consumer

/**
 * Magic spell.
 *
 * @param book       The book.
 * @param level      The level.
 * @param experience The experience.
 * @param animation  The animation.
 * @param graphic    The graphic.
 * @param audio      The audio.
 * @param castRunes  The runes.
 */
abstract class MagicSpell @JvmOverloads constructor(
    val book: SpellBook = SpellBook.MODERN, // Default spell book is MODERN
    val level: Int = 0, // Default level is set to 0
    @JvmField val experience: Double = 0.0, // Experience points for casting the spell
    @JvmField protected val animation: Animation? = null, // Optional animation for the spell
    @JvmField protected val graphic: Graphic? = null, // Optional graphic representation of the spell
    @JvmField val audio: Audio? = null, // Optional audio effect for the spell
    val castRunes: Array<Item?>? = arrayOfNulls(0) // Array of runes required to cast the spell, default is an empty array
) : Plugin<SpellType?> { // This class implements the Plugin interface for SpellType
    @JvmField
    var spellId: Int = 0

    open val delay: Int
        get() = 3

    /**
     * Cast boolean.
     *
     * @param entity the entity
     * @param target the target
     * @return the boolean
     */
    abstract fun cast(entity: Entity, target: Node): Boolean

    /**
     * Visualize.
     *
     * @param entity the entity
     * @param target the target
     */
    open fun visualize(entity: Entity, target: Node) {
        entity.graphics(graphic)
        entity.animate(animation)
        playGlobalAudio(entity.location, audio!!.id, 20)
    }

    /**
     * Using staff boolean.
     *
     * @param p    the p
     * @param rune the rune
     * @return the boolean
     */
    fun usingStaff(p: Player, rune: Int): Boolean {
        val weapon = p.equipment[3] ?: return false
        val staff = forId(rune) ?: return false
        val staves = staff.staves
        for (id in staves) {
            if (weapon.id == id) {
                return true
            }
        }
        return false
    }

    /**
     * Checks if the given entity meets the specified requirements.
     *
     * @param caster  the entity that is casting the action
     * @param message the boolean flag indicating if a message should be processed
     * @param remove  the boolean flag indicating if an item should be removed
     * @return true if the requirements are met, false otherwise
     */
    open fun meetsRequirements(caster: Entity, message: Boolean, remove: Boolean): Boolean {
        if (!checkLevelRequirement(caster, message)) {
            return false
        }
        if (caster is Player) {
            val spell = caster.properties.autocastSpell
            if (spell != null) {
                val slayer = caster.equipment[3].name.contains("layer's staff")
                val voidKnight = caster.equipment[3].name.contains("knight mace")
                if ((spell.spellId == 31 && !slayer) || (spell.spellId == 42 && !voidKnight)) {
                    caster.packetDispatch.sendMessage("You need the proper staff to autocast this spell.")
                    return false
                }
            }
        }
        if ((spellId == 12 || spellId == 30 || spellId == 56) && caster is Player) {
            if (caster.getAttribute("entangleDelay", 0) > ticks) {
                caster.asPlayer().sendMessage("You have recently cast a binding spell.")
                return false
            }
        }
        if (caster is Player) {
            val p = caster
            if (p.equipment[3] != null && p.equipment[3].id == 14726) {
                if (RandomFunction.getRandom(100) < 13) {
                    p.sendMessage("Your staff negates the rune requirement of the spell.")
                    return true
                }
            }
            if (castRunes == null) {
                return true
            }
            val toRemove: MutableList<Item?> = ArrayList(20)
            for (item in castRunes) {
                if (!hasRune(p, item, toRemove, message)) {
                    return false
                }
            }
            if (remove) {
                toRemove.forEach(Consumer { i: Item? ->
                    p.inventory.remove(i)
                })
            }
            return true
        }
        return true
    }

    /**
     * Check level requirement boolean.
     *
     * @param caster  the caster
     * @param message the message
     * @return the boolean
     */
    fun checkLevelRequirement(caster: Entity, message: Boolean): Boolean {
        if (caster is Player && caster.getSkills()
                .getLevel(Skills.MAGIC, if (this is CombatSpell) true else false) < levelRequirement()
        ) {
            if (message && caster is Player) {
                caster.packetDispatch.sendMessage("You need a Magic level of " + levelRequirement() + " to cast this spell.")
            }
            return false
        }
        return true
    }

    /**
     * Checks if a player has a specific rune.
     *
     * @param p        the player whose runes are being checked
     * @param item     the item associated with the rune
     * @param toRemove the list of items to be removed if the rune is found
     * @param message  indicates whether to display a message
     * @return true if the player has the rune, false otherwise
     */
    fun hasRune(p: Player, item: Item?, toRemove: MutableList<Item?>, message: Boolean): Boolean {
        if (!usingStaff(p, item!!.id)) {
            val hasBaseRune = p.inventory.contains(item.id, item.amount)
            if (!hasBaseRune) {
                val baseAmt = p.inventory.getAmount(item.id)
                if (baseAmt > 0) {
                    toRemove.add(Item(item.id, p.inventory.getAmount(item.id)))
                }
                var amtRemaining = item.amount - baseAmt
                val possibleComboRunes = CombinationRune.eligibleFor(
                    Runes.forId(item.id)!!
                )
                for (r in possibleComboRunes) {
                    if (p.inventory.containsItem(Item(r.id)) && amtRemaining > 0) {
                        val amt = p.inventory.getAmount(r.id)
                        if (amtRemaining < amt) {
                            toRemove.add(Item(r.id, amtRemaining))
                            amtRemaining = 0
                            continue
                        }
                        amtRemaining -= p.inventory.getAmount(r.id)
                        toRemove.add(Item(r.id, p.inventory.getAmount(r.id)))
                    }
                }
                if (amtRemaining <= 0) {
                    return true
                } else {
                    p.packetDispatch.sendMessage("You don't have enough " + item.name + "s to cast this spell.")
                    return false
                }
            }
            toRemove.add(item)
            return true
        }
        return true
    }

    /**
     * Add experience.
     *
     * @param entity the entity
     * @param hit    the hit
     */
    open fun addExperience(entity: Entity, hit: Int) {
        entity.getSkills().addExperience(Skills.MAGIC, experience, true)
        if (entity !is Player || hit < 1) {
            return
        }
        entity.getSkills().addExperience(Skills.HITPOINTS, hit * 1.33, true)
        if (entity.getProperties().attackStyle.style == WeaponInterface.STYLE_DEFENSIVE_CAST) {
            val baseXpReward = (CombatSwingHandler.EXPERIENCE_MOD * hit) / 2.0
            entity.getSkills().addExperience(Skills.DEFENCE, baseXpReward, true)
            entity.getSkills().addExperience(Skills.MAGIC, baseXpReward, true)
            return
        }
        entity.getSkills().addExperience(Skills.MAGIC, hit * (CombatSwingHandler.EXPERIENCE_MOD), true)
    }

    /**
     * Level requirement int.
     *
     * @return the int
     */
    fun levelRequirement(): Int {
        return level
    }

    override fun fireEvent(identifier: String, vararg args: Any): Any? {
        return null
    }

    /**
     * Gets experience.
     *
     * @param player the player
     * @return the experience
     */
    open fun getExperience(player: Player): Double {
        return experience
    }

    companion object {
        /**
         * Cast spell boolean.
         *
         * @param p       the player who is casting the spell
         * @param book    the spell book containing the spells
         * @param spellId the unique identifier for the spell being cast
         * @param target  the target node that the spell will affect
         * @return true if the spell was successfully cast, false otherwise
         */
        fun castSpell(p: Player, book: SpellBook, spellId: Int, target: Node): Boolean {
            if (p.getAttribute("magic-delay", 0) > ticks) {
                return false
            }
            val spell = book.getSpell(spellId) ?: return false
            if (spell.book != book || p.spellBookManager.spellBook != book.interfaceId) {
                return false
            }
            if (target.location != null && target !== p) {
                if (!target.location.withinDistance(p.location, 15)) {
                    return false
                }
                p.faceLocation(target.location)
            }
            val combatSpell = spell is CombatSpell
            if (!combatSpell && target is Entity) {
                p.faceTemporary(target, 1)
            }
            if (spell.cast(p, target)) {
                if (book != SpellBook.LUNAR && p.getAttribute("spell:swap", 0) != 0) {
                    p.removeAttribute("spell:swap")
                    p.spellBookManager.setSpellBook(SpellBook.LUNAR)
                    p.interfaceManager.openTab(Component(SpellBook.LUNAR.interfaceId))
                }
                if (!combatSpell) {
                    p.getSkills().addExperience(Skills.MAGIC, spell.getExperience(p), true)
                }
                if (p.getAttribute("magic-delay", 0) <= ticks) {
                    p.setAttribute("magic-delay", ticks + spell.delay)
                }
                p.dispatch(SpellCastEvent(book, spellId, target))
                return true
            }
            return false
        }
    }
}
