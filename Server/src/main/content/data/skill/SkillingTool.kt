package content.data.skill

import core.api.consts.Items
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.world.update.flag.context.Animation

/**
 * @author Emperor
 */
enum class SkillingTool(@JvmField val id: Int, val level: Int, @JvmField val ratio: Double, @JvmField val animation: Animation) {
    BRONZE_AXE(Items.BRONZE_AXE_1351, 1, 0.05, Animation(879)),
    IRON_AXE(Items.IRON_AXE_1349, 1, 0.1, Animation(877)),
    STEEL_AXE(Items.STEEL_AXE_1353, 6, 0.2, Animation(875)),
    BLACK_AXE(Items.BLACK_AXE_1361, 6, 0.25, Animation(873)),
    MITHRIL_AXE(Items.MITHRIL_AXE_1355, 21, 0.30, Animation(871)),
    ADAMANT_AXE(Items.ADAMANT_AXE_1357, 31, 0.45, Animation(869)),
    RUNE_AXE(Items.RUNE_AXE_1359, 41, 0.65, Animation(867)),
    DRAGON_AXE(Items.DRAGON_AXE_6739, 61, 0.85, Animation(2846)),
    INFERNO_ADZE(Items.INFERNO_ADZE_13661, 61, 0.85, Animation(10251)),
    HATCHET_CLASS1(Items.HATCHET_CLASS_1_14132, 1, 0.1, Animation(10603)),
    HATCHET_CLASS2(Items.HATCHET_CLASS_2_14134, 20, 0.3, Animation(10604)),
    HATCHET_CLASS3(Items.HATCHET_CLASS_3_14136, 40, 0.65, Animation(10605)),
    HATCHET_CLASS4(Items.HATCHET_CLASS_4_14138, 60, 0.85, Animation(10606)),
    HATCHET_CLASS5(Items.HATCHET_CLASS_5_14140, 80, 1.0, Animation(10607)),

    BRONZE_PICKAXE(Items.BRONZE_PICKAXE_1265, 1, 0.05, Animation(625)),
    IRON_PICKAXE(Items.IRON_PICKAXE_1267, 1, 0.1, Animation(626)),
    STEEL_PICKAXE(Items.STEEL_PICKAXE_1269, 6, 0.2, Animation(627)),
    MITHRIL_PICKAXE(Items.MITHRIL_PICKAXE_1273, 21, 0.30, Animation(629)),
    ADAMANT_PICKAXE(Items.ADAMANT_PICKAXE_1271, 31, 0.45, Animation(628)),
    RUNE_PICKAXE(Items.RUNE_PICKAXE_1275, 41, 0.65, Animation(624)),
    INFERNO_ADZE2(Items.INFERNO_ADZE_13661, 61, 1.0, Animation(10222)),
    PICKAXE_CLASS1(Items.PICKAXE_CLASS_1_14122, 1, 0.1, Animation(10608)),
    PICKAXE_CLASS2(Items.PICKAXE_CLASS_2_14124, 20, 0.3, Animation(10609)),
    PICKAXE_CLASS3(Items.PICKAXE_CLASS_3_14126, 40, 0.65, Animation(10610)),
    PICKAXE_CLASS4(Items.PICKAXE_CLASS_4_14128, 60, 0.85, Animation(10611)),
    PICKAXE_CLASS5(Items.PICKAXE_CLASS_5_14130, 80, 1.0, Animation(10612)),

    HARPOON_CLASS1(Items.HARPOON_CLASS_1_14142, 1, 0.1, Animation(10613)),
    HARPOON_CLASS2(Items.HARPOON_CLASS_2_14144, 20, 0.3, Animation(10614)),
    HARPOON_CLASS3(Items.HARPOON_CLASS_3_14146, 40, 0.65, Animation(10615)),
    HARPOON_CLASS4(Items.HARPOON_CLASS_4_14148, 60, 0.85, Animation(10616)),
    HARPOON_CLASS5(Items.HARPOON_CLASS_5_14150, 80, 1.0, Animation(10617)),

    BUTTERFLY_NET_CLASS1(Items.BUTTERFLY_NET_CLASS_1_14152, 1, 0.1, Animation(10618)),
    BUTTERFLY_NET_CLASS2(Items.BUTTERFLY_NET_CLASS_2_14154, 20, 0.3, Animation(10619)),
    BUTTERFLY_NET_CLASS3(Items.BUTTERFLY_NET_CLASS_3_14156, 40, 0.65, Animation(10620)),
    BUTTERFLY_NET_CLASS4(Items.BUTTERFLY_NET_CLASS_4_14158, 60, 0.85, Animation(10621)),
    BUTTERFLY_NET_CLASS5(Items.BUTTERFLY_NET_CLASS_5_14160, 80, 1.0, Animation(10622)),

    TRAINING_BOW(Items.TRAINING_BOW_9705, 1, 0.1, Animation(6713)),
    LONGBOW(Items.LONGBOW_839, 1, 0.1, Animation(6714)),
    SHORTBOW(Items.SHORTBOW_841, 1, 0.1, Animation(6714)),
    OAK_SHORTBOW(Items.OAK_SHORTBOW_843, 5, 0.2, Animation(6715)),
    OAK_LONGBOW(Items.OAK_LONGBOW_845, 5, 0.2, Animation(6715)),
    WILLOW_LONGBOW(Items.WILLOW_LONGBOW_847, 20, 0.30, Animation(6716)),
    WILLOW_SHORTBOW(Items.WILLOW_SHORTBOW_849, 20, 0.30, Animation(6716)),
    MAPLE_LONGBOW(Items.MAPLE_LONGBOW_851, 30, 0.45, Animation(6717)),
    MAPLE_SHORTBOW(Items.MAPLE_SHORTBOW_853, 30, 0.45, Animation(6717)),
    YEW_LONGBOW(Items.YEW_LONGBOW_855, 40, 0.65, Animation(6718)),
    YEW_SHORTBOW(Items.YEW_SHORTBOW_857, 40, 0.65, Animation(6718)),
    MAGIC_LONGBOW(Items.MAGIC_LONGBOW_859, 50, 0.85, Animation(6719)),
    MAGIC_SHORTBOW(Items.MAGIC_SHORTBOW_861, 50, 0.85, Animation(6719)),
    SEERCULL(Items.SEERCULL_6724, 50, 0.85, Animation(6720));

    companion object {
        fun forId(itemId: Int): SkillingTool? {
            for (tool in values()) {
                if (tool.id == itemId) {
                    return tool
                }
            }
            return null
        }

        @JvmStatic
        fun getHatchet(player: Player): SkillingTool? {
            var tool: SkillingTool? = null
            val hatchetPriority = arrayOf(HATCHET_CLASS5, HATCHET_CLASS4, DRAGON_AXE, HATCHET_CLASS3, RUNE_AXE, ADAMANT_AXE, HATCHET_CLASS2, MITHRIL_AXE, BLACK_AXE, STEEL_AXE, HATCHET_CLASS1, IRON_AXE, BRONZE_AXE)
            for (hatchet in hatchetPriority) {
                if (checkTool(player, Skills.WOODCUTTING, hatchet)) {
                    tool = hatchet
                    break
                }
            }
            if (checkTool(player, Skills.WOODCUTTING, INFERNO_ADZE)) {
                if (player.getSkills().getLevel(Skills.FIREMAKING) >= 92) {
                    tool = INFERNO_ADZE
                }
            }
            return tool
        }

        @JvmStatic
        fun getPickaxe(player: Player): SkillingTool? {
            var tool: SkillingTool? = null
            val pickaxePriority = arrayOf(PICKAXE_CLASS5, PICKAXE_CLASS4, RUNE_PICKAXE, PICKAXE_CLASS3, ADAMANT_PICKAXE, PICKAXE_CLASS2, MITHRIL_PICKAXE, STEEL_PICKAXE, PICKAXE_CLASS1, IRON_PICKAXE, BRONZE_PICKAXE,)
            for (pickaxe in pickaxePriority) {
                if (checkTool(player, Skills.MINING, pickaxe)) {
                    tool = pickaxe
                    break
                }
            }
            if (checkTool(player, Skills.MINING, INFERNO_ADZE2)) {
                if (player.getSkills().getLevel(Skills.FIREMAKING) >= 92) {
                    tool = INFERNO_ADZE2
                }
            }
            return tool
        }

        fun getHarpoon(player: Player): SkillingTool? {
            var tool: SkillingTool? = null
            val harpoonPriority = arrayOf(BUTTERFLY_NET_CLASS5, BUTTERFLY_NET_CLASS4, BUTTERFLY_NET_CLASS3, BUTTERFLY_NET_CLASS2, BUTTERFLY_NET_CLASS1,)
            for (harpoon in harpoonPriority) {
                if (checkTool(player, Skills.FISHING, harpoon)) {
                    tool = harpoon
                    break
                }
            }
            return tool
        }

        fun getButterflyNet(player: Player): SkillingTool? {
            var tool: SkillingTool? = null
            val butterflyNetPriority = arrayOf(BUTTERFLY_NET_CLASS5, BUTTERFLY_NET_CLASS4, BUTTERFLY_NET_CLASS3, BUTTERFLY_NET_CLASS2, BUTTERFLY_NET_CLASS1,)
            for (butterflyNet in butterflyNetPriority) {
                if (checkTool(player, Skills.HUNTER, butterflyNet)) {
                    tool = butterflyNet
                    break
                }
            }
            return tool
        }

        fun getBow(player: Player): SkillingTool? {
            var tool: SkillingTool? = null
            val bowPriority = arrayOf(
                SEERCULL,MAGIC_SHORTBOW,MAGIC_LONGBOW,YEW_SHORTBOW,YEW_LONGBOW,
                MAPLE_SHORTBOW,MAPLE_LONGBOW,WILLOW_SHORTBOW,WILLOW_LONGBOW,
                OAK_LONGBOW, OAK_SHORTBOW,SHORTBOW,LONGBOW,TRAINING_BOW
            )
            for (bowId in bowPriority) {
                if (checkTool(player, Skills.FIREMAKING, bowId)) {
                    tool = bowId
                    break
                }
            }
            return tool
        }


        fun getToolForSkill(player: Player, skill: Int): SkillingTool? {
            return when (skill) {
                Skills.MINING -> getPickaxe(player)
                Skills.WOODCUTTING -> getHatchet(player)
                Skills.FISHING -> getHarpoon(player)
                Skills.HUNTER -> getButterflyNet(player)
                Skills.FIREMAKING -> getBow(player)
                else -> null
            }
        }

        fun checkTool(player: Player, skillId: Int, tool: SkillingTool): Boolean {
            if (player.getSkills().getStaticLevel(skillId) < tool.level) {
                return false
            }
            if (player.equipment.getNew(3).id == tool.id) {
                return true
            }
            return player.inventory.contains(tool.id, 1)
        }
    }
}
