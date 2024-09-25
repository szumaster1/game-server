package content.global.skill.crafting.loom

import core.api.*
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class WeaveListener : InteractionListener {

    override fun defineListeners() {
        on(IntType.SCENERY, "weave") { player, node ->
            object : SkillDialogueHandler(player, SkillDialogue.THREE_OPTION, WeavingItem.SACK.product, WeavingItem.BASKET.product, WeavingItem.CLOTH.product) {
                override fun create(amount: Int, index: Int) {
                    submitIndividualPulse(
                        entity = player,
                        pulse = WeaveCraftingPulse(player, node.asScenery(), WeavingItem.values()[index], amount)
                    )
                }
            }.open()
            return@on true
        }
    }
}
