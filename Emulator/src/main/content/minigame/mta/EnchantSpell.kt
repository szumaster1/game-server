package content.minigame.mta

import content.minigame.mta.impl.EnchantingZone
import cfg.consts.Items
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.combat.spell.MagicSpell
import core.game.node.entity.combat.spell.Runes
import core.game.node.entity.combat.spell.SpellType
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.SpellBookManager.SpellBook
import core.game.node.entity.player.link.audio.Audio
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Plugin

/**
 * Represents the Enchant spell.
 */
class EnchantSpell : MagicSpell {
    private val jewellery: Map<Int, Item>?

    constructor() {
        jewellery = null
    }

    constructor(
        level: Int, experience: Double, jewellery: Map<Int, Item>, runes: Array<Item?>?
    ) : super(SpellBook.MODERN, level, experience, ANIMATION, GRAPHIC, Audio(115, 1, 0), runes) {
        this.jewellery = jewellery
    }

    override fun cast(entity: Entity, target: Node): Boolean {
        if (target !is Item || entity !is Player) {
            return false
        }
        entity.interfaceManager.setViewedTab(6)
        val enchanted = jewellery?.getOrDefault(target.id, null)

        if (enchanted == null) {
            entity.packetDispatch.sendMessage("You can't use this spell on this item.")
            return false
        }
        if (!meetsRequirements(entity, true, true)) {
            return false
        }

        if (entity.inventory.remove(target)) {
            val (visualId, graphic) = when (target.id) {
                Items.SAPPHIRE_RING_1637, Items.EMERALD_RING_1639, Items.RUBY_RING_1641, Items.DIAMOND_RING_1643, Items.DRAGONSTONE_RING_1645, Items.ONYX_RING_6575 -> Pair(
                    712,
                    Graphic(238, 92)
                )

                Items.SAPPHIRE_NECKLACE_1656, Items.SAPPHIRE_AMULET_1694, Items.SAPPHIRE_BRACELET_11072, Items.EMERALD_NECKLACE_1658, Items.EMERALD_AMULET_1696, Items.EMERALD_BRACELET_11076 -> Pair(
                    719,
                    Graphic(114, 92)
                )

                Items.RUBY_NECKLACE_1660, Items.RUBY_AMULET_1698, Items.RUBY_BRACELET_11085, Items.DIAMOND_NECKLACE_1662, Items.DIAMOND_AMULET_1700, Items.DIAMOND_BRACELET_11092 -> Pair(
                    720,
                    Graphic(115, 92)
                )

                Items.DRAGON_NECKLACE_1664, Items.DRAGONSTONE_AMMY_1702, Items.DRAGON_BRACELET_11115, Items.ONYX_NECKLACE_6577, Items.ONYX_AMULET_6581, Items.ONYX_BRACELET_11130 -> Pair(
                    721,
                    Graphic(116, 92)
                )

                else -> Pair(719, Graphic(114, 92))
            }
            core.api.visualize(entity, visualId, graphic)
            entity.inventory.add(enchanted)
        }

        //MTA-Specific Code
        if (entity.zoneMonitor.isInZone("Enchantment Chamber")) {
            entity.graphics(Graphic.create(237, 110))
            var pizazz = 0
            if (target.id == 6903) {
                pizazz =
                    (if (spellId == 5) 1 else if (spellId == 16) 2 else if (spellId == 28) 3 else if (spellId == 36) 4 else if (spellId == 51) 5 else 6) * 2
            } else {
                val shape = EnchantingZone.Shapes.forItem(target)
                if (shape != null) {
                    var convert = entity.getAttribute("mta-convert", 0)
                    convert += 1
                    if (convert >= 10) {
                        pizazz =
                            if (spellId == 5) 1 else if (spellId == 16) 2 else if (spellId == 28) 3 else if (spellId == 36) 4 else if (spellId == 51) 5 else 6
                        convert = 0
                    }
                    entity.setAttribute("mta-convert", convert)
                    if (shape == EnchantingZone.BONUS_SHAPE) {
                        pizazz += 1
                        entity.sendMessage("You get " + pizazz + " bonus point" + (if (pizazz != 1) "s" else "") + "!")
                    }
                }
            }
            if (pizazz != 0) {
                EnchantingZone.ZONE.incrementPoints(entity, MTAType.ENCHANTERS.ordinal, pizazz)
            }
        }
        return true
    }

    override val delay: Int
        get() = super.delay

    override fun getExperience(player: Player): Double {
        return if (player.zoneMonitor.isInZone("Enchantment Chamber")) {
            experience - experience * 0.25
        } else experience
    }

    override fun newInstance(arg: SpellType?): Plugin<SpellType?>? {

        SpellBook.MODERN.register(
            5, EnchantSpell(
                7, 17.5, mapOf(
                    //Begin Jewelry Enchantments
                    Items.SAPPHIRE_RING_1637 to Item(Items.RING_OF_RECOIL_2550),
                    Items.SAPPHIRE_NECKLACE_1656 to Item(Items.GAMES_NECKLACE8_3853),
                    Items.SAPPHIRE_AMULET_1694 to Item(Items.AMULET_OF_MAGIC_1727),
                    Items.SAPPHIRE_BRACELET_11072 to Item(Items.BRACELET_OF_CLAY_11074),
                    //Begin MTA-specific enchantments
                    Items.CUBE_6899 to Item(Items.ORB_6902),
                    Items.CYLINDER_6898 to Item(Items.ORB_6902),
                    Items.ICOSAHEDRON_6900 to Item(Items.ORB_6902),
                    Items.PENTAMID_6901 to Item(Items.ORB_6902),
                    Items.DRAGONSTONE_6903 to Item(Items.ORB_6902)
                ), arrayOf(Item(Items.COSMIC_RUNE_564, 1), Item(Items.WATER_RUNE_555, 1))
            )
        )

        SpellBook.MODERN.register(
            16, EnchantSpell(
                27, 37.0, mapOf(
                    //Begin Jewelry Enchantments
                    Items.EMERALD_RING_1639 to Item(Items.RING_OF_DUELLING8_2552),
                    Items.EMERALD_NECKLACE_1658 to Item(Items.BINDING_NECKLACE_5521),
                    Items.EMERALD_AMULET_1696 to Item(Items.AMULET_OF_DEFENCE_1729),
                    Items.EMERALD_BRACELET_11076 to Item(Items.CASTLEWAR_BRACE3_11079),
                    //Begin MTA-Specific Enchantments
                    Items.CUBE_6899 to Item(Items.ORB_6902),
                    Items.CYLINDER_6898 to Item(Items.ORB_6902),
                    Items.ICOSAHEDRON_6900 to Item(Items.ORB_6902),
                    Items.PENTAMID_6901 to Item(Items.ORB_6902),
                    Items.DRAGONSTONE_6903 to Item(Items.ORB_6902)
                ), arrayOf(Item(Runes.COSMIC_RUNE.id, 1), Item(Runes.AIR_RUNE.id, 3))
            )
        )

        SpellBook.MODERN.register(
            28, EnchantSpell(
                49, 59.0, mapOf(
                    //Begin Jewelry Enchantments
                    Items.RUBY_RING_1641 to Item(Items.RING_OF_FORGING_2568),
                    Items.RUBY_NECKLACE_1660 to Item(Items.DIGSITE_PENDANT_5_11194),
                    Items.RUBY_AMULET_1698 to Item(Items.AMULET_OF_STRENGTH_1725),
                    Items.RUBY_BRACELET_11085 to Item(Items.INOCULATION_BRACE_11088),
                    //Begin MTA-Specific Enchantments
                    Items.CUBE_6899 to Item(Items.ORB_6902),
                    Items.CYLINDER_6898 to Item(Items.ORB_6902),
                    Items.ICOSAHEDRON_6900 to Item(Items.ORB_6902),
                    Items.PENTAMID_6901 to Item(Items.ORB_6902),
                    Items.DRAGONSTONE_6903 to Item(Items.ORB_6902)
                ), arrayOf(Item(Runes.COSMIC_RUNE.id, 1), Item(Runes.FIRE_RUNE.id, 5))
            )
        )

        SpellBook.MODERN.register(
            36, EnchantSpell(
                57, 67.0, mapOf(
                    Items.DIAMOND_RING_1643 to Item(Items.RING_OF_LIFE_2570),
                    Items.DIAMOND_NECKLACE_1662 to Item(Items.PHOENIX_NECKLACE_11090),
                    Items.DIAMOND_AMULET_1700 to Item(Items.AMULET_OF_POWER_1731),
                    Items.DIAMOND_BRACELET_11092 to Item(Items.FORINTHRY_BRACE5_11095),
                    //Begin MTA-Specific Enchantments
                    Items.CUBE_6899 to Item(Items.ORB_6902),
                    Items.CYLINDER_6898 to Item(Items.ORB_6902),
                    Items.ICOSAHEDRON_6900 to Item(Items.ORB_6902),
                    Items.PENTAMID_6901 to Item(Items.ORB_6902),
                    Items.DRAGONSTONE_6903 to Item(Items.ORB_6902)
                ), arrayOf(Item(Runes.COSMIC_RUNE.id, 1), Item(Runes.EARTH_RUNE.id, 10))
            )
        )

        SpellBook.MODERN.register(
            51, EnchantSpell(
                68, 78.0, mapOf(
                    //Begin Jewelry Enchantment
                    Items.DRAGONSTONE_RING_1645 to Item(14646),
                    Items.DRAGON_NECKLACE_1664 to Item(Items.SKILLS_NECKLACE4_11105),
                    Items.DRAGONSTONE_AMMY_1702 to Item(Items.AMULET_OF_GLORY4_1712),
                    Items.DRAGON_BRACELET_11115 to Item(Items.COMBAT_BRACELET4_11118),
                    //Begin MTA-Specific Enchantments
                    Items.CUBE_6899 to Item(Items.ORB_6902),
                    Items.CYLINDER_6898 to Item(Items.ORB_6902),
                    Items.ICOSAHEDRON_6900 to Item(Items.ORB_6902),
                    Items.PENTAMID_6901 to Item(Items.ORB_6902),
                    Items.DRAGONSTONE_6903 to Item(Items.ORB_6902)
                ), arrayOf(Item(Runes.COSMIC_RUNE.id, 1), Item(Runes.WATER_RUNE.id, 15), Item(Runes.EARTH_RUNE.id, 15))
            )
        )

        SpellBook.MODERN.register(
            61, EnchantSpell(
                87, 97.0, mapOf(
                    //Begin Jewelry Enchantments
                    Items.ONYX_RING_6575 to Item(Items.RING_OF_STONE_6583),
                    Items.ONYX_NECKLACE_6577 to Item(Items.BERSERKER_NECKLACE_11128),
                    Items.ONYX_AMULET_6581 to Item(Items.AMULET_OF_FURY_6585),
                    Items.ONYX_BRACELET_11130 to Item(Items.REGEN_BRACELET_11133),
                    //Begin MTA-Specific Enchantments
                    Items.CUBE_6899 to Item(Items.ORB_6902),
                    Items.CYLINDER_6898 to Item(Items.ORB_6902),
                    Items.ICOSAHEDRON_6900 to Item(Items.ORB_6902),
                    Items.PENTAMID_6901 to Item(Items.ORB_6902),
                    Items.DRAGONSTONE_6903 to Item(Items.ORB_6902)
                ), arrayOf(Item(Runes.COSMIC_RUNE.id, 1), Item(Runes.FIRE_RUNE.id, 20), Item(Runes.EARTH_RUNE.id, 20))
            )
        )
        return this
    }

    companion object {
        private val ANIMATION = Animation.create(719)
        private val GRAPHIC = Graphic(114, 95)
    }
}