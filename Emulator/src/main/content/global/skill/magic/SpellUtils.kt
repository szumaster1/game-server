package content.global.skill.magic

import core.game.node.entity.combat.spell.CombinationRune
import core.game.node.entity.combat.spell.MagicStaff
import core.game.node.entity.combat.spell.Runes
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Spell utils.
 */
object SpellUtils {

    /**
     * Using staff.
     *
     * @param player    the player.
     * @param rune      the rune ID.
     * @return `true` if the player is using the staff, `false` otherwise.
     */
    @JvmStatic
    fun usingStaff(player: Player, rune: Int): Boolean {
        val weapon = player.equipment[3] ?: return false
        val staff = MagicStaff.forId(rune) ?: return false
        val staves = staff.staves
        for (id in staves) {
            if (weapon.id == id) {
                return true
            }
        }
        return false
    }

    /**
     * Has rune.
     *
     * @param player    the player.
     * @param rune      the rune item.
     * @return True if the player has the specified rune, false otherwise.
     */
    @JvmStatic
    fun hasRune(player: Player, rune: Item): Boolean {
        val removeItems = player.getAttribute("spell:runes", ArrayList<Item>())
        if (usingStaff(player, rune.id)) return true
        if (player.inventory.containsItem(rune)) {
            removeItems.add(rune)
            player.setAttribute("spell:runes", removeItems)
        }

        val baseAmt = player.inventory.getAmount(rune.id)
        var amtRemaining = rune.amount - baseAmt
        val possibleComboRunes = Runes.forId(rune.id)?.let { CombinationRune.eligibleFor(it) }
        if (possibleComboRunes != null) {
            for (r in possibleComboRunes) {
                if (player.inventory.containsItem(Item(r.id)) && amtRemaining > 0) {
                    val amt = player.inventory.getAmount(r.id)
                    if (amtRemaining <= amt) {
                        removeItems.add(Item(r.id, amtRemaining))
                        amtRemaining = 0
                        break
                    }
                    removeItems.add(Item(r.id, player.inventory.getAmount(r.id)))
                    amtRemaining -= player.inventory.getAmount(r.id)
                }
            }
        }
        player.setAttribute("spell:runes", removeItems)
        return amtRemaining <= 0
    }

    /**
     * Has rune.
     *
     * @param player    the player.
     * @param item      the item.
     * @param toRemove  the list of items to remove.
     * @param message   the message to display.
     * @return `true` if the player has the rune, `false` otherwise.
     */
    @JvmStatic
    fun hasRune(player: Player, item: Item, toRemove: MutableList<Item?>, message: Boolean): Boolean {
        if (!usingStaff(player, item.id)) {
            val hasBaseRune = player.inventory.contains(item.id, item.amount)
            if (!hasBaseRune) {
                val baseAmt = player.inventory.getAmount(item.id)
                if (baseAmt > 0) {
                    toRemove.add(Item(item.id, player.inventory.getAmount(item.id)))
                }
                var amtRemaining = item.amount - baseAmt
                val possibleComboRunes = CombinationRune.eligibleFor(Runes.forId(item.id)!!)
                for (r in possibleComboRunes) {
                    if (player.inventory.containsItem(Item(r.id)) && amtRemaining > 0) {
                        val amt = player.inventory.getAmount(r.id)
                        if (amtRemaining < amt) {
                            toRemove.add(Item(r.id, amtRemaining))
                            amtRemaining = 0
                            continue
                        }
                        amtRemaining -= player.inventory.getAmount(r.id)
                        toRemove.add(Item(r.id, player.inventory.getAmount(r.id)))
                    }
                }
                return if (amtRemaining <= 0) {
                    true
                } else {
                    player.packetDispatch.sendMessage("You don't have enough " + item.name + "s to cast this spell.")
                    false
                }
            }
            toRemove.add(item)
            return true
        }
        return true
    }

    /**
     * Attack NPC.
     *
     * @param npc the NPC.
     * @return `true` if the NPC is attackable, `false` otherwise.
     */
    fun attackableNPC(npc: NPC): Boolean {
        return npc.definition.hasAction("attack")
    }

    /**
     * Get a book from interface.
     *
     * @param id the interface id.
     * @return The book type (modern, ancient, lunar, none).
     */
    @JvmStatic
    fun getBookFromInterface(id: Int): String {
        return when (id) {
            192 -> "modern"
            193 -> "ancient"
            430 -> "lunar"
            else -> "none"
        }
    }
}
