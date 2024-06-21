package content.region.asgarnia.handlers.portsarim.mogre

import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class SkippyNightmareListener : InteractionListener {

    val skippy = intArrayOf(2795,2796,2797,2798,2799)

    override fun defineListeners() {
        on(skippy, IntType.NPC, "talk-to", "sober-up") { player, _ ->
            when(getVarbit(player, SkippyUtils.VARBIT_SKIPPY_AND_THE_MOGRES_PROGRESS)){
                0 -> {
                    if(!inInventory(player, Items.BUCKET_OF_WATER_1929)){
                        sendPlayerDialogue(player,"You know, I could shock him out of it if I could find some cold water...")
                    } else {
                        openDialogue(player, SobberSkippyDialogue())
                    }
                }
                1 -> {
                    if(inInventory(player, Items.BUCKET_OF_WATER_1929)) {
                        sendPlayerDialogue(player, "I think he's sober enough. And I don't want to use another bucket of water.")
                    } else {
                        openDialogue(player, SkippyDialogue())
                    }
                }
                2 -> openDialogue(player, SkippyHangoverCureDialogue())
                else -> openDialogue(player, SkippyAfterDialogue())
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
    }

}