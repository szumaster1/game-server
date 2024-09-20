package content.global.random.event.lostandfound

import core.api.*
import core.cache.def.impl.SceneryDefinition
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.world.map.Location
import core.tools.RandomFunction

/**
 * Lost and found utils.
 */
object LostAndFoundUtils {

    val eventLocation = Location(2338, 4747, 0)
    val previousLocation = "/save:original-loc"
    val essenceMine = "laf:essence-mine"

    /**
     * Cleanup the player's state after the lost and found event.
     *
     * @param [player] the player to clean up.
     */
    fun cleanup(player: Player) {
        setMinimapState(player, 0) // Reset the minimap state for the player.
        player.properties.teleportLocation = getAttribute(player, previousLocation, null) // Restore the player's previous location.
        removeAttribute(player, essenceMine) // Remove the attribute related to the essence mine.
    }

    /**
     * Check if the scenery is an odd appendage for the player.
     *
     * @param [player]  the player to check.
     * @param [scenery] the scenery to check.
     * @return `true` if the scenery is an odd appendage, `false` otherwise.
     */
    fun isOddAppendage(player: Player, scenery: Scenery): Boolean {
        val index: Int = scenery.getWrapper().id - 8994
        val current: Int = scenery.getWrapper().getChild(player).id
        for (i in 0..3) {
            if (index != i && SceneryDefinition.forId(8994 + i).getChildObject(player).id == current) {
                return false
            }
        }
        return true
    }

    /**
     * Set a random appendage for the player.
     *
     * @param [player] the player to set the appendage for.
     */
    fun setRandomAppendage(player: Player) {
        var value = 0
        val odd: Int = RandomFunction.random(4)
        val mod = if (RandomFunction.RANDOM.nextBoolean()) 0 else 1
        val offset: Int = RandomFunction.random(4) * 2
        for (i in 0..3) {
            value = if (i == odd) {
                value or ((offset + (1 - mod)) shl (i * 5))
            } else {
                value or ((offset + mod) shl (i * 5))
            }
        }
        setVarp(player, 531, value)
    }


    fun reward(player: Player) {
        val runes = player.getAttribute("teleport:items", emptyArray<Item>())
        runes.size
        for (rune in runes) {
            addItem(player, rune.id, rune.amount)
            sendDoubleItemDialogue(player, runes[0], runes[1], "Abyssal Services apologise for any inconvenience. Please accept these runes in way of recompense.")
        }
    }

}
