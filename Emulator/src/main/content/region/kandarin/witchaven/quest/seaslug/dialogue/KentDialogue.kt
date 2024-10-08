package content.region.kandarin.witchaven.quest.seaslug.dialogue

import content.region.kandarin.witchaven.quest.seaslug.SeaSlug
import core.api.*
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs
import org.rs.consts.QuestName
import org.rs.consts.Sounds

/**
 * Represents the Kent dialogue.
 */
@Initializable
class KentDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when {
            // After complete the quest.
            isQuestComplete(player, QuestName.SEA_SLUG) -> npc("Hello there, ${player.username}!").also { stage = 200 }
            // First talk.
            getQuestStage(player, QuestName.SEA_SLUG) < 15 -> npc("Oh thank Saradomin! I thought I'd be left out here", "forever.").also { stage = 0 }
            // Talk to him again.
            else -> player("Hello.").also { stage = 100 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.HALF_GUILTY, "Your wife sent me out to find you and your boy.", "Kennith's fine by the way, he's on the platform.").also { stage++ }
            1 -> npc(FacialExpression.HALF_GUILTY, "I knew the row boat wasn't sea worthy. I couldn't risk", "bringing him along but you must get him off that", "platform.").also { stage++ }
            2 -> player(FacialExpression.HALF_ASKING, "What's going on here?").also { stage++ }
            3 -> npc(FacialExpression.HALF_GUILTY, "Five days ago we pulled in a huge catch. As well as fish", "we caught small slug like creatures, hundreds of them.").also { stage++ }
            4 -> npc(FacialExpression.HALF_GUILTY, "That's when the fishermen began to act strange.").also { stage++ }
            5 -> npc(FacialExpression.HALF_GUILTY, "It was the sea slugs, they attack themselves to your", "body and somehow take over the mind of the carrier.").also { stage++ }
            6 -> npc(FacialExpression.HALF_GUILTY, "I told Kennith to hide until I returned but I was", "washed up here.").also { stage++ }
            7 -> npc(FacialExpression.HALF_GUILTY, "Please go back and get my boy, you can send help for", "me later.").also { stage++ }
            8 -> npc(FacialExpression.SCARED,"${player.username} wait!").also {
                setQuestStage(player!!, QuestName.SEA_SLUG, 15)
                setAttribute(player, "/save:${SeaSlug.ATTRIBUTE_TALK_WITH_KENT}", true)
                stage++
            }
            9 -> {
                end()
                submitWorldPulse(object : Pulse() {
                    var ticks = 0
                    override fun pulse(): Boolean {
                        when (ticks++) {
                            2 -> {
                                sendMessage(player, "*slooop*")
                                playAudio(player, Sounds.SLUG_SCOOP_SLUG_3025)
                                visualize(findLocalNPC(player, NPCs.KENT_701)!!, 4807, 790)
                                sendMessage(player, "He pulls a sea slug from under your top.")
                            }

                            4 -> {
                                openDialogue(player, KentDialogueFile())
                                return true
                            }
                        }
                        return false
                    }
                })
            }
            100 -> npc(FacialExpression.HALF_GUILTY, "Oh my, I must get back to shore.").also { stage = END_DIALOGUE }
            200 -> player("Hello again Kent.").also { stage++ }
            201 -> npcl(FacialExpression.FRIENDLY, "I never did get the chance to thank you properly for saving Kennith and myself.").also { stage++ }
            202 -> playerl(FacialExpression.FRIENDLY, "Oh, don't be silly, it was nothing really.").also { stage++ }
            203 -> npcl(FacialExpression.FRIENDLY, "Play it down if you will. It was a truly brave thing you did.").also { stage++ }
            204 -> player(FacialExpression.FRIENDLY, "I only did what anyone would have done in my position. I have to go now, take care.").also { stage++ }
            205 -> npc(FacialExpression.FRIENDLY, "You too, ${player.username}. Goodbye.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KENT_701)
    }

    /**
     * Kent dialogue file.
     */
    internal class KentDialogueFile : DialogueFile() {
        override fun handle(componentID: Int, buttonID: Int) {
            npc = NPC(NPCs.KENT_701)
            when (stage) {
                0 -> npc("A few more minutes and that thing would have full", "control of your body.").also { stage++ }
                1 -> player("Yuck! Thanks Kent.").also { stage = END_DIALOGUE }
            }
        }
    }

}
