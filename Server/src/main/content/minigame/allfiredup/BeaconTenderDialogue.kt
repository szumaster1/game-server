package content.minigame.allfiredup

import core.api.consts.Items
import core.api.getStatLevel
import core.api.sendDialogueOptions
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.GameWorld
import core.plugin.Initializable
import core.tools.END_DIALOGUE

private val VALID_LOGS = arrayOf(
    Items.LOGS_1511,
    Items.OAK_LOGS_1521,
    Items.WILLOW_LOGS_1519,
    Items.MAPLE_LOGS_1517,
    Items.YEW_LOGS_1515,
    Items.MAGIC_LOGS_1513
)

@Initializable
class BeaconTenderDialogue(player: Player? = null) : Dialogue(player) {
    var index = 0

    override fun newInstance(player: Player?): Dialogue {
        return BeaconTenderDialogue(player)
    }

    override fun open(vararg args: Any?): Boolean {
        npc = (args[0] as NPC).getShownNPC(player)
        index = getIndexOf((args[0] as NPC).originalId)
        val faceExpression = if(npc.id in intArrayOf(8054,8059,8063)) FacialExpression.OLD_NORMAL else FacialExpression.HALF_GUILTY
        if (index == AFUBeacon.GWD.ordinal && getStatLevel(player, Skills.SUMMONING) < 81) {
            npc(faceExpression, "Awwf uurrrhur", "(You need 81 Summoning to communicate with Nanuq.)")
            stage = 1000
            return true
        }
        if (index == AFUBeacon.MONASTERY.ordinal && getStatLevel(player, Skills.PRAYER) < 53) {
            npc(faceExpression,"I will aid you when your devotion is", "strong enough.", "(You need 53 Prayer for him to assist you.)")
            stage = 1000
            return true
        }
        npc(faceExpression, "Hello, adventurer.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val beacon = AFUBeacon.values()[index]
        val logs = getLogs(player, 5)
        val session: AFUSession? = player.getAttribute("afu-session", null)
        val faceExpression = if(npc.id in intArrayOf(8054,8059,8063)) FacialExpression.OLD_NORMAL else FacialExpression.HALF_GUILTY
        when (stage) {
            0 -> if (!GameWorld.settings!!.isMembers) {
                sendMessage(player, "You need to be on a members' world to access this content.")
            } else {
                player("Hello!").also { stage++ }
            }

            1 -> if (beacon.getState(player) == BeaconState.LIT && session?.isWatched(index) == false) {
                npc(faceExpression,"Hi. What you want?").also { stage++ }
            } else {
                npc(faceExpression,"I'm busy here. No time to chat.").also { stage = 1000 }
            }

            2 -> options("Could you look after this beacon for me?", "Nevermind.").also { stage = 10 }
            10 -> when (buttonId) {
                1 -> player("Can you watch this beacon for me?").also { stage++ }
                2 -> player("Nevermind.").also { stage = END_DIALOGUE }
            }

            11 -> npcl(faceExpression,"Certainly, adventurer. Do you have logs for me?").also { stage++ }
            12 -> sendDialogueOptions(player, "Do you want to give five logs to the fire tender?", "Yes.", "No.").also { stage++ }

            13 -> when (buttonId) {
                1 -> {
                    if (logs.id != 0) {
                        player("Here are five logs for you.").also {
                            player.inventory.remove(logs)
                            session?.setWatcher(index, logs)
                            stage = 14
                        }
                    } else {
                        npc(faceExpression,"How you expect me to tend fire for you? You not have enough good logs for me to use!").also { stage = 2000 }
                    }
                }

                2 -> end()
            }

            14 -> npcl(faceExpression, "Thanks. I use these five logs to keep beacon burning whilst you away.").also { stage++ }

            1000 -> player("You don't look busy to me.").also { stage++ }
            1001 -> npc(faceExpression, "How little you understand.").also { stage++ }
            1002 -> player("Exactly what I don't understand, then?").also { stage++ }
            1003 -> npc(faceExpression, "It take too long to explain.").also { stage++ }
            1004 -> player("Well, I've got nothing urgent I need to do - please", "explain.").also { stage++ }
            1005 -> npc(faceExpression, "It not worth it. You annoy me, be off with you!").also { stage++ }
            1006 -> player("Right, I'll see you later then, shall I?").also { stage++ }
            1007 -> npc(faceExpression, "Begone!").also { stage++ }
            1008 -> player("Bye!").also { stage++ }
            1009 -> npc(faceExpression, "BEGONE!").also { stage = END_DIALOGUE }

            2000 -> end()
        }
        return true
    }

    fun getIndexOf(id: Int): Int {
        if (id == 8065) return 0
        if (id == 8066) return 1
        for (index in ids.indices) {
            if (ids[index] == id) return index + 2
        }
        return -1
    }

    fun getLogs(player: Player, amount: Int): Item {
        var logId = 0
        for (log in VALID_LOGS) if (player.inventory.getAmount(log) >= amount) {
            logId = log; break
        }
        return Item(logId, amount)
    }

    override fun getIds(): IntArray {
        return intArrayOf(8067, 8068, 8069, 8070, 8071, 8072, 8073, 8074, 8075, 8076)
    }

}
