package content.global.skill.magic.lunar

import content.global.skill.cooking.Cookable
import content.global.skill.farming.CompostBins
import content.global.skill.farming.CompostType
import content.global.skill.farming.FarmingPatch
import content.global.skill.magic.spellconsts.Lunar
import core.api.*
import core.game.component.Component
import core.game.node.Node
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.TeleportManager
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.command.Privilege
import core.game.system.config.NPCConfigParser
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.game.world.map.RegionManager
import core.game.world.repository.Repository
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction
import org.rs.consts.*
import kotlin.math.floor

/**
 * Lunar listeners.
 */
class LunarListeners : content.global.skill.magic.SpellListener("lunar"), Commands {

    override fun defineListeners() {
        // Level 0
        onCast(Lunar.HOME_TELEPORT, NONE) { player, _ ->
            requires(player)
            player.teleporter.send(Location.create(2100, 3914, 0), TeleportManager.TeleportType.HOME)
            setDelay(player, true)
        }

        // Level 65
        onCast(Lunar.BAKE_PIE, NONE) { player, _ ->
            requires(player, 65, arrayOf(Item(Items.ASTRAL_RUNE_9075), Item(Items.FIRE_RUNE_554, 5), Item(Items.WATER_RUNE_555, 4)))
            bakePie(player)
        }

        // Level 66
        onCast(Lunar.CURE_PLANT, OBJECT) { player, node ->
            requires(player, 66, arrayOf(Item(Items.ASTRAL_RUNE_9075), Item(Items.EARTH_RUNE_557, 8)))
            curePlant(player, node!!.asScenery())
        }

        // Level 66
        onCast(Lunar.MONSTER_EXAMINE, NPC) { player, node ->
            requires(player, 66, arrayOf(Item(Items.ASTRAL_RUNE_9075), Item(Items.MIND_RUNE_558), Item(Items.COSMIC_RUNE_564)))
            examineMonster(player, node!!.asNpc())
        }

        // Level 67
        onCast(Lunar.NPC_CONTACT, NONE) { player, _ ->
            requires(player, 67, arrayOf(Item(Items.ASTRAL_RUNE_9075), Item(Items.COSMIC_RUNE_564), Item(Items.AIR_RUNE_556, 2)))
            player.interfaceManager.open(Component(429))
            player.setAttribute("contact-caller") {
                removeRunes(player)
                addXP(player, 63.0)
                setDelay(player, false)
                visualizeSpell(player, 4413, 728, 130, 3618)
            }
        }

        // Level 68
        onCast(Lunar.CURE_OTHER, PLAYER) { player, node ->
            node?.let { cureOther(player, node) }
        }

        // Level 68
        onCast(Lunar.HUMIDIFY, NONE) { player, _ ->
            requires(player, 68, arrayOf(Item(Items.ASTRAL_RUNE_9075, 1), Item(Items.WATER_RUNE_555, 3), Item(Items.FIRE_RUNE_554, 1)))
            humidify(player)
        }

        // Level 69
        onCast(Lunar.MOONCLAN_TELEPORT, NONE) { player, _ ->
            requires(player, 69, arrayOf(Item(Items.ASTRAL_RUNE_9075, 2), Item(Items.LAW_RUNE_563, 1), Item(Items.EARTH_RUNE_557, 2)))
            if (!player.isTeleBlocked) playGlobalAudio(player.location, Sounds.TELEPORT_ALL_200)
            sendTeleport(player, 66.0, Location.create(2111, 3916, 0))
        }

        // Level 70
        onCast(Lunar.MOONCLAN_GROUP_TELEPORT, NONE) { player, _ ->
            requires(player, 70, arrayOf(Item(Items.ASTRAL_RUNE_9075, 2), Item(Items.LAW_RUNE_563, 1), Item(Items.EARTH_RUNE_557, 4)))
            if (!player.isTeleBlocked) playGlobalAudio(player.location, Sounds.TELEPORT_ALL_200)
            sendGroupTeleport(player, 67.0, "Moonclan Island", Location.create(2111, 3916, 0))
        }

        // Level 71
        onCast(Lunar.OURANIA_TELEPORT, NONE) { player, _ ->
            requires(player, 71, arrayOf(Item(Items.ASTRAL_RUNE_9075, 2), Item(Items.LAW_RUNE_563, 1), Item(Items.EARTH_RUNE_557, 6)))
            if (!player.isTeleBlocked) playGlobalAudio(player.location, Sounds.TELEPORT_ALL_200)
            sendTeleport(player, 69.0, Location.create(2469, 3247, 0))
        }

        // Level 71
        onCast(Lunar.CURE_ME, NONE) { player, _ ->
            cureMe(player)
        }

        // Level 71
        onCast(Lunar.HUNTER_KIT, NONE) { player, _ ->
            if (freeSlots(player) == 0) sendMessage(player, "Not enough inventory space!").also { return@onCast }
            hunterKit(player)
        }

        // Level 72
        onCast(Lunar.WATERBIRTH_TELEPORT, NONE) { player, _ ->
            requires(player, 72, arrayOf(Item(Items.ASTRAL_RUNE_9075, 2), Item(Items.LAW_RUNE_563), Item(Items.WATER_RUNE_555)))
            if (!player.isTeleBlocked) playGlobalAudio(player.location, Sounds.TELEPORT_ALL_200)
            sendTeleport(player, 71.0, Location.create(2527, 3739, 0))
        }

        // Level 73
        onCast(Lunar.WATERBIRTH_GROUP_TELEPORT, NONE) { player, _ ->
            requires(player, 73, arrayOf(Item(Items.ASTRAL_RUNE_9075, 2), Item(Items.LAW_RUNE_563), Item(Items.WATER_RUNE_555, 5)))
            if (!player.isTeleBlocked) playGlobalAudio(player.location, Sounds.TELEPORT_ALL_200)
            sendGroupTeleport(player, 72.0, "Waterbirth Island", Location.create(2527, 3739, 0))
        }

        // Level 74
        onCast(Lunar.CURE_GROUP, NONE) { player, _ ->
            cureGroup(player)
        }

        // Level 75
        /**
         * Stat Spy
         */

        // Level 75
        onCast(Lunar.BARBARIAN_TELEPORT, NONE) { player, _ ->
            requires(player, 75, arrayOf(Item(Items.ASTRAL_RUNE_9075, 2), Item(Items.LAW_RUNE_563, 2), Item(Items.FIRE_RUNE_554, 3)))
            if (!player.isTeleBlocked) playGlobalAudio(player.location, Sounds.TELEPORT_ALL_200)
            sendTeleport(player, 76.0, Location.create(2544, 3572, 0))
        }

        // Level 76
        onCast(Lunar.BARBARIAN_GROUP_TELEPORT, NONE) { player, _ ->
            requires(player, 77, arrayOf(Item(Items.ASTRAL_RUNE_9075, 2), Item(Items.LAW_RUNE_563, 2), Item(Items.FIRE_RUNE_554, 6)))
            if (!player.isTeleBlocked) playGlobalAudio(player.location, Sounds.TELEPORT_ALL_200)
            sendGroupTeleport(player, 77.0, "Barbarian Outpost", Location.create(2544, 3572, 0))
        }

        // Level 77
        onCast(Lunar.SUPERGLASS_MAKE, NONE) { player, _ ->
            requires(player, 77, arrayOf(Item(Items.ASTRAL_RUNE_9075, 2), Item(Items.FIRE_RUNE_554, 6), Item(Items.AIR_RUNE_556, 10)))
            superglassMake(player)
        }

        // Level 78
        onCast(Lunar.KHAZARD_TELEPORT, NONE) { player, _ ->
            requires(player, 78, arrayOf(Item(Items.ASTRAL_RUNE_9075, 2), Item(Items.LAW_RUNE_563, 2), Item(Items.WATER_RUNE_555, 4)))
            if (!player.isTeleBlocked) playGlobalAudio(player.location, Sounds.TELEPORT_ALL_200)
            sendTeleport(player, 80.0, Location.create(2656, 3157, 0))
        }

        // Level 79
        onCast(Lunar.KHAZARD_GROUP_TELEPORT, NONE) { player, _ ->
            requires(player, 79, arrayOf(Item(Items.ASTRAL_RUNE_9075, 2), Item(Items.LAW_RUNE_563, 2), Item(Items.WATER_RUNE_555, 8)))
            if (!player.isTeleBlocked) playGlobalAudio(player.location, Sounds.TELEPORT_ALL_200)
            sendGroupTeleport(player, 81.0, "Port Khazard", Location.create(2656, 3157, 0))
        }

        // Level 79
        /**
         * Dream
         */

        // Level 80
        onCast(Lunar.STRING_JEWELLERY, NONE) { player, _ ->
            requires(player, 80, arrayOf(Item(Items.ASTRAL_RUNE_9075, 2), Item(Items.EARTH_RUNE_557, 10), Item(Items.WATER_RUNE_555, 5)))
            stringJewellery(player)
        }

        // Level 81
        /**
         * Stat Restore Pot Share
         */

        // Level 82
        /**
         * Magic Imbue
         */

        // Level 83
        onCast(Lunar.FERTILE_SOIL, OBJECT) { player, node ->
            node?.let { fertileSoil(player, node.asScenery()) }
        }

        // Level 84
        /**
         * Boost Potion Share
         */

        // Level 85
        onCast(Lunar.FISHING_GUILD_TELEPORT, NONE) { player, _ ->
            requires(player, 85, arrayOf(Item(Items.ASTRAL_RUNE_9075, 3), Item(Items.LAW_RUNE_563, 3), Item(Items.WATER_RUNE_555, 10)))
            if (!player.isTeleBlocked) playGlobalAudio(player.location, Sounds.TELEPORT_ALL_200)
            sendTeleport(player, 89.0, Location.create(2611, 3393, 0))
        }

        // Level 86
        onCast(Lunar.FISHING_GUILD_GROUP_TELEPORT, NONE) { player, _ ->
            requires(player, 86, arrayOf(Item(Items.ASTRAL_RUNE_9075, 3), Item(Items.LAW_RUNE_563, 3), Item(Items.WATER_RUNE_555, 14)))
            if (!player.isTeleBlocked) playGlobalAudio(player.location, Sounds.TELEPORT_ALL_200)
            sendGroupTeleport(player, 90.0, "Fishing Guild", Location.create(2611, 3393, 0))
        }

        // Level 86
        onCast(Lunar.PLANK_MAKE, ITEM) { player, node ->
            requires(player, 86, arrayOf(Item(Items.ASTRAL_RUNE_9075, 2), Item(Items.NATURE_RUNE_561, 1), Item(Items.EARTH_RUNE_557, 15)))
            plankMake(player, node!!.asItem())
        }

        // Level 87
        onCast(Lunar.CATHERBY_TELEPORT, NONE) { player, _ ->
            requires(player, 87, arrayOf(Item(Items.ASTRAL_RUNE_9075, 3), Item(Items.LAW_RUNE_563, 3), Item(Items.WATER_RUNE_555, 10)))
            if (!player.isTeleBlocked) playGlobalAudio(player.location, Sounds.TELEPORT_ALL_200)
            sendTeleport(player, 92.0, Location.create(2804, 3433, 0))
        }

        // Level 88
        onCast(Lunar.CATHERBY_GROUP_TELEPORT, NONE) { player, _ ->
            requires(player, 88, arrayOf(Item(Items.ASTRAL_RUNE_9075, 3), Item(Items.LAW_RUNE_563, 3), Item(Items.WATER_RUNE_555, 15)))
            if (!player.isTeleBlocked) playGlobalAudio(player.location, Sounds.TELEPORT_ALL_200)
            sendGroupTeleport(player, 93.0, "Catherby", Location.create(2804, 3433, 0))
        }

        // Level 89
        onCast(Lunar.ICE_PLATEAU_TELEPORT, NONE) { player, _ ->
            requires(player, 89, arrayOf(Item(Items.ASTRAL_RUNE_9075, 3), Item(Items.LAW_RUNE_563, 3), Item(Items.WATER_RUNE_555, 8)))
            if (!player.isTeleBlocked) playGlobalAudio(player.location, Sounds.TELEPORT_ALL_200)
            sendTeleport(player, 96.0, Location.create(2972, 3873, 0))
        }

        // Level 90
        onCast(Lunar.ICE_PLATEAU_GROUP_TELEPORT, NONE) { player, _ ->
            requires(player, 90, arrayOf(Item(Items.ASTRAL_RUNE_9075, 3), Item(Items.LAW_RUNE_563, 3), Item(Items.WATER_RUNE_555, 16)))
            if (!player.isTeleBlocked) playGlobalAudio(player.location, Sounds.TELEPORT_ALL_200)
            sendGroupTeleport(player, 99.0, "Ice Plateau", Location.create(2972, 3873, 0))
        }

        // Level 91
        onCast(Lunar.ENERGY_TRANSFER, PLAYER) { player, node ->
            node?.let { energyTransfer(player, node) }
        }

        // Level 92
        /**
         * Heal Other
         */

        // Level 93
        /**
         * Vengeance Other
         */

        // Level 94
        /**
         * Vengeance
         */

        // Level 95
        /**
         * Heal Group
         */

        // Level 96
        /**
         * Spellbook Swap
         */
    }

    override fun defineCommands() {
        define("poison", privilege = Privilege.ADMIN) { player, strings ->
            if (strings.size == 3) {
                var dmg = strings[2].toIntOrNull()
                val p = Repository.getPlayerByName(strings[1])
                if (p == null) {
                    sendMessage(player, "Player ${strings[1]} does not exist.")
                    return@define
                }
                if (dmg != null) {
                    p.let { applyPoison(it, it, dmg) }
                } else {
                    sendMessage(player, "Damage must be an integer. Format:")
                    sendMessage(player, "::poison username damage")
                }
            } else {
                sendMessage(player, "Invalid arguments provided. Format:")
                sendMessage(player, "::poison username damage")
            }
        }
        define("humidifykit", privilege = Privilege.ADMIN) { player, _ ->
            if (freeSlots(player) < 24) {
                sendMessage(player, "Not enough free space.")
                return@define
            } else {
                addItem(player, Items.ASTRAL_RUNE_9075, 100)
                addItem(player, Items.WATER_RUNE_555, 300)
                addItem(player, Items.FIRE_RUNE_554, 100)
                for (item in HumidifyItems.values()) {
                    addItem(player, item.empty)
                }
            }
        }
    }

    /**
     * This function represents the action of making planks.
     *
     * @param player The player performing the action.
     * @param item   The item used for making planks.
     */
    private fun plankMake(player: Player, item: Item) {

        val plankType = PlankType.getForLog(item)
        if (plankType == null) {
            sendMessage(player, "You need to use this spell on logs.")
            return
        }
        if (amountInInventory(player, Items.COINS_995) < plankType.price || !removeItem(
                player,
                Item(Items.COINS_995, plankType.price)
            )
        ) {
            sendMessage(player, "You need ${plankType.price} coins to convert that log into a plank.")
            return
        }
        lock(player, 3)
        setDelay(player, false)
        visualizeSpell(player, 6298, 1063, 120, Sounds.LUNAR_MAKE_PLANK_3617)
        removeRunes(player)
        replaceSlot(player, item.slot, plankType.plank)
        addXP(player, 90.0)
        showMagicTab(player)
    }

    /**
     * Represents the type of plank.
     *
     * @param log   The log item used to create the plank.
     * @param plank The plank item produced.
     * @param price The price of the plank.
     * @constructor Creates a [PlankType] with the specified log, plank, and price.
     */
    enum class PlankType(val log: Item, val plank: Item, val price: Int) {
        WOOD(Item(1511), Item(960), 70),
        OAK(Item(1521), Item(8778), 175),
        TEAK(Item(6333), Item(8780), 350),
        MAHOGANY(Item(6332), Item(8782), 1050);

        companion object {
            fun getForLog(item: Item): PlankType? {
                for (plankType in values()) {
                    if (plankType.log.id == item.id) {
                        return plankType
                    }
                }
                return null
            }
        }
    }

    /**
     * Function to cure a plant.
     *
     * @param player The player performing the action.
     * @param obj    The plant object to be cured.
     */
    fun curePlant(player: Player, obj: Scenery) {
        if (CompostBins.forObject(obj) != null) {
            sendMessage(player, "Bins don't often get diseased.")
            return
        }
        val fPatch = FarmingPatch.forObject(obj)
        if (fPatch == null) {
            sendMessage(player, "Umm... this spell won't cure that!")
            return
        }
        val patch = fPatch.getPatchFor(player)
        if (patch.isWeedy()) {
            sendMessage(player, "The weeds are healthy enough already.")
            return
        }
        if (patch.isEmptyAndWeeded()) {
            sendMessage(player, "There's nothing there to cure.")
            return
        }
        if (patch.isGrown()) {
            sendMessage(player, "That's not diseased.")
            return
        }
        if (patch.isDead) {
            sendMessage(player, "It says 'Cure' not 'Resurrect'. Although death may arise from disease, it is not in itself a disease and hence cannot be cured. So there.")
            return
        }
        if (!patch.isDiseased) {
            sendMessage(player, "It is growing just fine.")
            return
        }

        patch.cureDisease()
        removeRunes(player)
        addXP(player,60.0)
        visualizeSpell(player, Animations.LUNAR_CURE_PLANT_4409, 748, 100, Sounds.LUNAR_CURE_GROUP_2882)
        setDelay(player,false)
    }


    /**
     * Function to examine a monster.
     *
     * @param player The player examining the monster.
     * @param npc The non-player character (monster) being examined.
     */
    private fun examineMonster(player: Player, npc: NPC) {
        if (!npc.location.withinDistance(player.location)) {
            player.sendMessage("You must get closer to use this spell.")
            return
        }

        player.faceLocation(npc.location)
        visualizeSpell(player, 6293, 1060, soundID = Sounds.LUNAR_STAT_SPY_3620)
        removeRunes(player)
        addXP(player, 66.0)
        setDelay(player, false)

        openSingleTab(player, Components.DREAM_MONSTER_STAT_522)
        sendString(player, "Monster name : " + npc.definition.name, Components.DREAM_MONSTER_STAT_522, 0)
        sendString(player, "Combat Level : ${npc.definition.combatLevel}", Components.DREAM_MONSTER_STAT_522, 1)
        sendString(
            player,
            "Hitpoints : ${npc.definition.handlers.get(NPCConfigParser.LIFEPOINTS) ?: 0}",
            Components.DREAM_MONSTER_STAT_522,
            2
        )
        sendString(
            player,
            "Max hit : ${npc.getSwingHandler(false).calculateHit(npc, player, 1.0)}",
            Components.DREAM_MONSTER_STAT_522,
            3
        )

        val poisonStatus = if (npc.definition.handlers.getOrDefault(NPCConfigParser.POISON_IMMUNE, false) == true) {
            "This creature is immune to poison."
        } else "This creature is not immune to poison."

        sendString(player, poisonStatus, Components.DREAM_MONSTER_STAT_522, 4)
    }

    /**
     * Function to bake a pie for a player.
     *
     * @param player The player for whom the pie is baked.
     */
    private fun bakePie(player: Player) {
        val playerPies = ArrayList<Item>()

        for (item in player.inventory.toArray()) {
            if (item == null) continue
            val pie = Cookable.forId(item.id) ?: continue
            if (!pie.name.lowercase().contains("pie")) continue
            if (player.skills.getLevel(Skills.COOKING) < pie.level) continue
            playerPies.add(item)
        }

        if (playerPies.isEmpty()) {
            player.sendMessage("You have no pies which you have the level to cook.")
            return
        }

        player.pulseManager.run(object : Pulse() {
            var counter = 0
            override fun pulse(): Boolean {
                if (playerPies.isEmpty()) return true
                if (counter == 0) delay = Animation(4413).definition.getDurationTicks() + 1
                val item = playerPies[0]
                val pie = Cookable.forId(item.id)
                visualizeSpell(player, 4413, 746, 75, Sounds.LUNAR_BAKE_PIE_2879)
                addXP(player, 60.0)
                player.skills.addExperience(Skills.COOKING, pie!!.experience)
                setDelay(player, false)
                player.inventory.remove(item)
                player.inventory.add(Item(pie.cooked))
                playerPies.remove(item)
                if (playerPies.isNotEmpty()) removeRunes(player, false) else removeRunes(player, true)
                return false
            }
        })
    }

    /**
     * Sends a teleport request to a player.
     *
     * @param player The player to whom the teleport request is sent.
     * @param xp     The amount of experience points associated with the teleport request.
     * @param loc    The destination location to which the player will be teleported.
     */
    private fun sendTeleport(player: Player, xp: Double, loc: Location) {
        if (player.teleporter.send(loc, TeleportManager.TeleportType.LUNAR)) {
            addXP(player, xp)
            removeRunes(player)
            setDelay(player, true)
        }
    }

    /**
     * Sends a group teleport request to a specific destination.
     *
     * @param player   The player initiating the teleport request.
     * @param xp       The experience points required for the teleport.
     * @param destName The name of the destination.
     * @param loc      The location of the destination.
     */
    private fun sendGroupTeleport(player: Player, xp: Double, destName: String, loc: Location) {
        RegionManager.getLocalPlayers(player, 1).forEach {
            if (it == player) return@forEach
            if (it.isTeleBlocked) return@forEach
            if (!it.isActive) return@forEach
            if (!it.settings.isAcceptAid) return@forEach
            if (it.ironmanManager.isIronman) return@forEach
            it.setAttribute("t-o_location", loc)
            it.interfaceManager.open(Component(Components.TELEPORT_OTHER_326))
            it.packetDispatch.sendString(player.username, Components.TELEPORT_OTHER_326, 1)
            it.packetDispatch.sendString(destName, Components.TELEPORT_OTHER_326, 3)
        }
        sendTeleport(player, xp, loc)
    }

    private fun stringJewellery(player: Player) {
        val playerJewellery = ArrayDeque<Item>()

        for (item in player.inventory.toArray()) {
            if (item == null) continue
            if (!StringJewelleryItems.unstrungContains(item.id)) continue
            playerJewellery.add(item)
        }

        player.pulseManager.run(object : Pulse() {
            var counter = 0
            override fun pulse(): Boolean {
                removeAttribute(player, "spell:runes")
                if (playerJewellery.isEmpty())
                    return true
                requires(
                    player,
                    80,
                    arrayOf(
                        Item(Items.ASTRAL_RUNE_9075, 2),
                        Item(Items.EARTH_RUNE_557, 10),
                        Item(Items.WATER_RUNE_555, 5)
                    )
                )
                if (counter == 0) delay = animationDuration(Animation(4412)) + 1
                val item = playerJewellery[0]
                val strung = StringJewelleryItems.forId(item.id)
                setDelay(player, false)
                if (removeItem(player, item) && addItem(player, strung)) {
                    removeRunes(player, true)
                    visualizeSpell(player, 4412, 730, 100, Sounds.LUNAR_STRING_AMULET_2903)
                    rewardXP(player, Skills.CRAFTING, 4.0)
                    addXP(player, 83.0)
                    playerJewellery.remove(item)
                    if (playerJewellery.isNotEmpty()) removeRunes(player, false) else removeRunes(player, true)
                }
                counter++
                return playerJewellery.isEmpty()
            }
        })
    }

    /**
     * This function represents the action of performing the "superglass make" spell.
     * It takes a player as a parameter to execute the spell for that specific player.
     *
     * @param player The player for whom the superglass make spell is being executed.
     */
    private fun superglassMake(player: Player) {
        val GLASS_WEEDS = hashSetOf(Items.SODA_ASH_1781, Items.SEAWEED_401, Items.SWAMP_WEED_10978)
        val inv = player.inventory.toArray()
        var playerWeed: Int = amountInInventory(player, Items.SODA_ASH_1781) + amountInInventory(player, Items.SEAWEED_401) + amountInInventory(player, Items.SWAMP_WEED_10978)
        var playerSand: Int = amountInInventory(player, Items.BUCKET_OF_SAND_1783)
        var index = 0

        fun addMolten(): Boolean {
            if (RandomFunction.randomDouble(1.0) < 0.3) {
                if (addItem(player, Items.MOLTEN_GLASS_1775, 2)) return true
            } else {
                if (addItem(player, Items.MOLTEN_GLASS_1775)) return true
            }
            return false
        }

        val size = minOf(playerSand, playerWeed)

        if (index != size && size != 0) {
            for (item in inv) {
                if (item == null) continue
                if (index == size) break
                if (GLASS_WEEDS.contains(item.id)) {
                    if (removeItem(player, item) && removeItem(player, Items.BUCKET_OF_SAND_1783) && addMolten()) {
                        index++
                    } else {
                        break
                    }
                }
            }
        } else if (playerWeed == 0 || playerSand == 0 || size == 0) {
            sendMessage(player, "You lack the required ingredients.")
        }

        if (index == size && size != 0) {
            removeRunes(player, true)
            visualizeSpell(player, 4413, 729, 120, Sounds.LUNAR_HEATGLASS_2896)
            rewardXP(player, Skills.CRAFTING, 10.0)
            addXP(player, 78.0)
        }
    }

    /**
     * This function represents the action of fertilizing the soil.
     * It takes a player and a target scenery as parameters.
     *
     * @param player The player performing the action.
     * @param target The scenery to be fertilized.
     */
    private fun fertileSoil(player: Player, target: Scenery) {
        if (CompostBins.forObjectID(target.id) != null) {
            sendMessage(player, "No, that would be silly.")
            return
        }

        val fPatch = FarmingPatch.forObject(target)
        if (fPatch == null) {
            sendMessage(player, "Um... I don't want to fertilize that!")
            return
        }

        val patch = fPatch.getPatchFor(player)
        if (patch.isGrown()) {
            sendMessage(player, "Composting isn't going to make it get any bigger.")
            return
        }
        if (patch.isFertilized()) {
            sendMessage(player, "This patch has already been composted.")
            return
        }
        requires(player, 83, arrayOf(Item(Items.ASTRAL_RUNE_9075, 3), Item(Items.NATURE_RUNE_561, 2), Item(Items.EARTH_RUNE_557, 15)))
        removeRunes(player, true)
        animate(player, 4413)
        sendGraphics(724, target.location)
        playGlobalAudio(target.location, Sounds.LUNAR_FERTILIZE_2891)
        patch.compost = CompostType.SUPERCOMPOST
        sendMessage(player, "You fertilize the soil.")
        addXP(player, 87.0)
    }

    /**
     * Function to cure a player.
     *
     * @param player The player to be cured.
     */
    private fun cureMe(player: Player) {
        if (!isPoisoned(player)) {
            sendMessage(player, "You are not poisoned.")
            return
        }
        requires(player, 71, arrayOf(Item(Items.ASTRAL_RUNE_9075, 2), Item(Items.LAW_RUNE_563, 1), Item(Items.COSMIC_RUNE_564, 2)))
        removeRunes(player, true)
        visualizeSpell(player, 4411, 742, 90, Sounds.LUNAR_CURE_2884)
        curePoison(player)
        addXP(player, 69.0)
        playAudio(player, Sounds.LUNAR_CURE_OTHER_INDIVIDUAL_2900)
        sendMessage(player, "You have been cured of poison.")
    }

    /**
     * This function represents a cure action performed on a group of players.
     * It takes a player as a parameter to apply the cure.
     *
     * @param player the player to be cured
     */
    private fun cureGroup(player: Player) {
        requires(player, 74, arrayOf(Item(Items.ASTRAL_RUNE_9075, 2), Item(Items.LAW_RUNE_563, 2), Item(Items.COSMIC_RUNE_564, 2)))
        removeRunes(player, true)
        visualizeSpell(player, 4409, 744, 130, Sounds.LUNAR_CURE_GROUP_2882)
        curePoison(player)
        for (acct in RegionManager.getLocalPlayers(player, 1)) {
            if (!acct.isActive || acct.locks.isInteractionLocked) {
                continue
            }
            if (!acct.settings.isAcceptAid) {
                continue
            }
            curePoison(acct)
            sendMessage(acct, "You have been cured of poison.")
            visualizeSpell(acct, -1, 744, 130, Sounds.LUNAR_CURE_OTHER_INDIVIDUAL_2889)
        }
        addXP(player, 74.0)
    }

    /**
     * Function to cure another player.
     *
     * @param player The player performing the cure.
     * @param target The player being cured.
     */
    private fun cureOther(player: Player, target: Node) {
        if (!isPlayer(target)) {
            sendMessage(player, "You can only cast this spell on other players.")
            return
        }
        val p = target.asPlayer()
        if (!p.isActive || p.locks.isInteractionLocked) {
            sendMessage(player, "This player is busy.")
            return
        }
        if (!p.settings.isAcceptAid) {
            sendMessage(player, "This player is not accepting any aid.")
            return
        }
        if (!isPoisoned(p)) {
            sendMessage(player, "This player is not poisoned.")
            return
        }
        requires(player, 68, arrayOf(Item(Items.ASTRAL_RUNE_9075, 1), Item(Items.LAW_RUNE_563), Item(Items.EARTH_RUNE_557, 10)))
        player.face(p)
        visualizeSpell(player, 4411, 736, 130, Sounds.LUNAR_CURE_OTHER_2886)
        visualizeSpell(p, -1, 736, 130, Sounds.LUNAR_CURE_OTHER_INDIVIDUAL_2889)
        removeRunes(player, true)
        curePoison(p)
        sendMessage(p, "You have been cured of poison.")
        addXP(player, 65.0)
    }

    /**
     * Function to transfer energy from one player to another node.
     *
     * @param player The player initiating the energy transfer.
     * @param target The target node receiving the energy.
     */
    private fun energyTransfer(player: Player, target: Node) {
        if (!isPlayer(target)) {
            sendMessage(player, "You can only cast this spell on other players.")
            return
        }
        val p = target.asPlayer()
        if (!p.isActive || p.locks.isInteractionLocked) {
            sendMessage(player, "This player is busy.")
            return
        }
        if (!p.settings.isAcceptAid) {
            sendMessage(player, "This player is not accepting any aid.")
            return
        }
        if (10 >= player.skills.lifepoints) {
            sendMessage(player, "You need more hitpoints to cast this spell.")
            return
        }
        requires(
            player,
            91,
            arrayOf(Item(Items.ASTRAL_RUNE_9075, 3), Item(Items.LAW_RUNE_563, 2), Item(Items.NATURE_RUNE_561, 1))
        )
        player.face(p)
        visualizeSpell(player, 4411, 738, 90, Sounds.LUNAR_ENERGY_TRANSFER_2885)
        visualize(p, -1, 738)
        val hp = floor(player.skills.lifepoints * 0.10)
        var r = hp
        if (r > (100 - p.settings.runEnergy)) {
            r = (100 - p.settings.runEnergy)
        }
        if (r < 0) {
            r = 0.0
        }
        p.settings.runEnergy += r
        player.settings.runEnergy -= r
        impact(player, hp.toInt(), ImpactHandler.HitsplatType.NORMAL)
        var e = 100
        e -= p.settings.specialEnergy
        if (e < 0) {
            e = 0
        }
        if (e > player.settings.specialEnergy) {
            e = player.settings.specialEnergy
        }
        p.settings.specialEnergy += e
        player.settings.specialEnergy -= e
        removeRunes(player, true)
        addXP(player, 100.0)
    }

    /**
     * Function to set up the hunter's kit for a player.
     *
     * @param player The player for whom the kit is being set up.
     */
    private fun hunterKit(player: Player) {
        requires(player, 71, arrayOf(Item(Items.ASTRAL_RUNE_9075, 2), Item(Items.EARTH_RUNE_557, 2)))
        removeRunes(player, true)
        if (addItem(player, Items.HUNTER_KIT_11159)) {
            visualizeSpell(player, 6303, 1074, soundID = Sounds.LUNAR_HUNTER_KIT_3615)
            addXP(player, 70.0)
            setDelay(player, 2)
        }
    }

    /**
     * Function to humidify the player.
     *
     * @param player The player to be humidified.
     */
    private fun humidify(player: Player) {
        val playerEmpties = ArrayDeque<Item>()

        for (item in player.inventory.toArray()) {
            if (item == null) continue
            if (!HumidifyItems.emptyContains(item.id)) continue
            playerEmpties.add(item)
        }

        if (playerEmpties.isEmpty()) {
            sendMessage(player, "You have nothing in your inventory that this spell can humidify.")
            return
        }

        removeRunes(player)
        delayEntity(player, Animation(6294).duration)
        visualizeSpell(player, 6294, 1061, 20, Sounds.LUNAR_HUMIDIFY_3614)
        for (item in playerEmpties) {
            val filled = HumidifyItems.forId(item.id)
            removeItem(player, item.id) && addItem(player, filled)
        }
        addXP(player, 65.0)
        /*
         *  queueScript(player) {
         *      return@queueScript stopExecuting(player)
         *  }
         */
    }

}
