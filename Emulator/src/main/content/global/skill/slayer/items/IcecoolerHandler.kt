package content.global.skill.slayer.items

import content.minigame.pestcontrol.dialogue.VoidKnightDialogue.Companion.IDS
import core.api.getStatLevel
import core.api.sendMessage
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.npc.NPC
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.plugin.Plugin
import org.rs.consts.Items

class IcecoolerHandler : UseWithHandler(ICE_COOLER.id) {

    @Throws(Throwable::class)
        override fun newInstance(arg: Any): Plugin<Any> {
            for (id in IDS) {
                addHandler(id, NPC_TYPE, this)
            }
            return this
        }

        override fun handle(event: NodeUsageEvent): Boolean {
            val player = event.player
            if (getStatLevel(player, Skills.SLAYER) < 22) {
                sendMessage(player,"You need a Slayer level of at least 22 to do this.")
                return true
            }
            val npc = event.usedWith as NPC
            if (npc.getSkills().lifepoints > 2) {
                sendMessage(player,"The lizard isn't weak enough to be affected by the icy water.")
                return true
            }
            if (player.inventory.remove(ICE_COOLER)) {
                npc.impactHandler.manualHit(player, npc.getSkills().lifepoints, ImpactHandler.HitsplatType.NORMAL)
                sendMessage(player, "The lizard shudders and collapses from the freezing water.")
            }
            return true
        }

        companion object {
            private val ICE_COOLER = Item(Items.ICE_COOLER_6696)
        }
    }