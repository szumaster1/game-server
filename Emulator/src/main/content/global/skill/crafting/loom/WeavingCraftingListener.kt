package content.global.skill.crafting.loom

import core.api.setInterfaceSprite
import core.api.submitIndividualPulse
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Components

class WeavingCraftingListener : InteractionListener {

    override fun defineListeners() {
        on(IntType.SCENERY, "weave") { player, node ->
            object : SkillDialogueHandler(
                player, SkillDialogue.THREE_OPTION,
                Weaving.SACK.product,
                Weaving.BASKET.product,
                Weaving.CLOTH.product
            ) {
                override fun create(amount: Int, index: Int) {
                    submitIndividualPulse(
                        entity = player,
                        pulse = WeavingCraftingPulse(player, node.asScenery(), Weaving.values()[index], amount)
                    )
                }
            }.open()
            setInterfaceSprite(player, Components.SKILL_MAKE_304, 2,56, 32)
            setInterfaceSprite(player, Components.SKILL_MAKE_304, 3,207, 32)
            return@on true
        }
    }
}
