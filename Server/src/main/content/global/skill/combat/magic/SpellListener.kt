package content.global.skill.combat.magic

import core.api.playAudio
import core.api.playGlobalAudio
import core.api.setAttribute
import core.cache.def.impl.ItemDefinition
import core.game.interaction.Listener
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.GameWorld
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic

/**
 * Spell listener.
 */
abstract class SpellListener(val bookName: String) : Listener {
    companion object {
        @JvmField
        val NPC = -1 // Represents the ID for an NPC target.

        @JvmField
        val OBJECT = -2 // Represents the ID for an object target.

        @JvmField
        val ITEM = -3 // Represents the ID for an item target.

        @JvmField
        val PLAYER = -4 // Represents the ID for a player target.

        @JvmField
        val NONE = -5 // Represents no target.

        @JvmField
        val GROUND_ITEM = -6 // Represents the ID for a ground item target.
    }

    /**
     * On cast.
     *
     * @param spellID The ID of the spell being cast.
     * @param type    The type of the spell.
     * @param range   The range of the spell.
     * @param method  The method to be executed when the spell is cast.
     * @receiver The receiver of the spell.
     */
    fun onCast(spellID: Int, type: Int, range: Int = 10, method: (player: Player, node: Node?) -> Unit) {
        SpellListeners.add(spellID, type, bookName, range, method)
    }

    /**
     * On cast.
     *
     * @param spellID The ID of the spell being cast.
     * @param type    The type of the spell.
     * @param ids     The IDs of the spell.
     * @param range   The range of the spell.
     * @param method  The method to be executed when the spell is cast.
     * @receiver The receiver of the spell.
     */
    fun onCast(
        spellID: Int,
        type: Int,
        vararg ids: Int,
        range: Int = 10,
        method: (player: Player, node: Node?) -> Unit
    ) {
        SpellListeners.add(spellID, type, ids, bookName, range, method)
    }

    /**
     * Requires.
     *
     * @param player           The player casting the spell.
     * @param magicLevel       The required magic level to cast the spell.
     * @param runes            The required runes to cast the spell.
     * @param specialEquipment The required special equipment to cast the spell.
     */
    fun requires(
        player: Player,
        magicLevel: Int = 0,
        runes: Array<Item> = arrayOf<Item>(),
        specialEquipment: IntArray = intArrayOf()
    ) {
        if (player.getAttribute("magic-delay", 0) > GameWorld.ticks) {
            throw IllegalStateException()
        }
        if (player.getAttribute("tablet-spell", false)) {
            return
        }
        if (player.skills.getLevel(Skills.MAGIC) < magicLevel) {
            player.sendMessage("You need a magic level of $magicLevel to cast this spell.")
            throw IllegalStateException()
        }
        for (rune in runes) {
            if (!SpellUtils.hasRune(player, rune)) {
                player.sendMessage("You don't have enough ${rune.definition.name.lowercase()}s to cast this spell.")
                throw IllegalStateException()
            }
        }
        for (item in specialEquipment) {
            if (!player.equipment.contains(item, 1)) {
                player.sendMessage("You need a ${ItemDefinition.forId(item).name} to cast this.")
                throw IllegalStateException()
            }
        }
    }

    /**
     * Remove runes.
     *
     * @param player     The player casting the spell.
     * @param removeAttr Whether to remove the spell attributes.
     */
    fun removeRunes(player: Player, removeAttr: Boolean = true) {
        player.inventory.remove(*player.getAttribute("spell:runes", ArrayList<Item>()).toTypedArray())
        if (removeAttr) {
            player.removeAttribute("spell:runes")
            player.removeAttribute("tablet-spell")
        }
    }

    /**
     * Add XP.
     *
     * @param player The player casting the spell.
     * @param amount The amount of experience to add.
     */
    fun addXP(player: Player, amount: Double) {
        if (player.getAttribute("tablet-spell", false)) return
        player.skills.addExperience(Skills.MAGIC, amount)
    }

    /**
     * Visualize spell.
     *
     * @param player  The player casting the spell.
     * @param anim    The animation to be visualized.
     * @param gfx     The graphic to be visualized.
     * @param soundID The ID of the sound to be played.
     * @param delay   The delay before the visualization.
     * @param global  Whether the visualization is global.
     */
    fun visualizeSpell(
        player: Player,
        anim: Animation,
        gfx: Graphic,
        soundID: Int = -1,
        delay: Int = 0,
        global: Boolean = true
    ) {
        if (player.getAttribute("tablet-spell", false)) return
        player.visualize(anim, gfx)
        if (soundID != -1) {
            if (global) playGlobalAudio(player.location, soundID, delay)
            else playAudio(player, soundID, delay)
        }
    }

    /**
     * Visualize spell.
     *
     * @param player  The player casting the spell.
     * @param anim    The ID of the animation to be visualized.
     * @param gfx     The ID of the graphic to be visualized.
     * @param height  The height of the graphic.
     * @param soundID The ID of the sound to be played.
     * @param delay   The delay before the visualization.
     * @param global  Whether the visualization is global.
     */
    fun visualizeSpell(
        player: Player,
        anim: Int,
        gfx: Int,
        height: Int = 0,
        soundID: Int = -1,
        delay: Int = 0,
        global: Boolean = true
    ) {
        if (player.getAttribute("tablet-spell", false)) return
        player.visualize(Animation(anim), Graphic(gfx, height))
        if (soundID != -1) {
            if (global) playGlobalAudio(player.location, soundID, delay)
            else playAudio(player, soundID, delay)
        }
    }

    /**
     * Set delay.
     *
     * @param player     The player casting the spell.
     * @param isTeleport Whether the spell is a teleport spell.
     */
    fun setDelay(player: Player, isTeleport: Boolean = false) {
        if (!isTeleport) player.setAttribute("magic-delay", GameWorld.ticks + 3) else player.setAttribute(
            "magic-delay",
            GameWorld.ticks + 5
        )
    }

    /**
     * Set delay.
     *
     * @param player The player casting the spell.
     * @param delay  The delay before the spell can be cast again.
     */
    fun setDelay(player: Player, delay: Int) {
        setAttribute(player, "magic-delay", GameWorld.ticks + delay)
    }

    /**
     * Interrupt.
     *
     * @param player The player casting the spell.
     */
    fun interrupt(player: Player) {
        player.pulseManager.clear()
    }

    /**
     * Show magic tab.
     *
     * @param player The player casting the spell.
     */
    fun showMagicTab(player: Player) {
        player.interfaceManager.setViewedTab(6)
    }
}
