package content.region.asgarnia.handlers.portsarim.mogre

import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class SkippyListeners : InteractionListener {

    private val skippyWrapperIDs = intArrayOf(2795,2796,2797,2798,2799)

    override fun defineListeners() {

        /*
         * Sober up interaction.
         */

        on(skippyWrapperIDs, IntType.NPC, "sober-up") { player, node ->
            player.dialogueInterpreter.open(node.id)
            return@on true
        }

        /*
         * Used Forlorn boots on Skippy.
         */

        onUseWith(IntType.NPC, Items.FORLORN_BOOT_6663, *skippyWrapperIDs) { player, used, _ ->
            if (removeItem(player, used.asItem())) {
                sendNPCDialogue(player, NPCs.SKIPPY_2796, "Thanks, now I have two right boots!")
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

        /*
         * Used bucket of water on skippy (after used before).
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
