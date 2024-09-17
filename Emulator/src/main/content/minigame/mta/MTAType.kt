package content.minigame.mta

import content.minigame.mta.impl.AlchemistZone
import content.minigame.mta.impl.EnchantingZone
import content.minigame.mta.impl.GraveyardZone
import content.minigame.mta.impl.TelekineticZone
import core.api.*
import org.rs.consts.Components
import org.rs.consts.Items
import org.rs.consts.Scenery
import core.game.component.Component
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Location

/**
 * MTA type
 *
 * @param sceneryId Unique identifier for the scenery.
 * @param overlay The visual component associated with the MTA type.
 * @param startLocation The starting location for the MTA route, can be null.
 * @param endLocation The ending location for the MTA route, must not be null.
 * @param mtaZone The zone in which the MTA operates.
 */
enum class MTAType(
    val sceneryId: Int, // Unique identifier for the scenery associated with this MTA type.
    val overlay: Component, // The visual representation of the MTA type.
    private val startLocation: Location?, // Optional starting location for the MTA route.
    private val endLocation: Location, // Mandatory ending location for the MTA route.
    val mtaZone: MTAZone // The operational zone of the MTA.
) {
    /**
     * Telekinetic.
     */
    TELEKINETIC(Scenery.TELEKINETIC_TELEPORT_10778, Component(Components.MAGICTRAINING_TELE_198), null, Location.create(3363, 3316, 0), TelekineticZone()) {
        override fun hasRequirement(player: Player): Boolean {
            if (getStatLevel(player, Skills.MAGIC) < 33) {
                sendDialogueLines(player, "You need to be able to cast the Telekinetic Grab spell in order to", "enter.")
                return false
            }
            return true
        }
    },

    /**
     * Alchemists.
     */
    ALCHEMISTS(10780, Component(Components.MAGICTRAINING_ALCH_STATS_194), Location(3366, 9623, 2), Location(3363, 3320, 0), AlchemistZone.ZONE) {
        override fun hasRequirement(player: Player): Boolean {
            if (getStatLevel(player, Skills.MAGIC) < 21) {
                sendDialogueLines(player, "You need to be able to cast the Low Alchemy spell in order to", "enter.")
                return false
            }
            if (amountInInventory(player, Items.COINS_995) > 0) {
                sendDialogue(player, "You cannot take money into the Alchemists' Playground.")
                return false
            }
            return true
        }

        override fun exit(player: Player) {
            val earn = player.getAttribute("alch-earn", 0)
            if (earn != 0) {
                val coins = Item(995, earn)
                if (player.bank.hasSpaceFor(coins)) {
                    player.bank.add(coins)
                }
                sendDialogue(player,"You've been awarded $earn coins straight into your bank as a reward!")
            }
            super.exit(player)
        }
    },

    /**
     * Enchanters.
     */
    ENCHANTERS(Scenery.ENCHANTERS_TELEPORT_10779, Component(Components.MAGICTRAINING_ENCHANT_195), Location(3363, 9649, 0), Location(3361, 3318, 0), EnchantingZone.ZONE) {
        override fun hasRequirement(player: Player): Boolean {
            if (getStatLevel(player, Skills.MAGIC) < 7) {
                sendDialogueLines(player, "You need to be able to cast the Lvl-1 Enchant spell in order to", "enter.")
                return false
            }
            return true
        }
    },

    /**
     * Graveyard.
     */
    GRAVEYARD(Scenery.GRAVEYARD_TELEPORT_10781, Component(Components.MAGICTRAINING_GRAVE_196), Location(3363, 9639, 1), Location(3365, 3318, 0), GraveyardZone.ZONE) {
        override fun hasRequirement(player: Player): Boolean {
            if (getStatLevel(player, Skills.MAGIC) < 15) {
                sendDialogueLines(player, "You need to be able to cast the Bones to Bananas spell in order to", "enter.")
                return false
            }
            if (inInventory(player, Items.BANANA_1963, 1) || inInventory(player, Items.PEACH_6883, 1)) {
                sendDialogue(player,"You can't take bananas or peaches into the arena.")
                return false
            }
            return true
        }
    };

    /**
     * Enter.
     */
    fun enter(player: Player) {
        if (!player.getSavedData().activityData.isStartedMta || !player.inventory.containsItem(MageTrainingArena.PROGRESS_HAT) && !player.equipment.containsItem(MageTrainingArena.PROGRESS_HAT)) {
            sendDialogueLines(player, "You need a Pizazz Progress Hat in order to enter. Talk to the", "Entrance Guardian if you don't have one.")
            return
        }
        if (!hasRequirement(player)) {
            return
        }
        if (this !== TELEKINETIC) {
            teleport(player, startLocation!!)
        } else {
            TelekineticZone.start(player)
        }
        sendMessage(player, "You've entered the " + mtaZone.name + ".")
    }

    /**
     * Exit the game or current activity.
     */
    open fun exit(player: Player) {
        // Teleport the player to the designated end location
        teleport(player, endLocation)
    }

    /**
     * Check if the player meets the requirements.
     */
    open fun hasRequirement(player: Player): Boolean {
        // Currently, this function always returns false, indicating no requirements are met
        return false
    }

    /**
     * Retrieve the current zone.
     */
    fun getZone(): MTAZone {
        // Return the MTAZone associated with this instance
        return mtaZone
    }

    companion object {
        @JvmStatic
        fun forZone(mtaZone: MTAZone): MTAType {
            // Iterate through all MTAType values
            for (type in values()) {
                // Skip null types
                if (type == null) {
                    continue
                }
                // Check if the current type's zone matches the provided zone
                if (type.getZone() === mtaZone) {
                    // Return the matching MTAType
                    return type
                }
            }
            // Default return value if no match is found
            return TELEKINETIC
        }

        @JvmStatic
        fun forId(id: Int): MTAType? {
            // Iterate through all MTAType values
            for (t in values()) {
                // Check if the sceneryId matches the provided id
                if (t.sceneryId == id) {
                    // Return the matching MTAType
                    return t
                }
            }
            // Return null if no matching MTAType is found
            return null
        }
    }
}