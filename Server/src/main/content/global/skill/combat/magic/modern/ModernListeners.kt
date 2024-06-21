package content.global.skill.combat.magic.modern

import content.global.skill.combat.magic.SpellListener
import content.global.skill.combat.magic.TeleportMethod
import content.global.skill.combat.magic.spellconsts.Modern
import content.global.skill.combat.prayer.Bones
import content.global.skill.production.smithing.data.Bar
import content.global.skill.production.smithing.item.SmeltingPulse
import content.minigame.mta.impl.GraveyardZone
import core.ServerConstants
import core.api.*
import core.api.consts.Animations
import core.api.consts.Graphics
import core.api.consts.Items
import core.api.consts.Sounds
import core.game.event.ItemAlchemizationEvent
import core.game.event.TeleportEvent
import core.game.interaction.MovementPulse
import core.game.node.entity.Entity
import core.game.node.entity.impl.Animator
import core.game.node.entity.impl.Projectile
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.TeleportManager
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.network.packet.PacketRepository
import core.network.packet.context.MinimapStateContext
import core.network.packet.outgoing.MinimapState

class ModernListeners : SpellListener("modern") {
    private val CONFUSE_START = Graphic(102, 96)
    private val CONFUSE_PROJECTILE = Projectile.create(null as Entity?, null, 103, 40, 36, 52, 75, 15, 11)
    private val CONFUSE_END = Graphic(104, 96)
    private val WEAKEN_START = Graphic(105, 96)
    private val WEAKEN_PROJECTILE = Projectile.create(null as Entity?, null, 106, 40, 36, 52, 75, 15, 11)
    private val WEAKEN_END = Graphic(107, 96)
    private val CURSE_START = Graphic(108, 96)
    private val CURSE_PROJECTILE = Projectile.create(null as Entity?, null, 109, 40, 36, 52, 75, 15, 11)
    private val CURSE_END = Graphic(110, 96)
    private val VULNER_START = Graphic(167, 96)
    private val VULNER_PROJECTILE = Projectile.create(null as Entity?, null, 168, 40, 36, 52, 75, 15, 11)
    private val VULNER_END = Graphic(169, 96)
    private val ENFEEBLE_START = Graphic(170, 96)
    private val ENFEEBLE_PROJECTILE = Projectile.create(null as Entity?, null, 171, 40, 36, 52, 75, 15, 11)
    private val ENFEEBLE_END = Graphic(172, 96)
    private val STUN_START = Graphic(173, 96)
    private val STUN_PROJECTILE = Projectile.create(null as Entity?, null, 174, 40, 36, 52, 75, 15, 11)
    private val STUN_END = Graphic(107, 96)
    private val LOW_ANIMATION = Animation(716, Animator.Priority.HIGH)
    private val HIGH_ANIMATION = Animation(729, Animator.Priority.HIGH)
    private val LOW_ALCH_STAFF_ANIM = Animations.HUMAN_CAST_LOW_ALCH_SPELL_WITH_STAFF_9625
    private val LOW_ALCH_STAFF_GFX = Graphics.LOW_ALCH_WITH_STAFF_1692
    private val LOW_ALCH_ANIM = Animation(Animations.HUMAN_CAST_LOW_ALCHEMY_SPELL_712)
    private val LOW_ALCH_GFX = Graphic(Graphics.LOW_ALCH_112, 5)
    private val HIGH_ALCH_STAFF_ANIM = Animations.HUMAN_CAST_HIGH_ALCH_SPELL_WITH_STAFF_9633
    private val HIGH_ALCH_STAFF_GFX = Graphics.HIGH_ALCH_WITH_STAFF_1693
    private val HIGH_ALCH_ANIM = Animation(Animations.HUMAN_CAST_HIGH_ALCHEMY_SPELL_713)
    private val HIGH_ALCH_GFX = Graphic(Graphics.HIGH_ALCH_113, 5)
    private val BONE_CONVERT_GFX = Graphic(141, 96)
    private val BONE_CONVERT_ANIM = Animation(722)


    override fun defineListeners() {

        onCast(Modern.HOME_TELEPORT, NONE) { player, _ ->
            if (!getAttribute(player, "tutorial:complete", false)) {
                return@onCast
            }
            requires(player)
            player.teleporter.send(ServerConstants.HOME_LOCATION, TeleportManager.TeleportType.HOME)
            setDelay(player, true)
        }

        onCast(Modern.VARROCK_TELEPORT, NONE) { player, _ ->
            requires(
                player,
                25,
                arrayOf(Item(Items.FIRE_RUNE_554), Item(Items.AIR_RUNE_556, 3), Item(Items.LAW_RUNE_563))
            )
            val alternateTeleport = getAttribute(player, "diaries:varrock:alttele", false)
            val dest = if (alternateTeleport) Location.create(3165, 3472, 0) else Location.create(3213, 3424, 0)
            sendTeleport(player, 35.0, dest)
        }

        onCast(Modern.LUMBRIDGE_TELEPORT, NONE) { player, _ ->
            requires(
                player,
                31,
                arrayOf(Item(Items.EARTH_RUNE_557), Item(Items.AIR_RUNE_556, 3), Item(Items.LAW_RUNE_563))
            )
            sendTeleport(player, 41.0, Location.create(3221, 3219, 0))
        }

        onCast(Modern.FALADOR_TELEPORT, NONE) { player, _ ->
            requires(
                player,
                37,
                arrayOf(Item(Items.WATER_RUNE_555), Item(Items.AIR_RUNE_556, 3), Item(Items.LAW_RUNE_563))
            )
            sendTeleport(player, 47.0, Location.create(2965, 3378, 0))
        }

        onCast(Modern.CAMELOT_TELEPORT, NONE) { player, _ ->
            requires(player, 45, arrayOf(Item(Items.AIR_RUNE_556, 5), Item(Items.LAW_RUNE_563)))
            sendTeleport(player, 55.5, Location.create(2758, 3478, 0))
        }

        onCast(Modern.ARDOUGNE_TELEPORT, NONE) { player, _ ->
            if (!hasRequirement(player, "Plague City")) return@onCast
            requires(player, 51, arrayOf(Item(Items.WATER_RUNE_555, 2), Item(Items.LAW_RUNE_563, 2)))
            sendTeleport(player, 61.0, Location.create(2662, 3307, 0))
        }

        onCast(Modern.WATCHTOWER_TELEPORT, NONE) { player, _ ->
            if (!hasRequirement(player, "Watchtower")) return@onCast
            requires(player, 58, arrayOf(Item(Items.EARTH_RUNE_557, 2), Item(Items.LAW_RUNE_563, 2)))
            sendTeleport(player, 68.0, Location.create(2549, 3112, 0))
        }

        onCast(Modern.TROLLHEIM_TELEPORT, NONE) { player, _ ->
            if (!hasRequirement(player, "Eadgar's Ruse")) return@onCast
            requires(player, 61, arrayOf(Item(Items.FIRE_RUNE_554, 2), Item(Items.LAW_RUNE_563, 2)))
            sendTeleport(player, 68.0, Location.create(2891, 3678, 0))
        }

        onCast(Modern.APE_ATOLL_TELEPORT, NONE) { player, _ ->
            if (!hasRequirement(player, "Monkey Madness")) return@onCast
            requires(
                player,
                64,
                arrayOf(
                    Item(Items.FIRE_RUNE_554, 2),
                    Item(Items.WATER_RUNE_555, 2),
                    Item(Items.LAW_RUNE_563, 2),
                    Item(Items.BANANA_1963)
                )
            )
            sendTeleport(player, 74.0, Location.create(2754, 2784, 0))
        }

        onCast(Modern.TELEPORT_TO_HOUSE, NONE) { player, _ ->
            requires(
                player,
                40,
                arrayOf(Item(Items.LAW_RUNE_563), Item(Items.AIR_RUNE_556), Item(Items.EARTH_RUNE_557))
            )
            attemptHouseTeleport(player)
        }

        onCast(Modern.LOW_ALCHEMY, ITEM) { player, node ->
            val item = node?.asItem() ?: return@onCast
            requires(player, 21, arrayOf(Item(Items.FIRE_RUNE_554, 3), Item(Items.NATURE_RUNE_561)))
            alchemize(player, item, high = false)
        }

        onCast(Modern.HIGH_ALCHEMY, ITEM) { player, node ->
            val item = node?.asItem() ?: return@onCast
            requires(player, 55, arrayOf(Item(Items.FIRE_RUNE_554, 5), Item(Items.NATURE_RUNE_561, 1)))
            alchemize(player, item, high = true)
        }

        onCast(Modern.SUPERHEAT, ITEM) { player, node ->
            val item = node?.asItem() ?: return@onCast
            requires(player, 43, arrayOf(Item(Items.FIRE_RUNE_554, 4), Item(Items.NATURE_RUNE_561, 1)))
            superheat(player, item)
        }

        onCast(Modern.BONES_TO_BANANAS, NONE) { player, _ ->
            requires(
                player,
                15,
                arrayOf(Item(Items.EARTH_RUNE_557, 2), Item(Items.WATER_RUNE_555, 2), Item(Items.NATURE_RUNE_561, 1))
            )
            boneConvert(player, true)
        }

        onCast(Modern.BONES_TO_PEACHES, NONE) { player, _ ->
            requires(
                player,
                60,
                arrayOf(Item(Items.EARTH_RUNE_557, 4), Item(Items.WATER_RUNE_555, 4), Item(Items.NATURE_RUNE_561, 2))
            )
            boneConvert(player, false)
        }
    }

    private fun boneConvert(player: Player, bananas: Boolean) {
        val isInMTA = player.zoneMonitor.isInZone("Creature Graveyard")
        if (isInMTA && player.getAttribute("tablet-spell", false)) {
            sendMessage(player, "You can not use this tablet in the Mage Training Arena.")
            return
        }

        if (!bananas && !player.savedData.activityData.isBonesToPeaches && !player.getAttribute(
                "tablet-spell",
                false
            )
        ) {
            sendMessage(player, "You can only learn this spell from the Mage Training Arena.")
            return
        }

        val bones = if (isInMTA) intArrayOf(6904, 6905, 6906, 6907) else Bones.values().map { it.itemId }.toIntArray()

        for (item in player.inventory.toArray()) {
            item ?: continue
            if (isInMTA) {
                if (bones.contains(item.id)) {
                    val inInventory = player.inventory.getAmount(item.id)
                    val amount =
                        inInventory * (GraveyardZone.BoneType.forItem(Item(item.id))!!.ordinal + 1)
                    if (amount > 0) {
                        player.inventory.remove(Item(item.id, inInventory))
                        player.inventory.add(Item(if (bananas) Items.BANANA_1963 else Items.PEACH_6883, amount))
                    }
                }
            } else {
                if (bones.contains(item.id)) {
                    val inInventory = player.inventory.getAmount(item.id)
                    player.inventory.remove(Item(item.id, inInventory))
                    player.inventory.add(Item(if (bananas) Items.BANANA_1963 else Items.PEACH_6883, inInventory))
                }
            }
        }
        visualizeSpell(player, BONE_CONVERT_ANIM, BONE_CONVERT_GFX)
        playAudio(player, Sounds.BONES_TO_BANANAS_ALL_114)
        removeRunes(player)
        addXP(player, if (bananas) 25.0 else 65.0)
        setDelay(player, false)
    }

    private fun superheat(player: Player, item: Item) {
        if (!item.name.contains("ore") && !item.name.lowercase().equals("coal")) {
            sendMessage(player, "You can only cast this spell on ore.")
            return
        }

        // Elemental Workshop I special interaction
        if (item.id == Items.ELEMENTAL_ORE_2892) {
            sendMessage(player, "Even this spell is not hot enough to heat this item.")
            return
        }

        var bar = Bar.forOre(item.id) ?: return
        if (bar == Bar.IRON && player.inventory.getAmount(Items.COAL_453) >= 2 && player.skills.getLevel(Skills.SMITHING) >= Bar.STEEL.level && inInventory(
                player,
                Items.IRON_ORE_441,
                1
            )
        ) bar = Bar.STEEL
        if (player.skills.getLevel(Skills.SMITHING) < bar.level) {
            sendMessage(player, "You need a smithing level of ${bar.level} to superheat that ore.")
            return
        }

        for (items in bar.ores) {
            if (!inInventory(player, items.id, items.amount)) {
                player.packetDispatch.sendMessage("You do not have the required ores to make this bar.")
                return
            }
        }

        player.lock(3)
        removeRunes(player)
        addXP(player, 53.0)
        playAudio(player, Sounds.SUPERHEAT_ALL_190)
        showMagicTab(player)
        player.pulseManager.run(SmeltingPulse(player, item, bar, 1, true))
        setDelay(player, false)
    }

    fun alchemize(player: Player, item: Item, high: Boolean): Boolean {
        if (item.name == "Coins") sendMessage(player, "You can't alchemize something that's already gold!").also { return false }
        if ((!item.definition.isTradeable) && (!item.definition.isAlchemizable)) sendMessage(player, "You can't cast this spell on something like that.").also { return false }

        if (player.zoneMonitor.isInZone("Alchemists' Playground")) {
            sendMessage(player, "You can only alch items from the cupboards!")
            return false
        }

        val coins = Item(995, item.definition.getAlchemyValue(high))
        if (coins.amount > 0 && !player.inventory.hasSpaceFor(coins)) {
            sendMessage(player, "Not enough space in your inventory!")
            return false
        }

        if (player.pulseManager.current !is MovementPulse) {
            player.pulseManager.clear()
        }

        val staves = intArrayOf(
            Items.STAFF_OF_FIRE_1387,
            Items.FIRE_BATTLESTAFF_1393,
            Items.MYSTIC_FIRE_STAFF_1401,
            Items.LAVA_BATTLESTAFF_3053,
            Items.MYSTIC_LAVA_STAFF_3054,
            Items.STEAM_BATTLESTAFF_11736,
            Items.MYSTIC_STEAM_STAFF_11738
        )

        if (anyInEquipment(player, *staves)) {
            visualize(
                player,
                if (high) HIGH_ALCH_STAFF_ANIM else LOW_ALCH_STAFF_ANIM,
                if (high) HIGH_ALCH_STAFF_GFX else LOW_ALCH_STAFF_GFX
            )
        } else {
            visualize(
                player,
                if (high) HIGH_ALCH_ANIM else LOW_ALCH_ANIM,
                if (high) HIGH_ALCH_GFX else LOW_ALCH_GFX
            )
        }
        playAudio(player, if (high) Sounds.HIGH_ALCHEMY_97 else Sounds.LOW_ALCHEMY_98)

        if (coins.amount > 0)
            player.inventory.add(coins)

        player.dispatch(ItemAlchemizationEvent(item.id, high))
        removeItem(player, Item(item.id, 1))
        removeRunes(player)
        addXP(player, if (high) 65.0 else 31.0)
        showMagicTab(player)
        setDelay(player, 5)
        return true
    }

    private fun sendTeleport(player: Player, xp: Double, location: Location) {
        if (player.isTeleBlocked) {
            removeAttribute(player, "spell:runes")
            sendMessage(player, "A magical force prevents you from teleporting.")
            return
        }

        val teleType = TeleportManager.TeleportType.NORMAL

        if (player.teleporter.send(location, teleType)) {
            player.dispatch(TeleportEvent(teleType, TeleportMethod.SPELL, -1, location))

            removeRunes(player)
            addXP(player, xp)
            setDelay(player, true)
        }
    }

    private fun attemptHouseTeleport(player: Player) {
        if (player.isTeleBlocked) {
            removeAttribute(player, "spell:runes")
            sendMessage(player, "A magical force prevents you from teleporting.")
            return
        }
        val hasHouse = player.houseManager.location.exitLocation != null
        if (!hasHouse) {
            sendMessage(player, "You do not have a house you can teleport to.")
            return
        }

        player.houseManager.preEnter(player, false)
        val teleType = TeleportManager.TeleportType.NORMAL
        val loc = player.houseManager.getEnterLocation()
        player.teleporter.send(loc, teleType)
        removeRunes(player)
        addXP(player, 30.0)
        setDelay(player, true)
        submitWorldPulse(object : Pulse(2) {
            override fun pulse(): Boolean {
                PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 2))
                player.interfaceManager.openComponent(399)
                player.houseManager.postEnter(player, false)
                return true
            }
        })
    }
}
