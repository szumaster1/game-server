package content.global.random.event.lostandfound

import core.api.*
import core.api.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.skill.Skills
import core.utilities.RandomFunction

class LostAndFoundListeners : InteractionListener {

    private val sceneryIDs = (8998..9006).toIntArray()
    private val reducedMagicLevel = 10

    override fun defineListeners() {

        on(sceneryIDs, IntType.SCENERY, "operate") { player, node ->
            var magicLevel = getDynLevel(player, Skills.MAGIC)
            val randomRewardAmount = RandomFunction.random(8, 36)
            if (LostAndFound.isOddAppendage(player, node.asScenery())) {
                player.locks.unlockTeleport()
                LostAndFound.cleanup(player)
                queueScript(player, 4, QueueStrength.SOFT) {
                    sendPlainDialogue(player, false, "The Abyssal Services Department apologises for the inconvenience.")
                    if (getAttribute(player, LostAndFound.essenceMine, false)) {
                        addItem(
                            player,
                            if (getStatLevel(player, Skills.MINING) > 30
                            ) Items.PURE_ESSENCE_7936 else Items.RUNE_ESSENCE_1436, randomRewardAmount
                        )
                    }
                    return@queueScript stopExecuting(player)
                }
            } else {
                LostAndFound.setRandomAppendage(player)
                sendMessage(player, "That was not the correct appendage!")
                if (magicLevel > 1) {
                    magicLevel -= reducedMagicLevel
                }
            }
            return@on true
        }
    }
}
