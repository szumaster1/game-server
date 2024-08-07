package content.global.skill.production.herblore.handlers

import content.global.skill.production.herblore.data.Tar
import content.global.skill.production.herblore.data.Tar.Companion.forItem
import content.global.skill.production.herblore.item.HerbTarPulse
import core.api.consts.Items
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.network.packet.PacketRepository
import core.network.packet.context.ChildPositionContext
import core.network.packet.outgoing.RepositionChild
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Herb tar handler.
 */
@Initializable
class HerbTarHandler : UseWithHandler(Items.SWAMP_TAR_1939) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        for (tar in Tar.values()) {
            addHandler(tar.ingredient.id, ITEM_TYPE, this)
        }
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        //Tars.forItem(event.getUsedItem().getId() == 1939 ? event.getBaseItem() : event.getUsedItem())
        val handler: SkillDialogueHandler = object : SkillDialogueHandler(
            event.player,
            SkillDialogue.ONE_OPTION,
            forItem(if (event.usedItem.id == Items.SWAMP_TAR_1939) event.baseItem else event.usedItem)!!.tar
        ) {
            override fun create(amount: Int, index: Int) {
                event.player.pulseManager.run(
                    forItem(if (event.usedItem.id == Items.SWAMP_TAR_1939) event.baseItem else event.usedItem)?.let {
                        HerbTarPulse(event.player, null, it, amount)
                    }
                )
            }

            override fun getAll(index: Int): Int {
                return event.player.inventory.getAmount(event.usedItem)
            }
        }
        handler.open()
        PacketRepository.send(RepositionChild::class.java, ChildPositionContext(event.player, 309, 2, 210, 15))
        return true
    }
}
