package content.minigame.mta

import content.minigame.mta.impl.AlchemistZone
import content.minigame.mta.impl.EnchantingZone
import content.minigame.mta.impl.GraveyardZone
import content.minigame.mta.impl.TelekineticZone
import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.Scenery
import core.game.component.Component
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Location

/**
 * MTA type
 *
 * @property sceneryId
 * @property overlay
 * @property startLocation
 * @property endLocation
 * @property mtaZone
 */
enum class MTAType(
    val sceneryId: Int,
    val overlay: Component,
    private val startLocation: Location?,
    private val endLocation: Location,
    val mtaZone: MTAZone
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
     * Exit.
     */
    open fun exit(player: Player) {
        teleport(player, endLocation)
    }

    /**
     * Has requirement.
     */
    open fun hasRequirement(player: Player): Boolean {
        return false
    }

    /**
     * Get zone.
     */
    fun getZone(): MTAZone {
        return mtaZone
    }

    companion object {
        @JvmStatic
        fun forZone(mtaZone: MTAZone): MTAType {
            for (type in values()) {
                if (type == null) {
                    continue
                }
                if (type.getZone() === mtaZone) {
                    return type
                }
            }
            return TELEKINETIC
        }
        @JvmStatic
        fun forId(id: Int): MTAType? {
            for (t in values()) {
                if (t.sceneryId == id) {
                    return t
                }
            }
            return null
        }
    }
}