package content.global.skill.production.smithing.item

import content.global.skill.production.smithing.GodswordShardCombination
import core.api.*
import core.api.consts.Animations
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

class GodswordAssemblingPulse(player: Player?, var used: Item) : SkillPulse<Item>(player, null) {

    private val combination: GodswordShardCombination? = null
    private val ANIMATION = Animations.HUMAN_ANVIL_HAMMER_SMITHING_898
    private var tick = 0

    override fun checkRequirements(): Boolean {
        return true
    }

    override fun start() {
        super.start()
    }

    override fun animate() {

    }

    override fun reward(): Boolean {
        when (tick++) {
            0 -> animate(player, ANIMATION)
            5 -> animate(player, ANIMATION)
            9 -> {
                removeItem(player, combination!!.product)
                addItem(player, combination.item.id)
                sendDialogueLines(player, "Even for an experienced smith it is not an easy task, but eventually", "it is done.")
                rewardXP(player, Skills.SMITHING, 100.0)
            }
        }
        return true
    }

    override fun message(type: Int) {}

}
