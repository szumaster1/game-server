package content.global.skill.herblore.herbs

import core.api.addItem
import core.api.amountInInventory
import core.api.removeItem
import core.api.setInterfaceSprite
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Components
import org.rs.consts.Items

class HerbloreTarListener : InteractionListener {

    val tar = Tar.values().map(Tar::ingredient).toIntArray()

    override fun defineListeners() {
        onUseWith(IntType.ITEM, tar, Items.SWAMP_TAR_1939) { player, used, _ ->
            var tar = Tar.forItem(used.asItem())
            val handler: SkillDialogueHandler = object : SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, tar!!.product) {
                override fun create(amount: Int, index: Int) {
                    player.pulseManager.run(
                        tar?.let { HerbTarPulse(player, null, it, amount) }
                    )
                }

                override fun getAll(index: Int): Int {
                    return amountInInventory(player, used.id)
                }
            }
            handler.open()
            setInterfaceSprite(player, Components.SKILL_MULTI1_309, 2, 210, 15)
            return@onUseWith true
        }

        /*
         * Handles creating guam in the box & Fish food.
         */

        onUseWith(IntType.ITEM, Items.GROUND_GUAM_6681, Items.EMPTY_BOX_7960) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                if(!removeItem(player, Items.GROUND_SEAWEED_6683)) {
                    addItem(player, Items.FISH_FOOD_272)
                } else {
                    addItem(player, Items.GUAM_IN_A_BOX_6677)
                }
            }
            return@onUseWith true
        }
    }


}