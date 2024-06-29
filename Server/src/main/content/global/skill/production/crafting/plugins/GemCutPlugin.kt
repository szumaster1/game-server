package content.global.skill.production.crafting.plugins

import content.global.skill.production.crafting.data.GemData
import content.global.skill.production.crafting.item.GemCutPulse
import core.api.consts.Items
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class GemCutPlugin : UseWithHandler(
    Items.UNCUT_DIAMOND_1617,
    Items.UNCUT_RUBY_1619,
    Items.UNCUT_EMERALD_1621,
    Items.UNCUT_SAPPHIRE_1623,
    Items.UNCUT_OPAL_1625,
    Items.UNCUT_JADE_1627,
    Items.UNCUT_RED_TOPAZ_1629,
    Items.UNCUT_DRAGONSTONE_1631,
    Items.UNCUT_ONYX_6571
) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(1755, ITEM_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        val gem = GemData.forId(if (event.usedItem.id == Items.CHISEL_1755) event.baseItem else event.usedItem)
        val handler: SkillDialogueHandler = object : SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, gem.gem) {
            override fun create(amount: Int, index: Int) {
                player.pulseManager.run(GemCutPulse(player, gem.uncut, amount, gem))
            }

            override fun getAll(index: Int): Int {
                return player.inventory.getAmount(gem.uncut)
            }
        }
        if (player.inventory.getAmount(gem.uncut) == 1) {
            handler.create(0, 1)
        } else {
            handler.open()
        }
        return true
    }
}
