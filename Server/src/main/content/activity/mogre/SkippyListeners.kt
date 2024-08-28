package content.activity.mogre

import core.api.*
import cfg.consts.Items
import cfg.consts.NPCs
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Skippy listeners.
 * @author szumaster
 */
class SkippyListeners : InteractionListener {

    // Array of Skippy wrapper IDs for interaction.
    private val skippyWrapperIDs = intArrayOf(2795,2796,2797,2798,2799)

    override fun defineListeners() {

        /**
         * Handle the interaction when player tries to sober up Skippy
         */
        on(skippyWrapperIDs, IntType.NPC, "sober-up") { player, node ->
            player.dialogueInterpreter.open(node.id)
            return@on true
        }

        /**
         * Handle the action of using Forlorn boots on Skippy
         */
        onUseWith(IntType.NPC, Items.FORLORN_BOOT_6663, *skippyWrapperIDs) { player, used, _ ->
            if (removeItem(player, used.asItem())) {
                sendNPCDialogue(player, NPCs.SKIPPY_2796, "Thanks, now I have two right boots!")
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

        /**
         * Handle the action of using a bucket of water on Skippy after a previous use
         */
        onUseWith(IntType.NPC, Items.BUCKET_OF_WATER_1929, *skippyWrapperIDs) { player, used, _ ->
            if(getVarbit(player, SkippyUtils.VARBIT_SKIPPY_AND_THE_MOGRES_PROGRESS) > 1 && used.id == 1929){
                sendPlayerDialogue(player, "I think he's sober enough. And I don't want to use another bucket of water.")
            } else {
                sendMessage(player, "I can't do that.")
            }
            return@onUseWith true
        }
    }

}
