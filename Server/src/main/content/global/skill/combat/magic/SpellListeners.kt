package content.global.skill.combat.magic

import core.api.hasLineOfSight
import core.api.log
import core.api.removeAttribute
import core.game.event.SpellCastEvent
import core.game.interaction.MovementPulse
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.SpellBookManager
import core.game.world.map.path.Pathfinder
import core.tools.Log

/**
 * Spell listeners.
 */
object SpellListeners {

    val castMap = HashMap<String, (Player, Node?) -> Unit>()
    val spellRanges = HashMap<String, Int>()

    /**
     * Add a spell listener.
     *
     * @param spellID  The ID of the spell.
     * @param type     The type of the spell.
     * @param book     The spell book.
     * @param distance The casting distance of the spell.
     * @param method   The method to be invoked when the spell is cast.
     */
    fun add(spellID: Int, type: Int, book: String, distance: Int, method: (Player, Node?) -> Unit) {
        castMap["$book:$spellID:$type"] = method
        spellRanges["$book:$spellID:$type"] = distance
    }

    /**
     * Add a spell listener with multiple IDs.
     *
     * @param spellID  The ID of the spell.
     * @param type     The type of the spell.
     * @param ids      The array of IDs.
     * @param book     The spell book.
     * @param distance The casting distance of the spell.
     * @param method   The method to be invoked when the spell is cast.
     */
    fun add(spellID: Int, type: Int, ids: IntArray, book: String, distance: Int, method: (Player, Node?) -> Unit) {
        for (id in ids) {
            castMap["$book:$spellID:$type:$id"] = method
            spellRanges["$book:$spellID:$type:$id"] = distance
        }
    }

    /**
     * Get the casting range and method for a spell.
     *
     * @param spellID The ID of the spell.
     * @param type    The type of the spell.
     * @param book    The spell book.
     * @return a pair containing the casting range and the method to be invoked.
     */
    fun get(spellID: Int, type: Int, book: String): Pair<Int, ((Player, Node?) -> Unit)?> {
        log(this::class.java, Log.FINE, "Getting $book:$spellID:$type")
        return Pair(spellRanges["$book:$spellID:$type"] ?: 10, castMap["$book:$spellID:$type"])
    }

    /**
     * Get the casting range and method for a spell with a specific ID.
     *
     * @param spellID The ID of the spell.
     * @param type    The type of the spell.
     * @param id      The ID of the spell instance.
     * @param book    The spell book.
     * @return a pair containing the casting range and the method to be invoked.
     */
    fun get(spellID: Int, type: Int, id: Int, book: String): Pair<Int, ((Player, Node?) -> Unit)?> {
        log(this::class.java, Log.FINE, "Getting $book:$spellID:$type:$id")
        return Pair(spellRanges["$book:$spellID:$type:$id"] ?: 10, castMap["$book:$spellID:$type:$id"])
    }

    /**
     * Run the spell casting process.
     *
     * @param button The button ID.
     * @param type   The type of the spell.
     * @param book   The spell book.
     * @param player The player casting the spell.
     * @param node   The target node of the spell.
     */
    @JvmStatic
    fun run(button: Int, type: Int, book: String, player: Player, node: Node? = null) {
        var (range, method) = get(button, type, node?.id ?: 0, book)
        if (method == null) {
            var next = get(button, type, book)
            range = next.first
            method = next.second ?: return
        }

        if (type in intArrayOf(SpellListener.NPC, SpellListener.OBJECT, SpellListener.PLAYER, SpellListener.GROUND_ITEM)) {
            player.pulseManager.run(object : MovementPulse(player, node, Pathfinder.SMART) {
                override fun pulse(): Boolean {
                    try {
                        method.invoke(player, node)
                    } catch (e: IllegalStateException) {
                        player.removeAttribute("spell:runes")
                        return true
                    }
                    return true
                }

                override fun update(): Boolean {
                    if (player.location.withinMaxnormDistance(node!!.centerLocation, range) && hasLineOfSight(player, node)) {
                        player.faceLocation(node.getFaceLocation(player.location))
                        player.walkingQueue.reset()
                        pulse()
                        stop()
                        return true
                    }
                    return super.update()
                }
            })
        } else {
            try {
                method.invoke(player, node)
                player.dispatch(SpellCastEvent(SpellBookManager.SpellBook.valueOf(book.uppercase()), button, node))
            } catch (e: IllegalStateException) {
                removeAttribute(player, "spell:runes")
                return
            }
        }
    }
}
