package content.data.skill

import core.api.consts.Items
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.world.update.flag.context.Animation

/**
 * Represents a skilling tool.
 * @author Emperor
 */
enum class SkillingTool(@JvmField val id: Int, val level: Int, @JvmField val ratio: Double, @JvmField val animation: Animation) {
    /**
     * Represents a bronze axe (woodcutting).
     */
    BRONZE_AXE(id = Items.BRONZE_AXE_1351, level = 1, ratio = 0.05, animation = Animation(879)),

    /**
     * Represents an iron axe (woodcutting).
     */
    IRON_AXE(id = Items.IRON_AXE_1349, level = 1, ratio = 0.1, animation = Animation(877)),

    /**
     * Represents a steel axe (woodcutting).
     */
    STEEL_AXE(id = Items.STEEL_AXE_1353, level = 6, ratio = 0.2, animation = Animation(875)),

    /**
     * Represents a black axe (woodcutting).
     */
    BLACK_AXE(id = Items.BLACK_AXE_1361, level = 6, ratio = 0.25, animation = Animation(873)),

    /**
     * Represents a mithril axe (woodcutting).
     */
    MITHRIL_AXE(id = Items.MITHRIL_AXE_1355, level = 21, ratio = 0.30, animation = Animation(871)),

    /**
     * Represents an adamant axe (woodcutting).
     */
    ADAMANT_AXE(id = Items.ADAMANT_AXE_1357, level = 31, ratio = 0.45, animation = Animation(869)),

    /**
     * Represents a rune axe (woodcutting).
     */
    RUNE_AXE(id = Items.RUNE_AXE_1359, level = 41, ratio = 0.65, animation = Animation(867)),

    /**
     * Represents a dragon axe (woodcutting).
     */
    DRAGON_AXE(id = Items.DRAGON_AXE_6739, level = 61, ratio = 0.85, animation = Animation(2846)),

    /**
     * Represents the Inferno Adze (woodcutting).
     */
    INFERNO_ADZE(id = Items.INFERNO_ADZE_13661, level = 61, ratio = 0.85, animation = Animation(10251)),

    /**
     * Represents the Hatchet Class 1 (woodcutting).
     */
    HATCHET_CLASS1(id = Items.HATCHET_CLASS_1_14132, level = 1, ratio = 0.1, animation = Animation(10603)),

    /**
     * Represents the Hatchet Class 2 (woodcutting).
     */
    HATCHET_CLASS2(id = Items.HATCHET_CLASS_2_14134, level = 20, ratio = 0.3, animation = Animation(10604)),

    /**
     * Represents the Hatchet Class 3 (woodcutting).
     */
    HATCHET_CLASS3(id = Items.HATCHET_CLASS_3_14136, level = 40, ratio = 0.65, animation = Animation(10605)),

    /**
     * Represents the Hatchet Class 4 (woodcutting).
     */
    HATCHET_CLASS4(id = Items.HATCHET_CLASS_4_14138, level = 60, ratio = 0.85, animation = Animation(10606)),

    /**
     * Represents the Hatchet Class 5 (woodcutting).
     */
    HATCHET_CLASS5(id = Items.HATCHET_CLASS_5_14140, level = 80, ratio = 1.0, animation = Animation(10607)),

    /*
     * Pickaxes.
     */

    /**
     * Represents a bronze pickaxe (mining).
     */
    BRONZE_PICKAXE(id = Items.BRONZE_PICKAXE_1265, level = 1, ratio = 0.05, animation = Animation(625)),

    /**
     * Represents an iron pickaxe (mining).
     */
    IRON_PICKAXE(id = Items.IRON_PICKAXE_1267, level = 1, ratio = 0.1, animation = Animation(626)),

    /**
     * Represents a steel pickaxe (mining).
     */
    STEEL_PICKAXE(id = Items.STEEL_PICKAXE_1269, level = 6, ratio = 0.2, animation = Animation(627)),

    /**
     * Represents a mithril pickaxe (mining).
     */
    MITHRIL_PICKAXE(id = Items.MITHRIL_PICKAXE_1273, level = 21, ratio = 0.30, animation = Animation(629)),

    /**
     * Represents an adamant pickaxe (mining).
     */
    ADAMANT_PICKAXE(id = Items.ADAMANT_PICKAXE_1271, level = 31, ratio = 0.45, animation = Animation(628)),

    /**
     * Represents a rune pickaxe (mining).
     */
    RUNE_PICKAXE(id = Items.RUNE_PICKAXE_1275, level = 41, ratio = 0.65, animation = Animation(624)),

    /**
     * Represents the inferno adze (mining).
     */
    INFERNO_ADZE2(id = Items.INFERNO_ADZE_13661, level = 61, ratio = 1.0, animation = Animation(10222)),

    /**
     * Represents the pickaxe class 1 (mining).
     */
    PICKAXE_CLASS1(id = Items.PICKAXE_CLASS_1_14122, level = 1, ratio = 0.1, animation = Animation(10608)),

    /**
     * Represents the pickaxe class 2 (mining).
     */
    PICKAXE_CLASS2(id = Items.PICKAXE_CLASS_2_14124, level = 20, ratio = 0.3, animation = Animation(10609)),

    /**
     * Represents the pickaxe class 3 (mining).
     */
    PICKAXE_CLASS3(id = Items.PICKAXE_CLASS_3_14126, level = 40, ratio = 0.65, animation = Animation(10610)),

    /**
     * Represents the pickaxe class 4 (mining).
     */
    PICKAXE_CLASS4(id = Items.PICKAXE_CLASS_4_14128, level = 60, ratio = 0.85, animation = Animation(10611)),

    /**
     * Represents the pickaxe class 5 (mining).
     */
    PICKAXE_CLASS5(id = Items.PICKAXE_CLASS_5_14130, level = 80, ratio = 1.0, animation = Animation(10612)),

    /*
     * Harpoons.
     */

    /**
     * Represents the harpoon class 1 (Fishing).
     */
    HARPOON_CLASS1(id = Items.HARPOON_CLASS_1_14142, level = 1, ratio = 0.1, animation = Animation(10613)),

    /**
     * Represents the harpoon class 2 (Fishing).
     */
    HARPOON_CLASS2(id = Items.HARPOON_CLASS_2_14144, level = 20, ratio = 0.3, animation = Animation(10614)),

    /**
     * Represents the harpoon class 3 (Fishing).
     */
    HARPOON_CLASS3(id = Items.HARPOON_CLASS_3_14146, level = 40, ratio = 0.65, animation = Animation(10615)),

    /**
     * Represents the harpoon class 4 (Fishing).
     */
    HARPOON_CLASS4(id = Items.HARPOON_CLASS_4_14148, level = 60, ratio = 0.85, animation = Animation(10616)),

    /**
     * Represents the harpoon class 5 (Fishing).
     */
    HARPOON_CLASS5(id = Items.HARPOON_CLASS_5_14150, level = 80, ratio = 1.0, animation = Animation(10617)),

    /*
     * Butterfly net.
     */

    /**
     * Represents the butterfly net class 1 (Hunter).
     */
    BUTTERFLY_NET_CLASS1(id = Items.BUTTERFLY_NET_CLASS_1_14152, level = 1, ratio = 0.1, animation = Animation(10618)),

    /**
     * Represents the butterfly net class 2 (Hunter).
     */
    BUTTERFLY_NET_CLASS2(id = Items.BUTTERFLY_NET_CLASS_2_14154, level = 20, ratio = 0.3, animation = Animation(10619)),

    /**
     * Represents the butterfly net class 3 (Hunter).
     */
    BUTTERFLY_NET_CLASS3(id = Items.BUTTERFLY_NET_CLASS_3_14156, level = 40, ratio = 0.65, animation = Animation(10620)),

    /**
     * Represents the butterfly net class 4 (Hunter).
     */
    BUTTERFLY_NET_CLASS4(id = Items.BUTTERFLY_NET_CLASS_4_14158, level = 60, ratio = 0.85, animation = Animation(10621)),

    /**
     * Represents the butterfly net class 5 (Hunter).
     */
    BUTTERFLY_NET_CLASS5(id = Items.BUTTERFLY_NET_CLASS_5_14160, level = 80, ratio = 1.0, animation = Animation(10622)),

    /*
     * Firemaking tools.
     */

    /**
     * Represents the training bow (Firemaking).
     */
    TRAINING_BOW(id = Items.TRAINING_BOW_9705, level = 1, ratio = 0.1, animation = Animation(6713)),

    /**
     * Represents the longbow (Firemaking).
     */
    LONGBOW(id = Items.LONGBOW_839, level = 1, ratio = 0.1, animation = Animation(6714)),

    /**
     * Represents the shortbow (Firemaking).
     */
    SHORTBOW(id = Items.SHORTBOW_841, level = 1, ratio = 0.1, animation = Animation(6714)),

    /**
     * Represents the oak shortbow (Firemaking).
     */
    OAK_SHORTBOW(id = Items.OAK_SHORTBOW_843, level = 1, ratio = 0.2, animation = Animation(6715)),

    /**
     * Represents the oak longbow (Firemaking).
     */
    OAK_LONGBOW(id = Items.OAK_LONGBOW_845, level = 1, ratio = 0.2, animation = Animation(6715)),

    /**
     * Represents the willow shortbow (Firemaking).
     */
    WILLOW_SHORTBOW(id = Items.WILLOW_SHORTBOW_849, level = 1, ratio = 0.30, animation = Animation(6716)),

    /**
     * Represents the willow longbow (Firemaking).
     */
    WILLOW_LONGBOW(id = Items.WILLOW_LONGBOW_847, level = 1, ratio = 0.30, animation = Animation(6716)),

    /**
     * Represents the maple shortbow (Firemaking).
     */
    MAPLE_SHORTBOW(id = Items.MAPLE_SHORTBOW_853, level = 1, ratio = 0.45, animation = Animation(6717)),

    /**
     * Represents the maple longbow (Firemaking).
     */
    MAPLE_LONGBOW(id = Items.MAPLE_LONGBOW_851, level = 1, ratio = 0.45, animation = Animation(6717)),

    /**
     * Represents the yew shortbow (Firemaking).
     */
    YEW_SHORTBOW(id = Items.YEW_SHORTBOW_857, level = 1, ratio = 0.65, animation = Animation(6718)),

    /**
     * Represents the yew longbow (Firemaking).
     */
    YEW_LONGBOW(id = Items.YEW_LONGBOW_855, level = 1, ratio = 0.65, animation = Animation(6718)),

    /**
     * Represents the magic shortbow (Firemaking).
     */
    MAGIC_SHORTBOW(id = Items.MAGIC_SHORTBOW_861, level = 1, ratio = 0.85, animation = Animation(6719)),

    /**
     * Represents the magic longbow (Firemaking).
     */
    MAGIC_LONGBOW(id = Items.MAGIC_LONGBOW_859, level = 1, ratio = 0.85, animation = Animation(6719)),

    /**
     * Represents the seercull (Firemaking).
     */
    SEERCULL(id = Items.SEERCULL_6724, level = 1, ratio = 0.85, animation = Animation(6720)),

    /**
     * Represents the sacred clay bow (Firemaking).
     */
    SACRED_CLAY_BOW(id = Items.SACRED_CLAY_BOW_14121, level = 1, ratio = 1.00, animation = Animation(10990));

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
            val hatchetPriority = arrayOf(
                HATCHET_CLASS5,
                HATCHET_CLASS4,
                DRAGON_AXE,
                HATCHET_CLASS3,
                RUNE_AXE,
                ADAMANT_AXE,
                HATCHET_CLASS2,
                MITHRIL_AXE,
                BLACK_AXE,
                STEEL_AXE,
                HATCHET_CLASS1,
                IRON_AXE,
                BRONZE_AXE
            )
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
            val pickaxePriority = arrayOf(
                PICKAXE_CLASS5,
                PICKAXE_CLASS4,
                RUNE_PICKAXE,
                PICKAXE_CLASS3,
                ADAMANT_PICKAXE,
                PICKAXE_CLASS2,
                MITHRIL_PICKAXE,
                STEEL_PICKAXE,
                PICKAXE_CLASS1,
                IRON_PICKAXE,
                BRONZE_PICKAXE,
            )
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
            val harpoonPriority = arrayOf(
                BUTTERFLY_NET_CLASS5,
                BUTTERFLY_NET_CLASS4,
                BUTTERFLY_NET_CLASS3,
                BUTTERFLY_NET_CLASS2,
                BUTTERFLY_NET_CLASS1,
            )
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
            val butterflyNetPriority = arrayOf(
                BUTTERFLY_NET_CLASS5,
                BUTTERFLY_NET_CLASS4,
                BUTTERFLY_NET_CLASS3,
                BUTTERFLY_NET_CLASS2,
                BUTTERFLY_NET_CLASS1,
            )
            for (butterflyNet in butterflyNetPriority) {
                if (checkTool(player, Skills.HUNTER, butterflyNet)) {
                    tool = butterflyNet
                    break
                }
            }
            return tool
        }

        fun getFiremakingTool(player: Player): SkillingTool? {
            var tool: SkillingTool? = null
            val bowPriority = arrayOf(
                SACRED_CLAY_BOW,
                SEERCULL,
                MAGIC_SHORTBOW,
                MAGIC_LONGBOW,
                YEW_SHORTBOW,
                YEW_LONGBOW,
                MAPLE_SHORTBOW,
                MAPLE_LONGBOW,
                WILLOW_SHORTBOW,
                WILLOW_LONGBOW,
                OAK_LONGBOW,
                OAK_SHORTBOW,
                SHORTBOW,
                LONGBOW,
                TRAINING_BOW
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
                Skills.FIREMAKING -> getFiremakingTool(player)
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
