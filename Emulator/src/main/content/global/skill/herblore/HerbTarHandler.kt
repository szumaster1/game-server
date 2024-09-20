package content.global.skill.herblore

import content.global.skill.herblore.Tar.Companion.forItem
import org.rs.consts.Items
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.net.packet.PacketRepository
import core.net.packet.context.ChildPositionContext
import core.net.packet.outgoing.RepositionChild
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the plugin used to create a herb tar.
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
