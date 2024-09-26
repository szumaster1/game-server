package content.global.handlers.item.withnpc

import core.api.openDialogue
import core.api.visualize
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.item.Item
import org.rs.consts.Items
import org.rs.consts.NPCs
import org.rs.consts.Scenery

class HatEasterEggListener : InteractionListener {

    private val machineId = Scenery.MACHINE_20040
    private val wizardHat = Items.WIZARD_HAT_579

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, wizardHat, machineId) { player, _, _ ->
            openDialogue(player, object : DialogueFile() {
                override fun handle(componentID: Int, buttonID: Int) {
                    npc = NPC(NPCs.WATCHTOWER_WIZARD_872)
                    when(stage) {
                        0 -> {
                            npc("WHAT HAVE YOU DONE?")
                            visualize(player, -1, 482)
                            stage++
                        }
                        1 -> player(FacialExpression.AFRAID, "What do you mean?!").also { stage++ }
                        2 -> npc("You've disjointed the fabric assimilation matrix!").also { stage++ }
                        3 -> player(FacialExpression.THINKING, "W-what...?").also { stage++ }
                        4 -> npc("You've put us at risk of ripping Gielinor apart!").also { stage++ }
                        5 -> player(FacialExpression.HALF_GUILTY, "I.. I just wanted a hat...").also { stage++ }
                        6 -> npc("Damn you and damn your hat! You could kill us all!").also { stage++ }
                        7 -> player("I... I'm sorry....").also { stage++ }
                        8 -> npc("*sigh* I've managed to stabilize the flux material inductors.").also { stage++ }
                        9 -> npc("You may just be safe, yet.").also { stage++ }
                        10 -> npc("Here, take your damn hat and get out of here.").also { stage++ }
                        11 -> {
                            end()
                            player.inventory.remove(Item(Items.WIZARD_HAT_579))
                            player.inventory.add(Item(Items.BIG_WIZARD_HAT_14650))
                        }
                    }
                }
            })
            return@onUseWith true
        }
    }
}
