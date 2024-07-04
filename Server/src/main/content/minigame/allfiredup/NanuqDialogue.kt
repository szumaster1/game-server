package content.minigame.allfiredup

import core.api.amountInInventory
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.getStatLevel
import core.api.isQuestComplete
import core.api.removeItem
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

@Initializable
class NanuqDialogue(player: Player? = null) : Dialogue(player) {

    companion object {
        private val beacon = AFUBeacon.GWD
        private val VALID_LOGS = arrayOf(
            Items.LOGS_1511,
            Items.OAK_LOGS_1521,
            Items.WILLOW_LOGS_1519,
            Items.MAPLE_LOGS_1517,
            Items.YEW_LOGS_1515,
            Items.MAGIC_LOGS_1513
        )

        private fun getLogs(player: Player, amount: Int): Item {
            var logId = 0
            for (log in VALID_LOGS) {
                if (amountInInventory(player, log) >= amount) {
                    logId = log
                    break
                }
            }
            return Item(logId, amount)
        }
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val session: AFUSession? = player.getAttribute("afu-session", null)
        val logs = getLogs(player, 5)
        when (stage) {
            START_DIALOGUE -> {
                if (getStatLevel(player, Skills.SUMMONING) < 81) {
                    npc(FacialExpression.CHILD_NORMAL, "Awwf uurrrhur.", "(You need 81 Summoning to communicate with Nanuq.)").also { stage = END_DIALOGUE }
                } else if (!isQuestComplete(player, "All Fired Up")) {
                    npc(FacialExpression.CHILD_NORMAL, "Grrrrrrrrr.", "(I don't have time to chat with you right now.)").also { stage = END_DIALOGUE }
                } else {
                    npc(FacialExpression.CHILD_NORMAL, "Grrroooorrgh.", "(Hello, ${player.name}. What can I do for you today?)").also { stage++ }
                }
            }

            1 -> showTopics(
                Topic("Could you look after this beacon for me?", 10),
                Topic("Can you tell me about other beacons?", 20),
                Topic("Is it possible to get information about beacons that are further away?", 30, true),
                Topic("How's it going?", 40),
                Topic("I'm done talking to you, now!", 50)
            )

            10 -> {
                if (beacon.getState(player) == BeaconState.LIT && session?.isWatched(beacon.ordinal) == false) {
                    if (logs.id != 0) {
                        playerl(FacialExpression.NEUTRAL, "Here are five logs for you.").also { stage++ }
                    } else {
                        npc(FacialExpression.CHILD_NORMAL, "Grrrrrrrrr. Groooowliowlioooo!").also { stage = END_DIALOGUE }
                    }
                } else if (beacon.getState(player) == BeaconState.LIT && session?.isWatched(beacon.ordinal) == true) {
                    npc(FacialExpression.CHILD_NORMAL, "Grrrrrgh. Grrrrrgh.", "(Of course. I will; don't worry.", "I'll use the logs you've already left with me.)").also { stage = END_DIALOGUE }
                } else {
                    npc(FacialExpression.CHILD_NORMAL, "Grrrrrgh. Groooow.", "(Of course. Once the beacon is lit, just leave 5 logs with me", "and I'll take care of the fire for as long as I can.)").also { stage = END_DIALOGUE }
                }
            }
            11 -> npc(FacialExpression.CHILD_NORMAL, "Grrr. Groooow.", "(Thanks. I'll use these five logs to keep the beacon", "burning while you're away.)").also {
                removeItem(player, logs)
                session?.setWatcher(beacon.ordinal, logs)
                stage = END_DIALOGUE
            }
            20 -> npc(FacialExpression.CHILD_NORMAL, "Grrrr.", "(Map not implemented.)").also { stage = END_DIALOGUE }
            30 -> playerl(FacialExpression.NEUTRAL, "Is it possible to get information about beacons that are further away?").also { stage++ }
            31 -> npc(FacialExpression.CHILD_NORMAL, "Grrrrr.", "(Macaw pouch not implemented.)").also { stage = END_DIALOGUE }
            40 -> npc(FacialExpression.CHILD_NORMAL, "Grrr.", "(Nanuq ignores you.)").also { stage = END_DIALOGUE }
            50 -> npc(FacialExpression.CHILD_NORMAL, "Grrrrrrrrr. Groooowliowlioooo!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.NANUQ_8076)
    }

}
