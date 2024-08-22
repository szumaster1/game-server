package content.region.misthalin.quest.member.whatliesbelow

import cfg.consts.Sounds
import content.global.skill.production.runecrafting.data.Altar
import core.api.*
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Handles the infusion of the metal wand.
 *
 * Related to [What Lies Below][content.region.misthalin.quest.member.whatliesbelow.WhatLiesBelow] quest.
 * @author Vexia
 */
@Initializable
class MetalWandHandler : UseWithHandler(WhatLiesBelowListeners.WAND) {
    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(Altar.CHAOS.scenery, OBJECT_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        if (!inInventory(player, WhatLiesBelowListeners.CHAOS_RUNES, 15)) {
            player.sendMessage("You need 15 chaos runes.")
            return true
        }

        val chaosItem = if (inInventory(player, WhatLiesBelowListeners.CHAOS_TALISMAN)
        ) WhatLiesBelowListeners.CHAOS_TALISMAN else WhatLiesBelowListeners.CHAOS_TIARA
        if (getStatLevel(player, Skills.RUNECRAFTING) >= 35) {
            if (chaosItem != null && removeItem(player, Item(WhatLiesBelowListeners.CHAOS_RUNES, 15))) {
                lock(player, 5)
                playAudio(player, Sounds.SUROK_BINDWAND_3524)
                removeItem(player, WhatLiesBelowListeners.WAND)
                addItem(player, WhatLiesBelowListeners.INFUSED_WAND)
                animate(player, 6104)
                sendDialogueLines(
                    player,
                    "The metal wand bursts into life and crackles with arcane",
                    "power. This is a powerful instrument indeed!"
                )
            }
            return true
        } else {
            sendDialogue(player, "You need a Runecrafting level of 35 to make the infused wand.")
            return false
        }
    }
}