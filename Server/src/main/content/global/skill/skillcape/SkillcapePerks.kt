package content.global.skill.skillcape

import content.global.skill.production.runecrafting.data.Altar
import core.ServerStore
import core.ServerStore.Companion.getBoolean
import core.ServerStore.Companion.getInt
import cfg.consts.Items
import core.api.getAttribute
import core.api.hasRequirement
import core.api.sendDialogue
import core.api.sendItemDialogue
import core.cache.def.impl.ItemDefinition
import core.game.component.Component
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.SpellBookManager
import core.game.node.entity.player.link.TeleportManager
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.map.zone.impl.DarkZone
import core.plugin.Initializable

/**
 * Skillcape perks
 *
 * @param attribute Represents the specific attribute associated with the skillcape.
 * @param effect A lambda function that defines the effect of the skillcape on a Player.
 * @constructor Skillcape perks Represents a SkillcapePerks enum with the given attribute and effect.
 */
enum class SkillcapePerks(val attribute: String, val effect: ((Player) -> Unit)? = null) {
    /**
     * Barefisted Smithing.
     */
    BAREFISTED_SMITHING("cape_perks:barefisted-smithing"),

    /**
     * Divine Favor.
     */
    DIVINE_FAVOR("cape_perks:divine-favor"),

    /**
     * Constant Glow.
     */
    CONSTANT_GLOW("cape_perks:eternal-glow"),

    /**
     * Precision Miner.
     */
    PRECISION_MINER("cape_perks:precision-miner"),

    /**
     * Great Aim.
     */
    GREAT_AIM("cape_perks:great-aim"),

    /**
     * Nest Hunter.
     */
    NEST_HUNTER("cape_perks:nest-hunter"),

    /**
     * Precision Strikes.
     */
    PRECISION_STRIKES("cape_perks:precision-strikes"),

    /**
     * Fine Attunement.
     */
    FINE_ATTUNEMENT("cape_perks:fine-attunement"),

    /**
     * Grand Bullwark.
     */
    GRAND_BULLWARK("cape_perks:grand-bullwark"),

    /**
     * Accurate Marksman.
     */
    ACCURATE_MARKSMAN("cape_perks:accurate-marksman"),

    /**
     * Damage Spong.
     */
    DAMAGE_SPONG("cape_perks:damage-sponge"),

    /**
     * Marathon Runner.
     */
    MARATHON_RUNNER("cape_perks:marathon-runner"),

    /**
     * Librarian Magus.
     */
    LIBRARIAN_MAGUS("cape_perks:librarian-magus", { player ->
        val store = ServerStore.getArchive("daily-librarian-magus")
        val used = store.getInt(player.name, 0)
        if (used >= 3) {
            player.dialogueInterpreter.sendDialogue("Your cape is still on cooldown.")
        } else {
            player.dialogueInterpreter.open(509871234)
            store[player.name] = used + 1
        }
    }),

    /**
     * Abyss Warping.
     */
    ABYSS_WARPING("cape_perks:abyss_warp", { player ->
        val store = ServerStore.getArchive("daily-abyss-warp")
        val used = store.getInt(player.name, 0)
        if (used >= 3) {
            player.dialogueInterpreter.sendDialogue("Your cape is still on cooldown.")
        } else {
            player.dialogueInterpreter.open(509871233)
            store[player.name] = used + 1
        }
    }),

    /**
     * Seed Attraction.
     */
    SEED_ATTRACTION("cape_perks:seed_attract", { player ->
        val store = ServerStore.getArchive("daily-seed-attract")
        if (store.getBoolean(player.name)) {
            player.dialogueInterpreter.sendDialogue("Your cape is still on cooldown.")
        } else {
            val possibleSeeds = content.global.skill.gathering.farming.Plantable.values()
            for (i in 0 until 10) {
                var seed = possibleSeeds.random()
                while (seed == content.global.skill.gathering.farming.Plantable.SCARECROW || seed.applicablePatch == content.global.skill.gathering.farming.PatchType.FRUIT_TREE_PATCH || seed.applicablePatch == content.global.skill.gathering.farming.PatchType.TREE_PATCH || seed.applicablePatch == content.global.skill.gathering.farming.PatchType.SPIRIT_TREE_PATCH) {
                    seed = possibleSeeds.random()
                }
                val reward = core.game.node.item.Item(seed.itemID)
                if (!player.inventory.add(reward)) {
                    core.game.node.item.GroundItemManager.create(reward, player)
                }
            }
            player.dialogueInterpreter.sendDialogue("You pluck off the seeds that were stuck to your cape.")
            store[player.name] = true
        }
    }),

    /**
     * Tricks Of The Trade.
     */
    TRICKS_OF_THE_TRADE("cape_perks:tott", { player ->
        val hasHelmetBonus = getAttribute(player, "cape_perks:tott:helmet-stored", false)
        if (hasHelmetBonus) {
            sendDialogue(player, "Your cape's pockets are lined with all the utilities you need for slayer.")
        } else {
            sendDialogue(
                player, "Your cape is lined with empty pockets shaped like various utilities needed for slayer."
            )
        }
    }),

    /**
     * Hasty Cooking.
     */
    HASTY_COOKING("cape_perks:hasty-cooking"),

    /**
     * Smooth Hands.
     */
    SMOOTH_HANDS("cape_perks:smooth-hands"),

    /**
     * Pet Mastery.
     */
    PET_MASTERY("cape_perks:pet-mastery"),

    /**
     * Brewmaster.
     */
    BREWMASTER(
        "cape_perks:brewmaster"
    ),

    /**
     * None.
     */
    NONE("cape_perks:none");

    companion object {
        @JvmStatic
        fun isActive(perk: SkillcapePerks, player: Player): Boolean {
            return player.getAttribute(perk.attribute, false)
        }

        fun forSkillcape(skillcape: Skillcape): SkillcapePerks {
            return when (skillcape) {
                Skillcape.ATTACK -> PRECISION_STRIKES
                Skillcape.STRENGTH -> FINE_ATTUNEMENT
                Skillcape.DEFENCE -> GRAND_BULLWARK
                Skillcape.RANGING -> ACCURATE_MARKSMAN
                Skillcape.PRAYER -> DIVINE_FAVOR
                Skillcape.MAGIC -> LIBRARIAN_MAGUS
                Skillcape.RUNECRAFTING -> ABYSS_WARPING
                Skillcape.HITPOINTS -> DAMAGE_SPONG
                Skillcape.AGILITY -> MARATHON_RUNNER
                Skillcape.HERBLORE -> BREWMASTER
                Skillcape.THIEVING -> SMOOTH_HANDS
                Skillcape.CRAFTING -> NONE
                Skillcape.FLETCHING -> NONE
                Skillcape.SLAYER -> TRICKS_OF_THE_TRADE
                Skillcape.CONSTRUCTION -> NONE
                Skillcape.MINING -> PRECISION_MINER
                Skillcape.SMITHING -> BAREFISTED_SMITHING
                Skillcape.FISHING -> GREAT_AIM
                Skillcape.COOKING -> HASTY_COOKING
                Skillcape.FIREMAKING -> CONSTANT_GLOW
                Skillcape.WOODCUTTING -> NEST_HUNTER
                Skillcape.FARMING -> SEED_ATTRACTION
                Skillcape.HUNTING -> NONE
                Skillcape.SUMMONING -> PET_MASTERY
                else -> NONE
            }
        }
    }

    /**
     * Activate
     *
     * @param player The player who is activating the skillcape.
     */
    fun activate(player: Player) {
        // Check if skillcape perks are enabled in the game settings
        if (GameWorld.settings?.skillcape_perks != true) {
            return // Exit the function if perks are not enabled
        }
        // Check if the skillcape is not already active for the player
        if (!isActive(this, player)) {
            // Set the attribute to indicate the skillcape is active
            player.setAttribute("/save:$attribute", true)
        }
        // Log the activation of the skillcape for debugging purposes
        player.debug("Activated ${this.name}")
        // If the skillcape is the constant glow, check if the player is in a dark area
        if (this == CONSTANT_GLOW) DarkZone.checkDarkArea(player)
    }

    /**
     * Operate
     *
     * @param player The player who is operating the skillcape.
     */
    fun operate(player: Player) {
        // Check if skillcape perks are enabled in the game settings
        if (GameWorld.settings?.skillcape_perks != true) {
            // Inform the player that the item cannot be operated
            player.sendMessage("This item can not be operated.")
            return // Exit the function if perks are not enabled
        }
        // Invoke the effect associated with the skillcape on the player
        effect?.invoke(player)
    }

    /**
     * Deactivate
     *
     * @param player The player who is deactivating the skillcape.
     */
    fun deactivate(player: Player) {
        // Remove the active attribute of the skillcape from the player
        player.removeAttribute(attribute)
        // If the skillcape is the constant glow, check if the player is in a dark area
        if (this == CONSTANT_GLOW) DarkZone.checkDarkArea(player)
    }

    /**
     * Magic cape dialogue
     *
        *
     * @param player The player associated with the magic cape dialogue.
     */
    @Initializable
    class MagicCapeDialogue(player: Player? = null) : Dialogue(player) {

        override fun newInstance(player: Player?): Dialogue {
            return MagicCapeDialogue(player)
        }

        override fun open(vararg args: Any?): Boolean {
            when (player.spellBookManager.spellBook) {
                SpellBookManager.SpellBook.ANCIENT.interfaceId -> options("Modern", "Lunar")
                SpellBookManager.SpellBook.MODERN.interfaceId -> options("Ancient", "Lunar")
                SpellBookManager.SpellBook.LUNAR.interfaceId -> options("Modern", "Ancient")
            }
            return true
        }

        override fun handle(interfaceId: Int, buttonId: Int): Boolean {
            val spellbook = when (player.spellBookManager.spellBook) {
                SpellBookManager.SpellBook.ANCIENT.interfaceId -> {
                    when (buttonId) {
                        1 -> SpellBookManager.SpellBook.MODERN
                        2 -> SpellBookManager.SpellBook.LUNAR
                        else -> null
                    }
                }

                SpellBookManager.SpellBook.MODERN.interfaceId -> {
                    when (buttonId) {
                        1 -> SpellBookManager.SpellBook.ANCIENT
                        2 -> SpellBookManager.SpellBook.LUNAR
                        else -> null
                    }
                }

                SpellBookManager.SpellBook.LUNAR.interfaceId -> {
                    when (buttonId) {
                        1 -> SpellBookManager.SpellBook.MODERN
                        2 -> SpellBookManager.SpellBook.ANCIENT
                        else -> null
                    }
                }

                else -> null
            }

            end()
            if (spellbook != null) {
                if (spellbook == SpellBookManager.SpellBook.ANCIENT) {
                    if (!hasRequirement(player, "Desert Treasure")) return true
                } else if (spellbook == SpellBookManager.SpellBook.LUNAR) {
                    if (!hasRequirement(player, "Lunar Diplomacy")) return true
                }
                player.spellBookManager.setSpellBook(spellbook)
                player.interfaceManager.openTab(Component(spellbook.interfaceId))
                player.incrementAttribute("/save:cape_perks:librarian-magus-charges", -1)
            }
            return true
        }

        override fun getIds(): IntArray {
            return intArrayOf(509871234)
        }

    }

    /**
     * RC cape dialogue.
     */
    @Initializable
    class RCCapeDialogue(player: Player? = null) : Dialogue(player) {

        override fun newInstance(player: Player?): Dialogue {
            return RCCapeDialogue(player)
        }

        override fun open(vararg args: Any?): Boolean {
            options("Air", "Mind", "Water", "Earth", "More...")
            stage = 0
            return true
        }

        override fun handle(interfaceId: Int, buttonId: Int): Boolean {
            when (stage) {
                0 -> when (buttonId) {
                    1 -> sendAltar(player, Altar.AIR)
                    2 -> sendAltar(player, Altar.MIND)
                    3 -> sendAltar(player, Altar.WATER)
                    4 -> sendAltar(player, Altar.EARTH)
                    5 -> options("Fire", "Body", "Cosmic", "Chaos", "More...").also { stage++ }
                }

                1 -> when (buttonId) {
                    1 -> sendAltar(player, Altar.FIRE)
                    2 -> sendAltar(player, Altar.BODY)
                    3 -> sendAltar(player, Altar.COSMIC)
                    4 -> sendAltar(player, Altar.CHAOS)
                    5 -> options("Astral", "Nature", "Law", "Death", "More...").also { stage++ }
                }

                2 -> when (buttonId) {
                    1 -> sendAltar(player, Altar.ASTRAL)
                    2 -> sendAltar(player, Altar.NATURE)
                    3 -> sendAltar(player, Altar.LAW)
                    4 -> sendAltar(player, Altar.DEATH)
                    5 -> options("Blood", "Never mind.").also { stage++ }
                }

                3 -> when (buttonId) {
                    1 -> sendAltar(player, Altar.BLOOD)
                    2 -> end()
                }
            }
            return true
        }

        /**
         * Send altar
         *
         * @param player The player who will receive the altar.
         * @param altar The altar object that is being sent to the player.
         */
        fun sendAltar(player: Player, altar: Altar) {
            end()
            if (altar == Altar.DEATH && !hasRequirement(player, "Mourning's End Part II")) return
            if (altar == Altar.ASTRAL && !hasRequirement(player, "Lunar Diplomacy")) return
            if (altar == Altar.BLOOD && !hasRequirement(player, "Legacy of Seergaze")) return
            if (altar == Altar.LAW && !ItemDefinition.canEnterEntrana(player)) {
                sendItemDialogue(
                    player, Items.SARADOMIN_SYMBOL_8055, "No weapons or armour are permitted on holy Entrana."
                )
                return
            }

            var endLoc = if (altar == Altar.ASTRAL) Location.create(2151, 3864, 0) else altar.ruin!!.end

            player.teleporter.send(endLoc, TeleportManager.TeleportType.TELE_OTHER)
            player.incrementAttribute("/save:cape_perks:abyssal_warp", -1)
        }

        override fun getIds(): IntArray {
            return intArrayOf(509871233)
        }

    }
}
