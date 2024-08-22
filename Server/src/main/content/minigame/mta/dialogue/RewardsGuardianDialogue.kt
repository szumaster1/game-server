package content.minigame.mta.dialogue

import content.minigame.mta.MageTrainingArena
import core.api.addItemOrDrop
import cfg.consts.Items
import cfg.consts.NPCs
import core.api.removeItem
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Rewards Guardian dialogue.
 */
@Initializable
class RewardsGuardianDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        if (args.size > 1) {
            npc(FacialExpression.OLD_NORMAL, "Have you spoken to my fellow guardian downstairs?").also { stage = 3 }
            return true
        }
        player("Hi.").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (!player.getSavedData().activityData.isStartedMta) {
            when (stage) {
                0 -> npc(FacialExpression.OLD_NORMAL, "Greetings. Have you spoken to my fellow Guardian", "downstairs?").also { stage++ }
                1 -> player("Nope.").also { stage++ }
                2 -> npc(FacialExpression.OLD_NORMAL, "Well, you need to talk to him first.").also { stage = END_DIALOGUE }
                3 -> player("Nope.").also { stage++ }
                4 -> npc(FacialExpression.OLD_NORMAL, "Well, you need to talk to him first.").also { stage = END_DIALOGUE }
            }
            return true
        }
        when (stage) {
            0 -> npc(FacialExpression.OLD_NORMAL, "Greetings. What wisdom do you seek?").also { stage++ }
            1 -> options("Who are you?", "Can I trade my Pizazz Points please?", "Thanks, bye!").also { stage++ }
            2 -> when (buttonId) {
                1 -> player("Who are you?").also { stage = 10 }
                2 -> player("Can I trade my Pizazz Points please?").also { stage = 20 }
                3 -> end()
            }
            10 -> npc(FacialExpression.OLD_NORMAL, "Me? I'm here to grant you rewards for any of the", "Pizazz Points you may have earned in this training", "arena. Like my fellow Guardians, I am part of the", "arena and live to ensure its safe running.").also { stage++ }
            11 -> player("I see.").also { stage = 1 }
            20 -> npc(FacialExpression.OLD_NORMAL, "Why of course.").also { stage++ }
            21 -> {
                end()
                MageTrainingArena.SHOP.open(player)
            }
            30 -> npc(FacialExpression.OLD_NORMAL, "Well, we do stock a special book that you may be", "interested in, which provides a comprehensive guide to", "this training arena. It costs 200gp. Would like", "one?").also { stage++ }
            31 -> {
                end()
                if(!removeItem(player, Item(Items.COINS_995, 200))){
                    sendMessage(player, "You don't have enough coins for that.")
                } else {
                    addItemOrDrop(player, Items.ARENA_BOOK_6891)
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.REWARDS_GUARDIAN_3103)
    }
}
