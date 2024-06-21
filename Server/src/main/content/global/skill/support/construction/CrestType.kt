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

enum class CrestType(name: String, val cost: Int = 5000) : CrestRequirement {
    ARRAV(ARRAV_CREST_SYMBOL) {
        override fun eligible(player: Player): Boolean {
            return isQuestComplete(player, "Shield of Arrav")
        }
    },
    ASGARNIA(ASGARNIA_CREST_SYMBOL),
    DORGESHUUN(DORGESHUUN_CREST_SYMBOL) {
        override fun eligible(player: Player): Boolean {
            return isQuestComplete(player, "The Lost Tribe")
        }
    },
    DRAGON(DRAGON_CREST_SYMBOL) {
        override fun eligible(player: Player): Boolean {
            return isQuestComplete(player, "Dragon Slayer")
        }
    },
    FAIRY(FAIRY_CREST_SYMBOL) {
        override fun eligible(player: Player): Boolean {
            return isQuestComplete(player, "Lost City")
        }
    },
    GUTHIX(GUTHIX_CREST_SYMBOL) {
        override fun eligible(player: Player): Boolean {
            return player.getSkills().hasLevel(Skills.PRAYER, 70)
        }
    },
    HAM(H_A_M_CREST_SYMBOL),
    HORSE(HORSE_CREST_SYMBOL) {
        override fun eligible(player: Player): Boolean {
            return player.inventory.containsAtLeastOneItem(
                intArrayOf(Items.TOY_HORSEY_2524, Items.TOY_HORSEY_2520, Items.TOY_HORSEY_2526, Items.TOY_HORSEY_2522)
            )
        }
    },
    JOGRE(JOGRE_CREST_SYMBOL),
    KANDARIN(KANDARIN_CREST_SYMBOL),
    MISTHALIN(MISTHALIN_CREST_SYMBOL),
    MONEY(MONEY_CREST_SYMBOL, 500000),
    SARADOMIN(SARADOMIN_CREST_SYMBOL) {
        override fun eligible(player: Player): Boolean {
            return player.getSkills().hasLevel(Skills.PRAYER, 70)
        }
    },
    SKULL(SKULL_CREST_SYMBOL) {
        override fun eligible(player: Player): Boolean {
            return player.skullManager.isSkulled
        }
    },
    VARROCK(VARROCK_CREST_SYMBOL),
    ZAMORAK(ZAMORAK_CREST_SYMBOL) {
        override fun eligible(player: Player): Boolean {
            return player.getSkills().hasLevel(Skills.PRAYER, 70)
        }
    }
}