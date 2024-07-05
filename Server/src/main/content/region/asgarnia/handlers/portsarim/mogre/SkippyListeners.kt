package content.region.asgarnia.handlers.portsarim.mogre

import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class SkippyListeners : InteractionListener {

    val skippy = intArrayOf(2795,2796,2797,2798,2799)

    override fun defineListeners() {
        on(skippy, IntType.NPC, "sober-up") { player, node ->
            if (getVarbit(player, SkippyUtils.VARBIT_SKIPPY_AND_THE_MOGRES_PROGRESS) == 1) {
                sendPlayerDialogue(player, "I think he's sober enough. And I don't want to use another bucket of water.")
            } else {
                player.dialogueInterpreter.open(node.asNpc().id)
            }
            return@on true
        }

        onUseWith(IntType.NPC, Items.FORLORN_BOOT_6663, *skippy) { player, used, _ ->
            if (removeItem(player, used.asItem())) {
                sendNPCDialogue(player, NPCs.SKIPPY_2796, "Thanks, now I have two right boots!")
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

        onUseWith(IntType.NPC, Items.BUCKET_OF_WATER_1929, *skippy) { player, used, _ ->
            if(getVarbit(player, SkippyUtils.VARBIT_SKIPPY_AND_THE_MOGRES_PROGRESS) > 1 && used.id == 1929){
                sendPlayerDialogue(player, "I think he's sober enough. And I don't want to use another bucket of water.")
            } else {
                sendMessage(player, "I can't do that.")
            }
            return@onUseWith true
        }
    }

}
