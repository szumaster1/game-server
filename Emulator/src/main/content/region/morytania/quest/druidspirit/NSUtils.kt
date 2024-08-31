package content.region.morytania.quest.druidspirit

import cfg.consts.Items
import cfg.consts.Sounds
import content.region.morytania.swamp.handlers.GhastNPC
import core.api.*
import core.game.node.entity.player.Player
import core.game.node.scenery.SceneryBuilder
import core.game.system.task.Pulse
import core.game.world.map.RegionManager
import core.game.world.map.RegionManager.forId
import core.game.world.update.flag.context.Graphic
import core.tools.RandomFunction

/**
 * Represents the utility functions for Nature Spirit quest.
 */
object NSUtils {
    /**
     * Flags that a fungus has been placed by the player.
     *
     * @param player The player who placed the fungus.
     */
    fun flagFungusPlaced(player: Player) {
        setAttribute(player, "/save:ns:fungus_placed", true)
    }

    /**
     * Flags that a card has been placed by the player.
     *
     * @param player The player who placed the card.
     */
    fun flagCardPlaced(player: Player) {
        setAttribute(player, "/save:ns:card_placed", true)
    }

    /**
     * Checks if the player has placed a fungus.
     *
     * @param player The player to check.
     * @return True if the player has placed a fungus, otherwise false.
     */
    fun hasPlacedFungus(player: Player): Boolean {
        return getAttribute(player, "ns:fungus_placed", false)
    }

    /**
     * Checks if the player has placed a card.
     *
     * @param player The player to check.
     * @return True if the player has placed a card, otherwise false.
     */
    fun hasPlacedCard(player: Player): Boolean {
        return getAttribute(player, "ns:card_placed", false)
    }

    /**
     * Checks if the player is on a specific stone location.
     *
     * @param player The player to check.
     * @return True if the player is on the stone location, otherwise false.
     */
    fun onStone(player: Player): Boolean {
        return player.location.equals(3440, 3335, 0)
    }

    /**
     * Retrieves the number of Ghasts killed by the player.
     *
     * @param player The player whose Ghast kill count is to be retrieved.
     * @return The number of Ghasts killed by the player.
     */
    fun getGhastKC(player: Player): Int {
        return getAttribute(player, "ns:ghasts_killed", 0)
    }

    /**
     * Increments the Ghast kill count for the player.
     *
     * @param player The player whose Ghast kill count is to be incremented.
     */
    fun incrementGhastKC(player: Player) {
        // Update the player's Ghast kill count by setting the new value.
        setAttribute(player, "/save:ns:ghasts_killed", getGhastKC(player) + 1)
        val msg = when (getGhastKC(player)) {
            1 -> "That's one down, two more to go."
            2 -> "Two down, only one more to go."
            3 -> "That's it! I've killed all 3 Ghasts!"
            else -> ""
        }

        if (!msg.isEmpty()) {
            sendMessage(player, msg)
        }
    }

    /**
     *
     * Activates a pouch for a player when attacked by a Ghast NPC.
     *
     * @param player The player who is activating the pouch.
     * @param attacker The NPC that is attacking the player.
     * @return Returns true if the pouch activation was successful, otherwise false.
     */
    fun activatePouch(player: Player, attacker: GhastNPC): Boolean {
        var shouldAddEmptyPouch = false
        val pouchAmt = amountInInventory(player, Items.DRUID_POUCH_2958)
        if (pouchAmt == 1) shouldAddEmptyPouch = true
        if (pouchAmt > 0 && removeItem(player, Items.DRUID_POUCH_2958, Container.INVENTORY)) {
            if (shouldAddEmptyPouch) {
                addItem(player, Items.DRUID_POUCH_2957)
            }
            spawnProjectile(player, attacker, 268)
            submitWorldPulse(object : Pulse() {
                var ticks = 0
                override fun pulse(): Boolean {
                    when (ticks++) {
                        2 -> visualize(attacker, -1,
                            Graphic(269, 125)
                        )
                        3 -> attacker.transform(attacker.id + 1).also {
                            attacker.attack(player); attacker.setAttribute("woke", getWorldTicks())
                            return true
                        }
                    }
                    return false
                }
            })
            return true
        }
        return false
    }

    fun cleanupAttributes(player: Player) {
        removeAttribute(player, "ns:fungus_placed")
        removeAttribute(player, "ns:card_placed")
    }

    /**
     * Cast a bloom.
     *
     * @param player The player on whom the bloom effect will be cast.
     * @return Returns true if the bloom effect was successfully cast, otherwise false.
     */
    @JvmStatic
    fun castBloom(player: Player): Boolean {
        var success = false
        val region = forId(player.location.regionId)
        if (player.skills.prayerPoints < 1) {
            player.packetDispatch.sendMessage("You don't have enough prayer points to do this.")
            return false
        }
        handleVisuals(player)
        sendMessage(player, "You cast the spell in the swamp.")
        val locs = player.location.surroundingTiles
        for (o in locs) {
            val obj = RegionManager.getObject(o)
            if (obj != null) {
                if (obj.name.equals("Rotting log", ignoreCase = true) && player.skills.prayerPoints >= 1) {
                    if (player.location.withinDistance(obj.location, 2)) {
                        SceneryBuilder.replace(obj, obj.transform(3509))
                        success = true
                    }
                }
                if (obj.name.equals("Rotting branch", ignoreCase = true) && player.skills.prayerPoints >= 1) {
                    if (player.location.withinDistance(obj.location, 2)) {
                        SceneryBuilder.replace(obj, obj.transform(3511))
                        success = true
                    }
                }
                if (obj.name.equals("A small bush", ignoreCase = true) && player.skills.prayerPoints >= 1) {
                    if (player.location.withinDistance(obj.location, 2)) {
                        SceneryBuilder.replace(obj, obj.transform(3513))
                        success = true
                    }
                }
            }
        }
        return success
    }

    /**
     * Handles the visual effects and audio for a casting bloom.
     *
     * @param player The player whose visuals are being handled.
     */
    private fun handleVisuals(player: Player) {
        player.skills.decrementPrayerPoints(RandomFunction.random(1, 3).toDouble())
        playAudio(player, Sounds.CAST_BLOOM_1493)
        val aroundPlayer = player.location.surroundingTiles
        for (location in aroundPlayer) {
            player.packetDispatch.sendGlobalPositionGraphic(263, location)
        }
    }
}
