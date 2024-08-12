package content.global.skill.support.construction

import core.api.consts.Items
import core.api.isQuestComplete
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills

private val ARRAV_CREST_SYMBOL      = "the Shield of Arrav symbol"
private val ASGARNIA_CREST_SYMBOL   = "the symbol of Asgarnia"
private val DORGESHUUN_CREST_SYMBOL = "the Dorgeshuun brooch"
private val DRAGON_CREST_SYMBOL     = "a dragon"
private val FAIRY_CREST_SYMBOL      = "a fairy"
private val GUTHIX_CREST_SYMBOL     = "the symbol of Guthix"
private val H_A_M_CREST_SYMBOL      = "the symbol of the HAM cult."
private val HORSE_CREST_SYMBOL      = "a horse"
private val JOGRE_CREST_SYMBOL      = "Jiggig"
private val KANDARIN_CREST_SYMBOL   = "the symbol of Kandarin"
private val MISTHALIN_CREST_SYMBOL  = "the symbol of Misthalin"
private val MONEY_CREST_SYMBOL      = "a bag of money"
private val SARADOMIN_CREST_SYMBOL  = "the symbol of Saradomin"
private val SKULL_CREST_SYMBOL      = "a skull"
private val VARROCK_CREST_SYMBOL    = "the symbol of Varrock"
private val ZAMORAK_CREST_SYMBOL    = "the symbol of Zamorak"

/**
 * Crest type.
 */
enum class CrestType(name: String, val cost: Int = 5000) : CrestRequirement {
    /**
     * Arrav.
     */
    ARRAV(ARRAV_CREST_SYMBOL) {
        override fun eligible(player: Player): Boolean {
            return isQuestComplete(player, "Shield of Arrav")
        }
    },

    /**
     * Asgarnia.
     */
    ASGARNIA(ASGARNIA_CREST_SYMBOL),

    /**
     * Dorgeshuun.
     */
    DORGESHUUN(DORGESHUUN_CREST_SYMBOL) {
        override fun eligible(player: Player): Boolean {
            return isQuestComplete(player, "The Lost Tribe")
        }
    },

    /**
     * Dragon.
     */
    DRAGON(DRAGON_CREST_SYMBOL) {
        override fun eligible(player: Player): Boolean {
            return isQuestComplete(player, "Dragon Slayer")
        }
    },

    /**
     * Fairy.
     */
    FAIRY(FAIRY_CREST_SYMBOL) {
        override fun eligible(player: Player): Boolean {
            return isQuestComplete(player, "Lost City")
        }
    },

    /**
     * Guthix.
     */
    GUTHIX(GUTHIX_CREST_SYMBOL) {
        override fun eligible(player: Player): Boolean {
            return player.getSkills().hasLevel(Skills.PRAYER, 70)
        }
    },

    /**
     * Ham.
     */
    HAM(H_A_M_CREST_SYMBOL),

    /**
     * Horse.
     */
    HORSE(HORSE_CREST_SYMBOL) {
        override fun eligible(player: Player): Boolean {
            return player.inventory.containsAtLeastOneItem(
                intArrayOf(Items.TOY_HORSEY_2524, Items.TOY_HORSEY_2520, Items.TOY_HORSEY_2526, Items.TOY_HORSEY_2522)
            )
        }
    },

    /**
     * Jogre.
     */
    JOGRE(JOGRE_CREST_SYMBOL),

    /**
     * Kandarin.
     */
    KANDARIN(KANDARIN_CREST_SYMBOL),

    /**
     * Misthalin.
     */
    MISTHALIN(MISTHALIN_CREST_SYMBOL),

    /**
     * Money.
     */
    MONEY(MONEY_CREST_SYMBOL, 500000),

    /**
     * Saradomin.
     */
    SARADOMIN(SARADOMIN_CREST_SYMBOL) {
        override fun eligible(player: Player): Boolean {
            return player.getSkills().hasLevel(Skills.PRAYER, 70)
        }
    },

    /**
     * Skull.
     */
    SKULL(SKULL_CREST_SYMBOL) {
        override fun eligible(player: Player): Boolean {
            return player.skullManager.isSkulled()
        }
    },

    /**
     * Varrock.
     */
    VARROCK(VARROCK_CREST_SYMBOL),

    /**
     * Zamorak.
     */
    ZAMORAK(ZAMORAK_CREST_SYMBOL) {
        override fun eligible(player: Player): Boolean {
            return player.getSkills().hasLevel(Skills.PRAYER, 70)
        }
    }
}