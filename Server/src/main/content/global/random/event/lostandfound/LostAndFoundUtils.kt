package content.global.random.event.lostandfound

import core.api.getAttribute
import core.api.removeAttribute
import core.api.setMinimapState
import core.api.setVarp
import core.cache.def.impl.SceneryDefinition
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.world.map.Location
import core.tools.RandomFunction

object LostAndFoundUtils {

    val eventLocation = Location(2338, 4747, 0)
    val previousLocation = "/save:original-loc"
    val essenceMine = "laf:essence-mine"

    fun cleanup(player: Player) {
        setMinimapState(player, 0)
        player.properties.teleportLocation = getAttribute(player, previousLocation, null)
        removeAttribute(player, essenceMine)
    }

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

    /*
     * fun reward(player: Player) {
     *     val runes = player.getAttribute("teleport:items", emptyArray<Item>())
     *     runes.size
     *     for (rune in runes) {
     *         addItem(player, rune.id, rune.amount)
     *         sendDoubleItemDialogue(player, RUNE_1, RUNE_2, "")
     *     }
     * }
     */
}
